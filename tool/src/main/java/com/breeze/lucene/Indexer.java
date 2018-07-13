package com.breeze.lucene;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.*;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

/**
 * 单的索引建立
 * 
 * @author yangyang34
 *
 */
public class Indexer {
	public static Version luceneVersion = Version.LATEST;

	/**
	 * 建立索引
	 */
	public static void createIndex() {
		IndexWriter writer = null;
		try {
			// 1 创建Directory
			// Directory directory = new RAMDirectory(); // 创建内存directory
			Directory directory = FSDirectory.open(Paths.get("index")); // 硬盘上面生成irectory00
			// 2 创建IndexWriter
			IndexWriterConfig iwConfig = new IndexWriterConfig(new StandardAnalyzer());
			writer = new IndexWriter(directory, iwConfig);
			// 3 创建document对象
			Document document = null;
			// 4 为Ϊdocument添加field字段
			File f = new File("raw"); // 索引源代文件位置
			for (File file : f.listFiles()) {
				document = new Document();
				document.add(new StringField("path", f.getName(), Field.Store.YES));
				System.out.println(file.getName());
				document.add(new StringField("name", file.getName(), Field.Store.YES));
				InputStream stream = Files.newInputStream(Paths.get(file.toString()));
				document.add(new TextField("content",
						new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8)))); // textField内容会进行分词
				// document.add(new TextField("content", new FileReader(file)));
				// 如果不用utf-8编码的话直接用这个就可以了
				writer.addDocument(document);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 6、使用完成后需要将writer进行关闭
			try {
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) throws IOException {
		createIndex();
	}
}