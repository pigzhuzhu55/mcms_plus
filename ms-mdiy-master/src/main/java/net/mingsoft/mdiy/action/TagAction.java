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
 import net.mingsoft.mdiy.biz.ITagBiz;
 import net.mingsoft.mdiy.entity.TagEntity;
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














 @Api("标签管理接口")
 @Controller
 @RequestMapping({"/${ms.manager.path}/mdiy/tag"})
 public class TagAction
   extends BaseAction
 {
   @Autowired
   private ITagBiz tagBiz;

   @GetMapping({"/index"})
   public String index(HttpServletResponse response, HttpServletRequest request, @ApiIgnore ModelMap model) { return "/mdiy/tag/index"; }


























   @ApiOperation("标签列表")
   @ApiImplicitParams({@ApiImplicitParam(name = "tagName", value = "模块编号", required = false, paramType = "query"), @ApiImplicitParam(name = "tagType", value = "自定义页面绑定模板的路径", required = false, paramType = "query"), @ApiImplicitParam(name = "tagDescription", value = "自定义页面标题", required = false, paramType = "query")})
   @GetMapping({"/list"})
   @ResponseBody
   public void list(@ModelAttribute @ApiIgnore TagEntity tag, HttpServletResponse response, HttpServletRequest request, @ApiIgnore ModelMap model, BindingResult result) {
     BasicUtil.startPage();
     List tagList = this.tagBiz.query((BaseEntity)tag);
     outJson(response, JSONArray.toJSONString(new EUListBean(tagList, (int)BasicUtil.endPage(tagList).getTotal()), new SerializeFilter[] { (SerializeFilter)new DoubleValueFilter(), (SerializeFilter)new DateValueFilter() }));
   }




   @GetMapping({"/form"})
   public String form(@ModelAttribute TagEntity tag, HttpServletResponse response, HttpServletRequest request, @ApiIgnore ModelMap model) {
     if (tag.getId() != null) {
       BaseEntity tagEntity = this.tagBiz.getEntity(Integer.parseInt(tag.getId()));
       model.addAttribute("tagEntity", tagEntity);
     }
     return "/mdiy/tag/form";
   }



















   @ApiOperation("获取标签详情")
   @ApiImplicitParam(name = "id", value = "标签编号", required = true, paramType = "query")
   @GetMapping({"/get"})
   @ResponseBody
   public TagEntity get(@ModelAttribute @ApiIgnore TagEntity tag, HttpServletResponse response, HttpServletRequest request, @ApiIgnore ModelMap model) {
     if (tag.getId() == null) {
       return null;
     }
     TagEntity _tag = (TagEntity)this.tagBiz.getEntity(Integer.parseInt(tag.getId()));
     return _tag;
   }
























   @ApiOperation("保存标签")
   @ApiImplicitParams({@ApiImplicitParam(name = "tagName", value = "模块编号", required = true, paramType = "query"), @ApiImplicitParam(name = "tagDescription", value = "自定义页面标题", required = true, paramType = "query"), @ApiImplicitParam(name = "tagType", value = "自定义页面绑定模板的路径", required = false, paramType = "query")})
   @PostMapping({"/save"})
   @ResponseBody
   @RequiresPermissions({"mdiy:tag:save"})
   public void save(@ModelAttribute @ApiIgnore TagEntity tag, HttpServletResponse response, HttpServletRequest request, BindingResult result) {
     if (StringUtils.isBlank(tag.getTagName())) {
       outJson(response, null, false, getResString("err.empty", new String[] { getResString("tag.name") }));
       return;
     }
     if (!StringUtil.checkLength(tag.getTagName() + "", 1, 255)) {
       outJson(response, null, false, getResString("err.length", new String[] { getResString("tag.name"), "1", "255" }));

       return;
     }
     if (StringUtils.isBlank(tag.getTagDescription())) {
       outJson(response, null, false, getResString("err.empty", new String[] { getResString("tag.description") }));
       return;
     }
     if (!StringUtil.checkLength(tag.getTagDescription() + "", 1, 255)) {
       outJson(response, null, false, getResString("err.length", new String[] { getResString("tag.description"), "1", "255" }));

       return;
     }
     this.tagBiz.saveEntity((BaseEntity)tag);
     outJson(response, JSONObject.toJSONString(tag));
   }












   @ApiOperation("批量删除标签")
   @PostMapping({"/delete"})
   @ResponseBody
   @RequiresPermissions({"mdiy:tag:del"})
   public void delete(@RequestBody @ApiIgnore List<TagEntity> tags, HttpServletResponse response, HttpServletRequest request) {
     int[] ids = new int[tags.size()];
     for (int i = 0; i < tags.size(); i++) {
       ids[i] = Integer.parseInt(((TagEntity)tags.get(i)).getId());
     }
     this.tagBiz.delete(ids);
     outJson(response, true);
   }


























   @ApiOperation("更新标签信息")
   @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "标签编号", required = true, paramType = "query"), @ApiImplicitParam(name = "tagName", value = "模块编号", required = true, paramType = "query"), @ApiImplicitParam(name = "tagDescription", value = "自定义页面标题", required = true, paramType = "query"), @ApiImplicitParam(name = "tagType", value = "自定义页面绑定模板的路径", required = false, paramType = "query")})
   @PostMapping({"/update"})
   @ResponseBody
   @RequiresPermissions({"mdiy:tag:update"})
   public void update(@ModelAttribute @ApiIgnore TagEntity tag, HttpServletResponse response, HttpServletRequest request) {
     if (StringUtils.isBlank(tag.getId())) {
       outJson(response, null, false, getResString("err.empty", new String[] { getResString("id") }));
       return;
     }
     if (!StringUtil.checkLength(tag.getId() + "", 1, 11)) {
       outJson(response, null, false, getResString("err.length", new String[] { getResString("id"), "1", "11" }));

       return;
     }
     if (StringUtils.isBlank(tag.getTagName())) {
       outJson(response, null, false, getResString("err.empty", new String[] { getResString("tag.name") }));
       return;
     }
     if (!StringUtil.checkLength(tag.getTagName() + "", 1, 255)) {
       outJson(response, null, false, getResString("err.length", new String[] { getResString("tag.name"), "1", "255" }));

       return;
     }
     if (StringUtils.isBlank(tag.getTagDescription())) {
       outJson(response, null, false, getResString("err.empty", new String[] { getResString("tag.description") }));
       return;
     }
     if (!StringUtil.checkLength(tag.getTagDescription() + "", 1, 255)) {
       outJson(response, null, false, getResString("err.length", new String[] { getResString("tag.description"), "1", "255" }));
       return;
     }
     this.tagBiz.updateEntity((BaseEntity)tag);
     outJson(response, JSONObject.toJSONString(tag));
   }
 }


