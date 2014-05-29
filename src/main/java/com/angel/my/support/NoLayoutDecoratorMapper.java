
package com.angel.my.support;

import com.angel.my.util.AjaxUtil;
import com.opensymphony.module.sitemesh.Decorator;
import com.opensymphony.module.sitemesh.Page;
import com.opensymphony.module.sitemesh.mapper.AbstractDecoratorMapper;

import javax.servlet.http.HttpServletRequest;
public class NoLayoutDecoratorMapper extends AbstractDecoratorMapper {

	@Override
	public Decorator getDecorator(HttpServletRequest request, Page page) {
		if (AjaxUtil.isAjaxRequest(request) || "nolayout".equals(request.getParameter("htmlFormat"))) {
			return null;
		}
		return super.getDecorator(request, page);
	}
	
}
