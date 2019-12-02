package net.mingsoft.basic.biz;

import java.util.List;
import net.mingsoft.base.biz.IBaseBiz;
import net.mingsoft.basic.bean.CityBean;
import net.mingsoft.basic.entity.CityEntity;

public interface ICityBiz extends IBaseBiz {
  List<CityEntity> queryProvince();
  
  List<CityEntity> queryCity(CityEntity paramCityEntity);
  
  List<CityEntity> queryCounty(CityEntity paramCityEntity);
  
  List<CityEntity> queryTown(CityEntity paramCityEntity);
  
  List<CityEntity> queryVillage(CityEntity paramCityEntity);
  
  List<CityBean> queryForTree(int paramInt, String paramString);
  
  List<CityEntity> queryByLevel(int paramInt);
}


/* Location:              D:\User\Maven\repository\net\mingsoft\ms-basic\1.0.16\ms-basic-1.0.16.jar!\net\mingsoft\basic\biz\ICityBiz.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */