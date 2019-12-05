package net.mingsoft.base.dao;

import java.util.List;
import java.util.Map;
import net.mingsoft.base.entity.BaseEntity;
import org.apache.ibatis.annotations.Param;

public interface IBaseDao<E extends java.io.Serializable> {
  void alterTable(@Param("table") String paramString1, @Param("fileds") Map paramMap, @Param("type") String paramString2);
  
  int countBySQL(@Param("table") String paramString, @Param("wheres") Map paramMap);
  
  void createTable(@Param("table") String paramString, @Param("fileds") Map<Object, List> paramMap);
  
  void deleteBySQL(@Param("table") String paramString, @Param("wheres") Map paramMap);
  
  void dropTable(@Param("table") String paramString);
  
  List excuteSql(@Param("sql") String paramString);
  
  void insertBySQL(@Param("table") String paramString, @Param("fields") Map paramMap);
  
  List queryBySQL(@Param("table") String paramString1, @Param("fields") List<String> paramList, @Param("wheres") Map paramMap, @Param("begin") Integer paramInteger1, @Param("end") Integer paramInteger2, @Param("order") String paramString2);
  
  void updateBySQL(@Param("table") String paramString, @Param("fields") Map paramMap1, @Param("wheres") Map paramMap2);
  
  void delete(@Param("ids") int[] paramArrayOfint);
  
  @Deprecated
  void deleteEntity(int paramInt);
  
  void deleteByEntity(BaseEntity paramBaseEntity);
  
  BaseEntity getEntity(Integer paramInteger);
  
  <E> E getByEntity(BaseEntity paramBaseEntity);
  
  List<E> query(BaseEntity paramBaseEntity);
  
  List<E> queryAll();
  
  @Deprecated
  List<E> queryByPage(@Param("pageNo") int paramInt1, @Param("pageSize") int paramInt2, @Param("orderBy") String paramString, @Param("order") boolean paramBoolean);
  
  @Deprecated
  int queryCount();
  
  void saveBatch(@Param("list") List paramList);
  
  int saveEntity(BaseEntity paramBaseEntity);
  
  void updateEntity(BaseEntity paramBaseEntity);
}


