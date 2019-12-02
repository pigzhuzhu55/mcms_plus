 package net.mingsoft.basic.biz.impl;

 import java.io.Serializable;
 import java.util.ArrayList;
 import java.util.List;
 import net.mingsoft.base.biz.impl.BaseBizImpl;
 import net.mingsoft.base.dao.IBaseDao;
 import net.mingsoft.base.entity.BaseEntity;
 import net.mingsoft.basic.biz.ICategoryBiz;
 import net.mingsoft.basic.dao.ICategoryDao;
 import net.mingsoft.basic.entity.CategoryEntity;
 import org.apache.commons.lang3.StringUtils;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.stereotype.Service;




































 @Service("categoryBiz")
 public class CategoryBizImpl
   extends BaseBizImpl
   implements ICategoryBiz
 {
   private ICategoryDao categoryDao;

   public int count(CategoryEntity category) { return this.categoryDao.count(category); }




   public void deleteCategory(int categoryId) {
     CategoryEntity category = (CategoryEntity)this.categoryDao.getEntity(Integer.valueOf(categoryId));

     if (category != null) {

       category.setCategoryParentId(null);
       List<CategoryEntity> childrenList = this.categoryDao.queryChildren(category);
       int[] ids = new int[childrenList.size()];
       for (int i = 0; i < childrenList.size(); i++)
       {
         ids[i] = ((CategoryEntity)childrenList.get(i)).getCategoryId();
       }
       this.categoryDao.delete(ids);
       this.categoryDao.deleteEntity(categoryId);
       deleteEntity(categoryId);
     }
   }






   public ICategoryDao getCategoryDao() { return this.categoryDao; }










   protected IBaseDao getDao() { return (IBaseDao)this.categoryDao; }





   public BaseEntity getEntity(BaseEntity entity) {
     CategoryEntity category = (CategoryEntity)super.getEntity(entity);
     if (category == null) {
       return null;
     }

     List<CategoryEntity> childs = this.categoryDao.query((BaseEntity)category);
     resetChildren(category, childs);
     return (BaseEntity)category;
   }





   public BaseEntity getEntity(int id) {
     CategoryEntity category = (CategoryEntity)super.getEntity(id);
     if (category != null) {

       List<CategoryEntity> childs = this.categoryDao.queryChildren(category);
       resetChildren(category, childs);
     }
     return (BaseEntity)category;
   }



   public List query(BaseEntity entity) {
     List<CategoryEntity> list = super.query(entity);
     List<CategoryEntity> childList = new ArrayList();
     childList.addAll(list);
     for (int i = 0; i < list.size(); i++) {
       CategoryEntity c = list.get(i);
       resetChildren(c, childList);
     }
     return list;
   }




   public List<CategoryEntity> queryBatchCategoryById(List<Integer> listId) { return this.categoryDao.queryBatchCategoryById(listId); }





   public List<CategoryEntity> queryByAppIdOrModelId(Integer appId, Integer modelId) { return this.categoryDao.queryByAppIdOrModelId(appId, modelId); }










   public List queryByDictId(CategoryEntity category) { return this.categoryDao.queryByDictId(category); }







   public List<CategoryEntity> queryChildrenCategory(int categoryId, int appId, int modelId) {
     CategoryEntity category = new CategoryEntity();
     category.setCategoryAppId(appId);
     category.setCategoryModelId(modelId);
     category.setCategoryId(categoryId);

     return this.categoryDao.queryChildren(category);
   }



   public synchronized int[] queryChildrenCategoryIds(int categoryId, int appId, int modelId) {
     CategoryEntity category = new CategoryEntity();
     category.setCategoryAppId(appId);
     category.setCategoryModelId(modelId);
     category.setCategoryId(categoryId);
     List<CategoryEntity> list = this.categoryDao.queryChildren(category);
     int[] ids = new int[list.size()];
     for (int i = 0; i < list.size(); i++) {
       CategoryEntity _category = list.get(i);
       ids[i] = _category.getCategoryId();
     }
     return ids;
   }




   public List<CategoryEntity> queryChilds(CategoryEntity category) { return this.categoryDao.queryChilds(category); }


   private void resetChildren(CategoryEntity category, List<CategoryEntity> chrildrenCategoryList) {
     for (CategoryEntity c : chrildrenCategoryList) {
       if (c.getCategoryCategoryId() == category.getCategoryId() && !category.getChildrenCategoryList().contains(c)) {
         category.getChildrenCategoryList().add(c);
         resetChildren(c, chrildrenCategoryList);
       }
     }
   }


   public int saveCategory(CategoryEntity categoryEntity) {
     if (categoryEntity.getCategoryCategoryId() > 0) {
       CategoryEntity category = (CategoryEntity)this.categoryDao.getEntity(Integer.valueOf(categoryEntity.getCategoryCategoryId()));
       if (StringUtils.isEmpty(category.getCategoryParentId())) {
         categoryEntity.setCategoryParentId(categoryEntity.getCategoryCategoryId() + "");
       } else {
         categoryEntity.setCategoryParentId(category.getCategoryParentId() + "," + categoryEntity.getCategoryCategoryId());
       }
     }
     this.categoryDao.saveEntity((BaseEntity)categoryEntity);
     return saveEntity((BaseEntity)categoryEntity);
   }


   @Autowired
   public void setCategoryDao(ICategoryDao categoryDao) { this.categoryDao = categoryDao; }





   public void updateCategory(CategoryEntity categoryEntity) {
     if (categoryEntity.getCategoryCategoryId() > 0) {
       CategoryEntity category = (CategoryEntity)this.categoryDao.getEntity(Integer.valueOf(categoryEntity.getCategoryCategoryId()));
       if (StringUtils.isEmpty(category.getCategoryParentId())) {
         categoryEntity.setCategoryParentId(categoryEntity.getCategoryCategoryId() + "");
       } else {
         categoryEntity.setCategoryParentId(category.getCategoryParentId() + "," + categoryEntity.getCategoryCategoryId());
       }
     }
     this.categoryDao.updateEntity((BaseEntity)categoryEntity);
     updateEntity((BaseEntity)categoryEntity);
   }
 }


/* Location:              D:\User\Maven\repository\net\mingsoft\ms-basic\1.0.16\ms-basic-1.0.16.jar!\net\mingsoft\basic\biz\impl\CategoryBizImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */