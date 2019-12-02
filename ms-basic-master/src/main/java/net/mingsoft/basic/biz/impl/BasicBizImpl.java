 package net.mingsoft.basic.biz.impl;

 import java.util.List;
 import net.mingsoft.base.biz.impl.BaseBizImpl;
 import net.mingsoft.base.dao.IBaseDao;
 import net.mingsoft.base.entity.BaseEntity;
 import net.mingsoft.basic.biz.IBasicBiz;
 import net.mingsoft.basic.dao.IBasicDao;
 import net.mingsoft.basic.entity.BasicEntity;
 import net.mingsoft.basic.util.BasicUtil;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.stereotype.Service;



































 @Service("basicBiz")
 public class BasicBizImpl
   extends BaseBizImpl
   implements IBasicBiz
 {
   @Autowired
   private IBasicDao basicDao;

   public void deleteBasic(int basicId) {
     this.basicDao.deleteEntity(basicId);
     deleteEntity(basicId);
   }



   public void deleteBasic(int[] basicIds) {
     this.basicDao.delete(basicIds);
     delete(basicIds);
   }



   public BasicEntity getBasic(int basicId) { return (BasicEntity)this.basicDao.getEntity(Integer.valueOf(basicId)); }









   protected IBaseDao getDao() { return (IBaseDao)this.basicDao; }






   public List<BasicEntity> query(int categoryId) { return this.basicDao.query(null, Integer.valueOf(categoryId), null, null, null, null, null, null, null); }




   public int saveBasic(BasicEntity basic) {
     basic.setBasicAppId(BasicUtil.getAppId());
     this.basicDao.saveEntity((BaseEntity)basic);
     return saveEntity((BaseEntity)basic);
   }


   public void updateBasic(BasicEntity basic) {
     basic.setBasicAppId(BasicUtil.getAppId());
     this.basicDao.updateEntity((BaseEntity)basic);
     updateEntity((BaseEntity)basic);
   }




   public void updateHit(int basicId, Integer num) { this.basicDao.updateHit(basicId, num); }





   public void update(int basicId, Integer num, String field) { this.basicDao.updateFieldNum(basicId, num, field); }
 }


/* Location:              D:\User\Maven\repository\net\mingsoft\ms-basic\1.0.16\ms-basic-1.0.16.jar!\net\mingsoft\basic\biz\impl\BasicBizImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */