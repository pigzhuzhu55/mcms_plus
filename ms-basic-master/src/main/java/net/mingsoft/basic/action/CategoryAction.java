 package net.mingsoft.basic.action;

 import com.alibaba.fastjson.serializer.SerializeFilter;
 import io.swagger.annotations.Api;
 import io.swagger.annotations.ApiImplicitParam;
 import io.swagger.annotations.ApiImplicitParams;
 import io.swagger.annotations.ApiOperation;
 import java.sql.Timestamp;
 import java.util.List;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;
 import net.mingsoft.base.constant.e.BaseSessionEnum;
 import net.mingsoft.base.entity.BaseEntity;
 import net.mingsoft.base.filter.DateValueFilter;
 import net.mingsoft.base.filter.DoubleValueFilter;
 import net.mingsoft.base.util.JSONArray;
 import net.mingsoft.base.util.JSONObject;
 import net.mingsoft.basic.bean.EUListBean;
 import net.mingsoft.basic.biz.ICategoryBiz;
 import net.mingsoft.basic.constant.e.SessionConstEnum;
 import net.mingsoft.basic.entity.AppEntity;
 import net.mingsoft.basic.entity.CategoryEntity;
 import net.mingsoft.basic.entity.ManagerSessionEntity;
 import net.mingsoft.basic.util.BasicUtil;
 import net.mingsoft.basic.util.StringUtil;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.stereotype.Controller;
 import org.springframework.ui.ModelMap;
 import org.springframework.web.bind.annotation.GetMapping;
 import org.springframework.web.bind.annotation.ModelAttribute;
 import org.springframework.web.bind.annotation.PathVariable;
 import org.springframework.web.bind.annotation.PostMapping;
 import org.springframework.web.bind.annotation.RequestBody;
 import org.springframework.web.bind.annotation.RequestMapping;
 import org.springframework.web.bind.annotation.ResponseBody;
 import springfox.documentation.annotations.ApiIgnore;




















 @Api("分类表管理")
 @Controller
 @RequestMapping({"/${ms.manager.path}/category"})
 public class CategoryAction
   extends BaseAction
 {
   @Autowired
   private ICategoryBiz categoryBiz;

   @ApiOperation("返回主界面index")
   @ApiImplicitParams({@ApiImplicitParam(name = "modelTitle", value = "模块标题", required = true, paramType = "query"), @ApiImplicitParam(name = "modelId", value = "模块编号", required = true, paramType = "query")})
   @GetMapping({"/index"})
   public String index(HttpServletResponse response, HttpServletRequest request, ModelMap model) {
     String modelTitle = BasicUtil.getString("modelTitle");
     int modelId = BasicUtil.getInt("modelId").intValue();
     if (modelId == 0) {
       String modelCode = BasicUtil.getString("modelCode");
       modelId = BasicUtil.getModelCodeId(modelCode);
     }
     model.addAttribute("modelTitle", modelTitle);
     model.addAttribute("modelId", Integer.valueOf(modelId));
     return "/basic/category/index";
   }




























































   @ApiOperation("查询分类表列表")
   @ApiImplicitParams({@ApiImplicitParam(name = "categoryTitle", value = "类别的标题", required = true, paramType = "query"), @ApiImplicitParam(name = "categorySort", value = "类别的排序", required = false, paramType = "query"), @ApiImplicitParam(name = "categoryDateTime", value = "类别发布时间", required = false, paramType = "query"), @ApiImplicitParam(name = "categoryManagerId", value = "发布用户编号(发布者编号)", required = false, paramType = "query"), @ApiImplicitParam(name = "categoryModelId", value = "所属模块编号", required = false, paramType = "query"), @ApiImplicitParam(name = "categoryCategoryId", value = "父类别的编号", required = false, paramType = "query"), @ApiImplicitParam(name = "categorySmallImg", value = "缩略图", required = false, paramType = "query"), @ApiImplicitParam(name = "categoryAppId", value = "分类所属应用编号", required = false, paramType = "query"), @ApiImplicitParam(name = "categoryDescription", value = "栏目描述", required = false, paramType = "query"), @ApiImplicitParam(name = "categoryParentId", value = "父类型编号", required = false, paramType = "query"), @ApiImplicitParam(name = "categoryDictId", value = "字典对应编号", required = false, paramType = "query"), @ApiImplicitParam(name = "createBy", value = "创建用户编号", required = false, paramType = "query"), @ApiImplicitParam(name = "createDate", value = "创建日期", required = false, paramType = "query"), @ApiImplicitParam(name = "modelId", value = "模块编号", required = true, paramType = "path")})
   @GetMapping({"/list"})
   @ResponseBody
   public void list(@ModelAttribute @ApiIgnore CategoryEntity category, HttpServletResponse response, HttpServletRequest request, @ApiIgnore ModelMap model) {
     String modelId = request.getParameter("modelId");
     BasicUtil.setSession((BaseSessionEnum)SessionConstEnum.MANAGER_MODEL_CODE, modelId);

     ManagerSessionEntity managerSession = getManagerBySession(request);

     category.setCategoryModelId(Integer.parseInt(modelId));

     AppEntity app = getApp(request);

     category.setCategoryAppId(app.getAppId());

     if (managerSession.getManagerId() != app.getAppManagerId()) {
       category.setCategoryManagerId(managerSession.getManagerId());
     }
     BasicUtil.startPage();
     List categoryList = this.categoryBiz.query((BaseEntity)category);
     outJson(response, JSONArray.toJSONString(new EUListBean(categoryList, (int)BasicUtil.endPage(categoryList).getTotal()), new SerializeFilter[] { (SerializeFilter)new DoubleValueFilter(), (SerializeFilter)new DateValueFilter("yyyy-MM-dd HH:mm:ss") }));
   }








   @ApiOperation("返回编辑界面form")
   @ApiImplicitParams({@ApiImplicitParam(name = "categoryId", value = "类别的编号自增长id", required = true, paramType = "query"), @ApiImplicitParam(name = "modelTitle", value = "模块标题", required = true, paramType = "query"), @ApiImplicitParam(name = "modelId", value = "模块编号", required = true, paramType = "query")})
   @GetMapping({"/form"})
   public String form(@ModelAttribute @ApiIgnore CategoryEntity category, HttpServletResponse response, HttpServletRequest request, @ApiIgnore ModelMap model) {
     if (category.getCategoryId() > 0) {
       BaseEntity categoryEntity = this.categoryBiz.getEntity(category.getCategoryId());
       model.addAttribute("categoryEntity", categoryEntity);
     }
     request.setAttribute("modelTitle", BasicUtil.getString("modelTitle"));
     request.setAttribute("modelId", BasicUtil.getInt("modelId"));
     return "/basic/category/form";
   }











































   @ApiOperation("获取分类表")
   @ApiImplicitParam(name = "categoryId", value = "类别的编号自增长id", required = true, paramType = "query")
   @GetMapping({"/get"})
   @ResponseBody
   public void get(@ModelAttribute @ApiIgnore CategoryEntity category, HttpServletResponse response, HttpServletRequest request, @ApiIgnore ModelMap model) {
     if (category.getCategoryId() <= 0) {
       outJson(response, null, false, getResString("err.error", new String[] { getResString("category.id") }));
       return;
     }
     CategoryEntity _category = (CategoryEntity)this.categoryBiz.getEntity(category.getCategoryId());
     outJson(response, (BaseEntity)_category);
   }













































   @ApiOperation("保存分类表实体")
   @ApiImplicitParams({@ApiImplicitParam(name = "categoryTitle", value = "类别的标题", required = true, paramType = "query"), @ApiImplicitParam(name = "categorySort", value = "类别的排序", required = false, paramType = "query"), @ApiImplicitParam(name = "categoryDateTime", value = "类别发布时间", required = false, paramType = "query"), @ApiImplicitParam(name = "categoryManagerId", value = "发布用户编号(发布者编号)", required = false, paramType = "query"), @ApiImplicitParam(name = "categoryModelId", value = "所属模块编号", required = false, paramType = "query"), @ApiImplicitParam(name = "categoryCategoryId", value = "父类别的编号", required = false, paramType = "query"), @ApiImplicitParam(name = "categorySmallImg", value = "缩略图", required = false, paramType = "query"), @ApiImplicitParam(name = "categoryAppId", value = "分类所属应用编号", required = false, paramType = "query"), @ApiImplicitParam(name = "categoryDescription", value = "栏目描述", required = true, paramType = "query"), @ApiImplicitParam(name = "categoryParentId", value = "父类型编号", required = false, paramType = "query"), @ApiImplicitParam(name = "categoryDictId", value = "字典对应编号", required = false, paramType = "query"), @ApiImplicitParam(name = "createBy", value = "创建用户编号", required = false, paramType = "query"), @ApiImplicitParam(name = "createDate", value = "创建日期", required = false, paramType = "query")})
   @PostMapping({"/save"})
   @ResponseBody
   public void save(@ModelAttribute @ApiIgnore CategoryEntity category, HttpServletResponse response, HttpServletRequest request) {
     if (StringUtil.isBlank(category.getCategoryTitle())) {
       outJson(response, null, false, getResString("err.empty", new String[] { getResString("category.title") }));
       return;
     }
     if (!StringUtil.checkLength(category.getCategoryTitle() + "", 1, 50)) {
       outJson(response, null, false, getResString("err.length", new String[] { getResString("category.title"), "1", "50" }));

       return;
     }
     if (!StringUtil.checkLength(category.getCategoryDescription() + "", 0, 45)) {
       outJson(response, null, false, getResString("err.length", new String[] { getResString("category.description"), "1", "45" }));
       return;
     }
     category.setCategoryManagerId(getManagerBySession(request).getManagerId());
     category.setCategoryDateTime(new Timestamp(System.currentTimeMillis()));
     category.setCategoryAppId(BasicUtil.getAppId());
     this.categoryBiz.saveEntity((BaseEntity)category);
     outJson(response, JSONObject.toJSONString(category));
   }












   @ApiOperation("批量删除分类表")
   @PostMapping({"/delete"})
   @ResponseBody
   public void delete(@RequestBody List<CategoryEntity> categorys, HttpServletResponse response, HttpServletRequest request) {
     int[] ids = new int[categorys.size()];
     for (int i = 0; i < categorys.size(); i++) {
       ids[i] = ((CategoryEntity)categorys.get(i)).getCategoryId();
     }
     this.categoryBiz.delete(ids);
     outJson(response, true);
   }














































   @ApiOperation("更新分类表信息分类表")
   @ApiImplicitParams({@ApiImplicitParam(name = "categoryId", value = "类别的编号", required = true, paramType = "query"), @ApiImplicitParam(name = "categoryTitle", value = "类别的标题", required = true, paramType = "query"), @ApiImplicitParam(name = "categorySort", value = "类别的排序", required = false, paramType = "query"), @ApiImplicitParam(name = "categoryDateTime", value = "类别发布时间", required = false, paramType = "query"), @ApiImplicitParam(name = "categoryManagerId", value = "发布用户编号(发布者编号)", required = false, paramType = "query"), @ApiImplicitParam(name = "categoryModelId", value = "所属模块编号", required = false, paramType = "query"), @ApiImplicitParam(name = "categoryCategoryId", value = "父类别的编号", required = false, paramType = "query"), @ApiImplicitParam(name = "categorySmallImg", value = "缩略图", required = false, paramType = "query"), @ApiImplicitParam(name = "categoryAppId", value = "分类所属应用编号", required = false, paramType = "query"), @ApiImplicitParam(name = "categoryDescription", value = "栏目描述", required = true, paramType = "query"), @ApiImplicitParam(name = "categoryParentId", value = "父类型编号", required = false, paramType = "query"), @ApiImplicitParam(name = "categoryDictId", value = "字典对应编号", required = false, paramType = "query"), @ApiImplicitParam(name = "createBy", value = "创建用户编号", required = false, paramType = "query"), @ApiImplicitParam(name = "createDate", value = "创建日期", required = false, paramType = "query")})
   @PostMapping({"/update"})
   @ResponseBody
   public void update(@ModelAttribute @ApiIgnore CategoryEntity category, HttpServletResponse response, HttpServletRequest request) {
     if (StringUtil.isBlank(category.getCategoryTitle())) {
       outJson(response, null, false, getResString("err.empty", new String[] { getResString("category.title") }));
       return;
     }
     if (!StringUtil.checkLength(category.getCategoryTitle() + "", 1, 50)) {
       outJson(response, null, false, getResString("err.length", new String[] { getResString("category.title"), "1", "50" }));

       return;
     }
     if (!StringUtil.checkLength(category.getCategoryDescription() + "", 0, 45)) {
       outJson(response, null, false, getResString("err.length", new String[] { getResString("category.description"), "1", "45" }));
       return;
     }
     category.setCategoryManagerId(getManagerBySession(request).getManagerId());
     this.categoryBiz.updateEntity((BaseEntity)category);
     outJson(response, JSONObject.toJSONString(category));
   }
















































   @ApiOperation("根据分类id查找分类子分类")
   @ApiImplicitParam(name = "categoryId", value = "分类id", required = true, paramType = "path")
   @GetMapping({"/{categoryId}/queryChildren"})
   public void queryChildren(@PathVariable @ApiIgnore int categoryId, HttpServletRequest request, @ApiIgnore ModelMap mode, HttpServletResponse response) {
     CategoryEntity category = (CategoryEntity)this.categoryBiz.getEntity(categoryId);
     if (category != null) {
       List<CategoryEntity> list = this.categoryBiz.queryChilds(category);
       outJson(response, JSONObject.toJSONString(list));
     }
   }
 }


/* Location:              D:\User\Maven\repository\net\mingsoft\ms-basic\1.0.16\ms-basic-1.0.16.jar!\net\mingsoft\basic\action\CategoryAction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */