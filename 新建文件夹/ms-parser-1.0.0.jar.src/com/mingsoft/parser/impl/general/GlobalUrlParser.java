/*    */ package com.mingsoft.parser.impl.general;
/*    */ 
/*    */ import com.mingsoft.parser.IParser;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class GlobalUrlParser
/*    */   extends IParser
/*    */ {
/*    */   private static final String GLOBAL_URL = "\\{ms:global.url/\\}";
/*    */   
/*    */   public GlobalUrlParser(String htmlContent, String newContent) {
/* 48 */     this.htmlCotent = htmlContent;
/* 49 */     this.newCotent = newContent;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 55 */   public String parse() { return replaceAll("\\{ms:global.url/\\}"); }
/*    */ }


/* Location:              D:\User\Maven\repository\net\mingsoft\ms-parser\1.0.0\ms-parser-1.0.0.jar!\com\mingsoft\parser\impl\general\GlobalUrlParser.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.2
 */