 package net.mingsoft.basic.entity;

 import net.mingsoft.base.constant.e.BaseEnum;
 import org.apache.commons.lang3.StringUtils;



















































































































 public class ColumnEntity
   extends CategoryEntity
 {
   @Deprecated
   public static final int COLUMN_TYPE_LIST = 1;
   @Deprecated
   public static final int COLUMN_TYPE_COVER = 2;
   @Deprecated
   public static final int COLUMN_TYPE_URL = 3;
   private String columnKeyword;
   private String columnDescrip;
   private int columnType;
   private String columnDiyUrl;
   private String columnUrl;
   private String columnListUrl;
   private int columnContentModelId;
   private String columnFlag;
   private String columnPath;

   public String getColumnDescrip() { return this.columnDescrip; }







   public String getColumnKeyword() { return this.columnKeyword; }







   public String getColumnListUrl() { return this.columnListUrl; }


   public String getColumnPath() {
     if (StringUtils.isEmpty(this.columnPath)) {
       return this.columnPath;
     }
     return this.columnPath.replace("\\", "/");
   }






   public int getColumnContentModelId() { return this.columnContentModelId; }









   public int getColumnType() { return this.columnType; }







   public String getColumnUrl() { return this.columnUrl; }







   public String getColumnDiyUrl() { return this.columnDiyUrl; }






   public void setColumnDiyUrl(String columnDiyUrl) { this.columnDiyUrl = columnDiyUrl; }







   public void setColumnDescrip(String columnDescrip) { this.columnDescrip = columnDescrip; }







   public void setColumnKeyword(String columnKeyword) { this.columnKeyword = columnKeyword; }







   public void setColumnListUrl(String columnListUrl) { this.columnListUrl = columnListUrl; }



   public void setColumnPath(String columnPath) { this.columnPath = columnPath; }







   public void setColumnContentModelId(int columnContentModelId) { this.columnContentModelId = columnContentModelId; }








   @Deprecated
   public void setColumnType(int columnType) { this.columnType = columnType; }







   public void setColumnUrl(String columnUrl) { this.columnUrl = columnUrl; }






   public String getColumnFlag() { return this.columnFlag; }






   public void setColumnFlag(String columnFlag) { this.columnFlag = columnFlag; }



   public enum ColumnTypeEnum
     implements BaseEnum
   {
     COLUMN_TYPE_LIST("1"),



     COLUMN_TYPE_COVER("2"),



     COLUMN_TYPE_URL("3");
     private Object code;

     ColumnTypeEnum(Object code) { this.code = code; }






     public int toInt() { return Integer.valueOf(this.code + "").intValue(); }
   }
 }


