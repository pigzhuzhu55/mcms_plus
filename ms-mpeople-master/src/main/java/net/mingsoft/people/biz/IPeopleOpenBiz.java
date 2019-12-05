package net.mingsoft.people.biz;

import net.mingsoft.base.biz.IBaseBiz;
import net.mingsoft.people.entity.PeopleOpenEntity;

public interface IPeopleOpenBiz extends IBaseBiz {
  PeopleOpenEntity getByOpenId(String paramString);
  
  void savePeopleOpen(PeopleOpenEntity paramPeopleOpenEntity);
}


