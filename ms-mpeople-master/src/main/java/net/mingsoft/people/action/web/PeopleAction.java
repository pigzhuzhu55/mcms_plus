 package net.mingsoft.people.action.web;

 import cn.hutool.core.date.DateUtil;
 import cn.hutool.core.lang.Validator;
 import cn.hutool.crypto.SecureUtil;
 import cn.hutool.http.HttpUtil;
 import com.alibaba.fastjson.JSONObject;
 import io.swagger.annotations.Api;
 import io.swagger.annotations.ApiImplicitParam;
 import io.swagger.annotations.ApiImplicitParams;
 import io.swagger.annotations.ApiOperation;
 import java.sql.Timestamp;
 import java.util.Date;
 import java.util.HashMap;
 import java.util.Map;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;
 import net.mingsoft.base.constant.e.BaseCookieEnum;
 import net.mingsoft.base.constant.e.BaseEnum;
 import net.mingsoft.base.constant.e.BaseSessionEnum;
 import net.mingsoft.base.entity.BaseEntity;
 import net.mingsoft.base.entity.ResultJson;
 import net.mingsoft.basic.util.BasicUtil;
 import net.mingsoft.basic.util.StringUtil;
 import net.mingsoft.people.action.BaseAction;
 import net.mingsoft.people.bean.PeopleLoginBean;
 import net.mingsoft.people.biz.IPeopleBiz;
 import net.mingsoft.people.biz.IPeopleUserBiz;
 import net.mingsoft.people.constant.ModelCode;
 import net.mingsoft.people.constant.e.CookieConstEnum;
 import net.mingsoft.people.constant.e.PeopleEnum;
 import net.mingsoft.people.constant.e.SessionConstEnum;
 import net.mingsoft.people.entity.PeopleEntity;
 import net.mingsoft.people.entity.PeopleUserEntity;
 import org.apache.commons.lang3.StringUtils;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.stereotype.Controller;
 import org.springframework.web.bind.annotation.ModelAttribute;
 import org.springframework.web.bind.annotation.PostMapping;
 import org.springframework.web.bind.annotation.RequestMapping;
 import org.springframework.web.bind.annotation.ResponseBody;
 import springfox.documentation.annotations.ApiIgnore;

 @Api("会员模块,前端调用（不需要用户登录进行的操作）")
 @Controller("webPeople")
 @RequestMapping({"/"})
 public class PeopleAction
   extends BaseAction
 {
   @Autowired
   private IPeopleBiz peopleBiz;
   @Autowired
   private IPeopleUserBiz peopleUserBiz;

   @ApiOperation("验证码验证,例如流程需要短信验证或邮箱验证，为有效防止恶意发送验证码。提供给ajax异步请求使用,注意：页面提交对验证码表单属性名称必须是rand_code，否则无效")
   @ApiImplicitParam(name = "rand_code", value = "验证码", required = true, paramType = "query")
   @PostMapping({"/checkCode"})
   @ResponseBody
   public void checkCode(HttpServletRequest request, HttpServletResponse response) {
     if (!checkRandCode(request)) {
       outJson(response, (BaseEnum)ModelCode.PEOPLE, false,
           getResString("err.error", new String[] { getResString("rand.code") }));
       return;
     }
     outJson(response, (BaseEnum)ModelCode.PEOPLE, true);
   }




















   @ApiOperation("验证用户名、手机号、邮箱是否可用，同一时间只能判断一种，优先用户名称 ,只验证已绑定的用户,建议独立使用")
   @ApiImplicitParams({@ApiImplicitParam(name = "peopleId", value = "用户编号", required = false, paramType = "query"), @ApiImplicitParam(name = "peopleMail", value = "用户邮箱", required = false, paramType = "query"), @ApiImplicitParam(name = "peopleName", value = "登录帐号", required = false, paramType = "query"), @ApiImplicitParam(name = "peoplePhone", value = "用户电话", required = false, paramType = "query")})
   @PostMapping({"/check"})
   @ResponseBody
   public void check(@ModelAttribute @ApiIgnore PeopleEntity people, HttpServletRequest request, HttpServletResponse response) {
     PeopleEntity _people = this.peopleBiz.getByPeople(people, BasicUtil.getAppId());
     if (_people != null) {
       outJson(response, true);
     } else {
       outJson(response, false);
     }
   }






























   @ApiOperation("登录验证,登录必须存在验证码")
   @ApiImplicitParams({@ApiImplicitParam(name = "peoplePassword", value = "用户密码", required = true, paramType = "query"), @ApiImplicitParam(name = "peopleName", value = "登录帐号", required = true, paramType = "query"), @ApiImplicitParam(name = "rand_code", value = "验证码", required = true, paramType = "query"), @ApiImplicitParam(name = "peopleAutoLogin", value = " 大于0表示自动登录", required = false, paramType = "query")})
   @PostMapping({"/checkLogin"})
   @ResponseBody
   public void checkLogin(@ModelAttribute @ApiIgnore PeopleEntity people, HttpServletRequest request, HttpServletResponse response) {
     if (!checkRandCode(request)) {
       outJson(response, null, false, getResString("err.error", new String[] { getResString("rand.code") }));

       return;
     }
     if (StringUtils.isBlank(people.getPeopleName()) || StringUtils.isBlank(people.getPeoplePassword())) {
       outJson(response, (BaseEnum)ModelCode.PEOPLE_LOGIN, false,
           getResString("err.error", new String[] { getResString("people") }));

       return;
     }

     int appId = BasicUtil.getAppId();
     PeopleEntity peopleEntity = this.peopleBiz.getEntityByUserName(people.getPeopleName(), appId);
     if (peopleEntity == null) {

       outJson(response, (BaseEnum)ModelCode.PEOPLE_LOGIN, false,
           getResString("err.error", new String[] { getResString("pepple.no.exist") }));

       return;
     }

     String peoplePassWord = SecureUtil.md5(people.getPeoplePassword());
     if (peoplePassWord.equals(peopleEntity.getPeoplePassword())) {

       setPeopleBySession(request, peopleEntity);
       PeopleLoginBean tempPeople = new PeopleLoginBean();
       tempPeople.setPeopleId(peopleEntity.getPeopleId());
       tempPeople.setPeopleName(peopleEntity.getPeopleName());
       tempPeople.setPeopleMail(peopleEntity.getPeopleMail());
       tempPeople.setPeopleState(peopleEntity.getPeopleState());
       tempPeople.setCookie(request.getHeader("cookie"));

       if (people.getPeopleAutoLogin() > 0) {
         tempPeople.setPeoplePassword(people.getPeoplePassword());
         BasicUtil.setCookie(response, (BaseCookieEnum)CookieConstEnum.PEOPLE_COOKIE, JSONObject.toJSONString(tempPeople), 86400 * people
             .getPeopleAutoLogin());
       }
       peopleEntity.setPeopleIp(BasicUtil.getIp());

       this.peopleBiz.updateEntity((BaseEntity)peopleEntity);
       outJson(response, (BaseEnum)ModelCode.PEOPLE_LOGIN, true, JSONObject.toJSONString(tempPeople));
     }
     else {

       outJson(response, (BaseEnum)ModelCode.PEOPLE_LOGIN, false,
           getResString("err.error", new String[] { getResString("pepple.no.exist") }));
     }
   }








   @ApiOperation("验证用户是否登录")
   @PostMapping({"/checkLoginStatus"})
   @ResponseBody
   public void checkLoginStatus(HttpServletRequest request, HttpServletResponse response) {
     PeopleEntity people = getPeopleBySession();
     outJson(response, !(people == null));
   }






















   @ApiOperation("验证重置密码过程中收到的验证码是否正确")
   @ApiImplicitParams({@ApiImplicitParam(name = "peopleCode", value = "用户随机验证码", required = true, paramType = "query"), @ApiImplicitParam(name = "rand_code", value = "验证码", required = true, paramType = "query")})
   @PostMapping({"/checkResetPasswordCode"})
   @ResponseBody
   public void checkResetPasswordCode(@ModelAttribute @ApiIgnore PeopleEntity people, HttpServletRequest request, HttpServletResponse response) {
     if (StringUtils.isBlank(getRandCode(request)) || !checkRandCode(request)) {
       outJson(response, null, false, getResString("err.error", new String[] { getResString("rand.code") }));

       return;
     }
     PeopleEntity _people = (PeopleEntity)BasicUtil.getSession((BaseSessionEnum)SessionConstEnum.PEOPLE_EXISTS_SESSION);
     if (_people == null) {

       outJson(response, (BaseEnum)ModelCode.PEOPLE, false,
           getResString("err.not.exist", new String[] { getResString("people") }));

       return;
     }

     this.LOG.debug(_people.getPeoplePhoneCheck() + ":" + PeopleEnum.PHONE_CHECK.toInt());
     this.LOG.debug(_people.getPeopleCode() + ":" + people.getPeopleCode());

     if (_people.getPeoplePhoneCheck() == PeopleEnum.PHONE_CHECK.toInt() && _people
       .getPeopleCode().equals(people.getPeopleCode())) {
       BasicUtil.setSession((BaseSessionEnum)SessionConstEnum.PEOPLE_RESET_PASSWORD_SESSION, _people);
       outJson(response, (BaseEnum)ModelCode.PEOPLE, true,
           getResString("success", new String[] { getResString("people.get.password") }));
     } else if (_people.getPeopleMailCheck() == PeopleEnum.MAIL_CHECK.toInt() && _people
       .getPeopleCode().equals(people.getPeopleCode())) {
       BasicUtil.setSession((BaseSessionEnum)SessionConstEnum.PEOPLE_RESET_PASSWORD_SESSION, _people);
       outJson(response, (BaseEnum)ModelCode.PEOPLE, true,
           getResString("success", new String[] { getResString("people.get.password") }));
     } else {
       outJson(response, (BaseEnum)ModelCode.PEOPLE, false,
           getResString("fail", new String[] { getResString("people.get.password") }));
     }
   }

























   @ApiOperation("用户名、邮箱、手机号验证 ,用户重置密码必须使用该接口,适用场景:1、用户注册是对用户名、邮箱或手机号唯一性判断 2、用户取回密码是判断账号是否存在")
   @ApiImplicitParams({@ApiImplicitParam(name = "peopleId", value = "用户编号", required = true, paramType = "query"), @ApiImplicitParam(name = "peoplePhone", value = "用户电话", required = true, paramType = "query"), @ApiImplicitParam(name = "peopleMail", value = "用户邮箱", required = true, paramType = "query"), @ApiImplicitParam(name = "peopleName", value = "登录帐号", required = true, paramType = "query")})
   @PostMapping({"/isExists"})
   @ResponseBody
   public void isExists(@ModelAttribute @ApiIgnore PeopleEntity people, HttpServletRequest request, HttpServletResponse response) {
     this.LOG.debug(JSONObject.toJSONString(people));
     if (StringUtils.isBlank(people.getPeopleName()) && StringUtils.isBlank(people.getPeoplePhone()) &&
       StringUtils.isBlank(people.getPeopleMail())) {
       outJson(response, (BaseEnum)ModelCode.PEOPLE, false,
           getResString("err.empty", new String[] { getResString("people.name") }));

       return;
     }

     int appId = BasicUtil.getAppId();
     people.setPeopleAppId(appId);

     if (!StringUtils.isBlank(people.getPeopleMail())) {
       people.setPeopleMailCheck((BaseEnum)PeopleEnum.MAIL_CHECK);
     }

     if (!StringUtils.isBlank(people.getPeoplePhone())) {
       people.setPeoplePhoneCheck((BaseEnum)PeopleEnum.PHONE_CHECK);
     }
     PeopleEntity _people = (PeopleEntity)this.peopleBiz.getEntity((BaseEntity)people);
     if (_people != null) {
       BasicUtil.setSession((BaseSessionEnum)SessionConstEnum.PEOPLE_EXISTS_SESSION, _people);
       outJson(response, (BaseEnum)ModelCode.PEOPLE, true);
       return;
     }
     outJson(response, (BaseEnum)ModelCode.PEOPLE, false);
   }



































   @ApiOperation("用户注册,用户可以更具用名称、手机号、邮箱进行注册")
   @ApiImplicitParams({@ApiImplicitParam(name = "peoplePassword", value = "登录密码", required = true, paramType = "query"), @ApiImplicitParam(name = "peopleCode", value = "用户随机验证码", required = true, paramType = "query"), @ApiImplicitParam(name = "peoplePhone", value = "用户电话", required = false, paramType = "query"), @ApiImplicitParam(name = "peopleMail", value = "用户邮箱", required = false, paramType = "query"), @ApiImplicitParam(name = "peopleName", value = "登录帐号", required = false, paramType = "query")})
   @PostMapping({"/register"})
   @ResponseBody
   public void register(@ModelAttribute @ApiIgnore PeopleUserEntity people, HttpServletRequest request, HttpServletResponse response) {
     this.LOG.debug("people register");

     if (!checkRandCode(request)) {
       outJson(response, null, false, getResString("err.error", new String[] { getResString("rand.code") }));

       return;
     }

     if (people == null) {
       outJson(response, (BaseEnum)ModelCode.PEOPLE_REGISTER, false,
           getResString("err.empty", new String[] { getResString("people") }));

       return;
     }
     int appId = BasicUtil.getAppId();

     if (!StringUtils.isBlank(people.getPeopleName())) {

       if (StringUtils.isBlank(people.getPeopleName())) {
         outJson(response, (BaseEnum)ModelCode.PEOPLE_REGISTER, false,
             getResString("err.empty", new String[] { getResString("people.name") }));

         return;
       }
       if (people.getPeopleName().contains(" ")) {
         outJson(response, (BaseEnum)ModelCode.PEOPLE_REGISTER, false,
             getResString("people.name") + getResString("people.space"));

         return;
       }
       if (!StringUtil.checkLength(people.getPeopleName(), 3, 30)) {
         outJson(response, (BaseEnum)ModelCode.PEOPLE_REGISTER, false,
             getResString("err.length", new String[] { getResString("people.name"), "3", "30" }));

         return;
       }

       PeopleEntity peopleName = this.peopleBiz.getEntityByUserName(people.getPeopleName(), appId);
       if (peopleName != null) {
         outJson(response, (BaseEnum)ModelCode.PEOPLE_REGISTER, false,
             getResString("err.exist", new String[] { getResString("people.name") + peopleName.getPeopleName() }));

         return;
       }
     }
     if (!StringUtils.isBlank(people.getPeoplePhone())) {
       PeopleEntity peoplePhone = this.peopleBiz.getEntityByUserName(people.getPeoplePhone(), appId);
       if (peoplePhone != null && peoplePhone.getPeoplePhoneCheck() == PeopleEnum.PHONE_CHECK.toInt()) {
         outJson(response, (BaseEnum)ModelCode.PEOPLE_REGISTER, false,
             getResString("err.exist", new String[] { getResString("people.phone") }));
         return;
       }
       Object obj = BasicUtil.getSession((BaseSessionEnum)SessionConstEnum.SEND_CODE_SESSION);
       if (obj != null) {
         PeopleEntity _people = (PeopleEntity)obj;
         if (_people.getPeoplePhone().equals(people.getPeoplePhone())) {
           if (_people.getPeopleCode().equals(people.getPeopleCode())) {
             people.setPeoplePhoneCheck((BaseEnum)PeopleEnum.PHONE_CHECK);
           } else {
             outJson(response, (BaseEnum)ModelCode.PEOPLE_REGISTER, false,
                 getResString("err.error", new String[] { getResString("people.phone.code") }));

             return;
           }
         }
       }
     }

     if (!StringUtils.isBlank(people.getPeopleMail())) {

       if (people.getPeopleMail().contains(" ")) {
         outJson(response, (BaseEnum)ModelCode.PEOPLE_REGISTER, false,
             getResString("people.mail") + getResString("people.space"));
         return;
       }
       PeopleEntity peopleMail = this.peopleBiz.getEntityByUserName(people.getPeopleMail(), appId);
       if (peopleMail != null && peopleMail.getPeopleMailCheck() == PeopleEnum.MAIL_CHECK.toInt()) {
         outJson(response, (BaseEnum)ModelCode.PEOPLE_REGISTER, false,
             getResString("err.exist", new String[] { getResString("people.mail") }));
         return;
       }
       Object obj = BasicUtil.getSession((BaseSessionEnum)SessionConstEnum.SEND_CODE_SESSION);
       if (obj != null) {
         PeopleEntity _people = (PeopleEntity)obj;
         if (_people.getPeopleMail().equals(people.getPeopleMail())) {
           if (_people.getPeopleCode().equals(people.getPeopleCode())) {
             people.setPeopleMailCheck((BaseEnum)PeopleEnum.MAIL_CHECK);
           } else {
             outJson(response, (BaseEnum)ModelCode.PEOPLE_REGISTER, false,
                 getResString("err.error", new String[] { getResString("people.mail") }));


             return;
           }
         }
       }
     }

     if (StringUtils.isBlank(people.getPeoplePassword())) {
       outJson(response, (BaseEnum)ModelCode.PEOPLE_REGISTER, false,
           getResString("err.empty", new String[] { getResString("people.password") }));

       return;
     }
     if (people.getPeoplePassword().contains(" ")) {
       outJson(response, (BaseEnum)ModelCode.PEOPLE_REGISTER, false,
           getResString("people.password") + getResString("people.space"));

       return;
     }
     if (people.getPeoplePassword().length() < 6 || people.getPeoplePassword().length() > 30) {
       outJson(response, (BaseEnum)ModelCode.PEOPLE_REGISTER, false,
           getResString("err.length", new String[] { getResString("people.password"), "6", "30" }));

       return;
     }

     people.setPeoplePassword(SecureUtil.md5(people.getPeoplePassword()));
     people.setPeopleAppId(appId);
     people.setPeopleDateTime(new Date());
     this.peopleUserBiz.savePeople((PeopleEntity)people);
     outJson(response, (BaseEnum)ModelCode.PEOPLE_REGISTER, true,
         getResString("success", new String[] { getResString("people.register") }));
     this.LOG.debug("people register ok");
   }


























   @ApiOperation("用户重置密码")
   @ApiImplicitParams({@ApiImplicitParam(name = "peoplePassword", value = "登录密码", required = true, paramType = "query"), @ApiImplicitParam(name = "rand_code", value = "验证码", required = true, paramType = "query"), @ApiImplicitParam(name = "peopleCode", value = "用户随机验证码", required = true, paramType = "query")})
   @PostMapping({"/resetPassword"})
   @ResponseBody
   public void resetPassword(@ModelAttribute @ApiIgnore PeopleEntity people, HttpServletRequest request, HttpServletResponse response) {
     if (StringUtils.isBlank(getRandCode(request)) || !checkRandCode(request)) {
       outJson(response, null, false, getResString("err.error", new String[] { getResString("rand.code") }));

       return;
     }

     if (!StringUtil.checkLength(people.getPeoplePassword(), 3, 12)) {
       outJson(response, (BaseEnum)ModelCode.PEOPLE, false,
           getResString("err.error", new String[] { getResString("people.password") }));

       return;
     }
     PeopleEntity _people = (PeopleEntity)BasicUtil.getSession((BaseSessionEnum)SessionConstEnum.PEOPLE_RESET_PASSWORD_SESSION);

     if (_people == null) {

       outJson(response, (BaseEnum)ModelCode.PEOPLE, false,
           getResString("err.not.exist", new String[] { getResString("people") }));


       return;
     }

     if (_people.getPeoplePhoneCheck() == PeopleEnum.PHONE_CHECK.toInt() && _people
       .getPeopleCode().equals(people.getPeopleCode())) {
       _people.setPeoplePassword(SecureUtil.md5(people.getPeoplePassword()));
       this.peopleBiz.updateEntity((BaseEntity)_people);
       this.LOG.debug("更新密码成功");
       outJson(response, (BaseEnum)ModelCode.PEOPLE, true,
           getResString("success", new String[] { getResString("people.get.password") }));
     } else if (_people.getPeopleMailCheck() == PeopleEnum.MAIL_CHECK.toInt() && _people
       .getPeopleCode().equals(people.getPeopleCode())) {
       _people.setPeoplePassword(SecureUtil.md5(people.getPeoplePassword()));
       this.peopleBiz.updateEntity((BaseEntity)_people);
       this.LOG.debug("更新密码成功");
       outJson(response, (BaseEnum)ModelCode.PEOPLE, true,
           getResString("success", new String[] { getResString("people.get.password") }));
     } else {
       this.LOG.debug("更新密码失败");
       outJson(response, (BaseEnum)ModelCode.PEOPLE, false,
           getResString("fail", new String[] { getResString("people.get.password") }));
     }
   }









   @ApiOperation("自动登录")
   @PostMapping({"/autoLogin"})
   @ResponseBody
   public void autoLogin(HttpServletRequest request, HttpServletResponse response) {
     String cookie = BasicUtil.getCookie((BaseCookieEnum)CookieConstEnum.PEOPLE_COOKIE);
     if (StringUtils.isBlank(cookie)) {
       outJson(response, false);

       return;
     }
     PeopleEntity people = (PeopleEntity)JSONObject.parseObject(cookie, PeopleEntity.class);

     PeopleEntity peopleEntity = this.peopleBiz.getByPeople(people, BasicUtil.getAppId());
     if (peopleEntity != null) {

       setPeopleBySession(request, peopleEntity);
       outJson(response, true);
     } else {
       outJson(response, false);
     }
   }

































   @ApiOperation("用户发送验证码，可以通过邮箱或手机发送")
   @ApiImplicitParams({@ApiImplicitParam(name = "receive", value = "接收地址，只能是邮箱或手机号，邮箱需要使用邮箱插件，手机号需要短信插件", required = true, paramType = "query"), @ApiImplicitParam(name = "modelCode", value = "对应邮件插件的模块编号", required = true, paramType = "query"), @ApiImplicitParam(name = "thrid", value = "默认sendcloud", required = true, paramType = "query"), @ApiImplicitParam(name = "type", value = "类型", required = true, paramType = "query"), @ApiImplicitParam(name = "isSession", value = "true启用session保存code,false 关联用户信息，true一般是当用户手机还不存在系统中时使用，", required = true, paramType = "query"), @ApiImplicitParam(name = "peoplePhone", value = "用户电话", required = false, paramType = "query"), @ApiImplicitParam(name = "peopleMail", value = "用户邮箱", required = false, paramType = "query"), @ApiImplicitParam(name = "peopleName", value = "登录帐号", required = false, paramType = "query")})
   @PostMapping({"/sendCode"})
   public void sendCode(@ModelAttribute @ApiIgnore PeopleEntity people, HttpServletRequest request, HttpServletResponse response) {
     String receive = request.getParameter("receive");
     String modelCode = request.getParameter("modelCode");
     String thrid = request.getParameter("thrid");
     String type = request.getParameter("type");
     boolean isSession = BasicUtil.getBoolean("isSession").booleanValue();

     if (StringUtils.isBlank(receive)) {
       outJson(response, (BaseEnum)ModelCode.PEOPLE, false, getResString("err.empty", new String[] { getResString("receive") }));
       return;
     }
     if (StringUtils.isBlank(modelCode)) {
       outJson(response, (BaseEnum)ModelCode.PEOPLE, false, getResString("err.empty", new String[] { "modelCode" }));
       return;
     }
     if (StringUtils.isBlank(type)) {
       outJson(response, (BaseEnum)ModelCode.PEOPLE, false, getResString("err.empty", new String[] { getResString("type") }));

       return;
     }
     int appId = BasicUtil.getAppId();
     String peopleCode = StringUtil.randomNumber(6);

     String _modelCode = encryptByAES(request, modelCode);
     this.LOG.debug("前端传的" + modelCode);
     this.LOG.debug("解密" + _modelCode);
     Map<String, Object> params = new HashMap<>();
     params.put("modelCode", _modelCode);
     params.put("receive", receive);
     params.put("thrid", thrid);
     params.put("content", "{code:'" + peopleCode + "'}");
     params.put("type", type);
     if (isSession) {
       if (!checkRandCode(request)) {
         outJson(response, (BaseEnum)ModelCode.PEOPLE, false,
             getResString("err.error", new String[] { getResString("rand.code") }));

         return;
       }
       Object obj = BasicUtil.getSession((BaseSessionEnum)SessionConstEnum.SEND_CODE_SESSION);

       PeopleEntity p = (PeopleEntity)obj;
       if (obj != null && DateUtil.betweenMs(new Date(), p.getPeopleCodeSendDate()) == 60L) {
         outJson(response, (BaseEnum)ModelCode.PEOPLE, false, getResString("people.code.time.error"));


         return;
       }

       PeopleEntity _people = new PeopleEntity();
       _people.setPeopleCode(peopleCode);
       _people.setPeopleCodeSendDate(Timestamp.valueOf(DateUtil.now()));
       _people.setPeopleMail(receive);
       _people.setPeoplePhone(receive);
       BasicUtil.setSession((BaseSessionEnum)SessionConstEnum.SEND_CODE_SESSION, _people);
       String contentt = HttpUtil.post(getUrl(request) + "/msend/send.do", params);
       ResultJson rs = (ResultJson)JSONObject.parseObject(contentt, ResultJson.class);
       outJson(response, JSONObject.toJSONString(rs));
       this.LOG.debug("send " + receive + ":content " + peopleCode);

       return;
     }
     if (StringUtil.isMobile(receive)) {
       people.setPeoplePhone(receive);
     } else {
       people.setPeopleMail(receive);
     }

     if (StringUtils.isBlank(people.getPeopleName()) && getPeopleBySession() == null) {

       if (!StringUtils.isBlank(people.getPeopleMail())) {
         people.setPeopleMailCheck((BaseEnum)PeopleEnum.MAIL_CHECK);
       }

       if (!StringUtils.isBlank(people.getPeoplePhone())) {
         people.setPeoplePhoneCheck((BaseEnum)PeopleEnum.PHONE_CHECK);
       }
     }


     people.setPeopleAppId(appId);

     PeopleEntity peopleEntity = (PeopleEntity)this.peopleBiz.getEntity((BaseEntity)people);
     if (peopleEntity == null) {
       outJson(response, (BaseEnum)ModelCode.PEOPLE, false,
           getResString("err.not.exist", new String[] { getResString("people") }));
       return;
     }
     if (peopleEntity.getPeopleUser() != null) {
       CodeBean code = new CodeBean();
       code.setCode(peopleCode);
       code.setUserName(peopleEntity.getPeopleUser().getPuNickname());
       params.put("content", JSONObject.toJSONString(code));
     }


     peopleEntity.setPeopleCode(peopleCode);


     peopleEntity.setPeopleCodeSendDate(Timestamp.valueOf(DateUtil.now()));

     this.peopleBiz.updateEntity((BaseEntity)peopleEntity);

     PeopleEntity _people = (PeopleEntity)BasicUtil.getSession((BaseSessionEnum)SessionConstEnum.PEOPLE_EXISTS_SESSION);
     if (_people != null) {
       BasicUtil.setSession((BaseSessionEnum)SessionConstEnum.PEOPLE_EXISTS_SESSION, peopleEntity);
     }
     String content = HttpUtil.post(getUrl(request) + "/msend/send.do", params);
     this.LOG.debug("content :" + content);
     ResultJson rs = (ResultJson)JSONObject.parseObject(content, ResultJson.class);
     if (rs != null) {
       outJson(response, true);
     }

     this.LOG.debug("send " + receive + ":content " + peopleCode);
   }




















   @ApiOperation("验证用户输入的接收验证码")
   @ApiImplicitParams({@ApiImplicitParam(name = "receive", value = "接收地址，只能是邮箱或手机号，邮箱需要使用邮箱插件，手机号需要短信插件", required = true, paramType = "query"), @ApiImplicitParam(name = "code", value = "接收到的验证码", required = true, paramType = "query"), @ApiImplicitParam(name = "peopleId", value = "用户编号", required = true, paramType = "query")})
   @PostMapping({"/checkSendCode"})
   public void checkSendCode(@ModelAttribute @ApiIgnore PeopleEntity people, HttpServletRequest request, HttpServletResponse response) {
     String code = request.getParameter("code");
     String receive = request.getParameter("receive");
     Boolean isMobile = Boolean.valueOf(Validator.isMobile(receive));
     Boolean isEmail = Boolean.valueOf(Validator.isEmail(receive));
     if (isMobile.booleanValue()) {

       if (StringUtils.isBlank(code)) {
         outJson(response, (BaseEnum)ModelCode.PEOPLE, false,
             getResString("err.error", new String[] { getResString("people.phone.code") }));
         return;
       }
       people.setPeoplePhone(receive);
     }
     if (isEmail.booleanValue()) {

       if (StringUtils.isBlank(code)) {
         outJson(response, (BaseEnum)ModelCode.PEOPLE, false,
             getResString("err.error", new String[] { getResString("people.mail.code") }));
         return;
       }
       people.setPeopleMail(receive);
     }

     int appId = BasicUtil.getAppId();
     people.setPeopleAppId(appId);

     PeopleEntity peopleEntity = (PeopleEntity)this.peopleBiz.getEntity((BaseEntity)people);


     if (BasicUtil.getSession((BaseSessionEnum)SessionConstEnum.SEND_CODE_SESSION) != null) {
       peopleEntity = (PeopleEntity)BasicUtil.getSession((BaseSessionEnum)SessionConstEnum.SEND_CODE_SESSION);

       if (!peopleEntity.getPeopleCode().equals(code)) {
         if (isMobile.booleanValue()) {
           outJson(response, (BaseEnum)ModelCode.PEOPLE, false,
               getResString("err.error", new String[] { getResString("people.phone.code") }));
           return;
         }
         outJson(response, (BaseEnum)ModelCode.PEOPLE, false,
             getResString("err.error", new String[] { getResString("people.mail.code") }));

         return;
       }
       outJson(response, (BaseEnum)ModelCode.PEOPLE, true);
     } else {

       if (isMobile.booleanValue()) {

         if (peopleEntity.getPeoplePhoneCheck() == PeopleEnum.PHONE_CHECK.toInt()) {
           outJson(response, (BaseEnum)ModelCode.PEOPLE, false);


           return;
         }
       } else if (peopleEntity.getPeopleMailCheck() == PeopleEnum.MAIL_CHECK.toInt()) {
         outJson(response, (BaseEnum)ModelCode.PEOPLE, false);


         return;
       }

       String date = peopleEntity.getPeopleCodeSendDate().toString();


       if (DateUtil.betweenMs(new Date(), (Date)DateUtil.parse(date)) > 86400L) {
         outJson(response, (BaseEnum)ModelCode.PEOPLE, false, getResString("people.msg.code.error"));

         return;
       }

       if (!peopleEntity.getPeopleCode().equals(code)) {
         if (isMobile.booleanValue()) {
           outJson(response, (BaseEnum)ModelCode.PEOPLE, false,
               getResString("err.error", new String[] { getResString("people.phone.code") }));
           return;
         }
         outJson(response, (BaseEnum)ModelCode.PEOPLE, false,
             getResString("err.error", new String[] { getResString("people.mail.code") }));


         return;
       }

       peopleEntity.setPeopleCode("");
       if (StringUtil.isMobile(receive)) {
         peopleEntity.setPeoplePhoneCheck((BaseEnum)PeopleEnum.PHONE_CHECK);
       } else {
         peopleEntity.setPeopleMailCheck((BaseEnum)PeopleEnum.MAIL_CHECK);
       }
       this.peopleBiz.updateEntity((BaseEntity)peopleEntity);
       outJson(response, (BaseEnum)ModelCode.PEOPLE, true);
     }
   }






















   @ApiOperation("解绑邮箱-> 验证用户输入的接收验证码")
   @ApiImplicitParams({@ApiImplicitParam(name = "code", value = "接收到的验证码", required = true, paramType = "query"), @ApiImplicitParam(name = "receive", value = "接收地址，只能是邮箱或手机号，邮箱需要使用邮箱插件，手机号需要短信插件", required = true, paramType = "query"), @ApiImplicitParam(name = "peopleId", value = "用户编号", required = true, paramType = "query")})
   @PostMapping({"/cancelBind"})
   public void cancelBind(@ModelAttribute @ApiIgnore PeopleEntity people, HttpServletRequest request, HttpServletResponse response) {
     String code = request.getParameter("code");
     String receive = request.getParameter("receive");
     Boolean isMobile = Boolean.valueOf(Validator.isMobile(receive));
     Boolean isEmail = Boolean.valueOf(Validator.isEmail(receive));

     if (isMobile.booleanValue()) {

       if (StringUtils.isBlank(code)) {
         outJson(response, (BaseEnum)ModelCode.PEOPLE, false,
             getResString("err.error", new String[] { getResString("people.phone.code") }));
         return;
       }
       people.setPeoplePhone(receive);
     }
     if (isEmail.booleanValue()) {

       if (StringUtils.isBlank(code)) {
         outJson(response, (BaseEnum)ModelCode.PEOPLE, false,
             getResString("err.error", new String[] { getResString("people.mail.code") }));
         return;
       }
       people.setPeopleMail(receive);
     }

     int appId = BasicUtil.getAppId();
     people.setPeopleAppId(appId);

     PeopleEntity peopleEntity = (PeopleEntity)this.peopleBiz.getEntity((BaseEntity)people);


     if (BasicUtil.getSession((BaseSessionEnum)SessionConstEnum.SEND_CODE_SESSION) != null) {
       peopleEntity = (PeopleEntity)BasicUtil.getSession((BaseSessionEnum)SessionConstEnum.SEND_CODE_SESSION);

       if (!peopleEntity.getPeopleCode().equals(code)) {
         if (isMobile.booleanValue()) {
           outJson(response, (BaseEnum)ModelCode.PEOPLE, false,
               getResString("err.error", new String[] { getResString("people.phone.code") }));
           return;
         }
         outJson(response, (BaseEnum)ModelCode.PEOPLE, false,
             getResString("err.error", new String[] { getResString("people.mail.code") }));

         return;
       }
       outJson(response, (BaseEnum)ModelCode.PEOPLE, true);
     } else {

       if (isMobile.booleanValue()) {

         if (peopleEntity.getPeoplePhoneCheck() == PeopleEnum.PHONE_NO_CHECK.toInt()) {
           outJson(response, (BaseEnum)ModelCode.PEOPLE, false);


           return;
         }
       } else if (peopleEntity.getPeopleMailCheck() == PeopleEnum.MAIL_NO_CHECK.toInt()) {
         outJson(response, (BaseEnum)ModelCode.PEOPLE, false);


         return;
       }

       String date = peopleEntity.getPeopleCodeSendDate().toString();



       if (DateUtil.betweenMs(new Date(), (Date)DateUtil.parse(date)) > 86400L) {
         outJson(response, (BaseEnum)ModelCode.PEOPLE, false, getResString("people.msg.code.error"));

         return;
       }

       if (!peopleEntity.getPeopleCode().equals(code)) {
         if (isMobile.booleanValue()) {
           outJson(response, (BaseEnum)ModelCode.PEOPLE, false,
               getResString("err.error", new String[] { getResString("people.phone.code") }));
           return;
         }
         outJson(response, (BaseEnum)ModelCode.PEOPLE, false,
             getResString("err.error", new String[] { getResString("people.mail.code") }));


         return;
       }

       peopleEntity.setPeopleCode("");
       if (StringUtil.isMobile(receive)) {
         peopleEntity.setPeoplePhoneCheck((BaseEnum)PeopleEnum.PHONE_NO_CHECK);
       } else {
         peopleEntity.setPeopleMailCheck((BaseEnum)PeopleEnum.MAIL_NO_CHECK);
       }
       this.peopleBiz.updateEntity((BaseEntity)peopleEntity);
       outJson(response, (BaseEnum)ModelCode.PEOPLE, true);
     }
   }
 }


/* Location:              D:\User\Maven\repository\net\mingsoft\ms-mpeople\1.0.11\ms-mpeople-1.0.11.jar!\net\mingsoft\people\action\web\PeopleAction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */