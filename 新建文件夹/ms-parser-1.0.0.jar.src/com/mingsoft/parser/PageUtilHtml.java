/*     */ package com.mingsoft.parser;
/*     */ 
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
/*     */ public class PageUtilHtml
/*     */   extends PageUtil
/*     */ {
/*  30 */   public PageUtilHtml(int pageNo, int pageSize, int recordCount, String linkUrl) { super(pageNo, pageSize, recordCount, linkUrl); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getIndexUrl() {
/*  39 */     if (this.pageNo == 0) {
/*  40 */       this.indexUrl = "#";
/*     */     } else {
/*  42 */       this.indexUrl = String.valueOf(this.linkUrl.replace("list", "")) + "index.html";
/*     */     } 
/*  44 */     return this.indexUrl;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPreviousUrl() {
/*  52 */     if (this.pageNo == 0) {
/*  53 */       this.previousUrl = "#";
/*     */     }
/*  55 */     else if (this.pageNo == 1) {
/*  56 */       this.previousUrl = String.valueOf(this.linkUrl.replace("list", "")) + "index.html";
/*     */     } else {
/*  58 */       this.previousUrl = String.valueOf(this.linkUrl) + this.pageNo + ".html";
/*     */     } 
/*     */ 
/*     */     
/*  62 */     return this.previousUrl;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getNextUrl() {
/*  69 */     if (this.pageNo == this.pageCount) {
/*  70 */       this.nextUrl = "#";
/*     */     }
/*  72 */     else if (this.pageNo + 2 > this.pageCount) {
/*  73 */       if (this.pageCount == 1) {
/*  74 */         this.nextUrl = String.valueOf(this.linkUrl.replace("list", "")) + "index.html";
/*     */       } else {
/*  76 */         this.nextUrl = String.valueOf(this.linkUrl) + this.pageCount + ".html";
/*     */       } 
/*     */     } else {
/*     */       
/*  80 */       this.nextUrl = String.valueOf(this.linkUrl) + (this.pageNo + 2) + ".html";
/*     */     } 
/*     */     
/*  83 */     return this.nextUrl;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getLastUrl() {
/*  91 */     if (this.pageNo == this.pageCount) {
/*  92 */       this.lastUrl = "#";
/*     */     }
/*  94 */     else if (this.pageCount == 1) {
/*  95 */       this.lastUrl = String.valueOf(this.linkUrl.replace("list", "")) + "index.html";
/*     */     } else {
/*  97 */       this.lastUrl = String.valueOf(this.linkUrl) + this.pageCount + ".html";
/*     */     } 
/*     */     
/* 100 */     return this.lastUrl;
/*     */   }
/*     */ }


/* Location:              D:\User\Maven\repository\net\mingsoft\ms-parser\1.0.0\ms-parser-1.0.0.jar!\com\mingsoft\parser\PageUtilHtml.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.2
 */