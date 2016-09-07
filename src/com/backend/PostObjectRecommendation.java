package com.backend;

public class PostObjectRecommendation {

	String postID;
	String postTitle;
	String postBody;
	String votes;
	String answers;
	String views;
	
	public String getVotes() {
		return votes;
	}
	public void setVotes(String votes) {
		if(votes==null)
			return;
		this.votes = votes;
	}
	public String getAnswers() {
		return answers;
	}
	public void setAnswers(String answers) {
		if(answers==null)
			return;
		this.answers = answers;
	}
	public String getViews() {
		return views;
	}
	public void setViews(String views) {
		if(views==null)
			return;
		this.views = views;
	}
	public String getPostCreatorName() {
		if(postCreatorName==null)
			return "";
		return postCreatorName;
	}
	public void setPostCreatorName(String postCreatorName) {
		if(postCreatorName==null)
			return;
		this.postCreatorName = postCreatorName;
	}
	public String getPostCreatorScore() {
		if(postCreatorScore==null)
			return "";
		return postCreatorScore;
	}
	public void setPostCreatorScore(String postCreatorScore) {
		if(postCreatorScore==null)
			return;
		this.postCreatorScore = postCreatorScore;
	}
	String postCreatorName;
	String postCreatorScore;
	
	
	public String getPostID() {
		return postID;
	}
	public void setPostID(String postID) {
		if(postID==null)
			return;
		this.postID = postID;
	}
	public String getPostTitle() {
		return postTitle;
	}
	public void setPostTitle(String postTitle) {
		if(postTitle==null)
			return;
		this.postTitle = postTitle;
	}
	public String getPostBody() {
		return postBody;
	}
	public void setPostBody(String postBody) {
		if(postBody==null)
			return;
		this.postBody = postBody;
	}
	
	
	
}
