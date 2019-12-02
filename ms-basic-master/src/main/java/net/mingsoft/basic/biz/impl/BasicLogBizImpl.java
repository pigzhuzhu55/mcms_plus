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


/* Location:              D:\User\Maven\repository\net\mingsoft\ms-basic\1.0.16\ms-basic-1.0.16.jar!\net\mingsoft\basic\biz\impl\BasicLogBizImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */