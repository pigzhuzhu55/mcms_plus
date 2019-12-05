 package net.mingsoft.basic.bean;

 import com.github.pagehelper.PageInfo;
 import java.util.List;


































 public class ListBean
 {
   private PageInfo page;
   private List list;

   public ListBean(List list) { this.list = list; }


   public ListBean(List list, PageInfo page) {
     this.page = page;
     this.list = list;
   }


   public PageInfo getPage() { return this.page; }



   public void setPage(PageInfo page) { this.page = page; }



   public List getList() { return this.list; }



   public void setList(List list) { this.list = list; }
 }


