 package net.mingsoft.basic.entity;

 import net.mingsoft.base.entity.BaseEntity;



































 public class FileEntity
   extends BaseEntity
 {
   private static final long serialVersionUID = 1546046336900L;
   private Integer categoryId;
   private Integer appId;
   private String fileName;
   private String fileUrl;
   private Integer fileSize;
   private String fileJson;
   private String fileType;
   private String isChild;

   public void setCategoryId(Integer categoryId) { this.categoryId = categoryId; }






   public Integer getCategoryId() { return this.categoryId; }





   public void setAppId(Integer appId) { this.appId = appId; }






   public Integer getAppId() { return this.appId; }





   public void setFileName(String fileName) { this.fileName = fileName; }






   public String getFileName() { return this.fileName; }





   public void setFileUrl(String fileUrl) { this.fileUrl = fileUrl; }






   public String getFileUrl() { return this.fileUrl; }





   public void setFileSize(Integer fileSize) { this.fileSize = fileSize; }






   public Integer getFileSize() { return this.fileSize; }





   public void setFileJson(String fileJson) { this.fileJson = fileJson; }






   public String getFileJson() { return this.fileJson; }





   public void setFileType(String fileType) { this.fileType = fileType; }






   public String getFileType() { return this.fileType; }





   public void setIsChild(String isChild) { this.isChild = isChild; }






   public String getIsChild() { return this.isChild; }
 }


/* Location:              D:\User\Maven\repository\net\mingsoft\ms-basic\1.0.16\ms-basic-1.0.16.jar!\net\mingsoft\basic\entity\FileEntity.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */