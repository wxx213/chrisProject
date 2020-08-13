package com.mydeo.seckilldemo.util.param;

import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ParameterUtil {

	public static ConcurrentHashMap<String, String> getParameterMap(HttpServletRequest request) {

		ConcurrentHashMap<String, String> map = new ConcurrentHashMap<String, String>();
		
		Set<String> set = request.getParameterMap().keySet();
		
		for (String obj : set) {
			map.put(obj.toString(), getParameter(obj.toString(), request));
		}
		
		return map;
		
	}
	
	
	
	public static String getParameter(String name, HttpServletRequest request) {
		String result = "";
		
		try {
			result = URLDecoder.decode(request.getParameter(name) == null ? "" : request.getParameter(name), "UTF-8");
		} catch (Exception e) {
			try {
				result = URLDecoder.decode(request.getParameter(name) == null ? "" : request.getParameter(name).replaceAll("%", "%25"), "UTF-8");
			} catch (Exception e1) {
				
			}
		}
		
		return result.trim();
	}
	
	public static String getParameter(String name, String defaultValue, HttpServletRequest request) {
		String result = "";
		
		try {
			result = URLDecoder.decode(request.getParameter(name) == null ? defaultValue : request.getParameter(name), "UTF-8");
		} catch (Exception e) {
			try {
				result = URLDecoder.decode(request.getParameter(name) == null ? defaultValue : request.getParameter(name).replaceAll("%", "%25"), "UTF-8");
			} catch (Exception e1) {
				
			}
		}
		
		return result.trim();
	}
	
	public static String getParameterNull(String name, HttpServletRequest request) {
		
		String result = "";
		
		try {
			result = URLDecoder.decode(request.getParameter(name) == null ? "" : request.getParameter(name), "UTF-8");
		} catch (Exception e) {
			try {
				result = URLDecoder.decode(request.getParameter(name) == null ? "" : request.getParameter(name).replaceAll("%", "%25"), "UTF-8");
			} catch (Exception e1) {
				return null;
			}
		}
		
		return result.trim().equals("") ? null : result.trim();
	}
	
	public static void setAttribute(HttpServletRequest request) {
		Enumeration<String> pNames = request.getParameterNames();
		while(pNames.hasMoreElements()){
			String name=(String)pNames.nextElement();
			String value=getParameter(name, request);
			request.setAttribute(name, value);
		}
	}
}
