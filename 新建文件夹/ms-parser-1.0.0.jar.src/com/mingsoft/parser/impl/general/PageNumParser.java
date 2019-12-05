/*    */ package com.mingsoft.parser.impl.general;
/*    */ 
/*    */ import com.mingsoft.parser.IParser;
/*    */ import com.mingsoft.util.PageUtil;
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
/*    */ public class PageNumParser
/*    */   extends IParser
/*    */ {
/*    */   private PageUtil page;
/*    */   private static final String PAGE_TOTAL = "\\{ms:page.total/\\}";
/*    */   private static final String PAGE_CUR = "\\{ms:page.cur/\\}";
/*    */   private static final String PAGE_RCOUNT = "\\{ms:page.rcount/\\}";
/*    */   
/*    */   public PageNumParser(String htmlContent, PageUtil page) {
/* 63 */     this.htmlCotent = htmlContent;
/* 64 */     this.page = page;
/*    */   }
/*    */ 
/*    */   
/*    */   public String parse() {
/* 69 */     if (this.page != null) {
/*    */ 
/*    */       
/* 72 */       this.htmlCotent = replaceRegex("\\{ms:page.total/\\}", this.page.getPageCount());
/*    */       
/* 74 */       this.htmlCotent = replaceRegex("\\{ms:page.cur/\\}", this.page.getPageNo() + 1);
/*    */       
/* 76 */       this.htmlCotent = replaceRegex("\\{ms:page.rcount/\\}", this.page.getRecordCound());
/* 77 */       return this.htmlCotent;
/*    */     } 
/* 79 */     return this.htmlCotent;
/*    */   }
/*    */ 
/*    */   
/*    */   public String replaceRegex(String regex, int num) {
/* 84 */     this.newCotent = Integer.toString(num);
/* 85 */     String html = replaceAll(regex);
/* 86 */     return html;
/*    */   }
/*    */ }


/* Location:              D:\User\Maven\repository\net\mingsoft\ms-parser\1.0.0\ms-parser-1.0.0.jar!\com\mingsoft\parser\impl\general\PageNumParser.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.2
 */