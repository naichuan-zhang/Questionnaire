/**
 * 明日科技
 * 于国良 2016-06-29
 * QQ:80303857
 */
package com.mrkj.ygl.web;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.mrkj.ygl.entity.login.Sys_login;
import com.mrkj.ygl.entity.questionnaire.QuestionnaireMain;
import com.mrkj.ygl.entity.questionnaire.QuestionnaireQuestion;
import com.mrkj.ygl.service.login.Sys_loginService;
import com.mrkj.ygl.service.questionnaire.QuestionnaireService;
import com.mrkj.ygl.standard.Util;

@Controller
public class QuestionnaireController {

	@Autowired
	QuestionnaireService questionService;
	
	@Resource
	Sys_loginService userloginService;
	
	@Autowired
	private Util  ut;
	
	@Autowired
	SimpleDateFormat sdf;
/**
 * 分页查看	
 * @param page
 * @param row
 * @return
 * @throws ParseException 
 * @RequiresRoles(value = { "admin" },logical=Logical.OR)
 */
	@RequestMapping(value="/question",method=RequestMethod.GET)
	public ModelAndView view (@RequestParam(name="page",defaultValue="0") Integer page
			,@RequestParam(name="row",defaultValue="10")Integer row,String mainTitle
			,String mainStartTime,String mainOverTime) throws ParseException{
		ModelAndView mav = new ModelAndView("question");
		
		Map<String,Object> parm = new HashMap<>();
		if (page > 0){
			parm.put("page", (page-1)*row);
		}else{
			parm.put("page", page);
		}
		parm.put("row", row);
		if (mainTitle!=null&&!"".equals(mainTitle.trim())){
			parm.put("mainTitle", mainTitle);
		}
		if (mainStartTime!=null&&Pattern.compile("[0-9]{4}-[0-9]{2}-[0-9]{2}").matcher(mainTitle).matches()){
			parm.put("mainStartTime", sdf.parse(mainStartTime));
		}
		if (mainOverTime!=null&&Pattern.compile("[0-9]{4}-[0-9]{2}-[0-9]{2}").matcher(mainTitle).matches()){
			parm.put("mainOverTime", sdf.parse(mainOverTime));
		}
		List<QuestionnaireMain> mainList = questionService.findMainPage(parm);
		Long count = questionService.findCount(parm);
		Long temp= count%row;
		Long countPage = 0L;
		if (temp==0){
			countPage = count/row;
		}else{
			countPage = count/row+1;
		}
		String currentPage = null;
		if (page >0){
			currentPage = ut.page(page, Integer.valueOf(countPage+""));
		}else{
			currentPage = ut.page(page+1, Integer.valueOf(countPage+""));
		}
		mav.addObject("mainList", mainList);
		mav.addObject("currentPage", currentPage);
		
		return mav;
	}
	
