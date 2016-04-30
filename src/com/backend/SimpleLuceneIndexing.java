package com.backend;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Lucene Demo: basic similarity based content indexing
 * 
 * @author Sharonpova Current sample files fragments of wikibooks and
 *         stackoverflow.
 */

public class SimpleLuceneIndexing {

	private static void indexDirectory(IndexWriter writer, File dir) throws IOException {
		File[] files = dir.listFiles();
		for (int i = 0; i < files.length; i++) {
			File f = files[i];
			if (f.isDirectory()) {
				indexDirectory(writer, f); // recurse
			} else if (f.getName().endsWith(".txt")) {
				// call indexFile to add the title of the txt file to your index
				// (you can also index html)
				indexFile(writer, f);
			}
		}
	}

	private static void indexFile(IndexWriter writer, File f) throws IOException {
		System.out.println("Indexing " + f.getName());
		Document doc = new Document();
		doc.add(new TextField("filename", f.getName(), TextField.Store.YES));

		// open each file to index the content
		try {

			FileInputStream is = new FileInputStream(f);
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			StringBuffer stringBuffer = new StringBuffer();
			String line = null;
			while ((line = reader.readLine()) != null) {
				stringBuffer.append(line).append("\n");
			}
			reader.close();
			doc.add(new TextField("contents", stringBuffer.toString(), TextField.Store.YES));

		} catch (Exception e) {

			System.out.println("something wrong with indexing content of the files");
		}

		writer.addDocument(doc);

	}

	public static Map<String, String> buildUrlMap(String filename) {
		Map<String, String> map = new HashMap<String, String>();

		try {
			Scanner sc = new Scanner(new File(filename));

			while (sc.hasNextLine()) {
				String line = sc.nextLine();
				String[] t = line.split("####");
				String key = t[0];
				String value = t[1];
				map.put(key, value);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return map;

	}


	public static void main(String[] args) throws IOException, ParseException {

		String textfolder = "text";
		String mapfile = "./" + textfolder + "/mapfile.txt";

		Map<String, String> map = buildUrlMap(mapfile);
		// System.out.println(map);

		File dataDir = new File(textfolder); // my sample file folder path
		// Check whether the directory to be indexed exists
		if (!dataDir.exists() || !dataDir.isDirectory()) {
			throw new IOException(dataDir + " does not exist or is not a directory");
		}
		Directory indexDir = new RAMDirectory();

		// Specify the analyzer for tokenizing text.
		StandardAnalyzer analyzer = new StandardAnalyzer();
		IndexWriterConfig config = new IndexWriterConfig(analyzer);
		IndexWriter writer = new IndexWriter(indexDir, config);

		// call indexDirectory to add to your index
		// the names of the txt files in dataDir
		indexDirectory(writer, dataDir);
		writer.close();

		// Query string!
		// String querystr = "contazaents:Null Pointer";

		// List<String> posts = readPosts();

		int hitsPerPage = 50;
		TopScoreDocCollector collector = null;
		IndexSearcher searcher = null;
		IndexReader reader = DirectoryReader.open(indexDir);
		searcher = new IndexSearcher(reader);
		collector = TopScoreDocCollector.create(hitsPerPage);

		
		String querystr = " One way to implement deep copy is to add copy constructors to each associated class. ";
		querystr = querystr.replaceAll("[^a-zA-Z]", " ");
		querystr = querystr.replaceAll("\\s+", " ");
		Query q = new QueryParser("contents", analyzer).parse(querystr);
		
		
		
		searcher.search(q, collector);
		ScoreDoc[] hits = collector.topDocs().scoreDocs;
		System.out.println("Found " + hits.length + " hits.");
		System.out.println();
		
		int count = 0;
		for (int i = 0; i < hits.length; ++i) {
			int docId = hits[i].doc;
			Document d;
			d = searcher.doc(docId);
			String key_filename = d.get("filename");
			String subtopicurl = key_filename.split(".txt")[0].split("#")[1];
			String keyHeading = key_filename.split("#")[0];
			String text1 = gettext("./" + textfolder + "/" + key_filename);

			key_filename = key_filename.split("#")[0] + ".txt";
			String acturl = map.get(key_filename);

			text1 = text1.substring(0, (text1.length() < 400) ? text1.length() : 400) + " ...";

			// ---------------------------results-----------------------
			System.out.println("No: "+ count++);
			System.out.println(text1);
			System.out.println(acturl);
			System.out.println();

		} 
		

		reader.close();

	}


	public static String gettext(String file) {
		StringBuffer res = new StringBuffer();

		try {
			Scanner sc = new Scanner(new File(file));
			while (sc.hasNextLine()) {
				res.append(sc.nextLine());
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return res.toString();
	}

}