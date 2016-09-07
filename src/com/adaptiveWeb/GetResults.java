package com.adaptiveWeb;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLStreamHandlerFactory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.lucene.queryparser.classic.ParseException;

import com.backend.PostObjectRecommendation;
import com.backend.SimpleLuceneIndexing;
import com.db.DBConnector;
import com.db.UserTags;

@WebServlet("/GetResults")
public class GetResults extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public GetResults() {
	}

	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String query = request.getParameter("query");
		List<Map<String, String>> userData = null;
		String pageNum = request.getParameter("page");
		try {
			userData = SimpleLuceneIndexing.getResults(query);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		// IF ANY RESULTS WERE RETRIEVED
		if (userData.size() > 0) {
			String[] postIDs = new String[userData.size()];
			int index = 0;
			for (Map<String, String> subData : userData) {
				postIDs[index] = subData.get("postID");
				index++;
			}
			// String postTags = new DBConnector().getTagsforPostCSV(postIDs);
			ArrayList<UserTags> tags = new DBConnector().getAllTagForPosts(postIDs);
			if (request.getSession().getAttribute("userTags") != null) {
				ArrayList<UserTags> knownTags = (ArrayList<UserTags>) request.getSession().getAttribute("userTags");
				ArrayList<UserTags> newTags = getNewTags(tags, knownTags);
				System.out.println("Ajax " +new DBConnector().getTagsforPostCSV(newTags));
				request.getSession().removeAttribute("userTags");
				request.getSession().setAttribute("userTags", newTags);
			}

			String output = "";
			double perPage = 10;
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
				InputStream in = GetResults.class.getResourceAsStream("templateSearch2.txt");
				  BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			        StringBuilder out = new StringBuilder();
			        String line;
			        while ((line = reader.readLine()) != null) {
			            out.append(line);
			        }
			
				
				
				for (Map<String ,String> subData : userData) {
					String template=out.toString();
					template= template.replace("##title##", subData.get("title"));
					template= template.replace("##title##", subData.get("title"));

					template=template.replace("##data##", subData.get("data"));
					template=template.replace("##data##", subData.get("data"));

					template=template.replace("##postId##", subData.get("postId"));
					template=template.replace("##postId##", subData.get("postId"));
					template=template.replace("##postId##", subData.get("postId"));


					output+=template;
				}
				// request.getSession().setAttribute("storedTags", postTags);

			} else {
				output = "<div>There were no results for this query.</div>";
			}
			response.setContentType("text/plain");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(output);
		}

	}

	private ArrayList<UserTags> getNewTags(ArrayList<UserTags> tags, ArrayList<UserTags> knownTags) {
		ArrayList<UserTags> newTags = new ArrayList<UserTags>();
		HashSet<String> knownHash = new HashSet<String>();
		for (UserTags knownTag : knownTags) {
			knownHash.add(knownTag.getTag());
		}
		if (knownHash.isEmpty()) {
			return tags;
		} else {
			for (UserTags tag : tags) {
				if (!knownHash.contains(tag.getTag())) {
					newTags.add(tag);
				}
				else{
					tag.setScore(tag.getScore()+20);
					newTags.add(tag);
				}
			}
		}
		return newTags;
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
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
