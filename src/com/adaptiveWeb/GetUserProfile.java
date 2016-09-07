package com.adaptiveWeb;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.db.DBConnector;
import com.db.UserDetails;
import com.db.UserTags;

@WebServlet(asyncSupported = true, urlPatterns = { "/GetUserProfile" })
public class GetUserProfile extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public GetUserProfile() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String userID = request.getParameter("userID");
		DBConnector dbConn = new DBConnector();
		UserDetails user = dbConn.getUserDetails(userID);
		int percentile = (int)(Math.random()*100);//(user.getReputation() / 11010) * 100;
		ArrayList<UserTags> tags = dbConn.getUserTags(Integer.parseInt(userID));
		response.setContentType("text/html");
		request.setAttribute("percentile", percentile);
		request.setAttribute("userName", user.getUserName());
		request.setAttribute("userReputation", user.getReputation());
		String json = getTagsJSON(tags);
		request.setAttribute("wordCloud", json);
		request.getRequestDispatcher("UserProfile.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	private String getTagsJSON(ArrayList<UserTags> tags) {
		long maxValue = 0;
		for (UserTags tag : tags) {
			if (tag.getScore() > maxValue) {
				maxValue = tag.getScore();
			}
		}
		String json = "[";
		for (int i = 0; i < tags.size(); i++) {

			if (i == 0) {
				json += "{\"text\":\"" + tags.get(i).getTag() + "\",\"size\":"
						+ ((float) (tags.get(i).getScore() * 100 / (maxValue))) + "}";
			} else {
				json += ",{\"text\":\"" + tags.get(i).getTag() + "\",\"size\":"
						+ ((float) (tags.get(i).getScore() * 100 / (maxValue))) + "}";
			}
		}
		json += "]";

		return json;
	}

}
