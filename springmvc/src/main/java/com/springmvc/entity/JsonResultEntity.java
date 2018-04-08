package com.springmvc.entity;

import java.util.Map;

public class JsonResultEntity {

	private int code;
	private String message;
	private Map<String, Object> data;
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Map<String, Object> getData() {
		return data;
	}
	public void setData(Map<String, Object> data) {
		this.data = data;
	}
	public JsonResultEntity(int code, String message, Map<String, Object> data) {
		super();
		this.code = code;
		this.message = message;
		this.data = data;
	}
	
	
	
}
