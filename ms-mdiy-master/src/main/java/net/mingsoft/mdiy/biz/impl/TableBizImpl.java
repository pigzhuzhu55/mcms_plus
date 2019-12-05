 package net.mingsoft.mdiy.biz.impl;

 import net.mingsoft.base.biz.impl.BaseBizImpl;
 import net.mingsoft.base.dao.IBaseDao;
 import net.mingsoft.mdiy.biz.ITableBiz;
 import net.mingsoft.mdiy.dao.ITableDao;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.stereotype.Service;


































 @Service("tableBizImpl")
 public class TableBizImpl
   extends BaseBizImpl
   implements ITableBiz
 {
   @Autowired
   private ITableDao tableDao;

   protected IBaseDao getDao() { return (IBaseDao)this.tableDao; }
 }


