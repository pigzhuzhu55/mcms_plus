 package com.mingsoft.parser.impl.general;

 import com.mingsoft.parser.IParser;





































 public class GlobalLogoParser
   extends IParser
 {
   private static final String GLOBAL_LOGO = "\\{ms:global.logo/\\}";

   public GlobalLogoParser(String htmlContent, String newContent) {
     this.htmlCotent = htmlContent;
     this.newCotent = newContent;
   }




   public String parse() { return replaceAll("\\{ms:global.logo/\\}"); }
 }


