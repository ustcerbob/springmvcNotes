package com.springmvc.exception;

public class OverSaleException extends BusinessException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public OverSaleException(String msg){
		super(ExceptionCode.OVER_SALE, msg);
	}
	
}
