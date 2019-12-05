 package net.mingsoft.base.entity;

 import com.fasterxml.jackson.annotation.JsonFormat;
 import com.fasterxml.jackson.annotation.JsonIgnore;
 import java.io.Serializable;
 import java.util.Date;
 import javax.xml.bind.annotation.XmlTransient;
 import net.mingsoft.base.constant.e.DeleteEnum;
 import org.springframework.format.annotation.DateTimeFormat;















































































 public abstract class BaseEntity
   implements Serializable
 {
   @JsonIgnore
   @XmlTransient
   protected int createBy;
   @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
   @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
   protected Date createDate;
   @JsonIgnore
   @XmlTransient
   protected int del;
   protected String id;
   protected String remarks;
   @JsonIgnore
   @XmlTransient
   protected int updateBy;
   @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
   @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
   protected Date updateDate;
   @JsonIgnore
   @XmlTransient
   protected String sqlWhere;
   @JsonIgnore
   @XmlTransient
   protected String sqlDataScope;
   @JsonIgnore
   @XmlTransient
   protected String orderBy;
   protected String order;

   public int getCreateBy() { return this.createBy; }



   public void setCreateBy(int createBy) { this.createBy = createBy; }



   public Date getCreateDate() { return this.createDate; }



   public void setCreateDate(Date createDate) { this.createDate = createDate; }



   public int getDel() { return this.del; }



   public void setDel(DeleteEnum del) { this.del = del.toInt(); }



   public void setDel(int del) { this.del = del; }



   public String getId() { return this.id; }



   public void setId(String id) { this.id = id; }



   public String getRemarks() { return this.remarks; }



   public void setRemarks(String remarks) { this.remarks = remarks; }



   public int getUpdateBy() { return this.updateBy; }



   public void setUpdateBy(int updateBy) { this.updateBy = updateBy; }



   public Date getUpdateDate() { return this.updateDate; }



   public void setUpdateDate(Date updateDate) { this.updateDate = updateDate; }



   @JsonIgnore
   @XmlTransient
   public String getSqlWhere() { return this.sqlWhere; }



   public void setSqlWhere(String sqlWhere) { this.sqlWhere = sqlWhere; }



   public String getOrderBy() { return this.orderBy; }



   public void setOrderBy(String orderBy) { this.orderBy = orderBy; }




   public String getSqlDataScope() { return this.sqlDataScope; }



   public void setSqlDataScope(String sqlDataScope) { this.sqlDataScope = sqlDataScope; }



   public String getOrder() { return this.order; }



   public void setOrder(String order) { this.order = order; }
 }


