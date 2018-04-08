package com.springmvc.exception;

public class IllegalArgException extends BusinessException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public IllegalArgException(String msg){
		super(ExceptionCode.ILLEGAL_ARGS, msg);
	}
	
}
