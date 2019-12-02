package net.mingsoft.basic.dao;

import net.mingsoft.base.dao.IBaseDao;
import net.mingsoft.base.entity.BaseEntity;

public interface IAppDao extends IBaseDao {
  int countByUrl(String paramString);
  
  BaseEntity getByUrl(String paramString);
  
  BaseEntity getByManagerId(int paramInt);
}


/* Location:              D:\User\Maven\repository\net\mingsoft\ms-basic\1.0.16\ms-basic-1.0.16.jar!\net\mingsoft\basic\dao\IAppDao.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */