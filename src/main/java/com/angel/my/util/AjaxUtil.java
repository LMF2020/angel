
package com.angel.my.util;

import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

public class AjaxUtil {
	public static boolean isAjaxRequest(HttpServletRequest request) {
		String requestedWith = request.getHeader("X-Requested-With");
		
		return requestedWith != null ? "XMLHttpRequest".equals(requestedWith) : false;
	}
	
	public static String getRemoteIp(HttpServletRequest request) {
	    String remoteIp = request.getHeader("X-Real-IP"); //nginx反向代理  
	    if (StringUtils.hasText(remoteIp)) {  
	        return remoteIp;  
	    } else {  
	        remoteIp = request.getHeader("x-forwarded-for");//apache反射代理  
	        if (StringUtils.hasText(remoteIp)) {  
	            String[] ips = remoteIp.split(",");  
	            for (String ip : ips) {  
	                if (!"null".equalsIgnoreCase(ip)) {  
	                    return ip;  
	                }  
	            }  
	        }  
	        return request.getRemoteAddr();  
	    }  
	}  

	private AjaxUtil() {}
}
