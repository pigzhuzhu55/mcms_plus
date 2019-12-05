 package net.mingsoft.basic.elasticsearch.bean;

 import org.apache.commons.lang3.StringUtils;






























 public class SearchBean
 {
   private int pageNo = 1;

   private int pageSize = 20;

   private String orderBy = "id";

   private String order = "desc";


   private String keyword;


   public int getPageNo() { return this.pageNo; }


   public void setPageNo(int pageNo) {
     if (pageNo <= 1) {
       pageNo = 1;
     }
     this.pageNo = pageNo;
   }


   public int getPageSize() { return this.pageSize; }



   public void setPageSize(int pageSize) { this.pageSize = pageSize; }



   public String getOrderBy() { return this.orderBy; }


   public void setOrderBy(String orderBy) {
     if (StringUtils.isBlank(orderBy)) {
       orderBy = "id";
     }
     this.orderBy = orderBy;
   }


   public String getOrder() { return this.order; }



   public void setOrder(String order) {
     if (StringUtils.isBlank(order)) {
       order = "desc";
     }
     this.order = order;
   }


   public String getKeyword() { return this.keyword; }



   public void setKeyword(String keyword) { this.keyword = keyword; }
 }


