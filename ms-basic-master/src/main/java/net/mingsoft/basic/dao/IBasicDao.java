package net.mingsoft.basic.dao;

import java.util.List;
import java.util.Map;
import net.mingsoft.base.dao.IBaseDao;
import net.mingsoft.basic.entity.BasicEntity;
import org.apache.ibatis.annotations.Param;

public interface IBasicDao extends IBaseDao {
  void updateHit(@Param("basicId") int paramInt, @Param("num") Integer paramInteger);
  
  void updateFieldNum(@Param("basicId") int paramInt, @Param("num") Integer paramInteger, @Param("field") String paramString);
  
  List<BasicEntity> query(@Param("appId") Integer paramInteger1, @Param("categoryId") Integer paramInteger2, @Param("keyWord") String paramString1, @Param("begin") Integer paramInteger3, @Param("end") Integer paramInteger4, @Param("orderField") String paramString2, @Param("ad") Boolean paramBoolean, @Param("modelId") Integer paramInteger5, @Param("where") Map paramMap);
}


