package net.mingsoft.basic.dao;

import java.util.List;
import net.mingsoft.base.dao.IBaseDao;
import net.mingsoft.basic.entity.ColumnEntity;
import org.apache.ibatis.annotations.Param;

public interface IColumnDao extends IBaseDao {
  List<ColumnEntity> queryColumnListByWebsiteId(@Param("appId") int paramInt);
  
  List<ColumnEntity> queryColumnByCategoryIdAndWebsiteIdAndModelId(@Param("categoryCategoryId") int paramInt1, @Param("appId") int paramInt2, @Param("modelId") Integer paramInteger1, @Param("size") Integer paramInteger2);
  
  List<Integer> queryColumnChildIdList(@Param("categoryId") int paramInt1, @Param("appId") int paramInt2);
  
  int queryColumnChildListCountByWebsiteId(@Param("categoryCategoryId") int paramInt1, @Param("appId") int paramInt2);
  
  List<ColumnEntity> queryByAppIdAndModelId(@Param("appId") int paramInt1, @Param("modelId") int paramInt2);
}


/* Location:              D:\User\Maven\repository\net\mingsoft\ms-basic\1.0.16\ms-basic-1.0.16.jar!\net\mingsoft\basic\dao\IColumnDao.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */