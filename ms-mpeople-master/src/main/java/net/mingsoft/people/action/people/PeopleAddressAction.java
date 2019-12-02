 package net.mingsoft.people.action.people;

 import cn.hutool.core.lang.Validator;
 import com.alibaba.fastjson.JSONArray;
 import com.alibaba.fastjson.JSONObject;
 import io.swagger.annotations.Api;
 import io.swagger.annotations.ApiImplicitParam;
 import io.swagger.annotations.ApiImplicitParams;
 import io.swagger.annotations.ApiOperation;
 import java.util.List;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;
 import net.mingsoft.base.constant.e.BaseEnum;
 import net.mingsoft.base.entity.BaseEntity;
 import net.mingsoft.basic.util.BasicUtil;
 import net.mingsoft.people.action.BaseAction;
 import net.mingsoft.people.biz.IPeopleAddressBiz;
 import net.mingsoft.people.constant.Const;
 import net.mingsoft.people.constant.ModelCode;
 import net.mingsoft.people.entity.PeopleAddressEntity;
 import net.mingsoft.people.entity.PeopleEntity;
 import org.apache.commons.lang3.StringUtils;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.stereotype.Controller;
 import org.springframework.web.bind.annotation.GetMapping;
 import org.springframework.web.bind.annotation.ModelAttribute;
 import org.springframework.web.bind.annotation.PostMapping;
 import org.springframework.web.bind.annotation.RequestMapping;
 import springfox.documentation.annotations.ApiIgnore;


















































 @Api("普通用户收货地址信息接口")
 @Controller("peopleAddress")
 @RequestMapping({"/people/address"})
 public class PeopleAddressAction
   extends BaseAction
 {
   @Autowired
   private IPeopleAddressBiz peopleAddressBiz;

   @ApiOperation("用户收货地址列表接口")
   @GetMapping({"/list"})
   public void list(@ModelAttribute @ApiIgnore PeopleAddressEntity peopleAddress, HttpServletRequest request, HttpServletResponse response) {
     PeopleEntity people = getPeopleBySession();

     peopleAddress.setPeopleAddressAppId(BasicUtil.getAppId());
     peopleAddress.setPeopleAddressPeopleId(people.getPeopleId());
     List list = this.peopleAddressBiz.query((BaseEntity)peopleAddress);
     outJson(response, JSONArray.toJSONString(list));
   }







































   @ApiOperation("保存用户收货地址接口")
   @ApiImplicitParams({@ApiImplicitParam(name = "peopleAddressConsigneeName", value = "用户收货人姓名", required = true, paramType = "query"), @ApiImplicitParam(name = "peopleAddressProvince", value = "收货人所在的省", required = true, paramType = "query"), @ApiImplicitParam(name = "peopleAddressProvinceId", value = "收货人所在的省编号", required = true, paramType = "query"), @ApiImplicitParam(name = "peopleAddressCity", value = "收货人所在的市", required = true, paramType = "query"), @ApiImplicitParam(name = "peopleAddressCityId", value = "收货人所在的市编号", required = true, paramType = "query"), @ApiImplicitParam(name = "peopleAddressDistrict", value = "收货人所在区", required = true, paramType = "query"), @ApiImplicitParam(name = "peopleAddressDistrictId", value = "收货人所在区编号", required = true, paramType = "query"), @ApiImplicitParam(name = "peopleAddressStreet", value = "街道", required = true, paramType = "query"), @ApiImplicitParam(name = "peopleAddressStreetId", value = "街道编号", required = true, paramType = "query"), @ApiImplicitParam(name = "peopleAddressAddress", value = "收货人的详细收货地址", required = true, paramType = "query"), @ApiImplicitParam(name = "peopleAddressMail", value = "收货人邮箱", required = true, paramType = "query"), @ApiImplicitParam(name = "peopleAddressPhone", value = "收货人手机", required = true, paramType = "query"), @ApiImplicitParam(name = "peopleAddressDefault", value = "是否是收货人最终收货地址。0代表是，1代表不是，默认为0", required = false, paramType = "query")})
   @PostMapping({"/save"})
   public void save(@ModelAttribute @ApiIgnore PeopleAddressEntity peopleAddress, HttpServletRequest request, HttpServletResponse response) {
     PeopleEntity peopleEntity = getPeopleBySession();

     if (peopleAddress == null) {
       outJson(response, (BaseEnum)ModelCode.PEOPLE, false, getResString("people.msg.null.error"),
           getResString("people.msg.null.error"));

       return;
     }
     if (StringUtils.isBlank(peopleAddress.getPeopleAddressPhone())) {
       outJson(response, (BaseEnum)ModelCode.PEOPLE, false,
           getResString("people.msg.phone.error", Const.RESOURCES));

       return;
     }
     if (!StringUtils.isBlank(peopleAddress.getPeopleAddressMail()) &&
       !Validator.isEmail(peopleAddress.getPeopleAddressMail())) {
       outJson(response, (BaseEnum)ModelCode.PEOPLE, false,
           getResString("people.msg.mail.error", Const.RESOURCES));

       return;
     }

     if (StringUtils.isBlank(peopleAddress.getPeopleAddressProvince())) {
       outJson(response, (BaseEnum)ModelCode.PEOPLE, false, getResString("people.user.msg.null.error"));

       return;
     }
     peopleAddress.setPeopleAddressPeopleId(peopleEntity.getPeopleId());

     peopleAddress.setPeopleAddressAppId(BasicUtil.getAppId());

     this.peopleAddressBiz.saveEntity((BaseEntity)peopleAddress);
     outJson(response, null, true, JSONObject.toJSONString(peopleAddress));
   }








































   @ApiOperation("更新用户收货地址接口")
   @ApiImplicitParams({@ApiImplicitParam(name = "peopleAddressId", value = "用户收货地址编号", required = true, paramType = "query"), @ApiImplicitParam(name = "peopleAddressConsigneeName", value = "用户收货人姓名", required = true, paramType = "query"), @ApiImplicitParam(name = "peopleAddressProvince", value = "收货人所在的省", required = true, paramType = "query"), @ApiImplicitParam(name = "peopleAddressProvinceId", value = "收货人所在的省编号", required = true, paramType = "query"), @ApiImplicitParam(name = "peopleAddressCity", value = "收货人所在的市", required = true, paramType = "query"), @ApiImplicitParam(name = "peopleAddressCityId", value = "收货人所在的市编号", required = true, paramType = "query"), @ApiImplicitParam(name = "peopleAddressDistrict", value = "收货人所在区", required = true, paramType = "query"), @ApiImplicitParam(name = "peopleAddressDistrictId", value = "收货人所在区编号", required = true, paramType = "query"), @ApiImplicitParam(name = "peopleAddressStreet", value = "街道", required = true, paramType = "query"), @ApiImplicitParam(name = "peopleAddressStreetId", value = "街道编号", required = true, paramType = "query"), @ApiImplicitParam(name = "peopleAddressAddress", value = "收货人的详细收货地址", required = true, paramType = "query"), @ApiImplicitParam(name = "peopleAddressMail", value = "收货人邮箱", required = true, paramType = "query"), @ApiImplicitParam(name = "peopleAddressPhone", value = "收货人手机", required = true, paramType = "query"), @ApiImplicitParam(name = "peopleAddressDefault", value = "是否是收货人最终收货地址。0代表是，1代表不是，默认为0", required = false, paramType = "query")})
   @PostMapping({"/update"})
   public void update(@ModelAttribute @ApiIgnore PeopleAddressEntity peopleAddress, HttpServletRequest request, HttpServletResponse response) {
     PeopleEntity people = getPeopleBySession();
     peopleAddress.setPeopleAddressPeopleId(people.getPeopleId());
     PeopleAddressEntity address = (PeopleAddressEntity)this.peopleAddressBiz.getEntity((BaseEntity)peopleAddress);
     if (people.getPeopleId() != address.getPeopleAddressPeopleId()) {
       outJson(response, false);

       return;
     }
     if (StringUtils.isBlank(peopleAddress.getPeopleAddressProvince()) ||
       StringUtils.isBlank(peopleAddress.getPeopleAddressAddress())) {
       outJson(response, (BaseEnum)ModelCode.PEOPLE, false,
           getResString("people.address", Const.RESOURCES));

       return;
     }
     if (StringUtils.isBlank(peopleAddress.getPeopleAddressPhone())) {
       outJson(response, (BaseEnum)ModelCode.PEOPLE, false,
           getResString("people.msg.phone.error", Const.RESOURCES));
       return;
     }
     peopleAddress.setPeopleAddressPeopleId(people.getPeopleId());
     peopleAddress.setPeopleAddressAppId(BasicUtil.getAppId());

     this.peopleAddressBiz.updateEntity((BaseEntity)peopleAddress);
     outJson(response, null, true);
   }
















   @ApiOperation("设置默认地址接口")
   @ApiImplicitParam(name = "peopleAddressId", value = "用户收货地址编号", required = true, paramType = "query")
   @PostMapping({"/setDefault"})
   public void setDefault(@ModelAttribute @ApiIgnore PeopleAddressEntity peopleAddress, HttpServletRequest request, HttpServletResponse response) {
     PeopleEntity people = getPeopleBySession();

     peopleAddress.setPeopleAddressPeopleId(people.getPeopleId());
     peopleAddress.setPeopleAddressAppId(BasicUtil.getAppId());

     this.peopleAddressBiz.setDefault(peopleAddress);
     outJson(response, null, true);
   }
















   @ApiOperation("根据收货地址编号删除收货地址信息")
   @ApiImplicitParam(name = "peopleAddressId", value = "用户收货地址编号", required = true, paramType = "query")
   @PostMapping({"/delete"})
   public void delete(@ModelAttribute @ApiIgnore PeopleAddressEntity peopleAddress, HttpServletRequest request, HttpServletResponse response) {
     peopleAddress.setPeopleAddressPeopleId(getPeopleBySession().getPeopleId());
     peopleAddress.setPeopleAddressAppId(BasicUtil.getAppId());
     this.peopleAddressBiz.deleteEntity((BaseEntity)peopleAddress);
     outJson(response, null, true);
   }

























   @ApiOperation("获取收货地址详情接口")
   @ApiImplicitParams({@ApiImplicitParam(name = "peopleAddressId", value = "用户收货地址编号", required = true, paramType = "query")})
   @GetMapping({"/get"})
   public void get(@ModelAttribute @ApiIgnore PeopleAddressEntity peopleAddress, HttpServletRequest request, HttpServletResponse response) {
     int peopleId = getPeopleBySession().getPeopleId();

     peopleAddress.setPeopleAddressPeopleId(peopleId);
     PeopleAddressEntity address = (PeopleAddressEntity)this.peopleAddressBiz.getEntity((BaseEntity)peopleAddress);
     if (peopleId != address.getPeopleAddressPeopleId()) {
       outJson(response, false);
       return;
     }
     outJson(response, JSONObject.toJSONString(address));
   }
 }


/* Location:              D:\User\Maven\repository\net\mingsoft\ms-mpeople\1.0.11\ms-mpeople-1.0.11.jar!\net\mingsoft\people\action\people\PeopleAddressAction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */