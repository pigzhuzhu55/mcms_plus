 package net.mingsoft.basic.biz.impl;

 import java.util.List;
 import net.mingsoft.base.biz.impl.BaseBizImpl;
 import net.mingsoft.base.dao.IBaseDao;
 import net.mingsoft.basic.biz.IRoleModelBiz;
 import net.mingsoft.basic.dao.IRoleModelDao;
 import net.mingsoft.basic.entity.RoleModelEntity;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.stereotype.Service;









































 @Service("roleModelBiz")
 public class RoleModelBizImpl
   extends BaseBizImpl
   implements IRoleModelBiz
 {
   @Autowired
   private IRoleModelDao roleModelDao;

   public IBaseDao getDao() { return (IBaseDao)this.roleModelDao; }





   public void saveEntity(List<RoleModelEntity> roleModelList) { this.roleModelDao.saveEntity(roleModelList); }





   public void updateEntity(List<RoleModelEntity> roleModelList) { this.roleModelDao.updateEntity(roleModelList); }





   public List<RoleModelEntity> queryByRoleId(int roleId) { return this.roleModelDao.queryByRoleId(roleId); }
 }


