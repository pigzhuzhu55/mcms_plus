package net.mingsoft.people.dao;

import net.mingsoft.base.dao.IBaseDao;
import net.mingsoft.people.entity.PeopleOpenEntity;

public interface IPeopleOpenDao extends IBaseDao {
  PeopleOpenEntity getByOpenId(String paramString);
}


/* Location:              D:\User\Maven\repository\net\mingsoft\ms-mpeople\1.0.11\ms-mpeople-1.0.11.jar!\net\mingsoft\people\dao\IPeopleOpenDao.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */