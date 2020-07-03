package com.mrkj.ygl.filter;

import javax.servlet.annotation.WebFilter;

//@WebFilter(urlPatterns = "/*",filterName="encodingFilter")
public class EncodingFilter extends org.springframework.web.filter.CharacterEncodingFilter {

	@Override
	public void setEncoding(String encoding) {
		// TODO Auto-generated method stub
		super.setEncoding("UTF-8");
	}
	
}
