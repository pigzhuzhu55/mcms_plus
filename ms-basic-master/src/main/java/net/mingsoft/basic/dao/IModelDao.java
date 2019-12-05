package net.mingsoft.basic.dao;

import java.util.List;
import net.mingsoft.base.dao.IBaseDao;
import net.mingsoft.base.entity.BaseEntity;
import net.mingsoft.basic.entity.ModelEntity;
import org.apache.ibatis.annotations.Param;

public interface IModelDao extends IBaseDao {
  List<BaseEntity> queryChildList(int paramInt);
  
  List<BaseEntity> queryModelByRoleId(int paramInt);
  
  ModelEntity getEntityByModelCode(@Param("modelCode") String paramString);
  
  ModelEntity getModel(@Param("modelCodeRegex") String paramString, @Param("modelId") int paramInt);
}


