package com.gmail.aisdugo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gmail.aisdugo.service.ParsingDataService;
import com.gmail.aisdugo.service.PeoplesService;
import com.gmail.aisdugo.vo.Mfds;
import com.gmail.aisdugo.vo.Peoples;

@RestController
public class JsonController {

	@Autowired
	private PeoplesService peoplesService;
	
	//아이디 체크 데이터 가져오기
	@RequestMapping(value="peoples/idcheck", method=RequestMethod.GET)
	public Map<String, Object> idcheck(HttpServletRequest request, Model model){
		System.out.println("아이디 중복체크:컨트롤러");
		//jsp 페이지에서 데이터 가져오기
		String pid = request.getParameter("pid");
		String pnick = request.getParameter("pnick");
		System.out.println("jsp에서 받아온 데이터 : "+pid+"/"+pnick);
		
		//서비스로 넘길 데이터 만들기
		Peoples peoples = new Peoples();
		peoples.setPid(pid);
		peoples.setPnick(pnick);
		//서비스의 결과 가져오기
		Map<String, Object> map = peoplesService.idcheck(peoples);
		System.out.println("서비스 결과값:"+map.get("result")+"/"+map.get("oR"));
		
		return map;
		
	}
	
	//모바일 로그인 처리 메소드
	@RequestMapping(value="mobile/login", method=RequestMethod.GET)
	public Map<String, Object> mlogin(HttpServletRequest request){
		Map<String, Object> map = new HashMap<>();
		//결과 받아오기
		Peoples peoples = peoplesService.mlogin(request);
		System.out.println("데이터베이스에서 받아온 데이터 : 컨트롤러 : "+peoples);
		//결과를 저장
		map.put("result", peoples);
		
		return map;
	}
	
	
	@Autowired
	private ParsingDataService parsingDataService;
	
	//전체 데이터 조회
	@RequestMapping(value="mobile/mfdsnews", method=RequestMethod.GET)
	public Map<String, Object> mfdsnews(){
		List<Mfds> list = parsingDataService.alldata();
		//데이터를 담을 리스트
		List<Map<String, Object>> printList = new ArrayList<>();
		Map<String, Object> map = new HashMap<>();
		for(Mfds imsi : list) {
			map = new HashMap<>();
			map.put("title", imsi.getTitle());
			map.put("link", imsi.getLink());
			map.put("content", imsi.getContent());
			map.put("pubdate", imsi.getPubdate());
			printList.add(map);
		}
		map = new HashMap<>();
		map.put("data", printList);
		int totalList = printList.size();
		map.put("totalList", totalList);
		
		return map;
	}

}
