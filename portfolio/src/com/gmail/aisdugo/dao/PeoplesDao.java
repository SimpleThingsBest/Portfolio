package com.gmail.aisdugo.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.gmail.aisdugo.vo.Peoples;


@Repository
public class PeoplesDao {
@Autowired
private SqlSession sqlSession;
//아이디 중복체크 메소드
public List<Peoples> idcheck(Peoples peoples) {
	System.out.println("아이디 중복체크 : DAO");
	return sqlSession.selectList("peoples.idcheck", peoples);
}
//모바일 로그인 메소드
public Peoples mlogin(Peoples peoples) {
	System.out.println("모바일에서 받아온 데이터 : DAO : "+peoples);
	Peoples peoplesDB = sqlSession.selectOne("peoples.mlogin",peoples);
	System.out.println("데이터베이스에서 받아온 데이터 : DAO : "+peoplesDB);
	return peoplesDB;
}

//회원가입 메소드
public boolean register(Peoples peoples) {
	boolean registerSuccess = false;
	sqlSession.insert("peoples.register", peoples);
	registerSuccess = true;
	System.out.print(registerSuccess);
	return registerSuccess;
}

//로그인 메소드
public Peoples login(String pid) {
	System.out.println("로그인 DAO:"+sqlSession.selectOne("peoples.login", pid));
	return sqlSession.selectOne("peoples.login", pid);
}


}
