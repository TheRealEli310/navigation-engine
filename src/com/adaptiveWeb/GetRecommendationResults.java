package com.adaptiveWeb;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.backend.DBQuery;
import com.backend.PostObjectRecommendation;

/**
 * Servlet implementation class GET_RESULTS
 */
@WebServlet("/GetRecommendationResults")
public class GetRecommendationResults extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public GetRecommendationResults() {
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			String output = "";

			double perPage = 10;

			String pageNum = request.getParameter("page");
			String sortType = request.getParameter("sortType");
			String tags = request.getParameter("tags");
			ArrayList<String> tagsList = new ArrayList<String>();
			String[] split = tags.split(",");
			for (String s : split) {
				if (s.length() > 1)
					tagsList.add(s);

			}
			//String query = request.getParameter("query");
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
				DBQuery instance = DBQuery.getInstance();
				instance.openDataBase();
				ArrayList<PostObjectRecommendation> results = instance.getResults(Integer.parseInt(sortType), tagsList,
						pageNumber);
				instance.closeDataBase();
				InputStream in = GetResults.class.getResourceAsStream("templateSearch.txt");
				  BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			        StringBuilder out = new StringBuilder();
			        String line;
			        while ((line = reader.readLine()) != null) {
			            out.append(line);
			        }
			
				
				
				for (PostObjectRecommendation subData : results) {
					String template=out.toString();
					template= template.replace("##title##", subData.getPostTitle());
					template= template.replace("##title##", subData.getPostTitle());

					template=template.replace("##data##", subData.getPostBody().substring(0, 50));
					template=template.replace("##data##", subData.getPostBody().substring(0, 50));

					template=template.replace("##postId##", subData.getPostID());
					template=template.replace("##postId##", subData.getPostID());
					template=template.replace("##postId##", subData.getPostID());

					template=template.replace("##answers##", subData.getAnswers());
					template=template.replace("##views##", subData.getViews());
					template=template.replace("##votes##", subData.getVotes());
					template=template.replace("##name##", subData.getPostCreatorName());
					template=template.replace("##reputation##", subData.getPostCreatorScore());

					output+=template;
				}
				response.setContentType("text/plain");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(output);
			}
		} catch (Exception e) {
			e.printStackTrace();
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
