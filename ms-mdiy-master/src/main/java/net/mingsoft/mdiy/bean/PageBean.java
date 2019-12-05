 package net.mingsoft.mdiy.bean;




















 public class PageBean
 {
   private int nextId;
   private int total;
   private int size;
   private int preId;
   private int pageNo = 1;



   private String preUrl;



   private String nextUrl;



   private String indexUrl;



   private String lastUrl;



   private int searchTotal;



   public int getSearchTotal() { return this.searchTotal; }


   public void setSearchTotal(int searchTotal) { this.searchTotal = searchTotal; }


   public int getNextId() { return this.nextId; }


   public void setNextId(int nextId) { this.nextId = nextId; }


   public int getTotal() { return this.total; }


   public void setTotal(int total) { this.total = total; }


   public int getSize() { return this.size; }


   public void setSize(int size) { this.size = size; }


   public int getPreId() { return this.preId; }


   public void setPreId(int preId) { this.preId = preId; }


   public int getPageNo() { return this.pageNo; }


   public void setPageNo(int pageNo) { this.pageNo = pageNo; }


   public String getPreUrl() { return this.preUrl; }


   public void setPreUrl(String preUrl) { this.preUrl = preUrl; }


   public String getNextUrl() { return this.nextUrl; }


   public void setNextUrl(String nextUrl) { this.nextUrl = nextUrl; }


   public String getIndexUrl() { return this.indexUrl; }


   public void setIndexUrl(String indexUrl) { this.indexUrl = indexUrl; }


   public String getLastUrl() { return this.lastUrl; }


   public void setLastUrl(String lastUrl) { this.lastUrl = lastUrl; }
 }


