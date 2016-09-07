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

@WebServlet("/GetTagResults")
public class GetTagResults extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public GetTagResults() {
		super();
	}

	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		ArrayList<UserTags> userTags = (ArrayList<UserTags>) request.getSession().getAttribute("userTags");
		if (userTags!=null && userTags.size() > 0) {
			String userCSV = new DBConnector().getTagsforPostCSV(userTags);
			System.out.println("Ajax CSV: " + userCSV);
			response.getWriter().write(userCSV);
		} else {
			response.getWriter().write("NO TAGS RETRIEVED");

		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
