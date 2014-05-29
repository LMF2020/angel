package com.angel.my.common;

import com.angel.my.model.TPurchaserInfo;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class SessionInterceptor implements HandlerInterceptor {

	private static final Logger logger = Logger.getLogger(SessionInterceptor.class);

	private List<String> excludeUrls;

	public List<String> getExcludeUrls() {
		return excludeUrls;
	}

	public void setExcludeUrls(List<String> excludeUrls) {
		this.excludeUrls = excludeUrls;
	}

	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object object, Exception exception) throws Exception {
		
	}

	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object object, ModelAndView modelAndView) throws Exception {
		
	}

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {
		String redirectURL = "/login";   
		String requestUri = request.getRequestURI();
		String contextPath = request.getContextPath();
		String url = requestUri.substring(contextPath.length());
		logger.debug(url);
		if (excludeUrls.contains(url) || url.startsWith("/resources")) {
			return true;
		} else {
			TPurchaserInfo sessionInfo = (TPurchaserInfo) request.getSession().getAttribute("__SESSIONKEY__");
			if (sessionInfo != null && sessionInfo.getPurchaserCode() != null && !sessionInfo.getPurchaserCode().equals("")) {
				return true;
			} else {
				logger.debug("page url:"+url+"has been blocked!");
				request.setAttribute("loginMsg", "No Auth to visit current page!");
				response.sendRedirect(request.getContextPath() + redirectURL);   
				return false;
			}
		}
	}
}
