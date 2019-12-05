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
/*    */ public class GlobalLogoParser
/*    */   extends IParser
/*    */ {
/*    */   private static final String GLOBAL_LOGO = "\\{ms:global.logo/\\}";
/*    */   
/*    */   public GlobalLogoParser(String htmlContent, String newContent) {
/* 47 */     this.htmlCotent = htmlContent;
/* 48 */     this.newCotent = newContent;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 54 */   public String parse() { return replaceAll("\\{ms:global.logo/\\}"); }
/*    */ }


/* Location:              D:\User\Maven\repository\net\mingsoft\ms-parser\1.0.0\ms-parser-1.0.0.jar!\com\mingsoft\parser\impl\general\GlobalLogoParser.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.2
 */