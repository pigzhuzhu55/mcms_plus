 package net.mingsoft.basic.constant.e;

 import net.mingsoft.base.constant.e.BaseEnum;











 public enum ModelIsMenuEnum
   implements BaseEnum
 {
   MODEL_NOTMENU(1, "否"),




   MODEL_MEUN(0, "是");



   private String code;


   private int id;



   ModelIsMenuEnum(int id, String code) {
     this.code = code;
     this.id = id;
   }



   public int toInt() { return this.id; }




   public String toString() { return this.code.toString(); }
 }


