 package net.mingsoft.basic.action;

 import com.alibaba.fastjson.JSONArray;
 import com.alibaba.fastjson.JSONObject;
 import io.swagger.annotations.Api;
 import io.swagger.annotations.ApiImplicitParam;
 import io.swagger.annotations.ApiImplicitParams;
 import io.swagger.annotations.ApiOperation;
 import java.sql.Timestamp;
 import java.util.ArrayList;
 import java.util.HashMap;
 import java.util.List;
 import java.util.Map;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;
 import net.mingsoft.base.constant.e.BaseEnum;
 import net.mingsoft.base.entity.BaseEntity;
 import net.mingsoft.basic.bean.EUListBean;
 import net.mingsoft.basic.biz.IManagerBiz;
 import net.mingsoft.basic.biz.IModelBiz;
 import net.mingsoft.basic.biz.IRoleModelBiz;
 import net.mingsoft.basic.constant.ModelCode;
 import net.mingsoft.basic.entity.ManagerEntity;
 import net.mingsoft.basic.entity.ManagerSessionEntity;
 import net.mingsoft.basic.entity.ModelEntity;
 import net.mingsoft.basic.entity.RoleModelEntity;
 import net.mingsoft.basic.util.BasicUtil;
 import net.mingsoft.basic.util.StringUtil;
 import org.apache.shiro.authz.annotation.RequiresPermissions;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.stereotype.Controller;
 import org.springframework.ui.ModelMap;
 import org.springframework.web.bind.annotation.GetMapping;
 import org.springframework.web.bind.annotation.ModelAttribute;
 import org.springframework.web.bind.annotation.PathVariable;
 import org.springframework.web.bind.annotation.PostMapping;
 import org.springframework.web.bind.annotation.RequestMapping;
 import org.springframework.web.bind.annotation.ResponseBody;
 import springfox.documentation.annotations.ApiIgnore;





















 @Api("菜单管理接口")
 @Controller
 @RequestMapping({"/${ms.manager.path}/model"})
 public class ModelAction
   extends BaseAction
 {
   @Autowired
   private IModelBiz modelBiz;
   @Autowired
   private IManagerBiz managerBiz;
   @Autowired
   private IRoleModelBiz roleModelBiz;

   @ApiOperation("返回主界面index")
   @GetMapping({"/index"})
   public String index(HttpServletResponse response, HttpServletRequest request, ModelMap mode) {
     ManagerSessionEntity managerSession = getManagerBySession(request);
     int currentRoleId = managerSession.getManagerRoleID();
     List<BaseEntity> parentModelList = this.modelBiz.queryModelByRoleId(currentRoleId);
     mode.addAttribute("parentModelList", JSONArray.toJSONString(parentModelList));
     return "/basic/model/index";
   }
































   @ApiOperation("菜单列表接口")
   @GetMapping({"/list"})
   @ResponseBody
   public void list(@ModelAttribute @ApiIgnore ModelEntity modelEntity, HttpServletResponse response, HttpServletRequest request, @ApiIgnore ModelMap model) {
     ManagerSessionEntity managerSession = getManagerBySession(request);
     int currentRoleId = managerSession.getManagerRoleID();
     List<BaseEntity> modelList = this.modelBiz.queryModelByRoleId(currentRoleId);
     EUListBean _list = new EUListBean(modelList, modelList.size());
     outJson(response, JSONArray.toJSONString(_list, new com.alibaba.fastjson.serializer.SerializeFilter[0]));
   }





























   @ApiOperation("获取模块表")
   @ApiImplicitParam(name = "modelId", value = "模块的编号", required = true, paramType = "query")
   @GetMapping({"/get"})
   @ResponseBody
   public void get(@ModelAttribute @ApiIgnore ModelEntity modelEntity, HttpServletResponse response, HttpServletRequest request, @ApiIgnore ModelMap model) {
     if (modelEntity.getModelId() <= 0) {
       outJson(response, null, false, getResString("err.error", new String[] { getResString("model.id") }));

       return;
     }
     ModelEntity _model = (ModelEntity)this.modelBiz.getEntity(modelEntity.getModelId());
     if (_model != null) {
       Map<String, ModelEntity> mode = new HashMap<>();
       ModelEntity parentModel = (ModelEntity)this.modelBiz.getEntity(_model.getModelModelId());
       mode.put("parentModel", parentModel);
       mode.put("model", _model);
       outJson(response, JSONObject.toJSONString(mode));
       return;
     }
     outJson(response, (BaseEntity)_model);
   }










































   @ApiOperation("保存模块表实体")
   @ApiImplicitParams({@ApiImplicitParam(name = "modelTitle", value = "模块的标题", required = true, paramType = "query"), @ApiImplicitParam(name = "modelCode", value = "模块编码", required = false, paramType = "query"), @ApiImplicitParam(name = "modelModelId", value = "模块父id", required = false, paramType = "query"), @ApiImplicitParam(name = "modelUrl", value = "链接地址", required = false, paramType = "query"), @ApiImplicitParam(name = "modelDatetime", value = "发布时间", required = false, paramType = "query"), @ApiImplicitParam(name = "modelIcon", value = "模块图标", required = false, paramType = "query"), @ApiImplicitParam(name = "modelManagerId", value = "模块管理员Id", required = false, paramType = "query"), @ApiImplicitParam(name = "modelSort", value = "模块排序", required = false, paramType = "query"), @ApiImplicitParam(name = "modelIsMenu", value = "是否是菜单", required = false, paramType = "query"), @ApiImplicitParam(name = "isChild", value = "菜单类型", required = false, paramType = "query"), @ApiImplicitParam(name = "modelParentIds", value = "父级编号集合", required = false, paramType = "query")})
   @PostMapping({"/save"})
   @ResponseBody
   @RequiresPermissions({"model:save"})
   public void save(@ModelAttribute @ApiIgnore ModelEntity model, HttpServletResponse response, HttpServletRequest request) {
     if (StringUtil.isBlank(model.getModelTitle())) {
       outJson(response, null, false, getResString("err.empty", new String[] { getResString("model.title") }));
       return;
     }
     if (!StringUtil.checkLength(model.getModelTitle() + "", 1, 10)) {
       outJson(response, null, false, getResString("err.length", new String[] { getResString("model.title"), "1", "10" }));

       return;
     }
     model.setModelId(getManagerId(request));

     model.setModelDatetime(new Timestamp(System.currentTimeMillis()));


     if (!StringUtil.isBlank(model.getModelIcon())) {
       model.setModelIcon(model.getModelIcon().replace("|", ""));
     }

     ModelEntity parent = (ModelEntity)this.modelBiz.getEntity(model.getModelModelId());
     String parentIds = "";
     if (parent != null) {
       if (parent.getModelParentIds() != null) {
         parentIds = parent.getModelParentIds() + "," + model.getModelModelId();
       } else {
         parentIds = model.getModelModelId() + "";
       }
     }

     ModelEntity _model = this.modelBiz.getEntityByModelCode(model.getModelCode());
     if (!StringUtil.isBlank(_model)) {
       outJson(response, null, false, getResString("err.exist", new String[] { getResString("modelCode") }));
       return;
     }
     model.setModelParentIds(parentIds);
     this.modelBiz.saveEntity((BaseEntity)model);

     if (model.getModelId() > 0) {
       ManagerSessionEntity managerSession = getManagerBySession(request);
       List<RoleModelEntity> roleModels = new ArrayList<>();
       RoleModelEntity rolemodel = new RoleModelEntity();
       rolemodel.setModelId(model.getModelId());
       rolemodel.setRoleId(managerSession.getManagerRoleID());
       roleModels.add(rolemodel);
       this.roleModelBiz.saveEntity(roleModels);
     }
     if (StringUtil.isBlank(model.getModelCode()))
     {
       model.setModelCode(model.getModelId() + "");
     }
     this.modelBiz.updateEntity((BaseEntity)model);

     outJson(response, (BaseEnum)ModelCode.ROLE, true, String.valueOf(model.getModelId()));
   }












   @ApiOperation("批量删除模块表")
   @ApiImplicitParam(name = "ids", value = "模块编号，多个以逗号隔开", required = false, paramType = "query")
   @PostMapping({"/delete"})
   @ResponseBody
   @RequiresPermissions({"model:del"})
   public void delete(HttpServletResponse response, HttpServletRequest request) {
     int[] ids = BasicUtil.getInts("ids", ",");
     this.modelBiz.delete(ids);
     outJson(response, true);
   }












































   @ApiOperation("更新模块表信息模块表")
   @ApiImplicitParams({@ApiImplicitParam(name = "modelId", value = "模块的编号", required = true, paramType = "query"), @ApiImplicitParam(name = "modelTitle", value = "模块的标题", required = true, paramType = "query"), @ApiImplicitParam(name = "modelCode", value = "模块编码", required = false, paramType = "query"), @ApiImplicitParam(name = "modelModelId", value = "模块父id", required = false, paramType = "query"), @ApiImplicitParam(name = "modelUrl", value = "链接地址", required = false, paramType = "query"), @ApiImplicitParam(name = "modelDatetime", value = "发布时间", required = false, paramType = "query"), @ApiImplicitParam(name = "modelIcon", value = "模块图标", required = false, paramType = "query"), @ApiImplicitParam(name = "modelManagerId", value = "模块管理员Id", required = false, paramType = "query"), @ApiImplicitParam(name = "modelSort", value = "模块排序", required = false, paramType = "query"), @ApiImplicitParam(name = "modelIsMenu", value = "是否是菜单", required = false, paramType = "query"), @ApiImplicitParam(name = "isChild", value = "菜单类型", required = false, paramType = "query"), @ApiImplicitParam(name = "modelParentIds", value = "父级编号集合", required = false, paramType = "query")})
   @PostMapping({"/update"})
   @RequiresPermissions({"model:update"})
   @ResponseBody
   public void update(@ModelAttribute @ApiIgnore ModelEntity model, HttpServletResponse response, HttpServletRequest request) {
     if (StringUtil.isBlank(model.getModelTitle())) {
       outJson(response, null, false, getResString("err.empty", new String[] { getResString("model.title") }));
       return;
     }
     if (!StringUtil.checkLength(model.getModelTitle() + "", 1, 10)) {
       outJson(response, null, false, getResString("err.length", new String[] { getResString("model.title"), "1", "10" }));

       return;
     }
     ModelEntity _model = (ModelEntity)this.modelBiz.getEntity(model.getModelId());
     if (_model.getModelIsMenu().intValue() == 1 && model.getModelIsMenu().intValue() == 0) {
       outJson(response, null, false, getResString("model.is.menu"));

       return;
     }

     if (!StringUtil.isBlank(model.getModelIcon())) {
       model.setModelIcon(model.getModelIcon().replace("|", ""));
     }

     ModelEntity modelEntity = this.modelBiz.getEntityByModelCode(model.getModelCode());
     if (!StringUtil.isBlank(modelEntity) && modelEntity.getModelId() != model.getModelId()) {
       outJson(response, null, false, getResString("err.exist", new String[] { getResString("modelCode") }));

       return;
     }
     ModelEntity parent = (ModelEntity)this.modelBiz.getEntity(model.getModelModelId());
     String parentIds = "";
     if (parent != null) {
       if (parent.getModelParentIds() != null) {
         parentIds = parent.getModelParentIds() + "," + model.getModelModelId();
       } else {
         parentIds = model.getModelModelId() + "";
       }
     }
     model.setModelParentIds(parentIds);
     this.modelBiz.updateEntity((BaseEntity)model);
     outJson(response, (BaseEnum)ModelCode.ROLE, true, String.valueOf(model.getModelId()));
   }







   @ApiOperation("根据管理员ID查询模块集合")
   @ApiImplicitParam(name = "managerId", value = "管理员id", required = true, paramType = "path")
   @GetMapping({"/{managerId}/queryModelByRoleId"})
   public void queryModelByRoleId(@PathVariable @ApiIgnore int managerId, HttpServletRequest request, HttpServletResponse response) {
     ManagerEntity manager = (ManagerEntity)this.managerBiz.getEntity(managerId);
     if (manager == null) {
       return;
     }
     List<BaseEntity> modelList = new ArrayList<>();
     modelList = this.modelBiz.queryModelByRoleId(manager.getManagerRoleID());
     outJson(response, null, true, JSONObject.toJSONString(modelList));
   }
































   @ApiOperation("查询模块表列表")
   @ApiImplicitParam(name = "roleId", value = "角色编号", required = true, paramType = "query")
   @GetMapping({"/modelList"})
   @ResponseBody
   public void modelList(@ModelAttribute @ApiIgnore ModelEntity modelEntity, HttpServletResponse response, HttpServletRequest request, @ApiIgnore ModelMap model) {
     int roleId = BasicUtil.getInt("roleId").intValue();
     ManagerSessionEntity managerSession = getManagerBySession(request);
     int currentRoleId = managerSession.getManagerRoleID();
     boolean updateFalg = true;

     if (roleId == 0) {
       updateFalg = false;
       roleId = currentRoleId;
     }
     List<BaseEntity> modelList = this.modelBiz.queryModelByRoleId(currentRoleId);
     List<ModelEntity> _modelList = new ArrayList<>();
     List<RoleModelEntity> roleModelList = new ArrayList<>();
     if (roleId > 0) {
       roleModelList = this.roleModelBiz.queryByRoleId(roleId);
     }
     List<ModelEntity> childModelList = new ArrayList<>();

     for (BaseEntity base : modelList) {
       ModelEntity _model = (ModelEntity)base;
       if (_model.getModelIsMenu().intValue() == 1) {
         _model.setModelChildList(new ArrayList());
         _modelList.add(_model); continue;
       }  if (_model.getModelIsMenu().intValue() == 0) {
         childModelList.add(_model);
       }
     }

     for (ModelEntity _modelEntity : _modelList) {
       for (ModelEntity childModel : childModelList) {
         if (childModel.getModelModelId() == _modelEntity.getModelId()) {
           _modelEntity.getModelChildList().add(childModel);

           for (RoleModelEntity roleModelEntity : roleModelList) {
             if (roleModelEntity.getModelId() == childModel.getModelId() && updateFalg) {
               childModel.setChick(1);
             }
           }
         }
       }
     }

     EUListBean _list = new EUListBean(_modelList, _modelList.size());
     outJson(response, JSONArray.toJSONString(_list, new com.alibaba.fastjson.serializer.SerializeFilter[0]));
   }
 }


/* Location:              D:\User\Maven\repository\net\mingsoft\ms-basic\1.0.16\ms-basic-1.0.16.jar!\net\mingsoft\basic\action\ModelAction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */