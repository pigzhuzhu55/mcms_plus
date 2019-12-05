 package com.mingsoft.parser;

 import com.mingsoft.util.PageUtil;























 public class PageUtilHtml
   extends PageUtil
 {
   public PageUtilHtml(int pageNo, int pageSize, int recordCount, String linkUrl) { super(pageNo, pageSize, recordCount, linkUrl); }







   public String getIndexUrl() {
     if (this.pageNo == 0) {
       this.indexUrl = "#";
     } else {
       this.indexUrl = String.valueOf(this.linkUrl.replace("list", "")) + "index.html";
     }
     return this.indexUrl;
   }





   public String getPreviousUrl() {
     if (this.pageNo == 0) {
       this.previousUrl = "#";
     }
     else if (this.pageNo == 1) {
       this.previousUrl = String.valueOf(this.linkUrl.replace("list", "")) + "index.html";
     } else {
       this.previousUrl = String.valueOf(this.linkUrl) + this.pageNo + ".html";
     }


     return this.previousUrl;
   }




   public String getNextUrl() {
     if (this.pageNo == this.pageCount) {
       this.nextUrl = "#";
     }
     else if (this.pageNo + 2 > this.pageCount) {
       if (this.pageCount == 1) {
         this.nextUrl = String.valueOf(this.linkUrl.replace("list", "")) + "index.html";
       } else {
         this.nextUrl = String.valueOf(this.linkUrl) + this.pageCount + ".html";
       }
     } else {

       this.nextUrl = String.valueOf(this.linkUrl) + (this.pageNo + 2) + ".html";
     }

     return this.nextUrl;
   }





   public String getLastUrl() {
     if (this.pageNo == this.pageCount) {
       this.lastUrl = "#";
     }
     else if (this.pageCount == 1) {
       this.lastUrl = String.valueOf(this.linkUrl.replace("list", "")) + "index.html";
     } else {
       this.lastUrl = String.valueOf(this.linkUrl) + this.pageCount + ".html";
     }

     return this.lastUrl;
   }
 }


