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


/* Location:              D:\User\Maven\repository\net\mingsoft\ms-basic\1.0.16\ms-basic-1.0.16.jar!\net\mingsoft\basic\dao\IRoleModelDao.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */