package net.mingsoft.people.biz;

import java.util.List;
import java.util.Map;
import net.mingsoft.base.biz.IBaseBiz;
import net.mingsoft.people.entity.PeopleEntity;

public interface IPeopleBiz extends IBaseBiz {
  void deletePeople(int paramInt);
  
  void deletePeople(int[] paramArrayOfint);
  
  PeopleEntity getByPeople(PeopleEntity paramPeopleEntity, int paramInt);
  
  int getCountByAppIdAndMap(int paramInt, Map paramMap);
  
  int getCountByDate(String paramString, Integer paramInteger);
  
  PeopleEntity getEntityByCode(String paramString1, String paramString2, int paramInt);
  
  PeopleEntity getEntityByMailOrPhone(String paramString, int paramInt);
  
  PeopleEntity getEntityByUserName(String paramString, int paramInt);
  
  @Deprecated
  List<PeopleEntity> queryByAppIdAndMap(int paramInt, Map paramMap);
  
  List<PeopleEntity> query(int paramInt, Map paramMap);
  
  @Deprecated
  int queryCountByAppId(int paramInt);
  
  @Deprecated
  List<PeopleEntity> queryPageListByAppId(int paramInt);
  
  int savePeople(PeopleEntity paramPeopleEntity);
  
  void updatePeople(PeopleEntity paramPeopleEntity);
}


/* Location:              D:\User\Maven\repository\net\mingsoft\ms-mpeople\1.0.11\ms-mpeople-1.0.11.jar!\net\mingsoft\people\biz\IPeopleBiz.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */