package net.mingsoft.people.biz;

import net.mingsoft.people.entity.PeopleUserEntity;

public interface IPeopleUserBiz extends IPeopleBiz {
  int savePeopleUser(PeopleUserEntity paramPeopleUserEntity);
  
  void updatePeopleUser(PeopleUserEntity paramPeopleUserEntity);
  
  void deletePeopleUser(int paramInt);
  
  void deletePeopleUsers(int[] paramArrayOfint);
  
  PeopleUserEntity getByEntity(PeopleUserEntity paramPeopleUserEntity);
}


