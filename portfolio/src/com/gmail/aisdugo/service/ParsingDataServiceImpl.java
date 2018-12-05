package com.gmail.aisdugo.service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.gmail.aisdugo.CutDate;
import com.gmail.aisdugo.dao.MfdsDao;
import com.gmail.aisdugo.vo.Mfds;
@Service
public class ParsingDataServiceImpl implements ParsingDataService {

	@Autowired
	private MfdsDao mfdsDao;
	
	//제일 큰 날짜 찾아오기
	@Override
	public String maxpubdate() {
		System.out.println("ParsingService:MaxPubDate");
		String result = mfdsDao.maxpubdate();
		System.out.println(result);
		return result;
	}
	//다운로드 받을 데이터가 있으면 파싱해서 데이터베이스에 저장
	@Override
	public void dataput(Integer i, String xml) {
		System.out.println("ParsingService:ParsingAndPut:"+i);
		Thread th = new Thread() {
			//System.out.println("스레드 안");
			//데이터를 저장할 Vo 클래스 인스턴스
			Mfds mfds = new Mfds();
			public void run() {
				//전체데이터를 파싱해야 하는 경우와 일부데이터만 파싱해서 데이터베이스에 넣어야 하는지를 구별.
				//i가 1이면 파싱을 하지 않음
				//그 이외에는 i값까지 파싱을 함
				if(i == 0) {
					//파싱 안 함.
				}else {
					try {
						System.out.println("try시작:"+i);
						
					//xml을 파싱
					//xml을 파싱할 수 있는 객체 생성
					DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
					DocumentBuilder builder = factory.newDocumentBuilder();
					System.out.println("factory와 builder : "+factory+"\n"+builder);
					//파싱 할 문자열을 스트림으로 변환
					InputStream is = new ByteArrayInputStream(xml.getBytes());
					//문자열 파싱을 수행 - org.w3c.dom
					Document doc = builder.parse(is);
					System.out.println("is : "+is.toString());
					//루트를 찾기 - org.w3c.dom
					Element root = doc.getDocumentElement();
					System.out.println("root : "+root);
					NodeList titles = root.getElementsByTagName("title");
					System.out.println("titles : "+titles);
					NodeList links = root.getElementsByTagName("link");
					System.out.println("links : "+links);
					NodeList contents = root.getElementsByTagName("content:encoded");
					System.out.println("contents : "+contents);
					NodeList pubDates = root.getElementsByTagName("pubDate");
					System.out.println("pubDates : "+pubDates);
					int index = 0;
					CutDate cutDate = new CutDate();
					while(index<=i){
						Map<String, Object> map;
						//첫벗째의 의미 없는 태그를 버리고 태그 순서 맞춰주기
					if(index == 0) {
						Node title = titles.item(index);
						Node link = titles.item(index);
					}else {
						map = new HashMap<>();
						//두번째 태그부터 저장
						Node title = titles.item(index);
						Node link = links.item(index);
						Node content = contents.item(index-1);
						Node pubDate = pubDates.item(index-1);
						Node titleFirstChild = title.getFirstChild();
						Node linkFirstChild = link.getFirstChild();
						Node contentFirstChild = content.getFirstChild();
						Node pubDateFirstChild = pubDate.getFirstChild();
						String titleData = titleFirstChild.getNodeValue();
						String linkData = linkFirstChild.getNodeValue();
						String contentData = contentFirstChild.getNodeValue();
						String pubDateData = pubDateFirstChild.getNodeValue();
						pubDateData = cutDate.cutAndStick(pubDateData);
						mfds.setTitle(titleData);
						mfds.setLink(linkData);
						mfds.setContent(contentData);
						mfds.setPubdate(pubDateData);
						mfdsDao.dataput(mfds);
					}
					index = index + 1;
					}
					}catch(Exception e) {
						System.out.println(e.getMessage());
						e.printStackTrace();
					}
				}
			}
		};
		System.out.println("스레드 종료");
		th.start();
		System.out.println("run 시작");

	
	}
	//전체데이터 가져오기
	@Override
	public List<Mfds> alldata() {
		return mfdsDao.alldata();
	}
	@Override
	public List<Mfds> partdata(String pubdate) {
		// TODO Auto-generated method stub
		return mfdsDao.partdata(pubdate);
	}
}
