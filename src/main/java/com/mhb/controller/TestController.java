package com.mhb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mhb.service.TestService;

@Controller
@RequestMapping("test")
public class TestController {
	
	@Autowired
	private TestService testService;
	
	@RequestMapping("test")
	public void test() {
		testService.test();
	}
	
}
