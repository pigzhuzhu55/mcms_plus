 package net.mingsoft.mdiy.entity;

 import net.mingsoft.basic.entity.BaseEntity;




















































 public class SearchEntity
   extends BaseEntity
 {
   private int searchId;
   private String searchName;
   private String searchTemplets;
   private String searchType;

   public int getSearchId() { return this.searchId; }







   public void setSearchId(int searchId) { this.searchId = searchId; }







   public String getSearchName() { return this.searchName; }







   public void setSearchName(String searchName) { this.searchName = searchName; }







   public String getSearchTemplets() { return this.searchTemplets; }







   public void setSearchTemplets(String searchTemplets) { this.searchTemplets = searchTemplets; }




   public String getSearchType() { return this.searchType; }



   public void setSearchType(String searchType) { this.searchType = searchType; }
 }


