 package net.mingsoft.basic.biz.impl;

 import net.mingsoft.base.biz.impl.BaseBizImpl;
 import net.mingsoft.base.dao.IBaseDao;
 import net.mingsoft.basic.biz.IRoleBiz;
 import net.mingsoft.basic.dao.IRoleDao;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.stereotype.Service;











































 @Service("roleBiz")
 public class RoleBizImpl
   extends BaseBizImpl
   implements IRoleBiz
 {
   @Autowired
   private IRoleDao roleDao;

   public IBaseDao getDao() { return (IBaseDao)this.roleDao; }
 }


