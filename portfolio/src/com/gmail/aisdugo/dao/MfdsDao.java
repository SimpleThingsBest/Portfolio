package com.gmail.aisdugo.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.gmail.aisdugo.vo.Mfds;


@Repository
public class MfdsDao {
	
	@Autowired
	private SqlSession sqlSession;
	
	//제일 큰 날짜 찾아오기
	public String maxpubdate() {
		System.out.println("DAO:MaxPubDate:"+sqlSession.selectOne("mfds.maxpubdate"));
		String result;
		if(sqlSession.selectOne("mfds.maxpubdate") == null) {
			result = "NULL";
		}else {
			result = sqlSession.selectOne("mfds.maxpubdate");
		}
		return result;
	}
	
	//데이터 삽입하기
	public void dataput(Mfds mfds) {
		System.out.println("DAO:Put");
		sqlSession.insert("mfds.dataput", mfds);
	}
	
	//전체데이터 가져오기
	public List<Mfds> alldata() {
		System.out.println("전체데이터 가져오는 Dao");
		return sqlSession.selectList("mfds.alldata");
	}
	
	//부분데이터 가져오기
	public List<Mfds> partdata(String pubdate){
		System.out.println("부분데이터 가져오는 Dao");
		return sqlSession.selectList("mfds.partdata", pubdate);
	}

}
