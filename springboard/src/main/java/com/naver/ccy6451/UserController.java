package com.naver.ccy6451;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.naver.ccy6451.domain.User;
import com.naver.ccy6451.service.UserService;

@Controller
public class UserController {
	// user/register 라는 요청이 오면
	// user/register.jsp 파일로 포워딩
	@RequestMapping(value = "user/register", method = RequestMethod.GET)
	public String register(Model model) {
		return "user/register";
	}

	@Autowired
	private UserService userService;

	// 회원가입 처리를 위한 메소드
	@RequestMapping(value = "user/register", method = RequestMethod.POST)
	// RedirectAttributes는 redirect 로 이동할 때 한번만 전달되는 데이터를
	// 보관하는 Spring의 클래스
	public String register(MultipartHttpServletRequest request, RedirectAttributes attr) {
		userService.register(request);
		attr.addFlashAttribute("msg", "회원가입");
		// 삽입, 삭제, 갱신 다음에는 리다이렉트로 이동
		return "redirect:/";
	}

	@RequestMapping(value = "user/login", method = RequestMethod.GET)
	public String login(Model model) {

		return "user/login";
	}

	@RequestMapping(value = "user/login", method = RequestMethod.POST)
	public String login(HttpServletRequest request, Model model, RedirectAttributes attr, HttpSession session) {
		User user = userService.login(request);
		if (user == null) {
			attr.addFlashAttribute("msg", "없는 이메일이거나 잘못된 비밀번호 입니다.");

			return "redirect:login";
		}
		// 로그인에 성공한 경우
		else {
			session.setAttribute("user", user);
			// 이전 요청을 가져오기
			Object dest = session.getAttribute("dest");
			// 이전 요청이 없으면 시작 페이지로 이동
			if (dest == null) {
				return "redirect:/";
			}
			// 이전 요청이 있으면 그 페이지로 이동
			else {
				return "redirect:/" + dest.toString();

			}
		}
	}

	@RequestMapping(value = "user/logout", method = RequestMethod.GET)
	public String logout(HttpSession session) {
		// 세션을 초기화
		session.invalidate();

		// 메인페이지로 리다이렉트(연속 2번 하면 안되는 경우)
		return "redirect:/";
	}
	//메일보내기 링크를 눌렀을 때 메일 보내기 화면으로 이동하는 메소드
	@RequestMapping(value="user/sendmail",method=RequestMethod.GET)
	public String sendmail(@RequestParam("email")String email, Model model) {
		//model에 email 저장
		model.addAttribute("email",email);
		return "user/sendmail";
	}
	@RequestMapping(value="user/sendmail", method=RequestMethod.POST)
	public String sendmail(HttpServletRequest request, RedirectAttributes attr) {
		userService.sendmail(request);
		attr.addFlashAttribute("msg","메일보내기 성공");
		
		return "redirect:/";
	}

}
