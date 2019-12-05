 package net.mingsoft.basic.entity;

 import java.util.List;
 import net.mingsoft.base.entity.BaseEntity;















































 public class ManagerSessionEntity
   extends ManagerEntity
 {
   private int managerParentID;
   private List<BaseEntity> managerChildIDs;
   private int basicId;
   private String style;

   public String getStyle() { return this.style; }



   public void setStyle(String style) { this.style = style; }








   public int getManagerParentID() { return this.managerParentID; }







   public void setManagerParentID(int managerParentID) { this.managerParentID = managerParentID; }







   public List<BaseEntity> getManagerChildIDs() { return this.managerChildIDs; }







   public void setManagerChildIDs(List<BaseEntity> managerChildIDs) { this.managerChildIDs = managerChildIDs; }







   public int getBasicId() { return this.basicId; }







   public void setBasicId(int basicId) { this.basicId = basicId; }
 }


