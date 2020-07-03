package com.mrkj.ygl.exception;

import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping
public class MrkjException {
	
	
	@RequestMapping("error")
	public String error404(){
	
		return "error";
	}
	
	@RequestMapping("error")
	public String error500(){
		
		return "error";
	}
	
}
