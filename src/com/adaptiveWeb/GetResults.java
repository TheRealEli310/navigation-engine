package com.adaptiveWeb;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class GET_RESULTS
 */
@WebServlet("/GetResults")
public class GetResults extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public GetResults() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String output = "";

		double perPage = 10;

		String pageNum = request.getParameter("page");
		System.out.println(pageNum);
		if (pageNum == null)
			pageNum = "1";
		String rowCount = "100";

		if (!pageNum.toLowerCase().equals("undefined") && !rowCount.toLowerCase().equals("undefined")) {

			int parseInt = Integer.parseInt(rowCount);
			int tPages = (int) Math.ceil((parseInt) / perPage);
			output += "<input type=\"hidden\" class=\"pagenum\" value=\"" + pageNum
					+ "\" /><input type=\"hidden\" class=\"total-page\" value=\"" + tPages + "\" />";
			int pageNumber = Integer.parseInt(pageNum);
			List<Map<String, String>> userData = getUserData(pageNumber, (int) perPage);
			for (Map<String, String> subData : userData) {
				output += "<div class=\"url\">" + subData.get("url") + "</div>";
				output += "<div class=\"data\">" + subData.get("data") + "</div>";
			}

			response.setContentType("text/plain"); // Set content type of the
													// response so that jQuery
													// knows what it can expect.
			response.setCharacterEncoding("UTF-8"); // You want world
													// domination, huh?
			response.getWriter().write(output);
			// response.getWriter().append("Served at:
			// ").append(request.getContextPath());
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);

	}

	public List<Map<String, String>> getUserData(int pageNumber, int pageSize) {
		List<Map<String, String>> data = new LinkedList<Map<String, String>>();
		for (int i = 0; i <= pageSize; i++) {
			Map<String, String> h = new HashMap<String, String>();
			h.put("url", "www.google.com" + pageNumber);
			h.put("data", "some google data");
			h.put("date", "4/8/2013");
			data.add(h);

		}
		return data;
	}

}
