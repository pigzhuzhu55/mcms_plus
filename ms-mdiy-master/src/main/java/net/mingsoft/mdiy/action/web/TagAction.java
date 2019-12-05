 package net.mingsoft.mdiy.action.web;

 import freemarker.cache.FileTemplateLoader;
 import freemarker.cache.TemplateLoader;
 import freemarker.template.Configuration;
 import freemarker.template.Template;
 import freemarker.template.TemplateException;
 import io.swagger.annotations.Api;
 import io.swagger.annotations.ApiOperation;
 import java.io.File;
 import java.io.IOException;
 import java.io.StringWriter;
 import java.util.HashMap;
 import java.util.Map;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;
 import net.mingsoft.mdiy.action.BaseAction;
 import net.mingsoft.mdiy.biz.ITagBiz;
 import net.mingsoft.mdiy.parser.TagParser;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.stereotype.Controller;
 import org.springframework.ui.ModelMap;
 import org.springframework.web.bind.annotation.GetMapping;
 import org.springframework.web.bind.annotation.RequestMapping;
 import org.springframework.web.bind.annotation.ResponseBody;
 import springfox.documentation.annotations.ApiIgnore;














 @Api("标签管理接口")
 @Controller("webTagAction")
 @RequestMapping({"/mdiy/tag"})
 public class TagAction
   extends BaseAction
 {
   @Autowired
   private ITagBiz tagBiz;

   @ApiOperation("标签列表")
   @GetMapping({"/view"})
   @ResponseBody
   public void list(HttpServletResponse response, HttpServletRequest request, @ApiIgnore ModelMap model) {
     Map<Object, Object> map = new HashMap<>();





     String templateName = request.getRequestURI();
     System.out.println(templateName);

     try {
       FileTemplateLoader ft = new FileTemplateLoader(new File("/Users/killfen/dev-tools/apache-tomcat-7.0.62/wtpwebapps/mcms/templets/1/default"));
       Configuration cfg = new Configuration();
       cfg.setTemplateLoader((TemplateLoader)ft);
       try {
         Template template = cfg.getTemplate("index.htm", "UTF-8");
         StringWriter writer = new StringWriter();
         try {
           template.process(null, writer);
           TagParser tag = new TagParser(writer.toString());
           tag.rendering(map);
           outString(response, tag.getContent());
         } catch (TemplateException e) {
           e.printStackTrace();
         }
       } catch (IOException e) {
         e.printStackTrace();
       }

     } catch (IOException e) {

       e.printStackTrace();
     }
   }
 }


