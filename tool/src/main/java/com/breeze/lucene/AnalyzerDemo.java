package com.breeze.lucene;

import java.io.IOException;
import java.io.StringReader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.analysis.core.StopAnalyzer;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

public class AnalyzerDemo {

    private static final String[] example =
            {"The quick brown fox jumped over the lazy dog", "XY&Z CORPORATION -xyz@example.com", "你好，我是中国人"};

    private static final Analyzer[] analyzers = new Analyzer[]{new WhitespaceAnalyzer(), new SimpleAnalyzer(),
            new StopAnalyzer(), new StandardAnalyzer()};

    public static void main(String[] args) throws IOException {
        String[] strings = example;
        for (String text : strings) {
            analyze(text);
        }
    }

    public static void analyze(String text) throws IOException {
        System.out.println("analyzing \"" + text + "\"");
        for (Analyzer analyzer : analyzers) {
            String name = analyzer.getClass().getSimpleName();
            System.out.print(" " + name + " ");
            displayTokens(analyzer, text);
            System.out.println();

        }
    }

    public static void displayTokens(Analyzer analyzer, String text) throws IOException {
        TokenStream ts = analyzer.tokenStream("contents", new StringReader(text));
        displayTokens(ts);

        if (ts != null)
            try {
                ts.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    public static void displayTokens(TokenStream ts) throws IOException {
        CharTermAttribute cta = ts.addAttribute(CharTermAttribute.class);
        ts.reset();
        while (ts.incrementToken()) {
            // CharTermAttribute cta=ts.getAttribute(CharTermAttribute.class);
            System.out.print(" " + cta + " ");
        }
        ts.end();

    }
}
