 package net.mingsoft.mdiy.entity;

 import net.mingsoft.basic.entity.BaseEntity;












































 public class DictEntity
   extends BaseEntity
 {
   private static final long serialVersionUID = 1502518956351L;
   private Integer dictId;
   private Integer dictAppId;
   private String dictValue;
   private String dictLabel;
   private String dictType;
   private String dictDescription;
   private Integer dictSort;
   private String dictParentId;
   private String isChild;
   private String dictRemarks;

   public void setDictId(Integer dictId) { this.dictId = dictId; }






   public Integer getDictId() { return this.dictId; }






   public void setDictAppId(Integer dictAppId) { this.dictAppId = dictAppId; }






   public Integer getDictAppId() { return this.dictAppId; }






   public void setDictValue(String dictValue) { this.dictValue = dictValue; }






   public String getDictValue() { return this.dictValue; }






   public void setDictLabel(String dictLabel) { this.dictLabel = dictLabel; }






   public String getDictLabel() { return this.dictLabel; }






   public void setDictType(String dictType) { this.dictType = dictType; }






   public String getDictType() { return this.dictType; }






   public void setDictDescription(String dictDescription) { this.dictDescription = dictDescription; }






   public String getDictDescription() { return this.dictDescription; }






   public void setDictSort(Integer dictSort) { this.dictSort = dictSort; }






   public Integer getDictSort() { return this.dictSort; }






   public void setDictParentId(String dictParentId) { this.dictParentId = dictParentId; }






   public String getDictParentId() { return this.dictParentId; }







   public void setDictRemarks(String dictRemarks) { this.dictRemarks = dictRemarks; }






   public String getDictRemarks() { return this.dictRemarks; }





   public String getIsChild() { return this.isChild; }





   public void setIsChild(String isChild) { this.isChild = isChild; }
 }


