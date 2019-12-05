 package net.mingsoft.mdiy.constant.e;

 import net.mingsoft.base.constant.e.BaseEnum;








 public enum FieldEnum
   implements BaseEnum
 {
   TEXT("text"),



   TEXTAREA("textarea"),



   HTML("html"),



   INT("int"),



   FLOAT("float"),



   DATE("date"),



   IMAGE("image"),



   FILE("file"),



   OPTION("option"),



   RADIO("radio"),



   CHECKBOX("checkbox");


   private String text;



   FieldEnum(String text) { this.text = text; }









   public String toString() { return this.text; }






   public int toInt() { return 0; }
 }


