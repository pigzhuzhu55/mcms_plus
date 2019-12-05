 package net.mingsoft.mdiy.biz.impl;

 import net.mingsoft.base.biz.impl.BaseBizImpl;
 import net.mingsoft.base.dao.IBaseDao;
 import net.mingsoft.mdiy.biz.ITagBiz;
 import net.mingsoft.mdiy.dao.ITagDao;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.stereotype.Service;
































 @Service("tagBizImpl")
 public class TagBizImpl
   extends BaseBizImpl
   implements ITagBiz
 {
   @Autowired
   private ITagDao tagDao;

   protected IBaseDao getDao() { return (IBaseDao)this.tagDao; }
 }


