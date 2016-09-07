package com.backend;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

public class DBQuery {
	private static DBQuery db;
	private Connection conn;
	private MysqlDataSource dataSource;
	private String UER_REGISTER_TABLE_NAME = "User_Registration";
	private String FRIENDS_LISTS_TABLE_NAME = "FRIENDS_LISTS";
	private String POSTS_DATE_SORTED_TABLE_NAME = "posts_date_sorted";
	private String TAGS_POSTS_DATE_SORTED = "tags_posts_date_sorted";
	private String TAGS_POSTS_ANSWER_COUNT_SORTED = "tags_posts_answercount_sorted";
	private String TAGS_POSTS_SCORE_SORTED = "tags_posts_score_sorted";
	private String TAGS_POSTS_VIEWCOUNT_SORTED = "tags_posts_viewcount_sorted";
	
	private String SERVER_NAME = "jdbc:mysql://localhost:3306/adaptiveweb";

	private Statement stmt;

	public static DBQuery getInstance() {
		if (db == null)
			db = new DBQuery();
		return db;

	}

	public void openDataBase() {
		if (dataSource == null) {
			try {

				Class.forName("com.mysql.jdbc.Driver").newInstance();
				conn = DriverManager.getConnection(SERVER_NAME, "root", "");
				stmt = conn.createStatement();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public void closeDataBase() {
		if (dataSource != null) {
			try {
				conn.close();
				stmt.close();
				dataSource.getAutoClosePStmtStreams();

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}


	public void printUserResults() {

		ResultSet rs;
		String result = "";
		List<String> friends = new LinkedList<String>();
		int userId = 1288;

		try {
			String selectSQL = "SELECT * FROM " + POSTS_DATE_SORTED_TABLE_NAME + " LIMIT 5";
			System.out.println(selectSQL);
			PreparedStatement preparedStatement = conn.prepareStatement(selectSQL);
			rs = preparedStatement.executeQuery(selectSQL);
			try {
				while (rs.next()) {
					// result = rs.get
					result = rs.getString(1);
					System.out.println(result);
				}
			} finally {
				try {
					rs.close();
				} catch (Throwable ignore) {
				}
			}

			// return friends;
		} catch (Exception e) {
			e.printStackTrace();
		}

		// return friends;

	}
	
	
//tag, pageNumber(starts from 0 with pagesize 10), sortingtype
	
	//pageNumber starts with 0
	
	
	public ArrayList<PostObjectRecommendation> getResultsByDate(ArrayList<String> Tags,int pageNumber){
		
		ResultSet rs;
		String inTags = "";
		String result = "";
		int resultsPerPage = 10;
		ArrayList<PostObjectRecommendation> recosList = new ArrayList<PostObjectRecommendation>();
		
		for(int i = 0; i < Tags.size(); i++){
			
			inTags = inTags + "'" + Tags.get(i) + "'" + ",";
		}
		
		inTags = inTags.substring(0,inTags.length() - 1);
		//System.out.println(inTags);
				
		int limitStart = pageNumber * 10;
		int limitEnd =  resultsPerPage; 
		PostObjectRecommendation presentObj = null;
		
		try {
			String selectSQL = "SELECT * FROM " + TAGS_POSTS_DATE_SORTED + " WHERE SUBTAG IN (" + inTags + ")" + 
                    " ORDER BY CREATION_DATE DESC LIMIT " + limitStart + "," + limitEnd;
			System.out.println(selectSQL);
			PreparedStatement preparedStatement = conn.prepareStatement(selectSQL);
			rs = preparedStatement.executeQuery(selectSQL);
			try {
				while (rs.next()) {
					// result = rs.get
					presentObj = new PostObjectRecommendation();
					
					result = rs.getString(3)+ "," + "," + rs.getString(6);
					System.out.println(result);
					
					presentObj.setPostID(rs.getString(3));
					presentObj.setPostBody(rs.getString(5));
					presentObj.setPostTitle(rs.getString(6));
					recosList.add(presentObj);
				}
			} finally {
				try {
					rs.close();
				} catch (Throwable ignore) {
				}
			}

			// return friends;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return recosList;
	}
	
	

	private ArrayList<PostObjectRecommendation> getResultsByAnswerCount(ArrayList<String> tags, int pageNumber) {
		// TODO Auto-generated method stub
		ResultSet rs;
		String inTags = "";
		String result = "";
		int resultsPerPage = 10;
		ArrayList<PostObjectRecommendation> recosList = new ArrayList<PostObjectRecommendation>();
		
		for(int i = 0; i < tags.size(); i++){
			
			inTags = inTags + "'" + tags.get(i) + "'" + ",";
		}
		
		inTags = inTags.substring(0,inTags.length() - 1);
		//System.out.println(inTags);
				
		int limitStart = pageNumber * 10;
		int limitEnd =  resultsPerPage; 
		PostObjectRecommendation presentObj = null;
		
		try {
			String selectSQL = "SELECT * FROM " + TAGS_POSTS_ANSWER_COUNT_SORTED + " WHERE SUBTAG IN (" + inTags + ")" + 
                    " ORDER BY ANSWERCOUNT DESC LIMIT " + limitStart + "," + limitEnd;
			System.out.println(selectSQL);
			PreparedStatement preparedStatement = conn.prepareStatement(selectSQL);
			rs = preparedStatement.executeQuery(selectSQL);
			try {
				while (rs.next()) {
					// result = rs.get
					presentObj = new PostObjectRecommendation();
					
					result = rs.getString(3)+ "," + "," + rs.getString(6);
					System.out.println(result);
					
					presentObj.setPostID(rs.getString(3));
					presentObj.setPostBody(rs.getString(5));
					presentObj.setPostTitle(rs.getString(6));
					recosList.add(presentObj);
				}
			} finally {
				try {
					rs.close();
				} catch (Throwable ignore) {
				}
			}

			// return friends;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return recosList;
		
	}

	private ArrayList<PostObjectRecommendation> getResultsByViewCount(ArrayList<String> tags, int pageNumber) {
		// TODO Auto-generated method stub
		
		ResultSet rs;
		String inTags = "";
		String result = "";
		int resultsPerPage = 10;
		ArrayList<PostObjectRecommendation> recosList = new ArrayList<PostObjectRecommendation>();
		
		for(int i = 0; i < tags.size(); i++){
			
			inTags = inTags + "'" + tags.get(i) + "'" + ",";
		}
		
		inTags = inTags.substring(0,inTags.length() - 1);
		//System.out.println(inTags);
				
		int limitStart = pageNumber * 10;
		int limitEnd =  resultsPerPage; 
		PostObjectRecommendation presentObj = null;
		
		try {
			String selectSQL = "SELECT * FROM " + TAGS_POSTS_VIEWCOUNT_SORTED + " WHERE SUBTAG IN (" + inTags + ")" + 
                    " ORDER BY VIEWCOUNT DESC LIMIT " + limitStart + "," + limitEnd;
			System.out.println(selectSQL);
			PreparedStatement preparedStatement = conn.prepareStatement(selectSQL);
			rs = preparedStatement.executeQuery(selectSQL);
			try {
				while (rs.next()) {
					// result = rs.get
					presentObj = new PostObjectRecommendation();
					
					result = rs.getString(3)+ "," +  "," + rs.getString(6);
					System.out.println(result);
					
					presentObj.setPostID(rs.getString(3));
					presentObj.setPostBody(rs.getString(5));
					presentObj.setPostTitle(rs.getString(6));
					recosList.add(presentObj);
				}
			} finally {
				try {
					rs.close();
				} catch (Throwable ignore) {
				}
			}

			// return friends;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return recosList;

		
	}

	private ArrayList<PostObjectRecommendation> getResultsBySortedOrder(ArrayList<String> tags, int pageNumber,
			String TABLE_NAME,String COLUMN_NAME) {
		// TODO Auto-generated method stub
		
		ResultSet rs;
		String inTags = "";
		String result = "";
		int resultsPerPage = 10;
		ArrayList<PostObjectRecommendation> recosList = new ArrayList<PostObjectRecommendation>();
		
		for(int i = 0; i < tags.size(); i++){
			
			inTags = inTags + "'" + tags.get(i) + "'" + ",";
		}
		
		inTags = inTags.substring(0,inTags.length() - 1);
		//System.out.println(inTags);
				
		int limitStart = pageNumber * 10;
		int limitEnd =  resultsPerPage; 
		PostObjectRecommendation presentObj = null;
		
		try {
			String selectSQL =
					"select * from posts where id in (SELECT * FROM (SELECT postId FROM "
							 + TABLE_NAME + " WHERE SUBTAG IN (" + inTags + ")" + 
					                " ORDER BY " + COLUMN_NAME + " DESC LIMIT " + limitStart + "," + limitEnd
					                + ") as t)";
			System.out.println(selectSQL);
			PreparedStatement preparedStatement = conn.prepareStatement(selectSQL);
			rs = preparedStatement.executeQuery(selectSQL);
			try {
				while (rs.next()) {
					// result = rs.get
					presentObj = new PostObjectRecommendation();
					
					result = rs.getString(1)+ "," + rs.getString(6)+  "," + rs.getString(7);
					System.out.println(result);
					
					presentObj.setPostID(rs.getString(1));
					presentObj.setVotes(rs.getString(6));
					presentObj.setViews(rs.getString(7));
					presentObj.setPostBody(rs.getString(8));
					presentObj.setPostTitle(rs.getString(10));
					presentObj.setAnswers(rs.getString(12));
					recosList.add(presentObj);
				}
			} finally {
				try {
					rs.close();
				} catch (Throwable ignore) {
				}
			}

			// return friends;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return recosList;
		
	}
	
	public ArrayList<PostObjectRecommendation> getResults(int sortedOrder,ArrayList<String> Tags,int pageNumber){
		
		//Map<String, Object> x = new HashMap<>();
		 
		if(sortedOrder == 1){
			
			return getResultsBySortedOrder(Tags,pageNumber,TAGS_POSTS_DATE_SORTED,"CREATION_DATE");
			//getResultsByDate(Tags,pageNumber);
		}
		else if(sortedOrder == 2){
			return  getResultsBySortedOrder(Tags,pageNumber,TAGS_POSTS_SCORE_SORTED,"SCORE");
			//getResultsByScore(Tags,pageNumber);
		}
		else if(sortedOrder == 3){
			return  getResultsBySortedOrder(Tags,pageNumber,TAGS_POSTS_VIEWCOUNT_SORTED,"VIEWCOUNT");
			//getResultsByViewCount(Tags,pageNumber);
		}
		else if(sortedOrder == 4){
			return getResultsBySortedOrder(Tags,pageNumber,TAGS_POSTS_ANSWER_COUNT_SORTED,"ANSWERCOUNT");
			//getResultsByAnswerCount(Tags,pageNumber);
		}
		return null;
	}
	
	public static void main(String[] args) {

		DBQuery.getInstance().openDataBase();
		// DBQuery.getInstance().createFriendsTable();
		// DBQuery.getInstance().addGCMId("0761", "00787917");
		// userGCMId = DBQuery.getInstance().getUserGCMId("007797");

		//Tags = {"java","jsp"";

		
		ArrayList<String> Tags = new ArrayList<String>();
		Tags.add("jsp");
		
		
		DBQuery.getInstance().getResults(4, Tags, 2);
		//DBQuery.getInstance().getResults(4, tags, 2);
		
		//DBQuery.getInstance().getResults(4, tags, pageNumber);
		//DBQuery.getInstance().getResultsByDate(tags, pageNumber);
		//DBQuery.getInstance().getResultsByDate(tags, pageNumber);
		
		//DBQuery.getInstance().printUserResults();
		//DBQuery.getInstance().closeDataBase();

		// System.out.println(userGCMId);

	}
}