package com.springmvc.exception;

public abstract class BusinessException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ExceptionCode exceptionCode;
	public BusinessException(ExceptionCode exceptionCode, String msg){
		super(msg);
		this.exceptionCode = exceptionCode;
	}
	
	public int getCode() {
		return exceptionCode.getCode();
	}

	public int getStatusCode() {
		int code = getCode();
		//根据code获取statuscode,取code的前三位
		if(code > 9999){
			return code/100;
		}
		return code/10;
	}
	
	
}
