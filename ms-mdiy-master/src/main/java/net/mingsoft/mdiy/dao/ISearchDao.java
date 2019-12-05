package net.mingsoft.mdiy.dao;

import java.util.List;
import java.util.Map;
import net.mingsoft.base.dao.IBaseDao;
import net.mingsoft.mdiy.entity.SearchEntity;
import org.apache.ibatis.annotations.Param;

public interface ISearchDao extends IBaseDao {
  Map queryMapByNumArea(@Param("cmTableName") String paramString1, @Param("fieldFieldName") String paramString2, @Param("preNum") int paramInt1, @Param("nextNum") int paramInt2);
  
  SearchEntity getByIdAndAppId(@Param("id") int paramInt1, @Param("appId") int paramInt2);
  
  List query(@Param("appId") int paramInt1, @Param("start") int paramInt2, @Param("pageSize") int paramInt3);
  
  int queryCount(@Param("appId") int paramInt);
}


