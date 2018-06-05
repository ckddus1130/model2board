package com.naver.ccy6451;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.naver.ccy6451.domain.Board;
import com.naver.ccy6451.domain.Criteria;
import com.naver.ccy6451.domain.SearchCriteria;
import com.naver.ccy6451.service.BoardService;

@Controller
public class BoardController {
	@Autowired
	private BoardService boardService;
	//깃허브에서 작업을 수행한 것이 STS로 가는지 확인
	// 게시물 작성 페이지로 이동하도록 요청을 처리
	@RequestMapping(value = "board/register", method = RequestMethod.GET)
	public String register() {
		// System.out.println("게시물 작성");
		return "board/register";
	}

	// 게시물 작성 요청을 처리
	@RequestMapping(value = "board/register", method = RequestMethod.POST)
	public String register(HttpServletRequest request, RedirectAttributes attr, Model model) {
		// 찍어보는 작업
		System.out.println("게시글 저장");
		boardService.register(request);
		attr.addFlashAttribute("msg", "게시글 작성에 성공");
		// 데이터베이스에 저장하는 작업을 수행했으므로
		// 리다이렉트로 이동
		return "redirect:list";
	}

	// 게시물 목록보기를 처리
	@RequestMapping(value = "board/list", method = RequestMethod.GET)
	public String list(SearchCriteria criteria, Model model) {
		Map<String, Object>map = boardService.list(criteria);
		model.addAttribute("map", map);
		return "board/list";
	}

	// 게시물 상세보기를 처리
	@RequestMapping(value = "board/detail", method = RequestMethod.GET)
	//현재 페이지 번호와 페이지 당 출력 개수를 criteria를 저장하고
	//request는 다음페이지로 못 넘기지만 criteria로 받으면
	//다음 페이지에 자동으로 전달
	public String detail(Criteria criteria,HttpServletRequest request, Model model) {
		Board board = boardService.detail(request);
		model.addAttribute("vo", board);
		return "board/detail";
	}

	// 게시물 수정보기를 처리
	@RequestMapping(value = "board/update", method = RequestMethod.GET)
	public String update(Criteria criteria,HttpServletRequest request, Model model) {
		Board board = boardService.updateView(request);
		model.addAttribute("vo", board);
		return "board/update";
	}

	// 게시물 수정을 처리해 줄 메소드
	@RequestMapping(value = "board/update", method = RequestMethod.POST)
	public String update(Criteria criteria,HttpServletRequest request, Model model, RedirectAttributes attr) {
		//System.out.println("Controller요청");
		boardService.update(request);
		attr.addFlashAttribute("msg", "수정성공");

		return "redirect:list?page="+ criteria.getPage() + "&perPageNum=" + criteria.getPerPageNum();
	}

	// 게시물 삭제를 처리해 줄 메소드
	@RequestMapping(value = "board/delete", method = RequestMethod.GET)
	public String delete(Criteria criteria, HttpServletRequest request, Model model, RedirectAttributes attr) {
		// 서비스 메소드 호출
		boardService.delete(request);
		// 메시지 저장
		attr.addAttribute("msg", "삭제성공");
		// 일단 일을 했기에 redirect로 적어주고 지우고나서 어디로 가면
		// 좋을 지 생각해보자
		return "redirect:list?page="+ criteria.getPage() + "&perPageNum=" + criteria.getPerPageNum();
	}
}
