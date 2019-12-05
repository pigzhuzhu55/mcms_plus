 package net.mingsoft.basic.action;

 import cn.hutool.core.io.FileUtil;
 import cn.hutool.core.io.file.FileReader;
 import cn.hutool.core.io.file.FileWriter;
 import cn.hutool.core.util.ZipUtil;
 import io.swagger.annotations.Api;
 import io.swagger.annotations.ApiImplicitParam;
 import io.swagger.annotations.ApiImplicitParams;
 import io.swagger.annotations.ApiOperation;
 import java.io.File;
 import java.io.IOException;
 import java.util.ArrayList;
 import java.util.HashMap;
 import java.util.List;
 import java.util.Map;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;
 import net.mingsoft.base.constant.e.BaseCookieEnum;
 import net.mingsoft.base.constant.e.BaseEnum;
 import net.mingsoft.basic.biz.IAppBiz;
 import net.mingsoft.basic.constant.ModelCode;
 import net.mingsoft.basic.constant.e.CookieConstEnum;
 import net.mingsoft.basic.entity.AppEntity;
 import net.mingsoft.basic.entity.ManagerSessionEntity;
 import net.mingsoft.basic.util.BasicUtil;
 import org.apache.commons.io.FileUtils;
 import org.apache.commons.lang3.StringUtils;
 import org.apache.commons.lang3.math.NumberUtils;
 import org.apache.shiro.authz.annotation.RequiresPermissions;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.stereotype.Controller;
 import org.springframework.ui.ModelMap;
 import org.springframework.web.bind.annotation.GetMapping;
 import org.springframework.web.bind.annotation.PostMapping;
 import org.springframework.web.bind.annotation.RequestMapping;
 import org.springframework.web.bind.annotation.ResponseBody;
 import springfox.documentation.annotations.ApiIgnore;




































































 @Api("获取有关模版文件夹或模版文件信息")
 @Controller("/basicTemplate")
 @RequestMapping({"/${ms.manager.path}/template"})
 public class TemplateAction
   extends BaseAction
 {
   @Autowired
   private IAppBiz appBiz;
   private static String TEMPLATE = "templets";
   private static String MOBILE = "m";










   @ApiOperation("查询模版风格供站点选择")
   @GetMapping({"/queryAppTemplateSkin"})
   @ResponseBody
   public Map queryAppTemplateSkin(HttpServletRequest request) {
     List<String> folderNameList = queryTemplateFile(request);
     Map<Object, Object> map = new HashMap<>();
     if (folderNameList != null) {
       map.put("fileName", folderNameList);
     }
     return map;
   }








   @ApiOperation("查询模版文件供栏目选择")
   @GetMapping({"/queryTemplateFileForColumn"})
   @ResponseBody
   public List<String> queryTemplateFileForColumn(HttpServletRequest request) {
     ManagerSessionEntity managerSession = getManagerBySession(request);

     String path = BasicUtil.getRealPath(TEMPLATE) + File.separator + managerSession.getBasicId();
     int websiteId = managerSession.getBasicId();
     AppEntity website = (AppEntity)this.appBiz.getEntity(websiteId);
     path = path + File.separator + website.getAppStyle();
     this.LOG.debug("tempPath:" + path);
     List<String> listName = new ArrayList<>();
     files(listName, new File(path), website.getAppStyle());
     return listName;
   }

   private void files(List<String> list, File fileDir, String style) {
     if (fileDir.isDirectory()) {
       File[] files = fileDir.listFiles();
       for (int i = 0; i < files.length; i++) {
         File currFile = files[i];
         if (currFile.isFile()) {
           String ex = currFile.getName();
           if (ex.endsWith("htm") || ex.endsWith("html")) {
             String _pathName = new String();
             _pathName = files(currFile, style, _pathName);
             list.add(_pathName + currFile.getName());
           }
         } else if (currFile.isDirectory() &&
           !currFile.getName().equalsIgnoreCase(MOBILE)) {
           files(list, currFile, style);
         }
       }
     }
   }

   private String files(File file, String style, String pathName) {
     if (!file.getParentFile().getName().equals(style)) {
       pathName = file.getParentFile().getName() + "/" + pathName;
       pathName = files(file.getParentFile(), style, pathName);
     }
     return pathName;
   }











   @ApiOperation("点击模版管理，获取所有的模版文件名")
   @ApiImplicitParam(name = "pageNo", value = "pageNo", required = true, paramType = "query")
   @GetMapping({"/queryTemplateSkin"})
   protected String queryTemplateSkin(HttpServletResponse response, @ApiIgnore ModelMap model, HttpServletRequest request) {
     String pageNo = request.getParameter("pageNo");
     if (!NumberUtils.isNumber(pageNo)) {
       pageNo = "1";
     }
     ManagerSessionEntity managerSession = getManagerBySession(request);
     List<String> folderNameList = queryTemplateFile(request);
     model.addAttribute("folderNameList", folderNameList);
     model.addAttribute("websiteId", Integer.valueOf(managerSession.getBasicId()));
     int recordCount = 0;
     if (folderNameList != null) {
       recordCount = folderNameList.size();
     }
     BasicUtil.setCookie(response, (BaseCookieEnum)CookieConstEnum.PAGENO_COOKIE, pageNo);
     return "/basic/template/template_list";
   }









   @ApiOperation("解压zip模版文件")
   @ApiImplicitParam(name = "fileUrl", value = "文件路径", required = true, paramType = "query")
   @GetMapping({"/unZip"})
   @ResponseBody
   @RequiresPermissions({"template:upload"})
   public String unZip(@ApiIgnore ModelMap model, HttpServletRequest request) throws IOException {
     boolean hasDic = false;
     String entryName = "";
     String fileUrl = request.getParameter("fileUrl");

     File file = new File(BasicUtil.getRealPath(fileUrl));

     ZipUtil.unzip(file, new File(BasicUtil.getRealPath(fileUrl.substring(0, fileUrl.length() - file.getName().length()))));
     FileUtils.forceDelete(file);
     return entryName;
   }











   @ApiOperation("删除模版")
   @ApiImplicitParam(name = "fileName", value = "模版名称", required = true, paramType = "query")
   @PostMapping({"/delete"})
   @ResponseBody
   @RequiresPermissions({"template:del"})
   public boolean delete(HttpServletRequest request) {
     String fileName = request.getParameter("fileName");
     String path = BasicUtil.getRealPath(TEMPLATE + File.separator +
         BasicUtil.getAppId() + File.separator + fileName);
     try {
       FileUtils.deleteQuietly(new File(path));
       return true;
     } catch (Exception e) {
       return false;
     }
   }










   private String getTemplateUrl(HttpServletRequest request, String fileNameUrl) {
     ManagerSessionEntity managerSession = getManagerBySession(request);
     String templetsUrl = BasicUtil.getRealPath(TEMPLATE + File.separator + managerSession
         .getBasicId() + File.separator + fileNameUrl);
     return templetsUrl;
   }











   @ApiOperation("显示子文件和子文件夹")
   @ApiImplicitParam(name = "skinFolderName", value = "skinFolderName", required = true, paramType = "query")
   @GetMapping({"/showChildFileAndFolder"})
   public String showChildFileAndFolder(HttpServletResponse response, @ApiIgnore ModelMap model, HttpServletRequest request) {
     ManagerSessionEntity managerSession = getManagerBySession(request);
     List<String> folderNameList = null;
     String skinFolderName = request.getParameter("skinFolderName");
     File[] files = (new File(BasicUtil.getRealPath(skinFolderName))).listFiles();
     if (files != null) {
       folderNameList = new ArrayList<>();
       List<String> fileNameList = new ArrayList<>();
       for (int i = 0; i < files.length; i++) {
         File currFile = files[i];
         String filter = BasicUtil.getRealPath(TEMPLATE + File.separator +
             BasicUtil.getAppId());
         this.LOG.debug("过滤路径" + filter);
         String temp = currFile.getPath();


         temp = temp.replace(File.separator.equals("\\") ? "/" : "\\", File.separator).replace(filter, "");
         if (currFile.isDirectory()) {
           folderNameList.add(temp);
         } else {
           fileNameList.add(temp);
         }
       }
       folderNameList.addAll(fileNameList);
       model.addAttribute("fileNameList", folderNameList);
     }
     String uploadFileUrl = skinFolderName;
     model.addAttribute("uploadFileUrl", uploadFileUrl);
     model.addAttribute("websiteId", Integer.valueOf(managerSession.getBasicId()));
     return "/basic/template/template_file_list";
   }









   @ApiOperation("读取模版文件内容")
   @ApiImplicitParam(name = "fileName", value = "文件名称", required = true, paramType = "query")
   @GetMapping({"/readFileContent"})
   @RequiresPermissions({"template:update"})
   public String readFileContent(@ApiIgnore ModelMap model, HttpServletRequest request) {
     String fileName = request.getParameter("fileName");
     if (!StringUtils.isEmpty(fileName))
     {
       model.addAttribute("fileContent", FileReader.create(new File(BasicUtil.getRealPath(fileName))).readString());
     }
     model.addAttribute("name", (new File(BasicUtil.getRealPath(fileName))).getName());
     model.addAttribute("fileName", fileName);
     model.addAttribute("fileNamePrefix", fileName.substring(0, fileName.lastIndexOf(File.separator) + 1));
     return "/basic/template/template_edit_file";
   }









   @ApiOperation("删除模版文件")
   @ApiImplicitParam(name = "fileName", value = "文件名称", required = true, paramType = "query")
   @PostMapping({"/deleteTemplateFile"})
   @ResponseBody
   public int deleteTemplateFile(HttpServletRequest request) {
     int pageNo = 1;
     ManagerSessionEntity managerSession = getManagerBySession(request);
     String fileName = request.getParameter("fileName");
     FileUtil.del(BasicUtil.getRealPath(TEMPLATE + File.separator + managerSession
           .getBasicId() + File.separator + fileName));

     getHistoryPageNoByCookie(request);
     return pageNo;
   }
















   @ApiOperation("写入模版文件内容")
   @ApiImplicitParams({@ApiImplicitParam(name = "fileName", value = "文件名称", required = true, paramType = "query"), @ApiImplicitParam(name = "oldFileName", value = "旧文件名称", required = true, paramType = "query"), @ApiImplicitParam(name = "fileContent", value = "文件内容", required = true, paramType = "query")})
   @PostMapping({"/writeFileContent"})
   public void writeFileContent(@ApiIgnore ModelMap model, HttpServletRequest request, HttpServletResponse response) throws IOException {
     String fileName = request.getParameter("fileName");
     String oldFileName = request.getParameter("oldFileName");
     String fileContent = request.getParameter("fileContent");
     if (!StringUtils.isEmpty(fileName)) {

       String templets = BasicUtil.getRealPath(fileName);
       this.LOG.debug(templets);
       FileWriter.create(new File(templets)).write(fileContent);
       if (!fileName.equals(oldFileName)) {

         File newName = new File(templets);

         File oldName = new File(BasicUtil.getRealPath(oldFileName));

         oldName.renameTo(newName);
         FileUtil.del(BasicUtil.getRealPath(oldFileName));
       }
       outJson(response, (BaseEnum)ModelCode.ROLE, true, null);
     }
   }








   private List<String> queryTemplateFile(HttpServletRequest request) {
     ManagerSessionEntity managerSession = getManagerBySession(request);
     List<String> folderNameList = null;
     if (!isSystemManager(request)) {
       String templets = BasicUtil.getRealPath(TEMPLATE + File.separator + managerSession
           .getBasicId() + File.separator);
       File file = new File(templets);
       String[] str = file.list();
       if (str != null) {
         folderNameList = new ArrayList<>();
         for (int i = 0; i < str.length; i++) {

           if (str[i].indexOf(".") < 0) {
             folderNameList.add(str[i]);
           }
         }
       }
     }
     return folderNameList;
   }
 }


