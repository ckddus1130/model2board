<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="../include/header.jsp"%>
<div class="container">
	<form method="post">
		<div class="form-group">
			<label for="receiver">받는 사람 이메일</label> <input type="email"
				name="receiver" value="${email}" readonly="readonly"
				class="form-control" />
		</div>
		<div class="form-group">
			<label for="title">메일 제목</label> <input type="text" name="title"
				value="${user.email} 님이 보낸 메일" readonly="readonly"
				class="form-control" />
		</div>
		<div align="center">
			<label for="contents">메일 제목</label>
			<textarea class="form-control" rows="5" name="contents"></textarea>
		</div>
		<div align="center">
			<input type="submit" value="메일 보내기" class="btn btn-warning">
		</div>
	</form>
</div>
<%@ include file="../include/footer.jsp"%>
