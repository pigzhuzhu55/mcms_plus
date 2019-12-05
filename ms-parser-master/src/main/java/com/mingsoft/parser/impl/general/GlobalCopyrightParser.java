 package com.mingsoft.parser.impl.general;

 import com.mingsoft.parser.IParser;





































 public class GlobalCopyrightParser
   extends IParser
 {
   private static final String GLOBAL_COPYRIGHT = "\\{ms:global.copyright/\\}";

   public GlobalCopyrightParser(String htmlContent, String newContent) {
     this.htmlCotent = htmlContent;
     this.newCotent = newContent;
   }




   public String parse() { return replaceAll("\\{ms:global.copyright/\\}"); }
 }


