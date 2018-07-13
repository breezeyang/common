package com.breeze.lucene;

import java.nio.file.Paths;
import java.io.*;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.Explanation;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

/**
 * 使用索引搜索文件
 *
 * @author yangyang34
 */

public class Searcher {


    public static Version luceneVersion = Version.LATEST;

    /**
     * 查询内容
     */
    public static String indexSearch(String keywords) {
        String res = "";
        DirectoryReader reader = null;
        try {
            // 1 创建ßDirectory
            Directory directory = FSDirectory.open(Paths.get("index"));// 硬盘上生成Directory
            // 2 创建IndexReader
            reader = DirectoryReader.open(directory);
            // 3 根据IndexWriter创建IndexSearcher
            IndexSearcher searcher = new IndexSearcher(reader);
            // 4 创建搜索的query
            // 创建parse用来确定搜索的内容，第二个参数表示搜索的域
            QueryParser parser = new QueryParser("content", new StandardAnalyzer()); // content表示搜索的域或者说字段
            Query query = parser.parse(keywords); // 被搜索的内容
            // 5 根据Searcher返回TopDocs
            TopDocs tds = searcher.search(query, 20); // 查询20条记录
            // 6 根据TopDocs获取ScoreDoc
            ScoreDoc[] sds = tds.scoreDocs;
            // 7 据Searcher和ScoreDoc获取搜索到的document对象
            int cou = 0;
            for (ScoreDoc sd : sds) {
                cou++;
                Document d = searcher.doc(sd.doc);
                // 8 根据document对象获取查询的字段值
                /**
                 * 查询结果中content为空，是因为索引中没有存储content的内容，
                 * 需要根据索引path和name从原文件中获取content
                 **/
                Explanation explanation = searcher.explain(query, sd.doc);
                System.out.println(explanation.toString());
                res += cou + ". " + d.get("path") + " " + d.get("name") + " " + d.get("content") + "\n";
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 9、关闭reader
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return res;
    }

    public static void main(String[] args) throws IOException {
        System.out.println(indexSearch("hello world, fast text")); // 搜索的内容可以修改
    }
}