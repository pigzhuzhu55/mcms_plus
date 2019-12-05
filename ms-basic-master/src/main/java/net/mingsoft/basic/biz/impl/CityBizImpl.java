 package net.mingsoft.basic.biz.impl;

 import java.util.ArrayList;
 import java.util.HashMap;
 import java.util.List;
 import java.util.Map;
 import net.mingsoft.base.biz.impl.BaseBizImpl;
 import net.mingsoft.base.dao.IBaseDao;
 import net.mingsoft.basic.bean.CityBean;
 import net.mingsoft.basic.biz.ICityBiz;
 import net.mingsoft.basic.dao.ICityDao;
 import net.mingsoft.basic.entity.CityEntity;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.stereotype.Service;



































 @Service("cityBizImpl")
 public class CityBizImpl
   extends BaseBizImpl
   implements ICityBiz
 {
   @Autowired
   private ICityDao cityDao;

   protected IBaseDao getDao() { return (IBaseDao)this.cityDao; }



   public List<CityEntity> queryProvince() { return this.cityDao.queryProvince(); }



   public List<CityEntity> queryCity(CityEntity cityEntity) { return this.cityDao.queryCity(cityEntity); }



   public List<CityEntity> queryCounty(CityEntity cityEntity) { return this.cityDao.queryCounty(cityEntity); }



   public List<CityEntity> queryTown(CityEntity cityEntity) { return this.cityDao.queryTown(cityEntity); }



   public List<CityEntity> queryVillage(CityEntity cityEntity) { return this.cityDao.queryVillage(cityEntity); }


   public List<CityBean> queryForTree(int level, String type) {
     List<CityEntity> cityList = this.cityDao.queryByLevel(level);
     Map<Long, String> province = new HashMap<>();
     Map<Long, Map<Long, String>> city = new HashMap<>();
     Map<Long, Map<Long, String>> county = new HashMap<>();
     Map<Long, Map<Long, String>> town = new HashMap<>();
     Map<Long, Map<Long, String>> village = new HashMap<>();
     for (CityEntity cityEntity : cityList) {

       if (level >= 1) {

         if (province.get(cityEntity.getProvinceId()) == null) {
           province.put(cityEntity.getProvinceId(), cityEntity.getProvinceName());
         }
         if (level >= 2) {

           if (city.get(cityEntity.getProvinceId()) != null) {
             ((Map<Long, String>)city.get(cityEntity.getProvinceId())).put(cityEntity.getCityId(), cityEntity.getCityName());
           } else {
             Map<Long, String> tempCity = new HashMap<>();
             tempCity.put(cityEntity.getCityId(), cityEntity.getCityName());
             city.put(cityEntity.getProvinceId(), tempCity);
           }
           if (level >= 3) {

             if (county.get(cityEntity.getCityId()) != null) {
               ((Map<Long, String>)county.get(cityEntity.getCityId())).put(cityEntity.getCountyId(), cityEntity.getCountyName());
             } else {
               Map<Long, String> tempCounty = new HashMap<>();
               tempCounty.put(cityEntity.getCountyId(), cityEntity.getCountyName());
               county.put(cityEntity.getCityId(), tempCounty);
             }
             if (level >= 4) {

               if (town.get(cityEntity.getCountyId()) != null) {
                 ((Map<Long, String>)town.get(cityEntity.getCountyId())).put(cityEntity.getTownId(), cityEntity.getTownName());
               } else {
                 Map<Long, String> tempTown = new HashMap<>();
                 tempTown.put(cityEntity.getTownId(), cityEntity.getTownName());
                 town.put(cityEntity.getCountyId(), tempTown);
               }
               if (level >= 5) {

                 if (village.get(cityEntity.getTownId()) != null) {
                   ((Map<Long, String>)village.get(cityEntity.getTownId())).put(cityEntity.getVillageId(), cityEntity.getVillageName()); continue;
                 }
                 Map<Long, String> tempVillage = new HashMap<>();
                 tempVillage.put(cityEntity.getVillageId(), cityEntity.getVillageName());
                 village.put(cityEntity.getTownId(), tempVillage);
               }
             }
           }
         }
       }
     }



     List<CityBean> cityBeanList = new ArrayList<>();
     if ("tree".equals(type)) {

       if (level >= 1)
       {
         for (Long provinceKey : province.keySet()) {
           CityBean provinceBean = new CityBean();
           String provinceName = province.get(provinceKey);
           provinceBean.setId(provinceKey);
           provinceBean.setName(provinceName);
           provinceBean.setChildrensList(new ArrayList());
           if (level >= 2) {

             Map<Long, String> cityMap = city.get(provinceKey);
             for (Long cityKey : cityMap.keySet()) {
               String cityName = cityMap.get(cityKey);
               CityBean cityBean = new CityBean();
               cityBean.setId(cityKey);
               cityBean.setName(cityName);
               cityBean.setChildrensList(new ArrayList());
               if (level >= 3) {

                 Map<Long, String> countyMap = county.get(cityKey);
                 for (Long countyKey : countyMap.keySet()) {
                   String countyName = countyMap.get(countyKey);
                   CityBean countyBean = new CityBean();
                   countyBean.setId(countyKey);
                   countyBean.setName(countyName);
                   countyBean.setChildrensList(new ArrayList());
                   if (level >= 4) {

                     Map<Long, String> townMap = town.get(countyKey);
                     for (Long townKey : townMap.keySet()) {
                       String townName = townMap.get(townKey);
                       CityBean townBean = new CityBean();
                       townBean.setId(townKey);
                       townBean.setName(townName);
                       townBean.setChildrensList(new ArrayList());
                       if (level >= 5) {

                         Map<Long, String> villageMap = village.get(townKey);
                         for (Long villageKey : villageMap.keySet()) {
                           String villageName = villageMap.get(villageKey);
                           CityBean villageBean = new CityBean();
                           villageBean.setId(villageKey);
                           villageBean.setName(villageName);
                           townBean.getChildrensList().add(villageBean);
                         }
                       }
                       countyBean.getChildrensList().add(townBean);
                     }
                   }
                   cityBean.getChildrensList().add(countyBean);
                 }
               }
               provinceBean.getChildrensList().add(cityBean);
             }
           }
           cityBeanList.add(provinceBean);
         }
       }
     } else {
       CityBean cityBean = new CityBean();

       if (level >= 1) {
         for (Long provinceId : province.keySet()) {
           cityBean = new CityBean();
           cityBean.setId(provinceId);
           cityBean.setName(province.get(provinceId));
           cityBeanList.add(cityBean);
         }
       }

       if (level >= 2) {
         for (Long provinceId : city.keySet()) {
           Map<Long, String> _city = city.get(provinceId);
           for (Long cityId : _city.keySet()) {
             cityBean = new CityBean();
             cityBean.setId(cityId);
             cityBean.setName(_city.get(cityId));
             cityBean.setParentId(provinceId);
             cityBeanList.add(cityBean);
           }
         }
       }

       if (level >= 3) {
         for (Long cityId : county.keySet()) {
           Map<Long, String> _county = county.get(cityId);
           for (Long countyId : _county.keySet()) {
             cityBean = new CityBean();
             cityBean.setId(countyId);
             cityBean.setName(_county.get(countyId));
             cityBean.setParentId(cityId);
             cityBeanList.add(cityBean);
           }
         }
       }

       if (level >= 4) {
         for (Long countyId : town.keySet()) {
           Map<Long, String> _town = county.get(countyId);
           for (Long townId : _town.keySet()) {
             cityBean = new CityBean();
             cityBean.setId(townId);
             cityBean.setName(_town.get(townId));
             cityBean.setParentId(countyId);
             cityBeanList.add(cityBean);
           }
         }
       }

       if (level >= 5) {
         for (Long countyId : village.keySet()) {
           Map<Long, String> _village = county.get(countyId);
           for (Long villageId : _village.keySet()) {
             cityBean = new CityBean();
             cityBean.setId(villageId);
             cityBean.setName(_village.get(villageId));
             cityBean.setParentId(countyId);
             cityBeanList.add(cityBean);
           }
         }
       }
     }

     return cityBeanList;
   }



   public List<CityEntity> queryByLevel(int level) { return this.cityDao.queryByLevel(level); }
 }


