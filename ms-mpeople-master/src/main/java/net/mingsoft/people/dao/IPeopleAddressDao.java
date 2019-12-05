package net.mingsoft.people.dao;

import net.mingsoft.base.dao.IBaseDao;
import net.mingsoft.people.entity.PeopleAddressEntity;

public interface IPeopleAddressDao extends IBaseDao {
  void setDefault(PeopleAddressEntity paramPeopleAddressEntity);
}


