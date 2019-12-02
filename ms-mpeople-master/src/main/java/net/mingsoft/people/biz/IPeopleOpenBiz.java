package net.mingsoft.people.biz;

import net.mingsoft.base.biz.IBaseBiz;
import net.mingsoft.people.entity.PeopleOpenEntity;

public interface IPeopleOpenBiz extends IBaseBiz {
  PeopleOpenEntity getByOpenId(String paramString);
  
  void savePeopleOpen(PeopleOpenEntity paramPeopleOpenEntity);
}


/* Location:              D:\User\Maven\repository\net\mingsoft\ms-mpeople\1.0.11\ms-mpeople-1.0.11.jar!\net\mingsoft\people\biz\IPeopleOpenBiz.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */