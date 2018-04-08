package com.springmvc.handler;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.springmvc.exception.BusinessException;
import com.springmvc.exception.IllegalArgException;

@Controller
public class HelloHandler {
	@RequestMapping(value="/helloWorld",method=RequestMethod.GET)
	public String helloWorld(){
		System.out.println("hello world...");
		return "ok";
	}
	
	@ResponseBody
	@RequestMapping(value="/checkParam/{age}",method=RequestMethod.GET)
	public String checkParam(@PathVariable int age, @RequestParam String name) throws IllegalArgException{
		System.out.println(name);
		if(age < 0){
			throw new IllegalArgException("age must bigger than 0");
		}
		return "ok";
	}
	
}
