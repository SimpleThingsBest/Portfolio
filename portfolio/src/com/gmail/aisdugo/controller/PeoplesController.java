package com.gmail.aisdugo.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gmail.aisdugo.service.PeoplesService;
import com.gmail.aisdugo.vo.Peoples;

@Controller
public class PeoplesController {
	@Autowired
	private PeoplesService peoplesService;
	
	//회원가입 페이지로 이동
	@RequestMapping(value="/peoples/register", method=RequestMethod.GET)
	//단순 페이지이동. ==> 리턴 없이 생성해도 된다.
	//뷰 이름이 peoples/register가 된다.
	public void register(Model model) {
	}
	
	//회원가입 처리
	@RequestMapping(value="/peoples/register", method=RequestMethod.POST)
	public String register(MultipartHttpServletRequest request, RedirectAttributes attr) {
		boolean registerSuccess = peoplesService.register(request);

		System.out.print(registerSuccess);
		if(registerSuccess == true) {
			System.out.print("1-0");
			String t = "true";
			attr.addFlashAttribute("registerSuccess", t);
			System.out.print("1-1");
		}else {
			System.out.print("2-0");
			String t = "false";
			attr.addFlashAttribute("registerSuccess", t);
			System.out.print("2-1");
		}
		return "redirect:../";
		
	}
	
	//로그인 페이지로 이동
	@RequestMapping(value="/peoples/login", method=RequestMethod.GET)
	//단순 페이지이동. ==> 리턴 없이 생성해도 된다.
	//뷰 이름이 peoples/register가 된다.
	public void login(Model model) {
	}
	//로그인 처리
	@RequestMapping(value="/peoples/login", method=RequestMethod.POST)
	public String login(HttpServletRequest request, HttpSession session, RedirectAttributes attr) {
		System.out.println("로그인 컨트롤러:");
		//로그인이 완료되면 메인페이지로 리다이렉트
		//로그인에 실패하면 로그인 페이지로 리다이렉트
		Peoples peoples = peoplesService.login(request);
		if(peoples != null) {
			//로그인에 성공한 경우에는 pid와 pnick과 pimage를 가지고 가야한다.
			//pid는 session에 저장해두고 로그아웃될 때 세션 초기화 시킬 것이다.
			//pnick 과 pimage는 메인 화면에 띄울 것이다.
			session.setAttribute("peoples", peoples);
			return "redirect:../";
		}else {
			//로그인에 실패하면 attribute의 peoples key 값을 null로 저장
			//메인에서 로그인 여부를 구별하기 위함.
			//로그인에 실패한 경우 메시지 띄우기--한번만 띄울 것이므로 1회성 attribute로 저장
			attr.addFlashAttribute("msg", "없는 아이디이거나 틀린 비밀번호를 입력하셨습니다.");
			session.setAttribute("peoples", null);
			return "redirect:login";
		}
	}
	//로그아웃 처리
	@RequestMapping(value="/peoples/logout",method=RequestMethod.GET)
	public String logout(HttpSession session) {
		//세션 무력화
	session.invalidate();
	return "redirect:../";
	}
	
}
