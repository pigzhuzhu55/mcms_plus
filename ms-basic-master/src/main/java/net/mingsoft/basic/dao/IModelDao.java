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


/* Location:              D:\User\Maven\repository\net\mingsoft\ms-basic\1.0.16\ms-basic-1.0.16.jar!\net\mingsoft\basic\dao\IModelDao.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */