 package net.mingsoft.basic.biz.impl;

 import cn.hutool.core.util.ObjectUtil;
 import com.alibaba.fastjson.JSONArray;
 import java.sql.Timestamp;
 import java.util.ArrayList;
 import java.util.List;
 import net.mingsoft.base.biz.impl.BaseBizImpl;
 import net.mingsoft.base.constant.e.BaseEnum;
 import net.mingsoft.base.dao.IBaseDao;
 import net.mingsoft.base.entity.BaseEntity;
 import net.mingsoft.basic.biz.IModelBiz;
 import net.mingsoft.basic.biz.IRoleModelBiz;
 import net.mingsoft.basic.dao.IModelDao;
 import net.mingsoft.basic.entity.ModelEntity;
 import net.mingsoft.basic.entity.RoleModelEntity;
 import org.apache.commons.lang3.StringUtils;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.stereotype.Service;

































 @Service("modelBiz")
 public class ModelBizImpl
   extends BaseBizImpl
   implements IModelBiz
 {
   private IModelDao modelDao;
   @Autowired
   private IRoleModelBiz roleModelBiz;

   public ModelEntity getEntityByModelCode(BaseEnum modelCode) { return this.modelDao.getEntityByModelCode(modelCode.toString()); }





   public ModelEntity getEntityByModelCode(String modelCode) { return this.modelDao.getEntityByModelCode(modelCode); }















   public IModelDao getModelDao() { return this.modelDao; }



   @Autowired
   public void setModelDao(IModelDao modelDao) { this.modelDao = modelDao; }





   protected IBaseDao getDao() { return (IBaseDao)this.modelDao; }





   public ModelEntity getModel(String modelType, int modelId) { return this.modelDao.getModel(modelType, modelId); }




   public List<BaseEntity> queryModelByRoleId(int roleId) { return this.modelDao.queryModelByRoleId(roleId); }




   public void reModel(ModelEntity modelParent, String parentIds, int mangerRoleId, List<RoleModelEntity> roleModels, int parentId) {
     modelParent.setModelIsMenu((ObjectUtil.isNotNull(modelParent.getModelChildList()) && modelParent.getModelChildList().size() > 0) ?
         Integer.valueOf(1) : Integer.valueOf(0));

     modelParent.setModelModelId(parentId);
     modelParent.setModelDatetime(new Timestamp(System.currentTimeMillis()));
     modelParent.setModelParentIds(parentIds);
     ModelEntity modelParentEntity = getEntityByModelCode(modelParent.getModelCode());
     if (modelParentEntity == null) {
       saveEntity((BaseEntity)modelParent);
       RoleModelEntity roleModel = new RoleModelEntity();
       roleModel.setRoleId(mangerRoleId);
       roleModel.setModelId(modelParent.getModelId());
       roleModels.add(roleModel);
     } else {
       modelParent.setModelId(modelParentEntity.getModelId());
       updateEntity((BaseEntity)modelParent);
     }
     if (ObjectUtil.isNotNull(modelParent.getModelChildList()) && modelParent.getModelChildList().size() > 0) {
       for (ModelEntity modelEntity : modelParent.getModelChildList()) {
         reModel(modelEntity, StringUtils.isBlank(parentIds) ? (modelParent.getModelId() + "") : (parentIds + "," + modelParent.getModelId()), mangerRoleId, roleModels, modelParent.getModelId());
       }
     }
   }



   public void jsonToModel(String menuStr, int mangerRoleId) {
     List<RoleModelEntity> roleModels = new ArrayList<>();
     if (StringUtils.isNotBlank(menuStr)) {

       List<ModelEntity> list = JSONArray.parseArray(menuStr, ModelEntity.class);
       for (ModelEntity modelParent : list) {
         reModel(modelParent, null, mangerRoleId, roleModels, 0);
       }

       if (roleModels.size() > 0)
         this.roleModelBiz.saveEntity(roleModels);
     }
   }
 }


