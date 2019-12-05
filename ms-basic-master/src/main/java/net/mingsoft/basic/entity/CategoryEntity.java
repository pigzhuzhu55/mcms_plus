 package net.mingsoft.basic.entity;

 import com.alibaba.fastjson.annotation.JSONField;
 import com.fasterxml.jackson.annotation.JsonFormat;
 import java.util.ArrayList;
 import java.util.Date;
 import java.util.Iterator;
 import java.util.List;
 import org.springframework.format.annotation.DateTimeFormat;




































































































 public class CategoryEntity
   extends BaseEntity
 {
   private int categoryAppId;
   private int categoryCategoryId;
   private int categoryCreateBy;
   @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
   @JSONField(format = "yyyy-MM-dd HH:mm:ss")
   @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
   private Date categoryCreateDate;
   @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
   @JSONField(format = "yyyy-MM-dd HH:mm:ss")
   @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
   private Date categoryDateTime;
   private int categoryDel;
   private String categoryDescription;
   private int categoryDictId;
   private int categoryId;
   private int categoryLevel;
   private int categoryManagerId;
   private int categoryModelId;
   private String categoryParentId;
   private String categorySmallImg;
   private int categorySort;
   private String categoryTitle;
   private int categoryUpdateBy;
   private Date categoryUpdateDate;
   private List<CategoryEntity> childrenCategoryList;

   public CategoryEntity() {}

   public CategoryEntity(int appId, int modelId) {
     this.categoryAppId = appId;
     this.categoryModelId = modelId;
   }

   public CategoryEntity(int categoryId, String categoryTitle) {
     this.categoryId = categoryId;
     this.categoryTitle = categoryTitle;
   }






   public int[] getAllCategroyChildrenIds() {
     List<CategoryEntity> categoryList = new ArrayList<>();
     categoryList = getChilden(categoryList, this);
     int[] categoryIds = new int[categoryList.size()];
     int i = 0;
     for (CategoryEntity category : categoryList) {
       categoryIds[i++] = category.getCategoryId();
     }
     return categoryIds;
   }






   public List<CategoryEntity> getAllCategroyChildrenList() {
     List<CategoryEntity> categoryList = new ArrayList<>();
     return getChilden(categoryList, this);
   }


   public int getCategoryAppId() { return this.categoryAppId; }



   public int getCategoryCategoryId() { return this.categoryCategoryId; }



   public int getCategoryCreateBy() { return this.categoryCreateBy; }



   public Date getCategoryCreateDate() { return this.categoryCreateDate; }



   public Date getCategoryDateTime() { return this.categoryDateTime; }



   public int getCategoryDel() { return this.categoryDel; }



   public String getCategoryDescription() { return this.categoryDescription; }



   public int getCategoryDictId() { return this.categoryDictId; }



   public int getCategoryId() { return this.categoryId; }



   public int getCategoryLevel() { return this.categoryLevel; }



   public int getCategoryManagerId() { return this.categoryManagerId; }



   public int getCategoryModelId() { return this.categoryModelId; }



   public String getCategoryParentId() { return this.categoryParentId; }



   public String getCategorySmallImg() { return this.categorySmallImg; }



   public int getCategorySort() { return this.categorySort; }



   public String getCategoryTitle() { return this.categoryTitle; }



   public int getCategoryUpdateBy() { return this.categoryUpdateBy; }



   public Date getCategoryUpdateDate() { return this.categoryUpdateDate; }











   private List<CategoryEntity> getChilden(List<CategoryEntity> categoryList, CategoryEntity category) {
     categoryList.add(category);
     if (category.getChildrenCategoryList() != null) {
       categoryList.addAll(category.getChildrenCategoryList());
       Iterator<CategoryEntity> iterator = category.getChildrenCategoryList().iterator(); if (iterator.hasNext()) { CategoryEntity _category = iterator.next();
         return getChilden(categoryList, _category); }

     }
     return categoryList;
   }

   public List<CategoryEntity> getChildrenCategoryList() {
     if (this.childrenCategoryList == null) {
       this.childrenCategoryList = new ArrayList<>();
     }
     return this.childrenCategoryList;
   }


   public void setCategoryAppId(int categoryAppId) { this.categoryAppId = categoryAppId; }



   public void setCategoryCategoryId(int categoryCategoryId) { this.categoryCategoryId = categoryCategoryId; }



   public void setCategoryCreateBy(int categoryCreateBy) { this.categoryCreateBy = categoryCreateBy; }



   public void setCategoryCreateDate(Date categoryCreateDate) { this.categoryCreateDate = categoryCreateDate; }



   public void setCategoryDateTime(Date categoryDateTime) { this.categoryDateTime = categoryDateTime; }



   public void setCategoryDel(int categoryDel) { this.categoryDel = categoryDel; }



   public void setCategoryDescription(String categoryDescription) { this.categoryDescription = categoryDescription; }



   public void setCategoryDictId(int categoryDictId) { this.categoryDictId = categoryDictId; }



   public void setCategoryId(int categoryId) { this.categoryId = categoryId; }



   public void setCategoryLevel(int categoryLevel) { this.categoryLevel = categoryLevel; }



   public void setCategoryManagerId(int categoryManagerId) { this.categoryManagerId = categoryManagerId; }



   public void setCategoryModelId(int categoryModelId) { this.categoryModelId = categoryModelId; }



   public void setCategoryParentId(String categoryParentId) { this.categoryParentId = categoryParentId; }



   public void setCategorySmallImg(String categorySmallImg) { this.categorySmallImg = categorySmallImg; }



   public void setCategorySort(int categorySort) { this.categorySort = categorySort; }



   public void setCategoryTitle(String categoryTitle) { this.categoryTitle = categoryTitle; }



   public void setCategoryUpdateBy(int categoryUpdateBy) { this.categoryUpdateBy = categoryUpdateBy; }



   public void setCategoryUpdateDate(Date categoryUpdateDate) { this.categoryUpdateDate = categoryUpdateDate; }



   public void setChildrenCategoryList(List<CategoryEntity> childrenCategoryList) { this.childrenCategoryList = childrenCategoryList; }
 }


