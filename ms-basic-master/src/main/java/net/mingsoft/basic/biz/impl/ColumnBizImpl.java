 package net.mingsoft.basic.biz.impl;

 import java.io.File;
 import java.sql.Timestamp;
 import java.util.ArrayList;
 import java.util.List;
 import net.mingsoft.base.dao.IBaseDao;
 import net.mingsoft.base.entity.BaseEntity;
 import net.mingsoft.basic.biz.IColumnBiz;
 import net.mingsoft.basic.dao.IColumnDao;
 import net.mingsoft.basic.entity.CategoryEntity;
 import net.mingsoft.basic.entity.ColumnEntity;
 import net.mingsoft.basic.util.BasicUtil;
 import net.mingsoft.basic.util.SpringUtil;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.stereotype.Service;

































































 @Service("columnBiz")
 public class ColumnBizImpl
   extends CategoryBizImpl
   implements IColumnBiz
 {
   private IColumnDao columnDao;

   public IColumnDao getColumnDao() { return this.columnDao; }








   @Autowired
   public void setColumnDao(IColumnDao columnDao) { this.columnDao = columnDao; }




   protected IBaseDao getDao() { return (IBaseDao)this.columnDao; }










   public List<ColumnEntity> queryColumnListByWebsiteId(int columnWebsiteId) { return this.columnDao.queryColumnListByWebsiteId(columnWebsiteId); }



   public List<ColumnEntity> queryChild(int categoryCategoryId, int columnWebsiteId, Integer modelId, Integer size) { return this.columnDao.queryColumnByCategoryIdAndWebsiteIdAndModelId(categoryCategoryId, columnWebsiteId, modelId, size); }




   public List<ColumnEntity> queryAll(int appId, int modelId) { return this.columnDao.queryByAppIdAndModelId(appId, modelId); }



   public List<ColumnEntity> queryAll(int modelId) { return this.columnDao.queryByAppIdAndModelId(BasicUtil.getAppId(), modelId); }














   private void queryExpansionColumnListByWebsiteId(int categoryCategoryId, List<ColumnEntity> list, int columnWebsiteId) {
     List<ColumnEntity> queryChildList = new ArrayList<>();
     queryChildList = this.columnDao.queryColumnByCategoryIdAndWebsiteIdAndModelId(categoryCategoryId, columnWebsiteId, null, null);

     for (int i = 0; i < queryChildList.size(); i++) {
       list.add(queryChildList.get(i));
     }

     if (categoryCategoryId != 0) {
       ColumnEntity columnEntity = (ColumnEntity)this.columnDao.getEntity(Integer.valueOf(categoryCategoryId));
       queryExpansionColumnListByWebsiteId(columnEntity.getCategoryCategoryId(), list, columnWebsiteId);
     }
   }








   public List<ColumnEntity> querySibling(int columnId, Integer size) {
     ColumnEntity columnEntity = (ColumnEntity)this.columnDao.getEntity(Integer.valueOf(columnId));
     List<ColumnEntity> list = new ArrayList<>();
     if (columnEntity != null)
     {
       list = this.columnDao.queryColumnByCategoryIdAndWebsiteIdAndModelId(columnEntity.getCategoryCategoryId(), columnEntity
           .getAppId(), null, size);
     }
     return list;
   }








   public List<ColumnEntity> queryTopSiblingListByColumnId(int columnId, Integer size) {
     ColumnEntity columnEntity = (ColumnEntity)this.columnDao.getEntity(Integer.valueOf(columnId));
     List<ColumnEntity> list = null;
     if (columnEntity != null) {
       list = querySibling(columnEntity.getCategoryCategoryId(), size);
     }
     return list;
   }








   public List<ColumnEntity> queryChildListByColumnId(int columnId, Integer size) {
     ColumnEntity columnEntity = (ColumnEntity)this.columnDao.getEntity(Integer.valueOf(columnId));
     List<ColumnEntity> list = null;
     if (columnEntity != null) {
       list = this.columnDao.queryColumnByCategoryIdAndWebsiteIdAndModelId(columnEntity.getCategoryId(), columnEntity
           .getAppId(), null, size);
     }
     return list;
   }








   public int[] queryChildIdsByColumnId(int categoryId, int appId) {
     List<Integer> ids = this.columnDao.queryColumnChildIdList(categoryId, appId);
     int[] ret = new int[ids.size()];
     for (int i = 0; i < ret.length; i++)
       ret[i] = ((Integer)ids.get(i)).intValue();
     return ret;
   }










   private void queryColumnParent(ColumnEntity column, List<ColumnEntity> list) {
     if (column.getCategoryCategoryId() != 0) {
       ColumnEntity columnEntity = (ColumnEntity)this.columnDao.getEntity(Integer.valueOf(column.getCategoryCategoryId()));
       list.add(columnEntity);
       queryColumnParent(columnEntity, list);
     }
   }








   public List<ColumnEntity> queryParentColumnByColumnId(int columnId) {
     List<ColumnEntity> list = null;
     ColumnEntity columnEntity = (ColumnEntity)this.columnDao.getEntity(Integer.valueOf(columnId));
     if (columnEntity != null) {
       list = new ArrayList<>();

       queryColumnParent(columnEntity, list);
     }
     return list;
   }











   public int queryColumnChildListCountByWebsiteId(int categoryCategoryId, int columnWebsiteId) { return this.columnDao.queryColumnChildListCountByWebsiteId(categoryCategoryId, columnWebsiteId); }







   private void columnPath(ColumnEntity column) {
     IColumnBiz columnBiz = (IColumnBiz)SpringUtil.getBean(IColumnBiz.class);
     String columnPath = "";
     String delFile = "";

     column = (ColumnEntity)columnBiz.getEntity(column.getCategoryId());

     if (column.getCategoryCategoryId() == 0) {
       column.setColumnPath(File.separator + column.getCategoryId());
     } else {
       List<ColumnEntity> list = columnBiz.queryParentColumnByColumnId(column.getCategoryId());
       if (list != null && list.size() > 0) {
         String temp = "";
         for (int i = list.size() - 1; i >= 0; i--) {
           ColumnEntity entity = list.get(i);
           columnPath = columnPath + File.separator + entity.getCategoryId();
           temp = temp + File.separator + entity.getCategoryId();
         }
         column.setColumnPath(columnPath + File.separator + column.getCategoryId());
       }
     }
     columnBiz.updateEntity((BaseEntity)column);
   }


   public void save(ColumnEntity column, int modelCode, int CategoryManagerId) {
     column.setCategoryAppId(BasicUtil.getAppId());
     column.setAppId(BasicUtil.getAppId());
     column.setCategoryManagerId(CategoryManagerId);
     column.setCategoryDateTime(new Timestamp(System.currentTimeMillis()));
     column.setCategoryModelId(modelCode);
     if (column.getColumnType() == ColumnEntity.ColumnTypeEnum.COLUMN_TYPE_COVER.toInt()) {
       column.setColumnListUrl(null);
     }
     saveCategory((CategoryEntity)column);
     columnPath(column);
   }


   public void delete(int[] columns) {
     for (int i = 0; i < columns.length; i++) {
       if (getEntity(columns[i]) != null) {
         deleteCategory(columns[i]);
       }
     }
   }




   public void update(ColumnEntity column, int modelCode, int managerId) {
     int websiteId = BasicUtil.getAppId();



     if (column.getColumnType() == ColumnEntity.ColumnTypeEnum.COLUMN_TYPE_COVER.toInt()) {
       column.setColumnListUrl(null);
     }
     column.setCategoryManagerId(managerId);
     column.setAppId(websiteId);
     updateCategory((CategoryEntity)column);
     columnPath(column);

     List<ColumnEntity> childList = queryChild(column.getCategoryId(), websiteId, Integer.valueOf(modelCode), null);
     if (childList != null && childList.size() > 0)
     {
       for (int i = 0; i < childList.size(); i++) {
         ((ColumnEntity)childList.get(i)).setCategoryCategoryId(column.getCategoryId());
         ((ColumnEntity)childList.get(i)).setCategoryManagerId(managerId);
         ((ColumnEntity)childList.get(i)).setAppId(websiteId);
         updateCategory((CategoryEntity)childList.get(i));

         columnPath(childList.get(i));
       }
     }
   }
 }


