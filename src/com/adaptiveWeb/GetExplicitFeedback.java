package com.adaptiveWeb;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.db.DBConnector;
import com.db.UserTags;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

/**
 * Servlet implementation class GetExplicitFeedback
 */
@WebServlet("/getExplicitFeedback")
public class GetExplicitFeedback extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetExplicitFeedback() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		DBConnector db = new DBConnector();
		ArrayList <UserTags> data = new ArrayList<UserTags>();
		String userId =request.getSession().getAttribute("userID").toString();
		if(request.getParameter("cj")!=null && request.getParameter("cj").length()>0);
		{
			UserTags  u =  new UserTags();
			u.setUserid(Integer.parseInt(userId));
			u.setTag("collections");
			data.add(u);
			  u =  new UserTags();
			u.setUserid(Integer.parseInt(userId));
			u.setTag("constructor");
			data.add(u);
			  u =  new UserTags();
			u.setUserid(Integer.parseInt(userId));
			u.setTag("encapsulation");
			data.add(u);
			  u =  new UserTags();
			u.setUserid(Integer.parseInt(userId));
			u.setTag("eclipse");
			data.add(u);
			
		}
		if(request.getParameter("aj")!=null && request.getParameter("aj").length()>0);
		{
			UserTags  u =  new UserTags();
			u.setUserid(Integer.parseInt(userId));
			u.setTag("android");
			data.add(u);
			  u =  new UserTags();
			u.setUserid(Integer.parseInt(userId));
			u.setTag("hibernate");
			data.add(u);
			  u =  new UserTags();
			u.setUserid(Integer.parseInt(userId));
			u.setTag("jsp");
			data.add(u);
			  u =  new UserTags();
			u.setUserid(Integer.parseInt(userId));
			u.setTag("maven");
			data.add(u);
			
		}	
		if(request.getParameter("ds")!=null && request.getParameter("ds").length()>0);
		{
			
			UserTags  u =  new UserTags();
			u.setUserid(Integer.parseInt(userId));
			u.setTag("arraylist");
			data.add(u);
			  u =  new UserTags();
			u.setUserid(Integer.parseInt(userId));
			u.setTag("array");
			data.add(u);
			  u =  new UserTags();
			u.setUserid(Integer.parseInt(userId));
			u.setTag("queue");
			data.add(u);
			  u =  new UserTags();
			u.setUserid(Integer.parseInt(userId));
			u.setTag("sorting");
			data.add(u);
			
		}
		db.writeUserTags(data);
		
		//db.
		response.sendRedirect(request.getContextPath() + "/main.html");
		//doGet(request, response);
	}

}
