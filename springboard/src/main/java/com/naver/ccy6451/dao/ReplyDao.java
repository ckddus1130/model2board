package com.naver.ccy6451.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.naver.ccy6451.domain.Reply;

@Repository
public class ReplyDao {

	@Autowired
	private SqlSession sqlSession;

	// 댓글 저장을 위한 메소드
	public int register(Reply reply) {
		return sqlSession.insert("reply.register", reply);
	}

	// 댓글 개수를 가져오는 메소드
	public int replycnt(int bno) {
		return sqlSession.selectOne("board.replycnt", bno);
	}

	// 게시글에 해당하는 댓글목록을 가져오는 메소드
	public List<Reply> list(int bno) {
		return sqlSession.selectList("reply.list", bno);
	}
	
	// 댓글을 삭제하는 메소드
	public int delete(int rno) {
		return sqlSession.delete("reply.delete",rno);
	}
	
	//댓글을 수정하는 메소드
	public int update(Reply reply) {
		return sqlSession.update("reply.update", reply);
	}
}
