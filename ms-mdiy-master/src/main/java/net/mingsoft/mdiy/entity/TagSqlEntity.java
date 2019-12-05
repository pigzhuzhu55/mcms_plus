 package net.mingsoft.mdiy.entity;

 import net.mingsoft.base.entity.BaseEntity;



























 public class TagSqlEntity
   extends BaseEntity
 {
   private static final long serialVersionUID = 1543203721085L;
   private Integer tagId;
   private String tagSql;
   private String sort;

   public void setTagId(Integer tagId) { this.tagId = tagId; }






   public Integer getTagId() { return this.tagId; }





   public void setTagSql(String tagSql) { this.tagSql = tagSql; }






   public String getTagSql() { return this.tagSql; }





   public void setSort(String sort) { this.sort = sort; }






   public String getSort() { return this.sort; }
 }


