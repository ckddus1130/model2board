package com.naver.ccy6451.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class AuthenticationInterceptor implements HandlerInterceptor {

	// Controller가 처리하기 전에 호출되는 메소드
	// true를 리턴하면 Controller로 넘어가고
	// false를 리턴하면 Controller로 넘어가지 않습니다.
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// 로그인을 확인하기 위해서 session 가져오기
		HttpSession session = request.getSession();
		// 로그인 정보는 session의 user 속성에 저장되어 있습니다.
		// 로그인이 되어있찌 않으면 Controller로 가지 않습니다.
		if (session.getAttribute("user") == null) {
			// 사용자의 요청을 session에 dest라는 속성에 저장
			// 로그인이 되면 원래의 요청을 처리하기 위해서

			// 클라이언트의 전체 요청주소
			String requestURI = request.getRequestURI();
			// 서버의 주소
			String contextpath = request.getContextPath();
			// 주소 만들기
			String uri = requestURI.substring(contextpath.length() + 1);

			// 주소 뒤에 파라미터를 가져오기
			String query = request.getQueryString();
			
			// 실제 주소 만들기
			// 브라우저의 경우 "null"자체가 올수도 있어서
			if (query == null || query.equals("null")) {
				query = "";
			} else {
				query = "?" + query;
			}
			// 세션에 주소 저장하기
			session.setAttribute("dest", uri + query);
			// 세션에 메세지 저장하기
			session.setAttribute("msg", "로그인을 하셔야 이용할 수 있는 서비스입니다.");
			// 로그인 페이지로 리다이렉트
			response.sendRedirect(contextpath + "/user/login");
			return false;
		}
		// 로그인 되어 있는 경우는 Controller가 처리
		return true;
	}

	// Controller가 사용자의 요청을 정상적으로 처리하고 난 후
	// 호출되는 메소드
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

	}

	// Controller에서 예외 발생 여부에 상관없이 호출되는 메소드
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

	}

}
