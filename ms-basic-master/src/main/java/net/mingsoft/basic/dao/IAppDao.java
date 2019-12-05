package net.mingsoft.basic.dao;

import net.mingsoft.base.dao.IBaseDao;
import net.mingsoft.base.entity.BaseEntity;

public interface IAppDao extends IBaseDao {
  int countByUrl(String paramString);
  
  BaseEntity getByUrl(String paramString);
  
  BaseEntity getByManagerId(int paramInt);
}


