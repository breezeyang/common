package com.breeze.lucene.mmseg;

import java.io.IOException;
import java.io.StringReader;

import com.chenlb.mmseg4j.ComplexSeg;
import com.chenlb.mmseg4j.Dictionary;
import com.chenlb.mmseg4j.MMSeg;
import com.chenlb.mmseg4j.Seg;
import com.chenlb.mmseg4j.SimpleSeg;
import com.chenlb.mmseg4j.Word;


public class demo {

    public static String txt = "设施和服务";

    public static void simple() throws IOException {
        Dictionary dic = Dictionary.getInstance();
        Seg seg = new SimpleSeg(dic);
        MMSeg mmSeg = new MMSeg(new StringReader(txt), seg);
        Word word = null;
        while ((word = mmSeg.next()) != null) {
            System.out.print(word + "|");
        }

        System.out.println();
    }

    public static void complex() throws IOException {
        Dictionary dic = Dictionary.getInstance();
        Seg seg = new ComplexSeg(dic);
        MMSeg mmSeg = new MMSeg(new StringReader(txt), seg);
        Word word = null;
        while ((word = mmSeg.next()) != null) {
            System.out.print(word + "|");
        }
        System.out.println();
    }

    public static void main(String[] args) throws IOException {
        // simple();
        complex();
    }

}