	/**
	 * 我发起的问卷
	 * @param request
	 * @param page
	 * @param row
	 * @param mainTitle
	 * @param mainStartTime
	 * @param mainOverTime
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value="/question/me",method=RequestMethod.GET)
//	@RequiresRoles(value = { "admin" },logical=Logical.OR)
	@RequiresAuthentication
	public ModelAndView meView (HttpServletRequest request,@RequestParam(name="page",defaultValue="0") Integer page,@RequestParam(name="row",defaultValue="10")Integer row,String mainTitle,String mainStartTime,String mainOverTime) throws ParseException{
		ModelAndView mav = new ModelAndView("questionMe");
		
		HttpSession session = request.getSession();
		
		Sys_login loginEntity = (Sys_login)session.getAttribute("loginEntity");
		
		Map<String,Object> parm = new HashMap<>();
		if (loginEntity != null){
			parm.put("mainCreatuser", loginEntity.getWxname());
		}else{
			String username = (String)session.getAttribute("UserName");  //特殊情况下，服务器重新启动但是shiro登陆并没有超时，那么会有一定几率异常。
			loginEntity = userloginService.selectByUsername(username);
			session.setAttribute("loginEntity", loginEntity);
			parm.put("mainCreatuser", loginEntity.getWxname());
		}
		if (page > 0){
			parm.put("page", (page-1)*row);
		}else{
			parm.put("page", page);
		}
		parm.put("row", row);
		if (mainTitle!=null&&!"".equals(mainTitle.trim())){
			parm.put("mainTitle", mainTitle);
		}
		if (mainStartTime!=null&&Pattern.compile("[0-9]{4}-[0-9]{2}-[0-9]{2}").matcher(mainTitle).matches()){
			parm.put("mainStartTime", sdf.parse(mainStartTime));
		}
		if (mainOverTime!=null&&Pattern.compile("[0-9]{4}-[0-9]{2}-[0-9]{2}").matcher(mainTitle).matches()){
			parm.put("mainOverTime", sdf.parse(mainOverTime));
		}
		List<QuestionnaireMain> mainList = questionService.findMainPage(parm);
		Long count = questionService.findCount(parm);
		Long temp= count%row;
		Long countPage = 0L;
		if (temp==0){
			countPage = count/row;
		}else{
			countPage = count/row+1;
		}
		String currentPage = null;
		if (page >0){
			currentPage = ut.page(page, Integer.valueOf(countPage+""));
		}else{
			currentPage = ut.page(page+1, Integer.valueOf(countPage+""));
		}
		mav.addObject("mainList", mainList);
		mav.addObject("currentPage", currentPage);
		
		return mav;
	}
	
	/**
	 * 保存问卷主页面
	 * @param mainTitle
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping(value="/question",method=RequestMethod.POST)
	public ModelAndView newQuestion (
			HttpServletRequest request,String mainTitle,String mainEndtime) 
																throws ParseException{
			//返回ModelAndView并将视图指向question方法重新获取数据
			ModelAndView mav = new ModelAndView("redirect:question");
			QuestionnaireMain main = new QuestionnaireMain();			//创建实体类
			main.setMainId(UUID.randomUUID().toString());				//创建实体类
			main.setMainIsuse("n");										//设置是否使用，是否发布
			main.setMainTitle(mainTitle);								//设置问卷标题
			main.setMainCreat(new Date());								//设置创建时间
			HttpSession session = request.getSession();					//获取session
			//在session中获取用户信息，获取当前用户真实姓名
			Sys_login loginEntity = (Sys_login)session.getAttribute("loginEntity");
			main.setMainCreatuser(loginEntity.getWxname());
			//设置截止时间
			if (mainEndtime!=null&&!"".equals(mainEndtime.trim())){
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				main.setMainEndtime(sdf.parse(mainEndtime));
			}else{
				main.setMainEndtime(new Date());
			}
			//调用service层insertMain方法，将数据保存至数据库中
			questionService.insertMain(main);
			
		return mav;
	}
	
	/**
	 * ID删除
	 * @param mainId
	 * @return
	 */
	@RequestMapping(value="/question/del/{mainId}",method=RequestMethod.DELETE)
	@ResponseBody
//	@RequiresRoles(value = { "admin" },logical=Logical.OR)
	public Map<String,Boolean> deleteQuestion (@PathVariable String mainId){
		Map<String,Boolean> resultMap = new HashMap<>();
		
		if(questionService.deleteMainItem(mainId)){
			resultMap.put("success", true);
		}else{
			resultMap.put("success", false);
		}
		
		return resultMap;
	}
	
	/**
	 * 批量删除
	 * @param id
	 * @return
	 * 
	 * 批量删除
	 */
	@RequestMapping(value="/question/delCheck",method=RequestMethod.POST)
	@ResponseBody
//	@RequiresRoles(value = { "admin" },logical=Logical.OR)
	public Map<String,Boolean> delCheck(HttpServletRequest request,String[] id){
		
		Map<String,Boolean> resultMap = new HashMap<>();

		if (questionService.deleteCheck(id)){
			resultMap.put("success", true);
		}else{
			resultMap.put("success", false);
		}
		
		return resultMap;
	}
	
	/**
	 * 进入到问卷编辑页面
	 * 
	 * @param mainId
	 * @return 返回一份完整的问卷调查，标题 问题 答案
	 * @RequiresRoles(value = { "admin" },logical=Logical.OR)
	 */
	@RequestMapping(value="/question/edit/{mainId}",method=RequestMethod.GET)
	public ModelAndView editQuestion (@PathVariable String mainId){
		ModelAndView mav = new ModelAndView("editQuestionnaire");
		
		Map<String, Object> questionnaire = questionService.selectQuestionnaire(mainId,false);
		mav.addObject("questionnaire", questionnaire);
		
		return mav;
	}
	
