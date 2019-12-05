package net.mingsoft.basic.biz;

import java.util.List;
import net.mingsoft.base.biz.IBaseBiz;
import net.mingsoft.basic.entity.RoleModelEntity;

public interface IRoleModelBiz extends IBaseBiz {
  void saveEntity(List<RoleModelEntity> paramList);
  
  void updateEntity(List<RoleModelEntity> paramList);
  
  List<RoleModelEntity> queryByRoleId(int paramInt);
}


