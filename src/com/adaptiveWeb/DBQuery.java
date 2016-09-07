/*package com.adaptiveWeb;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

public class DBQuery {
	private static DBQuery db;
	private Connection conn;
	private MysqlDataSource dataSource;
	private String POST_TABLE = "posts";
	private String POST_TABLE = "posts_answers";

	private String SERVER_NAME = "jdbc:mysql://localhost:3306/LetsMeet";

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

	public String getUserGCMId(String fbId) {
		ResultSet rs;
		String result = "";
		try {
			// String selectSQL = "SELECT gcmKey FROM " +
			// UER_REGISTER_TABLE_NAME + " where facebookId= ? ";
			String selectSQL = "SELECT gcmKey FROM " + UER_REGISTER_TABLE_NAME + " where facebookId= '" + fbId + "' ";
			System.out.println(selectSQL);
			// String selectSQL = "SELECT USER_ID, USERNAME FROM DBUSER WHERE
			// USER_ID = ?";
			PreparedStatement preparedStatement = conn.prepareStatement(selectSQL);
			// preparedStatement.setString(1, fbId);
			rs = preparedStatement.executeQuery(selectSQL); // rs =
															// stmt.executeQuery("SELECT
															// gcmKey FROM " +
															// UER_REGISTER_TABLE_NAME
															// + " where
															// facebookId=\'" +
															// fbId+"\'");
			try {
				while (rs.next()) {
					result = rs.getString(1);
				}
			} finally {
				try {
					rs.close();
				} catch (Throwable ignore) {
				}
			}

			return result;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	public boolean addGCMId(String gcmId, String fbId) {
		String query = "insert into " + UER_REGISTER_TABLE_NAME + "(gcmKey, facebookId) values (?, ?)";
		PreparedStatement stmt;
		try {
			stmt = conn.prepareStatement(query);

			try {
				stmt.setString(1, gcmId);

				stmt.setString(2, fbId);
				stmt.execute();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			} finally {
				try {
					stmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return true;

	}
	
	
	public List<String> getFriends(String fbId) {
		ResultSet rs;
		String result = "";
		List<String> friends = new LinkedList<String>();
		try {
			String selectSQL = "SELECT secondFriend FROM " + FRIENDS_LISTS_TABLE_NAME + " where firstFriend= '" + fbId + "' ";
			System.out.println(selectSQL);
			PreparedStatement preparedStatement = conn.prepareStatement(selectSQL);
			rs = preparedStatement.executeQuery(selectSQL); 
			try {
				while (rs.next()) {
					result = rs.getString(1);
					friends.add(result);
				}
			} finally {
				try {
					rs.close();
				} catch (Throwable ignore) {
				}
			}

			return friends;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return friends;
	}

	public boolean setFriends(String fbId1, String fbId2) {
		ResultSet rs;
		String result = "";
		try {
			String selectSQL = "SELECT * FROM " + FRIENDS_LISTS_TABLE_NAME + " where firstFriend= '" + fbId1
					+ "' and secondFriend= '" + fbId2 + "'";
			System.out.println(selectSQL);
			PreparedStatement preparedStatement = conn.prepareStatement(selectSQL);
			rs = preparedStatement.executeQuery(selectSQL);
			try {
				while (rs.next()) {
					result = rs.getString(1);
				}
			} finally {
				try {
					rs.close();
				} catch (Throwable ignore) {
				}
			}
			if (result.length() == 0) {

				String query = "insert into " + FRIENDS_LISTS_TABLE_NAME + "(firstFriend, secondFriend) values (?, ?)";
				PreparedStatement stmt;
				try {
					stmt = conn.prepareStatement(query);

					try {
						stmt.setString(1, fbId1);

						stmt.setString(2, fbId2);
						stmt.execute();
						query = "insert into " + FRIENDS_LISTS_TABLE_NAME + "(firstFriend, secondFriend) values (?, ?)";
						stmt = conn.prepareStatement(query);
						stmt.setString(1, fbId2);

						stmt.setString(2, fbId1);
						stmt.execute();

					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						return false;
					} finally {
						try {
							stmt.close();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							return false;
						}

					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					return false;

				}
				return true;

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return true;
	}

	public boolean createFriendsTable() {

		String query = "CREATE TABLE FRIENDS_LISTS " + "(firstFriend VARCHAR(255),secondFriend VARCHAR(255))";

		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(query);

			stmt.execute();

		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			if (stmt != null)
				stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return true;

	}

	public static void main(String[] args) {

		DBQuery.getInstance().openDataBase();
		DBQuery.getInstance().createFriendsTable();
		// DBQuery.getInstance().addGCMId("0761", "00787917");
		// userGCMId = DBQuery.getInstance().getUserGCMId("007797");

		DBQuery.getInstance().closeDataBase();

		// System.out.println(userGCMId);

	}
}
*/