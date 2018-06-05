package com.naver.ccy6451.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.naver.ccy6451.dao.UserDao;
import com.naver.ccy6451.domain.User;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;

	@Override
	public String emailCheck(HttpServletRequest request) {
		// 파라미터 읽기
		String email = request.getParameter("email");
		// Dao의 메소드를 호출해서 결과를 전송

		return userDao.emailcheck(email);
	}

	@Override
	public void register(MultipartHttpServletRequest request) {
		// 파라미터 읽기
		String email = request.getParameter("email");
		String nickname = request.getParameter("nickname");
		String pw = request.getParameter("pw");
		// 파일은 읽는 방법이 다름
		MultipartFile image = request.getFile("image");
		// 파일을 저장할 경로 만들기
		// 파일은 절대경로로만 저장이 가능
		// 프로젝트 내의 userimage 디렉토리의 절대 경로를 만들기
		String uploadPath = request.getRealPath("/userimage");
		// 랜덤한 64자리의 문자열 만들기
		UUID uid = UUID.randomUUID();
		// 원본 파일이름 가져오기
		String filename = image.getOriginalFilename();
		filename = uid + "_" + filename;
		// 업로드할 파일의 실제 경로 만들기
		String filepath = uploadPath + "\\" + filename;

		// Dao의 파라미터로 사용할 객체
		User user = new User();
		// 업로드할 파일 객체 만들기
		File f = new File(filepath);
		try {
			// 파일 전송 - 파일 업로드
			image.transferTo(f);
			// Dao의 파라미터 만들기
			user.setEmail(email);
			user.setPw(BCrypt.hashpw(pw, BCrypt.gensalt(10)));
			user.setNickname(nickname);
			// 데이터베이스에는 파일 이름만 저장
			user.setImage(filename);
			userDao.register(user);
		} catch (Exception e) {
			System.out.println("회원가입 실패:" + e.getMessage());
		}

	}

	@Override
	public User login(HttpServletRequest request) {
		// 파라미터 읽기
		String email = request.getParameter("email");
		String pw = request.getParameter("pw");

		// Dao 의 메소드를 호출
		User user = userDao.login(email);
		if (user != null) {
			// 비밀번호가 일치하면
			if (BCrypt.checkpw(pw, user.getPw()) == true) {
				// 비밀번호만 초기화
				user.setPw("");
			} else {
				user = null;
			}
		}
		return user;
	}

	@Override
	public String address(String loc) {
		String[] ar = loc.split("-");
		String addr = "https://dapi.kakao.com/v2/local/geo/coord2address.json?";
		addr = addr + "x=" + ar[1] + "&y=" + ar[0] + "&input_coord=WGS84";

		try {
			// 위의 주소를 가지고 URL 객체를 생성
			URL url = new URL(addr);
			// URL객체를 가지고 HttpURLConnection
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			// 인증받기
			con.setRequestProperty("Authorization", "KakaoAK 6b75df538ee7d31484fbb583a5baa0c0");
			// 옵션을 설정
			con.setConnectTimeout(20000);
			con.setUseCaches(false);
			// 줄 단위 데이터 읽기
			BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			// 문자열을 임시로 저장할 객체 만들기
			StringBuilder sb = new StringBuilder();
			while (true) {
				// 한 줄의 데이터 읽기
				String line = br.readLine();
				// 읽은 데이터가 없으면 반복문 종료
				if (line == null) {
					break;
				}
				// 읽은 데이터가 있으면 sb에 추가
				sb.append(line);
			}
			br.close();
			con.disconnect();
			// 데이터 출력
			System.out.println(sb);
			// JSONObject를 생성
			JSONObject obj = new JSONObject(sb.toString());
			// System.out.println(obj);

			JSONArray imsi = obj.getJSONArray("documents");
			// System.out.println(imsi);
			JSONObject o = imsi.getJSONObject(0);
			JSONObject k = o.getJSONObject("address");

			String address = k.getString("address_name");
			// System.out.println(address);
			return address;

		} catch (Exception e) {
			System.out.println("주소가져오기실패: " + e.getMessage());
		}
		return null;

	}

	// 메일을 보내주는 객체를 주입
	@Autowired
	private MailSender mailSender;

	@Override
	public void sendmail(HttpServletRequest request) {
		//sendmail.jsp에서 전송되어온 파라미터 읽기
		String receiver = request.getParameter("receiver");
		String title = request.getParameter("title");
		String contents = request.getParameter("contents");
		
		System.out.println("receiver:" + receiver);
		System.out.println("title:" + title);
		System.out.println("contents:" + contents);
		//receiver:ckddus1130@naver.com
		//title:ckddus1130@naver.com 님이 보낸 메일
		//content:null
		//이렇게 null이 나온경우는 이름을 잘못 입력한거
		try {
			//메일 보내기 객체 만들기
			SimpleMailMessage message = new SimpleMailMessage();
			message.setFrom("ggangpae3@naver.com");
			//받는사람 설정
			message.setTo(receiver);
			//메일 제목 설정
			message.setSubject(title);
			//메일 내용 설정
			message.setText(contents);
			mailSender.send(message);
		}catch(Exception e) {
			System.out.println("메일보내기 에러:"+e.getMessage());
		}
	}
}
