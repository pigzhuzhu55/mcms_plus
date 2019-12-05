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
 import net.mingsoft.mdiy.biz.ITableBiz;
 import net.mingsoft.mdiy.entity.TableEntity;
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














 @Api("自定义表管理接口")
 @Controller
 @RequestMapping({"/${ms.manager.path}/mdiy/table"})
 public class TableAction
   extends BaseAction
 {
   @Autowired
   private ITableBiz tableBiz;

   @GetMapping({"/index"})
   public String index(HttpServletResponse response, HttpServletRequest request) { return "/mdiy/table/index"; }




































   @ApiOperation("查询自定义表列表接口")
   @ApiImplicitParams({@ApiImplicitParam(name = "tableName", value = "自定义表名", required = false, paramType = "query"), @ApiImplicitParam(name = "tableMaster", value = "主表或主业务关键字", required = false, paramType = "query"), @ApiImplicitParam(name = "tableMasterId", value = "数据编号，主要关联主表编号", required = false, paramType = "query")})
   @GetMapping({"/list"})
   @ResponseBody
   public void list(@ModelAttribute @ApiIgnore TableEntity table, HttpServletResponse response, HttpServletRequest request, @ApiIgnore ModelMap model, BindingResult result) {
     BasicUtil.startPage();
     List tableList = this.tableBiz.query((BaseEntity)table);
     outJson(response, JSONArray.toJSONString(new EUListBean(tableList, (int)BasicUtil.endPage(tableList).getTotal()), new SerializeFilter[] { (SerializeFilter)new DoubleValueFilter(), (SerializeFilter)new DateValueFilter() }));
   }




   @GetMapping({"/form"})
   public String form(@ModelAttribute TableEntity table, HttpServletResponse response, HttpServletRequest request, @ApiIgnore ModelMap model) {
     if (table.getId() != null) {
       BaseEntity tableEntity = this.tableBiz.getEntity(Integer.parseInt(table.getId()));
       model.addAttribute("tableEntity", tableEntity);
     }
     return "/mdiy/table/form";
   }





























   @ApiOperation(" 获取自定义表详情")
   @ApiImplicitParam(name = "id", value = "自定义表编号", required = true, paramType = "query")
   @GetMapping({"/get"})
   @ResponseBody
   public TableEntity get(@ModelAttribute @ApiIgnore TableEntity table, HttpServletResponse response, HttpServletRequest request, @ApiIgnore ModelMap model) {
     if (table.getId() == null) {
       return null;
     }
     TableEntity _table = (TableEntity)this.tableBiz.getEntity(Integer.parseInt(table.getId()));
     return _table;
   }



































   @ApiOperation("保存自定义表")
   @ApiImplicitParams({@ApiImplicitParam(name = "appId", value = "站点编号", required = true, paramType = "query"), @ApiImplicitParam(name = "tableName", value = "自定义表名", required = true, paramType = "query"), @ApiImplicitParam(name = "tableMaster", value = "主表或主业务关键字", required = true, paramType = "query"), @ApiImplicitParam(name = "tableMasterId", value = "数据编号，主要关联主表编号", required = true, paramType = "query")})
   @PostMapping({"/save"})
   @ResponseBody
   @RequiresPermissions({"mdiy:table:save"})
   public void save(@ModelAttribute @ApiIgnore TableEntity table, HttpServletResponse response, HttpServletRequest request, BindingResult result) {
     if (StringUtils.isBlank(table.getAppId() + "")) {
       outJson(response, null, false, getResString("err.empty", new String[] { getResString("app.id") }));
       return;
     }
     if (!StringUtil.checkLength(table.getAppId() + "", 1, 11)) {
       outJson(response, null, false, getResString("err.length", new String[] { getResString("app.id"), "1", "11" }));

       return;
     }
     if (StringUtils.isBlank(table.getTableName())) {
       outJson(response, null, false, getResString("err.empty", new String[] { getResString("table.name") }));
       return;
     }
     if (!StringUtil.checkLength(table.getTableName() + "", 1, 255)) {
       outJson(response, null, false, getResString("err.length", new String[] { getResString("table.name"), "1", "255" }));

       return;
     }
     if (StringUtils.isBlank(table.getTableMaster())) {
       outJson(response, null, false, getResString("err.empty", new String[] { getResString("table.master") }));
       return;
     }
     if (!StringUtil.checkLength(table.getTableMaster() + "", 1, 255)) {
       outJson(response, null, false, getResString("err.length", new String[] { getResString("table.master"), "1", "255" }));

       return;
     }
     if (StringUtils.isBlank(table.getTableMasterId())) {
       outJson(response, null, false, getResString("err.empty", new String[] { getResString("table.master.id") }));
       return;
     }
     if (!StringUtil.checkLength(table.getTableMasterId() + "", 1, 255)) {
       outJson(response, null, false, getResString("err.length", new String[] { getResString("table.master.id"), "1", "255" }));
       return;
     }
     this.tableBiz.saveEntity((BaseEntity)table);
     outJson(response, JSONObject.toJSONString(table));
   }












   @ApiOperation("批量删除自定义表")
   @PostMapping({"/delete"})
   @ResponseBody
   @RequiresPermissions({"mdiy:table:del"})
   public void delete(@RequestBody List<TableEntity> tables, HttpServletResponse response, HttpServletRequest request) {
     int[] ids = new int[tables.size()];
     for (int i = 0; i < tables.size(); i++) {
       ids[i] = Integer.parseInt(((TableEntity)tables.get(i)).getId());
     }
     this.tableBiz.delete(ids);
     outJson(response, true);
   }





































   @ApiOperation(" 更新自定义表信息")
   @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "自定义表编号", required = true, paramType = "query"), @ApiImplicitParam(name = "appId", value = "站点编号", required = true, paramType = "query"), @ApiImplicitParam(name = "tableName", value = "自定义表名", required = true, paramType = "query"), @ApiImplicitParam(name = "tableMaster", value = "主表或主业务关键字", required = true, paramType = "query"), @ApiImplicitParam(name = "tableMasterId", value = "数据编号，主要关联主表编号", required = true, paramType = "query")})
   @PostMapping({"/update"})
   @ResponseBody
   @RequiresPermissions({"mdiy:table:update"})
   public void update(@ModelAttribute @ApiIgnore TableEntity table, HttpServletResponse response, HttpServletRequest request) {
     if (StringUtils.isBlank(table.getId())) {
       outJson(response, null, false, getResString("err.empty", new String[] { getResString("id") }));
       return;
     }
     if (!StringUtil.checkLength(table.getId() + "", 1, 11)) {
       outJson(response, null, false, getResString("err.length", new String[] { getResString("id"), "1", "11" }));

       return;
     }
     if (StringUtils.isBlank(table.getAppId() + "")) {
       outJson(response, null, false, getResString("err.empty", new String[] { getResString("app.id") }));
       return;
     }
     if (!StringUtil.checkLength(table.getAppId() + "", 1, 11)) {
       outJson(response, null, false, getResString("err.length", new String[] { getResString("app.id"), "1", "11" }));

       return;
     }
     if (StringUtils.isBlank(table.getTableName())) {
       outJson(response, null, false, getResString("err.empty", new String[] { getResString("table.name") }));
       return;
     }
     if (!StringUtil.checkLength(table.getTableName() + "", 1, 255)) {
       outJson(response, null, false, getResString("err.length", new String[] { getResString("table.name"), "1", "255" }));

       return;
     }
     if (StringUtils.isBlank(table.getTableMaster())) {
       outJson(response, null, false, getResString("err.empty", new String[] { getResString("table.master") }));
       return;
     }
     if (!StringUtil.checkLength(table.getTableMaster() + "", 1, 255)) {
       outJson(response, null, false, getResString("err.length", new String[] { getResString("table.master"), "1", "255" }));

       return;
     }
     if (StringUtils.isBlank(table.getTableMasterId())) {
       outJson(response, null, false, getResString("err.empty", new String[] { getResString("table.master.id") }));
       return;
     }
     if (!StringUtil.checkLength(table.getTableMasterId() + "", 1, 255)) {
       outJson(response, null, false, getResString("err.length", new String[] { getResString("table.master.id"), "1", "255" }));
       return;
     }
     this.tableBiz.updateEntity((BaseEntity)table);
     outJson(response, JSONObject.toJSONString(table));
   }
 }


