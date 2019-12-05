 package net.mingsoft.people.action.people;

 import cn.hutool.core.util.ObjectUtil;
 import io.swagger.annotations.Api;
 import io.swagger.annotations.ApiImplicitParam;
 import io.swagger.annotations.ApiImplicitParams;
 import io.swagger.annotations.ApiOperation;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;
 import net.mingsoft.base.constant.e.BaseEnum;
 import net.mingsoft.base.entity.BaseEntity;
 import net.mingsoft.basic.util.BasicUtil;
 import net.mingsoft.people.action.BaseAction;
 import net.mingsoft.people.biz.IPeopleBiz;
 import net.mingsoft.people.biz.IPeopleUserBiz;
 import net.mingsoft.people.constant.Const;
 import net.mingsoft.people.constant.ModelCode;
 import net.mingsoft.people.entity.PeopleEntity;
 import net.mingsoft.people.entity.PeopleUserEntity;
 import org.apache.commons.lang3.StringUtils;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.stereotype.Controller;
 import org.springframework.ui.ModelMap;
 import org.springframework.validation.BindingResult;
 import org.springframework.web.bind.annotation.GetMapping;
 import org.springframework.web.bind.annotation.ModelAttribute;
 import org.springframework.web.bind.annotation.PostMapping;
 import org.springframework.web.bind.annotation.RequestMapping;
 import org.springframework.web.bind.annotation.ResponseBody;
 import springfox.documentation.annotations.ApiIgnore;











































 @Api("详细用户信息接口")
 @Controller("webPeopleUser")
 @RequestMapping({"/people/user"})
 public class PeopleUserAction
   extends BaseAction
 {
   @Autowired
   private IPeopleUserBiz peopleUserBiz;
   @Autowired
   private IPeopleBiz peopleBiz;

   @ApiOperation("查询人员表详情接口")
   @ApiImplicitParams({@ApiImplicitParam(name = "peopleMail", value = "用户邮箱", required = true, paramType = "query")})
   @GetMapping({"/get"})
   @ResponseBody
   public void get(@ModelAttribute @ApiIgnore PeopleUserEntity people, HttpServletResponse response, HttpServletRequest request, ModelMap model, BindingResult result) {
     people.setPeopleAppId(BasicUtil.getAppId());
     BasicUtil.startPage();
     PeopleUserEntity _people = this.peopleUserBiz.getByEntity(people);
     outJson(response, _people, new String[] { "peoplePassword", "peopleOldPassword" });
   }














   @ApiOperation("读取当前登录用户的基本信息接口")
   @GetMapping({"/info"})
   public void info(HttpServletRequest request, HttpServletResponse response) {
     PeopleEntity people = getPeopleBySession();
     PeopleUserEntity peopleUser = (PeopleUserEntity)this.peopleUserBiz.getEntity(people.getPeopleId());
     if (peopleUser == null) {

       outJson(response, (BaseEnum)ModelCode.PEOPLE_USER, false);

       return;
     }
     outJson(response, peopleUser, new String[] { "peopleOldPassword", "peoplePassword" });
   }






























   @ApiOperation("更新用户信息接口")
   @ApiImplicitParams({@ApiImplicitParam(name = "puRealName", value = "用户真实名称", required = false, paramType = "query"), @ApiImplicitParam(name = "puAddress", value = "用户地址", required = false, paramType = "query"), @ApiImplicitParam(name = "puIcon", value = "用户头像图标地址", required = false, paramType = "query"), @ApiImplicitParam(name = "puNickname", value = "用户昵称", required = false, paramType = "query"), @ApiImplicitParam(name = "puSex", value = "用户性别(0.未知、1.男、2.女)", required = false, paramType = "query"), @ApiImplicitParam(name = "puBirthday", value = "用户出生年月日", required = false, paramType = "query"), @ApiImplicitParam(name = "puCard", value = "身份证", required = false, paramType = "query"), @ApiImplicitParam(name = "puProvince", value = "省", required = false, paramType = "query"), @ApiImplicitParam(name = "puCity", value = "城市", required = false, paramType = "query"), @ApiImplicitParam(name = "puDistrict", value = "区", required = false, paramType = "query"), @ApiImplicitParam(name = "puStreet", value = "街道", required = false, paramType = "query")})
   @PostMapping({"/update"})
   public void update(@ModelAttribute @ApiIgnore PeopleUserEntity peopleUser, HttpServletRequest request, HttpServletResponse response) {
     if (peopleUser == null) {

       outJson(response, (BaseEnum)ModelCode.PEOPLE_USER, false,
           getResString("err.empty", new String[] { getResString("people") }));
       return;
     }
     PeopleEntity peopleByMail = this.peopleBiz.getEntityByMailOrPhone(peopleUser.getPeopleMail(), BasicUtil.getAppId());
     if (ObjectUtil.isNotNull(peopleByMail)) {
       outJson(response, (BaseEnum)ModelCode.PEOPLE, false, getResString("err.exist", new String[] { getResString("people.mail") }));
       return;
     }
     int peopleId = getPeopleBySession().getPeopleId();
     peopleUser.setPeopleId(peopleId);
     peopleUser.setPuPeopleId(Integer.valueOf(peopleId));
     PeopleUserEntity pue = (PeopleUserEntity)this.peopleUserBiz.getEntity(peopleUser.getPeopleId());
     if (pue.getPuPeopleId().intValue() == 0) {
       this.peopleUserBiz.saveEntity((BaseEntity)peopleUser);
     } else {
       this.peopleUserBiz.updatePeople((PeopleEntity)peopleUser);
     }

     outJson(response, (BaseEnum)ModelCode.PEOPLE_USER, true, getResString("success"));
   }















   @ApiOperation("保存用户头像(包含头像)接口")
   @ApiImplicitParams({@ApiImplicitParam(name = "puIcon", value = "用户头像", required = true, paramType = "query"), @ApiImplicitParam(name = "peopleMail", value = "用户手机", required = false, paramType = "query"), @ApiImplicitParam(name = "peoplePhone", value = "用户邮箱", required = false, paramType = "query")})
   @PostMapping({"/saveUserIcon"})
   public void saveUserIcon(@ModelAttribute @ApiIgnore PeopleUserEntity peopleUser, HttpServletRequest request, HttpServletResponse response) {
     PeopleEntity people = getPeopleBySession();

     if (people == null) {

       outJson(response, (BaseEnum)ModelCode.PEOPLE_USER, false,
           getResString("people.session.msg.null.error", Const.RESOURCES));

       return;
     }
     if (peopleUser == null) {

       outJson(response, (BaseEnum)ModelCode.PEOPLE_USER, false,
           getResString("people.user.msg.null.error", Const.RESOURCES));

       return;
     }
     String imgPath = peopleUser.getPuIcon().trim();
     if (!StringUtils.isBlank(imgPath)) {
       peopleUser.setPuIcon(imgPath);
     }

     peopleUser.setPeopleAppId(people.getPeopleAppId());
     peopleUser.setPeopleId(people.getPeopleId());
     this.peopleUserBiz.saveEntity((BaseEntity)peopleUser);

     if (!StringUtils.isBlank(peopleUser.getPeopleMail())) {
       people.setPeopleMail(peopleUser.getPeopleMail());
     }
     if (!StringUtils.isBlank(peopleUser.getPeoplePhone())) {
       people.setPeoplePhone(peopleUser.getPeoplePhone());
     }
     this.peopleBiz.updateEntity((BaseEntity)people);

     outJson(response, (BaseEnum)ModelCode.PEOPLE_USER, true,
         getResString("people.user.save.msg.success", Const.RESOURCES));
   }













   @ApiOperation("更新用户信息(包含头像)接口")
   @ApiImplicitParams({@ApiImplicitParam(name = "puIcon", value = "用户头像", required = false, paramType = "query")})
   @PostMapping({"/updateUserIcon"})
   public void updateUserIcon(@ModelAttribute @ApiIgnore PeopleUserEntity peopleUser, HttpServletRequest request, HttpServletResponse response) {
     PeopleEntity people = getPeopleBySession();

     if (people == null) {

       outJson(response, (BaseEnum)ModelCode.PEOPLE_USER, false,
           getResString("people.session.msg.null.error", Const.RESOURCES));

       return;
     }
     PeopleUserEntity oldPeopleUser = (PeopleUserEntity)this.peopleUserBiz.getEntity(people.getPeopleId());

     if (peopleUser == null) {

       outJson(response, (BaseEnum)ModelCode.PEOPLE_USER, false,
           getResString("people.user.msg.null.error", Const.RESOURCES));

       return;
     }
     String imgPath = peopleUser.getPuIcon().trim();
     if (!StringUtils.isBlank(imgPath)) {
       oldPeopleUser.setPuIcon(imgPath);
     }

     this.peopleUserBiz.updatePeople((PeopleEntity)oldPeopleUser);

     outJson(response, (BaseEnum)ModelCode.PEOPLE_USER, true,
         getResString("people.user.update.msg.success", Const.RESOURCES));
   }
 }


