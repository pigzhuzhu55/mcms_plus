package net.mingsoft.basic.biz;

import java.util.List;
import net.mingsoft.base.biz.IBaseBiz;
import net.mingsoft.base.constant.e.BaseEnum;
import net.mingsoft.base.entity.BaseEntity;
import net.mingsoft.basic.entity.ModelEntity;
import net.mingsoft.basic.entity.RoleModelEntity;

public interface IModelBiz extends IBaseBiz {
  public static final String CATEGORY_MODEL = "99";
  
  public static final String BASIC_MODEL = "98";
  
  public static final String ORDER_MODEL = "97";
  
  public static final String ORDER_STATUS_MODEL = "96";
  
  List<BaseEntity> queryModelByRoleId(int paramInt);
  
  ModelEntity getEntityByModelCode(BaseEnum paramBaseEnum);
  
  ModelEntity getEntityByModelCode(String paramString);
  
  ModelEntity getModel(String paramString, int paramInt);
  
  void reModel(ModelEntity paramModelEntity, String paramString, int paramInt1, List<RoleModelEntity> paramList, int paramInt2);
  
  void jsonToModel(String paramString, int paramInt);
}


