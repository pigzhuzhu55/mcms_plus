 package net.mingsoft.basic.biz.impl;

 import java.util.List;
 import net.mingsoft.base.biz.impl.BaseBizImpl;
 import net.mingsoft.base.dao.IBaseDao;
 import net.mingsoft.basic.biz.IManagerBiz;
 import net.mingsoft.basic.dao.IManagerDao;
 import net.mingsoft.basic.entity.ManagerEntity;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.stereotype.Service;


































































 @Service("managerBiz")
 public class ManagerBizImpl
   extends BaseBizImpl
   implements IManagerBiz
 {
   private IManagerDao managerDao;

   public IManagerDao getManagerDao() { return this.managerDao; }








   @Autowired
   public void setManagerDao(IManagerDao managerDao) { this.managerDao = managerDao; }









   public IBaseDao getDao() { return (IBaseDao)this.managerDao; }





   public void updateUserPasswordByUserName(ManagerEntity manager) { this.managerDao.updateUserPasswordByUserName(manager); }





   public List<ManagerEntity> queryAllChildManager(int managerId) { return this.managerDao.queryAllChildManager(managerId); }
 }


