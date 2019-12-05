 package com.mingsoft.parser.impl.general;

 import com.mingsoft.parser.IParser;
























































 public class GlobalHostParser
   extends IParser
 {
   private static final String GLOBAL_HOST = "\\{ms:global.host/\\}";

   public GlobalHostParser(String htmlContent, String newContent) {
     this.htmlCotent = htmlContent;
     this.newCotent = newContent;
   }



   public String parse() { return replaceAll("\\{ms:global.host/\\}"); }
 }


