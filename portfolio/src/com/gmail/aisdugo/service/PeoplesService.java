package com.gmail.aisdugo.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.gmail.aisdugo.vo.Peoples;
//사용자의 요청을 처리하는 서비스
public interface PeoplesService {

	//아이디 중복체크 메소드
	public Map<String, Object> idcheck(Peoples peoples);
	
	//모바일 로그인 처리를 위한 메소드
	public Peoples mlogin(HttpServletRequest request);
	
	//회원가입 메소드
	public boolean register(MultipartHttpServletRequest request);
	
	//로그인 메소드
	public Peoples login(HttpServletRequest request);
	
	
	
}
