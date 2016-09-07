package com.backend;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.FSDirectory;
import com.cleaner.DataCleanser;
import com.db.DBConnector;
import com.db.UserTags;

import java.io.File;

import java.io.IOException;
import java.util.*;

public class SimpleLuceneIndexing {

	private static Scanner sc;
	private final static String fileLocation = "C:/Users/Rakesh/Downloads/AdaptiveNavigationSupport/AdaptiveNavigationSupport/IndexedFiles";

	public static List<Map<String, String>> getResults(String query) throws IOException, ParseException {
		// QUERY PARSER
		StandardAnalyzer analyzer = new StandardAnalyzer();
		int hitsPerPage = 50;
		IndexReader reader = DirectoryReader.open(FSDirectory.open(new File(fileLocation).toPath()));
		IndexSearcher searcher = new IndexSearcher(reader);
		TopScoreDocCollector collector = TopScoreDocCollector.create(hitsPerPage);
		
		String querystr = query;
		querystr = DataCleanser.RemoveStopWords(querystr);
		Query q = new QueryParser("contents", analyzer).parse(querystr);
		searcher.search(q, collector);
		ScoreDoc[] hits = collector.topDocs().scoreDocs;
		
		DBConnector dbConnector = new DBConnector();
		List<Map<String, String>> data = new LinkedList<Map<String, String>>();
		
		for (int i = 0; i < 11/*hits.length*/; ++i) {
			int docId = hits[i].doc;
			Document d;
			d = searcher.doc(docId);
			String key_filename = d.get("filename");
			key_filename = key_filename.split(".txt")[0];
		System.out.println(key_filename);
			ArrayList<UserTags> tags = dbConnector.getTagsForPost(key_filename);
			String tagData = "";
			if (tags.size() > 0)
				for (UserTags t : tags)
					tagData += t.getTag() + "    ";
		System.out.println(tagData);
			Map<String, String> map = new HashMap<String, String>();
			map.put("url",  key_filename);
			map.put("data", tagData);
			map.put("postId", key_filename);
			map.put("title", dbConnector.getPostTitle(key_filename));
			data.add(map);

		}
		reader.close();
		return data;
	}
}