 package net.mingsoft.people.biz.impl;

 import net.mingsoft.base.biz.impl.BaseBizImpl;
 import net.mingsoft.base.dao.IBaseDao;
 import net.mingsoft.people.biz.IPeopleAddressBiz;
 import net.mingsoft.people.dao.IPeopleAddressDao;
 import net.mingsoft.people.entity.PeopleAddressEntity;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.stereotype.Service;








































 @Service("peopleAddressBizImpl")
 public class PeopleAddressBizImpl
   extends BaseBizImpl
   implements IPeopleAddressBiz
 {
   @Autowired
   private IPeopleAddressDao peopleAddressDao;

   protected IBaseDao getDao() { return (IBaseDao)this.peopleAddressDao; }




   public void setDefault(PeopleAddressEntity peopleAddress) { this.peopleAddressDao.setDefault(peopleAddress); }
 }


/* Location:              D:\User\Maven\repository\net\mingsoft\ms-mpeople\1.0.11\ms-mpeople-1.0.11.jar!\net\mingsoft\people\biz\impl\PeopleAddressBizImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */