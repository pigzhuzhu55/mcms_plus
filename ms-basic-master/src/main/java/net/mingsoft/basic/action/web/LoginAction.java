 package net.mingsoft.basic.action.web;

 import cn.hutool.core.io.file.FileReader;
 import cn.hutool.core.io.file.FileWriter;
 import cn.hutool.crypto.SecureUtil;
 import io.swagger.annotations.Api;
 import io.swagger.annotations.ApiImplicitParam;
 import io.swagger.annotations.ApiImplicitParams;
 import io.swagger.annotations.ApiOperation;
 import java.io.File;
 import java.util.List;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;
 import net.mingsoft.base.constant.e.BaseEnum;
 import net.mingsoft.base.constant.e.BaseSessionEnum;
 import net.mingsoft.base.entity.BaseEntity;
 import net.mingsoft.basic.action.BaseAction;
 import net.mingsoft.basic.biz.IAppBiz;
 import net.mingsoft.basic.biz.IManagerBiz;
 import net.mingsoft.basic.biz.IRoleBiz;
 import net.mingsoft.basic.constant.ModelCode;
 import net.mingsoft.basic.constant.e.SessionConstEnum;
 import net.mingsoft.basic.entity.AppEntity;
 import net.mingsoft.basic.entity.ManagerEntity;
 import net.mingsoft.basic.entity.ManagerSessionEntity;
 import net.mingsoft.basic.entity.RoleEntity;
 import net.mingsoft.basic.util.BasicUtil;
 import net.mingsoft.basic.util.StringUtil;
 import org.apache.commons.lang3.StringUtils;
 import org.apache.shiro.SecurityUtils;
 import org.apache.shiro.authc.AuthenticationToken;
 import org.apache.shiro.authc.UsernamePasswordToken;
 import org.apache.shiro.subject.Subject;
 import org.springframework.beans.BeanUtils;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.beans.factory.annotation.Value;
 import org.springframework.stereotype.Controller;
 import org.springframework.web.bind.annotation.GetMapping;
 import org.springframework.web.bind.annotation.ModelAttribute;
 import org.springframework.web.bind.annotation.PostMapping;
 import org.springframework.web.bind.annotation.RequestMapping;
 import springfox.documentation.annotations.ApiIgnore;



















































 @Api("登录的基础应用层接口")
 @Controller
 @RequestMapping({"/${ms.manager.path}"})
 public class LoginAction
   extends BaseAction
 {
   @Value("${ms.manager.path}")
   private String managerPath;
   @Autowired
   private IManagerBiz managerBiz;
   @Autowired
   private IRoleBiz roleBiz;
   @Autowired
   private IAppBiz appBiz;

   @ApiOperation("加载管理员登录界面")
   @GetMapping({"/login"})
   public String login(HttpServletRequest request) {
     if (BasicUtil.getSession((BaseSessionEnum)SessionConstEnum.MANAGER_SESSION) != null) {
       return "redirect:" + this.managerPath + "/index.do";
     }

     AppEntity app = BasicUtil.getApp();

     if (app != null) {

       if (!StringUtil.isBlank(app.getAppLoginPage())) {
         this.LOG.debug("跳转自定义登录界面");
         return "redirect:" + app.getAppLoginPage();
       }
     } else {

       File file = new File(BasicUtil.getRealPath("WEB-INF/ms.install"));

       if (file.exists()) {
         String defaultId = FileReader.create(new File(BasicUtil.getRealPath("WEB-INF/ms.install"))).readString();
         if (!StringUtils.isEmpty(defaultId)) {
           app = (AppEntity)this.appBiz.getEntity(Integer.parseInt(defaultId));
           app.setAppUrl(getUrl(request));
           this.appBiz.updateEntity((BaseEntity)app);
           FileWriter.create(new File(BasicUtil.getRealPath("WEB-INF/ms.install.bak"))).write(defaultId);
           file.delete();
         }
       }
     }

     request.setAttribute("app", app);
     return "/login";
   }















   @ApiOperation("验证登录")
   @ApiImplicitParams({@ApiImplicitParam(name = "managerName", value = "帐号", required = true, paramType = "query"), @ApiImplicitParam(name = "managerPassword", value = "密码", required = true, paramType = "query")})
   @PostMapping({"/checkLogin"})
   public void checkLogin(@ModelAttribute @ApiIgnore ManagerEntity manager, HttpServletRequest request, HttpServletResponse response) {
     AppEntity urlWebsite = this.appBiz.getByUrl(getDomain(request));
     if (urlWebsite == null) {
       outJson(response, (BaseEnum)ModelCode.ADMIN_LOGIN, false, getResString("err.not.exist", new String[] { getResString("app"), "!请尝试去文件 WEB-INF/ms.install.bak 后缀bak" }));

       return;
     }
     if (!checkRandCode(request)) {
       outJson(response, null, false, getResString("err.error", new String[] { getResString("rand.code") }));

       return;
     }

     ManagerEntity newManager = new ManagerEntity();
     newManager.setManagerName(manager.getManagerName());
     ManagerEntity _manager = (ManagerEntity)this.managerBiz.getEntity((BaseEntity)newManager);

     if (_manager == null || StringUtils.isEmpty(manager.getManagerName())) {

       outJson(response, (BaseEnum)ModelCode.ADMIN_LOGIN, false, getResString("err.nameEmpty"));

     }
     else if (SecureUtil.md5(manager.getManagerPassword()).equals(_manager.getManagerPassword())) {

       ManagerSessionEntity managerSession = new ManagerSessionEntity();
       AppEntity website = new AppEntity();

       RoleEntity role = (RoleEntity)this.roleBiz.getEntity(_manager.getManagerRoleID());
       website = this.appBiz.getByManagerId(role.getRoleManagerId());

       if ((website != null && urlWebsite.getAppId() == website.getAppId() && _manager.getManagerRoleID() > 1) || role.getAppId() == getAppId(request)) {
         if (website == null) {
           website = BasicUtil.getApp();
         }
         List childManagerList = this.managerBiz.queryAllChildManager(managerSession.getManagerId());
         managerSession.setBasicId(website.getAppId());
         managerSession.setManagerParentID(role.getRoleManagerId());
         managerSession.setManagerChildIDs(childManagerList);
         managerSession.setStyle(website.getAppStyle());

         BasicUtil.setSession((BaseSessionEnum)SessionConstEnum.MANAGER_SESSION, managerSession);
       }
       else if (_manager.getManagerRoleID() != 1) {
         this.LOG.debug("roleId: " + _manager.getManagerRoleID());
         outJson(response, (BaseEnum)ModelCode.ADMIN_LOGIN, false, getResString("err.not.exist", new String[] { getResString("manager") }));
       } else {
         BasicUtil.setSession((BaseSessionEnum)SessionConstEnum.MANAGER_SESSION, managerSession);
       }

       BeanUtils.copyProperties(_manager, managerSession);

       Subject subject = SecurityUtils.getSubject();
       UsernamePasswordToken upt = new UsernamePasswordToken(managerSession.getManagerName(), managerSession.getManagerPassword());
       subject.login((AuthenticationToken)upt);
       outJson(response, (BaseEnum)ModelCode.ADMIN_LOGIN, true, null);
     } else {

       outJson(response, (BaseEnum)ModelCode.ADMIN_LOGIN, false, getResString("err.password"));
     }
   }
 }


/* Location:              D:\User\Maven\repository\net\mingsoft\ms-basic\1.0.16\ms-basic-1.0.16.jar!\net\mingsoft\basic\action\web\LoginAction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */