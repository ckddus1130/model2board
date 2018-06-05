package com.naver.ccy6451.domain;

public class Reply {
	private int rno;
	private int bno;
	private String email;
	private String nickname;
	private String replytext;
	private String regdate;
	public int getRno() {
		return rno;
	}
	public void setRno(int rno) {
		this.rno = rno;
	}
	public int getBno() {
		return bno;
	}
	public void setBno(int bno) {
		this.bno = bno;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getReplytext() {
		return replytext;
	}
	public void setReplytext(String replytext) {
		this.replytext = replytext;
	}
	public String getRegdage() {
		return regdate;
	}
	public void setRegdage(String regdage) {
		this.regdate = regdate;
	}
	@Override
	public String toString() {
		return "Reply [rno=" + rno + ", bno=" + bno + ", email=" + email + ", nickname=" + nickname + ", replytext="
				+ replytext + ", regdate=" + regdate + "]";
	}
	
}
