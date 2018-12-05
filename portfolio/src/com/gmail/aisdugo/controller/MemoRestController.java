package com.gmail.aisdugo.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gmail.aisdugo.service.MemoService;

@RestController
//위에 어노테이션을 쓸 때는  spring버전에 4.0이상인지 확인
//jackson-databind 라이브러리가 포함되어 있는지 확인
public class MemoRestController {

	//실제 서비스되는 프로젝트의 경우에는 여러개의 서비스 객체가 포함되는 경우가 많습니다.
	@Autowired
	private MemoService memoService;
	
	@RequestMapping(value="parsingpage/memolist", method=RequestMethod.GET)
	public Map<String, Object> memolist(HttpServletRequest request){
		return memoService.memolist(request);
	}
	
	@RequestMapping(value="parsingpage/memodetail", method=RequestMethod.GET)
	public Map<String, Object> memodetail(HttpServletRequest request){
		return memoService.memodetail(request);
	}
}
