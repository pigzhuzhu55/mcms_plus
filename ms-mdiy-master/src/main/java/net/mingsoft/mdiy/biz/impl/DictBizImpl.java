 package net.mingsoft.mdiy.biz.impl;

 import net.mingsoft.base.biz.impl.BaseBizImpl;
 import net.mingsoft.base.dao.IBaseDao;
 import net.mingsoft.base.entity.BaseEntity;
 import net.mingsoft.basic.util.BasicUtil;
 import net.mingsoft.mdiy.biz.IDictBiz;
 import net.mingsoft.mdiy.dao.IDictDao;
 import net.mingsoft.mdiy.entity.DictEntity;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.stereotype.Service;


































 @Service("dictBizImpl")
 public class DictBizImpl
   extends BaseBizImpl
   implements IDictBiz
 {
   @Autowired
   private IDictDao dictDao;

   protected IBaseDao getDao() { return (IBaseDao)this.dictDao; }




   public DictEntity getByTypeAndLabel(String dictType, String dictLabel) {
     DictEntity dict = new DictEntity();
     dict.setDictType(dictType);
     dict.setDictLabel(dictLabel);
     dict.setAppId(BasicUtil.getAppId());
     return (DictEntity)this.dictDao.getByEntity((BaseEntity)dict);
   }
 }


