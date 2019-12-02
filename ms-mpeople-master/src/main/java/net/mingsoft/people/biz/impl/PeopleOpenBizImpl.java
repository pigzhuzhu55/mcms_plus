 package net.mingsoft.people.biz.impl;

 import net.mingsoft.base.dao.IBaseDao;
 import net.mingsoft.base.entity.BaseEntity;
 import net.mingsoft.people.biz.IPeopleOpenBiz;
 import net.mingsoft.people.dao.IPeopleDao;
 import net.mingsoft.people.dao.IPeopleOpenDao;
 import net.mingsoft.people.dao.IPeopleUserDao;
 import net.mingsoft.people.entity.PeopleOpenEntity;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.stereotype.Service;



































 @Service("peopleOpenBizImpl")
 public class PeopleOpenBizImpl
   extends PeopleUserBizImpl
   implements IPeopleOpenBiz
 {
   @Autowired
   private IPeopleOpenDao peopleOpenDao;
   @Autowired
   private IPeopleUserDao peopleUserDao;
   @Autowired
   private IPeopleDao peopleDao;

   protected IBaseDao getDao() { return (IBaseDao)this.peopleOpenDao; }




   public void savePeopleOpen(PeopleOpenEntity peopleOpen) {
     this.peopleDao.saveEntity((BaseEntity)peopleOpen);
     this.peopleUserDao.saveEntity((BaseEntity)peopleOpen);
     this.peopleOpenDao.saveEntity((BaseEntity)peopleOpen);
   }




   public PeopleOpenEntity getByOpenId(String openId) { return this.peopleOpenDao.getByOpenId(openId); }
 }


/* Location:              D:\User\Maven\repository\net\mingsoft\ms-mpeople\1.0.11\ms-mpeople-1.0.11.jar!\net\mingsoft\people\biz\impl\PeopleOpenBizImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */