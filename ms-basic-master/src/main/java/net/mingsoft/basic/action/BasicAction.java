 package net.mingsoft.basic.action;

 import com.alibaba.fastjson.JSONObject;
 import io.swagger.annotations.Api;
 import io.swagger.annotations.ApiImplicitParam;
 import io.swagger.annotations.ApiImplicitParams;
 import io.swagger.annotations.ApiOperation;
 import java.util.List;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;
 import net.mingsoft.base.entity.BaseEntity;
 import net.mingsoft.basic.biz.IBasicBiz;
 import net.mingsoft.basic.biz.ICategoryBiz;
 import net.mingsoft.basic.entity.BasicEntity;
 import net.mingsoft.basic.entity.CategoryEntity;
 import net.mingsoft.basic.entity.ModelEntity;
 import net.mingsoft.basic.util.StringUtil;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.stereotype.Controller;
 import org.springframework.ui.ModelMap;
 import org.springframework.web.bind.annotation.GetMapping;
 import org.springframework.web.bind.annotation.ModelAttribute;
 import org.springframework.web.bind.annotation.PathVariable;
 import org.springframework.web.bind.annotation.PostMapping;
 import org.springframework.web.bind.annotation.RequestMapping;
 import org.springframework.web.bind.annotation.ResponseBody;
 import springfox.documentation.annotations.ApiIgnore;




















































 @Api("基础应用层")
 @Controller
 @RequestMapping({"/${ms.manager.path}/basic"})
 public class BasicAction
   extends BaseAction
 {
   @Autowired
   private IBasicBiz basicBiz;
   @Autowired
   private ICategoryBiz categoryBiz;

   @ApiOperation("加载页面显示所有文章信息")
   @ApiImplicitParam(name = "modelCode", value = "模块编码", required = true, paramType = "query")
   @GetMapping({"/index"})
   public String index(HttpServletRequest request, @ApiIgnore ModelMap mode, HttpServletResponse response) {
     int appId = getManagerBySession(request).getBasicId();
     ModelEntity model = getCategoryModelCode(request);
     if (model == null) {
       outString(response, getResString("err"));
       return null;
     }
     List<CategoryEntity> list = this.categoryBiz.queryByAppIdOrModelId(Integer.valueOf(appId), Integer.valueOf(model.getModelId()));
     JSONObject ja = new JSONObject();
     request.setAttribute("listCategory", JSONObject.toJSON(list).toString());

     return "/basic/index";
   }










   @ApiOperation("加载文章列表页面，显示列表信息")
   @ApiImplicitParams({@ApiImplicitParam(name = "categoryId", value = "栏目id", required = true, paramType = "path"), @ApiImplicitParam(name = "pageNo", value = "页数", required = false, paramType = "query")})
   @GetMapping({"/{categoryId}/list"})
   public String list(HttpServletRequest request, @PathVariable @ApiIgnore int categoryId) {
     String keyWord = request.getParameter("keyword");
     String categoryTitle = request.getParameter("categoryTitle");

     int pageNo = 1;

     if (request.getParameter("pageNo") != null) {
       pageNo = Integer.parseInt(request.getParameter("pageNo"));
     }
     int count = 0;
     request.setAttribute("basicList", null);
     request.setAttribute("categoryId", Integer.valueOf(categoryId));
     return "/basic/basic_list";
   }







   @ApiOperation("加载添加文章页面")
   @ApiImplicitParam(name = "categoryId", value = "栏目id", required = true, paramType = "query")
   @GetMapping({"/add"})
   public String add(HttpServletRequest request, HttpServletResponse response) {
     String categoryId = request.getParameter("categoryId");
     request.setAttribute("categoryId", categoryId);
     return "/basic/basic";
   }





















   @ApiOperation("保存文章实体")
   @ApiImplicitParams({@ApiImplicitParam(name = "basicModelId", value = "模块编号", required = true, paramType = "query"), @ApiImplicitParam(name = "basicAppId", value = "文章所属应用", required = true, paramType = "query"), @ApiImplicitParam(name = "basicTitle", value = "标题 长度:200", required = false, paramType = "query"), @ApiImplicitParam(name = "basicDescription", value = "描述 长度:1500", required = false, paramType = "query"), @ApiImplicitParam(name = "basicThumbnails", value = "缩略图 长度:200", required = false, paramType = "query"), @ApiImplicitParam(name = "basicHit", value = "点击次数", required = false, paramType = "query"), @ApiImplicitParam(name = "basicCategoryId", value = "所属分类编号", required = false, paramType = "query"), @ApiImplicitParam(name = "basicPeopleId", value = "用户编号", required = false, paramType = "query"), @ApiImplicitParam(name = "basicDateTime", value = "发布时间", required = false, paramType = "query"), @ApiImplicitParam(name = "basicUpdateTime", value = "更新时间", required = false, paramType = "query"), @ApiImplicitParam(name = "basicSort", value = "排序", required = false, paramType = "query"), @ApiImplicitParam(name = "basicType", value = "通用属性", required = false, paramType = "query"), @ApiImplicitParam(name = "basicDisplay", value = "显示属性", required = false, paramType = "query")})
   @PostMapping({"/save"})
   public void save(@ModelAttribute @ApiIgnore BasicEntity basic, HttpServletRequest request, HttpServletResponse response) {
     basic.setBasicAppId(getAppId(request));
     basic.setBasicModelId(getModelCodeId(request));
     this.basicBiz.saveEntity((BaseEntity)basic);
     outJson(response, null, true);
   }







   @ApiOperation("加载编辑文档页面")
   @ApiImplicitParam(name = "basicId", value = "文章id", required = true, paramType = "path")
   @GetMapping({"/{basicId}/edit"})
   public String edit(@PathVariable @ApiIgnore int basicId, HttpServletRequest request) {
     BasicEntity basic = this.basicBiz.getBasic(basicId);
     request.setAttribute("basic", basic);
     return "/basic/basic";
   }






















   @ApiOperation("修改文章实体")
   @ApiImplicitParams({@ApiImplicitParam(name = "basicId", value = "文章编号", required = true, paramType = "query"), @ApiImplicitParam(name = "basicTitle", value = "标题 长度:200", required = false, paramType = "query"), @ApiImplicitParam(name = "basicDescription", value = "描述 长度:1500", required = false, paramType = "query"), @ApiImplicitParam(name = "basicThumbnails", value = "缩略图 长度:200", required = false, paramType = "query"), @ApiImplicitParam(name = "basicHit", value = "点击次数", required = false, paramType = "query"), @ApiImplicitParam(name = "basicCategoryId", value = "所属分类编号", required = false, paramType = "query"), @ApiImplicitParam(name = "basicPeopleId", value = "用户编号", required = false, paramType = "query"), @ApiImplicitParam(name = "basicDateTime", value = "发布时间", required = false, paramType = "query"), @ApiImplicitParam(name = "basicUpdateTime", value = "更新时间", required = false, paramType = "query"), @ApiImplicitParam(name = "basicAppId", value = "文章所属应用", required = false, paramType = "query"), @ApiImplicitParam(name = "basicSort", value = "排序", required = false, paramType = "query"), @ApiImplicitParam(name = "basicType", value = "通用属性", required = false, paramType = "query"), @ApiImplicitParam(name = "basicModelId", value = "模块编号", required = false, paramType = "query"), @ApiImplicitParam(name = "basicDisplay", value = "显示属性", required = false, paramType = "query")})
   @PostMapping({"/update"})
   @ResponseBody
   public void update(@ModelAttribute @ApiIgnore BasicEntity basic, HttpServletRequest request, HttpServletResponse response) {
     this.basicBiz.updateBasic(basic);
     outJson(response, null, true);
   }







   @ApiOperation("根据文章id删除文章实体")
   @ApiImplicitParam(name = "basicId", value = "文章id", required = true, paramType = "path")
   @PostMapping({"/{basicId}/delete"})
   @ResponseBody
   public void delete(@PathVariable @ApiIgnore int basicId, HttpServletResponse response, HttpServletRequest request) {
     this.basicBiz.deleteBasic(basicId);
     outJson(response, null, true);
   }






   @ApiOperation("文章多选删除方法")
   @ApiImplicitParam(name = "basicIds", value = "文章id,多个用逗号隔开", required = true, paramType = "path")
   @PostMapping({"/allDelete"})
   @ResponseBody
   public void allDelete(HttpServletResponse response, HttpServletRequest request) {
     String basicIds = request.getParameter("basicIds");
     if (!StringUtil.isBlank(basicIds)) {
       for (int i = 0; i < (basicIds.split(",")).length; i++) {
         int basicId = Integer.parseInt(basicIds.split(",")[i]);

         this.basicBiz.deleteBasic(basicId);
       }
     }
     outJson(response, null, true);
   }









   @ApiOperation("获取列表提供给ajax使用")
   @GetMapping({"/listForAjax"})
   public void listForAjax(HttpServletResponse response, HttpServletRequest request) {}









   @ApiOperation("获取所有json数据")
   @ApiImplicitParam(name = "categoryId", value = "栏目id", required = true, paramType = "path")
   @GetMapping({"/{categoryId}/query"})
   public void query(@PathVariable @ApiIgnore Integer categoryId, HttpServletRequest request, @ApiIgnore ModelMap mode, HttpServletResponse response) {
     List<BasicEntity> list = this.basicBiz.query(categoryId.intValue());
     String jsonStr = JSONObject.toJSONString(list);
     this.LOG.debug(jsonStr);
     outJson(response, jsonStr);
   }
 }


/* Location:              D:\User\Maven\repository\net\mingsoft\ms-basic\1.0.16\ms-basic-1.0.16.jar!\net\mingsoft\basic\action\BasicAction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */