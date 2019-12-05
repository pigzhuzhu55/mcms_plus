 package net.mingsoft.mdiy.action;

 import com.alibaba.fastjson.serializer.SerializeFilter;
 import io.swagger.annotations.Api;
 import io.swagger.annotations.ApiImplicitParam;
 import io.swagger.annotations.ApiImplicitParams;
 import io.swagger.annotations.ApiOperation;
 import java.util.ArrayList;
 import java.util.HashMap;
 import java.util.List;
 import java.util.Map;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;
 import net.mingsoft.base.constant.e.TableEnum;
 import net.mingsoft.base.entity.BaseEntity;
 import net.mingsoft.base.filter.DateValueFilter;
 import net.mingsoft.base.filter.DoubleValueFilter;
 import net.mingsoft.base.util.JSONArray;
 import net.mingsoft.basic.bean.EUListBean;
 import net.mingsoft.basic.biz.IColumnBiz;
 import net.mingsoft.basic.entity.ColumnEntity;
 import net.mingsoft.basic.util.BasicUtil;
 import net.mingsoft.basic.util.StringUtil;
 import net.mingsoft.mdiy.biz.IContentModelBiz;
 import net.mingsoft.mdiy.biz.IContentModelFieldBiz;
 import net.mingsoft.mdiy.constant.e.ContentModelFieldEnum;
 import net.mingsoft.mdiy.entity.ContentModelEntity;
 import net.mingsoft.mdiy.entity.ContentModelFieldEntity;
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













 @Api("自定义模型字段接口")
 @Controller
 @RequestMapping({"/${ms.manager.path}/mdiy/contentModel/contentModelField"})
 public class ContentModelFieldAction
   extends BaseAction
 {
   @Autowired
   private IContentModelFieldBiz contentModelFieldBiz;
   @Autowired
   private IContentModelBiz contentModelBiz;
   @Autowired
   private IColumnBiz columnBiz;

   @GetMapping({"/{contentModelId}/index"})
   public String index(@PathVariable int contentModelId, HttpServletRequest request, @ApiIgnore ModelMap model, HttpServletResponse response) {
     model.addAttribute("contentModelId", Integer.valueOf(contentModelId));
     model.put("fieldTypes", ContentModelFieldEnum.toMap());
     return "/mdiy/content_model/index_filed";
   }







   @ApiOperation("表单列表")
   @ApiImplicitParam(name = "contentModelId", value = "绑定内容模型表ID", required = true, paramType = "path")
   @GetMapping({"/{contentModelId}/list"})
   public void list(@PathVariable @ApiIgnore int contentModelId, HttpServletRequest request, @ApiIgnore ModelMap model, HttpServletResponse response) {
     BasicUtil.startPage();
     List contentModelFieldList = this.contentModelFieldBiz.queryListByCmid(contentModelId);

     model.put("fieldTypes", ContentModelFieldEnum.toMap());
     model.put("contentModelId", Integer.valueOf(contentModelId));
     model.addAttribute("contentModelFieldList", contentModelFieldList);
     outJson(response, JSONArray.toJSONString(new EUListBean(contentModelFieldList, (int)BasicUtil.endPage(contentModelFieldList).getTotal()), new SerializeFilter[] { (SerializeFilter)new DoubleValueFilter(), (SerializeFilter)new DateValueFilter() }));
   }















   @ApiOperation("保存内容模型接口")
   @ApiImplicitParams({@ApiImplicitParam(name = "fieldCmid", value = "绑定内容模型表ID", required = true, paramType = "query"), @ApiImplicitParam(name = "fieldTipsName", value = "字段提示文字", required = true, paramType = "query"), @ApiImplicitParam(name = "fieldFieldName", value = "字段名称", required = true, paramType = "query"), @ApiImplicitParam(name = "fieldType", value = "字段类型(1单行文本 2多行文本 3=HTML文本4=整数类型 5=小数类型6=时间类型7=图片8=附件类型9=使用option下拉框10=使用radio选项卡11=Checkbox多选框)", required = true, paramType = "query")})
   @PostMapping({"/save"})
   @ResponseBody
   public void save(@ModelAttribute @ApiIgnore ContentModelFieldEntity field, HttpServletRequest request, HttpServletResponse response) {
     ContentModelEntity contentModel = (ContentModelEntity)this.contentModelBiz.getEntity(field.getFieldCmid());

     if (!StringUtil.checkLength(field.getFieldTipsName(), 1, 30)) {
       outJson(response, null, false, getResString("err.length", new String[] { getResString("content.model.field.tips.name"), "1", "30" }));
       return;
     }
     if (!StringUtil.checkLength(field.getFieldFieldName(), 1, 30)) {
       outJson(response, null, false, getResString("err.length", new String[] { getResString("content.model.field.column.ame"), "1", "30" }));

       return;
     }
     if (this.contentModelFieldBiz.getEntityByCmId(field.getFieldCmid(), field.getFieldFieldName()) != null) {
       outJson(response, null, false, getResString("err.exist", new String[] { getResString("content.model.field") }));

       return;
     }
     this.contentModelFieldBiz.saveEntity((BaseEntity)field);


     Map<Object, Object> fileds = new HashMap<>();

     fileds.put("fieldName", field.getFieldFieldName());

     fileds.put("fieldType", field.getFieldColumnType());

     fileds.put("default", field.getFieldDefault());

     this.contentModelFieldBiz.alterTable(contentModel.getCmTableName(), fileds, TableEnum.ALTER_ADD);
     outJson(response, null, true, null);
   }











   @ApiOperation("删除表单类型接口")
   @PostMapping({"/delete"})
   @ResponseBody
   public void delete(@RequestBody List<ContentModelFieldEntity> contentModelFields, HttpServletRequest request, HttpServletResponse response) {
     for (int i = 0; i < contentModelFields.size(); i++) {

       ContentModelFieldEntity field = (ContentModelFieldEntity)this.contentModelFieldBiz.getEntity(((ContentModelFieldEntity)contentModelFields.get(i)).getFieldId());
       this.contentModelFieldBiz.deleteEntity(((ContentModelFieldEntity)contentModelFields.get(i)).getFieldId());

       ContentModelEntity contentModel = (ContentModelEntity)this.contentModelBiz.getEntity(field.getFieldCmid());
       if (contentModel != null) {
         Map<String, Object> fields = new HashMap<>();

         fields.put("fieldName", field.getFieldFieldName());

         this.contentModelFieldBiz.alterTable(contentModel.getCmTableName(), fields, TableEnum.ALTER_DROP);
       }
     }
     outJson(response, true);
   }









   @ApiOperation("编辑表单接口")
   @ApiImplicitParam(name = "filedId", value = "自增长ID", required = true, paramType = "path")
   @GetMapping({"/{filedId}/edit"})
   @ResponseBody
   public void edit(@PathVariable @ApiIgnore int filedId, HttpServletResponse response) {
     ContentModelFieldEntity contentModelField = (ContentModelFieldEntity)this.contentModelFieldBiz.getEntity(filedId);
     outJson(response, (BaseEntity)contentModelField);
   }





















   @ApiOperation("更新内容模型接口")
   @ApiImplicitParams({@ApiImplicitParam(name = "fieldCmid", value = "绑定内容模型表ID", required = true, paramType = "query"), @ApiImplicitParam(name = "fieldTipsName", value = "字段提示文字", required = false, paramType = "query"), @ApiImplicitParam(name = "fieldFieldName", value = "字段名称", required = false, paramType = "query"), @ApiImplicitParam(name = "fieldType", value = "字段类型(1单行文本 2多行文本 3=HTML文本4=整数类型 5=小数类型6=时间类型7=图片8=附件类型9=使用option下拉框10=使用radio选项卡11=Checkbox多选框)", required = false, paramType = "query"), @ApiImplicitParam(name = "fieldCmid", value = "字段名称", required = false, paramType = "query"), @ApiImplicitParam(name = "fieldLength", value = "字段名称", required = false, paramType = "query"), @ApiImplicitParam(name = "fieldDefault", value = "字段名称", required = false, paramType = "query"), @ApiImplicitParam(name = "fieldIsNull", value = "字段名称", required = false, paramType = "query"), @ApiImplicitParam(name = "fieldIsSearch", value = "字段名称", required = false, paramType = "query")})
   @PostMapping({"/update"})
   @ResponseBody
   public void update(@ModelAttribute @ApiIgnore ContentModelFieldEntity contentModelFieldEntity, HttpServletRequest request, HttpServletResponse response) {
     this.contentModelFieldBiz.updateEntity((BaseEntity)contentModelFieldEntity);
     outJson(response, true);
   }










   @ApiOperation("返回cms模块的自定义模型的html页面（适用于cms模块）")
   @ApiImplicitParam(name = "columnId", value = "栏目编号", required = true, paramType = "path")
   @GetMapping({"/{columnId}/queryField"})
   public String queryField(@PathVariable @ApiIgnore int columnId, @ApiIgnore ModelMap model, HttpServletRequest request) {
     ColumnEntity column = (ColumnEntity)this.columnBiz.getEntity(columnId);
     if (column != null) {

       int fieldCmid = column.getColumnContentModelId();

       List<ContentModelFieldEntity> listField = this.contentModelFieldBiz.queryListByCmid(fieldCmid);
       int basicId = BasicUtil.getInt("basicId").intValue();

       if (basicId != 0) {

         ContentModelEntity contentModel = (ContentModelEntity)this.contentModelBiz.getEntity(fieldCmid);

         if (contentModel != null) {

           List<String> listFieldName = new ArrayList<>();

           for (int i = 0; i < listField.size(); i++) {
             ContentModelFieldEntity field = listField.get(i);
             listFieldName.add(field.getFieldFieldName());
           }

           Map<String, Integer> where = new HashMap<>();
           where.put("basicId", Integer.valueOf(basicId));

           List<Map> fieldLists = this.contentModelFieldBiz.queryBySQL(contentModel.getCmTableName(), listFieldName, where);
           if (fieldLists != null && fieldLists.size() > 0) {
             Map filedValue = fieldLists.get(0);
             model.addAttribute("filedValue", filedValue);
           }
         }
       }
       model.addAttribute("listField", listField);
       model.addAttribute("appId", Integer.valueOf(BasicUtil.getAppId()));
     }
     return "/mdiy/content_model/content_model_field";
   }
 }


