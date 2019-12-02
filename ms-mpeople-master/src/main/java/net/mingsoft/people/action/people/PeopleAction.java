 package net.mingsoft.people.action.people;

 import cn.hutool.core.util.ObjectUtil;
 import cn.hutool.crypto.SecureUtil;
 import cn.hutool.crypto.digest.DigestUtil;
 import freemarker.core.ParseException;
 import freemarker.template.MalformedTemplateNameException;
 import freemarker.template.TemplateNotFoundException;
 import io.swagger.annotations.Api;
 import io.swagger.annotations.ApiImplicitParam;
 import io.swagger.annotations.ApiImplicitParams;
 import io.swagger.annotations.ApiOperation;
 import java.io.IOException;
 import java.util.Map;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;
 import net.mingsoft.base.constant.e.BaseCookieEnum;
 import net.mingsoft.base.constant.e.BaseEnum;
 import net.mingsoft.base.entity.BaseEntity;
 import net.mingsoft.basic.util.BasicUtil;
 import net.mingsoft.basic.util.StringUtil;
 import net.mingsoft.mdiy.biz.IPageBiz;
 import net.mingsoft.mdiy.entity.PageEntity;
 import net.mingsoft.mdiy.util.ParserUtil;
 import net.mingsoft.people.action.BaseAction;
 import net.mingsoft.people.biz.IPeopleBiz;
 import net.mingsoft.people.constant.ModelCode;
 import net.mingsoft.people.constant.e.CookieConstEnum;
 import net.mingsoft.people.constant.e.PeopleEnum;
 import net.mingsoft.people.entity.PeopleEntity;
 import org.apache.commons.lang3.StringUtils;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.stereotype.Controller;
 import org.springframework.web.bind.annotation.GetMapping;
 import org.springframework.web.bind.annotation.ModelAttribute;
 import org.springframework.web.bind.annotation.PathVariable;
 import org.springframework.web.bind.annotation.PostMapping;
 import org.springframework.web.bind.annotation.RequestMapping;
 import org.springframework.web.bind.annotation.ResponseBody;
 import springfox.documentation.annotations.ApiIgnore;

















































 @Api("用户基础信息接口(需用户登录)")
 @Controller("peopleMain")
 @RequestMapping({"/people"})
 public class PeopleAction
   extends BaseAction
 {
   @Autowired
   private IPeopleBiz peopleBiz;
   @Autowired
   private IPageBiz pageBiz;

   @ApiOperation("重置密码接口")
   @ApiImplicitParam(name = "peoplePassword", value = "用户密码", required = true, paramType = "query")
   @PostMapping({"/resetPassword"})
   @ResponseBody
   public void resetPassword(@ModelAttribute @ApiIgnore PeopleEntity people, HttpServletRequest request, HttpServletResponse response) {
     if (!StringUtil.checkLength(people.getPeoplePassword(), 6, 30)) {
       outJson(response, (BaseEnum)ModelCode.PEOPLE, false,
           getResString("err.length", new String[] { getResString("people.password"), "6", "20" }));
       return;
     }
     if (StringUtils.isBlank(people.getPeoplePassword())) {

       outJson(response, (BaseEnum)ModelCode.PEOPLE, false,
           getResString("err.empty", new String[] { getResString("people.password") }));

       return;
     }
     PeopleEntity _people = (PeopleEntity)this.peopleBiz.getEntity(getPeopleBySession().getPeopleId());

     String peoplePassWord = DigestUtil.md5Hex(people.getPeoplePassword(), "utf-8");

     _people.setPeoplePassword(peoplePassWord);
     this.peopleBiz.updateEntity((BaseEntity)_people);
     outJson(response, (BaseEnum)ModelCode.PEOPLE, true);
   }




















   @PostMapping({"/changePassword"})
   @ApiImplicitParams({@ApiImplicitParam(name = "peoplePassword", value = "用户密码", required = true, paramType = "query"), @ApiImplicitParam(name = "peopleOldPassword", value = "用户旧密码", required = true, paramType = "query")})
   @ResponseBody
   public void changePassword(@ModelAttribute @ApiIgnore PeopleEntity people, HttpServletRequest request, HttpServletResponse response) {
     if (!checkRandCode(request)) {
       outJson(response, (BaseEnum)ModelCode.PEOPLE, false,
           getResString("err.error", new String[] { getResString("rand.code") }));
       return;
     }
     if (StringUtils.isBlank(people.getPeoplePassword())) {

       outJson(response, (BaseEnum)ModelCode.PEOPLE, false,
           getResString("err.empty", new String[] { getResString("people.password") })); return;
     }
     if (StringUtils.isBlank(people.getPeoplePassword())) {

       outJson(response, (BaseEnum)ModelCode.PEOPLE, false,
           getResString("err.empty", new String[] { getResString("people.password") }));


       return;
     }

     if (!StringUtil.checkLength(people.getPeoplePassword(), 6, 30)) {
       outJson(response, (BaseEnum)ModelCode.PEOPLE, false,
           getResString("err.length", new String[] { getResString("people.password"), "6", "20" }));

       return;
     }

     if (people.getPeoplePassword().contains(" ")) {
       outJson(response, (BaseEnum)ModelCode.PEOPLE, false,
           getResString("people.password") + getResString("people.space"));

       return;
     }

     PeopleEntity _people = new PeopleEntity();
     _people.setPeopleId(getPeopleBySession().getPeopleId());
     PeopleEntity curPeople = (PeopleEntity)this.peopleBiz.getEntity((BaseEntity)_people);
     if (!curPeople.isNewUser() && StringUtils.isBlank(people.getPeopleOldPassword())) {

       outJson(response, (BaseEnum)ModelCode.PEOPLE, false,
           getResString("err.empty", new String[] { getResString("people.old.password") }));
       return;
     }
     if (!curPeople.isNewUser() && !curPeople.getPeoplePassword().equals(SecureUtil.md5(people.getPeopleOldPassword()))) {

       outJson(response, (BaseEnum)ModelCode.PEOPLE, false,
           getResString("err.error", new String[] { getResString("people.password") }));

       return;
     }
     String peoplePassWord = DigestUtil.md5Hex(people.getPeoplePassword(), "utf-8");

     curPeople.setPeoplePassword(peoplePassWord);
     this.peopleBiz.updateEntity((BaseEntity)curPeople);
     outJson(response, (BaseEnum)ModelCode.PEOPLE, true);
   }























   @ApiOperation("修改手机号接口")
   @PostMapping({"/changePhone"})
   @ApiImplicitParams({@ApiImplicitParam(name = "peoplePassword", value = "用户密码", required = true, paramType = "query"), @ApiImplicitParam(name = "peopleCode", value = "手机验证码", required = true, paramType = "query")})
   @ResponseBody
   public void changePhone(@ModelAttribute @ApiIgnore PeopleEntity people, HttpServletRequest request, HttpServletResponse response) {
     PeopleEntity _people = getPeopleBySession();


     if (StringUtils.isBlank(people.getPeoplePhone())) {
       outJson(response, (BaseEnum)ModelCode.PEOPLE, false,
           getResString("err.empty", new String[] { getResString("people.phone") }));

       return;
     }

     if (_people.getPeoplePhoneCheck() == PeopleEnum.PHONE_CHECK.toInt()) {
       PeopleEntity tempPeople = this.peopleBiz.getByPeople(people, BasicUtil.getAppId());

       if (!people.getPeopleCode().equals(tempPeople.getPeopleCode())) {

         outJson(response, (BaseEnum)ModelCode.PEOPLE_REGISTER, false,
             getResString("err.error", new String[] { getResString("people.phone.code") }));
         return;
       }
     }
     people.setPeoplePhone(people.getPeoplePhone());
     this.peopleBiz.updateEntity((BaseEntity)people);
     outJson(response, (BaseEnum)ModelCode.PEOPLE, true);
   }















   @ApiOperation("验证用户短信、邮箱验证码是否正确接口")
   @ApiImplicitParam(name = "peopleCode", value = "手机验证码", required = true, paramType = "query")
   @PostMapping({"/checkPeopleCode"})
   @ResponseBody
   public void checkPeopleCode(@ModelAttribute @ApiIgnore PeopleEntity people, HttpServletRequest request, HttpServletResponse response) {
     PeopleEntity _people = getPeopleBySession();
     PeopleEntity _temp = this.peopleBiz.getByPeople(_people, BasicUtil.getAppId());
     if (people.getPeopleCode().equals(_temp.getPeopleCode())) {
       outJson(response, (BaseEnum)ModelCode.PEOPLE, true);
     } else {
       outJson(response, (BaseEnum)ModelCode.PEOPLE, false);
     }
   }









   @ApiOperation("读取当前登录用户的基本信息 用户信息接口")
   @GetMapping({"/info"})
   @ResponseBody
   public void info(HttpServletRequest request, HttpServletResponse response) {
     PeopleEntity people = getPeopleBySession();

     outJson(response, people, new String[] { "peopleOldPassword", "peoplePassword" });
   }





   @ApiOperation("退出登录接口")
   @GetMapping({"/quit"})
   @ResponseBody
   public void quit(HttpServletRequest request, HttpServletResponse response) {
     removePeopleBySession(request);
     BasicUtil.setCookie(response, (BaseCookieEnum)CookieConstEnum.PEOPLE_COOKIE, null);
     outJson(response, true);
   }
















   @ApiOperation("更新用户邮箱或手机号接口")
   @ApiImplicitParams({@ApiImplicitParam(name = "peopleMail", value = "用户邮箱", required = false, paramType = "query"), @ApiImplicitParam(name = "peoplePhone", value = "手机号", required = false, paramType = "query")})
   @PostMapping({"/update"})
   public void update(@ModelAttribute @ApiIgnore PeopleEntity people, HttpServletRequest request, HttpServletResponse response) {
     if (people == null) {

       outJson(response, (BaseEnum)ModelCode.PEOPLE, false,
           getResString("err.empty", new String[] { getResString("people") }));
       return;
     }
     int peopleId = getPeopleBySession().getPeopleId();
     PeopleEntity _people = (PeopleEntity)this.peopleBiz.getEntity(peopleId);

     if (_people.getPeopleMailCheck() == PeopleEnum.MAIL_CHECK.toInt()) {
       people.setPeopleMail(null);
     }
     if (_people.getPeoplePhoneCheck() == PeopleEnum.PHONE_CHECK.toInt()) {
       people.setPeoplePhone(null);
     }
     people.setPeopleName(null);
     people.setPeopleId(peopleId);
     this.peopleBiz.updateEntity((BaseEntity)people);

     outJson(response, (BaseEnum)ModelCode.PEOPLE, true, getResString("success"));
   }







   @ApiOperation("前端会员中心所有页面都可以使用该方法 支持参数传递与解析，例如页面中有参数id=10 传递过来，跳转页面可以使用{id/}获取该参数")
   @ApiImplicitParam(name = "diy", value = "id", required = true, paramType = "path")
   @GetMapping({"/{diy}"})
   public void diy(@PathVariable("diy") String diy, HttpServletRequest req, HttpServletResponse resp) {
     Map<String, Object> map = BasicUtil.assemblyRequestMap();

     map.put("isDo", Boolean.valueOf(true));

     map.put("modelName", "mcms");
     map.put("url", BasicUtil.getUrl());

     String content = "";
     PageEntity page = new PageEntity();
     page.setPageKey("people/" + diy);

     PageEntity _page = (PageEntity)this.pageBiz.getEntity((BaseEntity)page);
     if (ObjectUtil.isNull(_page)) {
       outJson(resp, false, getResString("templet.file"));
       return;
     }
     try {
       content = ParserUtil.generate(_page.getPagePath(), map, isMobileDevice(req));
     } catch (TemplateNotFoundException e) {
       e.printStackTrace();
     } catch (MalformedTemplateNameException e) {
       e.printStackTrace();
     } catch (ParseException e) {
       e.printStackTrace();
     } catch (IOException e) {
       e.printStackTrace();
     }
     outString(resp, content);
   }
 }


/* Location:              D:\User\Maven\repository\net\mingsoft\ms-mpeople\1.0.11\ms-mpeople-1.0.11.jar!\net\mingsoft\people\action\people\PeopleAction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */