package net.mingsoft.mdiy.biz;

import java.util.List;
import java.util.Map;
import net.mingsoft.base.biz.IBaseBiz;
import net.mingsoft.base.entity.BaseEntity;
import net.mingsoft.mdiy.constant.e.FieldSearchEnum;
import net.mingsoft.mdiy.entity.ContentModelFieldEntity;

public interface IContentModelFieldBiz extends IBaseBiz {
  public static final int CHECKBOX = 11;
  
  public static final int RADIO = 10;
  
  public static final int OPTION = 9;
  
  public static final int INT = 4;
  
  public static final int FLOAT = 5;
  
  List<ContentModelFieldEntity> queryListByCmid(int paramInt);
  
  @Deprecated
  int queryCountByCmid(int paramInt);
  
  @Deprecated
  List<BaseEntity> queryByPage(int paramInt, String paramString, boolean paramBoolean);
  
  @Deprecated
  int getCountFieldName(String paramString, int paramInt);
  
  @Deprecated
  ContentModelFieldEntity getEntityByFieldName(String paramString);
  
  ContentModelFieldEntity getEntityByCmId(int paramInt, String paramString);
  
  @Deprecated
  List<Integer> queryListBySQL(String paramString, Map<String, String> paramMap);
  
  List<ContentModelFieldEntity> queryByContentModelId(int paramInt);
  
  @Deprecated
  List<ContentModelFieldEntity> queryByIsSearch(int paramInt, FieldSearchEnum paramFieldSearchEnum);
  
  @Deprecated
  void deleteEntityByFieldCmid(int paramInt);
}


