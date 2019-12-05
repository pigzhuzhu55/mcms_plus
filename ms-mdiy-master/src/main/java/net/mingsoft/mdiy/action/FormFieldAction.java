 package net.mingsoft.mdiy.action;

 import io.swagger.annotations.Api;
 import io.swagger.annotations.ApiImplicitParam;
 import io.swagger.annotations.ApiImplicitParams;
 import io.swagger.annotations.ApiOperation;
 import java.util.HashMap;
 import java.util.List;
 import java.util.Map;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;
 import net.mingsoft.base.constant.e.TableEnum;
 import net.mingsoft.base.entity.BaseEntity;
 import net.mingsoft.basic.util.StringUtil;
 import net.mingsoft.mdiy.biz.IFormBiz;
 import net.mingsoft.mdiy.biz.IFormFieldBiz;
 import net.mingsoft.mdiy.constant.e.DiyFormFieldEnum;
 import net.mingsoft.mdiy.entity.FormEntity;
 import net.mingsoft.mdiy.entity.FormFieldEntity;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.stereotype.Controller;
 import org.springframework.web.bind.annotation.GetMapping;
 import org.springframework.web.bind.annotation.ModelAttribute;
 import org.springframework.web.bind.annotation.PathVariable;
 import org.springframework.web.bind.annotation.PostMapping;
 import org.springframework.web.bind.annotation.RequestMapping;
 import org.springframework.web.bind.annotation.ResponseBody;
 import springfox.documentation.annotations.ApiIgnore;
































 @Api("自定义表单字段接口")
 @Controller("diyFormField")
 @RequestMapping({"/${ms.manager.path}/mdiy/form/formField"})
 public class FormFieldAction
   extends BaseAction
 {
   private static final String FIELD_ID = "id";
   private static final String FIELD_DATE = "date";
   private static final String FIELD_FORMID = "formId";
   @Autowired
   private IFormFieldBiz diyFormFieldBiz;
   @Autowired
   private IFormBiz diyFormBiz;

   @ApiOperation("查询字段的列表接口")
   @ApiImplicitParam(name = "diyFormId", value = "自定义表单编号", required = true, paramType = "query")
   @GetMapping({"/list"})
   @ResponseBody
   public Map list(@ApiIgnore int diyFormId, HttpServletRequest request, HttpServletResponse response) {
     Map<Object, Object> map = new HashMap<>();

     List<FormFieldEntity> fieldList = this.diyFormFieldBiz.queryByDiyFormId(diyFormId);
     map.put("fieldList", fieldList);

     Map<Integer, String> fieldType = DiyFormFieldEnum.toMap();
     map.put("fieldType", fieldType);
     map.put("fieldNum", Integer.valueOf(fieldType.size()));
     return map;
   }






















   @ApiOperation("添加自定义字段")
   @ApiImplicitParams({@ApiImplicitParam(name = "diyFormId", value = "自定义表单编号", required = true, paramType = "path"), @ApiImplicitParam(name = "diyFormFieldTipsName", value = "字段提示文字", required = true, paramType = "query"), @ApiImplicitParam(name = "diyFormFieldFieldName", value = "字段名称", required = true, paramType = "query"), @ApiImplicitParam(name = "diyFormFieldFormId", value = "对应的自定义from的id", required = true, paramType = "query"), @ApiImplicitParam(name = "diyFormFieldType", value = "字段类型1字符2日期3文本4整型5小数", required = true, paramType = "query"), @ApiImplicitParam(name = "diyFormFieldDefault", value = "字段的默认值0，null", required = true, paramType = "query"), @ApiImplicitParam(name = "diyFormFieldSort", value = "排序", required = false, paramType = "query"), @ApiImplicitParam(name = "diyFormFieldIsNull", value = "判断字段为必填还是可选", required = false, paramType = "query")})
   @PostMapping({"/{diyFormId}/save"})
   @ResponseBody
   public void save(@ModelAttribute @ApiIgnore FormFieldEntity diyFormfield, @ApiIgnore @PathVariable int diyFormId, HttpServletResponse response) {
     FormEntity diyForm = (FormEntity)this.diyFormBiz.getEntity(diyFormId);
     if (diyForm == null) {
       outJson(response, null, false, getResString("err.not.exist", new String[] { getResString("diy.form") }));

       return;
     }
     if (!StringUtil.checkLength(diyFormfield.getDiyFormFieldTipsName(), 1, 20)) {
       outJson(response, null, false,
           getResString("err.length", new String[] { getResString("diy.form.tips.name"), "1", "20" }));
       return;
     }
     if (!StringUtil.checkLength(diyFormfield.getDiyFormFieldFieldName(), 1, 20)) {
       outJson(response, null, false,
           getResString("err.length", new String[] { getResString("diy.form.table.column.name"), "1", "20" }));

       return;
     }
     if (this.diyFormFieldBiz.getByFieldName(Integer.valueOf(diyFormfield.getDiyFormFieldFormId()), diyFormfield
         .getDiyFormFieldFieldName()) != null) {
       outJson(response, null, false,
           getResString("err.exist", new String[] { getResString("diy.form.table.column.name") }));


       return;
     }

     Map<String, String> fileds = new HashMap<>();

     fileds.put("fieldName", diyFormfield.getDiyFormFieldFieldName());

     fileds.put("fieldType", diyFormfield.getDiyFormFieldColumnType());
     fileds.put("default", diyFormfield.getDiyFormFieldDefault());

     this.diyFormFieldBiz.alterTable(diyForm.getFormTableName(), fileds, TableEnum.ALTER_ADD);
     this.diyFormFieldBiz.saveEntity((BaseEntity)diyFormfield);
     outJson(response, null, true, null);
   }










   @ApiOperation("获取编辑的字段实体的信息接口")
   @ApiImplicitParam(name = "diyFormFieldId", value = "自定义表单字段编号", required = true, paramType = "path")
   @GetMapping({"/{diyFormFieldId}/edit"})
   @ResponseBody
   public Map edit(@PathVariable @ApiIgnore int diyFormFieldId, HttpServletRequest request) {
     Map<Object, Object> mode = new HashMap<>();
     FormFieldEntity diyFormfield = (FormFieldEntity)this.diyFormFieldBiz.getEntity(diyFormFieldId);
     mode.put("diyFormfield", diyFormfield);
     return mode;
   }



















   @ApiOperation("更新字段信息接口")
   @ApiImplicitParams({@ApiImplicitParam(name = "diyFormId", value = "自定义表单编号", required = true, paramType = "query"), @ApiImplicitParam(name = "diyFormFieldTipsName", value = "字段提示文字", required = true, paramType = "query"), @ApiImplicitParam(name = "diyFormFieldFieldName", value = "字段名称", required = true, paramType = "query"), @ApiImplicitParam(name = "diyFormFieldFormId", value = "对应的自定义from的id", required = true, paramType = "query"), @ApiImplicitParam(name = "diyFormFieldType", value = "字段类型1字符2日期3文本4整型5小数", required = true, paramType = "query"), @ApiImplicitParam(name = "diyFormFieldDefault", value = "字段的默认值0，null", required = true, paramType = "query"), @ApiImplicitParam(name = "diyFormFieldSort", value = "排序", required = false, paramType = "query"), @ApiImplicitParam(name = "diyFormFieldIsNull", value = "判断字段为必填还是可选", required = false, paramType = "query")})
   @PostMapping({"/update"})
   @ResponseBody
   public void update(@ModelAttribute @ApiIgnore FormFieldEntity diyFormfield, HttpServletResponse response) {
     if (!StringUtil.checkLength(diyFormfield.getDiyFormFieldTipsName(), 1, 20)) {
       outJson(response, null, false,
           getResString("err.length", new String[] { getResString("fieldTipsName"), "1", "20" }));
       return;
     }
     if (!StringUtil.checkLength(diyFormfield.getDiyFormFieldFieldName(), 1, 20)) {
       outJson(response, null, false,
           getResString("err.length", new String[] { getResString("fieldFieldName"), "1", "20" }));

       return;
     }

     FormEntity diyForm = (FormEntity)this.diyFormBiz.getEntity(diyFormfield.getDiyFormFieldFormId());

     FormFieldEntity oldField = (FormFieldEntity)this.diyFormFieldBiz.getEntity(diyFormfield.getDiyFormFieldId());
     Map<Object, Object> fields = new HashMap<>();

     fields.put("fieldOldName", oldField.getDiyFormFieldFieldName());

     fields.put("fieldName", diyFormfield.getDiyFormFieldFieldName());

     fields.put("fieldType", diyFormfield.getDiyFormFieldColumnType());

     fields.put("default", diyFormfield.getDiyFormFieldDefault());
     if (diyForm == null) {
       outJson(response, null, false, getResString("err.not.exist"));

       return;
     }
     this.diyFormFieldBiz.alterTable(diyForm.getFormTableName(), fields, "modify");
     this.diyFormFieldBiz.updateEntity((BaseEntity)diyFormfield);
     outJson(response, null, true, null);
   }










   @ApiOperation("判断字段名是否存在重复接口")
   @ApiImplicitParam(name = "diyFormFieldFieldName", value = "字段名", required = true, paramType = "path")
   @GetMapping({"/{diyFormFieldFieldName}/checkFieldNameExist"})
   @ResponseBody
   public boolean checkFieldNameExist(@PathVariable @ApiIgnore String diyFormFieldFieldName, HttpServletRequest request) {
     int diyFormFieldFormId = 1;
     if (request.getParameter("diyFormFieldFormId") != null) {
       diyFormFieldFormId = Integer.parseInt(request.getParameter("diyFormFieldFormId"));
     }

     if (diyFormFieldFieldName.equalsIgnoreCase("id") || diyFormFieldFieldName.equalsIgnoreCase("date") || diyFormFieldFieldName
       .equalsIgnoreCase("formId") || this.diyFormFieldBiz
       .getByFieldName(Integer.valueOf(diyFormFieldFormId), diyFormFieldFieldName) != null) {
       return true;
     }
     return false;
   }














   @ApiOperation("删除自定义字段接口")
   @ApiImplicitParam(name = "fieldId", value = "表单编号", required = true, paramType = "path")
   @PostMapping({"/{fieldId}/delete"})
   public void delete(@PathVariable @ApiIgnore int fieldId, HttpServletRequest request, HttpServletResponse response) {
     FormFieldEntity diyFormField = (FormFieldEntity)this.diyFormFieldBiz.getEntity(fieldId);
     if (diyFormField == null) {
       outJson(response, null, false,
           getResString("err.not.exist", new String[] { getResString("diy.form.field") }));
       return;
     }
     FormEntity diyForm = (FormEntity)this.diyFormBiz.getEntity(diyFormField.getDiyFormFieldFormId());
     if (diyForm == null) {
       outJson(response, null, false, getResString("err.not.exist", new String[] { getResString("diy.form") }));
       return;
     }
     Map<Object, Object> fields = new HashMap<>();

     fields.put("fieldName", diyFormField.getDiyFormFieldFieldName());

     this.diyFormFieldBiz.alterTable(diyForm.getFormTableName(), fields, "drop");
     this.diyFormFieldBiz.deleteEntity(diyFormField.getDiyFormFieldId());
     outJson(response, null, true);
   }
 }


