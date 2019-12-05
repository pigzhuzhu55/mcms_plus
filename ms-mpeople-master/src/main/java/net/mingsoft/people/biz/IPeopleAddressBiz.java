package net.mingsoft.people.biz;

import net.mingsoft.base.biz.IBaseBiz;
import net.mingsoft.people.entity.PeopleAddressEntity;

public interface IPeopleAddressBiz extends IBaseBiz {
  void setDefault(PeopleAddressEntity paramPeopleAddressEntity);
}


