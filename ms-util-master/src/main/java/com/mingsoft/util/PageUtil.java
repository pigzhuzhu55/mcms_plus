 package com.mingsoft.util;













































































 @Deprecated
 public class PageUtil
 {
   protected int index;
   protected String indexUrl;
   protected int previous;
   protected String previousUrl;
   protected int next;
   protected String nextUrl;
   protected int last;
   protected String lastUrl;
   protected int recordCount;
   protected String linkUrl;
   protected int pageNo;
   protected int pageCount;

   public void setPageNo(int pageNo) { this.pageNo = pageNo; }










   protected int pageSize = 10;





   private boolean hasParams;






   public int getPageCount() { return this.pageCount; }







   public void setPageCount(int pageCount) { this.pageCount = pageCount; }






   public PageUtil(int pageSize) {
     this.pageSize = pageSize;
     this.pageNo = 0;
   }










   public PageUtil(int pageNo, int recordCount, String linkUrl) {
     this.pageNo = pageNo - 1;
     this.recordCount = recordCount;
     this.linkUrl = (linkUrl == null) ? "" : linkUrl;

     calculatePageCount();
   }










   public PageUtil(int pageNo, int recordCount) {
     this.pageNo = pageNo - 1;
     this.recordCount = recordCount;
     calculatePageCount();
   }













   public PageUtil(int pageNo, int pageSize, int recordCount, String linkUrl) {
     this(pageNo, recordCount, linkUrl);
     this.pageSize = pageSize;
     calculatePageCount();

     if (this.pageNo + 1 > this.pageCount && this.pageCount > 1) {
       this.pageNo = this.pageCount - 1;
     }
   }





   private void calculatePageCount() {
     if (this.linkUrl.indexOf("?") > 0) {
       this.hasParams = true;
     }

     if (this.recordCount == 0) {
       this.pageCount = 0;

     }
     else if (this.recordCount % this.pageSize == 0) {
       this.pageCount = this.recordCount / this.pageSize;
     } else {
       this.pageCount = this.recordCount / this.pageSize + 1;
     }
   }








   public int getIndex() { return this.index; }







   public void setIndex(int index) { this.index = index; }







   public int getPrevious() { return this.previous; }







   public void setPrevious(int previous) { this.previous = previous; }







   public int getNext() { return this.next; }







   public void setNext(int next) { this.next = next; }







   public int getLast() { return this.last; }








   public void setLast(int last) { this.last = last; }







   public int getRecordCound() { return this.recordCount; }







   public int getPageNo() { return this.pageNo; }






   public String getIndexUrl() {
     if (this.pageNo == 0) {
       this.indexUrl = "#";
     } else {
       this.indexUrl = String.valueOf(this.linkUrl) + (this.hasParams ? "&" : "?") + "pageNo=1";
     }
     return this.indexUrl;
   }





   public String getPreviousUrl() {
     if (this.pageNo == 0) {
       this.previousUrl = "#";
     } else {
       this.previousUrl = String.valueOf(this.linkUrl) + (this.hasParams ? "&" : "?") + "pageNo=" + this.pageNo;
     }
     return this.previousUrl;
   }





   public String getNextUrl() {
     if (this.pageNo == this.pageCount) {
       this.nextUrl = "#";
     }
     else if (this.pageNo + 2 > this.pageCount) {
       this.nextUrl = String.valueOf(this.linkUrl) + (this.hasParams ? "&" : "?") + "pageNo=" + this.pageCount;
     } else {
       this.nextUrl = String.valueOf(this.linkUrl) + (this.hasParams ? "&" : "?") + "pageNo=" + (this.pageNo + 2);
     }

     return this.nextUrl;
   }







   public String getLastUrl() {
     if (this.pageNo == this.pageCount) {
       this.lastUrl = "#";
     } else {
       this.lastUrl = String.valueOf(this.linkUrl) + (this.hasParams ? "&" : "?") + "pageNo=" + this.pageCount;
     }
     return this.lastUrl;
   }






   public int getPageSize() { return this.pageSize; }







   public String getLinkUrl() { return String.valueOf(this.linkUrl) + (this.hasParams ? "&" : "?"); }








   public void setLinkUrl(String linkUrl) { this.linkUrl = linkUrl; }






   public static void main(String[] args) {
     String url = "http://localhost/mswx/admin/hotel/listHotel.do?1=1";
     System.out.println(url.indexOf("?"));
   }
 }


