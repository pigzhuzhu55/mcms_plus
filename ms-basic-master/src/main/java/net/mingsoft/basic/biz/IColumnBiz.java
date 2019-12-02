package net.mingsoft.basic.biz;

import java.util.List;
import net.mingsoft.basic.entity.ColumnEntity;

public interface IColumnBiz extends ICategoryBiz {
  List<ColumnEntity> queryColumnListByWebsiteId(int paramInt);
  
  List<ColumnEntity> queryChild(int paramInt1, int paramInt2, Integer paramInteger1, Integer paramInteger2);
  
  List<ColumnEntity> queryAll(int paramInt1, int paramInt2);
  
  List<ColumnEntity> queryAll(int paramInt);
  
  List<ColumnEntity> querySibling(int paramInt, Integer paramInteger);
  
  List<ColumnEntity> queryTopSiblingListByColumnId(int paramInt, Integer paramInteger);
  
  List<ColumnEntity> queryChildListByColumnId(int paramInt, Integer paramInteger);
  
  int[] queryChildIdsByColumnId(int paramInt1, int paramInt2);
  
  List<ColumnEntity> queryParentColumnByColumnId(int paramInt);
  
  int queryColumnChildListCountByWebsiteId(int paramInt1, int paramInt2);
  
  void save(ColumnEntity paramColumnEntity, int paramInt1, int paramInt2);
  
  void delete(int[] paramArrayOfint);
  
  void update(ColumnEntity paramColumnEntity, int paramInt1, int paramInt2);
}


/* Location:              D:\User\Maven\repository\net\mingsoft\ms-basic\1.0.16\ms-basic-1.0.16.jar!\net\mingsoft\basic\biz\IColumnBiz.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */