 package net.mingsoft.mdiy.biz.impl;

 import net.mingsoft.base.biz.impl.BaseBizImpl;
 import net.mingsoft.base.dao.IBaseDao;
 import net.mingsoft.mdiy.biz.IPageBiz;
 import net.mingsoft.mdiy.dao.IPageDao;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.stereotype.Service;






































 @Service("pageBizImpl")
 public class PageBizImpl
   extends BaseBizImpl
   implements IPageBiz
 {
   @Autowired
   private IPageDao pageDao;

   protected IBaseDao getDao() { return (IBaseDao)this.pageDao; }
 }


