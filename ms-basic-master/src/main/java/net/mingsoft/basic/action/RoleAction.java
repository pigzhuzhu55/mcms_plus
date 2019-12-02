 package net.mingsoft.basic.action;

 import com.alibaba.fastjson.serializer.SerializeFilter;
 import io.swagger.annotations.Api;
 import io.swagger.annotations.ApiImplicitParam;
 import io.swagger.annotations.ApiImplicitParams;
 import io.swagger.annotations.ApiOperation;
 import java.util.ArrayList;
 import java.util.List;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;
 import net.mingsoft.base.constant.e.BaseEnum;
 import net.mingsoft.base.entity.BaseEntity;
 import net.mingsoft.base.filter.DateValueFilter;
 import net.mingsoft.base.filter.DoubleValueFilter;
 import net.mingsoft.base.util.JSONArray;
 import net.mingsoft.base.util.JSONObject;
 import net.mingsoft.basic.bean.EUListBean;
 import net.mingsoft.basic.biz.IModelBiz;
 import net.mingsoft.basic.biz.IRoleBiz;
 import net.mingsoft.basic.biz.IRoleModelBiz;
 import net.mingsoft.basic.constant.ModelCode;
 import net.mingsoft.basic.entity.ManagerSessionEntity;
 import net.mingsoft.basic.entity.RoleEntity;
 import net.mingsoft.basic.entity.RoleModelEntity;
 import net.mingsoft.basic.util.BasicUtil;
 import org.apache.shiro.authz.annotation.RequiresPermissions;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.stereotype.Controller;
 import org.springframework.ui.ModelMap;
 import org.springframework.util.StringUtils;
 import org.springframework.web.bind.annotation.GetMapping;
 import org.springframework.web.bind.annotation.ModelAttribute;
 import org.springframework.web.bind.annotation.PathVariable;
 import org.springframework.web.bind.annotation.PostMapping;
 import org.springframework.web.bind.annotation.RequestBody;
 import org.springframework.web.bind.annotation.RequestMapping;
 import org.springframework.web.bind.annotation.RequestParam;
 import org.springframework.web.bind.annotation.ResponseBody;
 import springfox.documentation.annotations.ApiIgnore;
























 @Api("角色管理控制层")
 @Controller
 @RequestMapping({"/${ms.manager.path}/basic/role"})
 public class RoleAction
   extends BaseAction
 {
   @Autowired
   private IRoleBiz roleBiz;
   @Autowired
   private IModelBiz modelBiz;
   @Autowired
   private IRoleModelBiz roleModelBiz;

   @ApiOperation("返回主界面index")
   @GetMapping({"/index"})
   public String index(HttpServletResponse response, HttpServletRequest request) { return "/basic/role/index"; }























   @ApiOperation("查询角色列表")
   @ApiImplicitParams({@ApiImplicitParam(name = "roleName", value = "角色名称", required = false, paramType = "query"), @ApiImplicitParam(name = "roleManagerId", value = "该角色的创建者ID", required = false, paramType = "query")})
   @GetMapping({"/list"})
   @ResponseBody
   public void list(@ModelAttribute @ApiIgnore RoleEntity role, HttpServletResponse response, HttpServletRequest request, @ApiIgnore ModelMap model) {
     ManagerSessionEntity managerSession = getManagerBySession(request);
     role.setRoleManagerId(managerSession.getManagerId());
     role.setAppId(BasicUtil.getAppId());
     BasicUtil.startPage();
     List roleList = this.roleBiz.query((BaseEntity)role);
     outJson(response, JSONArray.toJSONString(new EUListBean(roleList, (int)BasicUtil.endPage(roleList).getTotal()), new SerializeFilter[] { (SerializeFilter)new DoubleValueFilter(), (SerializeFilter)new DateValueFilter() }));
   }

   @ApiOperation("根据角色ID查询模块集合")
   @ApiImplicitParam(name = "roleId", value = "角色ID", required = true, paramType = "path")
   @GetMapping({"/{roleId}/queryByRole"})
   @ResponseBody
   public void queryByRole(@PathVariable @ApiIgnore int roleId, HttpServletResponse response) {
     List models = this.modelBiz.queryModelByRoleId(roleId);
     outJson(response, JSONObject.toJSONString(models));
   }



   @ApiOperation("返回编辑界面role_form")
   @ApiImplicitParam(name = "roleId", value = "角色ID", required = true, paramType = "query")
   @GetMapping({"/form"})
   public String form(@ModelAttribute @ApiIgnore RoleEntity role, HttpServletResponse response, HttpServletRequest request, @ApiIgnore ModelMap model) {
     if (role.getRoleId() > 0) {
       BaseEntity roleEntity = this.roleBiz.getEntity(role.getRoleId());
       model.addAttribute("roleEntity", roleEntity);
     }
     return "/basic/role/form";
   }
















   @ApiOperation("获取角色")
   @ApiImplicitParam(name = "roleId", value = "角色ID", required = true, paramType = "query")
   @GetMapping({"/get"})
   @ResponseBody
   public void get(@ModelAttribute @ApiIgnore RoleEntity role, HttpServletResponse response, HttpServletRequest request, @ApiIgnore ModelMap model) {
     if (role.getRoleId() <= 0) {
       outJson(response, null, false, getResString("err.error", new String[] { getResString("role.id") }));
       return;
     }
     RoleEntity _role = (RoleEntity)this.roleBiz.getEntity(role.getRoleId());
     outJson(response, (BaseEntity)_role);
   }





















   @ApiOperation("保存角色实体")
   @ApiImplicitParams({@ApiImplicitParam(name = "roleName", value = "角色名称", required = false, paramType = "query"), @ApiImplicitParam(name = "roleManagerId", value = "该角色的创建者ID", required = false, paramType = "query")})
   @PostMapping({"/saveOrUpdateRole"})
   @ResponseBody
   @RequiresPermissions({"role:save"})
   public void saveOrUpdateRole(@ModelAttribute @ApiIgnore RoleEntity role, @RequestParam(value = "ids[]", required = false) List<Integer> ids, HttpServletResponse response, HttpServletRequest request) {
     RoleEntity _role = new RoleEntity();
     _role.setRoleName(role.getRoleName());

     role.setAppId(BasicUtil.getAppId());

     ManagerSessionEntity managerSession = getManagerBySession(request);
     role.setRoleManagerId(managerSession.getManagerId());
     if (StringUtils.isEmpty(role.getRoleName())) {
       outJson(response, (BaseEnum)ModelCode.ROLE, false, getResString("err.empty", new String[] { getResString("rolrName") }));

       return;
     }
     if (role.getRoleId() > 0) {

       if (this.roleBiz.getEntity((BaseEntity)_role) != null && !role.getRoleName().equals(BasicUtil.getString("oldRoleName"))) {
         outJson(response, (BaseEnum)ModelCode.ROLE, false, getResString("roleName.exist"));
         return;
       }
       this.roleBiz.updateEntity((BaseEntity)role);
     } else {

       if (this.roleBiz.getEntity((BaseEntity)_role) != null) {
         outJson(response, (BaseEnum)ModelCode.ROLE, false, getResString("roleName.exist"));

         return;
       }
       this.roleBiz.saveEntity((BaseEntity)role);
     }

     List<RoleModelEntity> roleModelList = new ArrayList<>();
     if (ids != null) {
       for (Integer id : ids) {
         RoleModelEntity roleModel = new RoleModelEntity();
         roleModel.setRoleId(role.getRoleId());
         roleModel.setModelId(id.intValue());
         roleModelList.add(roleModel);
       }

       this.roleModelBiz.deleteEntity(role.getRoleId());
       this.roleModelBiz.saveEntity(roleModelList);
     } else {
       this.roleModelBiz.deleteEntity(role.getRoleId());
     }

     outJson(response, JSONObject.toJSONString(role));
   }












   @ApiOperation("批量删除角色")
   @PostMapping({"/delete"})
   @ResponseBody
   @RequiresPermissions({"role:del"})
   public void delete(@RequestBody List<RoleEntity> roles, HttpServletResponse response, HttpServletRequest request) {
     int[] ids = new int[roles.size()];
     ManagerSessionEntity managerSession = getManagerBySession(request);
     int currentRoleId = managerSession.getManagerRoleID();
     for (int i = 0; i < roles.size(); i++) {
       if (currentRoleId != ((RoleEntity)roles.get(i)).getRoleId())
       {


         ids[i] = ((RoleEntity)roles.get(i)).getRoleId(); }
     }
     this.roleBiz.delete(ids);
     outJson(response, true);
   }
 }


/* Location:              D:\User\Maven\repository\net\mingsoft\ms-basic\1.0.16\ms-basic-1.0.16.jar!\net\mingsoft\basic\action\RoleAction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */