 package net.mingsoft.mdiy.biz.impl;

 import net.mingsoft.base.biz.impl.BaseBizImpl;
 import net.mingsoft.base.dao.IBaseDao;
 import net.mingsoft.mdiy.biz.IContentModelBiz;
 import net.mingsoft.mdiy.dao.IContentModelDao;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.stereotype.Service;






































 @Service("contentModelBizImpl")
 public class ContentModelBizImpl
   extends BaseBizImpl
   implements IContentModelBiz
 {
   @Autowired
   private IContentModelDao contentModelDao;

   protected IBaseDao getDao() { return (IBaseDao)this.contentModelDao; }
 }


