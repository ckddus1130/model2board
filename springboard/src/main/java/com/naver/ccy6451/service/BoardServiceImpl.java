package com.naver.ccy6451.service;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.naver.ccy6451.dao.BoardDao;
import com.naver.ccy6451.domain.Board;
import com.naver.ccy6451.domain.PageMaker;
import com.naver.ccy6451.domain.SearchCriteria;
import com.naver.ccy6451.domain.User;

@Service
public class BoardServiceImpl implements BoardService {

	@Autowired
	private BoardDao boardDao;

	@Override
	public void boardService(HttpServletRequest request) {

	}

	@Override
	public void register(HttpServletRequest request) {
		// 파라미터 읽기
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		// 파라미터를 이용해서 수행할 작업이 있으면 수행
		String ip = request.getRemoteAddr();

		// 로그인한 유저의 email 과 nickname은 session의
		// user 속성에 있습니다.
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");

		String email = user.getEmail();
		String nickname = user.getNickname();

		// Dao 메소드를 호출해야 하는 경우 Dao 메소드의 파라미터를 생성
		Board board = new Board();
		board.setEmail(email);
		board.setIp(ip);
		board.setContent(content);
		board.setTitle(title);
		board.setNickname(nickname);

		System.out.println("파라미터:" + board);
		// Dao 메소드를 호출
		boardDao.register(board);
		// 리턴할 결과를 만들어서 리턴

	}

	@Override
	public Map<String, Object> list(SearchCriteria criteria) {
		// 결과를 저장할 Map을 생성
		Map<String, Object> map = new HashMap<String, Object>();

		// list는 매개변수가 필요하니까 criteria 매개변수 추가
		List<Board> list = boardDao.list(criteria);
		// 마지막 페이지에 있는 데이터가 1개 밖에 없을 때
		// 그 데이터를 삭제하면 그 페이지의 데이터는 없습니다.
		//1 페이지에 1~10의데이터 2페이지에 11~12의 데이터가 있는데 11~12의 데이터를
		//없앴는데 2페이지의 화면을 보여줄 수는 없기때문에 if조건문을 걸어준것
		if (list.size() == 0) {
			// 현재 페이지 번호를 1감소시켜서 데이터를 다시 가져오기
			criteria.setPage(criteria.getPage() - 1);
			list = boardDao.list(criteria);
		}

		// 오늘날짜에 작성된 게시글은 시간을
		// 이전에 작성된 게시글은 날짜를 출력하기위해서 작업을 수행
		// 오늘날짜 만들기
		Calendar cal = Calendar.getInstance();
		java.sql.Date today = new java.sql.Date(cal.getTimeInMillis());
		// list의 데이터들을 확인해서 날짜와 시간을 저장
		for (Board board : list) {
			// 작성한 날짜 가져오기 // substring 해줄떄 10은 뒤까지가아니라 앞까지다
			String regdate = board.getRegdate().substring(0, 10);
			if (today.toString().equals(regdate)) {
				// 시간을 저장 - 분까지 저장
				board.setDispDate(board.getRegdate().substring(11, 16));
			} else {
				// 날짜를 저장
				board.setDispDate(regdate);
			}
			
					//댓글 개수 가져오기
					int replycnt = boardDao.replycnt(board.getBno());
					//댓글 개수 저장
					board.setReplycnt(replycnt);
		}

		// 게시물 목록을 Map에 저장
		map.put("list", list);

		// 페이지 번호 목록 만들기
		PageMaker pageMaker = new PageMaker();
		// 현재 페이지와 페이지 당 출력 개수는 저장
		pageMaker.setCriteria(criteria);
		// 전체 데이터 개수 저장
		pageMaker.settotalCount(boardDao.totalCount(criteria));
		// 페이지 번호 목록 Map에 저장
		map.put("pageMaker", pageMaker);
		
		return map;
	}

	@Override
	public Board detail(HttpServletRequest request) {
		// 파라미터 읽기
		String bno = request.getParameter("bno");
		// 조회수 1증가시키는 메소드 호출
		boardDao.updatecnt(Integer.parseInt(bno));
		Board board = boardDao.detail(Integer.parseInt(bno));
		
		//댓글 개수를 가져와서 설정
		board.setReplycnt(boardDao.replycnt(Integer.parseInt(bno)));
	return board;

	}

	// 수정보기 같은경우에는 수정하는 부분만 바뀌고 나머지는
	// 그대로 사용할 수 있기 때문에 위의 detail 메소드를 복사해서
	// 일부분만 지우고 사용해주었다.
	@Override
	public Board updateView(HttpServletRequest request) {
		// 파라미터 읽기
		String bno = request.getParameter("bno");
		// 데이터를 가져오는 메소드를 호출해서 리턴
		return boardDao.detail(Integer.parseInt(bno));

	}

	@Override
	public void update(HttpServletRequest request) {
		System.out.println("서비스요청 잘갑니까?");
		// 파라미터 읽기
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		String bno = request.getParameter("bno");
		// 파라미터를 이용해서 수행할 작업이 있으면 수행
		String ip = request.getRemoteAddr();

		// Dao 메소드를 호출해야 하는 경우 Dao 메소드의 파라미터를 생성
		Board board = new Board();
		board.setIp(ip);
		board.setContent(content);
		board.setTitle(title);
		board.setBno(Integer.parseInt(bno));
		// 파라미터 잘 갔는지 확인하는 작업
		System.out.println(board);
		// Dao 메소드를 호출
		boardDao.update(board);
		// 리턴할 결과를 만들어서 리턴

	}

	@Override
	public void delete(HttpServletRequest request) {
		// 파라미터 읽기
		String bno = request.getParameter("bno");
		// Dao 메소드를 호출
		boardDao.delete(Integer.parseInt(bno));

	}
}
