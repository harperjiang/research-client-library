package edu.clarkson.cs.clientlib.common.http;

public class ResponseError {

	private Object code;

	private String message;

	public Object getCode() {
		return code;
	}

	public void setCode(Object code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
