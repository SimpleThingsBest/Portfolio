package com.gmail.aisdugo.service;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.gmail.aisdugo.dao.PeoplesDao;
import com.gmail.aisdugo.vo.Peoples;

@Service
public class PeoplesServiceImpl implements PeoplesService {

	@Autowired
	private PeoplesDao peoplesDao;
	
	//아이디,닉네임중복체크 메소드
	@Override
	public Map<String, Object> idcheck(Peoples peoples) {
		Map<String, Object> map = new HashMap();
		System.out.println("||체크 서비스");
		//아이디 받아오기
		String beforSliceId = peoples.getPid();
		String afterSliceId [] = new String[3];
		afterSliceId = beforSliceId.split("\\|\\|");
		/*for(String imsi : afterSliceId) {
			System.out.println(imsi);
			//aisdugo
			//@naver.com
		}*/
		if(afterSliceId.length > 1) {
			System.out.println("|| 존재");
			//||가 들어간 경우
			map.put("oR", "false");
			
		}else {
			System.out.println("|| 없음");
			map.put("oR", "true");
		}
		
		System.out.println("아이디 중복체크 : 서비스");
		List<Peoples> list = peoplesDao.idcheck(peoples);
		System.out.println("dao 통과");
		//System.out.println(list.get(0));
		switch(list.size()){
		case 0 :
			System.out.println("중복검사 통과");
			map.put("result", "true");
			break;
		case 1 :
			//첫번째 사람과 아이디, 닉네임이 모두 중복 된 경우는 생각하지 않음.
			//ajax에서 아이디 중복체크를 성공했어야지 다음으로 넘어가게 할 것이기 때문.
			System.out.println("아이디나 닉네임이 한사람과 겹침:");
			System.out.println(list.get(0));
			System.out.println(peoples.getPid());
			String id1 = list.get(0)+"";
			System.out.println(id1);
			if(id1.equals(peoples.getPid())) {
				//아이디 중복
				System.out.println("아이디중복");
				map.put("result", "idFalse");
			}else {
				//닉네임 중복
				System.out.println("닉네임중복");
				map.put("result", "nickFalse");
			}
			break;
		case 2 :
			System.out.println("아이디나 닉네임이 두사람과 겹침");
			String id2 = list.get(0)+"";
			if(id2.equals(peoples.getPid())) {
				//첫번째 사람과 아이디가 중복 됨.
				System.out.println("아이디중복");
				map.put("result", "idFalse");
			}else {
				//첫번째 사람과 닉네임이 중복 됨.
				System.out.print("닉네임중복");
				map.put("result", "nickFalse");
			}
			break;
		}
		return map;
	}
	
	//모바일 로그인 처리를 위한 메소드
	@Override
	public Peoples mlogin(HttpServletRequest request) {
		//파라미터 읽기
		String pid = request.getParameter("pid");
		String ppw = request.getParameter("ppw");
		System.out.println("모바일에서 받아온 데이터 : 서비스 : id="+pid+"/pw="+ppw);
		//필요한 작업 수행
		//Dao의 파라미터 만들기
		Peoples peoples = new Peoples();
		peoples.setPid(pid);
		
		Peoples peoplesDB = peoplesDao.mlogin(peoples);
		System.out.println("데이터베이스 결과 : 서비스 : "+peoplesDB);


		//비밀번호가 맞는지 확인 -- 암호화 되어 있으므로 BCrypt.checkpw를 이용하여 비밀번호가 맞는지 확인해야 함.
		if(peoplesDB != null) {
			//아이디는 일치
		if(BCrypt.checkpw(ppw, peoplesDB.getPpw())) {
			//비밀번호가 일치할 때 -- 비밀번호만 null로 설정
			System.out.println("비밀번호 맞음");
			peoplesDB.setPpw(null);
			//다운로드 받을 이미지의 파일경로를 생성
			String downloadPath = request.getRealPath("/peoplesimage");
			String downloadFile = downloadPath+"/"+peoplesDB.getPimage();
			try(FileInputStream fis = new FileInputStream(downloadFile);
	 				BufferedInputStream bis = new BufferedInputStream(fis);) {
				byte [] b = new byte[fis.available()];
				bis.read(b);
				peoplesDB.setByteimage(b);;
			}catch(Exception e) {
				e.printStackTrace();
			}
			
		}else {
			//비밀번호가 틀렸을 때 -- peoples를 null로 만듦
			System.out.println("비밀번호 틀림");
			peoplesDB = null;
			peoplesDB = new Peoples();
			peoplesDB.setPid("NULL");
		}}else {
			//아이디가 불일치
			peoplesDB = new Peoples();
			peoplesDB.setPid("NULL");
		}
		
		//Dao 메소드를 호출하고 결과를 Controller한테 보내주기
		//seleteOne은 데이터가 없으면 null을 리턴
		return	peoplesDB;
	}
	
