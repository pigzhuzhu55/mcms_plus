package net.mingsoft.people.dao;

import net.mingsoft.base.dao.IBaseDao;
import net.mingsoft.people.entity.PeopleOpenEntity;

public interface IPeopleOpenDao extends IBaseDao {
  PeopleOpenEntity getByOpenId(String paramString);
}


