 package net.mingsoft.basic.action;

 import cn.hutool.crypto.SecureUtil;
 import io.swagger.annotations.Api;
 import javax.servlet.http.HttpServletRequest;
 import net.mingsoft.base.constant.e.BaseCookieEnum;
 import net.mingsoft.base.constant.e.BaseEnum;
 import net.mingsoft.base.constant.e.BaseSessionEnum;
 import net.mingsoft.basic.biz.IAppBiz;
 import net.mingsoft.basic.biz.IModelBiz;
 import net.mingsoft.basic.constant.Const;
 import net.mingsoft.basic.constant.e.CookieConstEnum;
 import net.mingsoft.basic.constant.e.SessionConstEnum;
 import net.mingsoft.basic.entity.AppEntity;
 import net.mingsoft.basic.entity.ManagerSessionEntity;
 import net.mingsoft.basic.entity.ModelEntity;
 import net.mingsoft.basic.util.BasicUtil;
 import net.mingsoft.basic.util.SpringUtil;
 import org.apache.commons.lang3.math.NumberUtils;
 import org.springframework.beans.BeanUtils;

 @Api("基础应用层的父类base")
 public abstract class BaseAction
         extends net.mingsoft.base.action.BaseAction
 {
   protected ModelEntity getCategoryModelCode(HttpServletRequest request) {
     Object obj = BasicUtil.getSession((BaseSessionEnum)SessionConstEnum.MODEL_ID_SESSION);
     if (NumberUtils.isNumber(obj.toString())) {

       IModelBiz modelBiz = (IModelBiz)SpringUtil.getBean(IModelBiz.class);
       return modelBiz.getModel("99", Integer.parseInt(obj.toString()));
     }
     return null;
   }








   protected ModelEntity getBasicModelCode(HttpServletRequest request) {
     Object obj = BasicUtil.getSession((BaseSessionEnum)SessionConstEnum.MODEL_ID_SESSION);
     if (NumberUtils.isNumber(obj.toString())) {
       IModelBiz modelBiz = (IModelBiz)SpringUtil.getBean(IModelBiz.class);
       return modelBiz.getModel("98", Integer.parseInt(obj.toString()));
     }
     return null;
   }








   protected int getManagerId(HttpServletRequest request) {
     ManagerSessionEntity managerSession = getManagerBySession(request);
     int managerParent = managerSession.getManagerParentID();

     if (managerParent == 0) {
       return managerSession.getManagerId();
     }
     return managerParent;
   }








   protected boolean isSystemManager(HttpServletRequest request) {
     ManagerSessionEntity manager = getManagerBySession(request);
     if (manager.getManagerRoleID() == 1) {
       return true;
     }
     return false;
   }










   protected ManagerSessionEntity getManagerBySession(HttpServletRequest request) {
     ManagerSessionEntity managerSession = (ManagerSessionEntity)BasicUtil.getSession((BaseSessionEnum)SessionConstEnum.MANAGER_SESSION);
     if (managerSession != null)
     {
       return managerSession;
     }
     return null;
   }



   protected String getResString(String key) { return Const.RESOURCES.getString(key); }









   protected boolean checkRandCode(HttpServletRequest request) { return checkRandCode(request, SessionConstEnum.CODE_SESSION.toString()); }











   protected String decryptByAES(HttpServletRequest request, String str) {
     String _str = SecureUtil.aes(SecureUtil.md5(getApp(request).getAppId() + "").substring(16).getBytes()).decryptStr(str);
     return _str;
   }










   protected String encryptByAES(HttpServletRequest request, String str) {
     String _str = SecureUtil.aes(SecureUtil.md5(getApp(request).getAppId() + "").substring(16).getBytes()).encryptHex(str);
     return _str;
   }







   protected int getHistoryPageNoByCookie(HttpServletRequest request) {
     if (Integer.valueOf(BasicUtil.getCookie((BaseCookieEnum)CookieConstEnum.PAGENO_COOKIE)).intValue() >= 1) {
       return Integer.valueOf(BasicUtil.getCookie((BaseCookieEnum)CookieConstEnum.PAGENO_COOKIE)).intValue();
     }
     return 0;
   }









   @Deprecated
   protected int getAppId(HttpServletRequest request) { return getApp(request).getAppId(); }









   @Deprecated
   protected AppEntity getApp(HttpServletRequest request) {
     AppEntity app = new AppEntity();

     IAppBiz appBiz = (IAppBiz)SpringUtil.getBean(IAppBiz.class);
     AppEntity website = appBiz.getByUrl(getDomain(request));
     if (website == null) {
       return null;
     }
     BeanUtils.copyProperties(website, app);
     return app;
   }








   protected String getRandCode(HttpServletRequest request) { return BasicUtil.getSession((BaseSessionEnum)SessionConstEnum.CODE_SESSION) + ""; }










   protected int getModelCodeIdForAES(HttpServletRequest request, String code) {
     IModelBiz modelBiz = (IModelBiz)SpringUtil.getBean(IModelBiz.class);
     ModelEntity model = modelBiz.getEntityByModelCode(decryptByAES(request, code));

     if (model != null) {
       return model.getModelId();
     }
     return 0;
   }







   @Deprecated
   protected int getModelCodeId(HttpServletRequest request) {
     Object obj = BasicUtil.getSession((BaseSessionEnum)SessionConstEnum.MODEL_ID_SESSION);
     if (obj != null) {
       return Integer.parseInt(obj.toString());
     }
     return 0;
   }







   protected int getRootModelCodeId(HttpServletRequest request) {
     Object obj = BasicUtil.getSession((BaseSessionEnum)SessionConstEnum.MODEL_ID_SESSION);
     if (NumberUtils.isNumber(obj.toString())) {
       IModelBiz modelBiz = (IModelBiz)SpringUtil.getBean(IModelBiz.class);
       ModelEntity model = (ModelEntity)modelBiz.getEntity(Integer.parseInt(obj.toString()));
       return model.getModelModelId();
     }
     return 0;
   }









   protected int getModelCodeId(HttpServletRequest request, BaseEnum code) {
     IModelBiz modelBiz = (IModelBiz)SpringUtil.getBean(IModelBiz.class);
     ModelEntity model = modelBiz.getEntityByModelCode(code);
     if (model != null) {
       return model.getModelId();
     }
     return 0;
   }









   protected int getModelCodeId(HttpServletRequest request, String code) {
     IModelBiz modelBiz = (IModelBiz)SpringUtil.getBean(IModelBiz.class);
     ModelEntity model = modelBiz.getEntityByModelCode(code);
     if (model != null) {
       return model.getModelId();
     }
     return 0;
   }








   protected String getCodeBySession(HttpServletRequest request) {
     Object obj = BasicUtil.getSession((BaseSessionEnum)SessionConstEnum.CODE_SESSION);
     if (obj != null)
     {
       return (String)obj;
     }
     return null;
   }








   protected String redirectBack(HttpServletRequest request, boolean flag) {
     if (flag) {
       return "redirect:" + BasicUtil.getCookie((BaseCookieEnum)CookieConstEnum.BACK_COOKIE);
     }
     return BasicUtil.getCookie((BaseCookieEnum)CookieConstEnum.BACK_COOKIE);
   }










   protected boolean checkRandCode(HttpServletRequest request, String param) {
     String sessionCode = getRandCode(request);
     String requestCode = request.getParameter(param);
     this.LOG.debug("session_code:" + sessionCode + " requestCode:" + requestCode);
     if (sessionCode.equalsIgnoreCase(requestCode)) {
       return true;
     }
     return false;
   }








   @Deprecated
   protected String view(String path) { return Const.VIEW + path; }









   @Deprecated
   protected void removeUrlParams(HttpServletRequest request, String[] fitlers) { request.setAttribute("params", BasicUtil.assemblyRequestUrlParams(fitlers)); }
 }


/* Location:              D:\User\Maven\repository\net\mingsoft\ms-basic\1.0.16\ms-basic-1.0.16.jar!\net\mingsoft\basic\action\BaseAction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */