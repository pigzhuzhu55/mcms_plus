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
 import net.mingsoft.mdiy.biz.IPageBiz;
 import net.mingsoft.mdiy.entity.PageEntity;
 import org.apache.commons.lang3.StringUtils;
 import org.apache.shiro.authz.annotation.RequiresPermissions;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.stereotype.Controller;
 import org.springframework.ui.ModelMap;
 import org.springframework.web.bind.annotation.GetMapping;
 import org.springframework.web.bind.annotation.ModelAttribute;
 import org.springframework.web.bind.annotation.PostMapping;
 import org.springframework.web.bind.annotation.RequestBody;
 import org.springframework.web.bind.annotation.RequestMapping;
 import org.springframework.web.bind.annotation.ResponseBody;
 import springfox.documentation.annotations.ApiIgnore;
















 @Api("自定义页面接口")
 @Controller
 @RequestMapping({"/${ms.manager.path}/mdiy/page"})
 public class PageAction
   extends BaseAction
 {
   @Autowired
   private IPageBiz pageBiz;

   @GetMapping({"/index"})
   public String index(HttpServletResponse response, HttpServletRequest request) { return "/mdiy/page/index"; }





























   @ApiOperation("查询自定义页面列表接口")
   @ApiImplicitParams({@ApiImplicitParam(name = "pageModelId", value = "模块编号", required = false, paramType = "query"), @ApiImplicitParam(name = "pagePath", value = "自定义页面绑定模板的路径", required = false, paramType = "query"), @ApiImplicitParam(name = "pageTitle", value = "自定义页面标题", required = false, paramType = "query"), @ApiImplicitParam(name = "pageKey", value = "自定义页面访问路径", required = false, paramType = "query")})
   @GetMapping({"/list"})
   @ResponseBody
   public void list(@ModelAttribute @ApiIgnore PageEntity page, HttpServletResponse response, HttpServletRequest request, @ApiIgnore ModelMap model) {
     page.setPageAppId(Integer.valueOf(BasicUtil.getAppId()));
     BasicUtil.startPage();
     List pageList = this.pageBiz.query((BaseEntity)page);
     outJson(response, JSONArray.toJSONString(new EUListBean(pageList, (int)BasicUtil.endPage(pageList).getTotal()), new SerializeFilter[] { (SerializeFilter)new DoubleValueFilter(), (SerializeFilter)new DateValueFilter() }));
   }




   @GetMapping({"/form"})
   public String form(@ModelAttribute PageEntity page, HttpServletResponse response, HttpServletRequest request, @ApiIgnore ModelMap model) {
     if (page.getPageId() != null) {
       BaseEntity pageEntity = this.pageBiz.getEntity(page.getPageId().intValue());
       model.addAttribute("pageEntity", pageEntity);
     }

     return "/mdiy/page/form";
   }





















   @ApiOperation("获取自定义页面接口")
   @ApiImplicitParam(name = "pageId", value = "自定义页面编号", required = true, paramType = "query")
   @GetMapping({"/get"})
   @ResponseBody
   public void get(@ModelAttribute @ApiIgnore PageEntity page, HttpServletResponse response, HttpServletRequest request, @ApiIgnore ModelMap model) {
     if (page.getPageId().intValue() <= 0) {
       outJson(response, null, false, getResString("err.error", new String[] { getResString("page.id") }));
       return;
     }
     PageEntity _page = (PageEntity)this.pageBiz.getEntity(page.getPageId().intValue());
     outJson(response, (BaseEntity)_page);
   }



























   @ApiOperation("保存自定义页面接口")
   @ApiImplicitParams({@ApiImplicitParam(name = "pagePath", value = "自定义页面绑定模板的路径", required = true, paramType = "query"), @ApiImplicitParam(name = "pageTitle", value = "自定义页面标题", required = true, paramType = "query"), @ApiImplicitParam(name = "pageKey", value = "自定义页面访问路径", required = true, paramType = "query"), @ApiImplicitParam(name = "pageModelId", value = "模块编号", required = false, paramType = "query")})
   @PostMapping({"/save"})
   @ResponseBody
   @RequiresPermissions({"mdiy:page:save"})
   public void save(@ModelAttribute @ApiIgnore PageEntity page, HttpServletResponse response, HttpServletRequest request) {
     page.setPageAppId(Integer.valueOf(BasicUtil.getAppId()));

     if (StringUtils.isBlank(page.getPageAppId() + "")) {
       outJson(response, null, false, getResString("err.empty", new String[] { getResString("page.app.id") }));
       return;
     }
     if (!StringUtil.checkLength(page.getPageAppId() + "", 1, 10)) {
       outJson(response, null, false, getResString("err.length", new String[] { getResString("page.app.id"), "1", "10" }));

       return;
     }
     if (StringUtils.isBlank(page.getPagePath())) {
       outJson(response, null, false, getResString("err.empty", new String[] { getResString("page.path") }));
       return;
     }
     if (!StringUtil.checkLength(page.getPagePath() + "", 1, 255)) {
       outJson(response, null, false, getResString("err.length", new String[] { getResString("page.path"), "1", "255" }));

       return;
     }
     if (StringUtils.isBlank(page.getPageTitle())) {
       outJson(response, null, false, getResString("err.empty", new String[] { getResString("page.title") }));
       return;
     }
     if (!StringUtil.checkLength(page.getPageTitle() + "", 1, 255)) {
       outJson(response, null, false, getResString("err.length", new String[] { getResString("page.title"), "1", "255" }));

       return;
     }
     if (StringUtils.isBlank(page.getPageKey())) {
       outJson(response, null, false, getResString("err.empty", new String[] { getResString("page.key") }));
       return;
     }
     if (!StringUtil.checkLength(page.getPageKey() + "", 1, 255)) {
       outJson(response, null, false, getResString("err.length", new String[] { getResString("page.key"), "1", "255" }));
       return;
     }
     this.pageBiz.saveEntity((BaseEntity)page);
     outJson(response, JSONObject.toJSONString(page));
   }












   @ApiOperation("批量删除自定义页面接口")
   @PostMapping({"/delete"})
   @ResponseBody
   @RequiresPermissions({"mdiy:page:del"})
   public void delete(@RequestBody List<PageEntity> pages, HttpServletResponse response, HttpServletRequest request) {
     int[] ids = new int[pages.size()];
     for (int i = 0; i < pages.size(); i++) {
       ids[i] = ((PageEntity)pages.get(i)).getPageId().intValue();
     }
     this.pageBiz.delete(ids);
     outJson(response, true);
   }





























   @ApiOperation("更新自定义页面接口")
   @ApiImplicitParams({@ApiImplicitParam(name = "pageId", value = "自定义页面编号", required = true, paramType = "query"), @ApiImplicitParam(name = "pagePath", value = "自定义页面绑定模板的路径", required = true, paramType = "query"), @ApiImplicitParam(name = "pageTitle", value = "自定义页面标题", required = true, paramType = "query"), @ApiImplicitParam(name = "pageKey", value = "自定义页面访问路径", required = true, paramType = "query"), @ApiImplicitParam(name = "pageModelId", value = "模块编号", required = false, paramType = "query")})
   @PostMapping({"/update"})
   @ResponseBody
   @RequiresPermissions({"mdiy:page:update"})
   public void update(@ModelAttribute @ApiIgnore PageEntity page, HttpServletResponse response, HttpServletRequest request) {
     if (StringUtils.isBlank(page.getPagePath())) {
       outJson(response, null, false, getResString("err.empty", new String[] { getResString("page.path") }));
       return;
     }
     if (!StringUtil.checkLength(page.getPagePath() + "", 1, 255)) {
       outJson(response, null, false, getResString("err.length", new String[] { getResString("page.path"), "1", "255" }));

       return;
     }
     if (StringUtils.isBlank(page.getPageTitle())) {
       outJson(response, null, false, getResString("err.empty", new String[] { getResString("page.title") }));
       return;
     }
     if (!StringUtil.checkLength(page.getPageTitle() + "", 1, 255)) {
       outJson(response, null, false, getResString("err.length", new String[] { getResString("page.title"), "1", "255" }));

       return;
     }
     if (StringUtils.isBlank(page.getPageKey())) {
       outJson(response, null, false, getResString("err.empty", new String[] { getResString("page.key") }));
       return;
     }
     if (!StringUtil.checkLength(page.getPageKey() + "", 1, 255)) {
       outJson(response, null, false, getResString("err.length", new String[] { getResString("page.key"), "1", "255" }));
       return;
     }
     this.pageBiz.updateEntity((BaseEntity)page);
     outJson(response, JSONObject.toJSONString(page));
   }
 }


