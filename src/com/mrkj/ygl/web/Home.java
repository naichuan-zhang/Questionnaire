/**
 * 明日科技
 * 于国良 2016-06-29
 * QQ:80303857
 */
package com.mrkj.ygl.web;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.mrkj.ygl.entity.questionnaire.QuestionnaireMain;
import com.mrkj.ygl.service.questionnaire.QuestionnaireService;
import com.mrkj.ygl.standard.Util;

@Controller
@RequestMapping(value = {"index"})
public class Home {

	@Autowired
	QuestionnaireService questionService;
	
	@Autowired
	private Util  ut;
	
	@RequestMapping(method = RequestMethod.GET)
	//@RequiresAuthentication	//验证通过用户可以登录
	public ModelAndView home (HttpServletRequest request,@RequestParam(name="page",defaultValue="0") Integer page,@RequestParam(name="row",defaultValue="10")Integer row){
		ModelAndView mav = new ModelAndView("index");
		
		Map<String,Object> parm = new HashMap<>();
		if (page > 0){
			parm.put("page", (page-1)*row);
		}else{
			parm.put("page", page);
		}
		parm.put("row", row);
		parm.put("mainEndtime",new Date());
		parm.put("mainIsuse","y");
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
	
	
	
	
}
