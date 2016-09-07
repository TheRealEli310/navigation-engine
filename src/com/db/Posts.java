package com.db;
public class Posts {
	private int ID;
	private int POSTTYPE;
	private int ACCEPTED_ANSWER_ID;
	private int PARENT_ID;
	private String TITLE;
	private String BODY;
	private String URL;
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public int getPOSTTYPE() {
		return POSTTYPE;
	}
	public void setPOSTTYPE(int pOSTTYPE) {
		POSTTYPE = pOSTTYPE;
	}
	public int getACCEPTED_ANSWER_ID() {
		return ACCEPTED_ANSWER_ID;
	}
	public void setACCEPTED_ANSWER_ID(int aCCEPTED_ANSWER_ID) {
		ACCEPTED_ANSWER_ID = aCCEPTED_ANSWER_ID;
	}
	public int getPARENT_ID() {
		return PARENT_ID;
	}
	public void setPARENT_ID(int pARENT_ID) {
		PARENT_ID = pARENT_ID;
	}
	public String getTITLE() {
		return TITLE;
	}
	public void setTITLE(String tITLE) {
		TITLE = tITLE;
	}
	public String getBODY() {
		return BODY;
	}
	public void setBODY(String bODY) {
		BODY = bODY;
	}
	public String getURL() {
		return URL;
	}
	public void setURL(String uRL) {
		URL = uRL;
	}
}
