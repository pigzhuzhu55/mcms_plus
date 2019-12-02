package net.mingsoft.basic.biz;

import java.util.List;
import net.mingsoft.base.biz.IBaseBiz;
import net.mingsoft.basic.entity.RoleModelEntity;

public interface IRoleModelBiz extends IBaseBiz {
  void saveEntity(List<RoleModelEntity> paramList);
  
  void updateEntity(List<RoleModelEntity> paramList);
  
  List<RoleModelEntity> queryByRoleId(int paramInt);
}


/* Location:              D:\User\Maven\repository\net\mingsoft\ms-basic\1.0.16\ms-basic-1.0.16.jar!\net\mingsoft\basic\biz\IRoleModelBiz.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */