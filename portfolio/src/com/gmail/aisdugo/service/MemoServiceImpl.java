package com.gmail.aisdugo.service;

import java.io.File;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

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

	@Override
	public Map<String, Object> memodelete(HttpServletRequest request) {
		//파라미터 읽어오기
		//혹시 파라미터에 인트자료형에 공백을 무심코 넣거나 하면 오류가 납니다. <a href="num=1 "> 이렇게 1 뒤에 공백이 있으면 숫자로 인식할 수 없습니다
		//그래서 항상 trim이라는 속성을 기억하는 것이 좋습니다.
		int num = Integer.parseInt(request.getParameter("num").trim());
		//데이터베이스의 삽입삭제갱신작업을 수행하면 영향받은 행의 개수가 리턴됨.
		//0이 리턴되면 잘못된 것이 아니고 조건에 맞는 데이터가 없는 것입니다.
		int r = memoDao.memodelete(num);
		Map<String, Object> map = new HashMap<>();
		if(r>=0) {
			map.put("result", "success");
		}else {
			map.put("result", "fail");
		}
		
		return map;
	}

	@Override
	public Map<String, Object> memoinsert(MultipartHttpServletRequest request) {
		System.out.println("메모추가 : 서비스 들어옴");
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		System.out.println("메모추가 : "+title+"\n"+content);
		//파라미터로 전달된 파일 찾아오기
		MultipartFile image = request.getFile("imagepath");
		//파일을 업로드 할 프로젝트 내의 실제 경로 찾아오기
		//서블릿 3.0 이상을 사용하면 request.getRealPath 대신에 request.getSelrvletContext().getRealPath 사용
		String uploadPath = request.getServletContext().getRealPath("/memoimage");
		System.out.println("메모추가 : "+uploadPath);
		//자바에서 랜덤한 64글자를 만들어 줄 수 있는 객체 생성 (UUID)
		UUID uuid = UUID.randomUUID();
		//원본 파일 이름 찾아오기
		String filename = image.getOriginalFilename();
		System.out.println("메모추가 : "+filename);
		//Dao에 데이터베이스 수행 메소드에 전달할 파라미터 생성
		Map<String, Object> map = new HashMap<>();
		try {
			//업로드할 파일이 있다면
			if(filename.length() > 0) {
				//파일이 있음
				//랜덤한 문자열과 파일의 확장자를 연결해서 새로운 파일이름 만들기
				int idx = filename.lastIndexOf(".");
				String saveFileName = uuid + filename.substring(idx);
				System.out.println("메모추가 : "+saveFileName);
				//업로드 할 파일 경로 만들기
				String saveFilePath = uploadPath + "/" + saveFileName;
				System.out.println("메모추가 : "+saveFilePath);
				File f = new File(saveFilePath);
				//파일 업로드
				image.transferTo(f); //--> 파일경로로 전달받은 파일을 보냄

				System.out.println("메모추가 : 파일저장 완료");
				map.put("imagepath", saveFileName);

				System.out.println("메모추가 : ???"+map.get("imagepath"));
			}else {
				//파일이 없음
				map.put("imagepath", " ");
				System.out.println("메모추가 : ???");
			}
			map.put("title", title);
			map.put("content", content);
			System.out.println("메모추가 : "+map.get("title")+"/"+map.get("content"));
			//오늘 날짜 및 시간을 "yyyy-MM-dd h:m:s" 의 형태의 문자열로 만들기
			Calendar cal = Calendar.getInstance();
			String regdate =
					cal.get(Calendar.YEAR)+"-"
					+(cal.get(Calendar.MONTH)+1)+"-"
					+cal.get(Calendar.DAY_OF_MONTH)+" "
					+cal.get(Calendar.HOUR)+":"
					+cal.get(Calendar.MINUTE)+":"
					+cal.get(Calendar.SECOND);
			//util.Date(시간ok) 와 DateFormatter의 조함으로도 가능
			map.put("regdate", regdate);
			System.out.println("메모추가 : "+map.get("regdate"));
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		int r = memoDao.memoinsert(map);
		Map<String, Object> resultMap = new HashMap<>();
		if(r>=0) {
			resultMap.put("result", "success");
			System.out.println(resultMap.values());
		}else {
			resultMap.put("result", "fail");
			System.out.println(resultMap.values());
		}
		return resultMap;
	}
	
	
}
