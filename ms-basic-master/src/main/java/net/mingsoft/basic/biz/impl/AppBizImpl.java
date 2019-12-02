 package net.mingsoft.basic.biz.impl;

 import net.mingsoft.base.dao.IBaseDao;
 import net.mingsoft.basic.biz.IAppBiz;
 import net.mingsoft.basic.dao.IAppDao;
 import net.mingsoft.basic.entity.AppEntity;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.stereotype.Service;





































 @Service("appBiz")
 public class AppBizImpl
   extends BasicBizImpl
   implements IAppBiz
 {
   @Autowired
   private IAppDao appDao;

   public AppEntity getByManagerId(int managerId) { return (AppEntity)this.appDao.getByManagerId(managerId); }









   protected IBaseDao getDao() { return (IBaseDao)this.appDao; }





   public int countByUrl(String websiteUrl) { return this.appDao.countByUrl(websiteUrl); }





   public AppEntity getByUrl(String url) { return (AppEntity)this.appDao.getByUrl(url); }
 }


/* Location:              D:\User\Maven\repository\net\mingsoft\ms-basic\1.0.16\ms-basic-1.0.16.jar!\net\mingsoft\basic\biz\impl\AppBizImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */