 package net.mingsoft.mdiy.action.web;

 import freemarker.core.ParseException;
 import freemarker.template.MalformedTemplateNameException;
 import freemarker.template.TemplateNotFoundException;
 import io.swagger.annotations.Api;
 import io.swagger.annotations.ApiOperation;
 import java.io.IOException;
 import java.util.Map;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;
 import net.mingsoft.basic.util.BasicUtil;
 import net.mingsoft.mdiy.action.BaseAction;
 import net.mingsoft.mdiy.util.ParserUtil;
 import org.springframework.stereotype.Controller;
 import org.springframework.web.bind.annotation.GetMapping;
 import org.springframework.web.bind.annotation.PathVariable;
 import org.springframework.web.bind.annotation.RequestMapping;


















 @Api("自定义页码接口")
 @Controller("webDiyPath")
 @RequestMapping({"/mdiy/page"})
 public class PageAction
   extends BaseAction
 {
   @ApiOperation("自定义页码")
   @GetMapping({"/{diy}"})
   public void diy(@PathVariable("diy") String diy, HttpServletRequest req, HttpServletResponse resp) {
     Map<String, Object> map = BasicUtil.assemblyRequestMap();

     map.put("isDo", Boolean.valueOf(true));
     try {
       String content = ParserUtil.generate(ParserUtil.buildHtmlPath(diy), map, isMobileDevice(req));
       outString(resp, content);
     } catch (TemplateNotFoundException e1) {
       e1.printStackTrace();
     } catch (MalformedTemplateNameException e1) {
       e1.printStackTrace();
     } catch (ParseException e1) {
       e1.printStackTrace();
     } catch (IOException e1) {
       e1.printStackTrace();
     }
   }
 }


