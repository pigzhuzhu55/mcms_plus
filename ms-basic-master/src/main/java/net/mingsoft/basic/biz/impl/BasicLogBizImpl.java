 package net.mingsoft.basic.biz.impl;

 import net.mingsoft.base.biz.impl.BaseBizImpl;
 import net.mingsoft.base.dao.IBaseDao;
 import net.mingsoft.basic.biz.IBasicLogBiz;
 import net.mingsoft.basic.dao.IBasicLogDao;
 import net.mingsoft.basic.entity.BasicLogEntity;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.stereotype.Service;










 @Service("basicLogBiz")
 public class BasicLogBizImpl
   extends BaseBizImpl
   implements IBasicLogBiz
 {
   @Autowired
   private IBasicLogDao basicLogDao;

   protected IBaseDao getDao() { return (IBaseDao)this.basicLogDao; }





   public int count(BasicLogEntity basicLog) { return this.basicLogDao.count(basicLog); }
 }


