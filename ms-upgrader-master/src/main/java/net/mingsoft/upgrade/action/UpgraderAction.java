 package net.mingsoft.upgrade.action;

 import cn.hutool.core.io.FileUtil;
 import cn.hutool.core.util.ObjectUtil;
 import cn.hutool.http.HttpRequest;
 import cn.hutool.http.HttpUtil;
 import com.alibaba.druid.pool.DruidDataSource;
 import io.swagger.annotations.ApiOperation;
 import java.io.File;
 import java.io.FileNotFoundException;
 import java.io.FileOutputStream;
 import java.io.FileReader;
 import java.io.IOException;
 import java.io.InputStream;
 import java.io.OutputStream;
 import java.io.UnsupportedEncodingException;
 import java.lang.reflect.InvocationTargetException;
 import java.lang.reflect.Method;
 import java.net.URL;
 import java.net.URLClassLoader;
 import java.sql.Connection;
 import java.sql.SQLException;
 import java.sql.Timestamp;
 import java.util.ArrayList;
 import java.util.Enumeration;
 import java.util.List;
 import java.util.zip.ZipException;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;
 import net.mingsoft.base.entity.BaseEntity;
 import net.mingsoft.base.entity.ResultJson;
 import net.mingsoft.basic.action.BasicAction;
 import net.mingsoft.basic.biz.IModelBiz;
 import net.mingsoft.basic.biz.IRoleModelBiz;
 import net.mingsoft.basic.entity.ModelEntity;
 import net.mingsoft.basic.entity.RoleModelEntity;
 import net.mingsoft.basic.util.BasicUtil;
 import net.mingsoft.basic.util.SpringUtil;
 import net.mingsoft.basic.util.StringUtil;
 import org.apache.commons.lang3.StringUtils;
 import org.apache.http.HttpEntity;
 import org.apache.http.client.ClientProtocolException;
 import org.apache.http.client.methods.CloseableHttpResponse;
 import org.apache.http.client.methods.HttpPost;
 import org.apache.http.client.methods.HttpUriRequest;
 import org.apache.http.conn.HttpHostConnectException;
 import org.apache.http.impl.client.CloseableHttpClient;
 import org.apache.http.impl.client.HttpClients;
 import org.apache.ibatis.jdbc.ScriptRunner;
 import org.apache.tools.zip.ZipEntry;
 import org.apache.tools.zip.ZipFile;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.beans.factory.annotation.Value;
 import org.springframework.stereotype.Controller;
 import org.springframework.ui.ModelMap;
 import org.springframework.util.ClassUtils;
 import org.springframework.web.bind.annotation.PostMapping;
 import org.springframework.web.bind.annotation.RequestMapping;
 import org.springframework.web.bind.annotation.RequestMethod;
 import org.springframework.web.bind.annotation.ResponseBody;
 import springfox.documentation.annotations.ApiIgnore;

 @Controller("upgrader")
 @RequestMapping({"/${ms.manager.path}/upgrader"})
 public class UpgraderAction
   extends BasicAction {
   private String html = "<html><head><title>价值源自分享！</title></head><body style=\"padding-top: 5%;background-color: #ffffff;\"><center>\t<img src=\"http://cdn.mingsoft.net/global/mstore/{result/}.png\" />\t<div>\t\t<p style=\"clear: both; margin: 30px auto 20px auto; line-height: 35px; font-size: 20px; color: #7e7e7e;\">{message/}</p>\t</div></center></body></html>";





   @Value("${ms.mstore.http:http://store.mingsoft.net}")
   private String MS_MSTORE_HTTP;




   @Value("${ms.mstore.host:store.mingsoft.net}")
   private String MS_MSTORE_HOST;




   @Autowired
   private IModelBiz modelBiz;




   @Autowired
   private IRoleModelBiz roleModelBiz;





   @ResponseBody
   @RequestMapping(value = {"/sync"}, method = {RequestMethod.GET})
   public void sync(HttpServletRequest request, HttpServletResponse response) {
     String sync = ((HttpRequest)HttpUtil.createPost(this.MS_MSTORE_HTTP + "/mstore/sync.do").header("ms", "upgrader")).execute().body();
     outJson(response, sync);
   }









   private void reModel(ModelEntity modelParent, String parentIds, int mangerRoleId, List<RoleModelEntity> roleModels, int parentId) {
     modelParent.setModelIsMenu((ObjectUtil.isNotNull(modelParent.getModelChildList()) && modelParent.getModelChildList().size() > 0) ?
         Integer.valueOf(1) : Integer.valueOf(0));

     modelParent.setModelModelId(parentId);
     modelParent.setModelDatetime(new Timestamp(System.currentTimeMillis()));
     modelParent.setModelParentIds(parentIds);
     ModelEntity modelParentEntity = this.modelBiz.getEntityByModelCode(modelParent.getModelCode());
     if (modelParentEntity == null) {
       this.modelBiz.saveEntity((BaseEntity)modelParent);
       RoleModelEntity roleModel = new RoleModelEntity();
       roleModel.setRoleId(mangerRoleId);
       roleModel.setModelId(modelParent.getModelId());
       roleModels.add(roleModel);
     } else {
       modelParent.setModelId(modelParentEntity.getModelId());
       this.modelBiz.updateEntity((BaseEntity)modelParent);
     }
     if (ObjectUtil.isNotNull(modelParent.getModelChildList()) && modelParent.getModelChildList().size() > 0) {
       for (ModelEntity modelEntity : modelParent.getModelChildList()) {
         reModel(modelEntity, StringUtils.isBlank(parentIds) ? (modelParent.getModelId() + "") : (parentIds + "," + modelParent.getModelId()), mangerRoleId, roleModels, modelParent.getModelId());
       }
     }
   }

   @ApiOperation("菜单导入接口")
   @PostMapping({"/import"})
   public void importMenu(String menuStr, HttpServletResponse response, HttpServletRequest request, @ApiIgnore ModelMap model) {
     if (StringUtils.isBlank(menuStr)) {
       outJson(response, null, false, getResString("err.empty", new String[] { getResString("menu") }));
       return;
     }
     this.modelBiz.jsonToModel(menuStr, getManagerBySession(request).getManagerRoleID());
     outJson(response, true);
   }









   @ResponseBody
   @RequestMapping({"/setup"})
   public void setup(HttpServletRequest request, HttpServletResponse response) throws ClientProtocolException, IOException {
     String cookie = BasicUtil.getString("cookie");
     String id = BasicUtil.getString("id");

     CloseableHttpClient httpclient = HttpClients.createDefault();
     String url = this.MS_MSTORE_HTTP + "/people/mstore/" + id + "/setup.do";
     HttpPost httpPost = new HttpPost(url);
     httpPost.setHeader("Host", this.MS_MSTORE_HOST);
     httpPost.setHeader("ms", "upgrader");
     httpPost.setHeader("accept", "");
     httpPost.setHeader("method", "setup");
     httpPost.setHeader("cookie", cookie);
     httpPost.setHeader("Content-Type", "text/html;charset=UTF-8");
     CloseableHttpResponse object = null;
     try {
       object = httpclient.execute((HttpUriRequest)httpPost);
     } catch (HttpHostConnectException e) {
       e.printStackTrace();
       outString(response, this.html.replace("{result/}", "false").replace("{message/}", "链接MStore失败，请回到主界面重试！"));
       return;
     }
     HttpEntity entity = object.getEntity();
     InputStream in = entity.getContent();

     String zipfile = "";
     String pack = "";
     String source = "";
     String menuStr = "";
     try {
       zipfile = object.getHeaders("fileName")[0].getValue();
       pack = object.getHeaders("pack")[0].getValue();
       source = object.getHeaders("source")[0].getValue();
     } catch (ArrayIndexOutOfBoundsException e) {
       outString(response, this.html.replace("{result/}", "false").replace("{message/}", "安装包获取失败，请回到主界面重试！"));

       return;
     }

     if (StringUtils.isNotBlank(pack)) {
       ResultJson result = new ResultJson();
       if (!checkModel(pack)) {
         if (source.equals("yes")) {
           outString(response, this.html.replace("{result/}", result.isResult() + "").replace("{message/}", "请先使用源码加载模块到系统！ <form  method=\"post\" target='_blank' action=" + this.MS_MSTORE_HTTP + "/people/mstore/down.do><input name=\"id\" style=\"display:none\" value=\"" + id + "\"/><input  type=\"submit\" value=\"下载源码\" style='    width: 104px;height: 34px;border: none;background-color: #0099ff;color: #ffffff;font-size: 14px;cursor: pointer;'/></form> "));


         }
         else {



           outString(response, this.html.replace("{result/}", result.isResult() + "").replace("{message/}", "请先加载源码或Maven依赖到系统！"));
         }

         return;
       }
     }

     if (StringUtil.isBlank(zipfile)) {
       return;
     }

     File classesFile = new File(ClassUtils.getDefaultClassLoader().getResource("").getPath());

     if (!FileUtil.exist(classesFile)) {
       FileUtil.mkdir(classesFile);
     }

     File zFile = new File(ClassUtils.getDefaultClassLoader().getResource("").getPath() + zipfile);
     try {
       FileOutputStream fout = new FileOutputStream(zFile);
       int l = -1;
       byte[] tmp = new byte[1024];
       while ((l = in.read(tmp)) != -1) {
         fout.write(tmp, 0, l);
       }

       fout.flush();
       fout.close();
     } finally {

       in.close();
     }
     httpclient.close();

     String entryName = "";
     List<File> sqlFiles = new ArrayList<>();
     List<File> files = new ArrayList<>();
     List<File> classFiles = new ArrayList<>();
     File menu = null;



     try {
       ZipFile zipFile = new ZipFile(zFile);


       File unzipFile = new File(ClassUtils.getDefaultClassLoader().getResource("").getPath());

       Enumeration<? extends ZipEntry> zipEnum = zipFile.getEntries();


       while (zipEnum.hasMoreElements()) {

         ZipEntry entry = zipEnum.nextElement();
         try {
           entryName = new String(entry.getName().getBytes("utf-8"));
           if (entryName.indexOf(".DS_Store") > -1) {
             continue;
           }

           if (entryName.indexOf(File.separator) > -1) {
             FileUtil.mkdir(entryName.substring(0, entryName.indexOf(File.separator)));
           }
         } catch (UnsupportedEncodingException e) {

           e.printStackTrace();
         }

         if (entry.isDirectory()) {
           (new File(unzipFile.getAbsolutePath() + File.separator + entryName)).mkdirs();
           continue;
         }
         try {
           File temp = new File(unzipFile.getAbsolutePath() + File.separator + entryName);
           InputStream input = zipFile.getInputStream(entry);
           OutputStream output = new FileOutputStream(temp);

           if (entryName.indexOf(".sql") > 0) {
             sqlFiles.add(new File(unzipFile.getAbsolutePath() + File.separator + entryName));
           }
           if (entryName.indexOf(".class") > 0) {
             classFiles.add(new File(unzipFile.getAbsolutePath() + File.separator + entryName));
           }
           if (entryName.indexOf(".json") > 0) {
             menu = new File(unzipFile.getAbsolutePath() + File.separator + entryName);
           }
           byte[] buffer = new byte[8192];
           int readLen = 0;
           while ((readLen = input.read(buffer, 0, 8192)) != -1) {
             output.write(buffer, 0, readLen);
           }
           output.flush();
           output.close();
           input.close();
           input = null;
           output = null;
         } catch (IOException e) {

           e.printStackTrace();
         }
       }


       zipFile.close();
     }
     catch (ZipException e1) {

       e1.printStackTrace();
     } catch (IOException e1) {

       e1.printStackTrace();
     }


     if (sqlFiles.size() > 0) {

       try {

         DruidDataSource dds = (DruidDataSource)SpringUtil.getBean(request.getServletContext(), "dataSource");
         ScriptRunner runner = new ScriptRunner((Connection)dds.getConnection());
         runner.setErrorLogWriter(null);
         runner.setLogWriter(null);
         runner.setSendFullScript(true);
         FileReader reader = null;
         for (File sql : sqlFiles) {
           reader = new FileReader(sql);
           runner.runScript(reader);
         }
         runner.closeConnection();
         reader.close();
       } catch (FileNotFoundException e) {

         e.printStackTrace();
       } catch (SQLException e) {
         e.printStackTrace();
       }
     }


     if (menu != null)
     {

       menuStr = FileUtil.readUtf8String(menu);
     }


     int mangerRoleId = getManagerBySession(request).getManagerRoleID();
     this.modelBiz.jsonToModel(menuStr, mangerRoleId);

     if (classFiles.size() > 0) {


       ClassLoader cload = new URLClassLoader(new URL[] { (new File(ClassUtils.getDefaultClassLoader().getResource("").getPath())).toURI().toURL() }, Thread.currentThread().getContextClassLoader());
       try {
         for (File cls : classFiles) {
           String className = cls.getPath().substring(cls.getPath().indexOf("classes") + 8, cls
               .getPath().length() - 6);
           className = className.replace("/", ".").replace("\\", ".");
           Class<?> clzss = Class.forName(className, true, cload);
           Object obj = clzss.newInstance();
           Method[] method = clzss.getDeclaredMethods();
           for (Method m : method) {
             if (m.toString().indexOf("public ") > -1) {
               try {
                 Object returnObj = m.invoke(obj, new Object[0]);
                 if (returnObj instanceof ResultJson) {
                   ResultJson result = (ResultJson)returnObj;
                   if (!result.isResult()) {
                     outString(response, this.html.replace("{result/}", "false")
                         .replace("{message/}", result.getResultMsg()));
                     return;
                   }
                 }
               } catch (IllegalArgumentException e) {

                 e.printStackTrace();
               } catch (InvocationTargetException e) {

                 e.printStackTrace();
               }

             }
           }

         }

       } catch (ClassNotFoundException e1) {

         e1.printStackTrace();
       } catch (InstantiationException e1) {

         e1.printStackTrace();
       } catch (IllegalAccessException e1) {

         e1.printStackTrace();
       }
     }



     zFile.delete();
     for (File f : files) {
       f.delete();
     }
     outString(response, this.html.replace("{result/}", "true").replace("{message/}", "安装升级成功！"));
   }


   private boolean checkModel(String className) {
     try {
       Class<?> clazz = Class.forName(className);
     } catch (ClassNotFoundException e) {
       e.printStackTrace();
       return false;
     }
     return true;
   }
 }


