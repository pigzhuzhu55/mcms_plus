 package net.mingsoft.mdiy.biz.impl;

 import net.mingsoft.base.dao.IBaseDao;
 import net.mingsoft.base.entity.BaseEntity;
 import net.mingsoft.basic.biz.impl.BasicBizImpl;
 import net.mingsoft.basic.util.BasicUtil;
 import net.mingsoft.mdiy.biz.ISearchBiz;
 import net.mingsoft.mdiy.dao.ISearchDao;
 import net.mingsoft.mdiy.entity.SearchEntity;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.stereotype.Service;













































 @Service("searchBiz")
 public class SearchBizImpl
   extends BasicBizImpl
   implements ISearchBiz
 {
   @Autowired
   private ISearchDao searchDao;

   protected IBaseDao getDao() { return (IBaseDao)this.searchDao; }




   public SearchEntity getById(int searchId) {
     SearchEntity search = new SearchEntity();
     search.setAppId(BasicUtil.getAppId());
     search.setSearchId(searchId);
     Object obj = this.searchDao.getByEntity((BaseEntity)search);
     return (obj != null) ? (SearchEntity)obj : null;
   }
 }


