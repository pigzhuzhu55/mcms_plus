 package com.mingsoft.parser.impl.general;

 import com.mingsoft.parser.IParser;
 import com.mingsoft.util.PageUtil;









































 public class PageParser
   extends IParser
 {
   private final String PAGE_INDEX = "\\{ms:page.index/\\}";




   private final String PAGE_PRE = "\\{ms:page.pre/\\}";




   private final String PAGE_NEXT = "\\{ms:page.next/\\}";




   private final String PAGE_OVER = "\\{ms:page.last/\\}";







   private PageUtil page;







   public PageParser(String htmlContent, PageUtil page) {
     this.htmlCotent = htmlContent;
     this.page = page;
   }


   public String parse() {
     if (this.page != null) {


       this.newCotent = this.page.getIndexUrl();
       String indexHtml = replaceAll("\\{ms:page.index/\\}");

       this.htmlCotent = indexHtml;
       this.newCotent = this.page.getPreviousUrl();
       String preHtml = replaceAll("\\{ms:page.pre/\\}");

       this.htmlCotent = preHtml;
       this.newCotent = this.page.getNextUrl();
       String nextHtml = replaceAll("\\{ms:page.next/\\}");

       this.htmlCotent = nextHtml;
       this.newCotent = this.page.getLastUrl();
       String traileHtml = replaceAll("\\{ms:page.last/\\}");
       return traileHtml;
     }
     return this.htmlCotent;
   }
 }


