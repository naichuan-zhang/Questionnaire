package com.mrkj.ygl.base;


import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.websocket.Extension;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;


public class BaseContext extends ServerEndpointConfig.Configurator{

	@Override
	public boolean checkOrigin(String originHeaderValue) {
		// TODO Auto-generated method stub
		return super.checkOrigin(originHeaderValue);
	}

	@Override
	public <T> T getEndpointInstance(Class<T> clazz) throws InstantiationException {
		// TODO Auto-generated method stub
		return super.getEndpointInstance(clazz);
	}

	@Override
	public List<Extension> getNegotiatedExtensions(List<Extension> installed, List<Extension> requested) {
		// TODO Auto-generated method stub
		return super.getNegotiatedExtensions(installed, requested);
	}

	@Override
	public String getNegotiatedSubprotocol(List<String> supported, List<String> requested) {
		// TODO Auto-generated method stub
		return super.getNegotiatedSubprotocol(supported, requested);
	}

	@Override
	public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {
		// TODO Auto-generated method stub
		 HttpSession httpSession = (HttpSession)request.getHttpSession();
		 ServletContext context = httpSession.getServletContext();
		 sec.getUserProperties().put(ServletContext.class.getName(), context);
		 sec.getUserProperties().put(HttpSession.class.getName(), httpSession);
	}

}
