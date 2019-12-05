 package net.mingsoft.mdiy.entity;

 import com.fasterxml.jackson.annotation.JsonFormat;
 import java.util.Date;
 import net.mingsoft.base.entity.BaseEntity;
 import org.springframework.format.annotation.DateTimeFormat;

































 public class TableEntity
   extends BaseEntity
 {
   private static final long serialVersionUID = 1543309653613L;
   private Integer appId;
   private String tableName;
   private String tableMaster;
   private String tableMasterId;
   @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
   @DateTimeFormat(pattern = "yyyy-MM-dd")
   private Date createDate;
   @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
   @DateTimeFormat(pattern = "yyyy-MM-dd")
   private Date updateDate;

   public void setAppId(Integer appId) { this.appId = appId; }






   public Integer getAppId() { return this.appId; }





   public void setTableName(String tableName) { this.tableName = tableName; }






   public String getTableName() { return this.tableName; }





   public void setTableMaster(String tableMaster) { this.tableMaster = tableMaster; }






   public String getTableMaster() { return this.tableMaster; }





   public void setTableMasterId(String tableMasterId) { this.tableMasterId = tableMasterId; }






   public String getTableMasterId() { return this.tableMasterId; }





   public void setCreateDate(Date createDate) { this.createDate = createDate; }






   public Date getCreateDate() { return this.createDate; }





   public void setUpdateDate(Date updateDate) { this.updateDate = updateDate; }






   public Date getUpdateDate() { return this.updateDate; }
 }


