package net.mingsoft.basic.dao;

import java.util.List;
import net.mingsoft.base.dao.IBaseDao;
import net.mingsoft.basic.entity.CityEntity;
import org.apache.ibatis.annotations.Param;

public interface ICityDao extends IBaseDao {
  List<CityEntity> queryProvince();
  
  List<CityEntity> queryCity(CityEntity paramCityEntity);
  
  List<CityEntity> queryCounty(CityEntity paramCityEntity);
  
  List<CityEntity> queryTown(CityEntity paramCityEntity);
  
  List<CityEntity> queryVillage(CityEntity paramCityEntity);
  
  List<CityEntity> queryByLevel(@Param("level") int paramInt);
}


