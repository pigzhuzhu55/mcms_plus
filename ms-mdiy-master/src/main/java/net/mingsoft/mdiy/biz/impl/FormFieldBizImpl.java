 package net.mingsoft.mdiy.biz.impl;

 import java.util.List;
 import net.mingsoft.base.biz.impl.BaseBizImpl;
 import net.mingsoft.base.dao.IBaseDao;
 import net.mingsoft.mdiy.biz.IFormFieldBiz;
 import net.mingsoft.mdiy.dao.IFormFieldDao;
 import net.mingsoft.mdiy.entity.FormFieldEntity;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.stereotype.Service;



















 @Service
 public class FormFieldBizImpl
   extends BaseBizImpl
   implements IFormFieldBiz
 {
   @Autowired
   private IFormFieldDao diyFormFieldDao;

   protected IBaseDao getDao() { return (IBaseDao)this.diyFormFieldDao; }




   public List<FormFieldEntity> queryByDiyFormId(int diyFormId) { return this.diyFormFieldDao.queryByDiyFormId(diyFormId); }





   public FormFieldEntity getByFieldName(Integer diyFormFormId, String diyFormFieldFieldName) { return this.diyFormFieldDao.getByFieldName(diyFormFormId, diyFormFieldFieldName); }
 }


