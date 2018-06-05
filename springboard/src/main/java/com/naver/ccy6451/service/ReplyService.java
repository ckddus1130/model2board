package com.naver.ccy6451.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.naver.ccy6451.domain.Reply;

public interface ReplyService {
	// 댓글 저장을 위한 메소드
	public boolean register(HttpServletRequest request);

	//댓글 목록을 가져오기 위한 메소드
	public List<Reply>list(HttpServletRequest request);
	
	//댓글을 삭제하기 위한 메소드
	public boolean delete(HttpServletRequest request);
	
	//댓글을 수정하기 위한 메소드
	public boolean update(HttpServletRequest request);
}
