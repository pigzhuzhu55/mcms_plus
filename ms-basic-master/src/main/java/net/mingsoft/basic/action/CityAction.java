 package net.mingsoft.basic.action;

 import com.alibaba.fastjson.serializer.SerializeFilter;
 import io.swagger.annotations.Api;
 import io.swagger.annotations.ApiImplicitParam;
 import io.swagger.annotations.ApiImplicitParams;
 import io.swagger.annotations.ApiOperation;
 import java.util.List;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;
 import net.mingsoft.base.entity.BaseEntity;
 import net.mingsoft.base.filter.DateValueFilter;
 import net.mingsoft.base.filter.DoubleValueFilter;
 import net.mingsoft.base.util.JSONArray;
 import net.mingsoft.base.util.JSONObject;
 import net.mingsoft.basic.bean.EUListBean;
 import net.mingsoft.basic.biz.ICityBiz;
 import net.mingsoft.basic.entity.CityEntity;
 import net.mingsoft.basic.util.BasicUtil;
 import org.apache.commons.lang3.StringUtils;
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
















 @Api("省市县镇村数据管理控制层接口")
 @Controller
 @RequestMapping({"/${ms.manager.path}/basic/city"})
 public class CityAction
   extends BaseAction
 {
   @Autowired
   private ICityBiz cityBiz;

   @ApiOperation("返回主界面index")
   @GetMapping({"/index"})
   public String index(HttpServletResponse response, HttpServletRequest request) { return "/basic/city/index"; }














































   @ApiOperation("查询省市县镇村数据列表")
   @ApiImplicitParams({@ApiImplicitParam(name = "provinceId", value = "省／直辖市／自治区级id", required = false, paramType = "query"), @ApiImplicitParam(name = "provinceName", value = "省／直辖市／自治区级名称", required = false, paramType = "query"), @ApiImplicitParam(name = "cityId", value = "市级id", required = false, paramType = "query"), @ApiImplicitParam(name = "cityName", value = "市级名称", required = false, paramType = "query"), @ApiImplicitParam(name = "cityPy", value = "城市拼音首字母", required = false, paramType = "query"), @ApiImplicitParam(name = "countyId", value = "县／区级id", required = false, paramType = "query"), @ApiImplicitParam(name = "countyName", value = "县／区级名称", required = false, paramType = "query"), @ApiImplicitParam(name = "townId", value = "街道／镇级id", required = false, paramType = "query"), @ApiImplicitParam(name = "townName", value = "街道／镇级名称", required = false, paramType = "query"), @ApiImplicitParam(name = "villageId", value = "村委会id", required = false, paramType = "query"), @ApiImplicitParam(name = "villageName", value = "村委会名称", required = false, paramType = "query")})
   @GetMapping({"/list"})
   @ResponseBody
   public void list(@ModelAttribute @ApiIgnore CityEntity city, HttpServletResponse response, HttpServletRequest request, @ApiIgnore ModelMap model) {
     BasicUtil.startPage();
     List cityList = this.cityBiz.query((BaseEntity)city);
     outJson(response, JSONArray.toJSONString(new EUListBean(cityList, (int)BasicUtil.endPage(cityList).getTotal()), new SerializeFilter[] { (SerializeFilter)new DoubleValueFilter(), (SerializeFilter)new DateValueFilter() }));
   }




   @ApiOperation("返回编辑界面city_form")
   @ApiImplicitParam(name = "id", value = "主键编号", required = true, paramType = "query")
   @GetMapping({"/form"})
   public String form(@ModelAttribute @ApiIgnore CityEntity city, HttpServletResponse response, HttpServletRequest request, @ApiIgnore ModelMap model) {
     if (StringUtils.isEmpty(city.getId())) {
       BaseEntity cityEntity = this.cityBiz.getEntity(Integer.parseInt(city.getId()));
       model.addAttribute("cityEntity", cityEntity);
     }

     return "/basic/city/form";
   }































   @ApiOperation("获取省市县镇村数据")
   @ApiImplicitParam(name = "id", value = "主键编号", required = true, paramType = "query")
   @GetMapping({"/get"})
   @ResponseBody
   public void get(@ModelAttribute @ApiIgnore CityEntity city, HttpServletResponse response, HttpServletRequest request, @ApiIgnore ModelMap model) {
     if (StringUtils.isEmpty(city.getId())) {
       outJson(response, null, false, getResString("err.error", new String[] { getResString("id") }));
       return;
     }
     CityEntity _city = (CityEntity)this.cityBiz.getEntity(Integer.parseInt(city.getId()));
     outJson(response, (BaseEntity)_city);
   }











































   @ApiOperation("保存省市县镇村数据实体")
   @ApiImplicitParams({@ApiImplicitParam(name = "provinceId", value = "省／直辖市／自治区级id", required = false, paramType = "query"), @ApiImplicitParam(name = "provinceName", value = "省／直辖市／自治区级名称", required = false, paramType = "query"), @ApiImplicitParam(name = "cityId", value = "市级id", required = false, paramType = "query"), @ApiImplicitParam(name = "cityName", value = "市级名称", required = false, paramType = "query"), @ApiImplicitParam(name = "cityPy", value = "城市拼音首字母", required = false, paramType = "query"), @ApiImplicitParam(name = "countyId", value = "县／区级id", required = false, paramType = "query"), @ApiImplicitParam(name = "countyName", value = "县／区级名称", required = false, paramType = "query"), @ApiImplicitParam(name = "townId", value = "街道／镇级id", required = false, paramType = "query"), @ApiImplicitParam(name = "townName", value = "街道／镇级名称", required = false, paramType = "query"), @ApiImplicitParam(name = "villageId", value = "村委会id", required = false, paramType = "query"), @ApiImplicitParam(name = "villageName", value = "村委会名称", required = false, paramType = "query")})
   @PostMapping({"/save"})
   @ResponseBody
   public void save(@ModelAttribute @ApiIgnore CityEntity city, HttpServletResponse response, HttpServletRequest request) {
     this.cityBiz.saveEntity((BaseEntity)city);
     outJson(response, JSONObject.toJSONString(city));
   }












   @ApiOperation("批量删除省市县镇村数据")
   @PostMapping({"/delete"})
   @ResponseBody
   public void delete(@RequestBody List<CityEntity> citys, HttpServletResponse response, HttpServletRequest request) {
     int[] ids = new int[citys.size()];
     for (int i = 0; i < citys.size(); i++) {
       ids[i] = Integer.parseInt(((CityEntity)citys.get(i)).getId());
     }
     this.cityBiz.delete(ids);
     outJson(response, true);
   }













































   @ApiOperation("更新省市县镇村数据信息省市县镇村数据")
   @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "主键编号", required = true, paramType = "query"), @ApiImplicitParam(name = "provinceId", value = "省／直辖市／自治区级id", required = false, paramType = "query"), @ApiImplicitParam(name = "provinceName", value = "省／直辖市／自治区级名称", required = false, paramType = "query"), @ApiImplicitParam(name = "cityId", value = "市级id", required = false, paramType = "query"), @ApiImplicitParam(name = "cityName", value = "市级名称", required = false, paramType = "query"), @ApiImplicitParam(name = "cityPy", value = "城市拼音首字母", required = false, paramType = "query"), @ApiImplicitParam(name = "countyId", value = "县／区级id", required = false, paramType = "query"), @ApiImplicitParam(name = "countyName", value = "县／区级名称", required = false, paramType = "query"), @ApiImplicitParam(name = "townId", value = "街道／镇级id", required = false, paramType = "query"), @ApiImplicitParam(name = "townName", value = "街道／镇级名称", required = false, paramType = "query"), @ApiImplicitParam(name = "villageId", value = "村委会id", required = false, paramType = "query"), @ApiImplicitParam(name = "villageName", value = "村委会名称", required = false, paramType = "query")})
   @PostMapping({"/update"})
   @ResponseBody
   public void update(@ModelAttribute @ApiIgnore CityEntity city, HttpServletResponse response, HttpServletRequest request) {
     this.cityBiz.updateEntity((BaseEntity)city);
     outJson(response, JSONObject.toJSONString(city));
   }






   @ApiOperation("查询省列表")
   @GetMapping({"/province"})
   @ResponseBody
   public void province(HttpServletResponse response, HttpServletRequest request) {
     List cityList = this.cityBiz.queryProvince();
     outJson(response, JSONArray.toJSONString(cityList, new SerializeFilter[0]));
   }







   @ApiOperation("根据省id查询城市列表")
   @ApiImplicitParam(name = "provinceId", value = "省／直辖市／自治区级id", required = true, paramType = "query")
   @GetMapping({"/city"})
   @ResponseBody
   public void city(@ModelAttribute @ApiIgnore CityEntity city, HttpServletResponse response, HttpServletRequest request) {
     List cityList = this.cityBiz.queryCity(city);
     outJson(response, JSONArray.toJSONString(cityList, new SerializeFilter[0]));
   }







   @ApiOperation("根据城市id查询区域列表")
   @ApiImplicitParam(name = "cityId", value = "市级id", required = true, paramType = "query")
   @GetMapping({"/county"})
   @ResponseBody
   public void county(@ModelAttribute @ApiIgnore CityEntity city, HttpServletResponse response, HttpServletRequest request) {
     List cityList = this.cityBiz.queryCounty(city);
     outJson(response, JSONArray.toJSONString(cityList, new SerializeFilter[0]));
   }
 }


