 package net.mingsoft.people.biz.impl;

 import java.util.HashMap;
 import java.util.List;
 import java.util.Map;
 import net.mingsoft.base.biz.impl.BaseBizImpl;
 import net.mingsoft.base.dao.IBaseDao;
 import net.mingsoft.base.entity.BaseEntity;
 import net.mingsoft.basic.util.BasicUtil;
 import net.mingsoft.people.biz.IPeopleBiz;
 import net.mingsoft.people.dao.IPeopleDao;
 import net.mingsoft.people.entity.PeopleEntity;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.stereotype.Service;




































 @Service("peopleBiz")
 public class PeopleBizImpl
   extends BaseBizImpl
   implements IPeopleBiz
 {
   @Autowired
   private IPeopleDao peopleDao;

   protected IBaseDao getDao() { return (IBaseDao)this.peopleDao; }






   public int savePeople(PeopleEntity people) {
     this.peopleDao.saveEntity((BaseEntity)people);
     return saveEntity((BaseEntity)people);
   }







   public void updatePeople(PeopleEntity people) {
     people.setPeopleAppId(BasicUtil.getAppId());
     this.peopleDao.updateEntity((BaseEntity)people);
     updateEntity((BaseEntity)people);
   }





   public void deletePeople(int id) {
     this.peopleDao.deleteEntity(id);
     deleteEntity(id);
   }








   public PeopleEntity getEntityByUserName(String userName, int appId) { return this.peopleDao.getEntityByUserName(userName, appId); }









   public List<PeopleEntity> queryPageListByAppId(int appId) { return this.peopleDao.queryPageListByAppId(appId); }








   public int queryCountByAppId(int appId) { return this.peopleDao.getCount(Integer.valueOf(appId), null); }





   public PeopleEntity getEntityByCode(String userName, String peopleCode, int appId) { return this.peopleDao.getEntityByCode(userName, peopleCode, appId); }




   public int getCountByDate(String peopleDateTime, Integer appId) {
     Map<Object, Object> where = new HashMap<>();
     where.put("peopleDateTime", peopleDateTime);
     return this.peopleDao.getCount(appId, where);
   }


   public void deletePeople(int[] peopleIds) {
     if (peopleIds == null) {
       return;
     }
     this.peopleDao.deletePeoples(peopleIds);
     delete(peopleIds);
   }




   public List<PeopleEntity> queryByAppIdAndMap(int appId, Map whereMap) { return this.peopleDao.queryByAppIdAndMap(appId, whereMap); }





   public int getCountByAppIdAndMap(int appId, Map whereMap) { return this.peopleDao.getCountByAppIdAndMap(appId, whereMap); }





   public PeopleEntity getByPeople(PeopleEntity people, int appId) { return this.peopleDao.getByPeople(people, appId); }





   public PeopleEntity getEntityByMailOrPhone(String userName, int appId) { return this.peopleDao.getEntityByMailOrPhone(userName, appId); }





   public List<PeopleEntity> query(int appId, Map where) { return this.peopleDao.query(appId, where); }
 }


