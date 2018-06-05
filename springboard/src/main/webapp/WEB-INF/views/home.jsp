<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!-- header.jsp를 가져다 쓰고싶어! 
    현재 파일 위치에서 include 디렉토리의 header.jsp 파일의
    코드를 가져와서 삽입-->
<%@ include file="include/header.jsp"%>
<section class="content">
	<div class="box">
		<div class="box-header with-border" id="address">
			
		</div>

		<c:if test="${user==null}">
			<div class="box-header with-border">
				<a href="user/login"><h3 class="box-title">로그인</h3></a>
			</div>

			<div class="box-header with-border">
				<a href="user/register"><h3 class="box-title">회원가입</h3></a>
			</div>
		</c:if>

		<c:if test="${user!=null}">
			<div class="box-header with-border">
				<a href="user/logout"><h3 class="box-title">로그아웃</h3></a>
			</div>
						<div class="box-header with-border">
				<a href="board/register"><h3 class="box-title">게시글 작성</h3></a>
			</div>
		</c:if>
		</div>
						<div class="box-header with-border">
				<a href="board/list"><h3 class="box-title">게시글 보기</h3></a>
			</div>
	</div>
</section>
<%@ include file="include/footer.jsp"%>

<!-- 이 조건이 없다면 무조건 뜨기 때문에 조건 설정 필수 -->
<c:if test="${msg != null}">
	<link rel="stylesheet"
		href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
	<link rel="stylesheet" href="/resources/demos/style.css">
	<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
	<script>
		$(function() {
			$("#dialog-message").dialog({
				modal : true,
				buttons : {
					Ok : function() {
						$(this).dialog("close");
					}
				}
			});
		});
	</script>
	</head>
	<body>

		<div id="dialog-message" title="회원가입 성공">
			<p>
				<span class="ui-icon ui-icon-circle-check"
					style="float: left; margin: 0 7px 50px 0;"></span> 회원가입이 되었다리~
			</p>
			<p>
				Currently using <b>36% of your storage space</b>.
			</p>
		</div>
</c:if>

<script>
	//10초마다 한번씩 동작하는 타이머
	//스크립트에서 문제가 생기면 조금씩 잘라내면서 찍어보면서 디버깅하세요
	setInterval(function() {
		//현재 접속된 브라우저의 위도와 경도 출력하기
		navigator.geolocation.getCurrentPosition(function(position){
		
			//전송해 줄 파라미터 만들기
			loc = position.coords.latitude + "-" + position.coords.longitude;
			//alert(loc);
		
		$.ajax({
			url:"address",
			data:{"loc":loc},
			dataType:"json",
			success:function(data){
				document.getElementById("address").innerHTML=
					"<h3>" +data.address + "</h3>";
			}
		});
		});
		
	}, 10000);
</script>














