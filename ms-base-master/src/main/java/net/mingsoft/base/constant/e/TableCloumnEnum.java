 package net.mingsoft.base.constant.e;




























 public enum TableCloumnEnum
   implements BaseEnum
 {
   VARCHAR(1, "varchar"), TEXT(3, "text"), INT(4, "int"), DATETIME(2, "datetime"), FLOAT(5, "float");
   private int id;
   private String obj;

   TableCloumnEnum(int id, String obj) {
     this.obj = obj;
     this.id = id;
   }




   public int toInt() { return this.id; }



   public String toString() { return this.obj.toString(); }
 }


