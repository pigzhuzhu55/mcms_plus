 package net.mingsoft.mdiy.action;

 import com.alibaba.fastjson.serializer.SerializeFilter;
 import io.swagger.annotations.Api;
 import io.swagger.annotations.ApiImplicitParam;
 import io.swagger.annotations.ApiImplicitParams;
 import io.swagger.annotations.ApiOperation;
 import java.util.List;
 import java.util.Map;
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
 import net.mingsoft.mdiy.biz.IFormBiz;
 import net.mingsoft.mdiy.biz.IFormFieldBiz;
 import net.mingsoft.mdiy.entity.FormEntity;
 import net.mingsoft.mdiy.entity.FormFieldEntity;
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


























 @Api("自定义表单接口")
 @Controller
 @RequestMapping({"/${ms.manager.path}/mdiy/form"})
 public class FormAction
   extends BaseAction
 {
   private static final String TABLE_NAME_PREFIX = "mdiy_";
   private static final String TABLE_NAME_SPLIT = "_";
   @Autowired
   IFormBiz formBiz;
   @Autowired
   IFormFieldBiz formFieldBiz;

   @GetMapping({"/index"})
   public String index(HttpServletResponse response, HttpServletRequest request) { return "/mdiy/form/index"; }


































   @ApiOperation("查询自定义表单列表接口")
   @ApiImplicitParams({@ApiImplicitParam(name = "formTipsName", value = "自定义表单提示文字", required = false, paramType = "query"), @ApiImplicitParam(name = "formTableName", value = "自定义表单表名", required = false, paramType = "query"), @ApiImplicitParam(name = "dfManagerid", value = "自定义表单关联的关联员id", required = false, paramType = "query")})
   @GetMapping({"/list"})
   @ResponseBody
   public void list(@ModelAttribute @ApiIgnore FormEntity form, HttpServletResponse response, HttpServletRequest request, @ApiIgnore ModelMap model) {
     form.setAppId(BasicUtil.getAppId());
     BasicUtil.startPage();
     List formList = this.formBiz.query((BaseEntity)form);
     outJson(response, JSONArray.toJSONString(new EUListBean(formList, (int)BasicUtil.endPage(formList).getTotal()), new SerializeFilter[] { (SerializeFilter)new DoubleValueFilter(), (SerializeFilter)new DateValueFilter() }));
   }




   @GetMapping({"/form"})
   public String form(@ModelAttribute FormEntity form, HttpServletResponse response, HttpServletRequest request, @ApiIgnore ModelMap model) {
     if (form.getFormId() != null) {
       FormEntity formEntity = (FormEntity)this.formBiz.getEntity(form.getFormId().intValue());
       model.addAttribute("formEntity", formEntity);
     }

     return "/mdiy/form/form";
   }



























   @ApiOperation("获取自定义表单接口")
   @ApiImplicitParam(name = "formId", value = "自定义表单编号", required = true, paramType = "query")
   @GetMapping({"/get"})
   @ResponseBody
   public void get(@ModelAttribute @ApiIgnore FormEntity form, HttpServletResponse response, HttpServletRequest request, @ApiIgnore ModelMap model) {
     if (form.getFormId().intValue() <= 0) {
       outJson(response, null, false, getResString("err.error", new String[] { getResString("form.id") }));
       return;
     }
     FormEntity _form = (FormEntity)this.formBiz.getEntity(form.getFormId().intValue());
     outJson(response, (BaseEntity)_form);
   }












   @ApiOperation("批量删除自定义表单接口")
   @PostMapping({"/delete"})
   @ResponseBody
   public void delete(@RequestBody List<FormEntity> forms, HttpServletResponse response, HttpServletRequest request) {
     for (int i = 0; i < forms.size(); i++) {

       FormEntity form = (FormEntity)this.formBiz.getEntity(((FormEntity)forms.get(i)).getFormId().intValue());
       if (form == null) {
         outJson(response, null, false, getResString("err.not.exist", new String[] { getResString("diy.form") }));
         return;
       }
       this.formBiz.dropTable(form.getFormTableName());
       this.formBiz.deleteEntity(((FormEntity)forms.get(i)).getFormId().intValue());
     }
     outJson(response, true);
   }



































   @ApiOperation("保存自定义表单接口")
   @ApiImplicitParams({@ApiImplicitParam(name = "formTableName", value = "自定义表单表名", required = true, paramType = "query"), @ApiImplicitParam(name = "formTipsName", value = "自定义表单提示文字", required = false, paramType = "query"), @ApiImplicitParam(name = "dfManagerid", value = "自定义表单关联的关联员id", required = false, paramType = "query"), @ApiImplicitParam(name = "formAppId", value = "自定义表单关联的应用编号", required = false, paramType = "query"), @ApiImplicitParam(name = "formUrl", value = "表单的访问地址", required = false, paramType = "query")})
   @PostMapping({"/save"})
   @RequiresPermissions({"mdiy:form:save"})
   public void save(@ModelAttribute @ApiIgnore FormEntity form, HttpServletRequest request, HttpServletResponse response) {
     if (!StringUtil.checkLength(form.getFormTableName(), 1, 20)) {
       outJson(response, null, false, getResString("err.length", new String[] { getResString("fieldTipsName"), "1", "20" }));
       return;
     }
     if (!StringUtil.checkLength(form.getFormTipsName(), 1, 20)) {
       outJson(response, null, false, getResString("err.length", new String[] { getResString("fieldFieldName"), "1", "20" }));

       return;
     }

     ManagerEntity managerSession = (ManagerEntity)BasicUtil.getSession((BaseSessionEnum)SessionConstEnum.MANAGER_SESSION);

     int managerId = managerSession.getManagerId();

     String formTableName = "mdiy_" + form.getFormTableName() + "_" + managerId;
     FormEntity _form = new FormEntity();
     _form.setFormTableName(formTableName);

     if (this.formBiz.getEntity((BaseEntity)_form) != null) {
       outJson(response, null, false, getResString("err.exist", new String[] { getResString("diy.form.table.name") }));

       return;
     }
     int appId = getAppId(request);

     form.setAppId(BasicUtil.getAppId());

     String tableName = "mdiy_" + form.getFormTableName() + "_" + managerId;
     form.setFormTableName(tableName);


     this.formBiz.createDiyFormTable(form.getFormTableName(), null);

     this.formBiz.saveEntity((BaseEntity)form);
     _form.setAppId(BasicUtil.getAppId());
     _form = (FormEntity)this.formBiz.getEntity((BaseEntity)_form);
     int diyFormId = _form.getFormId().intValue();
     outJson(response, null, true, String.valueOf(diyFormId));
   }


































   @ApiOperation("更新自定义表单表信息接口")
   @ApiImplicitParams({@ApiImplicitParam(name = "formTableName", value = "自定义表单表名", required = true, paramType = "query"), @ApiImplicitParam(name = "formTipsName", value = "自定义表单提示文字", required = false, paramType = "query"), @ApiImplicitParam(name = "dfManagerid", value = "自定义表单关联的关联员id", required = false, paramType = "query"), @ApiImplicitParam(name = "updateBy", value = "最后更新用户编号", required = false, paramType = "query"), @ApiImplicitParam(name = "updateDate", value = "最后更新日期", required = false, paramType = "query")})
   @PostMapping({"/update"})
   @RequiresPermissions({"mdiy:form:update"})
   public void update(@ModelAttribute @ApiIgnore FormEntity form, HttpServletResponse response, HttpServletRequest request) {
     if (!StringUtil.checkLength(form.getFormTableName(), 1, 20)) {
       outJson(response, null, false, getResString("err.length", new String[] { getResString("fieldTipsName"), "1", "20" }));
       return;
     }
     if (!StringUtil.checkLength(form.getFormTipsName(), 1, 20)) {
       outJson(response, null, false, getResString("err.length", new String[] { getResString("fieldFieldName"), "1", "20" }));
       return;
     }
     FormEntity _form = new FormEntity();
     _form.setFormTableName(form.getFormTableName());
     _form.setAppId(BasicUtil.getAppId());
     _form = (FormEntity)this.formBiz.getEntity((BaseEntity)_form);

     int formId = _form.getFormId().intValue();
     form.setFormId(Integer.valueOf(formId));

     this.formBiz.updateEntity((BaseEntity)form);

     outJson(response, null, true, String.valueOf(formId));
   }











   @ApiOperation("验证自定义表名合法性接口")
   @ApiImplicitParams({@ApiImplicitParam(name = "formId", value = "自定义表单编号", required = true, paramType = "query"), @ApiImplicitParam(name = "formTableName", value = "自定义表单表名", required = true, paramType = "query")})
   @GetMapping({"/checkTableNameExist"})
   public void checkTableNameExist(@ModelAttribute @ApiIgnore FormEntity form, HttpServletRequest request, HttpServletResponse response) {
     ManagerEntity managerSession = (ManagerEntity)BasicUtil.getSession((BaseSessionEnum)SessionConstEnum.MANAGER_SESSION);

     int managerId = managerSession.getManagerId();

     String formTableName = "mdiy_" + form.getFormTableName() + "_" + managerId;
     FormEntity _form = new FormEntity();
     _form.setFormTableName(formTableName);
     _form = (FormEntity)this.formBiz.getEntity((BaseEntity)_form);
     if (_form == null) {
       outJson(response, null, false);
       return;
     }
     outJson(response, null, true);
   }








   @ApiOperation("加载自定义表单的数据列表页面接口")
   @ApiImplicitParam(name = "formId", value = "自定义表单编号", required = true, paramType = "query")
   @GetMapping({"/querydata"})
   public String query(@ModelAttribute @ApiIgnore FormEntity form, HttpServletRequest request, @ApiIgnore ModelMap model) {
     int pageNo = 1;

     if (request.getParameter("pageNo") != null) {
       pageNo = Integer.parseInt(request.getParameter("pageNo"));
     }

     int appId = BasicUtil.getAppId();
     int count = this.formBiz.countDiyFormData(form.getFormId().intValue(), appId);
     Map map = this.formBiz.queryDiyFormData(form.getFormId().intValue(), appId, null);
     if (map != null) {
       List<FormFieldEntity> fields = (List<FormFieldEntity>)map.get("fields");
       if (fields != null) {
         model.addAttribute("fields", fields);
       }
       if (map.get("list") != null) {
         model.addAttribute("list", map.get("list"));
       }
     }

     model.addAttribute("title", request.getParameter("title"));
     return "/mdiy/diy_form/diy_form_data_list";
   }











   @ApiOperation("根据id删除自定义表单接口")
   @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "记录编号", required = true, paramType = "path"), @ApiImplicitParam(name = "diyFormId", value = "表单编号", required = true, paramType = "path")})
   @PostMapping({"/{diyFormId}/{id}/delete"})
   @ResponseBody
   @RequiresPermissions({"mdiy:form:del"})
   public void delete(@ApiIgnore @PathVariable("id") int id, @ApiIgnore @PathVariable("diyFormId") int diyFormId, HttpServletRequest request, HttpServletResponse response) {
     this.formBiz.deleteQueryDiyFormData(id, diyFormId);
     outJson(response, null, true);
   }
 }


