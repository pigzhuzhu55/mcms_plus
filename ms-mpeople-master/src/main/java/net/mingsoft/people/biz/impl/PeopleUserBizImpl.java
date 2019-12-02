 package net.mingsoft.people.biz.impl;

 import net.mingsoft.base.dao.IBaseDao;
 import net.mingsoft.base.entity.BaseEntity;
 import net.mingsoft.people.biz.IPeopleUserBiz;
 import net.mingsoft.people.dao.IPeopleUserDao;
 import net.mingsoft.people.entity.PeopleEntity;
 import net.mingsoft.people.entity.PeopleUserEntity;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.stereotype.Service;
































 @Service("peopleUserBiz")
 public class PeopleUserBizImpl
   extends PeopleBizImpl
   implements IPeopleUserBiz
 {
   @Autowired
   private IPeopleUserDao peopleUserDao;

   protected IBaseDao getDao() { return (IBaseDao)this.peopleUserDao; }






   public int savePeopleUser(PeopleUserEntity peopleEntity) {
     savePeople((PeopleEntity)peopleEntity);
     return this.peopleUserDao.saveEntity((BaseEntity)peopleEntity);
   }






   public void updatePeopleUser(PeopleUserEntity peopleEntity) {
     updatePeople((PeopleEntity)peopleEntity);
     this.peopleUserDao.updateEntity((BaseEntity)peopleEntity);
   }






   public void deletePeopleUser(int peopleId) {
     deletePeople(peopleId);
     this.peopleUserDao.deleteEntity(peopleId);
   }


   public void deletePeopleUsers(int[] peopleIds) {
     if (peopleIds == null) {
       return;
     }
     deletePeople(peopleIds);
     this.peopleUserDao.deletePeopleUsers(peopleIds);
   }



   public PeopleUserEntity getByEntity(PeopleUserEntity peopleUser) { return (PeopleUserEntity)this.peopleUserDao.getByEntity((BaseEntity)peopleUser); }
 }


/* Location:              D:\User\Maven\repository\net\mingsoft\ms-mpeople\1.0.11\ms-mpeople-1.0.11.jar!\net\mingsoft\people\biz\impl\PeopleUserBizImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */