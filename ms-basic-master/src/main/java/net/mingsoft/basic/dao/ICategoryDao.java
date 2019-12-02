package net.mingsoft.basic.dao;

import java.util.List;
import net.mingsoft.base.dao.IBaseDao;
import net.mingsoft.basic.entity.CategoryEntity;
import org.apache.ibatis.annotations.Param;

public interface ICategoryDao extends IBaseDao {
  @Deprecated
  List<CategoryEntity> queryChilds(@Param("category") CategoryEntity paramCategoryEntity);
  
  @Deprecated
  int count(@Param("category") CategoryEntity paramCategoryEntity);
  
  @Deprecated
  List<CategoryEntity> queryBatchCategoryById(@Param("listId") List<Integer> paramList);
  
  @Deprecated
  List<CategoryEntity> queryByAppIdOrModelId(@Param("appId") Integer paramInteger1, @Param("modelId") Integer paramInteger2);
  
  List<CategoryEntity> queryChildren(CategoryEntity paramCategoryEntity);
  
  List queryByDictId(CategoryEntity paramCategoryEntity);
}


/* Location:              D:\User\Maven\repository\net\mingsoft\ms-basic\1.0.16\ms-basic-1.0.16.jar!\net\mingsoft\basic\dao\ICategoryDao.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */