/**
 * 明日科技
 * 于国良 2016-06-29
 * QQ:80303857
 */
package com.mrkj.ygl.websocket;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import com.alibaba.fastjson.JSONObject;
import com.mrkj.ygl.base.BaseContext;

@ServerEndpoint(value = "/websocket",configurator=BaseContext.class)
public class MySocket {
	
	//静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;
    
    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static CopyOnWriteArraySet<MySocket> webSocketSet = new CopyOnWriteArraySet<MySocket>();
    public static java.util.concurrent.ConcurrentHashMap<String , String> useronline = new java.util.concurrent.ConcurrentHashMap<String , String>();
    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。若要实现服务端与单一客户端通信的话，可以使用Map来存放，其中Key可以为用户标识
    private Session session;
    private ServletContext context = null;
    private HttpSession httpSession = null;
    /**
     * 连接建立成功调用的方法
     * @param session  可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
     * @throws IOException 
     */
    @OnOpen
    public void onOpen(Session session,EndpointConfig config) throws IOException{
        this.session = session;
        webSocketSet.add(this);
        context = (ServletContext)config.getUserProperties().get(ServletContext.class.getName());
        httpSession = (HttpSession)config.getUserProperties().get(HttpSession.class.getName());
        if (onlineCount == 0){
        	httpSession.setAttribute("userloginname", "admin");
        }else{
        	httpSession.setAttribute("userloginname", "user");
        }
        context.setAttribute((String)httpSession.getAttribute("userloginname"), this);
        
        addOnlineCount();           //在线数加1
        System.out.println("在线人数：" + getOnlineCount());
        sendUseronline ();
    }
     
    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(){
        webSocketSet.remove(this);  //从set中删除
        subOnlineCount();           //在线数减1 
        System.out.println("在线人数：" + getOnlineCount());
    }
     
    /**
     * 收到客户端消息后调用的方法
     * @param message 客户端发送过来的消息
     * @param session 可选的参数
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("接收消息:" + message);
        	try {
	        		JSONObject result = new JSONObject();
	        		SimpleDateFormat sdf = new SimpleDateFormat("hh_mm_ss");
	        		//<dd><h5>小蝶 2016-5-18 13:24:32：</h5><p style="color: #04477c">你好我是客服小蝶~！</p></dd>
	        		result.put("state", "message");
	        		result.put("message", "<dd><h5>小蝶 "+sdf.format(new Date())+"：</h5><p style='color: #04477c'>你好少年！</p></dd>");
		            this.sendMessage(result.toString());
	        
        	} catch (IOException e) {
                e.printStackTrace();
            }
        
    }
     
    
    /**
     * 发生错误时调用
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error){
        System.out.println("报错咯。");
        error.printStackTrace();
    }
     
    /**
     * 这个方法与上面几个方法不一样。没有用注解，是根据自己需要添加的方法。
     * @param message
     * @throws IOException
     */
    public void sendMessage(String message) throws IOException{
        this.session.getBasicRemote().sendText(message);
    }
    
    public void sendUseronline () throws IOException{
    	//群发消息
    	JSONObject result = null;
        for(MySocket item: webSocketSet){
        	result = new JSONObject();
        	result.put("userentity", useronline);
        	result.put("state", "online");
        	item.sendMessage(result.toString());
        }
    }
 
    public static synchronized int getOnlineCount() {
        return onlineCount;
    }
 
    public static synchronized void addOnlineCount() {
    	MySocket.onlineCount++;
    }
     
    public static synchronized void subOnlineCount() {
    	MySocket.onlineCount--;
    }
}
