package com.naver.ccy6451.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.naver.ccy6451.domain.Board;
import com.naver.ccy6451.domain.SearchCriteria;

public interface BoardService {

	public void boardService(HttpServletRequest request);

	// 매개변수를 HttpServletRequest를 이용해서 만들면 가장 수월하게 메소드 모양을
	// 만들 수 있습니다.
	public void register(HttpServletRequest request);

	//게시물 전체를 가져오는 메소드
	//왜 변경? 매개변수가 있어야 하고
	//게시물 목록과 현재 페이지 번호를 같이 넘겨주어야 합니다.
	//게시물 상세보기를 하거나 수정 및 삭제를 했을 때 현재 페이지 번호를
	//같이 넘겨서 목록보기를 하거나 작업이 완료되면
	//현재 페이지로 이동하는 게 UI가 좋기 때문입니다.
	//출력할 페이지 번호 관련 데이터도 같이 넘겨주어야 합니다.
	//게시물 목록, 현재페이지, 출력할페이지,번호관련데이터들을
	//다 묶어줄 수 있나여?  없는데.. Map을 사용해라
	public Map<String,Object>list (SearchCriteria criteria);
	//게시물 상세보기를 위한 메소드
	public Board detail(HttpServletRequest request);
	
	//게시물 수정보기를 위한 메소드
	public Board updateView(HttpServletRequest request);
	
	//게시글 수정을 처리해 줄 메소드를 선언
	public void update(HttpServletRequest request);
	
	//게시글 삭제를 처리해 줄 메소드를 선언
	public void delete(HttpServletRequest request);
}
