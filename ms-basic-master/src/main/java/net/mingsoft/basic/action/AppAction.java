 package net.mingsoft.basic.action;

 import cn.hutool.core.util.ObjectUtil;
 import io.swagger.annotations.Api;
 import io.swagger.annotations.ApiImplicitParam;
 import io.swagger.annotations.ApiImplicitParams;
 import io.swagger.annotations.ApiOperation;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;
 import net.mingsoft.base.constant.e.BaseCookieEnum;
 import net.mingsoft.base.constant.e.BaseEnum;
 import net.mingsoft.base.entity.BaseEntity;
 import net.mingsoft.basic.biz.IAppBiz;
 import net.mingsoft.basic.constant.ModelCode;
 import net.mingsoft.basic.constant.e.CookieConstEnum;
 import net.mingsoft.basic.entity.AppEntity;
 import net.mingsoft.basic.entity.ManagerSessionEntity;
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











































 @Api("网站基本信息接口")
 @Controller
 @RequestMapping({"/${ms.manager.path}/app/"})
 public class AppAction
   extends BaseAction
 {
   @Autowired
   private IAppBiz appBiz;

   @ApiOperation("跳转到修改页面")
   @ApiImplicitParam(name = "appId", value = "站点ID", required = true, paramType = "path")
   @GetMapping({"/{appId}/edit"})
   public String edit(ModelMap mode, @PathVariable @ApiIgnore int appId, HttpServletRequest request) {
     AppEntity app = null;

     if (appId < 0) {
       app = BasicUtil.getApp();
       if (app != null) {

         if (BasicUtil.getSession("appId") == null) {
           BasicUtil.setSession("appId", Integer.valueOf(app.getAppId()));
         }
       } else {
         appId = ((Integer)BasicUtil.getSession("appId")).intValue();
         app = (AppEntity)this.appBiz.getEntity(appId);
       }
     } else {
       app = (AppEntity)this.appBiz.getEntity(appId);
     }


     if (isSystemManager(request)) {
       mode.addAttribute("SystemManager", Boolean.valueOf(true));
     } else {
       mode.addAttribute("SystemManager", Boolean.valueOf(false));
     }

     mode.addAttribute("app", app);
     return "/basic/app/app";
   }





























   @ApiOperation("更新站点信息")
   @ApiImplicitParams({@ApiImplicitParam(name = "appName", value = "应用名称", required = false, paramType = "query"), @ApiImplicitParam(name = "appDescription", value = "应用描述", required = false, paramType = "query"), @ApiImplicitParam(name = "appLogo", value = "应用logo", required = false, paramType = "query"), @ApiImplicitParam(name = "appDatetime", value = "站点日期", required = false, paramType = "query"), @ApiImplicitParam(name = "appKeyword", value = "网站关键字", required = false, paramType = "query"), @ApiImplicitParam(name = "appCopyright", value = "网站版权信息", required = false, paramType = "query"), @ApiImplicitParam(name = "appStyle", value = "网站采用的模板风格", required = false, paramType = "query"), @ApiImplicitParam(name = "appUrl", value = "网站域名", required = false, paramType = "query"), @ApiImplicitParam(name = "appManagerId", value = "管理站点的管理员id", required = false, paramType = "query"), @ApiImplicitParam(name = "appMobileStyle", value = "移动端样式目录", required = false, paramType = "query"), @ApiImplicitParam(name = "appPayDate", value = "应用续费时间", required = false, paramType = "query"), @ApiImplicitParam(name = "appPay", value = "应用费用清单", required = false, paramType = "query"), @ApiImplicitParam(name = "appLoginPage", value = "应用自定义登录界面", required = false, paramType = "query")})
   @PostMapping({"/update"})
   @RequiresPermissions({"app:update"})
   public void update(ModelMap mode, @ModelAttribute @ApiIgnore AppEntity app, HttpServletRequest request, HttpServletResponse response) {
     mode.clear();

     ManagerSessionEntity managerSessionEntity = getManagerBySession(request);
     if (managerSessionEntity == null) {
       return;
     }
     mode.addAttribute("managerSession", managerSessionEntity);


     if (!isSystemManager(request)) {
       app.setAppPayDate(null);
       app.setAppPay(null);
     }
     int managerRoleID = managerSessionEntity.getManagerRoleID();


     String cookie = BasicUtil.getCookie((BaseCookieEnum)CookieConstEnum.PAGENO_COOKIE);
     int pageNo = 1;

     if (!StringUtil.isBlank(cookie) && Integer.valueOf(cookie).intValue() > 0) {
       pageNo = Integer.valueOf(cookie).intValue();
     }
     mode.addAttribute("pageNo", Integer.valueOf(pageNo));
     if (!checkForm(app, response)) {
       return;
     }
     if (!StringUtil.isBlank(app.getAppLogo())) {
       app.setAppLogo(app.getAppLogo().replace("|", ""));
     }
     if (ObjectUtil.isNotNull(app.getAppUrl())) {

       String url = app.getAppUrl();
       String[] _url = url.split("\r\n");
       StringBuffer sb = new StringBuffer();
       for (String temp : _url) {
         String lastChar = temp.trim().substring(temp.length() - 1);
         if (lastChar.equals("/") || lastChar.equals("\\")) {
           sb.append(temp.substring(0, temp.trim().length() - 1));
         } else {
           sb.append(temp);
         }
         sb.append("\r\n");
       }
       app.setAppUrl(sb.toString());
     }
     this.appBiz.updateEntity((BaseEntity)app);
     outJson(response, (BaseEnum)ModelCode.APP, true, String.valueOf(pageNo), String.valueOf(managerRoleID));
   }













   public boolean checkForm(AppEntity app, HttpServletResponse response) {
     if (!StringUtil.checkLength(app.getAppKeyword(), 0, 1000)) {
       outJson(response, (BaseEnum)ModelCode.APP, false,
           getResString("err.length", new String[] { getResString("appKeyword"), "0", "1000" }));
       return false;
     }
     if (!StringUtil.checkLength(app.getAppCopyright(), 0, 1000)) {
       outJson(response, (BaseEnum)ModelCode.APP, false,
           getResString("err.length", new String[] { getResString("appCopyright"), "0", "1000" }));
       return false;
     }
     if (!StringUtil.checkLength(app.getAppDescription(), 0, 1000)) {
       outJson(response, (BaseEnum)ModelCode.APP, false,
           getResString("err.length", new String[] { getResString("appDescrip"), "0", "1000" }));
       return false;
     }
     if (!StringUtil.checkLength(app.getAppName(), 1, 50)) {
       outJson(response, (BaseEnum)ModelCode.APP, false,
           getResString("err.length", new String[] { getResString("appTitle"), "1", "50" }));
       return false;
     }
     if (!StringUtil.isBlank(app.getAppStyle()) && !StringUtil.checkLength(app.getAppStyle(), 1, 30)) {
       outJson(response, (BaseEnum)ModelCode.APP, false,
           getResString("err.length", new String[] { getResString("appStyle"), "1", "30" }));
       return false;
     }
     if (ObjectUtil.isNotNull(app.getAppHostUrl()) &&
       !StringUtil.checkLength(app.getAppHostUrl(), 10, 150)) {
       outJson(response, (BaseEnum)ModelCode.APP, false,
           getResString("err.length", new String[] { getResString("appUrl"), "10", "150" }));
       return false;
     }

     return true;
   }








   @ApiOperation("判断是否有重复的域名")
   @GetMapping({"/checkUrl"})
   @ResponseBody
   public boolean checkUrl(HttpServletRequest request) {
     if (request.getParameter("appUrl") != null) {
       if (this.appBiz.countByUrl(request.getParameter("appUrl")) > 0) {
         return true;
       }
       return false;
     }

     return false;
   }
 }


/* Location:              D:\User\Maven\repository\net\mingsoft\ms-basic\1.0.16\ms-basic-1.0.16.jar!\net\mingsoft\basic\action\AppAction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */