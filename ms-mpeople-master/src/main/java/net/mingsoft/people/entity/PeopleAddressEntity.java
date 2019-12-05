 package net.mingsoft.people.entity;

 import net.mingsoft.base.constant.e.BaseEnum;
 import net.mingsoft.base.entity.BaseEntity;


















































































 public class PeopleAddressEntity
   extends BaseEntity
 {
   int peopleAddressId;
   int peopleAddressPeopleId;
   String peopleAddressConsigneeName;
   String peopleAddressProvince;
   long peopleAddressProvinceId;
   String peopleAddressCity;
   long peopleAddressCityId;
   String peopleAddressDistrict;
   long peopleAddressDistrictId;
   String peopleAddressStreet;
   long peopleAddressStreetId;
   String peopleAddressAddress;
   String peopleAddressMail;
   String peopleAddressPhone;
   int peopleAddressDefault;
   int peopleAddressAppId;

   public int getPeopleAddressId() { return this.peopleAddressId; }



   public void setPeopleAddressId(int peopleAddressId) { this.peopleAddressId = peopleAddressId; }



   public int getPeopleAddressPeopleId() { return this.peopleAddressPeopleId; }



   public void setPeopleAddressPeopleId(int peopleAddressPeopleId) { this.peopleAddressPeopleId = peopleAddressPeopleId; }



   public String getPeopleAddressConsigneeName() { return this.peopleAddressConsigneeName; }



   public void setPeopleAddressConsigneeName(String peopleAddressConsigneeName) { this.peopleAddressConsigneeName = peopleAddressConsigneeName; }



   public String getPeopleAddressProvince() { return this.peopleAddressProvince; }



   public void setPeopleAddressProvince(String peopleAddressProvince) { this.peopleAddressProvince = peopleAddressProvince; }



   public String getPeopleAddressCity() { return this.peopleAddressCity; }



   public void setPeopleAddressCity(String peopleAddressCity) { this.peopleAddressCity = peopleAddressCity; }



   public String getPeopleAddressDistrict() { return this.peopleAddressDistrict; }



   public void setPeopleAddressDistrict(String peopleAddressDistrict) { this.peopleAddressDistrict = peopleAddressDistrict; }



   public String getPeopleAddressAddress() { return this.peopleAddressAddress; }



   public void setPeopleAddressAddress(String peopleAddressAddress) { this.peopleAddressAddress = peopleAddressAddress; }



   public String getPeopleAddressMail() { return this.peopleAddressMail; }



   public void setPeopleAddressMail(String peopleAddressMail) { this.peopleAddressMail = peopleAddressMail; }



   public String getPeopleAddressPhone() { return this.peopleAddressPhone; }



   public void setPeopleAddressPhone(String peopleAddressPhone) { this.peopleAddressPhone = peopleAddressPhone; }



   public int getPeopleAddressDefault() { return this.peopleAddressDefault; }







   @Deprecated
   public void setPeopleAddressDefault(int peopleAddressDefault) { this.peopleAddressDefault = peopleAddressDefault; }






   public void setPeopleAddressDefault(BaseEnum peopleAddressDefault) { this.peopleAddressDefault = peopleAddressDefault.toInt(); }



   public int getPeopleAddressAppId() { return this.peopleAddressAppId; }



   public void setPeopleAddressAppId(int peopleAddressAppId) { this.peopleAddressAppId = peopleAddressAppId; }



   public String getPeopleAddressStreet() { return this.peopleAddressStreet; }



   public void setPeopleAddressStreet(String peopleAddressStreet) { this.peopleAddressStreet = peopleAddressStreet; }



   public long getPeopleAddressProvinceId() { return this.peopleAddressProvinceId; }



   public void setPeopleAddressProvinceId(long peopleAddressProvinceId) { this.peopleAddressProvinceId = peopleAddressProvinceId; }



   public long getPeopleAddressCityId() { return this.peopleAddressCityId; }



   public void setPeopleAddressCityId(long peopleAddressCityId) { this.peopleAddressCityId = peopleAddressCityId; }



   public long getPeopleAddressDistrictId() { return this.peopleAddressDistrictId; }



   public void setPeopleAddressDistrictId(long peopleAddressDistrictId) { this.peopleAddressDistrictId = peopleAddressDistrictId; }



   public long getPeopleAddressStreetId() { return this.peopleAddressStreetId; }



   public void setPeopleAddressStreetId(long peopleAddressStreetId) { this.peopleAddressStreetId = peopleAddressStreetId; }
 }


