 package com.mingsoft.parser.impl.general;

 import com.mingsoft.parser.IParser;





































 public class GlobalNameParser
   extends IParser
 {
   private static final String GLOBAL_NAME = "\\{ms:global.name/\\}";

   public GlobalNameParser(String htmlContent, String newContent) {
     this.htmlCotent = htmlContent;
     this.newCotent = newContent;
   }




   public String parse() { return replaceAll("\\{ms:global.name/\\}"); }
 }


