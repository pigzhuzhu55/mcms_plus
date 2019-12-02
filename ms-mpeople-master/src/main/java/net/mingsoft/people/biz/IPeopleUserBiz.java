package net.mingsoft.people.biz;

import net.mingsoft.people.entity.PeopleUserEntity;

public interface IPeopleUserBiz extends IPeopleBiz {
  int savePeopleUser(PeopleUserEntity paramPeopleUserEntity);
  
  void updatePeopleUser(PeopleUserEntity paramPeopleUserEntity);
  
  void deletePeopleUser(int paramInt);
  
  void deletePeopleUsers(int[] paramArrayOfint);
  
  PeopleUserEntity getByEntity(PeopleUserEntity paramPeopleUserEntity);
}


/* Location:              D:\User\Maven\repository\net\mingsoft\ms-mpeople\1.0.11\ms-mpeople-1.0.11.jar!\net\mingsoft\people\biz\IPeopleUserBiz.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */