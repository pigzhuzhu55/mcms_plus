 package net.mingsoft.basic.entity;

 import net.mingsoft.base.entity.BaseEntity;











































 public class CityEntity
   extends BaseEntity
 {
   private static final long serialVersionUID = 1501138049606L;
   private Long provinceId;
   private String provinceName;
   private Long cityId;
   private String cityName;
   private String cityPy;
   private Long countyId;
   private String countyName;
   private Long townId;
   private String townName;
   private Long villageId;
   private String villageName;

   public CityEntity() {}

   public CityEntity(Long provinceId) { this.provinceId = provinceId; }



   public CityEntity(Long provinceId, String provinceName) {
     this.provinceId = provinceId;
     this.provinceName = provinceName;
   }


   public CityEntity(Long provinceId, String provinceName, Long cityId) {
     this.provinceId = provinceId;
     this.provinceName = provinceName;
     this.cityId = cityId;
   }


   public CityEntity(Long provinceId, String provinceName, Long cityId, String cityName) {
     this.provinceId = provinceId;
     this.provinceName = provinceName;
     this.cityId = cityId;
     this.cityName = cityName;
   }


   public CityEntity(Long provinceId, String provinceName, Long cityId, String cityName, Long countyId) {
     this.provinceId = provinceId;
     this.provinceName = provinceName;
     this.cityId = cityId;
     this.cityName = cityName;
     this.countyId = countyId;
   }


   public CityEntity(Long provinceId, String provinceName, Long cityId, String cityName, Long countyId, String countyName) {
     this.provinceId = provinceId;
     this.provinceName = provinceName;
     this.cityId = cityId;
     this.cityName = cityName;
     this.countyId = countyId;
     this.countyName = countyName;
   }


   public CityEntity(Long provinceId, String provinceName, Long cityId, String cityName, Long countyId, String countyName, Long townId) {
     this.provinceId = provinceId;
     this.provinceName = provinceName;
     this.cityId = cityId;
     this.cityName = cityName;
     this.countyId = countyId;
     this.countyName = countyName;
     this.townId = townId;
   }


   public CityEntity(Long provinceId, String provinceName, Long cityId, String cityName, Long countyId, String countyName, Long townId, String townName) {
     this.provinceId = provinceId;
     this.provinceName = provinceName;
     this.cityId = cityId;
     this.cityName = cityName;
     this.countyId = countyId;
     this.countyName = countyName;
     this.townId = townId;
     this.townName = townName;
   }


   public CityEntity(Long provinceId, String provinceName, Long cityId, String cityName, Long countyId, String countyName, Long townId, String townName, Long villageId) {
     this.provinceId = provinceId;
     this.provinceName = provinceName;
     this.cityId = cityId;
     this.cityName = cityName;
     this.countyId = countyId;
     this.countyName = countyName;
     this.townId = townId;
     this.townName = townName;
     this.villageId = villageId;
   }


   public CityEntity(Long provinceId, String provinceName, Long cityId, String cityName, Long countyId, String countyName, Long townId, String townName, Long villageId, String villageName) {
     this.provinceId = provinceId;
     this.provinceName = provinceName;
     this.cityId = cityId;
     this.cityName = cityName;
     this.countyId = countyId;
     this.countyName = countyName;
     this.townId = townId;
     this.townName = townName;
     this.villageId = villageId;
     this.villageName = villageName;
   }








   public void setProvinceId(Long provinceId) { this.provinceId = provinceId; }






   public Long getProvinceId() { return this.provinceId; }






   public void setProvinceName(String provinceName) { this.provinceName = provinceName; }






   public String getProvinceName() { return this.provinceName; }






   public void setCityId(Long cityId) { this.cityId = cityId; }






   public Long getCityId() { return this.cityId; }






   public void setCityName(String cityName) { this.cityName = cityName; }






   public String getCityName() { return this.cityName; }






   public void setCountyId(Long countyId) { this.countyId = countyId; }






   public Long getCountyId() { return this.countyId; }






   public void setCountyName(String countyName) { this.countyName = countyName; }






   public String getCountyName() { return this.countyName; }






   public void setTownId(Long townId) { this.townId = townId; }






   public Long getTownId() { return this.townId; }






   public void setTownName(String townName) { this.townName = townName; }






   public String getTownName() { return this.townName; }






   public void setVillageId(Long villageId) { this.villageId = villageId; }






   public Long getVillageId() { return this.villageId; }






   public void setVillageName(String villageName) { this.villageName = villageName; }






   public String getVillageName() { return this.villageName; }





   public String getCityPy() { return this.cityPy; }





   public void setCityPy(String cityPy) { this.cityPy = cityPy; }
 }


