 package com.mingsoft.parser.impl.general;

 import com.mingsoft.parser.IParser;






































 public class GlobalKeywordParser
   extends IParser
 {
   private static final String GLOBAL_KEYWORD = "\\{ms:global.keyword/\\}";

   public GlobalKeywordParser(String htmlContent, String newContent) {
     this.htmlCotent = htmlContent;
     this.newCotent = newContent;
   }




   public String parse() { return replaceAll("\\{ms:global.keyword/\\}"); }
 }


