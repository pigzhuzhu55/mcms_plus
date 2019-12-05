 package net.mingsoft.people.entity;

 import com.fasterxml.jackson.annotation.JsonFormat;
 import com.fasterxml.jackson.annotation.JsonIgnore;
 import java.sql.Timestamp;
 import java.util.Date;
 import javax.xml.bind.annotation.XmlTransient;
 import net.mingsoft.base.constant.e.BaseEnum;
 import net.mingsoft.base.entity.SessionEntity;
 import org.apache.commons.lang3.StringUtils;
 import org.springframework.format.annotation.DateTimeFormat;






































































 public class PeopleEntity
   extends SessionEntity
 {
   private int peopleAppId;
   private int peopleAutoLogin;
   private String peopleCode;
   private String peopleIp;
   @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
   @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
   private Timestamp peopleCodeSendDate;
   @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
   @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
   private Date peopleDateTime;
   private int peopleId;
   private String peopleMail;
   private int peopleMailCheck = -1;





   private String peopleName;





   private String peopleOldPassword;




   private String peoplePassword;




   private String peoplePhone;




   private int peoplePhoneCheck = -1;




   private Integer peopleState;



   private PeopleUserEntity peopleUser;



   private boolean newUser;




   public boolean isNewUser() { return StringUtils.isBlank(getPeoplePassword()); }









   public int getPeopleAppId() { return this.peopleAppId; }



   public int getPeopleAutoLogin() { return this.peopleAutoLogin; }








   public String getPeopleCode() { return this.peopleCode; }



   public Timestamp getPeopleCodeSendDate() { return this.peopleCodeSendDate; }








   public Date getPeopleDateTime() { return this.peopleDateTime; }








   public int getPeopleId() { return this.peopleId; }








   public String getPeopleMail() { return this.peopleMail; }



   public int getPeopleMailCheck() { return this.peopleMailCheck; }








   public String getPeopleName() { return this.peopleName; }



   public String getPeopleOldPassword() { return this.peopleOldPassword; }








   public String getPeoplePassword() { return this.peoplePassword; }








   public String getPeoplePhone() { return this.peoplePhone; }



   public int getPeoplePhoneCheck() { return this.peoplePhoneCheck; }








   public Integer getPeopleState() { return this.peopleState; }



   public PeopleUserEntity getPeopleUser() { return this.peopleUser; }








   public void setPeopleAppId(int peopleAppId) { this.peopleAppId = peopleAppId; }



   public void setPeopleAutoLogin(int peopleAutoLogin) { this.peopleAutoLogin = peopleAutoLogin; }








   public void setPeopleCode(String peopleCode) { this.peopleCode = peopleCode; }



   public void setPeopleCodeSendDate(Timestamp peopleCodeSendDate) { this.peopleCodeSendDate = peopleCodeSendDate; }








   public void setPeopleDateTime(Date peopleDateTime) { this.peopleDateTime = peopleDateTime; }








   public void setPeopleId(int peopleId) { this.peopleId = peopleId; }








   public void setPeopleMail(String peopleMail) { this.peopleMail = peopleMail; }



   @JsonIgnore
   @XmlTransient
   public void setPeopleMailCheck(BaseEnum check) { this.peopleMailCheck = check.toInt(); }








   @Deprecated
   public void setPeopleMailCheck(int peopleMailCheck) { this.peopleMailCheck = peopleMailCheck; }








   public void setPeopleName(String peopleName) { this.peopleName = peopleName; }



   public void setPeopleOldPassword(String peopleOldPassword) { this.peopleOldPassword = peopleOldPassword; }








   public void setPeoplePassword(String peoplePassword) { this.peoplePassword = peoplePassword; }








   public void setPeoplePhone(String peoplePhone) { this.peoplePhone = peoplePhone; }




   @JsonIgnore
   @XmlTransient
   public void setPeoplePhoneCheck(BaseEnum check) { this.peoplePhoneCheck = check.toInt(); }








   @Deprecated
   public void setPeoplePhoneCheck(int peoplePhoneCheck) { this.peoplePhoneCheck = peoplePhoneCheck; }








   @JsonIgnore
   public void setPeopleStateEnum(BaseEnum e) { this.peopleState = Integer.valueOf(e.toInt()); }









   public void setPeopleState(Integer peopleState) { this.peopleState = peopleState; }



   public void setPeopleUser(PeopleUserEntity peopleUser) { this.peopleUser = peopleUser; }






   public String getPeopleIp() { return this.peopleIp; }





   public void setPeopleIp(String peopleIp) { this.peopleIp = peopleIp; }
 }


