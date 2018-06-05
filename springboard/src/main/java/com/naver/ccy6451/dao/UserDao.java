package com.naver.ccy6451.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.naver.ccy6451.domain.User;

//Dao 만드려면 Repository~
@Repository
public class UserDao {

	// XML Mapper를 이용하는 MyBatis 클래스의 객체를 주입
	@Autowired
	private SqlSession sqlSession;

	// email 중복 체크를 위한 메소드 생성
	// email이 와야하니까 매개변수에 email
	public String emailcheck(String email) {
		return sqlSession.selectOne("user.emailcheck", email);

	}

	// 회원가입을 위한 메소드
	public void register(User user) {
		sqlSession.insert("user.register", user);
	}
	
	//로그인 처리를 위한 메소드
	public User login(String email) {
		return sqlSession.selectOne("user.login",email);
	}
	
}
