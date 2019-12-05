package net.mingsoft.basic.dao;

import java.util.List;
import net.mingsoft.base.dao.IBaseDao;
import net.mingsoft.basic.entity.RoleModelEntity;

public interface IRoleModelDao extends IBaseDao {
  void saveEntity(List<RoleModelEntity> paramList);
  
  void updateEntity(List<RoleModelEntity> paramList);
  
  void deleteByRoleId(int paramInt);
  
  List<RoleModelEntity> queryByRoleId(int paramInt);
}


