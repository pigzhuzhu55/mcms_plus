 package net.mingsoft.mdiy.action;

 import com.alibaba.fastjson.JSONObject;
 import com.alibaba.fastjson.serializer.SerializeFilter;
 import io.swagger.annotations.Api;
 import io.swagger.annotations.ApiImplicitParam;
 import io.swagger.annotations.ApiImplicitParams;
 import io.swagger.annotations.ApiOperation;
 import java.util.List;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;
 import net.mingsoft.base.constant.e.BaseSessionEnum;
 import net.mingsoft.base.entity.BaseEntity;
 import net.mingsoft.base.filter.DateValueFilter;
 import net.mingsoft.base.filter.DoubleValueFilter;
 import net.mingsoft.base.util.JSONArray;
 import net.mingsoft.basic.bean.EUListBean;
 import net.mingsoft.basic.constant.e.SessionConstEnum;
 import net.mingsoft.basic.entity.ManagerEntity;
 import net.mingsoft.basic.util.BasicUtil;
 import net.mingsoft.basic.util.StringUtil;
 import net.mingsoft.mdiy.biz.IContentModelBiz;
 import net.mingsoft.mdiy.biz.IContentModelFieldBiz;
 import net.mingsoft.mdiy.biz.IFormBiz;
 import net.mingsoft.mdiy.entity.ContentModelEntity;
 import net.mingsoft.mdiy.entity.FormEntity;
 import org.apache.shiro.authz.annotation.RequiresPermissions;
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


























 @Api("自定义模型接口")
 @Controller
 @RequestMapping({"/${ms.manager.path}/mdiy/contentModel/"})
 public class ContentModelAction
   extends BaseAction
 {
   private static final String TABLE_NAME_PREFIX = "mdiy_";
   private static final String TABLE_NAME_SPLIT = "_";
   @Autowired
   private IContentModelBiz contentModelBiz;
   @Autowired
   IFormBiz formBiz;
   @Autowired
   private IContentModelFieldBiz fieldBiz;

   @GetMapping({"/index"})
   public String index(HttpServletResponse response, HttpServletRequest request) { return "/mdiy/content_model/index"; }




   @GetMapping({"/form"})
   public String form(@ModelAttribute ContentModelEntity contentModel, HttpServletResponse response, HttpServletRequest request, @ApiIgnore ModelMap model) {
     if (contentModel.getCmId() > 0) {
       BaseEntity contentModelEntity = this.contentModelBiz.getEntity(contentModel.getCmId());
       model.addAttribute("contentModelEntity", contentModelEntity);
     }

     return "/mdiy/content_model/form";
   }
























   @ApiOperation("查询自定义模型列表接口")
   @ApiImplicitParams({@ApiImplicitParam(name = "cmTipsName", value = "表名提示文字", required = false, paramType = "query"), @ApiImplicitParam(name = "cmTableName", value = "表单名称", required = false, paramType = "query"), @ApiImplicitParam(name = "cmModelId", value = "自定义模型模块编号", required = false, paramType = "query")})
   @GetMapping({"/list"})
   @ResponseBody
   public void list(@ModelAttribute @ApiIgnore ContentModelEntity contentModel, HttpServletResponse response, HttpServletRequest request, @ApiIgnore ModelMap model) {
     contentModel.setAppId(BasicUtil.getAppId());
     BasicUtil.startPage();
     List contentModelList = this.contentModelBiz.query((BaseEntity)contentModel);
     outJson(response, JSONArray.toJSONString(new EUListBean(contentModelList, (int)BasicUtil.endPage(contentModelList).getTotal()), new SerializeFilter[] { (SerializeFilter)new DoubleValueFilter(), (SerializeFilter)new DateValueFilter() }));
   }


















   @ApiOperation("获取自定义模型接口")
   @ApiImplicitParam(name = "cmId", value = "自定义模型编号", required = true, paramType = "query")
   @GetMapping({"/get"})
   @ResponseBody
   public void get(@ModelAttribute @ApiIgnore ContentModelEntity contentModel, HttpServletResponse response, HttpServletRequest request, @ApiIgnore ModelMap model) {
     if (contentModel.getCmId() <= 0) {
       outJson(response, null, false, getResString("err.error", new String[] { getResString("cm.id") }));
       return;
     }
     ContentModelEntity _contentModel = (ContentModelEntity)this.contentModelBiz.getEntity(contentModel.getCmId());
     outJson(response, JSONObject.toJSON(_contentModel));
   }











   @ApiOperation("批量删除自定义模型编号")
   @PostMapping({"/delete"})
   @ResponseBody
   @RequiresPermissions({"mdiy:content:del"})
   public void delete(@RequestBody List<ContentModelEntity> contentModels, HttpServletResponse response, HttpServletRequest request) {
     for (int i = 0; i < contentModels.size(); i++) {
       if (contentModels.size() > 0 &&
         contentModels.get(i) != null) {
         ContentModelEntity cme = (ContentModelEntity)this.contentModelBiz.getEntity(((ContentModelEntity)contentModels.get(i)).getCmId());
         if (cme != null) {
           this.contentModelBiz.dropTable(cme.getCmTableName());
         }
         this.contentModelBiz.deleteEntity(((ContentModelEntity)contentModels.get(i)).getCmId());
       }
     }

     outJson(response, true);
   }








   @ApiOperation("保存内容模型接口")
   @ApiImplicitParams({@ApiImplicitParam(name = "cmTipsName", value = "表名提示文字", required = true, paramType = "query"), @ApiImplicitParam(name = "cmTableName", value = "表单名称", required = true, paramType = "query")})
   @PostMapping({"/save"})
   @ResponseBody
   @RequiresPermissions({"mdiy:content:save"})
   public void save(@ModelAttribute @ApiIgnore ContentModelEntity contentModel, HttpServletRequest request, HttpServletResponse response) {
     contentModel.setAppId(BasicUtil.getAppId());

     if (!StringUtil.checkLength(contentModel.getCmTipsName(), 1, 30)) {
       outJson(response, null, false, getResString("err.length", new String[] { getResString("content.model.tips.name"), "1", "30" }));
       return;
     }
     if (!StringUtil.checkLength(contentModel.getCmTableName(), 1, 10)) {
       outJson(response, null, false, getResString("err.length", new String[] { getResString("content.model.table.name"), "1", "10" }));

       return;
     }

     ManagerEntity managerSession = (ManagerEntity)BasicUtil.getSession((BaseSessionEnum)SessionConstEnum.MANAGER_SESSION);

     int managerId = managerSession.getManagerId();
     ContentModelEntity contentModelEntity = new ContentModelEntity();
     contentModelEntity.setCmTableName("mdiy_" + contentModel.getCmTableName() + "_" + managerId);
     if (this.contentModelBiz.getEntity((BaseEntity)contentModelEntity) != null) {
       outJson(response, null, false, getResString("err.exist", new String[] { getResString("content.model") }));

       return;
     }
     contentModel.setCmTableName("mdiy_" + contentModel.getCmTableName() + "_" + managerId);

     this.contentModelBiz.createTable(contentModel.getCmTableName(), null);
     this.contentModelBiz.saveEntity((BaseEntity)contentModel);

     int cmId = ((ContentModelEntity)this.contentModelBiz.getEntity((BaseEntity)contentModelEntity)).getCmId();

     outJson(response, null, true, String.valueOf(cmId));
   }











   @ApiOperation("更新内容模型接口")
   @ApiImplicitParams({@ApiImplicitParam(name = "cmId", value = "自定义模型编号", required = true, paramType = "query"), @ApiImplicitParam(name = "cmTipsName", value = "表名提示文字", required = true, paramType = "query")})
   @PostMapping({"/update"})
   @ResponseBody
   @RequiresPermissions({"mdiy:content:update"})
   public void update(@ModelAttribute @ApiIgnore ContentModelEntity contentModel, HttpServletRequest request, HttpServletResponse response) {
     if (!StringUtil.checkLength(contentModel.getCmTipsName(), 1, 30)) {
       outJson(response, null, false, getResString("err.length", new String[] { getResString("content.model.tips.name"), "1", "30" }));
       return;
     }
     this.contentModelBiz.updateEntity((BaseEntity)contentModel);
     outJson(response, null, true, null);
   }








   @ApiOperation(" 判断自定义模型表名是否重复接口")
   @ApiImplicitParam(name = "cmTableName", value = "表单名称", required = true, paramType = "path")
   @GetMapping({"/{cmTableName}/checkcmTableNameExist"})
   @ResponseBody
   public boolean checkcmTableNameExist(@PathVariable @ApiIgnore String cmTableName, HttpServletRequest request) {
     ManagerEntity managerSession = (ManagerEntity)BasicUtil.getSession((BaseSessionEnum)SessionConstEnum.MANAGER_SESSION);

     int managerId = managerSession.getManagerId();
     cmTableName = "mdiy_" + cmTableName + "_" + managerId;

     ContentModelEntity contentModel = new ContentModelEntity();
     contentModel.setCmTableName(cmTableName);

     FormEntity formEntity = new FormEntity();
     formEntity.setFormTableName(cmTableName);
     if (this.contentModelBiz.getEntity((BaseEntity)contentModel) != null || this.formBiz.getEntity((BaseEntity)formEntity) != null) {
       return true;
     }
     return false;
   }
 }


