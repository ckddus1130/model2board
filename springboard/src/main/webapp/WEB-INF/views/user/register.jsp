<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../include/header.jsp"%>

<section class="content">
	<!-- 회원가입 -->
	<!-- method 와 enctype은 파일을 업로드 하기 위해서 설정한것
	파일이 업로드되는 폼은 반드시 메소드는 post로 
	enctype은 multipart/form-data 로 설정되어야합니다.
	onsubmit에 함수를 연결한 것은 폼의 데이터를 전송할 때
	유효성 검사를 하기 위해서 입니다.                                                                                      -->
	<form id="registerform" enctype="multipart/form-data" method="post"
		onsubmit="return check()">
		<p align="center">
		<table border="1" width="50%" height="80%" align='center'>
			<tr>
				<td colspan="3" align="center"><h2>회원 가입</h2></td>
			</tr>
			<tr>
				<td rowspan="5" align="center">
					<p></p> <img id="img" width="100" height="100" border="1" /> <br />
					<br /> <input type='file' id="image" name="image" /><br />
				</td>
			</tr>

			<tr>
				<td bgcolor="#f5f5f5"><font size="2">&nbsp;&nbsp;&nbsp;&nbsp;이메일</font></td>
				<td>&nbsp;&nbsp;&nbsp; <!-- onblur는 포커스가 떠날 때 confirmId()를 호출해서 email 중복 검사를 수행합니다. -->
					<input type="email" name="email" id="email" size="30" maxlength=50
					onblur="confirmId()" required="required" /> <!-- 메시지 출력 영역 -->
					<div id="emailDiv"></div>
				</td>
			</tr>
			<tr>
				<td bgcolor="#f5f5f5"><font size="2">&nbsp;&nbsp;&nbsp;&nbsp;비밀번호</font></td>
				<td>&nbsp;&nbsp;&nbsp; <input type="password" name="pw" id="pw"
					size="20" required="required" />
				</td>
			</tr>
			<tr>
				<td bgcolor="#f5f5f5"><font size="2">&nbsp;&nbsp;&nbsp;&nbsp;비밀번호
						확인</font></td>
				<td>&nbsp;&nbsp;&nbsp; <input type="password" id="pwconfirm"
					size="20" required="required" />
				</td>
			</tr>
			<tr>
				<td width="17%" bgcolor="#f5f5f5"><font size="2">&nbsp;&nbsp;&nbsp;&nbsp;이름</font></td>
				<td>&nbsp;&nbsp;&nbsp; <!-- html5의 pattern 속성을 이용해서 정규식 검사 수행 -->
					<input type="text" name="nickname" size="20"
					pattern="([a-z, A-Z, 가-힣]){2,}" required="required"
					title="닉네임은 문자 2자 이상입니다." />
				</td>
			</tr>
			<tr>
				<td align="center" colspan="3">
					<p></p> <input type="submit" value="회원가입" class="btn btn-warning" />
					<input type="button" value="메인으로" class="btn btn-success"
					onclick="javascript:window.location='../'">
					<p></p>
				</td>
			</tr>
		</table>
	</form>
	<br /> <br />
</section>
<%@include file="../include/footer.jsp"%>

<script>
	//이메일 중복 검사 통과 여부를 저장할 변수
	//전송 버튼을 눌렀을 때 이 값이  false이면 전송을 하지 않기 위해서
	var emailcheck = false;

	// email 중복체크를 위한 함수
	function confirmId() {
		//email에 입력한 값 확인하기
		val = document.getElementById("email").value
		//메시지 출력 영역 가져오기
		emaildiv = document.getElementById("emailDiv");

		$.ajax({
			url : 'emailcheck',
			data : {
				'email' : val
			},
			dataType : 'json',
			success : function(data) {
				if (data.result == true) {
					emaildiv.innerHTML = "사용가능한 이메일 입니다.";
					emaildiv.style.color = 'blue';
					emailcheck = true;
				}
				//이메일이 중복된 경우
				else {
					emaildiv.innerHTML = "사용 불가능한 이메일입니다.";
					emaildiv.style.color = 'red';
					emailcheck = false;
				}
			}
		});

	}

	var img = document.getElementById("img");
	var image = document.getElementById("image");

	//선택한 파일이름을 저장할 변수
	var filename = "";

	//image 에서 선택이 변경되었을 때 호출되는 함수만들기 
	image.addEventListener('change', function() {
		readURL(this);
	});

	//함수를 불러서 이렇게 받아줍니다.
	function readURL(input) {
		//선택한 파일명 가져오기
		filename = input.files[0].name;
		//그림 파일인지 확인
		var ext = filename.substr(filename.length - 3, filename.length);
		//마지막 3글자가 gif도 아니고 jpg도 아니고 png도 아니고 
		if (ext.toLowerCase() != 'gif' && ext.toLowerCase() != 'jpg'
				&& ext.toLowerCase() != 'png') {
			alert("그림 파일을 선택하세요");
			return;
		}
		//그림 파일의 내용 읽기
		var reader = new FileReader();
		reader.readAsDataURL(input.files[0]);
		//그림 파일의 내용을 전부 읽으면 img에 출력
		reader.onload = function(e) {
			img.src = e.target.result;
		}

	};
	
	// form 에서 submit 했을 때 호출되는 함수
	// false를 리턴하면 서버로 전송하지 않습니다.
	
	function check(){
		//emailcheck 의 값이 false 이면 서버로 전송하지 않도록
		if(emailcheck==false){
			document.getElementById("emailDiv").innerHTML =
				"이메일 중복 검사를 통과하지 못했습니다.";
			document.getElementById("emailDiv").style.color=
				'red';
			document.getElementById("email").focus();
			
			return false;
		}
		//비밀번호에 입력한 값과 비밀번호 확인에 입력한 값이
		//일치 하지 않으면 서버로 전송하지 않도록
		var pw = document.getElementById("pw");
		var pwconfirm = document.getElementById("pwconfirm");
		alert(pw.value);
		alert(pwconfirm.value);
		if(pw.value != pwconfirm.value){
			alert("두 개의 비밀번호는 일치해야 합니다.");
			pw.focus();
			return false;
		}
		
		//비밀번호는숫자,영문자 , 특수문자 1개 이상으로 8자 이상
		//만들어 졌는지 검사  정규식이용
		var p1 =/[0-9]/;
		var p2 =/[a-zA-Z]/;
		var p3 =/[~!@#$%^&*()]/;
		if(!p1.test(pw.value) || !p2.test(pw.value) ||
		!p3.test(pw.value) || pw.value.length < 8){
			alert("비밀번호는 8자 이상 숫자 , 영문자 , 특수문자를 포함해야 합니다.");
			pw.focus();
			return false;
		}
	}
</script>
