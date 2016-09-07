package com.backend;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class Indexer {
	public static String fileLocation = "F:/AWProjectFinal/AdaptiveNavigationSupport/Files";

	private static void indexFile(IndexWriter writer, File f) throws IOException {
		System.out.println("Indexing " + f.getName());
		Document doc = new Document();
		doc.add(new TextField("filename", f.getName(), TextField.Store.YES));
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

	private static void indexDirectory(IndexWriter writer, File dir) throws IOException {
		File[] files = dir.listFiles();
		for (int i = 0; i < files.length; i++) {
			File f = files[i];
			if (f.isDirectory()) {
				indexDirectory(writer, f);
			} else if (f.getName().endsWith(".txt")) {
				indexFile(writer, f);
			}
		}
	}

	@SuppressWarnings("resource")
	public void indexFiles() {
		try {
			StandardAnalyzer analyzer = new StandardAnalyzer();
			String indexedDir = "F:/AWProjectFinal/AdaptiveNavigationSupport/IndexedFiles";
			Directory indexDir = FSDirectory.open(new File(indexedDir).toPath());
			File dataDir = new File(fileLocation);
			if (!dataDir.exists() || !dataDir.isDirectory()) {
				throw new IOException(dataDir + " does not exist or is not a directory");
			}
			IndexWriterConfig config = new IndexWriterConfig(analyzer);
			IndexWriter writer = new IndexWriter(indexDir, config);
			indexDirectory(writer, dataDir);
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
