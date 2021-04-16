package com.example.showcase.interceptor;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.ContentCachingResponseWrapper;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ReqRespLogInterceptor extends HandlerInterceptorAdapter{

 	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		log.info("*****************Request*****************");
		log.info("*****************Start*****************");

		if (null != request) {
			log.info("Start Header Section of request ");
			log.info("Method Type : " + request.getMethod());
			Enumeration headerNames = request.getHeaderNames();
			while (headerNames.hasMoreElements()) {
				String headerName = (String) headerNames.nextElement();
				String headerValue = request.getHeader(headerName);
				log.debug("Header Name: " + headerName + " Header Value : " + headerValue);
			}
			log.info("Request Path info :" + request.getServletPath());
			log.info("End Header Section of request ");
		}
		log.info("*****************End*****************");
		return true;
		//return super.preHandle(request, response, handler);
	}
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		ContentCachingResponseWrapper responseWrapper =new ContentCachingResponseWrapper(response);
		byte[] responseArray=responseWrapper.getContentAsByteArray();
        String responseStr=new String(responseArray,"utf-8");
        System.out.println("string===="+responseStr);       
        /*It is important to copy cached reponse body back to response stream
        to see response */
        responseWrapper.copyBodyToResponse();
		//super.postHandle(request, response, handler, modelAndView);
	}
}
