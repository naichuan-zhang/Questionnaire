/**
 * 明日科技
 * 于国良 2016-06-29
 * QQ:80303857
 */
package com.mrkj.ygl.web;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.hp.hpl.sparta.ParseException;
import com.mrkj.ygl.service.workday.ZQRLService;


@Controller
@RequestMapping("/zqrl")
public class WorkDayController {
	
	@Resource(name="zQRLService")
	private ZQRLService zQRLService;
	
	@RequestMapping("/zqrlset")
	public ModelAndView goZQRLSet()
	{   
		ModelAndView mv = new ModelAndView("workDatSet");
		mv.addObject("data",JSONArray.toJSON(zQRLService.getAll()));
		return mv;
	}
	
	@RequestMapping("/getevents")
	@ResponseBody
	public Object getevents(String start,String end)
	{
		return zQRLService.getDataByParameters(start,end);
	}
	
	
	@RequestMapping("/addzqrl")
	@ResponseBody
	public Object addZQCalendar(String title,String start,String end) throws ParseException
	{
		/*红色：#f26866；黄色：#f7c966；蓝色：#64d4fa；橘色：#f1a247；绿色：#1fce6f*/
		String reds[]=new String[]{"#f26866","#f7c966","#64d4fa","#f1a247","#1fce6f"};
		String id = UUID.randomUUID().toString();
		Random r = new Random();
		String color =reds[r.nextInt(4)];
		int isR = zQRLService.add(id,title,color,start,end);
		Map <String,String> res =new HashMap<String,String>();
		res.put("isR", isR+"");
		res.put("id", id);
		res.put("title", title);
		res.put("color", color);
		return res;
	}

	@RequestMapping("/updatezqrl")
	@ResponseBody
	public Object updateZQCalendar(String id ,String title,String start,String end)
	{
		return zQRLService.updateByParameters(id,title,start,end);
	}
	
	@RequestMapping("/delzqrl")
	@ResponseBody
	public Object delZQCalendar(String id)
	{
		return zQRLService.delById(id);
	}
}
