 package net.mingsoft.basic.entity;

 import net.mingsoft.base.entity.BaseEntity;

















































 public class RoleEntity
   extends BaseEntity
 {
   private int roleId;
   private String roleName;
   private int roleManagerId;
   private int appId;

   public int getRoleId() { return this.roleId; }







   public void setRoleId(int roleId) { this.roleId = roleId; }







   public String getRoleName() { return this.roleName; }







   public void setRoleName(String roleName) { this.roleName = roleName; }







   public int getRoleManagerId() { return this.roleManagerId; }







   public void setRoleManagerId(int rolePeopleId) { this.roleManagerId = rolePeopleId; }



   public int getAppId() { return this.appId; }



   public void setAppId(int appId) { this.appId = appId; }
 }


