 package net.mingsoft.mdiy.biz.impl;

 import net.mingsoft.base.biz.impl.BaseBizImpl;
 import net.mingsoft.base.dao.IBaseDao;
 import net.mingsoft.mdiy.biz.ITableFiledBiz;
 import net.mingsoft.mdiy.dao.ITableFiledDao;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.stereotype.Service;


































 @Service("tableFiledBizImpl")
 public class TableFiledBizImpl
   extends BaseBizImpl
   implements ITableFiledBiz
 {
   @Autowired
   private ITableFiledDao tableFiledDao;

   protected IBaseDao getDao() { return (IBaseDao)this.tableFiledDao; }
 }


