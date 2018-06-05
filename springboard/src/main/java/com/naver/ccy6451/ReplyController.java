package com.naver.ccy6451;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.naver.ccy6451.domain.Reply;
import com.naver.ccy6451.service.ReplyService;

//결과를 html 화면으로 만드는 것이 아니고 
//text 나 json으로 만들어주는 Controller를 만들어주는
//어노테이션
@RestController
public class ReplyController {
	@Autowired
	private ReplyService replyService;

	// 댓글 저장을 위한 메소드
	@RequestMapping(value = "reply/register", method = RequestMethod.GET)
	// 하나 짜리일 때는 void로는 json을 못만들어서 
	//json으로 만들어줄 때는 map을 이용해주세요
	public Map<String, Object> register(HttpServletRequest request) {

		boolean result = replyService.register(request);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", result);
		return map;
	}
	
	//댓글목록을 가져오는 메소드
	@RequestMapping(value="reply/list", method=RequestMethod.GET)
	public List<Reply>list(HttpServletRequest request){
		return replyService.list(request);
	}
	
	//댓글 삭제를 처리하는 메소드
	@RequestMapping(value = "reply/delete", method = RequestMethod.GET)
	public Map<String, Object> delete(HttpServletRequest request) {

		boolean result = replyService.delete(request);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", result);
		return map;
	}
	
	//댓글 수정을 처리하는 메소드
	//insert, delete, update는 하나만 잘 만들어놓으면 구조가 똑같아서 복붙해서 바꿔
	//주기만 하면 됩니다.
	@RequestMapping(value = "reply/update", method = RequestMethod.GET)
	public Map<String, Object> update(HttpServletRequest request) {

		boolean result = replyService.update(request);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", result);
		return map;
	}
}
