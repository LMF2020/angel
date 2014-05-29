package com.angel.my.common;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

abstract public class BaseController {

	//protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	public final static String EXCEPTION_MESSAGE = "EXCEPTION_MESSAGE";

	//~ Instance fields ================================================================================================
	
	//~ Methods ========================================================================================================
	@InitBinder
	public void initBinder(WebDataBinder binder) {
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        dateFormat.setLenient(false);
//        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
    }
	
	@ExceptionHandler()
	public @ResponseBody String handleException(Exception exception, HttpServletRequest request, HttpServletResponse response) {
		if(!(request.getRequestURI().endsWith(".json") || request.getRequestURI().endsWith("Json")))
			throw new RuntimeException(exception);
		
		ResponseData data = new ResponseData(false, exception.getClass() + ": " + exception.getMessage());
		data.setRequestURI(request.getRequestURI());
		data.setExecptionTrace(ExceptionUtils.getStackTrace(exception));
		request.setAttribute(EXCEPTION_MESSAGE, data.getExecptionTrace());
		
		String json = JSON.toJSONString(data);
		
		response.setStatus(500);
		response.setContentType("application/json;charset=UTF-8");
		return json;
	}
}
