/*     */ package com.mingsoft.parser.impl.general;
/*     */ 
/*     */ import com.mingsoft.parser.IParser;
/*     */ import com.mingsoft.util.PageUtil;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PageParser
/*     */   extends IParser
/*     */ {
/*  49 */   private final String PAGE_INDEX = "\\{ms:page.index/\\}";
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  54 */   private final String PAGE_PRE = "\\{ms:page.pre/\\}";
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  59 */   private final String PAGE_NEXT = "\\{ms:page.next/\\}";
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  64 */   private final String PAGE_OVER = "\\{ms:page.last/\\}";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private PageUtil page;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PageParser(String htmlContent, PageUtil page) {
/*  81 */     this.htmlCotent = htmlContent;
/*  82 */     this.page = page;
/*     */   }
/*     */ 
/*     */   
/*     */   public String parse() {
/*  87 */     if (this.page != null) {
/*     */ 
/*     */       
/*  90 */       this.newCotent = this.page.getIndexUrl();
/*  91 */       String indexHtml = replaceAll("\\{ms:page.index/\\}");
/*     */       
/*  93 */       this.htmlCotent = indexHtml;
/*  94 */       this.newCotent = this.page.getPreviousUrl();
/*  95 */       String preHtml = replaceAll("\\{ms:page.pre/\\}");
/*     */       
/*  97 */       this.htmlCotent = preHtml;
/*  98 */       this.newCotent = this.page.getNextUrl();
/*  99 */       String nextHtml = replaceAll("\\{ms:page.next/\\}");
/*     */       
/* 101 */       this.htmlCotent = nextHtml;
/* 102 */       this.newCotent = this.page.getLastUrl();
/* 103 */       String traileHtml = replaceAll("\\{ms:page.last/\\}");
/* 104 */       return traileHtml;
/*     */     } 
/* 106 */     return this.htmlCotent;
/*     */   }
/*     */ }


/* Location:              D:\User\Maven\repository\net\mingsoft\ms-parser\1.0.0\ms-parser-1.0.0.jar!\com\mingsoft\parser\impl\general\PageParser.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.2
 */