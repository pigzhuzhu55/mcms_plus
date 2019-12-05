package net.mingsoft.basic.dao;

import java.util.List;
import net.mingsoft.base.dao.IBaseDao;
import net.mingsoft.base.entity.BaseEntity;
import net.mingsoft.basic.entity.ManagerEntity;
import org.apache.ibatis.annotations.Param;

public interface IManagerDao extends IBaseDao {
  ManagerEntity queryManagerByManagerName(String paramString);
  
  void updateUserPasswordByUserName(ManagerEntity paramManagerEntity);
  
  int countManagerName(String paramString);
  
  List<ManagerEntity> queryAllChildManager(int paramInt);
  
  void deleteManagerByRoleId(int paramInt);
  
  List<BaseEntity> queryByPage(@Param("managerId") int paramInt1, @Param("pageNo") int paramInt2, @Param("pageSize") int paramInt3, @Param("orderBy") String paramString, @Param("order") boolean paramBoolean);
}


