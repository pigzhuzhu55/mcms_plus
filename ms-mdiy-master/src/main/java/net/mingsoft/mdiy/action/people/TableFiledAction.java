 package net.mingsoft.mdiy.action.people;

 import cn.hutool.core.util.ObjectUtil;
 import com.alibaba.fastjson.serializer.SerializeFilter;
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
 import net.mingsoft.base.filter.DateValueFilter;
 import net.mingsoft.base.filter.DoubleValueFilter;
 import net.mingsoft.base.util.JSONArray;
 import net.mingsoft.base.util.JSONObject;
 import net.mingsoft.basic.bean.EUListBean;
 import net.mingsoft.basic.util.BasicUtil;
 import net.mingsoft.basic.util.StringUtil;
 import net.mingsoft.mdiy.action.BaseAction;
 import net.mingsoft.mdiy.biz.ITableBiz;
 import net.mingsoft.mdiy.biz.ITableFiledBiz;
 import net.mingsoft.mdiy.constant.Const;
 import net.mingsoft.mdiy.constant.e.FieldEnum;
 import net.mingsoft.mdiy.entity.TableEntity;
 import net.mingsoft.mdiy.entity.TableFiledEntity;
 import org.apache.commons.lang3.StringUtils;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.stereotype.Controller;
 import org.springframework.ui.ModelMap;
 import org.springframework.web.bind.annotation.GetMapping;
 import org.springframework.web.bind.annotation.ModelAttribute;
 import org.springframework.web.bind.annotation.PostMapping;
 import org.springframework.web.bind.annotation.RequestMapping;
 import org.springframework.web.bind.annotation.ResponseBody;
 import springfox.documentation.annotations.ApiIgnore;





























































 @Api("自定义表对应字段管理接口")
 @Controller("peopleTableFiledAction")
 @RequestMapping({"/people/mdiy/tableFiled"})
 public class TableFiledAction
   extends BaseAction
 {
   @Autowired
   private ITableFiledBiz tableFiledBiz;
   @Autowired
   private ITableBiz tableBiz;

   @ApiOperation("查询自定义表对应字段列表")
   @ApiImplicitParams({@ApiImplicitParam(name = "tableId", value = "自定义表编号", required = false, paramType = "query"), @ApiImplicitParam(name = "tfName", value = "字段名称", required = false, paramType = "query"), @ApiImplicitParam(name = "tfType", value = "类型", required = false, paramType = "query"), @ApiImplicitParam(name = "tfDefault", value = "默认值", required = false, paramType = "query"), @ApiImplicitParam(name = "tfUnique", value = "描述", required = false, paramType = "query"), @ApiImplicitParam(name = "tfRequired", value = "0非必填1必填", required = false, paramType = "query"), @ApiImplicitParam(name = "tfConfig", value = "json配置", required = false, paramType = "query"), @ApiImplicitParam(name = "tfHelp", value = "帮助信息", required = false, paramType = "query")})
   @GetMapping({"/list"})
   @ResponseBody
   public void list(@ModelAttribute TableFiledEntity tableFiled, HttpServletResponse response, HttpServletRequest request, @ApiIgnore ModelMap model) {
     BasicUtil.startPage();
     List tableFiledList = this.tableFiledBiz.query((BaseEntity)tableFiled);
     outJson(response, JSONArray.toJSONString(new EUListBean(tableFiledList, (int)BasicUtil.endPage(tableFiledList).getTotal()), new SerializeFilter[] { (SerializeFilter)new DoubleValueFilter(), (SerializeFilter)new DateValueFilter() }));
   }








































   @ApiOperation("获取自定义表对应字段")
   @ApiImplicitParam(name = "id", value = "自定义表对应字段编号", required = true, paramType = "query")
   @GetMapping({"/get"})
   @ResponseBody
   public TableFiledEntity get(@ModelAttribute @ApiIgnore TableFiledEntity tableFiled, HttpServletResponse response, HttpServletRequest request, @ApiIgnore ModelMap model) {
     if (tableFiled.getId() == null) {
       return null;
     }
     TableFiledEntity _tableFiled = (TableFiledEntity)this.tableFiledBiz.getEntity(Integer.parseInt(tableFiled.getId()));
     return _tableFiled;
   }

















































   @ApiOperation("保存自定义表对应字段实体")
   @ApiImplicitParams({@ApiImplicitParam(name = "tableId", value = "自定义表编号", required = true, paramType = "query"), @ApiImplicitParam(name = "tfName", value = "字段名称", required = true, paramType = "query"), @ApiImplicitParam(name = "tfType", value = "类型", required = true, paramType = "query"), @ApiImplicitParam(name = "tfDefault", value = "默认值", required = true, paramType = "query"), @ApiImplicitParam(name = "tfUnique", value = "描述", required = true, paramType = "query"), @ApiImplicitParam(name = "tfRequired", value = "0非必填1必填", required = true, paramType = "query"), @ApiImplicitParam(name = "tfConfig", value = "json配置", required = true, paramType = "query"), @ApiImplicitParam(name = "tfHelp", value = "帮助信息", required = true, paramType = "query")})
   @PostMapping({"/save"})
   @ResponseBody
   public void save(@ModelAttribute @ApiIgnore TableFiledEntity tableFiled, HttpServletResponse response, HttpServletRequest request) {
     if (StringUtils.isBlank(tableFiled.getTfName())) {
       outJson(response, null, false, getResString("err.empty", new String[] { getResString("tf.name") }));
       return;
     }
     if (!StringUtil.checkLength(tableFiled.getTfName() + "", 1, 255)) {
       outJson(response, null, false, getResString("err.length", new String[] { getResString("tf.name"), "1", "255" }));

       return;
     }
     if (StringUtils.isBlank(tableFiled.getTfType())) {
       outJson(response, null, false, getResString("err.empty", new String[] { getResString("tf.type") }));
       return;
     }
     if (!StringUtil.checkLength(tableFiled.getTfType() + "", 1, 255)) {
       outJson(response, null, false, getResString("err.length", new String[] { getResString("tf.type"), "1", "255" }));

       return;
     }
     if (!StringUtil.checkLength(tableFiled.getTfDefault() + "", 0, 255)) {
       outJson(response, null, false, getResString("err.length", new String[] { getResString("tf.default"), "0", "255" }));
       return;
     }
     if (!StringUtil.checkLength(tableFiled.getTfDescription() + "", 0, 255)) {
       outJson(response, null, false, getResString("err.length", new String[] { getResString("tf.description"), "0", "255" }));
       return;
     }
     if (!StringUtil.checkLength(tableFiled.getTfConfig() + "", 0, 255)) {
       outJson(response, null, false, getResString("err.length", new String[] { getResString("tf.config"), "0", "255" }));
       return;
     }
     if (!StringUtil.checkLength(tableFiled.getTfHelp() + "", 0, 255)) {
       outJson(response, null, false, getResString("err.length", new String[] { getResString("tf.help"), "0", "255" }));
       return;
     }
     TableFiledEntity _tableFiled = new TableFiledEntity();
     _tableFiled.setTableId(tableFiled.getTableId());
     _tableFiled.setTfName(tableFiled.getTfName());
     List<TableFiledEntity> list = this.tableFiledBiz.query((BaseEntity)_tableFiled);

     if (list.size() > 0) {
       outJson(response, null, false, getResString("err.exist", new String[] { getResString("tf.name") }));
       return;
     }
     TableEntity table = (TableEntity)this.tableBiz.getEntity(tableFiled.getTableId().intValue());
     this.tableFiledBiz.saveEntity((BaseEntity)tableFiled);

     String fieldType = "varchar(500)";
     if (FieldEnum.DATE.toString().equals(tableFiled.getTfType())) {
       fieldType = "date";
       tableFiled.setTfDefault(null);
     }
     if (FieldEnum.INT.toString().equals(tableFiled.getTfType())) {
       fieldType = "int(11)";
     }

     Map<Object, Object> fileds = new HashMap<>();

     fileds.put("fieldName", Const.TABLE_FILED_PREFIX + tableFiled.getId());

     fileds.put("fieldType", fieldType);

     fileds.put("default", tableFiled.getTfDefault());

     this.tableFiledBiz.alterTable(Const.TABLE_PREFIX + table.getId(), fileds, TableEnum.ALTER_ADD);
     outJson(response, JSONObject.toJSONString(tableFiled));
   }












   @ApiOperation("批量删除自定义表对应字段")
   @ApiImplicitParam(name = "ids", value = "自定义表对应字段编号，多个用逗号隔开", required = true, paramType = "path")
   @PostMapping({"/del"})
   @ResponseBody
   public void del(HttpServletResponse response, HttpServletRequest request) {
     int[] ids = BasicUtil.getInts("ids", ",");
     if (ObjectUtil.isNull(ids)) {
       outJson(response, null, false, getResString("err.empty", new String[] { getResString("ids") }));
       return;
     }
     for (int i = 0; i < ids.length; i++) {

       TableFiledEntity tableField = (TableFiledEntity)this.tableFiledBiz.getEntity(ids[i]);
       if (ObjectUtil.isNotNull(tableField)) {
         Map<String, Object> fields = new HashMap<>();

         fields.put("fieldName", Const.TABLE_FILED_PREFIX + ids[i]);

         this.tableFiledBiz.alterTable(Const.TABLE_PREFIX + tableField.getTableId(), fields, TableEnum.ALTER_DROP);
       }
     }
     this.tableFiledBiz.delete(ids);
     outJson(response, true);
   }


















































   @ApiOperation("更新自定义表对应字段信息")
   @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "自定义表对应字段编号", required = true, paramType = "query"), @ApiImplicitParam(name = "tfName", value = "字段名称", required = true, paramType = "query"), @ApiImplicitParam(name = "tfType", value = "类型", required = true, paramType = "query"), @ApiImplicitParam(name = "tfDefault", value = "默认值", required = true, paramType = "query"), @ApiImplicitParam(name = "tfUnique", value = "描述", required = true, paramType = "query"), @ApiImplicitParam(name = "tfRequired", value = "0非必填1必填", required = true, paramType = "query"), @ApiImplicitParam(name = "tfConfig", value = "json配置", required = true, paramType = "query"), @ApiImplicitParam(name = "tfHelp", value = "帮助信息", required = true, paramType = "query")})
   @PostMapping({"/update"})
   @ResponseBody
   public void update(@ModelAttribute @ApiIgnore TableFiledEntity tableFiled, HttpServletResponse response, HttpServletRequest request) {
     if (StringUtils.isBlank(tableFiled.getTfName())) {
       outJson(response, null, false, getResString("err.empty", new String[] { getResString("tf.name") }));
       return;
     }
     if (!StringUtil.checkLength(tableFiled.getTfName() + "", 1, 255)) {
       outJson(response, null, false, getResString("err.length", new String[] { getResString("tf.name"), "1", "255" }));

       return;
     }
     if (!StringUtil.checkLength(tableFiled.getTfDefault() + "", 0, 255)) {
       outJson(response, null, false, getResString("err.length", new String[] { getResString("tf.default"), "0", "255" }));
       return;
     }
     if (!StringUtil.checkLength(tableFiled.getTfDescription() + "", 0, 255)) {
       outJson(response, null, false, getResString("err.length", new String[] { getResString("tf.description"), "0", "255" }));
       return;
     }
     if (!StringUtil.checkLength(tableFiled.getTfConfig() + "", 0, 255)) {
       outJson(response, null, false, getResString("err.length", new String[] { getResString("tf.config"), "0", "255" }));
       return;
     }
     if (!StringUtil.checkLength(tableFiled.getTfHelp() + "", 0, 255)) {
       outJson(response, null, false, getResString("err.length", new String[] { getResString("tf.help"), "0", "255" }));

       return;
     }
     tableFiled.setTfType(null);
     this.tableFiledBiz.updateEntity((BaseEntity)tableFiled);
     outJson(response, JSONObject.toJSONString(tableFiled));
   }
 }


