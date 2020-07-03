package com.mrkj.ygl.springException;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

//@ControllerAdvice
public class SpringException {

	
	@ExceptionHandler(Exception.class)
	public String handlerException (){
		return "error";
	}
	
}
