 package net.mingsoft.mdiy.entity;

 import com.fasterxml.jackson.annotation.JsonFormat;
 import java.util.Date;
 import net.mingsoft.base.entity.BaseEntity;
 import org.springframework.format.annotation.DateTimeFormat;


















































 public class TableFiledEntity
   extends BaseEntity
 {
   private static final long serialVersionUID = 1543309653139L;
   private Integer tableId;
   private Integer tfSort;
   private String tfName;
   private String tfType;
   private String tfDefault;
   private String tfDescription;
   private Integer tfUnique;
   private Integer tfRequired;
   private String tfConfig;
   private String tfHelp;
   @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
   @DateTimeFormat(pattern = "yyyy-MM-dd")
   private Date createDate;
   @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
   @DateTimeFormat(pattern = "yyyy-MM-dd")
   private Date updateDate;

   public void setTableId(Integer tableId) { this.tableId = tableId; }






   public Integer getTableId() { return this.tableId; }





   public void setTfName(String tfName) { this.tfName = tfName; }






   public String getTfName() { return this.tfName; }





   public void setTfType(String tfType) { this.tfType = tfType; }






   public String getTfType() { return this.tfType; }





   public void setTfDefault(String tfDefault) { this.tfDefault = tfDefault; }






   public String getTfDefault() { return this.tfDefault; }





   public void setTfDescription(String tfDescription) { this.tfDescription = tfDescription; }






   public String getTfDescription() { return this.tfDescription; }





   public void setTfUnique(Integer tfUnique) { this.tfUnique = tfUnique; }






   public Integer getTfUnique() { return this.tfUnique; }





   public void setTfRequired(Integer tfRequired) { this.tfRequired = tfRequired; }






   public Integer getTfRequired() { return this.tfRequired; }





   public void setTfConfig(String tfConfig) { this.tfConfig = tfConfig; }






   public String getTfConfig() { return this.tfConfig; }





   public void setTfHelp(String tfHelp) { this.tfHelp = tfHelp; }






   public String getTfHelp() { return this.tfHelp; }





   public void setCreateDate(Date createDate) { this.createDate = createDate; }






   public Date getCreateDate() { return this.createDate; }





   public void setUpdateDate(Date updateDate) { this.updateDate = updateDate; }






   public Date getUpdateDate() { return this.updateDate; }



   public Integer getTfSort() { return this.tfSort; }



   public void setTfSort(Integer tfSort) { this.tfSort = tfSort; }
 }