	/**
	 * 跳转到问题编辑页面
	 * @param mainId
	 * @return 返回增加问题页面
	 */
	@RequestMapping(value="/question/add/{mainId}",method=RequestMethod.GET)
//	@RequiresRoles(value = { "admin" },logical=Logical.OR)
	public ModelAndView addQuestion (@PathVariable String mainId){
		ModelAndView mav = new ModelAndView("addQuestion");
		
		mav.addObject("mainId", mainId);
		
		return mav;
	}
	
	/**
	 * 这里是增加问题
	 * @param mainId
	 * @param questionTitle 问题
	 * @param questionNum 问题数量
	 * @param questionDestype 答案类型 img text
	 * @param questionType 答案类型 radio check
	 * @return
	 */
	@RequestMapping(value="/question/addAnswer",method=RequestMethod.POST)
//	@RequiresRoles(value = { "admin" },logical=Logical.OR)
	public ModelAndView addAnswer (String mainId,String questionId,String questionTitle,Integer questionNum,String questionDestype,String questionType){
		ModelAndView mav = new ModelAndView("addAnswer");
		
		QuestionnaireQuestion record = new QuestionnaireQuestion();
		if (questionId == null || "".equals(questionId.trim())){
			questionId = UUID.randomUUID().toString();
			record.setQuestionId(questionId);
			record.setMainId(mainId);
			record.setQuestionDestype(questionDestype);
			record.setQuestionTitle(questionTitle);
			record.setQuestionType(questionType);
			questionService.insertQuestion(record);
		}else{
			record.setQuestionId(questionId);
			record.setMainId(mainId);
			record.setQuestionDestype(questionDestype);
			record.setQuestionTitle(questionTitle);
			record.setQuestionType(questionType);
			questionService.updateQuestion(record);
		}
		mav.addObject("questionId", questionId);
		mav.addObject("questionNum", questionNum);
		mav.addObject("questionTitle", questionTitle);
		mav.addObject("questionDestype", questionDestype);
		mav.addObject("questionType", questionType);
		mav.addObject("mainId", mainId);
		
		return mav;
	}
	
	
	/**
	 * 这里是保存文字答案
	 * @param mainId
	 * @param questionNum
	 * @param questionId
	 * @param answerText
	 * @return
	 */
	@RequestMapping(value="/question/addAnswerText",method=RequestMethod.POST)
//	@RequiresRoles(value = { "admin" },logical=Logical.OR)
	public ModelAndView addAnswer (String mainId,Integer questionNum, String questionId,String[] answerText){
		ModelAndView mav = new ModelAndView("redirect:edit/"+mainId);
		
		boolean success = questionService.insertAnswers(questionId, answerText) == questionNum;
		mav.addObject("success", success);
		
		return mav;
	}
	
	/**
	 * 这里是保存图片答案
	 *
	 * @param request
	 * @param mainId
	 * @param questionNum
	 * @param questionId
	 * @param files
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value="/question/addAnswerFile",method=RequestMethod.POST)
//	@RequiresRoles(value = { "admin" },logical=Logical.OR)
	public ModelAndView addAnswer (HttpServletRequest request,String mainId,Integer questionNum ,String questionId,@RequestParam("answerFile") CommonsMultipartFile[] files) throws IOException{
		ModelAndView mav = new ModelAndView("redirect:edit/"+mainId);
		
		int i = 0;
		String[] answerText = new String[files.length];
		String[] answerDestype = new String[answerText.length];
		for (CommonsMultipartFile cmfile : files ){
			String newFileName = UUID.randomUUID().toString();
			String ph = request.getSession().getServletContext().getRealPath("/upload");
			String oldFileName = cmfile.getOriginalFilename();
			String ext = oldFileName.substring(oldFileName.lastIndexOf("."));
			StringBuffer sb = new StringBuffer(ph).append("/photo/");
			sb.append(newFileName).append(ext);
			File file = new File(sb.toString());
			FileUtils.writeByteArrayToFile(file, cmfile.getBytes());
		    String filepath = new StringBuffer("upload/photo/").append(newFileName).append(ext).toString();
		    answerText[i]=filepath;
		    answerDestype[i]=oldFileName;
		    i++;
		}
		boolean success = questionService.insertAnswers(questionId, answerText,answerDestype)==questionNum;
		mav.addObject("success", success);
		
		return mav;
	}
	
	/**
	 * 这里是删除问题&答案
	 * @param questionId
	 * @return
	 */
	@RequestMapping(value="/question/delQuestion/{questionId}",method=RequestMethod.DELETE)
	@ResponseBody
//	@RequiresRoles(value = { "admin" },logical=Logical.OR)
	public Map<String,Object> delQuestion (@PathVariable String questionId){
		Map<String,Object> resultMap = new HashMap<>();
		
		resultMap.put("success", questionService.delectQuestionAndAnswer(questionId));
		
		return resultMap;
	}
	
	/**
	 * 这里是编辑问题&答案
	 * @param mainId
	 * @param questionId
	 * @return
	 */
	@RequestMapping(value="/question/editAnswer/{mainId}/{questionId}",method=RequestMethod.GET)
//	@RequiresRoles(value = { "admin" },logical=Logical.OR)
	public ModelAndView editAnswer (@PathVariable String mainId,@PathVariable String questionId){
		ModelAndView mav = new ModelAndView("addQuestion");
		
		mav.addObject("questionnaireQuestion", questionService.selectQuestionById(questionId));
		mav.addObject("questionId", questionId);
		mav.addObject("mainId", mainId);
		
		return mav;
	}
	
	/**
	 * 这是问卷发布，每次发布一个哦
	 * @param mainId
	 * @return
	 */
	@RequestMapping(value="question/action/{mainId}",method=RequestMethod.PUT)
	@ResponseBody
//	@RequiresRoles(value = { "admin" },logical=Logical.OR)
	public Map<String,Boolean> actionStart (@PathVariable String mainId){
		Map<String,Boolean> resultMap = new HashMap<>();
		
		QuestionnaireMain record = new QuestionnaireMain();
		record.setMainId(mainId);
		record.setMainIsuse("y");
		resultMap.put("success", questionService.updateMainAction(record));
		
		return resultMap;
	}
	
	/**
	 * 暂停问卷
	 * @param mainId
	 * @return
	 */
	@RequestMapping(value="question/pause/{mainId}",method=RequestMethod.PUT)
	@ResponseBody
//	@RequiresRoles(value = { "admin" },logical=Logical.OR)
	public Map<String,Boolean> pause (@PathVariable String mainId){
		Map<String,Boolean> resultMap = new HashMap<>();
		
		QuestionnaireMain record = new QuestionnaireMain();
		record.setMainId(mainId);
		record.setMainIsuse("n");
		resultMap.put("success", questionService.updateMainAction(record));
		
		return resultMap;
	}
	
	/**
	 * 保存问卷调查
	 * @return
	 */
	@RequestMapping(value="question/actionSub",method=RequestMethod.POST)
	@ResponseBody
//	@RequiresAuthentication
	public Map<String,Boolean> actionSub (HttpServletRequest request){
		Map<String,Boolean> resultMap = new HashMap<>();
		
		HttpSession session = request.getSession();
		String username = (String)(session.getAttribute("UserName")!=null?session.getAttribute("UserName"):"");
		String mainId = request.getParameter("mainId");
		Sys_login sysLoginEntity = (Sys_login)session.getAttribute("loginEntity");
		
		if(questionService.selectMainUser(username,mainId)){
			String message = request.getParameter("message");
			List<String> parms = new ArrayList<>();
			Map<String, String[]> parm = request.getParameterMap();
			Set<Entry<String, String[]>> entrySet = parm.entrySet();
			for (Entry<String, String[]> entry : entrySet){
				String[] answerIds = entry.getValue();
				parms.addAll(Arrays.asList(answerIds));
			}
			resultMap.put("success", questionService.updateValueIn(parms,username,mainId,message,sysLoginEntity.getWxname()));
		}else{
			resultMap.put("success", false);
		}

		return resultMap;
	}
	
	/**
	 * @RequiresAuthentication
	 * @param mainId
	 * @return
	 */
	
