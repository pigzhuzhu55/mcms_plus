package net.mingsoft.base.biz;

import java.util.List;
import java.util.Map;
import net.mingsoft.base.constant.e.TableEnum;
import net.mingsoft.base.entity.BaseEntity;

public interface IBaseBiz<E> {
  @Deprecated
  void alterTable(String paramString1, Map paramMap, String paramString2);
  
  void alterTable(String paramString, Map paramMap, TableEnum paramTableEnum);
  
  int countBySQL(String paramString, Map paramMap);
  
  void createTable(String paramString, Map<Object, List> paramMap);
  
  void delete(int[] paramArrayOfint);
  
  void deleteBySQL(String paramString, Map paramMap);
  
  void deleteEntity(BaseEntity paramBaseEntity);
  
  void deleteEntity(int paramInt);
  
  void dropTable(String paramString);
  
  Object excuteSql(String paramString);
  
  <E> E getEntity(BaseEntity paramBaseEntity);
  
  <E> BaseEntity getEntity(int paramInt);
  
  void insertBySQL(String paramString, Map paramMap);
  
  List<E> query(BaseEntity paramBaseEntity);
  
  List<E> queryAll();
  
  List queryBySQL(String paramString, List<String> paramList, Map paramMap);
  
  List queryBySQL(String paramString, List<String> paramList, Map paramMap, Integer paramInteger1, Integer paramInteger2);
  
  @Deprecated
  int queryCount();
  
  void saveBatch(List paramList);
  
  int saveEntity(BaseEntity paramBaseEntity);
  
  void updateBySQL(String paramString, Map paramMap1, Map paramMap2);
  
  void updateEntity(BaseEntity paramBaseEntity);
}


