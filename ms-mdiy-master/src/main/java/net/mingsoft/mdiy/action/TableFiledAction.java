 package net.mingsoft.mdiy.action;

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
 import net.mingsoft.basic.util.BasicUtil;
 import net.mingsoft.basic.util.StringUtil;
 import net.mingsoft.mdiy.biz.ITableFiledBiz;
 import net.mingsoft.mdiy.entity.TableFiledEntity;
 import org.apache.commons.lang3.StringUtils;
 import org.apache.shiro.authz.annotation.RequiresPermissions;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.stereotype.Controller;
 import org.springframework.ui.ModelMap;
 import org.springframework.validation.BindingResult;
 import org.springframework.web.bind.annotation.GetMapping;
 import org.springframework.web.bind.annotation.ModelAttribute;
 import org.springframework.web.bind.annotation.PostMapping;
 import org.springframework.web.bind.annotation.RequestBody;
 import org.springframework.web.bind.annotation.RequestMapping;
 import org.springframework.web.bind.annotation.ResponseBody;
 import springfox.documentation.annotations.ApiIgnore;













 @Api("自定义表对应字段接口")
 @Controller
 @RequestMapping({"/${ms.manager.path}/mdiy/tableFiled"})
 public class TableFiledAction
   extends BaseAction
 {
   @Autowired
   private ITableFiledBiz tableFiledBiz;

   @GetMapping({"/index"})
   public String index(HttpServletResponse response, HttpServletRequest request, @ApiIgnore ModelMap model) {
     model.addAttribute("tableId", BasicUtil.getInt("id"));
     return "/mdiy/table_filed/index";
   }
















































   @ApiOperation("查询自定义表对应字段列表")
   @ApiImplicitParams({@ApiImplicitParam(name = "tableId", value = "自定义表编号", required = false, paramType = "query"), @ApiImplicitParam(name = "tfName", value = "字段名称", required = false, paramType = "query"), @ApiImplicitParam(name = "tfType", value = "类型", required = false, paramType = "query"), @ApiImplicitParam(name = "tfDefault", value = "默认值", required = false, paramType = "query"), @ApiImplicitParam(name = "tfRequired", value = "0非必填1必填", required = false, paramType = "query"), @ApiImplicitParam(name = "tfConfig", value = "json配置", required = false, paramType = "query")})
   @GetMapping({"/list"})
   @ResponseBody
   public void list(@ModelAttribute @ApiIgnore TableFiledEntity tableFiled, HttpServletResponse response, HttpServletRequest request, @ApiIgnore ModelMap model, BindingResult result) {
     BasicUtil.startPage();
     List tableFiledList = this.tableFiledBiz.query((BaseEntity)tableFiled);
     outJson(response, JSONArray.toJSONString(new EUListBean(tableFiledList, (int)BasicUtil.endPage(tableFiledList).getTotal()), new SerializeFilter[] { (SerializeFilter)new DoubleValueFilter(), (SerializeFilter)new DateValueFilter() }));
   }




   @GetMapping({"/form"})
   public String form(@ModelAttribute TableFiledEntity tableFiled, HttpServletResponse response, HttpServletRequest request, @ApiIgnore ModelMap model) {
     if (tableFiled.getId() != null) {
       BaseEntity tableFiledEntity = this.tableFiledBiz.getEntity(Integer.parseInt(tableFiled.getId()));
       model.addAttribute("tableFiledEntity", tableFiledEntity);
     }
     return "/mdiy/table_filed/form";
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

















































   @ApiOperation("保存自定义表对应字段接口")
   @ApiImplicitParams({@ApiImplicitParam(name = "tableId", value = "自定义表编号", required = true, paramType = "query"), @ApiImplicitParam(name = "tfName", value = "字段名称", required = true, paramType = "query"), @ApiImplicitParam(name = "tfType", value = "类型", required = true, paramType = "query"), @ApiImplicitParam(name = "tfDefault", value = "默认值", required = true, paramType = "query"), @ApiImplicitParam(name = "tfUnique", value = "描述", required = true, paramType = "query"), @ApiImplicitParam(name = "tfRequired", value = "0非必填1必填", required = true, paramType = "query"), @ApiImplicitParam(name = "tfConfig", value = "json配置", required = true, paramType = "query"), @ApiImplicitParam(name = "tfHelp", value = "帮助信息", required = true, paramType = "query")})
   @PostMapping({"/save"})
   @ResponseBody
   @RequiresPermissions({"mdiy:tableFiled:save"})
   public void save(@ModelAttribute @ApiIgnore TableFiledEntity tableFiled, HttpServletResponse response, HttpServletRequest request, BindingResult result) {
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
     if (StringUtils.isBlank(tableFiled.getTfDefault())) {
       outJson(response, null, false, getResString("err.empty", new String[] { getResString("tf.default") }));
       return;
     }
     if (!StringUtil.checkLength(tableFiled.getTfDefault() + "", 1, 255)) {
       outJson(response, null, false, getResString("err.length", new String[] { getResString("tf.default"), "1", "255" }));

       return;
     }
     if (StringUtils.isBlank(tableFiled.getTfDescription())) {
       outJson(response, null, false, getResString("err.empty", new String[] { getResString("tf.description") }));
       return;
     }
     if (!StringUtil.checkLength(tableFiled.getTfDescription() + "", 1, 255)) {
       outJson(response, null, false, getResString("err.length", new String[] { getResString("tf.description"), "1", "255" }));

       return;
     }
     if (StringUtils.isBlank(tableFiled.getTfConfig())) {
       outJson(response, null, false, getResString("err.empty", new String[] { getResString("tf.config") }));
       return;
     }
     if (!StringUtil.checkLength(tableFiled.getTfConfig() + "", 1, 255)) {
       outJson(response, null, false, getResString("err.length", new String[] { getResString("tf.config"), "1", "255" }));

       return;
     }
     if (StringUtils.isBlank(tableFiled.getTfHelp())) {
       outJson(response, null, false, getResString("err.empty", new String[] { getResString("tf.help") }));
       return;
     }
     if (!StringUtil.checkLength(tableFiled.getTfHelp() + "", 1, 255)) {
       outJson(response, null, false, getResString("err.length", new String[] { getResString("tf.help"), "1", "255" }));
       return;
     }
     this.tableFiledBiz.saveEntity((BaseEntity)tableFiled);
     outJson(response, JSONObject.toJSONString(tableFiled));
   }












   @ApiOperation("批量删除自定义表对应字段")
   @PostMapping({"/delete"})
   @ResponseBody
   @RequiresPermissions({"mdiy:tableFiled:del"})
   public void delete(@RequestBody List<TableFiledEntity> tableFileds, HttpServletResponse response, HttpServletRequest request) {
     int[] ids = new int[tableFileds.size()];
     for (int i = 0; i < tableFileds.size(); i++) {
       ids[i] = Integer.parseInt(((TableFiledEntity)tableFileds.get(i)).getId());
     }
     this.tableFiledBiz.delete(ids);
     outJson(response, true);
   }


















































   @ApiOperation("更新自定义表对应字段信息")
   @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "自定义表对应字段编号", required = true, paramType = "query"), @ApiImplicitParam(name = "tfName", value = "字段名称", required = true, paramType = "query"), @ApiImplicitParam(name = "tfType", value = "类型", required = true, paramType = "query"), @ApiImplicitParam(name = "tfDefault", value = "默认值", required = true, paramType = "query"), @ApiImplicitParam(name = "tfUnique", value = "描述", required = true, paramType = "query"), @ApiImplicitParam(name = "tfRequired", value = "0非必填1必填", required = true, paramType = "query"), @ApiImplicitParam(name = "tfConfig", value = "json配置", required = true, paramType = "query"), @ApiImplicitParam(name = "tfHelp", value = "帮助信息", required = true, paramType = "query")})
   @PostMapping({"/update"})
   @ResponseBody
   @RequiresPermissions({"mdiy:tableFiled:update"})
   public void update(@ModelAttribute @ApiIgnore TableFiledEntity tableFiled, HttpServletResponse response, HttpServletRequest request) {
     if (StringUtils.isBlank(tableFiled.getId())) {
       outJson(response, null, false, getResString("err.empty", new String[] { getResString("id") }));
       return;
     }
     if (!StringUtil.checkLength(tableFiled.getId() + "", 1, 11)) {
       outJson(response, null, false, getResString("err.length", new String[] { getResString("id"), "1", "11" }));

       return;
     }
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
     if (StringUtils.isBlank(tableFiled.getTfDefault())) {
       outJson(response, null, false, getResString("err.empty", new String[] { getResString("tf.default") }));
       return;
     }
     if (!StringUtil.checkLength(tableFiled.getTfDefault() + "", 1, 255)) {
       outJson(response, null, false, getResString("err.length", new String[] { getResString("tf.default"), "1", "255" }));

       return;
     }
     if (StringUtils.isBlank(tableFiled.getTfDescription())) {
       outJson(response, null, false, getResString("err.empty", new String[] { getResString("tf.description") }));
       return;
     }
     if (!StringUtil.checkLength(tableFiled.getTfDescription() + "", 1, 255)) {
       outJson(response, null, false, getResString("err.length", new String[] { getResString("tf.description"), "1", "255" }));

       return;
     }
     if (StringUtils.isBlank(tableFiled.getTfConfig())) {
       outJson(response, null, false, getResString("err.empty", new String[] { getResString("tf.config") }));
       return;
     }
     if (!StringUtil.checkLength(tableFiled.getTfConfig() + "", 1, 255)) {
       outJson(response, null, false, getResString("err.length", new String[] { getResString("tf.config"), "1", "255" }));

       return;
     }
     if (StringUtils.isBlank(tableFiled.getTfHelp())) {
       outJson(response, null, false, getResString("err.empty", new String[] { getResString("tf.help") }));
       return;
     }
     if (!StringUtil.checkLength(tableFiled.getTfHelp() + "", 1, 255)) {
       outJson(response, null, false, getResString("err.length", new String[] { getResString("tf.help"), "1", "255" }));
       return;
     }
     this.tableFiledBiz.updateEntity((BaseEntity)tableFiled);
     outJson(response, JSONObject.toJSONString(tableFiled));
   }
 }


