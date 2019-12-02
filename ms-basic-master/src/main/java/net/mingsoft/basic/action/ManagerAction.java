 package net.mingsoft.basic.action;

 import cn.hutool.crypto.SecureUtil;
 import com.alibaba.fastjson.serializer.SerializeFilter;
 import io.swagger.annotations.Api;
 import io.swagger.annotations.ApiImplicitParam;
 import io.swagger.annotations.ApiImplicitParams;
 import io.swagger.annotations.ApiOperation;
 import java.util.Date;
 import java.util.List;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;
 import net.mingsoft.base.constant.e.BaseSessionEnum;
 import net.mingsoft.base.entity.BaseEntity;
 import net.mingsoft.base.filter.DateValueFilter;
 import net.mingsoft.base.filter.DoubleValueFilter;
 import net.mingsoft.base.util.JSONArray;
 import net.mingsoft.base.util.JSONObject;
 import net.mingsoft.basic.bean.EUListBean;
 import net.mingsoft.basic.biz.IManagerBiz;
 import net.mingsoft.basic.constant.e.SessionConstEnum;
 import net.mingsoft.basic.entity.ManagerEntity;
 import net.mingsoft.basic.entity.ManagerSessionEntity;
 import net.mingsoft.basic.util.BasicUtil;
 import net.mingsoft.basic.util.StringUtil;
 import org.apache.shiro.authz.annotation.RequiresPermissions;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.stereotype.Controller;
 import org.springframework.ui.ModelMap;
 import org.springframework.web.bind.annotation.GetMapping;
 import org.springframework.web.bind.annotation.ModelAttribute;
 import org.springframework.web.bind.annotation.PostMapping;
 import org.springframework.web.bind.annotation.RequestBody;
 import org.springframework.web.bind.annotation.RequestMapping;
 import org.springframework.web.bind.annotation.ResponseBody;
 import springfox.documentation.annotations.ApiIgnore;
















 @Api("管理员管理接口")
 @Controller
 @RequestMapping({"/${ms.manager.path}/basic/manager"})
 public class ManagerAction
   extends BaseAction
 {
   @Autowired
   private IManagerBiz managerBiz;

   @GetMapping({"/index"})
   public String index(HttpServletResponse response, HttpServletRequest request) { return "/basic/manager/index"; }




























   @ApiOperation("查询管理员列表")
   @GetMapping({"/list"})
   @ResponseBody
   public void list(@ModelAttribute @ApiIgnore ManagerEntity manager, HttpServletResponse response, HttpServletRequest request, @ApiIgnore ModelMap model) {
     ManagerSessionEntity managerSession = getManagerBySession(request);
     BasicUtil.startPage();
     List managerList = this.managerBiz.queryAllChildManager(managerSession.getManagerId());
     outJson(response, JSONArray.toJSONString(new EUListBean(managerList, (int)BasicUtil.endPage(managerList).getTotal()), new SerializeFilter[] { (SerializeFilter)new DoubleValueFilter(), (SerializeFilter)new DateValueFilter() }));
   }


























   @ApiOperation("查询管理员列表,去掉当前管理员id，确保不能删除和修改自己")
   @GetMapping({"/query"})
   @ResponseBody
   public void query(HttpServletResponse response, HttpServletRequest request, @ApiIgnore ModelMap model) {
     ManagerSessionEntity managerSession = getManagerBySession(request);
     BasicUtil.startPage();
     List<ManagerEntity> managerList = this.managerBiz.queryAllChildManager(managerSession.getManagerId());
     for (ManagerEntity _manager : managerList) {
       if (_manager.getManagerId() == managerSession.getManagerId()) {
         _manager.setManagerId(0);
       }
     }
     outJson(response, JSONArray.toJSONString(new EUListBean(managerList, (int)BasicUtil.endPage(managerList).getTotal()), new SerializeFilter[] { (SerializeFilter)new DoubleValueFilter(), (SerializeFilter)new DateValueFilter() }));
   }
























   @ApiOperation("获取管理员接口")
   @GetMapping({"/get"})
   @ResponseBody
   public void get(@ModelAttribute @ApiIgnore ManagerEntity manager, HttpServletResponse response, HttpServletRequest request, @ApiIgnore ModelMap model) {
     ManagerEntity managerEntity = new ManagerEntity();

     if (manager.getManagerId() > 0) {
       managerEntity = (ManagerEntity)this.managerBiz.getEntity(manager.getManagerId());
     } else {
       ManagerEntity managerSession = (ManagerEntity)BasicUtil.getSession((BaseSessionEnum)SessionConstEnum.MANAGER_SESSION);
       managerEntity = (ManagerEntity)this.managerBiz.getEntity(managerSession.getManagerId());
     }
     managerEntity.setManagerPassword("");
     outJson(response, (BaseEntity)managerEntity);
   }































   @ApiOperation("保存管理员实体")
   @ApiImplicitParams({@ApiImplicitParam(name = "managerName", value = "帐号", required = true, paramType = "query"), @ApiImplicitParam(name = "managerNickName", value = "昵称", required = true, paramType = "query"), @ApiImplicitParam(name = "managerPassword", value = "密码", required = true, paramType = "query"), @ApiImplicitParam(name = "managerRoleID", value = "角色ID", required = false, paramType = "query"), @ApiImplicitParam(name = "managerPeopleID", value = "用户ID", required = false, paramType = "query")})
   @PostMapping({"/save"})
   @ResponseBody
   @RequiresPermissions({"manager:save"})
   public void save(@ModelAttribute @ApiIgnore ManagerEntity manager, HttpServletResponse response, HttpServletRequest request) {
     ManagerEntity newManager = new ManagerEntity();
     newManager.setManagerName(manager.getManagerName());

     if (this.managerBiz.getEntity((BaseEntity)newManager) != null) {
       outJson(response, null, false, getResString("err.exist", new String[] { getResString("manager.name") }));

       return;
     }
     if (StringUtil.isBlank(manager.getManagerName())) {
       outJson(response, null, false, getResString("err.empty", new String[] { getResString("manager.name") }));
       return;
     }
     if (!StringUtil.checkLength(manager.getManagerName() + "", 1, 15)) {
       outJson(response, null, false, getResString("err.length", new String[] { getResString("manager.name"), "1", "15" }));

       return;
     }
     if (StringUtil.isBlank(manager.getManagerNickName())) {
       outJson(response, null, false, getResString("err.empty", new String[] { getResString("manager.nickname") }));
       return;
     }
     if (!StringUtil.checkLength(manager.getManagerNickName() + "", 1, 15)) {
       outJson(response, null, false, getResString("err.length", new String[] { getResString("manager.nickname"), "1", "15" }));

       return;
     }
     if (StringUtil.isBlank(manager.getManagerPassword())) {
       outJson(response, null, false, getResString("err.empty", new String[] { getResString("manager.password") }));
       return;
     }
     if (!StringUtil.checkLength(manager.getManagerPassword() + "", 1, 45)) {
       outJson(response, null, false, getResString("err.length", new String[] { getResString("manager.password"), "1", "45" }));
       return;
     }
     manager.setManagerPassword(SecureUtil.md5(manager.getManagerPassword()));
     manager.setManagerTime(new Date());
     this.managerBiz.saveEntity((BaseEntity)manager);
     outJson(response, JSONObject.toJSONString(manager));
   }












   @ApiOperation("批量删除管理员")
   @PostMapping({"/delete"})
   @ResponseBody
   @RequiresPermissions({"manager:del"})
   public void delete(@RequestBody List<ManagerEntity> managers, HttpServletResponse response, HttpServletRequest request) {
     int[] ids = new int[managers.size()];
     for (int i = 0; i < managers.size(); i++) {
       ids[i] = ((ManagerEntity)managers.get(i)).getManagerId();
     }
     this.managerBiz.delete(ids);
     outJson(response, true);
   }
































   @ApiOperation("更新管理员信息管理员")
   @ApiImplicitParams({@ApiImplicitParam(name = "managerName", value = "帐号", required = true, paramType = "query"), @ApiImplicitParam(name = "managerNickName", value = "昵称", required = true, paramType = "query"), @ApiImplicitParam(name = "managerPassword", value = "密码", required = true, paramType = "query"), @ApiImplicitParam(name = "managerRoleID", value = "角色ID", required = false, paramType = "query"), @ApiImplicitParam(name = "managerPeopleID", value = "用户ID", required = false, paramType = "query")})
   @PostMapping({"/update"})
   @ResponseBody
   @RequiresPermissions({"manager:update"})
   public void update(@ModelAttribute @ApiIgnore ManagerEntity manager, HttpServletResponse response, HttpServletRequest request) {
     ManagerEntity newManager = new ManagerEntity();
     newManager.setManagerName(manager.getManagerName());

     ManagerEntity _manager = (ManagerEntity)this.managerBiz.getEntity((BaseEntity)newManager);

     if (_manager != null &&
       manager.getManagerId() != _manager.getManagerId()) {
       outJson(response, null, false, getResString("err.exist", new String[] { getResString("manager.name") }));

       return;
     }

     if (StringUtil.isBlank(manager.getManagerName())) {
       outJson(response, null, false, getResString("err.empty", new String[] { getResString("manager.name") }));
       return;
     }
     if (!StringUtil.checkLength(manager.getManagerName() + "", 1, 15)) {
       outJson(response, null, false, getResString("err.length", new String[] { getResString("manager.name"), "1", "15" }));

       return;
     }
     if (StringUtil.isBlank(manager.getManagerNickName())) {
       outJson(response, null, false, getResString("err.empty", new String[] { getResString("manager.nickname") }));
       return;
     }
     if (!StringUtil.checkLength(manager.getManagerNickName() + "", 1, 15)) {
       outJson(response, null, false, getResString("err.length", new String[] { getResString("manager.nickname"), "1", "15" }));

       return;
     }
     if (!StringUtil.isBlank(manager.getManagerPassword())) {
       if (!StringUtil.checkLength(manager.getManagerPassword() + "", 1, 45)) {
         outJson(response, null, false, getResString("err.length", new String[] { getResString("manager.password"), "1", "45" }));
         return;
       }
       manager.setManagerPassword(SecureUtil.md5(manager.getManagerPassword()));
     }

     this.managerBiz.updateEntity((BaseEntity)manager);
     outJson(response, JSONObject.toJSONString(manager));
   }
 }


/* Location:              D:\User\Maven\repository\net\mingsoft\ms-basic\1.0.16\ms-basic-1.0.16.jar!\net\mingsoft\basic\action\ManagerAction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */