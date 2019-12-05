 package net.mingsoft.mdiy.util;

 import cn.hutool.core.io.FileUtil;
 import freemarker.cache.FileTemplateLoader;
 import freemarker.cache.TemplateLoader;
 import freemarker.core.ParseException;
 import freemarker.template.Configuration;
 import freemarker.template.MalformedTemplateNameException;
 import freemarker.template.Template;
 import freemarker.template.TemplateException;
 import freemarker.template.TemplateNotFoundException;
 import java.io.File;
 import java.io.IOException;
 import java.io.StringWriter;
 import java.util.Map;
 import net.mingsoft.basic.util.BasicUtil;
 import net.mingsoft.mdiy.parser.TagParser;













































































 public class ParserUtil
 {
   public static final String TEMPLATES = "templets";
   public static final String HTML = "html";
   public static final String MOBILE = "m";
   public static final String INDEX = "index";
   public static final String HTML_SUFFIX = ".html";
   public static final String PAGE_LIST = "list-";
   public static final String HTM_SUFFIX = ".htm";
   public static final String IS_DO = "isDo";
   public static final String URL = "url";
   public static final String COLUMN = "column";
   public static final String ID = "id";
   public static final String TABLE_NAME = "tableName";
   public static final String MODEL_NAME = "modelName";
   public static final String DO_SUFFIX = ".do";
   public static final String PAGE = "pageTag";
   public static final String PAGE_NO = "pageNo";
   public static final String SIZE = "size";
   public static final String TYPE_ID = "typeid";
   public static final String APP_ID = "appId";
   public static boolean IS_SINGLE = true;
   public static Configuration cfg = new Configuration();

   public static FileTemplateLoader ftl = null;









   public static String buildTempletPath() { return buildTempletPath(null); }







   public static String buildTempletPath(String path) {
     return BasicUtil.getRealPath("templets") + File.separator + BasicUtil.getAppId() + File.separator +
       BasicUtil.getApp().getAppStyle() + ((path != null) ? (File.separator + path) : "");
   }







   public static String buildMobileHtmlPath(String path) { return BasicUtil.getRealPath("html") + File.separator + BasicUtil.getAppId() + File.separator + "m" + File.separator + path + ".html"; }









   public static String buildHtmlPath(String path) { return BasicUtil.getRealPath("html") + File.separator + BasicUtil.getAppId() + File.separator + path + ".html"; }








   public static boolean hasMobileFile(String path) {
     if (FileUtil.exist(buildTempletPath("m" + File.separator + path))) {
       return true;
     }
     return false;
   }


















   public static String generate(String templatePath, Map<String, Object> params, boolean isMobile) throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException {
     if (IS_SINGLE) {
       params.put("url", BasicUtil.getUrl());
     }

     params.put("html", "html");

     params.put("appId", Integer.valueOf(BasicUtil.getAppId()));
     if (ftl == null || !buildTempletPath().equals(ftl.baseDir.getPath())) {
       ftl = new FileTemplateLoader(new File(buildTempletPath()));
       cfg.setNumberFormat("#");
       cfg.setTemplateLoader((TemplateLoader)ftl);
     }

     Template template = cfg.getTemplate((isMobile ? (BasicUtil.getApp().getAppMobileStyle() + File.separator) : "") + templatePath, "utf-8");

     StringWriter writer = new StringWriter();
     TagParser tag = null;
     String content = null;
     try {
       template.process(null, writer);
       tag = new TagParser(writer.toString(), params);
       content = tag.rendering();
       return content;
     } catch (TemplateException e) {
       e.printStackTrace();

       return null;
     }
   }











   public static String read(String templatePath, boolean isMobile) throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException {
     if (ftl == null || !buildTempletPath().equals(ftl.baseDir.getPath())) {
       ftl = new FileTemplateLoader(new File(buildTempletPath()));
       cfg.setNumberFormat("#");
       cfg.setTemplateLoader((TemplateLoader)ftl);
     }

     Template template = cfg.getTemplate((isMobile ? (BasicUtil.getApp().getAppMobileStyle() + File.separator) : "") + templatePath, "utf-8");

     StringWriter writer = new StringWriter();
     TagParser tag = null;
     String content = null;
     try {
       template.process(null, writer);
       return writer.toString();
     } catch (TemplateException e) {
       e.printStackTrace();

       return null;
     }
   }
 }


