 package net.mingsoft.base.biz.impl;

 import java.io.Serializable;
 import java.util.List;
 import java.util.Map;
 import net.mingsoft.base.biz.IBaseBiz;
 import net.mingsoft.base.constant.e.TableEnum;
 import net.mingsoft.base.dao.IBaseDao;
 import net.mingsoft.base.entity.BaseEntity;
 import org.slf4j.Logger;
 import org.slf4j.LoggerFactory;
































 public abstract class BaseBizImpl<E extends Serializable>
   implements IBaseBiz
 {
   private IBaseDao<E> baseDao;
   protected final Logger LOG = LoggerFactory.getLogger(getClass());



   public int saveEntity(BaseEntity entity) { return getDao().saveEntity(entity); }





   public void deleteEntity(int id) { getDao().deleteEntity(id); }






   public void updateEntity(BaseEntity entity) { getDao().updateEntity(entity); }





   public List<E> queryAll() { return getDao().queryAll(); }






   @Deprecated
   public int queryCount() { return getDao().queryCount(); }




   public BaseEntity getEntity(int id) { return getDao().getEntity(Integer.valueOf(id)); }





   public List queryBySQL(String table, List fields, Map wheres, Integer begin, Integer end) { return getDao().queryBySQL(table, fields, wheres, begin, end, null); }





   public int countBySQL(String table, Map wheres) { return getDao().countBySQL(table, wheres); }





   public List queryBySQL(String table, List fields, Map wheres) { return getDao().queryBySQL(table, fields, wheres, null, null, null); }





   public void updateBySQL(String table, Map fields, Map wheres) { getDao().updateBySQL(table, fields, wheres); }





   public void deleteBySQL(String table, Map wheres) { getDao().deleteBySQL(table, wheres); }





   public void insertBySQL(String table, Map fields) { getDao().insertBySQL(table, fields); }





   public void createTable(String table, Map fileds) { getDao().createTable(table, fileds); }





   public void alterTable(String table, Map fileds, String type) { getDao().alterTable(table, fileds, type); }




   public void alterTable(String table, Map fileds, TableEnum type) { getDao().alterTable(table, fileds, type.toString()); }





   public void dropTable(String table) { getDao().dropTable(table); }





   public Object excuteSql(String sql) { return getDao().excuteSql(sql); }











   public void saveBatch(List list) { getDao().saveBatch(list); }




   public void delete(int[] ids) { getDao().delete(ids); }





   public void deleteEntity(BaseEntity entity) { getDao().deleteByEntity(entity); }





   public E getEntity(BaseEntity entity) { return (E)getDao().getByEntity(entity); }





   public List<E> query(BaseEntity entity) { return getDao().query(entity); }

   protected abstract IBaseDao<E> getDao();
 }


