 package net.mingsoft.mdiy.biz.impl;

 import java.util.ArrayList;
 import java.util.Iterator;
 import java.util.List;
 import java.util.Map;
 import net.mingsoft.base.dao.IBaseDao;
 import net.mingsoft.base.entity.BaseEntity;
 import net.mingsoft.basic.biz.impl.BasicBizImpl;
 import net.mingsoft.mdiy.biz.IContentModelFieldBiz;
 import net.mingsoft.mdiy.constant.e.FieldSearchEnum;
 import net.mingsoft.mdiy.dao.IContentModelFieldDao;
 import net.mingsoft.mdiy.entity.ContentModelFieldEntity;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.stereotype.Service;














































 @Service("contentModelFieldBizImpl")
 public class ContentModelFieldBizImpl
   extends BasicBizImpl
   implements IContentModelFieldBiz
 {
   @Autowired
   private IContentModelFieldDao contentModelFieldDao;

   protected IBaseDao getDao() { return (IBaseDao)this.contentModelFieldDao; }










   public List<ContentModelFieldEntity> queryListByCmid(int fieldCmid) { return this.contentModelFieldDao.queryListByCmid(fieldCmid); }









   @Deprecated
   public int queryCountByCmid(int fieldCmid) { return this.contentModelFieldDao.queryCountByCmid(fieldCmid); }


















   @Deprecated
   public List<BaseEntity> queryByPage(int fieldCmid, String orderBy, boolean order) { return this.contentModelFieldDao.queryByPage(fieldCmid, 1, 100000, orderBy, order); }














   @Deprecated
   public int getCountFieldName(String fieldFieldName, int fieldCmdId) { return this.contentModelFieldDao.getCountFieldName(fieldFieldName, fieldCmdId); }










   public ContentModelFieldEntity getEntityByFieldName(String fieldFieldName) { return this.contentModelFieldDao.getEntityByFieldName(fieldFieldName); }











   public ContentModelFieldEntity getEntityByCmId(int cmId, String fieldFieldName) { return this.contentModelFieldDao.getEntityByCmId(cmId, fieldFieldName); }













   @Deprecated
   public List<Integer> queryListBySQL(String table, Map<String, String> diyFieldName) {
     List<ContentModelFieldEntity> listField = new ArrayList<>();
     Iterator<String> it = diyFieldName.keySet().iterator();

     for (Iterator<String> iter = diyFieldName.keySet().iterator(); iter.hasNext(); ) {

       ContentModelFieldEntity field = this.contentModelFieldDao.getEntityByFieldName(iter.next().toString());
       if (field != null) {
         listField.add(field);
       }
     }

     String where = "where 1=1";
     for (int i = 0; i < listField.size(); i++) {
       for (Iterator<String> iter = diyFieldName.keySet().iterator(); iter.hasNext(); ) {
         String key = iter.next().toString();
         if (((ContentModelFieldEntity)listField.get(i)).getFieldFieldName().equals(String.valueOf(key))) {

           if (((ContentModelFieldEntity)listField.get(i)).getFieldType() == 4 || ((ContentModelFieldEntity)listField.get(i)).getFieldType() == 5) {

             if (((String)diyFieldName.get(key)).indexOf("-") > 0) {

               int preNum = 1;

               int nextNum = 12;
               where = where + " and " + ((ContentModelFieldEntity)listField.get(i)).getFieldFieldName() + " between " + preNum + " and " + nextNum;

               continue;
             }
             where = where + " and " + ((ContentModelFieldEntity)listField.get(i)).getFieldFieldName() + " = " + Integer.valueOf(diyFieldName.get(key));

             continue;
           }
           where = where + " and " + ((ContentModelFieldEntity)listField.get(i)).getFieldFieldName() + " like '%" + String.valueOf(diyFieldName.get(key)) + "%'";
         }
       }
     }


     List<Map> listMap = this.contentModelFieldDao.queryListByListField(table, where);
     List<Integer> listIds = new ArrayList<>();
     for (int i = 0; i < listMap.size(); i++) {
       Iterator iter = ((Map)listMap.get(i)).keySet().iterator();
       while (iter.hasNext()) {
         listIds.add(Integer.valueOf(((Map)listMap.get(i)).get(iter.next()).toString()));
       }
     }

     return listIds;
   }



   public List<ContentModelFieldEntity> queryByContentModelId(int contentModelId) { return this.contentModelFieldDao.queryByContentModelId(contentModelId); }











   @Deprecated
   public void deleteEntityByFieldCmid(int fieldCmid) { this.contentModelFieldDao.deleteEntityByFieldCmid(fieldCmid); }




   @Deprecated
   public List<ContentModelFieldEntity> queryByIsSearch(int contentMdoelId, FieldSearchEnum fieldIsSearch) { return this.contentModelFieldDao.queryByIsSearch(Integer.valueOf(contentMdoelId), Integer.valueOf(fieldIsSearch.toInt())); }
 }


