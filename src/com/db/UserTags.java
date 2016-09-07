package com.db;

public class UserTags {
	private String superTag;
	private String tag;
	private long score;
	private int userid;
	public long getScore() {
		return score;
	}
	public void setScore(long score) {
		this.score = score;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public String getSuperTag() {
		return superTag;
	}
	public void setSuperTag(String superTag) {
		this.superTag = superTag;
	}
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
}
