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
 import net.mingsoft.base.entity.BaseEntity;
 import net.mingsoft.base.filter.DateValueFilter;
 import net.mingsoft.base.filter.DoubleValueFilter;
 import net.mingsoft.base.util.JSONArray;
 import net.mingsoft.base.util.JSONObject;
 import net.mingsoft.basic.bean.EUListBean;
 import net.mingsoft.basic.biz.IColumnBiz;
 import net.mingsoft.basic.entity.ColumnEntity;
 import net.mingsoft.basic.entity.ManagerSessionEntity;
 import net.mingsoft.basic.util.BasicUtil;
 import net.mingsoft.basic.util.StringUtil;
 import net.mingsoft.mdiy.biz.IContentModelFieldBiz;
 import net.mingsoft.mdiy.biz.ISearchBiz;
 import net.mingsoft.mdiy.constant.Const;
 import net.mingsoft.mdiy.entity.ContentModelFieldEntity;
 import net.mingsoft.mdiy.entity.SearchEntity;
 import org.apache.commons.lang3.StringUtils;
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





















 @Api("自定义搜索接口")
 @Controller
 @RequestMapping({"/${ms.manager.path}/mdiy/search"})
 public class SearchAction
   extends BaseAction
 {
   @Autowired
   private ISearchBiz searchBiz;
   @Autowired
   private IColumnBiz columnBiz;
   @Autowired
   private IContentModelFieldBiz fieldBiz;

   @GetMapping({"/index"})
   public String index(HttpServletResponse response, HttpServletRequest request, @ApiIgnore ModelMap model) {
     model.addAttribute("searchType", BasicUtil.resToMap("net.mingsoft.mdiy.resources.search_type"));
     return "/mdiy/search/index";
   }

























   @ApiOperation("查询自定义搜索列表接口")
   @ApiImplicitParams({@ApiImplicitParam(name = "searchName", value = "搜索名称", required = false, paramType = "query"), @ApiImplicitParam(name = "searchTemplets", value = "搜索结果模板", required = false, paramType = "query"), @ApiImplicitParam(name = "searchType", value = "搜索类型", required = false, paramType = "query")})
   @GetMapping({"/list"})
   @ResponseBody
   public void list(@ModelAttribute @ApiIgnore SearchEntity search, HttpServletResponse response, HttpServletRequest request, @ApiIgnore ModelMap model) {
     if (search == null) {
       search = new SearchEntity();
     }
     search.setAppId(BasicUtil.getAppId());
     BasicUtil.startPage();
     List searchList = this.searchBiz.query((BaseEntity)search);
     outJson(response, JSONArray.toJSONString(new EUListBean(searchList, (int)BasicUtil.endPage(searchList).getTotal()), new SerializeFilter[] { (SerializeFilter)new DoubleValueFilter(), (SerializeFilter)new DateValueFilter() }));
   }




   @GetMapping({"/form"})
   public void form(@ModelAttribute SearchEntity search, HttpServletResponse response, HttpServletRequest request, @ApiIgnore ModelMap model) {
     if (search.getSearchId() <= 0) {
       outJson(response, false);
       return;
     }
     SearchEntity searchEntity = (SearchEntity)this.searchBiz.getEntity((BaseEntity)search);
     outJson(response, (BaseEntity)searchEntity);
   }



















   @ApiOperation("获取自定义搜索详情")
   @ApiImplicitParam(name = "searchId", value = "自定义搜索编号", required = true, paramType = "query")
   @GetMapping({"/get"})
   @ResponseBody
   public void get(@ModelAttribute @ApiIgnore SearchEntity search, HttpServletResponse response, HttpServletRequest request, @ApiIgnore ModelMap model) {
     if (search.getSearchId() <= 0) {
       outJson(response, null, false, getResString("err.error", new String[] { getResString("search.id") }));
       return;
     }
     SearchEntity _search = (SearchEntity)this.searchBiz.getEntity(search.getSearchId());
     outJson(response, (BaseEntity)_search);
   }
























   @ApiOperation("保存自定义搜索接口")
   @ApiImplicitParams({@ApiImplicitParam(name = "searchType", value = "搜索类型", required = true, paramType = "query"), @ApiImplicitParam(name = "searchName", value = "搜索名称", required = true, paramType = "query"), @ApiImplicitParam(name = "searchTemplets", value = "搜索结果模板", required = false, paramType = "query")})
   @PostMapping({"/save"})
   @ResponseBody
   @RequiresPermissions({"mdiy:search:save"})
   public void save(@ModelAttribute @ApiIgnore SearchEntity search, HttpServletResponse response, HttpServletRequest request) {
     if (StringUtils.isBlank(search.getSearchType())) {
       outJson(response, null, false, getResString("err.empty", new String[] { getResString("search.type") }));
       return;
     }
     if (!StringUtil.checkLength(search.getSearchType() + "", 1, 255)) {
       outJson(response, null, false, getResString("err.length", new String[] { getResString("search.type"), "1", "255" }));

       return;
     }
     if (StringUtils.isBlank(search.getSearchName())) {
       outJson(response, null, false, getResString("err.empty", new String[] { getResString("search.name") }));
       return;
     }
     if (!StringUtil.checkLength(search.getSearchName() + "", 1, 255)) {
       outJson(response, null, false, getResString("err.length", new String[] { getResString("search.name"), "1", "255" }));
       return;
     }
     search.setAppId(BasicUtil.getAppId());
     this.searchBiz.saveEntity((BaseEntity)search);
     outJson(response, JSONObject.toJSONString(search));
   }












   @ApiOperation("批量删除自定义搜索接口")
   @PostMapping({"/delete"})
   @ResponseBody
   @RequiresPermissions({"mdiy:search:del"})
   public void delete(@RequestBody List<SearchEntity> searchs, HttpServletResponse response, HttpServletRequest request) {
     int[] ids = new int[searchs.size()];
     for (int i = 0; i < searchs.size(); i++) {
       ids[i] = ((SearchEntity)searchs.get(i)).getSearchId();
     }
     this.searchBiz.delete(ids);
     outJson(response, true);
   }

























   @ApiOperation("更新自定义搜索信息接口")
   @ApiImplicitParams({@ApiImplicitParam(name = "searchType", value = "搜索类型", required = true, paramType = "query"), @ApiImplicitParam(name = "searchName", value = "搜索名称", required = false, paramType = "query"), @ApiImplicitParam(name = "searchTemplets", value = "搜索结果模板", required = false, paramType = "query")})
   @PostMapping({"/update"})
   @ResponseBody
   @RequiresPermissions({"mdiy:search:update"})
   public void update(@ModelAttribute @ApiIgnore SearchEntity search, HttpServletResponse response, HttpServletRequest request) {
     if (StringUtils.isBlank(search.getSearchType())) {
       outJson(response, null, false, getResString("err.empty", new String[] { getResString("search.type") }));
       return;
     }
     if (!StringUtil.checkLength(search.getSearchType() + "", 1, 255)) {
       outJson(response, null, false, getResString("err.length", new String[] { getResString("search.type"), "1", "255" }));
       return;
     }
     this.searchBiz.updateEntity((BaseEntity)search);
     outJson(response, JSONObject.toJSONString(search));
   }







   @ApiOperation("查询栏目自定义的字段名接口")
   @ApiImplicitParam(name = "columnId", value = "栏目编号", required = true, paramType = "path")
   @GetMapping({"/{columnId}/queryFieldName"})
   @ResponseBody
   public Map queryFieldName(@PathVariable @ApiIgnore int columnId, HttpServletRequest request) {
     Map<Object, Object> model = new HashMap<>();

     ColumnEntity column = (ColumnEntity)this.columnBiz.getEntity(columnId);
     if (column != null) {

       int fieldCmid = column.getColumnContentModelId();

       List<ContentModelFieldEntity> listField = this.fieldBiz.queryListByCmid(fieldCmid);
       model.put("listField", listField);
     }
     return model;
   }









   @ApiOperation("生成搜索表单的html样式")
   @GetMapping({"/generateSreachFormHtml"})
   public String generateSreachFormHtml(@ApiIgnore ModelMap model, HttpServletRequest request) {
     ManagerSessionEntity managerSession = getManagerBySession(request);
     int searchId = 0;
     if (!StringUtils.isBlank(request.getParameter("searchId"))) {
       searchId = Integer.valueOf(request.getParameter("searchId")).intValue();
     }


     Map<String, String[]> field = (Map)new HashMap<>();
     field = request.getParameterMap();
     int basicCategoryId = 0;
     int cmId = 0;
     Map<String, String> basicField = getMapByProperties(Const.BASIC_FIELD);
     Map<String, String> basicAttribute = getMapByProperties(Const.BASIC_ATTRIBUTE);
     List<Map<String, String>> listFieldName = new ArrayList<>();
     for (Map.Entry<String, String[]> entry : field.entrySet()) {
       String key = entry.getKey();
       String value = ((String[])entry.getValue())[0];
       if (key.equals("columnId") && !StringUtils.isBlank(value) && !key.equals("searchId")) {
         basicCategoryId = Integer.valueOf(value).intValue();
       }
       if (!key.equals("columnId") && !key.equals("searchId")) {
         Map<String, String> map = new HashMap<>();
         map.put("name", key);
         map.put("type", value);

         if (!StringUtils.isBlank(basicField.get(key))) {
           map.put("ch", basicField.get(key));
         } else {

           if (basicCategoryId != 0) {
             ColumnEntity column = (ColumnEntity)this.columnBiz.getEntity(Integer.valueOf(basicCategoryId).intValue());

             cmId = column.getColumnContentModelId();
           }
           ContentModelFieldEntity fieldEntity = this.fieldBiz.getEntityByCmId(cmId, key);
           if (fieldEntity != null) {
             String fieldTipsName = fieldEntity.getFieldTipsName();
             map.put("ch", fieldTipsName);
           }
         }
         if (key.equals("article_type")) {
           map.put("default", basicAttribute.toString());
         } else {
           map.put("default", key.toString());
         }
         listFieldName.add(map);
       }
     }
     model.addAttribute("searchId", Integer.valueOf(searchId));
     model.addAttribute("searchType", request.getParameter("searchType"));
     model.addAttribute("websiteId", Integer.valueOf(managerSession.getBasicId()));
     model.addAttribute("listFieldName", listFieldName);
     model.addAttribute("basicCategoryId", Integer.valueOf(basicCategoryId));
     return "/mdiy/search/search_field";
   }







   @ApiOperation("跳转至创建搜索页面")
   @GetMapping({"/{searchId}/searchCode"})
   public String searchCode(@PathVariable int searchId, ModelMap model, HttpServletRequest request) {
     List<ColumnEntity> columnList = this.columnBiz.queryColumnListByWebsiteId(BasicUtil.getAppId());
     SearchEntity searchEntity = new SearchEntity();
     searchEntity.setSearchId(searchId);
     SearchEntity search = (SearchEntity)this.searchBiz.getEntity((BaseEntity)searchEntity);
     model.addAttribute("columnList", JSONArray.toJSONString(columnList, new SerializeFilter[0]));
     model.addAttribute("searchId", Integer.valueOf(searchId));
     model.addAttribute("searchType", search.getSearchType());
     return "/mdiy/search/search_code";
   }
 }


