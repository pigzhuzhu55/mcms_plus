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
/*    */ public class GlobalHostParser
/*    */   extends IParser
/*    */ {
/*    */   private static final String GLOBAL_HOST = "\\{ms:global.host/\\}";
/*    */   
/*    */   public GlobalHostParser(String htmlContent, String newContent) {
/* 66 */     this.htmlCotent = htmlContent;
/* 67 */     this.newCotent = newContent;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 72 */   public String parse() { return replaceAll("\\{ms:global.host/\\}"); }
/*    */ }


/* Location:              D:\User\Maven\repository\net\mingsoft\ms-parser\1.0.0\ms-parser-1.0.0.jar!\com\mingsoft\parser\impl\general\GlobalHostParser.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.2
 */