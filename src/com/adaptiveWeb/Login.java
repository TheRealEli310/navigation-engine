package com.adaptiveWeb;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.db.DBConnector;
import com.db.UserTags;

@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Login() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			DBConnector dbConn = new DBConnector();
			String type = request.getParameter("type");
			if (type != null && type.toLowerCase().equalsIgnoreCase("signup")) {
				String firstName = request.getParameter("firstName");
				String lastName = request.getParameter("lastName");
				// String password = request.getParameter("password");
				String username = request.getParameter("userName");
				if (dbConn.isUserNameAvailable(username)) {
					User user = new User();
					user.setFirstName(firstName);
					user.setLastName(lastName);
					user.setUserName(username);
					int userID = dbConn.getNewUserID();
					dbConn.createNewUser(userID, username);
					request.getSession().setAttribute("user", user);
					request.getSession().setAttribute("userID", userID);
					response.sendRedirect(request.getContextPath() + "/main.html");
				} else {
					// User Name exists - show error message
					response.setContentType("text/html");
					PrintWriter out = response.getWriter();
					out.println("<HTML><HEAD><TITLE>Access Denied</TITLE></HEAD>");
					out.println(
							"<BODY>The username is invalid/already in use. Click <A HREF=\"/AdaptiveNavigationSupport/\">here </a>to go back.");
					out.println("</BODY></HTML>");
				}
			}
			if (type != null && type.toLowerCase().equalsIgnoreCase("login")) {
				String username = request.getParameter("userName");
				// String password = request.getParameter("password");
				if (!dbConn.isUserNameAvailable(username)) {
					User user = new User();
					user.setUserName(username);
					int userID = dbConn.getUserID(username);
					ArrayList<UserTags> tags = dbConn.getUserTags(userID);
					String userTags = dbConn.getTagsforPostCSV(tags);
					request.getSession().setAttribute("user", user);
					request.getSession().setAttribute("userID", userID);
					request.getSession().setAttribute("userTags", tags);
					//request.getSession().setAttribute("storedTags", userTags);
					System.out.println(userTags);
					response.sendRedirect(request.getContextPath() + "/getExplicitData.html");
				} else {
					response.setContentType("text/html");
					PrintWriter out = response.getWriter();
					out.println("<HTML><HEAD><TITLE>Access Denied</TITLE></HEAD>");
					out.println(
							"<BODY>Username does not exist. Click <A HREF=\"/AdaptiveNavigationSupport/\">here </a>to go back.");
					out.println("</BODY></HTML>");
				}
			}
		} catch (Exception e) {
			request.getRequestDispatcher("/WEB-INF/login.html").forward(request, response);
		}
	}
}
