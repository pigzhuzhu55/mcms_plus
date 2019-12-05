 package com.mingsoft.parser.impl.general;

 import com.mingsoft.parser.IParser;





































 public class GlobalDescripParser
   extends IParser
 {
   private static final String GLOBAL_DESCRIP = "\\{ms:global.descrip/\\}";

   public GlobalDescripParser(String htmlContent, String newContent) {
     this.htmlCotent = htmlContent;
     this.newCotent = newContent;
   }




   public String parse() { return replaceAll("\\{ms:global.descrip/\\}"); }
 }


