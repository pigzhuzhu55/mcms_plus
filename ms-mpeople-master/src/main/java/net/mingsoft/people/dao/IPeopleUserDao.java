package net.mingsoft.people.dao;

import net.mingsoft.base.dao.IBaseDao;
import org.apache.ibatis.annotations.Param;

public interface IPeopleUserDao extends IBaseDao {
  void deletePeopleUsers(@Param("peopleIds") int[] paramArrayOfint);
}


/* Location:              D:\User\Maven\repository\net\mingsoft\ms-mpeople\1.0.11\ms-mpeople-1.0.11.jar!\net\mingsoft\people\dao\IPeopleUserDao.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */