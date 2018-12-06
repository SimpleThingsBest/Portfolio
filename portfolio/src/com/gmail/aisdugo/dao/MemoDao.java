package com.gmail.aisdugo.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MemoDao {
	@Autowired
	public SqlSession sqlSession;
	
	//Select, Alert,Create, Drop은 행과 연관이 없어서 0이 리턴될 수 있다.
	//select는 관련이 없어서이고
	//Alter, Create, Drop은 자동 commit이기 때문

	//전체 데이터 개수를 가져오는 메소드
	public int memocount() {
		return sqlSession.selectOne("memo.memocount");
	}
	
	//전체 데이터 목록을 가져오는 메소드
	public List<Map<String, Object>> memolist(){
		return sqlSession.selectList("memo.memolist");	
	}
	
	//num을 가져와서 하나의 데이터를 찾아오는 메소드
	public Map<String, Object> memodetail(int num){
		return sqlSession.selectOne("memo.memodetail", num);
	}
	
	//map으로 파라미터를 저장해서 데이터를 추가하는 메소드
	public int memoinsert(Map<String, Object> map) {
		System.out.println("Dao!! 메모추가 : "+map.toString());
		return sqlSession.insert("memo.memoinsert", map);
	}
	
	//num을 파라미터로 받아서 데이터를 삭제하는 메소드
	public int memodelete(int num) {
		return sqlSession.delete("memo.memodelete", num);
	}
	}
