 package com.mingsoft.parser.impl.general;

 import com.mingsoft.parser.IParser;
 import com.mingsoft.util.PageUtil;

















































 public class PageNumParser
   extends IParser
 {
   private PageUtil page;
   private static final String PAGE_TOTAL = "\\{ms:page.total/\\}";
   private static final String PAGE_CUR = "\\{ms:page.cur/\\}";
   private static final String PAGE_RCOUNT = "\\{ms:page.rcount/\\}";

   public PageNumParser(String htmlContent, PageUtil page) {
     this.htmlCotent = htmlContent;
     this.page = page;
   }


   public String parse() {
     if (this.page != null) {


       this.htmlCotent = replaceRegex("\\{ms:page.total/\\}", this.page.getPageCount());

       this.htmlCotent = replaceRegex("\\{ms:page.cur/\\}", this.page.getPageNo() + 1);

       this.htmlCotent = replaceRegex("\\{ms:page.rcount/\\}", this.page.getRecordCound());
       return this.htmlCotent;
     }
     return this.htmlCotent;
   }


   public String replaceRegex(String regex, int num) {
     this.newCotent = Integer.toString(num);
     String html = replaceAll(regex);
     return html;
   }
 }


