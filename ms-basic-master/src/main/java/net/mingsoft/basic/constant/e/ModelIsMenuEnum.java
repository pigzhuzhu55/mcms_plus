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


/* Location:              D:\User\Maven\repository\net\mingsoft\ms-basic\1.0.16\ms-basic-1.0.16.jar!\net\mingsoft\basic\constant\e\ModelIsMenuEnum.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */