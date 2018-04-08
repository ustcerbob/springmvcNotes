package com.springmvc.exception;

public enum ExceptionCode {

	
	ILLEGAL_ARGS(40001),
	OVER_SALE(50001);
	
	private int code;
	private ExceptionCode(int code){
		this.code = code;
	}
	public int getCode(){
		return code;
	}
}
