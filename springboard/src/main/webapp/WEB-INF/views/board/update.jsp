<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../include/header.jsp"%>
<section class="content">
	<div class="box-header">
		<h3 class="box-title">게시판 수정</h3>
	</div>

	<form role="form" method="post">
	<!-- 데이터 수정을 할 때 기본키의 값이 있어야 해서 필요하고
	작업이 끝나고 결과 페이지로 이동할 때 
	상세보기로 이동하려면 글번호가 필요합니다. -->
	<input type="hidden" name="bno" value="${vo.bno}">
	
	<!-- 현재 페에지 번호와 페이지 당 출력 개수 -->
		<input type="hidden" name="page" value="${criteria.page}"/>
		<input type="hidden" name="page" value="${criteria.perPageNum}"/>
	
		<div class="box-body">
			<div class="form-group">
				<label>제목</label> <input type="text" name='title'
					class="form-control" value="${vo.title}">
			</div>
			<div class="form-group">
				<label>내용</label>
				<textarea class="form-control" name="content" rows="5"
					>${vo.content}</textarea>
			</div>

			<div class="form-group">
				<label>작성자</label> <input type="text" name="nickname"
					value="${user.nickname}" class="form-control" readonly="readonly">
			</div>
		</div>

		<div class="box-footer">
			<button type="submit" class="btn btn-primary">작성완료</button>
		</div>
	</form>
</section>
<%@include file="../include/footer.jsp"%>
