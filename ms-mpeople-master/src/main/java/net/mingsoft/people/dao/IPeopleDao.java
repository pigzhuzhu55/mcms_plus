package net.mingsoft.people.dao;

import java.util.List;
import java.util.Map;
import net.mingsoft.base.dao.IBaseDao;
import net.mingsoft.people.entity.PeopleEntity;
import org.apache.ibatis.annotations.Param;

public interface IPeopleDao extends IBaseDao {
  PeopleEntity getEntityByUserName(@Param("userName") String paramString, @Param("appId") int paramInt);
  
  PeopleEntity getEntityByMailOrPhone(@Param("userName") String paramString, @Param("appId") int paramInt);
  
  PeopleEntity getByPeople(@Param("people") PeopleEntity paramPeopleEntity, @Param("appId") int paramInt);
  
  List<PeopleEntity> queryPageListByAppId(@Param("appId") int paramInt);
  
  int queryCountByAppId(@Param("appId") int paramInt);
  
  PeopleEntity getEntityByCode(@Param("userName") String paramString1, @Param("peopleCode") String paramString2, @Param("appId") int paramInt);
  
  int getCount(@Param("appId") Integer paramInteger, @Param("where") Map paramMap);
  
  void deletePeoples(@Param("peopleIds") int[] paramArrayOfint);
  
  List<PeopleEntity> queryByAppIdAndMap(@Param("appId") int paramInt, @Param("whereMap") Map paramMap);
  
  int getCountByAppIdAndMap(@Param("appId") int paramInt, @Param("whereMap") Map paramMap);
  
  List<PeopleEntity> query(@Param("appId") int paramInt, @Param("where") Map paramMap);
}


