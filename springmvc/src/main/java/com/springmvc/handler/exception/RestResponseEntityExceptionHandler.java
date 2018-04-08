package com.springmvc.handler.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.springmvc.entity.JsonResultEntity;
import com.springmvc.exception.BusinessException;

@ControllerAdvice
public class RestResponseEntityExceptionHandler {

	@ExceptionHandler(BusinessException.class)
	@ResponseBody
	ResponseEntity<JsonResultEntity> handleException(BusinessException bex){
		return ResponseEntity.ok(new JsonResultEntity(bex.getCode(),bex.getMessage(),null));
	}
	
	@ExceptionHandler(Exception.class)
	@ResponseBody
	ResponseEntity<JsonResultEntity> handleException(Exception ex){
		return ResponseEntity.ok(new JsonResultEntity(500,ex.getMessage(),null));
	}
}
