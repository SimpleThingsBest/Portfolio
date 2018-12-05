package com.gmail.aisdugo.service;

import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.gmail.aisdugo.vo.Mfds;

public interface ParsingDataService {
	
	//제일 큰 날짜 찾아오기
	public String maxpubdate();
	//데이터 삽입하기
	public void dataput(Integer i , String xml);
	//전체데이터 가져오기
	public List<Mfds> alldata();
	//부분데이터 가져오기
	public List<Mfds> partdata(String pubdate);

}
