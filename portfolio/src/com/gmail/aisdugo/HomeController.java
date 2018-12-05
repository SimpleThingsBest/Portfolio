package com.gmail.aisdugo;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.gmail.aisdugo.dao.MemoDao;
import com.gmail.aisdugo.service.ParsingDataService;
import com.gmail.aisdugo.vo.Mfds;

@Controller
public class HomeController {
	//데이터베이스 접속 정보 테스트
	//@Autowired
	//private DataSource dataSource; //java.sql.DataSource - 데이터베이스 연결 테스트
	//private SqlSession sqlSession; - 데이터베이스의 빈이 잘 만들어졌는지 테스트
	//private MemoDao memoDao;
	
	@Autowired
	private ParsingDataService parsingDataService;
	
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(HttpServletRequest request, Model model) {
		System.out.println("HomeController");
		//System.out.println(dataSource);
		//Map<String, Object> memoTestMap = new HashMap<>();
		//memoTestMap.put("title", "안녕하세요");
		//memoTestMap.put("content", "메모의 내용입니다.");
		//memoTestMap.put("regdate", "2018-12-04 17:31:00");
		//memoTestMap.put("imagepath", "image.png");
		//System.out.println(sqlSession.insert("memo.memoinsert", memoTestMap));
		//System.out.println(sqlSession.selectList("memo.memolist"));
		//System.out.println(sqlSession.selectOne("memo.memodetail", 2).toString());
		//System.out.println(sqlSession.delete("memo.memodelete", 2));
		
		//System.out.println(memoDao.memoinsert(memoTestMap));
		
		//웹에서 파싱할 데이터를 가지고 와서 데이터베이스에 데이터가 있는지 확인
		
			//웹 데이터를 다운로드 받기 위한 스레드를 생성하고 재정의
			Thread th = new Thread() {
				public void run() {
					try {
						//다운로드 받을 URL
						URL url = new URL("http://www.mfds.go.kr/www/rss/brd.do?brdId=ntc0021");
						System.out.println("url:"+url.hashCode());
						//System.out.println("url생성:"+url);
						//URL 연결 객체 생성
						HttpURLConnection con = (HttpURLConnection)url.openConnection();
						System.out.println("connection:"+con.hashCode());
						//System.out.println("connect 성공:"+con);
						//캐시설정--다운로드 받아두고 다음에 설정할 것인지를 설정
						con.setUseCaches(true);
						con.setConnectTimeout(30000);
						//문자열을 읽기 위한 스트림 생성
						BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
						System.out.println("stream:"+br.hashCode());
						//System.out.println("스트림생성:"+br);
						//줄단위로 읽기
						StringBuilder sb = new StringBuilder();
						while(true) {
							String line = br.readLine();
							if(line == null) {
								break;
							}
							sb.append(line);
						}
						//읽은 내용을 하나의 문자열로 만들기
						String xml = sb.toString();
						//System.out.println("읽은 내용:"+xml);
						//xml을 파싱
						//xml을 파싱할 수 있는 객체 생성
						DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
						DocumentBuilder builder = factory.newDocumentBuilder();
						System.out.println("파싱성공");
						//파싱 할 문자열을 스트림으로 변환
						InputStream is = new ByteArrayInputStream(xml.getBytes());
						
						//날짜만 파싱을 해서 데이터베이스의 날짜와 비교
						//데이터베이스의 값이 null이면 스트림으로 변환한 문자열을 서비스로 전송
						//max 날짜와 파싱한 날짜가 같으면 인덱스를 추가하지 않고 빠져나오는 반복문을 생성
						//서비스에 스트림으로 변환한 문자열과 인덱스를 전송
						//인덱스까지 파싱하도록 서비스를 만들기
						//문자열 파싱을 수행 - org.w3c.dom
						Document doc = builder.parse(is);
						//루트를 찾기 - org.w3c.dom
						Element root = doc.getDocumentElement();
						NodeList pubDates = root.getElementsByTagName("pubDate");
						//데이터베이스에서 max날짜를 가져오기
						String oldpubDateData = parsingDataService.maxpubdate();
						
						//max날짜가 null이면 데이터베이스에 데이터가 없음
						//스트림으로 변환한 문자열과 pubDatas의 길이를 서비스로 전송해서 스트림 전체 파싱
						CutDate cutDate = new CutDate();
						
						if(oldpubDateData == "NULL") {
							//System.out.println("oldpubDateData가 NULL일 때 : " + oldpubDateData);
							parsingDataService.dataput(pubDates.getLength(), xml);
						}else { //max날짜가 null이 아니면 새로운 날짜와 비교하는 반복문 생성
							int i = 0;
							//System.out.println("oldpubDateData가 NULL이 아닐 때 : "+oldpubDateData);
							while(true) {
								//System.out.println("반복문 들어옴 : "+i);
								//pubDate태그를 찾아서 그 내용을 문자열로 저장
								Node pubDate = pubDates.item(i);
								Node pubDateFirstChild = pubDate.getFirstChild();
								String pubDateData = pubDateFirstChild.getNodeValue();
								//System.out.println("반복문 진행중 : "+pubDateData);
								//데이터베이스의 데이터와 비교할 수 있게 형태를 변환하고 비교
								String newpubDateData = cutDate.cutAndStick(pubDateData);
								//System.out.println("반복문 진행중 : "+oldpubDateData+":"+newpubDateData);
								if(oldpubDateData.equalsIgnoreCase(newpubDateData)) {
									//i값을 플러스 시키지 않고 반복문을 종료.
									//System.out.println("반복문 종료 : "+i);
									break;
								}
							i=i+1;
							}
							//i값까지 파싱하도록 서비스 호출
							parsingDataService.dataput(i, xml);
						}
					}catch(Exception e) {
						System.out.println(e.getMessage());
						e.printStackTrace();
					}
				}
			};
			th.start();
			
		return "home";
	}
	
	@RequestMapping(value="parsingpage/mfdsnews", method=RequestMethod.GET)
	public String mfdsnews(HttpServletRequest request, Model model){
		System.out.println("ParsingController");
		
		//서비스에서 데이터를 받아서 Json형식으로 클라이언트의 로컬스토리지에 저장.
		//부분 적으로 데이터를 가져올 때는 로컬스토리지에서 날짜를 뽑아서 제일 최근의 것과 비교해서 추가.
		
		//전체데이터
		//데이터가져오기
		//데이터를 담을 객체 생성
		List<Mfds> list = parsingDataService.alldata();
		System.out.println(list.toString());
		List<Map<String, Object>> list1 = new ArrayList<>();
		Map<String, Object> map;
		for(Mfds imsi : list) {
			map = new HashMap<>();
			map.put("title",imsi.getTitle());
			map.put("link",imsi.getLink());
			map.put("content",imsi.getContent());
			map.put("pubdate",imsi.getPubdate());
			//System.out.println(map);
			list1.add(map);
		}
		
		System.out.println(list1);
		request.setAttribute("data", list1);
		
		
		return "parsingpage/mfdsnews";
	}
	
	@RequestMapping(value="parsingpage/memo", method=RequestMethod.GET)
	public String memolist(HttpServletRequest request) {
		return "parsingpage/memo";
	}
	
}