	@RequestMapping(value="question/statistics/{mainId}",method=RequestMethod.GET)
	public ModelAndView statistics (@PathVariable String mainId){
		ModelAndView mav = new ModelAndView("statistics");
		
		Map<String, Object> questionnaire = 
				questionService.selectQuestionnaire(mainId,true);	//获取完整问卷
		//把完整问卷放置于ModelAndView当中，这等同于放置在request域当中，方便JSP页面获取
		mav.addObject("questionnaire", questionnaire);				
		
		return mav;
	}
	
	/**
	 * 拷贝问卷一份
	 * @param mainId
	 * @return
	 */
	
	@RequestMapping(value="question/copy/{mainId}",method=RequestMethod.GET)
	@ResponseBody
//	@RequiresRoles(value = { "admin" },logical=Logical.OR)
	@RequiresAuthentication
	public Map<String,Boolean> copyQuestion (HttpServletRequest request,@PathVariable String mainId){
		Map<String,Boolean> resultMap = new HashMap<>();
		HttpSession session = request.getSession();
		Sys_login loginEntity = (Sys_login)session.getAttribute("loginEntity");
		try{
			resultMap.put("success", questionService.updateCopyQuestion(mainId,loginEntity.getWxname()));
		}catch(Exception e){
			resultMap.put("success", false);
		}
		
		return resultMap;
	}
	
