package com.gmail.aisdugo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.gmail.aisdugo.service.FreeboardService;

@Controller
public class FreeboardController {
	
	@Autowired
	private FreeboardService freeboardService;
	

}
