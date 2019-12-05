/*     */ package com.mingsoft.util;
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
/*     */ @Deprecated
/*     */ public class PageUtil
/*     */ {
/*     */   protected int index;
/*     */   protected String indexUrl;
/*     */   protected int previous;
/*     */   protected String previousUrl;
/*     */   protected int next;
/*     */   protected String nextUrl;
/*     */   protected int last;
/*     */   protected String lastUrl;
/*     */   protected int recordCount;
/*     */   protected String linkUrl;
/*     */   protected int pageNo;
/*     */   protected int pageCount;
/*     */   
/*  95 */   public void setPageNo(int pageNo) { this.pageNo = pageNo; }
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
/* 106 */   protected int pageSize = 10;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean hasParams;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 119 */   public int getPageCount() { return this.pageCount; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 127 */   public void setPageCount(int pageCount) { this.pageCount = pageCount; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PageUtil(int pageSize) {
/* 135 */     this.pageSize = pageSize;
/* 136 */     this.pageNo = 0;
/*     */   }
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
/*     */   public PageUtil(int pageNo, int recordCount, String linkUrl) {
/* 149 */     this.pageNo = pageNo - 1;
/* 150 */     this.recordCount = recordCount;
/* 151 */     this.linkUrl = (linkUrl == null) ? "" : linkUrl;
/*     */     
/* 153 */     calculatePageCount();
/*     */   }
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
/*     */   public PageUtil(int pageNo, int recordCount) {
/* 166 */     this.pageNo = pageNo - 1;
/* 167 */     this.recordCount = recordCount;
/* 168 */     calculatePageCount();
/*     */   }
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
/*     */   public PageUtil(int pageNo, int pageSize, int recordCount, String linkUrl) {
/* 184 */     this(pageNo, recordCount, linkUrl);
/* 185 */     this.pageSize = pageSize;
/* 186 */     calculatePageCount();
/*     */     
/* 188 */     if (this.pageNo + 1 > this.pageCount && this.pageCount > 1) {
/* 189 */       this.pageNo = this.pageCount - 1;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void calculatePageCount() {
/* 198 */     if (this.linkUrl.indexOf("?") > 0) {
/* 199 */       this.hasParams = true;
/*     */     }
/*     */     
/* 202 */     if (this.recordCount == 0) {
/* 203 */       this.pageCount = 0;
/*     */     
/*     */     }
/* 206 */     else if (this.recordCount % this.pageSize == 0) {
/* 207 */       this.pageCount = this.recordCount / this.pageSize;
/*     */     } else {
/* 209 */       this.pageCount = this.recordCount / this.pageSize + 1;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 220 */   public int getIndex() { return this.index; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 228 */   public void setIndex(int index) { this.index = index; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 236 */   public int getPrevious() { return this.previous; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 244 */   public void setPrevious(int previous) { this.previous = previous; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 252 */   public int getNext() { return this.next; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 260 */   public void setNext(int next) { this.next = next; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 268 */   public int getLast() { return this.last; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 277 */   public void setLast(int last) { this.last = last; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 285 */   public int getRecordCound() { return this.recordCount; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 293 */   public int getPageNo() { return this.pageNo; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getIndexUrl() {
/* 301 */     if (this.pageNo == 0) {
/* 302 */       this.indexUrl = "#";
/*     */     } else {
/* 304 */       this.indexUrl = String.valueOf(this.linkUrl) + (this.hasParams ? "&" : "?") + "pageNo=1";
/*     */     } 
/* 306 */     return this.indexUrl;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPreviousUrl() {
/* 314 */     if (this.pageNo == 0) {
/* 315 */       this.previousUrl = "#";
/*     */     } else {
/* 317 */       this.previousUrl = String.valueOf(this.linkUrl) + (this.hasParams ? "&" : "?") + "pageNo=" + this.pageNo;
/*     */     } 
/* 319 */     return this.previousUrl;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getNextUrl() {
/* 327 */     if (this.pageNo == this.pageCount) {
/* 328 */       this.nextUrl = "#";
/*     */     }
/* 330 */     else if (this.pageNo + 2 > this.pageCount) {
/* 331 */       this.nextUrl = String.valueOf(this.linkUrl) + (this.hasParams ? "&" : "?") + "pageNo=" + this.pageCount;
/*     */     } else {
/* 333 */       this.nextUrl = String.valueOf(this.linkUrl) + (this.hasParams ? "&" : "?") + "pageNo=" + (this.pageNo + 2);
/*     */     } 
/*     */     
/* 336 */     return this.nextUrl;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getLastUrl() {
/* 346 */     if (this.pageNo == this.pageCount) {
/* 347 */       this.lastUrl = "#";
/*     */     } else {
/* 349 */       this.lastUrl = String.valueOf(this.linkUrl) + (this.hasParams ? "&" : "?") + "pageNo=" + this.pageCount;
/*     */     } 
/* 351 */     return this.lastUrl;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 359 */   public int getPageSize() { return this.pageSize; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 367 */   public String getLinkUrl() { return String.valueOf(this.linkUrl) + (this.hasParams ? "&" : "?"); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 376 */   public void setLinkUrl(String linkUrl) { this.linkUrl = linkUrl; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void main(String[] args) {
/* 384 */     String url = "http://localhost/mswx/admin/hotel/listHotel.do?1=1";
/* 385 */     System.out.println(url.indexOf("?"));
/*     */   }
/*     */ }


/* Location:              D:\User\Maven\repository\net\mingsoft\ms-util\1.0.1\ms-util-1.0.1.jar!\com\mingsof\\util\PageUtil.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.2
 */