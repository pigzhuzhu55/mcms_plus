 package net.mingsoft.people.entity;

 import java.util.Date;
























































 public class PeopleUserEntity
   extends PeopleEntity
 {
   private static final long serialVersionUID = 1503454222227L;
   private Integer puPeopleId;
   private String puRealName;
   private String puAddress;
   private String puIcon;
   private String puNickname;
   private Integer puSex;
   private Date puBirthday;
   private String puCard;
   private long puProvince;
   private long puCity;
   private long puDistrict;
   private long puStreet;

   public void setPuPeopleId(Integer puPeopleId) { this.puPeopleId = puPeopleId; }






   public Integer getPuPeopleId() { return this.puPeopleId; }






   public void setPuRealName(String puRealName) { this.puRealName = puRealName; }






   public String getPuRealName() { return this.puRealName; }






   public void setPuAddress(String puAddress) { this.puAddress = puAddress; }






   public String getPuAddress() { return this.puAddress; }






   public void setPuIcon(String puIcon) { this.puIcon = puIcon; }






   public String getPuIcon() { return this.puIcon; }






   public void setPuNickname(String puNickname) { this.puNickname = puNickname; }






   public String getPuNickname() { return this.puNickname; }






   public void setPuSex(Integer puSex) { this.puSex = puSex; }






   public Integer getPuSex() { return this.puSex; }






   public void setPuBirthday(Date puBirthday) { this.puBirthday = puBirthday; }






   public Date getPuBirthday() { return this.puBirthday; }






   public void setPuCard(String puCard) { this.puCard = puCard; }






   public String getPuCard() { return this.puCard; }






   public void setPuProvince(long puProvince) { this.puProvince = puProvince; }






   public long getPuProvince() { return this.puProvince; }






   public void setPuCity(long puCity) { this.puCity = puCity; }






   public long getPuCity() { return this.puCity; }






   public void setPuDistrict(long puDistrict) { this.puDistrict = puDistrict; }






   public long getPuDistrict() { return this.puDistrict; }






   public void setPuStreet(long puStreet) { this.puStreet = puStreet; }






   public long getPuStreet() { return this.puStreet; }
 }


/* Location:              D:\User\Maven\repository\net\mingsoft\ms-mpeople\1.0.11\ms-mpeople-1.0.11.jar!\net\mingsoft\people\entity\PeopleUserEntity.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */