package net.mingsoft.people.dao;

import net.mingsoft.base.dao.IBaseDao;
import org.apache.ibatis.annotations.Param;

public interface IPeopleUserDao extends IBaseDao {
  void deletePeopleUsers(@Param("peopleIds") int[] paramArrayOfint);
}


