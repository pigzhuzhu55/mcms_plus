 package net.mingsoft.basic.action.web;

 import com.alibaba.fastjson.JSONArray;
 import io.swagger.annotations.Api;
 import io.swagger.annotations.ApiImplicitParam;
 import io.swagger.annotations.ApiImplicitParams;
 import io.swagger.annotations.ApiOperation;
 import java.util.List;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;
 import net.mingsoft.base.entity.BaseEntity;
 import net.mingsoft.basic.action.BaseAction;
 import net.mingsoft.basic.bean.CityBean;
 import net.mingsoft.basic.biz.ICityBiz;
 import net.mingsoft.basic.entity.CityEntity;
 import net.mingsoft.basic.util.BasicUtil;
 import org.apache.commons.lang3.StringUtils;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.stereotype.Controller;
 import org.springframework.ui.ModelMap;
 import org.springframework.web.bind.annotation.GetMapping;
 import org.springframework.web.bind.annotation.ModelAttribute;
 import org.springframework.web.bind.annotation.RequestMapping;
 import org.springframework.web.bind.annotation.ResponseBody;
 import springfox.documentation.annotations.ApiIgnore;


























































 @Api("省市县镇村数据管理接口")
 @Controller("webCityAction")
 @RequestMapping({"/basic/city"})
 public class CityAction
   extends BaseAction
 {
   @Autowired
   private ICityBiz cityBiz;

   @ApiOperation("查询省市县镇村数据列表")
   @ApiImplicitParams({@ApiImplicitParam(name = "provinceId", value = "省／直辖市／自治区级id", required = false, paramType = "query"), @ApiImplicitParam(name = "provinceName", value = "省／直辖市／自治区级名称", required = false, paramType = "query"), @ApiImplicitParam(name = "cityId", value = "市级id", required = false, paramType = "query"), @ApiImplicitParam(name = "cityName", value = "市级名称", required = false, paramType = "query"), @ApiImplicitParam(name = "cityPy", value = "城市拼音首字母", required = false, paramType = "query"), @ApiImplicitParam(name = "countyId", value = "县／区级id", required = false, paramType = "query"), @ApiImplicitParam(name = "countyName", value = "县／区级名称", required = false, paramType = "query"), @ApiImplicitParam(name = "townId", value = "街道／镇级id", required = false, paramType = "query"), @ApiImplicitParam(name = "townName", value = "街道／镇级名称", required = false, paramType = "query"), @ApiImplicitParam(name = "villageId", value = "村委会id", required = false, paramType = "query"), @ApiImplicitParam(name = "villageName", value = "村委会名称", required = false, paramType = "query")})
   @GetMapping({"/list"})
   @ResponseBody
   public void list(@ModelAttribute @ApiIgnore CityEntity city, HttpServletResponse response, HttpServletRequest request, ModelMap model) {
     BasicUtil.startPage();
     List cityList = this.cityBiz.query((BaseEntity)city);
     BasicUtil.endPage(cityList);
     outJson(response, JSONArray.toJSONStringWithDateFormat(cityList, "yyyy-MM-dd", new com.alibaba.fastjson.serializer.SerializerFeature[0]));
   }
































   @ApiOperation("获取省市县镇村数据")
   @ApiImplicitParam(name = "id", value = "城市主键编号", required = true, paramType = "query")
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














   @ApiOperation("更新省市县镇村数据信息省市县镇村数据")
   @GetMapping({"/query"})
   @ResponseBody
   public void query(HttpServletResponse response, HttpServletRequest request) {
     int level = BasicUtil.getInt("level", 3).intValue();
     String type = BasicUtil.getString("type", "tree");
     List<CityBean> cityList = this.cityBiz.queryForTree(level, type);
     outJson(response, JSONArray.toJSONString(cityList));
   }





   @ApiOperation("根据拼音首字母查询城市")
   @ApiImplicitParam(name = "cityPy", value = "城市拼音首字母", required = true, paramType = "query")
   @GetMapping({"/queryCityPy"})
   @ResponseBody
   public void queryPy(HttpServletResponse response, HttpServletRequest request) {
     String cityPy = BasicUtil.getString("cityPy");
     CityEntity city = new CityEntity();
     city.setCityPy(cityPy);
     List<CityEntity> cityList = this.cityBiz.query((BaseEntity)city);
     outJson(response, JSONArray.toJSONString(cityList));
   }
 }


/* Location:              D:\User\Maven\repository\net\mingsoft\ms-basic\1.0.16\ms-basic-1.0.16.jar!\net\mingsoft\basic\action\web\CityAction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */