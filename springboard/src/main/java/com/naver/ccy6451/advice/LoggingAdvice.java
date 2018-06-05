package com.naver.ccy6451.advice;

import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Calendar;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

//객체를 자동으로 생성하기 위한 어노테이션
@Component
// Advice 클래스로 만들기 위한 어노테이션
@Aspect
public class LoggingAdvice {

	// advice로 수행될 메소드
	// 중간에 가로챈다는 의미로 invoke라고 하고 invoke로 많이 사용
	// pointcut 작성 @Around가 pointcut 접근지정자() 리턴타입(*) 패키지 (..)은 매개변수개수
	//접근지정자는 public 다음 *은 모든 리턴 타입
	//com.naver.ccy6451 . . 은 com.naver.ccy6451 패키지 안에 있는 모든
	//*Dao는 Dao로 끝나는 클래스   .*은 메소드 이름이 무엇이든지
	//(..)은 매개변수 개수에 상관없이
	@Around("execution(public * com.naver.ccy6451..*Dao.*(..))")
	public Object invoke(ProceedingJoinPoint joinPoint) throws Throwable {
		// pointcut으로 설정된 메소드가 호출되기 전에 수행할 내용
		
		//메소드 이름 가져오기  toLongString()은 패키지이름까지 나옵니다.
		String methodName = joinPoint.getSignature().toLongString();
		//현재 시간 만들기
		Calendar cal  = Calendar.getInstance();
		java.util.Date date = new java.util.Date(cal.getTimeInMillis());
		
		//파일에 문자열 기록하기 - 파일이 존재하면 이어쓰기
		FileOutputStream fos = new FileOutputStream("d:\\log.txt",true);
		Object obj = joinPoint.proceed();
		//문자열을 기록할 수 있게 만들 클래스의 객체 만들기
		PrintWriter pw=new PrintWriter(fos);
		//파일에 내용 기록 
		pw.println(methodName + " "+ date.toString() + "\n");
		pw.flush();
		
		pw.close();

		// pointcut으로 설정된 메소드가 호출 된 후에 수행할 내용
		// pointcut이 뭔지??????????
		return obj;
	}
}
