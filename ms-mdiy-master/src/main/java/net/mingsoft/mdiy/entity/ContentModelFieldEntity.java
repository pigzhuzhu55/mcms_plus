 package net.mingsoft.mdiy.entity;

 import net.mingsoft.base.constant.e.TableCloumnEnum;
 import net.mingsoft.base.entity.BaseEntity;
 import net.mingsoft.mdiy.constant.e.ContentModelFieldEnum;
 import net.mingsoft.mdiy.constant.e.FieldSearchEnum;


















































 public class ContentModelFieldEntity
   extends BaseEntity
 {
   private int fieldId;
   private String fieldTipsName;
   private String fieldFieldName;
   private int fieldType;
   private String fieldTypeString;
   private String fieldDefault;
   private int fieldCmid;
   private int fieldIsNull;
   private int fieldIsSearch;
   private int fieldLength = 1;


   public String getFieldTypeString() { return ContentModelFieldEnum.get(getFieldType()).toString(); }



   public void setFieldTypeString(String fieldTypeString) { this.fieldTypeString = fieldTypeString; }



   public int getFieldLength() { return this.fieldLength; }


   public void setFieldLength(int fieldLength) {
     if (this.fieldLength <= 0) {
       this.fieldLength = 11;
     } else {
       this.fieldLength = fieldLength;
     }
   }






   public int getFieldIsSearch() { return this.fieldIsSearch; }







   @Deprecated
   public void setFieldIsSearch(int fieldIsSearch) { this.fieldIsSearch = fieldIsSearch; }



   public void setFieldIsSearch(FieldSearchEnum fieldIsSearch) { this.fieldIsSearch = fieldIsSearch.toInt(); }








   public int getFieldId() { return this.fieldId; }








   public void setFieldId(int fieldId) { this.fieldId = fieldId; }








   public String getFieldTipsName() { return this.fieldTipsName; }








   public void setFieldTipsName(String fieldTipsName) { this.fieldTipsName = fieldTipsName; }








   public String getFieldFieldName() { return this.fieldFieldName; }








   public void setFieldFieldName(String fieldFieldName) { this.fieldFieldName = fieldFieldName; }








   public int getFieldType() { return this.fieldType; }













   public String getFieldColumnType() {
     if (getFieldType() == ContentModelFieldEnum.DATE.toInt())
       return TableCloumnEnum.DATETIME.toString();
     if (getFieldType() == ContentModelFieldEnum.FLOAT.toInt())
       return TableCloumnEnum.FLOAT.toString() + "(10)";
     if (getFieldType() == ContentModelFieldEnum.INT.toInt())
       return TableCloumnEnum.INT.toString() + "(11)";
     if (getFieldType() == ContentModelFieldEnum.HTML.toInt()) {
       return TableCloumnEnum.TEXT.toString();
     }
     return TableCloumnEnum.VARCHAR.toString() + "(1000)";
   }









   public void setFieldType(int fieldType) { this.fieldType = fieldType; }








   public int getFieldCmid() { return this.fieldCmid; }








   public void setFieldCmid(int fieldCmid) { this.fieldCmid = fieldCmid; }







   public String getFieldDefault() {
     if (this.fieldType == ContentModelFieldEnum.DATE.toInt() || this.fieldType == ContentModelFieldEnum.HTML
       .toInt()) {
       return null;
     }

     if (this.fieldDefault == null || this.fieldDefault.length() == 0) {
       return null;
     }
     return this.fieldDefault;
   }







   public void setFieldDefault(String fieldDefault) { this.fieldDefault = fieldDefault; }








   public int getFieldIsNull() { return this.fieldIsNull; }








   public void setFieldIsNull(int fieldIsNull) { this.fieldIsNull = fieldIsNull; }
 }


