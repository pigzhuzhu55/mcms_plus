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


/* Location:              D:\User\Maven\repository\net\mingsoft\ms-basic\1.0.16\ms-basic-1.0.16.jar!\net\mingsoft\basic\bean\EUListBean.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */