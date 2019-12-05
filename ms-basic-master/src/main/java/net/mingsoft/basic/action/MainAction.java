 package net.mingsoft.basic.action;

 import cn.hutool.crypto.SecureUtil;
 import com.alibaba.fastjson.JSONObject;
 import io.swagger.annotations.Api;
 import io.swagger.annotations.ApiImplicitParam;
 import io.swagger.annotations.ApiImplicitParams;
 import io.swagger.annotations.ApiOperation;
 import java.util.ArrayList;
 import java.util.HashMap;
 import java.util.List;
 import java.util.Map;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;
 import net.mingsoft.base.constant.e.BaseEnum;
 import net.mingsoft.base.entity.BaseEntity;
 import net.mingsoft.basic.biz.IManagerBiz;
 import net.mingsoft.basic.biz.IModelBiz;
 import net.mingsoft.basic.constant.ModelCode;
 import net.mingsoft.basic.entity.ManagerEntity;
 import net.mingsoft.basic.entity.ManagerModelPageEntity;
 import net.mingsoft.basic.entity.ManagerSessionEntity;
 import net.mingsoft.basic.entity.ModelEntity;
 import net.mingsoft.basic.util.BasicUtil;
 import net.mingsoft.basic.util.StringUtil;
 import org.apache.shiro.SecurityUtils;
 import org.apache.shiro.subject.Subject;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.beans.factory.annotation.Value;
 import org.springframework.stereotype.Controller;
 import org.springframework.web.bind.annotation.GetMapping;
 import org.springframework.web.bind.annotation.PathVariable;
 import org.springframework.web.bind.annotation.PostMapping;
 import org.springframework.web.bind.annotation.RequestMapping;
 import org.springframework.web.bind.annotation.ResponseBody;
 import springfox.documentation.annotations.ApiIgnore;



















































 @Api("主界面控制层")
 @Controller
 @RequestMapping({"/${ms.manager.path}"})
 public class MainAction
   extends BaseAction
 {
   @Autowired
   private IModelBiz modelBiz;
   @Autowired
   private IManagerBiz managerBiz;
   @Value("${ms.manager.path}")
   private String managerPath;

   @ApiOperation("加载后台主界面，并查询数据")
   @GetMapping({"/index"})
   public String index(HttpServletRequest request) {
     ManagerSessionEntity managerSession = getManagerBySession(request);
     List<BaseEntity> modelList = new ArrayList<>();
     ModelEntity model = new ModelEntity();
     modelList = this.modelBiz.queryModelByRoleId(managerSession.getManagerRoleID());

     List<BaseEntity> _modelList = new ArrayList<>();
     for (int i = 0; i < modelList.size(); i++) {
       ModelEntity _model = (ModelEntity)modelList.get(i);
       if (StringUtil.isBlank(_model.getIsChild())) {
         _modelList.add(_model);
       }
     }
     request.setAttribute("managerSession", managerSession);
     request.setAttribute("modelList", JSONObject.toJSONString(modelList));
     int managerId = managerSession.getManagerId();

     ManagerModelPageEntity managerModelPage = null;

     if (managerModelPage != null) {
       request.setAttribute("managerModelPage", managerModelPage);
     }
     request.setAttribute("client", BasicUtil.getApp().getAppUrl() + "/" + this.managerPath);
     return "/index";
   }


   @GetMapping({"/main"})
   public String main(HttpServletRequest request) { return "/main"; }





   @GetMapping({"/rf"})
   @ResponseBody
   public void rf(HttpServletRequest request) {}





   @ApiOperation("查询该父模块下的子模块")
   @ApiImplicitParam(name = "modelId", value = "模块编号", required = true, paramType = "path")
   @GetMapping({"/{modelId}/queryListByModelId"})
   @ResponseBody
   public Map queryListByModelId(@PathVariable @ApiIgnore int modelId, HttpServletRequest request) {
     Map<Object, Object> modelMap = new HashMap<>();
     List<BaseEntity> modelList = null;
     ManagerSessionEntity managerSession = getManagerBySession(request);
     ModelEntity model = new ModelEntity();
     if (isSystemManager(request) && modelId == 1) {
       model.setModelManagerId(1);
       model.setModelId(modelId);
       modelList = this.modelBiz.query((BaseEntity)model);
     } else if (isSystemManager(request)) {
       model.setModelModelId(modelId);
       modelList = this.modelBiz.query((BaseEntity)model);
     } else {
       modelList = this.modelBiz.queryModelByRoleId(managerSession.getManagerRoleID());
       for (int i = 0; i < modelList.size(); i++) {
         ModelEntity _model = (ModelEntity)modelList.get(i);
         if (_model.getModelModelId() != modelId) {
           modelList.remove(i);
           i--;
         }
       }
     }
     modelMap.put("modelList", modelList);
     return modelMap;
   }







   @ApiOperation("修改登录密码")
   @PostMapping({"/editPassword"})
   @ResponseBody
   public void editPassword(HttpServletResponse response, HttpServletRequest request) {
     ManagerSessionEntity managerSessionEntity = getManagerBySession(request);
     outJson(response, (BaseEnum)ModelCode.ROLE, false, JSONObject.toJSONString(managerSessionEntity.getManagerName()));
   }













   @ApiOperation("修改登录密码，若不填写密码则表示不修改")
   @ApiImplicitParams({@ApiImplicitParam(name = "oldManagerPassword", value = "旧密码", required = true, paramType = "query"), @ApiImplicitParam(name = "newManagerPassword", value = "新密码", required = true, paramType = "query")})
   @PostMapping({"/updatePassword"})
   @ResponseBody
   public void updatePassword(HttpServletResponse response, HttpServletRequest request) {
     String oldManagerPassword = SecureUtil.md5(request.getParameter("oldManagerPassword"));

     String newManagerPassword = request.getParameter("newManagerPassword");

     ManagerSessionEntity managerSessionEntity = getManagerBySession(request);

     if (StringUtil.isBlank(newManagerPassword) || StringUtil.isBlank(oldManagerPassword)) {
       outJson(response, (BaseEnum)ModelCode.ROLE, false, getResString("err.empty", new String[] { getResString("managerPassword") }));

       return;
     }

     if (!oldManagerPassword.equals(managerSessionEntity.getManagerPassword())) {
       outJson(response, (BaseEnum)ModelCode.ROLE, false, getResString("err.password", new String[] { getResString("managerPassword") }));

       return;
     }
     if (!StringUtil.checkLength(newManagerPassword, 1, 16)) {
       outJson(response, (BaseEnum)ModelCode.ROLE, false, getResString("err.length", new String[] { getResString("managerPassword"), "1", "16" }));

       return;
     }
     managerSessionEntity.setManagerPassword(SecureUtil.md5(newManagerPassword));

     this.managerBiz.updateUserPasswordByUserName((ManagerEntity)managerSessionEntity);
     outJson(response, (BaseEnum)ModelCode.ROLE, true, null);
   }






   @ApiOperation("退出系统")
   @GetMapping({"/loginOut"})
   @ResponseBody
   public boolean loginOut(HttpServletRequest request) {
     Subject subject = SecurityUtils.getSubject();
     subject.logout();
     return true;
   }







   @ApiOperation("加载UI页面")
   @GetMapping({"/ui"})
   public String ui(HttpServletRequest request) { return "/ui"; }








   @ApiOperation("加载UI列表界面")
   @GetMapping({"/ui/list"})
   public String list(HttpServletRequest request) { return "/ui/list"; }








   @ApiOperation("加载UI的表单页面")
   @GetMapping({"/ui/form"})
   public String form(HttpServletRequest request) { return "/ui/from"; }
 }


