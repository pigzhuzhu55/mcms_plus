 package net.mingsoft.basic.action.web;

 import cn.hutool.core.util.ObjectUtil;
 import com.alibaba.fastjson.JSONObject;
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
 import net.mingsoft.basic.action.BaseAction;
 import net.mingsoft.basic.bean.EUListBean;
 import net.mingsoft.basic.biz.ICategoryBiz;
 import net.mingsoft.basic.entity.CategoryEntity;
 import net.mingsoft.basic.util.BasicUtil;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.stereotype.Controller;
 import org.springframework.ui.ModelMap;
 import org.springframework.web.bind.annotation.GetMapping;
 import org.springframework.web.bind.annotation.ModelAttribute;
 import org.springframework.web.bind.annotation.PathVariable;
 import org.springframework.web.bind.annotation.RequestMapping;
 import org.springframework.web.bind.annotation.ResponseBody;
 import springfox.documentation.annotations.ApiIgnore;











































 @Api("供前端查询分类信息使用接口")
 @Controller("webCategory")
 @RequestMapping({"/category"})
 public class CategoryAction
   extends BaseAction
 {
   @Autowired
   private ICategoryBiz categoryBiz;

   @ApiOperation("根据分类id查找其父分类,如果父分类不存在则返回该分类")
   @ApiImplicitParam(name = "categoryId", value = "分类编号", required = true, paramType = "path")
   @GetMapping({"/{categoryId}/getParentCategory"})
   @ResponseBody
   public void getParentCategory(@PathVariable @ApiIgnore int categoryId, HttpServletRequest request, HttpServletResponse response) {
     CategoryEntity category = (CategoryEntity)this.categoryBiz.getEntity(categoryId);
     if (ObjectUtil.isNotNull(category)) {
       CategoryEntity paCategory = (CategoryEntity)this.categoryBiz.getEntity(category.getCategoryCategoryId());
       if (ObjectUtil.isNull(paCategory)) {
         outJson(response, JSONObject.toJSONString(category));
       } else {
         outJson(response, JSONObject.toJSONString(paCategory));
       }
     }
   }








   @ApiOperation("根据指定分类id查询其子分类")
   @ApiImplicitParam(name = "categoryId", value = "分类编号", required = true, paramType = "path")
   @GetMapping({"/{categoryId}/queryChildren"})
   public void queryChildren(@PathVariable @ApiIgnore int categoryId, HttpServletRequest request, HttpServletResponse response) {
     CategoryEntity category = (CategoryEntity)this.categoryBiz.getEntity(categoryId);
     if (category != null) {
       List<CategoryEntity> list = this.categoryBiz.queryChilds(category);
       outJson(response, JSONObject.toJSONStringWithDateFormat(list, "yyyy-MM-dd HH:mm:ss", new com.alibaba.fastjson.serializer.SerializerFeature[0]));
     }
   }










































   @ApiOperation("根据指定分类id查询其子分类")
   @ApiImplicitParams({@ApiImplicitParam(name = "categoryModelId", value = "所属模块编号", required = true, paramType = "query"), @ApiImplicitParam(name = "categoryDateTime", value = "类别发布时间", required = false, paramType = "query"), @ApiImplicitParam(name = "categoryManagerId", value = "发布用户编号(发布者编号)", required = false, paramType = "query"), @ApiImplicitParam(name = "categoryCategoryId", value = "父类别的编号", required = false, paramType = "query"), @ApiImplicitParam(name = "categoryDictId", value = "字典对应编号", required = false, paramType = "query"), @ApiImplicitParam(name = "categoryTitle", value = "类别的标题", required = false, paramType = "query")})
   @GetMapping({"/list"})
   @ResponseBody
   public void list(@ModelAttribute @ApiIgnore CategoryEntity category, HttpServletResponse response, HttpServletRequest request, ModelMap model) {
     if (category.getCategoryModelId() <= 0) {
       outJson(response, null, false, getResString("err.empty", new String[] { getResString("category.modelid") }));

       return;
     }
     category.setCategoryAppId(BasicUtil.getAppId());
     BasicUtil.startPage();
     List categoryList = this.categoryBiz.query((BaseEntity)category);
     outJson(response, JSONArray.toJSONString(new EUListBean(categoryList, (int)BasicUtil.endPage(categoryList).getTotal()), new SerializeFilter[] { (SerializeFilter)new DoubleValueFilter(), (SerializeFilter)new DateValueFilter() }));
   }
 }


