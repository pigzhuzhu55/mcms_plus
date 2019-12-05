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
 import net.mingsoft.mdiy.biz.ITagSqlBiz;
 import net.mingsoft.mdiy.entity.TagSqlEntity;
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












 @Api("标签对应多个sql语句管理接口")
 @Controller
 @RequestMapping({"/${ms.manager.path}/mdiy/tagSql"})
 public class TagSqlAction
   extends BaseAction
 {
   @Autowired
   private ITagSqlBiz tagSqlBiz;

   @GetMapping({"/index"})
   public String index(HttpServletResponse response, HttpServletRequest request, @ApiIgnore ModelMap model) {
     model.addAttribute("tagId", BasicUtil.getInt("id"));
     return "/mdiy/tag_sql/index";
   }





















   @ApiOperation("查询标签对应多个sql语句列表")
   @ApiImplicitParams({@ApiImplicitParam(name = "tagId", value = "自定义标签编号", required = false, paramType = "query"), @ApiImplicitParam(name = "tagSql", value = "自定义sql支持ftl写法", required = false, paramType = "query"), @ApiImplicitParam(name = "sort", value = "排序升序", required = false, paramType = "query")})
   @GetMapping({"/list"})
   @ResponseBody
   public void list(@ModelAttribute @ApiIgnore TagSqlEntity tagSql, HttpServletResponse response, HttpServletRequest request, @ApiIgnore ModelMap model, BindingResult result) {
     BasicUtil.startPage();
     List tagSqlList = this.tagSqlBiz.query((BaseEntity)tagSql);
     outJson(response, JSONArray.toJSONString(new EUListBean(tagSqlList, (int)BasicUtil.endPage(tagSqlList).getTotal()), new SerializeFilter[] { (SerializeFilter)new DoubleValueFilter(), (SerializeFilter)new DateValueFilter() }));
   }





















   @ApiOperation("保存标签对应多个sql语句")
   @ApiImplicitParams({@ApiImplicitParam(name = "tagSql", value = "自定义sql支持ftl写法", required = true, paramType = "query"), @ApiImplicitParam(name = "sort", value = "排序升序", required = true, paramType = "query"), @ApiImplicitParam(name = "tagId", value = "自定义标签编号", required = false, paramType = "query")})
   @PostMapping({"/save"})
   @ResponseBody
   @RequiresPermissions({"mdiy:tagSql:save"})
   public void save(@ModelAttribute @ApiIgnore TagSqlEntity tagSql, HttpServletResponse response, HttpServletRequest request, BindingResult result) {
     if (StringUtils.isBlank(tagSql.getTagSql())) {
       outJson(response, null, false, getResString("err.empty", new String[] { getResString("tag.sql") }));
       return;
     }
     if (!StringUtil.checkLength(tagSql.getTagSql() + "", 1, 1000)) {
       outJson(response, null, false, getResString("err.length", new String[] { getResString("tag.sql"), "1", "1000" }));

       return;
     }
     if (StringUtils.isBlank(tagSql.getSort())) {
       outJson(response, null, false, getResString("err.empty", new String[] { getResString("sort") }));
       return;
     }
     if (!StringUtil.checkLength(tagSql.getSort() + "", 1, 255)) {
       outJson(response, null, false, getResString("err.length", new String[] { getResString("sort"), "1", "255" }));
       return;
     }
     this.tagSqlBiz.saveEntity((BaseEntity)tagSql);
     outJson(response, JSONObject.toJSONString(tagSql));
   }


















   @ApiOperation("批量删除标签属性")
   @PostMapping({"/delete"})
   @ResponseBody
   @RequiresPermissions({"mdiy:tagSql:del"})
   public void delete(@RequestBody List<TagSqlEntity> tagSqls, HttpServletResponse response, HttpServletRequest request) {
     int[] ids = new int[tagSqls.size()];
     for (int i = 0; i < tagSqls.size(); i++) {
       ids[i] = Integer.parseInt(((TagSqlEntity)tagSqls.get(i)).getId());
     }
     this.tagSqlBiz.delete(ids);
     outJson(response, true);
   }

























   @ApiOperation("更新标签对应多个sql语句")
   @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "标签对应多个sql语句编号", required = true, paramType = "query"), @ApiImplicitParam(name = "tagId", value = "自定义标签编号", required = true, paramType = "query"), @ApiImplicitParam(name = "tagSql", value = "自定义sql支持ftl写法", required = true, paramType = "query"), @ApiImplicitParam(name = "sort", value = "排序升序", required = true, paramType = "query")})
   @PostMapping({"/update"})
   @ResponseBody
   @RequiresPermissions({"mdiy:tagSql:update"})
   public void update(@ModelAttribute @ApiIgnore TagSqlEntity tagSql, HttpServletResponse response, HttpServletRequest request) {
     if (StringUtils.isBlank(tagSql.getId())) {
       outJson(response, null, false, getResString("err.empty", new String[] { getResString("id") }));
       return;
     }
     if (!StringUtil.checkLength(tagSql.getId() + "", 1, 11)) {
       outJson(response, null, false, getResString("err.length", new String[] { getResString("id"), "1", "11" }));

       return;
     }
     if (StringUtils.isBlank(tagSql.getTagId() + "")) {
       outJson(response, null, false, getResString("err.empty", new String[] { getResString("tag.id") }));
       return;
     }
     if (!StringUtil.checkLength(tagSql.getTagId() + "", 1, 11)) {
       outJson(response, null, false, getResString("err.length", new String[] { getResString("tag.id"), "1", "11" }));

       return;
     }
     if (StringUtils.isBlank(tagSql.getTagSql())) {
       outJson(response, null, false, getResString("err.empty", new String[] { getResString("tag.sql") }));

       return;
     }
     if (StringUtils.isBlank(tagSql.getSort())) {
       outJson(response, null, false, getResString("err.empty", new String[] { getResString("tag.description") }));
       return;
     }
     if (!StringUtil.checkLength(tagSql.getSort() + "", 1, 255)) {
       outJson(response, null, false, getResString("err.length", new String[] { getResString("tag.description"), "1", "255" }));
       return;
     }
     this.tagSqlBiz.updateEntity((BaseEntity)tagSql);
     outJson(response, JSONObject.toJSONString(tagSql));
   }

















   @ApiOperation("获取标签对应多个sql语句详情")
   @ApiImplicitParam(name = "id", value = "标签对应多个sql语句编号", required = true, paramType = "query")
   @GetMapping({"/get"})
   @ResponseBody
   public TagSqlEntity get(@ModelAttribute @ApiIgnore TagSqlEntity tagSql, HttpServletResponse response, HttpServletRequest request, @ApiIgnore ModelMap model) {
     if (tagSql.getId() == null) {
       return null;
     }
     TagSqlEntity _tagSql = (TagSqlEntity)this.tagSqlBiz.getEntity(Integer.parseInt(tagSql.getId()));
     return _tagSql;
   }
 }


