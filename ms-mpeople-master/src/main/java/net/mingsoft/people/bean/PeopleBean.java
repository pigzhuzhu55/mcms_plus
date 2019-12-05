 package net.mingsoft.people.bean;

 import net.mingsoft.people.entity.PeopleUserEntity;












 public class PeopleBean
   extends PeopleUserEntity
 {
   private String peopleDateTimes;
   private String startTime;
   private String endTime;

   public String getPeopleDateTimes() { return this.peopleDateTimes; }



   public void setPeopleDateTimes(String peopleDateTimes) { this.peopleDateTimes = peopleDateTimes; }


   public String getStartTime() {
     if (this.peopleDateTimes != null && this.peopleDateTimes != "") {
       return this.peopleDateTimes.split("至")[0];
     }
     return this.startTime;
   }


   public void setStartTime(String startTime) { this.startTime = startTime; }


   public String getEndTime() {
     if (this.peopleDateTimes != null && this.peopleDateTimes != "") {
       return this.peopleDateTimes.split("至")[1];
     }
     return this.startTime;
   }


   public void setEndTime(String endTime) { this.endTime = endTime; }
 }


