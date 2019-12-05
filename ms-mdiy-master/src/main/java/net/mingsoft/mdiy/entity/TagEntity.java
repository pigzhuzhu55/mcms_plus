 package net.mingsoft.mdiy.entity;

 import net.mingsoft.base.entity.BaseEntity;




















 public class TagEntity
   extends BaseEntity
 {
   private static final long serialVersionUID = 1540341874663L;
   private String tagName;
   private int tagType;
   private String tagDescription;

   public void setTagName(String tagName) { this.tagName = tagName; }






   public String getTagName() { return this.tagName; }





   public void setTagType(int tagType) { this.tagType = tagType; }






   public int getTagType() { return this.tagType; }





   public void setTagDescription(String tagDescription) { this.tagDescription = tagDescription; }






   public String getTagDescription() { return this.tagDescription; }
 }


