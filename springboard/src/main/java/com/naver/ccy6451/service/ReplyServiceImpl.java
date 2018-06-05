package com.naver.ccy6451.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.naver.ccy6451.dao.ReplyDao;
import com.naver.ccy6451.domain.Reply;

@Service
public class ReplyServiceImpl implements ReplyService {

	@Autowired
	private ReplyDao replyDao;
	// 파라미터의 이름을 테이블의 컬럼이름과 동일

	@Override
	public boolean register(HttpServletRequest request) {
		// 바로 리턴하는게 아니라 리턴 타입이 있을 때는 아래와 같이 해주는게 좋습니다.
		boolean result = false;

		// 1. 파라미터 읽기 파라미터는 무조건 String을 받아주세요
		String bno = request.getParameter("bno");
		String email = request.getParameter("email");
		String nickname = request.getParameter("nickname");
		String replytext = request.getParameter("replytext");

		// 2.Dao의 파라미터를 만들기
		Reply reply = new Reply();
		reply.setBno(Integer.parseInt(bno));
		reply.setEmail(email);
		reply.setNickname(nickname);
		reply.setReplytext(replytext);

		// 3.Dao의 메소드 호출
		int r = replyDao.register(reply);

		// 4. Dao 의 호출 결과를 가지고 리턴할 결과 만들기
		if (r > 0) {
			result = true;
		}
		return result;
	}

	@Override
	public List<Reply> list(HttpServletRequest request) {
		List<Reply> list = new ArrayList<Reply>();

		String bno = request.getParameter("bno");

		list = replyDao.list(Integer.parseInt(bno));
		return list;

	}

	@Override
	public boolean delete(HttpServletRequest request) {
		boolean result = false;

		String rno = request.getParameter("rno");

		int r = replyDao.delete(Integer.parseInt(rno));

		if (r > 0) {
			result = true;
		}

		return result;
	}

	@Override
	public boolean update(HttpServletRequest request) {
		boolean result = false;

		String rno = request.getParameter("rno");
		String replytext = request.getParameter("replytext");

		// Dao 매개변수 만들기
		Reply reply = new Reply();
		reply.setRno(Integer.parseInt(rno));
		reply.setReplytext(replytext);

		// Dao 메소드 호출
		int r = replyDao.update(reply);

		if (r > 0) {
			result = true;
		}
		return result;

	}
}
