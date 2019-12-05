 package com.mingsoft.parser.impl.general;

 import com.mingsoft.parser.IParser;






































 public class GlobalUrlParser
   extends IParser
 {
   private static final String GLOBAL_URL = "\\{ms:global.url/\\}";

   public GlobalUrlParser(String htmlContent, String newContent) {
     this.htmlCotent = htmlContent;
     this.newCotent = newContent;
   }




   public String parse() { return replaceAll("\\{ms:global.url/\\}"); }
 }


