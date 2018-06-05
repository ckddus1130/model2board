package com.naver.ccy6451;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.naver.ccy6451.service.UserService;

@RestController
public class JSONController {
	@Autowired
	private UserService userService;

	// 이메일 중복 체크 요청을 처리
	@RequestMapping(value = "user/emailcheck", method = RequestMethod.GET)
	public Map<String, Object> emailCheck(HttpServletRequest request) {
		// 존재하는 이메일이면 email에 그 이메일이 저장되고
		// 존재하지 않는 이메일이라면 null이 저장됩니다.
		String email = userService.emailCheck(request);
		// 리턴할 Map을 생성
		Map<String, Object> map = new HashMap<String, Object>();
		// result라는 키에서 email이 null인지 여부 저장
		// 존재하는 이메일이면 false, 존재하지 않는 이메일이면 true
		map.put("result", email == null);
		return map;

	}

	// JsonController에서는 map이나 list 배열로 가져와야 합니다.
	@RequestMapping(value = "address", method = RequestMethod.GET)
	public Map<String, Object> address(String loc) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		// 서비스에서 주소를 가져오는 메소드 호출
		String address = userService.address(loc);
		map.put("address", address);
		return map;
	}
	
}
