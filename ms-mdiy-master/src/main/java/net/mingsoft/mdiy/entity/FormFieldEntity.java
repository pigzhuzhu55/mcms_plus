 package net.mingsoft.mdiy.entity;

 import net.mingsoft.base.constant.e.TableCloumnEnum;
 import net.mingsoft.base.entity.BaseEntity;
 import net.mingsoft.mdiy.constant.e.DiyFormFieldEnum;











































 public class FormFieldEntity
   extends BaseEntity
 {
   private String diyFormFieldDefault;
   private String diyFormFieldFieldName;
   private int diyFormFieldFormId;
   private int diyFormFieldId;
   private int diyFormFieldIsNull;
   private int diyFormFieldSort;
   private String diyFormFieldTipsName;
   private int diyFormFieldLength = 1;





   private int diyFormFieldType;





   public int getDiyFormFieldLength() { return this.diyFormFieldLength; }



   public void setDiyFormFieldLength(int diyFormFieldLength) { this.diyFormFieldLength = diyFormFieldLength; }


   public String getDiyFormFieldDefault() {
     if ("0".equals(this.diyFormFieldDefault) || "null".equals(this.diyFormFieldDefault)) {
       if (this.diyFormFieldType == DiyFormFieldEnum.DATE.toInt() || this.diyFormFieldType == DiyFormFieldEnum.TEXTAREA
         .toInt())
         return "null";
       if (this.diyFormFieldType == DiyFormFieldEnum.FLOAT.toInt() || this.diyFormFieldType == DiyFormFieldEnum.INT
         .toInt()) {
         return "0";
       }
     } else {
       return this.diyFormFieldDefault;
     }

     return this.diyFormFieldDefault;
   }


   public String getDiyFormFieldFieldName() { return this.diyFormFieldFieldName.toUpperCase(); }



   public int getDiyFormFieldFormId() { return this.diyFormFieldFormId; }



   public int getDiyFormFieldId() { return this.diyFormFieldId; }



   public int getDiyFormFieldIsNull() { return this.diyFormFieldIsNull; }



   public int getDiyFormFieldSort() { return this.diyFormFieldSort; }



   public String getDiyFormFieldTipsName() { return this.diyFormFieldTipsName; }




   public int getDiyFormFieldType() { return this.diyFormFieldType; }



   public String getDiyFormFieldColumnType() {
     if (getDiyFormFieldType() == DiyFormFieldEnum.DATE.toInt())
       return TableCloumnEnum.DATETIME.toString();
     if (getDiyFormFieldType() == DiyFormFieldEnum.FLOAT.toInt())
       return TableCloumnEnum.FLOAT.toString() + "(11)";
     if (getDiyFormFieldType() == DiyFormFieldEnum.INT.toInt())
       return TableCloumnEnum.INT.toString() + "(11)";
     if (getDiyFormFieldType() == DiyFormFieldEnum.TEXTAREA.toInt()) {
       return TableCloumnEnum.TEXT.toString();
     }
     return TableCloumnEnum.VARCHAR.toString() + "(100)";
   }





   public void setDiyFormFieldDefault(String diyFormFieldDefault) { this.diyFormFieldDefault = diyFormFieldDefault; }


   public void setDiyFormFieldFieldName(String diyFormFieldFieldName) { this.diyFormFieldFieldName = diyFormFieldFieldName; }



   public void setDiyFormFieldFormId(int diyFormFieldFormId) { this.diyFormFieldFormId = diyFormFieldFormId; }



   public void setDiyFormFieldId(int diyFormFieldId) { this.diyFormFieldId = diyFormFieldId; }



   public void setDiyFormFieldIsNull(int diyFormFieldIsNull) { this.diyFormFieldIsNull = diyFormFieldIsNull; }



   public void setDiyFormFieldSort(int diyFormFieldSort) { this.diyFormFieldSort = diyFormFieldSort; }



   public void setDiyFormFieldTipsName(String diyFormFieldTipsName) { this.diyFormFieldTipsName = diyFormFieldTipsName; }



   public void setDiyFormFieldType(DiyFormFieldEnum diyFormFieldType) { this.diyFormFieldType = diyFormFieldType.toInt(); }



   public void setDiyFormFieldType(int diyFormFieldType) { this.diyFormFieldType = diyFormFieldType; }
 }


