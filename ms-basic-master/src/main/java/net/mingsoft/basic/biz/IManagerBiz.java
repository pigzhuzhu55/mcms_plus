package net.mingsoft.basic.biz;

import java.util.List;
import net.mingsoft.base.biz.IBaseBiz;
import net.mingsoft.basic.entity.ManagerEntity;

public interface IManagerBiz extends IBaseBiz {
  void updateUserPasswordByUserName(ManagerEntity paramManagerEntity);
  
  List<ManagerEntity> queryAllChildManager(int paramInt);
}


