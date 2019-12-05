 package com.mingsoft.parser.impl.general;

 import com.mingsoft.parser.IParser;






































 public class GlobalSkinUrlParser
   extends IParser
 {
   private static final String GLOBAL_SKIN = "\\{ms:globalskin.url/\\}";

   public GlobalSkinUrlParser(String htmlContent, String newContent) {
     this.htmlCotent = htmlContent;
     this.newCotent = newContent;
   }




   public String parse() { return replaceAll("\\{ms:globalskin.url/\\}"); }
 }


