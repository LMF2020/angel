package com.angel.my.common;

public class ResponseData {
	//~ Static fields ==================================================================================================
	public static final ResponseData SUCCESS_NO_DATA = new ResponseData(true);
	public static final ResponseData FAILED_NO_DATA = new ResponseData(false);
	
	//~ Instance fields ================================================================================================
	private boolean success;
	private String type;
	private Object message;
	private String requestURI;
	private String execptionTrace;
	
	//~ Constructors ===================================================================================================
	public ResponseData(boolean success) {
		this(success, null, null);
	}
	
	public ResponseData(boolean success, Object message) {
		this(success, null, message);
	}
	
	public ResponseData(boolean success, String type, Object message) {
		this.success = success;
		this.type = type;
		this.message = message;
	}

	//~ Methods ========================================================================================================
	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public Object getMessage() {
		return message;
	}

	public void setMessage(Object message) {
		this.message = message;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getRequestURI() {
		return requestURI;
	}

	public void setRequestURI(String requestURI) {
		this.requestURI = requestURI;
	}

	public String getExecptionTrace() {
		return execptionTrace;
	}

	public void setExecptionTrace(String execptionTrace) {
		this.execptionTrace = execptionTrace;
	}
}