	/**
	 * 修改主表Title
	 * @return
	 */
	@RequestMapping(value="question/editMainTitle",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Boolean> editMainTitle (String mainId,String mainTitle,String mainEndtime){
		//通过@ResponseBody注解可以将返回的Map转换为json格式
		Map<String,Boolean> resultMap = new HashMap<>();
		//调用service层当中的updateMainTitle方法进行插入或修改操作。
		resultMap.put("success", questionService.updateMainTitle(mainId, mainTitle,mainEndtime));

		return resultMap;
	}
	
	@RequestMapping(value="question/delWt/{questionId}",method=RequestMethod.DELETE)
	@ResponseBody
	public Map<String,Boolean> delWt (@PathVariable String questionId){
		
		Map<String,Boolean> resultMap = new HashMap<>();
		resultMap.put("success", questionService.delectQuestionAndAnswer(questionId));
		
		return resultMap;
	}
	
	//增加问题与答案，第二版。
	
	@RequestMapping(value="question/addQuestionTestAndFile",method=RequestMethod.POST)
	public ModelAndView addQuestionTestAndFile(MultipartHttpServletRequest request
													,String mainId) throws IOException{
		//转发重定向前面使用了redirect前缀，为了让URL路径始终保持在编辑页面
		ModelAndView mav = new ModelAndView("redirect:/question/edit/"+mainId);
		//获取所有文字参数以Map的形式呈现
		Map<String, String[]> parms = request.getParameterMap();
		//获取文件参数以Map形式呈现
		Map<String, MultipartFile> fileMap = request.getFileMap();
		//保存文件路径的Map
		Map<String,String> resultFileMap = new HashMap<>();
		//获取EntrySet，Entry是Map的键值对。
		Set<Entry<String, MultipartFile>> parmEntrySet = fileMap.entrySet();
		//使用for循环迭代文件并上传文件
		for (Entry<String, MultipartFile> tempEntry : parmEntrySet){
			MultipartFile mf = tempEntry.getValue();
			if (mf.getSize()>0){
				//设置文件名称，为避免重复使用UUID
				String newFileName = UUID.randomUUID().toString();
				//获取文件上传路径（绝对路径）
				String ph = request.getSession().getServletContext()
												.getRealPath("/upload");
				//获取文件名的原始名称，即从客户端上传过来的文件名称
				String oldFileName = mf.getOriginalFilename();
				//截取扩展名
				String ext = oldFileName.substring(oldFileName.lastIndexOf("."));
				//拼接文件完成路径
				StringBuffer sb = new StringBuffer(ph).append("/file/");
				sb.append(newFileName).append(ext);
				File file = new File(sb.toString());
				//调用调用org.apache.commons.io.FileUtils的copyInputStreamToFile写入文件
				FileUtils.copyInputStreamToFile(mf.getInputStream(), file);
				//获取文件相对路径
				String filepath = new StringBuffer("upload/file/").append(newFileName)
						.append(ext).toString();
				//使用Map保存路径，键为前台传递过来的guid，值为上传文件的相对路径
				resultFileMap.put(tempEntry.getKey(), filepath);
			}
		}
		//调用Service层insertQuestionAndAnswers方法保存问题与答案
		questionService.insertQuestionAndAnswers(mainId,resultFileMap,parms);

		return mav;
	}
	
	//修改操作时，返回问题与答案，第二版。
	
	@RequestMapping(value="question/editQuestionnaire/{questionId}",method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> editQuestionnaire(@PathVariable String questionId){
	
		return questionService.selectQuestionByIdToAnswer(questionId);
	}
	
	
	//修改问题与答案，第二版。
	
	@RequestMapping(value="question/editQuestionTestAndFile",method=RequestMethod.POST)
	public ModelAndView editQuestionTestAndFile(MultipartHttpServletRequest request
									,String mainId,String questionId) throws IOException{
		//转发重定向前面使用了redirect前缀，为了让URL路径始终保持在编辑页面
		ModelAndView mav = new ModelAndView("redirect:/question/edit/"+mainId);
		//获取所有文字参数以Map的形式呈现
		Map<String, String[]> parms = request.getParameterMap();
		//获取文件参数以Map形式呈现
		Map<String, MultipartFile> fileMap = request.getFileMap();
		//保存文件路径的Map
		Map<String,String> resultFileMap = new HashMap<>();
		//获取EntrySet，Entry是Map的键值对。
		Set<Entry<String, MultipartFile>> parmEntrySet = fileMap.entrySet();
		//使用for循环迭代文件并上传文件
		for (Entry<String, MultipartFile> tempEntry : parmEntrySet){
			MultipartFile mf = tempEntry.getValue();
			if (mf.getSize()>0){
				//设置文件名称，为避免重复使用UUID
				String newFileName = UUID.randomUUID().toString();
				//获取文件上传路径（绝对路径）
				String ph = request.getSession().getServletContext()
												.getRealPath("/upload");
				//获取文件名的原始名称，即从客户端上传过来的文件名称
				String oldFileName = mf.getOriginalFilename();
				//截取扩展名
				String ext = oldFileName.substring(oldFileName.lastIndexOf("."));
				//拼接文件完成路径
				StringBuffer sb = new StringBuffer(ph).append("/file/");
				sb.append(newFileName).append(ext);
				File file = new File(sb.toString());
				//调用调用org.apache.commons.io.FileUtils的copyInputStreamToFile写入文件
				FileUtils.copyInputStreamToFile(mf.getInputStream(), file);
				//获取文件相对路径
				String filepath = new StringBuffer("upload/file/").append(newFileName)
																.append(ext).toString();
				//使用Map保存路径，键为前台传递过来的guid，值为上传文件的相对路径
				resultFileMap.put(tempEntry.getKey(), filepath);
			}
		}
		//调用Service层updateQuestionAndAnswers方法更新问题与答案
		questionService.updateQuestionAndAnswers(mainId, resultFileMap, parms);
		
		return mav;
	}
	
	/********************************************以下为前端内容,无任何权限可访问**********************************************************/
	@RequestMapping(value="question/go/{mainId}",method=RequestMethod.GET)
	public ModelAndView goQuestionPage (HttpServletRequest request,@PathVariable String mainId){
		
		ModelAndView mav = new ModelAndView("lowpage/lowquestion");
		
		HttpSession session = request.getSession();
		String username = (String)session.getAttribute("UserName");
		Boolean bflag = true; //questionService.selectMainUser(username, mainId);敞开权限更加合理
		if (bflag){  // 这里判断的是如果用户回答过该问题则不进入该页面
			mav.setViewName("lowpage/lowquestion");
			Map<String, Object> questionnaire = questionService.selectQuestionnaire(mainId,false);
			mav.addObject("questionnaire", questionnaire);
		}else{
			mav.setViewName("lowpage/lowError");
		}
		return mav;
	}
	
	@RequestMapping(value="question/goLowError",method=RequestMethod.GET)
	public ModelAndView goLowError (){
		ModelAndView mav = new ModelAndView("lowpage/lowError");
		
		
		return mav;
	}
}
