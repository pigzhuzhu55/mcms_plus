package net.mingsoft.mdiy.dao;

import java.util.List;
import java.util.Map;
import net.mingsoft.base.dao.IBaseDao;
import net.mingsoft.base.entity.BaseEntity;
import net.mingsoft.mdiy.entity.ContentModelFieldEntity;
import org.apache.ibatis.annotations.Param;

public interface IContentModelFieldDao extends IBaseDao {
  List<ContentModelFieldEntity> queryListByCmid(int paramInt);
  
  int queryCountByCmid(int paramInt);
  
  List<BaseEntity> queryByPage(@Param("fieldCmid") int paramInt1, @Param("pageNo") int paramInt2, @Param("pageSize") int paramInt3, @Param("orderBy") String paramString, @Param("order") boolean paramBoolean);
  
  int getCountFieldName(@Param("fieldFieldName") String paramString, @Param("fieldCmdId") int paramInt);
  
  @Deprecated
  List<BaseEntity> queryListByCmId(@Param("fieldCmId") int paramInt);
  
  @Deprecated
  ContentModelFieldEntity getEntityByFieldName(String paramString);
  
  ContentModelFieldEntity getEntityByCmId(@Param("cmId") int paramInt, @Param("fieldFieldName") String paramString);
  
  @Deprecated
  List<Map> queryListBySQL(@Param("table") String paramString, @Param("diyFieldName") Map<String, String> paramMap);
  
  @Deprecated
  List<Map> queryListByListField(@Param("table") String paramString1, @Param("where") String paramString2);
  
  List<ContentModelFieldEntity> queryByContentModelId(@Param("contentModelId") int paramInt);
  
  @Deprecated
  List<ContentModelFieldEntity> queryByIsSearch(@Param("contentModelId") Integer paramInteger1, @Param("fieldIsSearch") Integer paramInteger2);
  
  @Deprecated
  void deleteEntityByFieldCmid(@Param("fieldCmid") int paramInt);
}


