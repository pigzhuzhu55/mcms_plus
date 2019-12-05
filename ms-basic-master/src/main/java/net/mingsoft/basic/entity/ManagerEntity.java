 package net.mingsoft.basic.entity;

 import com.fasterxml.jackson.annotation.JsonFormat;
 import java.util.Date;
 import net.mingsoft.base.entity.SessionEntity;







































































 public class ManagerEntity
   extends SessionEntity
 {
   private int managerId;
   private String managerName;
   private String managerNickName;
   private String roleName;
   private String managerPassword;
   private int managerRoleID;
   private int managerPeopleID;
   private int managerSystemSkinId;
   @JsonFormat(pattern = "yyyy-MM-dd")
   private Date managerTime;

   public String getRoleName() { return this.roleName; }







   public void setRoleName(String roleName) { this.roleName = roleName; }







   public Date getManagerTime() { return this.managerTime; }







   public void setManagerTime(Date managerTime) { this.managerTime = managerTime; }







   public int getManagerId() { return this.managerId; }







   public void setManagerId(int managerId) { this.managerId = managerId; }







   public String getManagerName() { return this.managerName; }







   public void setManagerName(String managerName) { this.managerName = managerName; }







   public String getManagerPassword() { return this.managerPassword; }







   public void setManagerPassword(String managerPassword) { this.managerPassword = managerPassword; }







   public int getManagerPeopleID() { return this.managerPeopleID; }







   public void setManagerPeopleID(int managerPeopleID) { this.managerPeopleID = managerPeopleID; }







   public String getManagerNickName() { return this.managerNickName; }







   public void setManagerNickName(String managerNickName) { this.managerNickName = managerNickName; }







   public int getManagerRoleID() { return this.managerRoleID; }







   public void setManagerRoleID(int managerRoleID) { this.managerRoleID = managerRoleID; }



   public int getManagerSystemSkinId() { return this.managerSystemSkinId; }



   public void setManagerSystemSkinId(int managerSystemSkinId) { this.managerSystemSkinId = managerSystemSkinId; }
 }


