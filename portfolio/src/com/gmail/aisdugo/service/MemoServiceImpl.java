package com.gmail.aisdugo.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gmail.aisdugo.dao.MemoDao;

@Service
public class MemoServiceImpl implements MemoService {
	@Autowired
	private MemoDao memoDao;

	@Override
	public Map<String, Object> memolist(HttpServletRequest request) {
		Map<String, Object> memolistMap = new HashMap<>();
		//전체 데이터 개수를 가져오기
		int memoTotalCount = memoDao.memocount();
		//전체 데이터 목록 가져오기
		List<Map<String, Object>> list = memoDao.memolist();
		
		memolistMap.put("memototalcount", memoTotalCount);
		memolistMap.put("memolist", list);
		memolistMap.put("a", 0);
		
		return memolistMap;
	}

	@Override
	public Map<String, Object> memodetail(HttpServletRequest request) {
		//num이라는 파라미터를 읽어서 정수로 변환
		int num = Integer.parseInt(request.getParameter("num"));
		Map<String, Object> memodetailMap = memoDao.memodetail(num);
		return memodetailMap;
	}
	
	
}
