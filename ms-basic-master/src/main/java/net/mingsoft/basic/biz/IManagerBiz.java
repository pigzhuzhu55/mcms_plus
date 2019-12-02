package net.mingsoft.basic.biz;

import java.util.List;
import net.mingsoft.base.biz.IBaseBiz;
import net.mingsoft.basic.entity.ManagerEntity;

public interface IManagerBiz extends IBaseBiz {
  void updateUserPasswordByUserName(ManagerEntity paramManagerEntity);
  
  List<ManagerEntity> queryAllChildManager(int paramInt);
}


/* Location:              D:\User\Maven\repository\net\mingsoft\ms-basic\1.0.16\ms-basic-1.0.16.jar!\net\mingsoft\basic\biz\IManagerBiz.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */