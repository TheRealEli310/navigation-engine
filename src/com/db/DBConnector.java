package com.db;

import java.sql.*;
import java.util.ArrayList;

public class DBConnector {

	public Connection connectToDatabase() {
		Connection connection = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/adaptiveweb", "root", "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return connection;
	}

	public String convertArrToCSV(String[] value) {
		String csv = "";
		for (int i = 0; i < value.length; i++) {
			if (i == 0) {
				csv = value[i];
			} else {
				csv += ", " + value[i];
			}
		}
		return csv;
	}

	public ArrayList<UserTags> getTagsForPost(String postID) {
		ArrayList<UserTags> tags = new ArrayList<UserTags>();
		if (postID == null) {
			return null;
		}
		try {
			String sql = "SELECT SUPERTAG,SUBTAG,POSTID FROM TAGTOPOST WHERE POSTID = " + postID;
			Connection connection = connectToDatabase();
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				UserTags tag = new UserTags();
				tag.setTag(resultSet.getString("SUBTAG"));
				tag.setSuperTag(resultSet.getString("SUPERTAG"));
				tags.add(tag);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tags;
	}

	public ArrayList<UserTags> getAllTagForPosts(String[] postIDs) {
		String csvPostIDs = convertArrToCSV(postIDs);
		ArrayList<UserTags> tags = new ArrayList<UserTags>();
		try {
			String sql = "SELECT DISTINCT(COUNT(SUBTAG)) REP,SUBTAG, SUPERTAG FROM TAGTOPOST WHERE POSTID IN ("
					+ csvPostIDs + ") GROUP BY SUBTAG,SUPERTAG ORDER BY REP DESC LIMIT 3";
			Connection connection = connectToDatabase();
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				UserTags tag = new UserTags();
				tag.setTag(resultSet.getString("SUBTAG"));
				tag.setSuperTag(resultSet.getString("SUPERTAG"));
				tags.add(tag);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tags;
	}

	public String getTagsforPostCSV(String[] postIDs) {
		ArrayList<UserTags> tags = getAllTagForPosts(postIDs);
		String csvTags = "";
		boolean firstTag = true;
		for (UserTags t : tags) {
			if (firstTag) {
				csvTags = t.getSuperTag() + ":" + t.getTag();
				firstTag = false;
			} else {
				csvTags += "," + t.getSuperTag() + ":" + t.getTag();
			}
		}
		return csvTags;
	}

	public String getTagsforPostCSV(ArrayList<UserTags> userTags) {
		String csvTags = "";
		boolean firstTag = true;
		if (userTags.size() > 0)
			for (UserTags t : userTags) {
				if (firstTag) {
					csvTags = t.getSuperTag() + ":" + t.getTag();
					firstTag = false;
				} else {
					csvTags += "," + t.getSuperTag() + ":" + t.getTag();
				}
			}
		return csvTags;
	}

	public ArrayList<UserTags> getAllTags() {
		ArrayList<UserTags> tags = new ArrayList<UserTags>();
		try {
			String sql = "SELECT DISTINCT(SUBTAG), SUPERTAG FROM TAGTOPOST ORDER BY SUPERTAG DESC";
			Connection connection = connectToDatabase();
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				UserTags tag = new UserTags();
				tag.setTag(resultSet.getString("SUBTAG"));
				tag.setSuperTag(resultSet.getString("SUPERTAG"));
				tags.add(tag);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tags;
	}

	public boolean isUserNameAvailable(String userName) {
		if (userName == null) {
			return false;
		}
		Connection connection = connectToDatabase();
		String sql = "SELECT ID FROM USERS where lower(DisplayName)= '" + userName + "'";
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				int id = resultSet.getInt("ID");
				if (id > 0) {
					return false;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	public int getUserID(String userName) {
		if (userName == null) {
			return 0;
		}
		Connection connection = connectToDatabase();
		String sql = "SELECT ID FROM USERS where lower(DisplayName) = '" + userName + "'";
		int userID = 0;
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				userID = resultSet.getInt("ID");
				if (userID > 0) {
					return userID;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userID;
	}

	public ArrayList<Posts> getPostDetails(String[] ids) {
		if (ids.length < 1) {
			return null;
		}
		ArrayList<Posts> posts = new ArrayList<Posts>();
		Connection conn = connectToDatabase();
		String queryIDs = convertArrToCSV(ids);
		String query = "SELECT ID, POSTTYPE,  ACCEPTED_ANSWER_ID, PARENT_ID, TITLE, BODY FROM POSTS  WHERE ID IN ("
				+ queryIDs + ")";
		try {
			Statement stmt = conn.createStatement();
			ResultSet resSet = stmt.executeQuery(query);
			while (resSet.next()) {
				Posts currentPost = new Posts();
				currentPost.setID(resSet.getInt("ID"));
				currentPost.setPARENT_ID(resSet.getInt("PARENT_ID"));
				currentPost.setACCEPTED_ANSWER_ID(resSet.getInt("ACCEPTED_ANSWER_ID"));
				currentPost.setTITLE(resSet.getString("TITLE"));
				currentPost.setBODY(resSet.getString("BODY"));
				currentPost.setURL("http://stackoverflow.com/questions/" + currentPost.getID());
				posts.add(currentPost);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return posts;
	}

	public String getPostTitle(String postID) {
		return getQuestion(postID).getTITLE();
	}

	public Posts getQuestion(String postID) {
		String query = "SELECT ID, POSTTYPE, ACCEPTED_ANSWER_ID, PARENT_ID, TITLE, BODY FROM POSTS  WHERE ID = "
				+ postID;
		Posts currentPost = new Posts();
		try {
			Connection connection = connectToDatabase();
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			while (resultSet.next()) {
				currentPost.setID(resultSet.getInt("ID"));
				currentPost.setPARENT_ID(resultSet.getInt("PARENT_ID"));
				currentPost.setACCEPTED_ANSWER_ID(resultSet.getInt("ACCEPTED_ANSWER_ID"));
				currentPost.setTITLE(resultSet.getString("TITLE"));
				currentPost.setBODY(resultSet.getString("BODY"));

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return currentPost;
	}

	public Posts getAnswer(Posts question) {
		Connection connection = connectToDatabase();
		Posts answer = new Posts();
		String query = "SELECT ID, POSTTYPE, ACCEPTED_ANSWER_ID, PARENT_ID, TITLE, BODY FROM POSTS_ANSWERS WHERE ID = "
				+ question.getACCEPTED_ANSWER_ID();
		try {
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(query);
			while (rs.next()) {
				answer.setID(rs.getInt("ID"));
				answer.setPARENT_ID(rs.getInt("PARENT_ID"));
				answer.setTITLE(rs.getString("TITLE"));
				answer.setBODY(rs.getString("BODY"));
				if (answer.getPARENT_ID() == question.getID()) {
					return answer;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return answer;
	}

	public UserDetails getUserDetails(String userID) {
		UserDetails user = new UserDetails();
		try {
			Connection connection = connectToDatabase();
			Statement stmt = connection.createStatement();
			String query = "SELECT ID as userID, DisplayName as userName,  CASE WHEN Age IS NULL THEN 0 ELSE Age END as age, accountID as accountID, reputation FROM USERS WHERE ID = "
					+ userID;
			ResultSet resultSet = stmt.executeQuery(query);
			while (resultSet.next()) {
				user.setUserID(resultSet.getInt("userID"));
				user.setUserName(resultSet.getString("userName"));
				user.setAge(resultSet.getInt("age"));
				user.setAccountID(resultSet.getInt("accountID"));
				user.setReputation(resultSet.getInt("reputation"));
				if (!user.getUserName().isEmpty()) {
					return user;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return user;
	}

	public int getNewUserID() {
		Connection connection = connectToDatabase();
		String sql = "SELECT MAX(ID) as ID FROM USERS";
		int userID = 0;
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				userID = resultSet.getInt("ID");
				if (userID > 0) {
					return ++userID;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ++userID;
	}

	public boolean createNewUser(int userID, String userName) {
		String sql = "INSERT INTO `USERS` (`Id`,`Reputation`,	`CreationDate`,`DisplayName`,`LastAccessDate`,`Views`,`UpVotes`,`DownVotes`) VALUES ("
				+ userID + ",0,	NOW(),\"" + userName + "\",NOW(),0,0,0)";
		Connection connection = connectToDatabase();
		try {
			Statement statement = connection.createStatement();
			statement.executeQuery(sql);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public ArrayList<UserTags> getUserTags(int userID) {
		ArrayList<UserTags> tags = new ArrayList<UserTags>();
		try {
			String sql = "SELECT t.supertag as supertag, up.SUBTAG as tag, dif as tagscore  FROM userprofile up left join tags t on up.subtag = t.subtag where user = "
					+ userID;
			Connection connection = connectToDatabase();
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				UserTags tag = new UserTags();
				tag.setSuperTag(resultSet.getString("supertag"));
				tag.setTag(resultSet.getString("tag"));
				tag.setScore(resultSet.getLong("tagscore"));
				tag.setUserid(userID);
				tags.add(tag);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tags;
	}

	public void writeUserTags(ArrayList<UserTags> tags) {
		if (tags.size() > 0) {
			String sql = "INSERT INTO `userprofile` (`user`,`subtag`,`dif`) VALUES ";
			for (int i = 0; i < tags.size(); i++) {
				String subsql = "";
				if (i == 0) {
					subsql += "(";
					subsql += tags.get(i).getUserid() + ",'" + tags.get(i).getTag() + "'," + tags.get(i).getScore();
					subsql += ") ";
				} else {
					subsql += ", (";
					subsql += tags.get(i).getUserid() + " ,'" + tags.get(i).getTag() + "', " + tags.get(i).getScore();
					subsql += ") ";
				}
				sql += subsql;
				System.out.println(sql);
			}
			try {
				Connection connection = connectToDatabase();
				Statement stmt = connection.createStatement();
				stmt.executeUpdate(sql);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
