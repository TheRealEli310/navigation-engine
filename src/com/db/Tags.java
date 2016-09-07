package com.db;

public class Tags {
	private int postID;
	private String tag;
	private String superTag;

	public int getPostID() {
		return postID;
	}

	public void setPostID(int postID) {
		this.postID = postID;
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
}
