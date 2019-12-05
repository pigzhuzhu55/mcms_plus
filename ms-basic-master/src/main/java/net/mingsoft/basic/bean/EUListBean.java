 package net.mingsoft.basic.bean;

 import java.util.List;

































 public class EUListBean
 {
   private int total;
   private List rows;

   public EUListBean(List rows, int total) {
     this.total = total;
     this.rows = rows;
   }



   public int getTotal() { return this.total; }




   public void setTotal(int total) { this.total = total; }




   public List getRows() { return this.rows; }




   public void setRows(List rows) { this.rows = rows; }
 }