	//회원가입 메소드
	@Override
	public boolean register(MultipartHttpServletRequest request) {
		//파라미터 읽기
		String pid = request.getParameter("email");
		String ppw = request.getParameter("pw");
		String pnick = request.getParameter("nickname");
		//파일은 getFile로 읽고 MultipartFile로 저장
		MultipartFile pimage = request.getFile("image");
		
		//파일 업로드 처리
		//업로드 할 디렉토리를 문자열로 만들기
		String uploadPath = request.getRealPath("/peoplesimage");
		//파일 이름 만들기 -- 중복되지 않게 하기 위해 UUID와 원본파일 이름을 합쳐서 생성
		UUID uuid = UUID.randomUUID();
		String filename = pimage.getOriginalFilename();
		String filepath = uploadPath+"/"+uuid+"_"+filename;

		System.out.println("이미지가 저장되는 파일 경로 : "+uploadPath);
		
		//파일 업로드와 데이터베이스 작업
		Peoples peoples = new Peoples();
		File file = new File(filepath);
		try {
			peoples.setPid(pid);
			//비밀번호 암호화
			peoples.setPpw(BCrypt.hashpw(ppw, BCrypt.gensalt()));
			peoples.setPnick(pnick);
			peoples.setPimage(uuid+"_"+filename);
			//파일업로드
			pimage.transferTo(file);
		}catch(Exception e){
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		boolean registerSuccess = peoplesDao.register(peoples);

		System.out.print(registerSuccess);
		return registerSuccess;
	}
	
	//pc로그인 메소드
	@Override
	public Peoples login(HttpServletRequest request) {
		System.out.println("로그인 서비스:");
		//파라미터 읽기 -- 아이디와 비밀번호
		String pid = request.getParameter("email");
		String ppw = request.getParameter("pw");
		System.out.println(pid+"/"+ppw);
		//파라미터 pid에 해당하는 데이터를 데이터베이스에서 가져오기
		//peoples가 null이면 데이터베이스에 없는 것. -- 이 경우 아래 작업을 하지 않고 리턴됨.
		Peoples peoples = peoplesDao.login(pid);
		if(peoples != null) {
			System.out.println("peoples값이 있는 경우:"+peoples.getPpw());
			//비밀번호가 맞는지 확인 -- 암호화 되어 있으므로 BCrypt.checkpw를 이용하여 비밀번호가 맞는지 확인해야 함.
			if(BCrypt.checkpw(ppw, peoples.getPpw() ) ) {
				System.out.println("비밀번호 검사");
				//비밀번호가 일치할 때 -- 비밀번호만 null로 설정
				System.out.println("비밀번호 맞음");
				peoples.setPpw(null);
			}else {
				//비밀번호가 틀렸을 때 -- peoples를 null로 만듦
				System.out.println("비밀번호 틀림");
				peoples = null;
			}
			
			
			
			
			System.out.println("서비스 작업 끝");
		}
		return peoples;
	}


}
