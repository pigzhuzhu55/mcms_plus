 package net.mingsoft.base.constant.e;

































 public enum DeleteEnum
   implements BaseEnum
 {
   DEL(1, "已删除"),




   NOTDEL(0, "正常");



   private String code;


   private int id;



   DeleteEnum(int id, String code) {
     this.code = code;
     this.id = id;
   }



   public int toInt() { return this.id; }




   public String toString() { return this.code.toString(); }
 }


