 package net.mingsoft.basic.action;

 import com.alibaba.fastjson.serializer.SerializeFilter;
 import io.swagger.annotations.Api;
 import io.swagger.annotations.ApiImplicitParam;
 import io.swagger.annotations.ApiImplicitParams;
 import io.swagger.annotations.ApiOperation;
 import java.util.List;
 import javax.annotation.Resource;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;
 import net.mingsoft.base.entity.BaseEntity;
 import net.mingsoft.base.filter.DateValueFilter;
 import net.mingsoft.base.filter.DoubleValueFilter;
 import net.mingsoft.base.util.JSONArray;
 import net.mingsoft.base.util.JSONObject;
 import net.mingsoft.basic.bean.EUListBean;
 import net.mingsoft.basic.biz.IFileBiz;
 import net.mingsoft.basic.entity.FileEntity;
 import net.mingsoft.basic.util.BasicUtil;
 import net.mingsoft.basic.util.StringUtil;
 import org.apache.shiro.authz.annotation.RequiresPermissions;
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














 @Api("基础文件表管理控制层")
 @Controller("netFileAction")
 @RequestMapping({"/${ms.manager.path}/basic/file"})
 public class FileAction
   extends BaseAction
 {
   @Resource(name = "fileBizImpl")
   private IFileBiz fileBiz;

   @ApiOperation("返回主界面index")
   @GetMapping({"/index"})
   public String index(HttpServletResponse response, HttpServletRequest request) { return "/basic/file/index"; }
















































   @ApiOperation("查询基础文件表列表")
   @ApiImplicitParams({@ApiImplicitParam(name = "categoryId", value = "文件分类编号", required = false, paramType = "query"), @ApiImplicitParam(name = "fileName", value = "文件名称", required = false, paramType = "query"), @ApiImplicitParam(name = "fileUrl", value = "文件链接", required = false, paramType = "query"), @ApiImplicitParam(name = "fileSize", value = "文件大小", required = false, paramType = "query"), @ApiImplicitParam(name = "fileJson", value = "文件详情Json数据", required = false, paramType = "query"), @ApiImplicitParam(name = "fileType", value = "文件类型：图片、音频、视频等", required = false, paramType = "query"), @ApiImplicitParam(name = "isChild", value = "子业务", required = false, paramType = "query")})
   @GetMapping({"/list"})
   @ResponseBody
   public void list(@ModelAttribute @ApiIgnore FileEntity file, HttpServletResponse response, HttpServletRequest request, @ApiIgnore ModelMap model, BindingResult result) {
     BasicUtil.startPage();
     List fileList = this.fileBiz.query((BaseEntity)file);
     outJson(response, JSONArray.toJSONString(new EUListBean(fileList, (int)BasicUtil.endPage(fileList).getTotal()), new SerializeFilter[] { (SerializeFilter)new DoubleValueFilter(), (SerializeFilter)new DateValueFilter() }));
   }




   @ApiOperation("返回编辑界面file_form")
   @ApiImplicitParam(name = "id", value = "文件编号", required = true, paramType = "query")
   @GetMapping({"/form"})
   public String form(@ModelAttribute @ApiIgnore FileEntity file, HttpServletResponse response, HttpServletRequest request, @ApiIgnore ModelMap model) {
     if (file.getId() != null) {
       BaseEntity fileEntity = this.fileBiz.getEntity(Integer.parseInt(file.getId()));
       model.addAttribute("fileEntity", fileEntity);
     }
     return "/basic/file/form";
   }





































   @ApiOperation("获取基础文件表")
   @ApiImplicitParam(name = "id", value = "文件编号", required = true, paramType = "query")
   @GetMapping({"/get"})
   @ResponseBody
   public FileEntity get(@ModelAttribute @ApiIgnore FileEntity file, HttpServletResponse response, HttpServletRequest request, @ApiIgnore ModelMap model) {
     if (file.getId() == null) {
       return null;
     }
     FileEntity _file = (FileEntity)this.fileBiz.getEntity(Integer.parseInt(file.getId()));
     return _file;
   }














































   @ApiOperation("保存基础文件表实体")
   @ApiImplicitParams({@ApiImplicitParam(name = "categoryId", value = "文件分类编号", required = true, paramType = "query"), @ApiImplicitParam(name = "fileName", value = "文件名称", required = true, paramType = "query"), @ApiImplicitParam(name = "fileUrl", value = "文件链接", required = true, paramType = "query"), @ApiImplicitParam(name = "fileSize", value = "文件大小", required = true, paramType = "query"), @ApiImplicitParam(name = "fileJson", value = "文件详情Json数据", required = true, paramType = "query"), @ApiImplicitParam(name = "fileType", value = "文件类型：图片、音频、视频等", required = true, paramType = "query"), @ApiImplicitParam(name = "isChild", value = "子业务", required = true, paramType = "query")})
   @PostMapping({"/save"})
   @ResponseBody
   @RequiresPermissions({"file:save"})
   public void save(@ModelAttribute @ApiIgnore FileEntity file, HttpServletResponse response, HttpServletRequest request, BindingResult result) {
     if (StringUtil.isBlank(file.getCategoryId())) {
       outJson(response, null, false, getResString("err.empty", new String[] { getResString("category.id") }));
       return;
     }
     if (!StringUtil.checkLength(file.getCategoryId() + "", 1, 11)) {
       outJson(response, null, false, getResString("err.length", new String[] { getResString("category.id"), "1", "11" }));

       return;
     }
     if (StringUtil.isBlank(file.getFileName())) {
       outJson(response, null, false, getResString("err.empty", new String[] { getResString("file.name") }));
       return;
     }
     if (!StringUtil.checkLength(file.getFileName() + "", 1, 200)) {
       outJson(response, null, false, getResString("err.length", new String[] { getResString("file.name"), "1", "200" }));

       return;
     }
     if (StringUtil.isBlank(file.getFileUrl())) {
       outJson(response, null, false, getResString("err.empty", new String[] { getResString("file.url") }));
       return;
     }
     if (!StringUtil.checkLength(file.getFileUrl() + "", 1, 500)) {
       outJson(response, null, false, getResString("err.length", new String[] { getResString("file.url"), "1", "500" }));

       return;
     }
     if (StringUtil.isBlank(file.getFileSize())) {
       outJson(response, null, false, getResString("err.empty", new String[] { getResString("file.size") }));
       return;
     }
     if (!StringUtil.checkLength(file.getFileSize() + "", 1, 11)) {
       outJson(response, null, false, getResString("err.length", new String[] { getResString("file.size"), "1", "11" }));

       return;
     }
     if (StringUtil.isBlank(file.getFileJson())) {
       outJson(response, null, false, getResString("err.empty", new String[] { getResString("file.json") }));
       return;
     }
     if (!StringUtil.checkLength(file.getFileJson() + "", 1, 500)) {
       outJson(response, null, false, getResString("err.length", new String[] { getResString("file.json"), "1", "500" }));

       return;
     }
     if (StringUtil.isBlank(file.getFileType())) {
       outJson(response, null, false, getResString("err.empty", new String[] { getResString("file.type") }));
       return;
     }
     if (!StringUtil.checkLength(file.getFileType() + "", 1, 50)) {
       outJson(response, null, false, getResString("err.length", new String[] { getResString("file.type"), "1", "50" }));

       return;
     }
     if (StringUtil.isBlank(file.getIsChild())) {
       outJson(response, null, false, getResString("err.empty", new String[] { getResString("is.child") }));
       return;
     }
     if (!StringUtil.checkLength(file.getIsChild() + "", 1, 50)) {
       outJson(response, null, false, getResString("err.length", new String[] { getResString("is.child"), "1", "50" }));
       return;
     }
     this.fileBiz.saveEntity((BaseEntity)file);
     outJson(response, JSONObject.toJSONString(file));
   }












   @ApiOperation("批量删除基础文件表")
   @PostMapping({"/delete"})
   @ResponseBody
   @RequiresPermissions({"file:del"})
   public void delete(@RequestBody List<FileEntity> files, HttpServletResponse response, HttpServletRequest request) {
     int[] ids = new int[files.size()];
     for (int i = 0; i < files.size(); i++) {
       ids[i] = Integer.parseInt(((FileEntity)files.get(i)).getId());
     }
     this.fileBiz.delete(ids);
     outJson(response, true);
   }
















































   @ApiOperation("更新基础文件表信息基础文件表")
   @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "文件编号", required = true, paramType = "query"), @ApiImplicitParam(name = "categoryId", value = "文件分类编号", required = true, paramType = "query"), @ApiImplicitParam(name = "fileName", value = "文件名称", required = true, paramType = "query"), @ApiImplicitParam(name = "fileUrl", value = "文件链接", required = true, paramType = "query"), @ApiImplicitParam(name = "fileSize", value = "文件大小", required = true, paramType = "query"), @ApiImplicitParam(name = "fileJson", value = "文件详情Json数据", required = true, paramType = "query"), @ApiImplicitParam(name = "fileType", value = "文件类型：图片、音频、视频等", required = true, paramType = "query"), @ApiImplicitParam(name = "isChild", value = "子业务", required = true, paramType = "query")})
   @PostMapping({"/update"})
   @ResponseBody
   @RequiresPermissions({"file:update"})
   public void update(@ModelAttribute @ApiIgnore FileEntity file, HttpServletResponse response, HttpServletRequest request) {
     if (StringUtil.isBlank(file.getId())) {
       outJson(response, null, false, getResString("err.empty", new String[] { getResString("file.id") }));
       return;
     }
     if (!StringUtil.checkLength(file.getId() + "", 1, 11)) {
       outJson(response, null, false, getResString("err.length", new String[] { getResString("file.id"), "1", "11" }));

       return;
     }
     if (StringUtil.isBlank(file.getCategoryId())) {
       outJson(response, null, false, getResString("err.empty", new String[] { getResString("category.id") }));
       return;
     }
     if (!StringUtil.checkLength(file.getCategoryId() + "", 1, 11)) {
       outJson(response, null, false, getResString("err.length", new String[] { getResString("category.id"), "1", "11" }));

       return;
     }
     if (StringUtil.isBlank(file.getFileName())) {
       outJson(response, null, false, getResString("err.empty", new String[] { getResString("file.name") }));
       return;
     }
     if (!StringUtil.checkLength(file.getFileName() + "", 1, 200)) {
       outJson(response, null, false, getResString("err.length", new String[] { getResString("file.name"), "1", "200" }));

       return;
     }
     if (StringUtil.isBlank(file.getFileUrl())) {
       outJson(response, null, false, getResString("err.empty", new String[] { getResString("file.url") }));
       return;
     }
     if (!StringUtil.checkLength(file.getFileUrl() + "", 1, 500)) {
       outJson(response, null, false, getResString("err.length", new String[] { getResString("file.url"), "1", "500" }));

       return;
     }
     if (StringUtil.isBlank(file.getFileSize())) {
       outJson(response, null, false, getResString("err.empty", new String[] { getResString("file.size") }));
       return;
     }
     if (!StringUtil.checkLength(file.getFileSize() + "", 1, 11)) {
       outJson(response, null, false, getResString("err.length", new String[] { getResString("file.size"), "1", "11" }));

       return;
     }
     if (StringUtil.isBlank(file.getFileJson())) {
       outJson(response, null, false, getResString("err.empty", new String[] { getResString("file.json") }));
       return;
     }
     if (!StringUtil.checkLength(file.getFileJson() + "", 1, 500)) {
       outJson(response, null, false, getResString("err.length", new String[] { getResString("file.json"), "1", "500" }));

       return;
     }
     if (StringUtil.isBlank(file.getFileType())) {
       outJson(response, null, false, getResString("err.empty", new String[] { getResString("file.type") }));
       return;
     }
     if (!StringUtil.checkLength(file.getFileType() + "", 1, 50)) {
       outJson(response, null, false, getResString("err.length", new String[] { getResString("file.type"), "1", "50" }));

       return;
     }
     if (StringUtil.isBlank(file.getIsChild())) {
       outJson(response, null, false, getResString("err.empty", new String[] { getResString("is.child") }));
       return;
     }
     if (!StringUtil.checkLength(file.getIsChild() + "", 1, 50)) {
       outJson(response, null, false, getResString("err.length", new String[] { getResString("is.child"), "1", "50" }));
       return;
     }
     this.fileBiz.updateEntity((BaseEntity)file);
     outJson(response, JSONObject.toJSONString(file));
   }
 }


