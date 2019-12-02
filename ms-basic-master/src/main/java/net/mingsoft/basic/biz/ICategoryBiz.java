package net.mingsoft.basic.biz;

import java.util.List;
import net.mingsoft.base.biz.IBaseBiz;
import net.mingsoft.basic.entity.CategoryEntity;

public interface ICategoryBiz extends IBaseBiz {
  int count(CategoryEntity paramCategoryEntity);
  
  void deleteCategory(int paramInt);
  
  List<CategoryEntity> queryBatchCategoryById(List<Integer> paramList);
  
  List<CategoryEntity> queryByAppIdOrModelId(Integer paramInteger1, Integer paramInteger2);
  
  List queryByDictId(CategoryEntity paramCategoryEntity);
  
  @Deprecated
  List<CategoryEntity> queryChildrenCategory(int paramInt1, int paramInt2, int paramInt3);
  
  @Deprecated
  int[] queryChildrenCategoryIds(int paramInt1, int paramInt2, int paramInt3);
  
  List<CategoryEntity> queryChilds(CategoryEntity paramCategoryEntity);
  
  int saveCategory(CategoryEntity paramCategoryEntity);
  
  void updateCategory(CategoryEntity paramCategoryEntity);
}


/* Location:              D:\User\Maven\repository\net\mingsoft\ms-basic\1.0.16\ms-basic-1.0.16.jar!\net\mingsoft\basic\biz\ICategoryBiz.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */