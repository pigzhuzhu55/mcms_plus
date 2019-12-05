 package net.mingsoft.people.action;

 import cn.hutool.crypto.digest.DigestUtil;
 import io.swagger.annotations.Api;
 import io.swagger.annotations.ApiImplicitParam;
 import io.swagger.annotations.ApiImplicitParams;
 import io.swagger.annotations.ApiOperation;
 import java.util.Date;
 import java.util.List;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;
 import net.mingsoft.base.constant.e.BaseEnum;
 import net.mingsoft.base.entity.BaseEntity;
 import net.mingsoft.base.util.JSONObject;
 import net.mingsoft.basic.bean.EUListBean;
 import net.mingsoft.basic.util.BasicUtil;
 import net.mingsoft.basic.util.FileUtil;
 import net.mingsoft.basic.util.StringUtil;
 import net.mingsoft.people.bean.PeopleBean;
 import net.mingsoft.people.biz.IPeopleBiz;
 import net.mingsoft.people.biz.IPeopleUserBiz;
 import net.mingsoft.people.constant.Const;
 import net.mingsoft.people.constant.ModelCode;
 import net.mingsoft.people.entity.PeopleEntity;
 import net.mingsoft.people.entity.PeopleUserEntity;
 import org.apache.commons.lang3.StringUtils;
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




















 @Api("用户基础信息接口")
 @Controller
 @RequestMapping({"/${ms.manager.path}/people/peopleUser"})
 public class PeopleUserAction
   extends BaseAction
 {
   @Autowired
   private IPeopleUserBiz peopleUserBiz;
   @Autowired
   private IPeopleBiz peopleBiz;

   @GetMapping({"/index"})
   public String index(HttpServletResponse response, HttpServletRequest request) { return "/people/user/index"; }













































   @ApiOperation("用户基础信息列表接口")
   @ApiImplicitParams({@ApiImplicitParam(name = "peopleName", value = "用户账号", required = false, paramType = "query"), @ApiImplicitParam(name = "puSex", value = "用户性别", required = false, paramType = "query"), @ApiImplicitParam(name = "puNickname", value = "用户昵称", required = false, paramType = "query"), @ApiImplicitParam(name = "peopleState", value = "审核状态", required = false, paramType = "query"), @ApiImplicitParam(name = "puRealName", value = "真实姓名", required = false, paramType = "query"), @ApiImplicitParam(name = "peopleDateTimes", value = "注册时间范围，如2019-01-23至2019-01-24", required = false, paramType = "query")})
   @GetMapping({"/list"})
   @ResponseBody
   public EUListBean list(@ModelAttribute @ApiIgnore PeopleBean peopleUser, HttpServletResponse response, HttpServletRequest request, @ApiIgnore ModelMap model) {
     if (peopleUser == null) {
       peopleUser = new PeopleBean();
     }
     peopleUser.setPeopleAppId(BasicUtil.getAppId());
     BasicUtil.startPage();
     List<PeopleBean> peopleUserList = this.peopleUserBiz.query((BaseEntity)peopleUser);
     EUListBean list = new EUListBean(peopleUserList, (int)BasicUtil.endPage(peopleUserList).getTotal());
     return list;
   }




   @GetMapping({"/form"})
   public String form(@ModelAttribute PeopleUserEntity peopleUser, HttpServletResponse response, HttpServletRequest request, ModelMap model) {
     if (peopleUser.getPuPeopleId() != null) {
       BaseEntity peopleUserEntity = this.peopleUserBiz.getEntity(peopleUser.getPuPeopleId().intValue());
       model.addAttribute("peopleUserEntity", peopleUserEntity);
     }
     return "/people/user/form";
   }



































   @ApiOperation("获取用户基础详情接口")
   @ApiImplicitParam(name = "puPeopleId", value = "用户编号", required = true, paramType = "query")
   @GetMapping({"/get"})
   @ResponseBody
   public void get(@ModelAttribute @ApiIgnore PeopleUserEntity peopleUser, HttpServletResponse response, HttpServletRequest request, @ApiIgnore ModelMap model) {
     if (peopleUser.getPuPeopleId().intValue() <= 0) {
       outJson(response, null, false, getResString("err.error", new String[] { getResString("pu.people.id") }));
       return;
     }
     PeopleUserEntity _peopleUser = (PeopleUserEntity)this.peopleUserBiz.getEntity(peopleUser.getPuPeopleId().intValue());
     outJson(response, _peopleUser, new String[] { "peopleOldPassword", "peoplePassword" });
   }













































   @ApiOperation("保存用户基础信息接口")
   @ApiImplicitParams({@ApiImplicitParam(name = "puRealName", value = "用户真实名称", required = false, paramType = "query"), @ApiImplicitParam(name = "peopleName", value = "登录账号", required = true, paramType = "query"), @ApiImplicitParam(name = "peoplePassword", value = "用户密码", required = true, paramType = "query"), @ApiImplicitParam(name = "puAddress", value = "用户地址", required = false, paramType = "query"), @ApiImplicitParam(name = "puIcon", value = "用户头像图标地址", required = false, paramType = "query"), @ApiImplicitParam(name = "puNickname", value = "用户昵称", required = false, paramType = "query"), @ApiImplicitParam(name = "puSex", value = "用户性别", required = false, paramType = "query"), @ApiImplicitParam(name = "puCard", value = "身份证", required = false, paramType = "query")})
   @PostMapping({"/save"})
   @ResponseBody
   @RequiresPermissions({"people:save"})
   public void save(@ModelAttribute @ApiIgnore PeopleUserEntity peopleUser, HttpServletResponse response, HttpServletRequest request) {
     if (!StringUtil.checkLength(peopleUser.getPuRealName() + "", 0, 50)) {
       outJson(response, null, false, getResString("err.length", new String[] { getResString("pu.real.name"), "0", "50" }));
       return;
     }
     if (!StringUtil.checkLength(peopleUser.getPuAddress() + "", 0, 200)) {
       outJson(response, null, false, getResString("err.length", new String[] { getResString("pu.address"), "0", "200" }));
       return;
     }
     if (!StringUtil.checkLength(peopleUser.getPuIcon() + "", 0, 200)) {
       outJson(response, null, false, getResString("err.length", new String[] { getResString("pu.icon"), "0", "200" }));
       return;
     }
     if (!StringUtil.checkLength(peopleUser.getPuNickname() + "", 0, 50)) {
       outJson(response, null, false, getResString("err.length", new String[] { getResString("pu.nickname"), "0", "50" }));

       return;
     }
     if (!StringUtil.checkLength(peopleUser.getPuSex() + "", 0, 10)) {
       outJson(response, null, false, getResString("err.length", new String[] { getResString("pu.sex"), "0", "10" }));

       return;
     }
     if (!StringUtil.checkLength(peopleUser.getPuCard() + "", 0, 255)) {
       outJson(response, null, false, getResString("err.length", new String[] { getResString("pu.card"), "0", "255" }));
       return;
     }
     if (!StringUtils.isBlank(DigestUtil.md5Hex(peopleUser.getPeoplePassword())))
     {
       peopleUser.setPeoplePassword(DigestUtil.md5Hex(peopleUser.getPeoplePassword()));
     }

     if (!checkPeople(peopleUser, request, response)) {
       return;
     }
     peopleUser.setPeopleDateTime(new Date());
     peopleUser.setPeopleAppId(BasicUtil.getAppId());
     this.peopleUserBiz.savePeople((PeopleEntity)peopleUser);
     outJson(response, peopleUser, new String[] { "peopleOldPassword", "peoplePassword" });
   }












   @ApiOperation("批量删除用户接口")
   @PostMapping({"/delete"})
   @ResponseBody
   @RequiresPermissions({"people:del"})
   public void delete(@RequestBody List<PeopleUserEntity> peopleUsers, HttpServletResponse response, HttpServletRequest request) {
     int[] ids = new int[peopleUsers.size()];
     for (int i = 0; i < peopleUsers.size(); i++) {
       ids[i] = ((PeopleUserEntity)peopleUsers.get(i)).getPuPeopleId().intValue();
     }
     FileUtil.del(peopleUsers);
     this.peopleUserBiz.deletePeople(ids);
     outJson(response, true);
   }














































   @ApiOperation("修改用户基础信息接口")
   @ApiImplicitParams({@ApiImplicitParam(name = "puPeopleId", value = "用户编号", required = true, paramType = "query"), @ApiImplicitParam(name = "peopleName", value = "登录账号", required = true, paramType = "query"), @ApiImplicitParam(name = "peoplePassword", value = "用户密码", required = true, paramType = "query"), @ApiImplicitParam(name = "puRealName", value = "用户真实名称", required = false, paramType = "query"), @ApiImplicitParam(name = "puAddress", value = "用户地址", required = false, paramType = "query"), @ApiImplicitParam(name = "puIcon", value = "用户头像图标地址", required = false, paramType = "query"), @ApiImplicitParam(name = "puNickname", value = "用户昵称", required = false, paramType = "query"), @ApiImplicitParam(name = "puSex", value = "用户性别", required = false, paramType = "query"), @ApiImplicitParam(name = "puCard", value = "身份证", required = false, paramType = "query")})
   @PostMapping({"/update"})
   @ResponseBody
   @RequiresPermissions({"people:update"})
   public void update(@ModelAttribute @ApiIgnore PeopleUserEntity peopleUser, HttpServletResponse response, HttpServletRequest request) {
     if (!StringUtil.checkLength(peopleUser.getPuRealName() + "", 0, 50)) {
       outJson(response, null, false, getResString("err.length", new String[] { getResString("pu.real.name"), "0", "50" }));
       return;
     }
     if (!StringUtil.checkLength(peopleUser.getPuAddress() + "", 0, 200)) {
       outJson(response, null, false, getResString("err.length", new String[] { getResString("pu.address"), "0", "200" }));
       return;
     }
     if (!StringUtil.checkLength(peopleUser.getPuIcon() + "", 0, 200)) {
       outJson(response, null, false, getResString("err.length", new String[] { getResString("pu.icon"), "0", "200" }));
       return;
     }
     if (!StringUtil.checkLength(peopleUser.getPuNickname() + "", 0, 50)) {
       outJson(response, null, false, getResString("err.length", new String[] { getResString("pu.nickname"), "0", "50" }));

       return;
     }
     if (!StringUtil.checkLength(peopleUser.getPuSex() + "", 0, 10)) {
       outJson(response, null, false, getResString("err.length", new String[] { getResString("pu.sex"), "0", "10" }));

       return;
     }
     if (!StringUtil.checkLength(peopleUser.getPuCard() + "", 0, 255)) {
       outJson(response, null, false, getResString("err.length", new String[] { getResString("pu.card"), "0", "255" }));
       return;
     }
     if (!checkUpdatePeople(peopleUser, request, response)) {
       return;
     }

     if (!StringUtils.isBlank(peopleUser.getPeoplePassword()))
     {
       peopleUser.setPeoplePassword(DigestUtil.md5Hex(peopleUser.getPeoplePassword()));
     }
     peopleUser.setPeopleId(peopleUser.getPuPeopleId().intValue());
     this.peopleUserBiz.updatePeople((PeopleEntity)peopleUser);
     outJson(response, peopleUser, new String[] { "peopleOldPassword", "peoplePassword" });
   }









   public boolean checkUpdatePeople(PeopleUserEntity peopleUser, HttpServletRequest request, HttpServletResponse response) {
     PeopleUserEntity oldPeopleUser = (PeopleUserEntity)this.peopleUserBiz.getEntity(peopleUser.getPuPeopleId().intValue());

     int appId = BasicUtil.getAppId();

     if (!StringUtils.isBlank(peopleUser.getPeopleMail()) && !StringUtil.isEmail(peopleUser.getPeopleMail()).booleanValue()) {
       outJson(response, (BaseEnum)ModelCode.PEOPLE_USER, false, getResString("people.msg.mail.format.error", Const.RESOURCES));
       return false;
     }

     if (StringUtils.isBlank(peopleUser.getPeopleName())) {
       outJson(response, (BaseEnum)ModelCode.PEOPLE_USER, false, getResString("people.msg.name.error", Const.RESOURCES));
       return false;
     }


     if (!StringUtils.isBlank(peopleUser.getPeoplePhone()) && !StringUtil.isMobile(peopleUser.getPeoplePhone())) {
       outJson(response, (BaseEnum)ModelCode.PEOPLE_USER, false, getResString("people.msg.phone.format.error", Const.RESOURCES));
       return false;
     }



     if (!StringUtils.isBlank(peopleUser.getPeopleName())) {

       PeopleEntity peoplePhone = this.peopleBiz.getEntityByUserName(peopleUser.getPeopleName(), appId);


       if (StringUtils.isBlank(oldPeopleUser.getPeopleName())) {
         if (peoplePhone != null) {
           outJson(response, (BaseEnum)ModelCode.PEOPLE_USER, false, getResString("people.register.msg.name.repeat.error", Const.RESOURCES));
           return false;
         }

       } else if (!oldPeopleUser.getPeopleName().equals(peopleUser.getPeopleName()) &&
         peoplePhone != null) {
         outJson(response, (BaseEnum)ModelCode.PEOPLE_USER, false, getResString("people.register.msg.name.repeat.error", Const.RESOURCES));
         return false;
       }
     }



     if (!StringUtils.isBlank(peopleUser.getPeoplePhone())) {
       PeopleEntity peoplePhone = this.peopleBiz.getEntityByUserName(peopleUser.getPeoplePhone(), appId);


       if (StringUtils.isBlank(oldPeopleUser.getPeoplePhone())) {
         if (peoplePhone != null) {
           outJson(response, (BaseEnum)ModelCode.PEOPLE_USER, false, getResString("people.register.msg.name.repeat.error", Const.RESOURCES));
           return false;
         }

       } else if (!oldPeopleUser.getPeoplePhone().equals(peopleUser.getPeoplePhone()) &&
         peoplePhone != null) {
         outJson(response, (BaseEnum)ModelCode.PEOPLE_USER, false, getResString("people.register.msg.name.repeat.error", Const.RESOURCES));
         return false;
       }
     }



     if (!StringUtils.isBlank(peopleUser.getPeopleMail())) {
       PeopleEntity peoplePhone = this.peopleBiz.getEntityByUserName(peopleUser.getPeopleMail(), appId);


       if (StringUtils.isBlank(oldPeopleUser.getPeopleMail())) {
         if (peoplePhone != null) {
           outJson(response, (BaseEnum)ModelCode.PEOPLE_USER, false, getResString("people.register.msg.name.repeat.error", Const.RESOURCES));
           return false;
         }

       } else if (!oldPeopleUser.getPeopleMail().equals(peopleUser.getPeopleMail()) &&
         peoplePhone != null) {
         outJson(response, (BaseEnum)ModelCode.PEOPLE_USER, false, getResString("people.register.msg.name.repeat.error", Const.RESOURCES));
         return false;
       }
     }



     return true;
   }








   public boolean checkPeople(PeopleUserEntity peopleUser, HttpServletRequest request, HttpServletResponse response) {
     int appId = BasicUtil.getAppId();

     if (!StringUtils.isBlank(peopleUser.getPeopleMail()) && !StringUtil.isEmail(peopleUser.getPeopleMail()).booleanValue()) {
       outJson(response, (BaseEnum)ModelCode.PEOPLE_USER, false, getResString("people.msg.mail.format.error", Const.RESOURCES));
       return false;
     }

     if (StringUtils.isBlank(peopleUser.getPeopleName())) {
       outJson(response, (BaseEnum)ModelCode.PEOPLE_USER, false, getResString("people.msg.name.error", Const.RESOURCES));
       return false;
     }

     if (!StringUtils.isBlank(peopleUser.getPeoplePhone()) && !StringUtil.isMobile(peopleUser.getPeoplePhone())) {
       outJson(response, (BaseEnum)ModelCode.PEOPLE_USER, false, getResString("people.msg.phone.format.error", Const.RESOURCES));
       return false;
     }



     if (!StringUtils.isBlank(peopleUser.getPeopleName())) {

       PeopleEntity peoplePhone = this.peopleBiz.getEntityByUserName(peopleUser.getPeopleName(), appId);
       if (peoplePhone != null) {
         outJson(response, (BaseEnum)ModelCode.PEOPLE_USER, false, getResString("people.register.msg.name.repeat.error", Const.RESOURCES));
         return false;
       }
     }
     if (!StringUtils.isBlank(peopleUser.getPeoplePhone())) {

       PeopleEntity peoplePhone = this.peopleBiz.getEntityByUserName(peopleUser.getPeoplePhone(), appId);
       if (peoplePhone != null) {
         outJson(response, (BaseEnum)ModelCode.PEOPLE_USER, false, getResString("people.register.msg.phone.repeat.error", Const.RESOURCES));
         return false;
       }
     }
     if (!StringUtils.isBlank(peopleUser.getPeopleMail())) {

       PeopleEntity peopleMail = this.peopleBiz.getEntityByUserName(peopleUser.getPeopleMail(), appId);
       if (peopleMail != null) {
         outJson(response, (BaseEnum)ModelCode.PEOPLE_USER, false, getResString("people.register.msg.mail.repeat.error", Const.RESOURCES));
         return false;
       }
     }

     return true;
   }







   @Deprecated
   @GetMapping({"/getEntity"})
   public void getEntity(String peopleId, HttpServletRequest request, HttpServletResponse response) {
     if (StringUtils.isBlank(peopleId) || !StringUtil.isInteger(peopleId)) {
       outJson(response, (BaseEnum)ModelCode.PEOPLE_USER, false);
       return;
     }
     PeopleUserEntity peopleUser = (PeopleUserEntity)this.peopleUserBiz.getEntity(Integer.parseInt(peopleId));
     if (peopleUser == null) {
       outJson(response, (BaseEnum)ModelCode.PEOPLE_USER, false);
       return;
     }
     outJson(response, (BaseEnum)ModelCode.PEOPLE_USER, true, null, JSONObject.toJSONString(peopleUser));
   }
 }


