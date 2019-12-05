 package net.mingsoft.mdiy.biz.impl;

 import java.util.ArrayList;
 import java.util.Date;
 import java.util.HashMap;
 import java.util.List;
 import java.util.Map;
 import java.util.Set;
 import net.mingsoft.base.biz.impl.BaseBizImpl;
 import net.mingsoft.base.dao.IBaseDao;
 import net.mingsoft.mdiy.biz.IFormBiz;
 import net.mingsoft.mdiy.constant.e.DiyFormFieldEnum;
 import net.mingsoft.mdiy.dao.IFormDao;
 import net.mingsoft.mdiy.dao.IFormFieldDao;
 import net.mingsoft.mdiy.entity.FormEntity;
 import net.mingsoft.mdiy.entity.FormFieldEntity;
 import org.apache.commons.lang3.StringUtils;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.stereotype.Service;





































 @Service
 public class FormBizImpl
   extends BaseBizImpl
   implements IFormBiz
 {
   private static final String FORM_ID = "FROMID";
   private static final String DATE = "DATE";
   private static final String ID = "ID";
   @Autowired
   private IFormDao formDao;
   @Autowired
   private IFormFieldDao formFieldDao;

   protected IBaseDao getDao() { return (IBaseDao)this.formDao; }




   public void saveDiyFormData(int formId, Map params) {
     FormEntity dfe = (FormEntity)this.formDao.getEntity(Integer.valueOf(formId));
     if (dfe == null) {
       return;
     }
     String tableName = dfe.getFormTableName();
     List<FormFieldEntity> filedList = this.formFieldDao.queryByDiyFormId(formId);
     if (filedList == null) {
       return;
     }
     Map<String, Object> values = builderSqlMap(filedList, params);
     values.put("FROMID", Integer.valueOf(formId));
     values.put("DATE", new Date());
     this.formFieldDao.insertBySQL(tableName, values);
   }



   public Map queryDiyFormData(int diyFormId, int appId, Map whereMap) {
     FormEntity dfe = (FormEntity)this.formDao.getEntity(Integer.valueOf(diyFormId));
     if (dfe != null) {
       List<FormFieldEntity> fieldList = this.formFieldDao.queryByDiyFormId(diyFormId);
       List<Map<String, String>> fields = new ArrayList<>();
       Map<String, String> field = new HashMap<>();

       List<String> keys = new ArrayList<>();
       for (int i = 0; i < fieldList.size(); i++) {
         field.put(((FormFieldEntity)fieldList.get(i)).getDiyFormFieldFieldName(), ((FormFieldEntity)fieldList.get(i)).getDiyFormFieldTipsName());
         fields.add(field);
         keys.add(((FormFieldEntity)fieldList.get(i)).getDiyFormFieldFieldName().toLowerCase());
       }
       Map<Object, Object> wheres = new HashMap<>();
       wheres.put("FROMID", Integer.valueOf(diyFormId));

       if (whereMap != null) {

         Set<String> whereStr = whereMap.keySet();

         String[] array = whereStr.toArray(new String[whereStr.size()]);

         for (String str : array) {

           if (!keys.contains(str)) {
             whereStr.remove(str);
           }
         }

         for (FormFieldEntity formFieldEntity : fieldList) {
           for (String key : whereStr) {
             if (DiyFormFieldEnum.INT.toInt() == formFieldEntity.getDiyFormFieldType() || DiyFormFieldEnum.FLOAT
               .toInt() == formFieldEntity.getDiyFormFieldType()) {
               wheres.put(key, whereMap.get(key)); continue;
             }
             wheres.put(key, "'" + whereMap.get(key) + "'");
           }
         }
       } else {

         List list = this.formDao.queryBySQL(dfe.getFormTableName(), fields, wheres, Integer.valueOf(0), Integer.valueOf(10), null);
         wheres.put("list", list);
       }
       wheres.put("fields", fieldList);
       return wheres;
     }
     return null;
   }



   public void deleteQueryDiyFormData(int id, int diyFormId) {
     FormEntity dfe = (FormEntity)this.formDao.getEntity(Integer.valueOf(diyFormId));
     Map<Object, Object> wheres = new HashMap<>();
     wheres.put("FROMID", Integer.valueOf(diyFormId));
     wheres.put("ID", Integer.valueOf(id));
     this.formDao.deleteBySQL(dfe.getFormTableName(), wheres);
   }



   public int countDiyFormData(int diyFormId, int appId) {
     FormEntity dfe = (FormEntity)this.formDao.getEntity(Integer.valueOf(diyFormId));
     Map<Object, Object> wheres = new HashMap<>();
     wheres.put("FROMID", Integer.valueOf(diyFormId));
     return this.formDao.countBySQL(dfe.getFormTableName(), wheres);
   }








   private Map builderSqlMap(List<FormFieldEntity> listField, Map params) {
     Map<Object, Object> mapParams = new HashMap<>();

     for (int i = 0; i < listField.size(); i++) {
       FormFieldEntity field = listField.get(i);

       String fieldName = field.getDiyFormFieldFieldName();
       String fieldNameLowerCase = field.getDiyFormFieldFieldName().toLowerCase();













       if (!StringUtils.isBlank((new StringBuilder()).append(params.get(fieldNameLowerCase)).append("").toString())) {

         mapParams.put(fieldName, params.get(fieldNameLowerCase));
       } else {

         mapParams.put(fieldName, params.get(fieldName));
       }
     }

     return mapParams;
   }




   public void createDiyFormTable(String table, Map<Object, List> fileds) { this.formDao.createDiyFormTable(table, fileds); }
 }


