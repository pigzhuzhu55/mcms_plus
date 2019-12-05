 package net.mingsoft.basic.exception;

 import com.alibaba.fastjson.JSONObject;
 import java.io.IOException;
 import java.io.PrintWriter;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;
 import org.slf4j.Logger;
 import org.slf4j.LoggerFactory;
 import org.springframework.boot.web.servlet.error.ErrorController;
 import org.springframework.stereotype.Controller;
 import org.springframework.ui.ModelMap;
 import org.springframework.web.bind.annotation.RequestMapping;











 @Controller
 public class NotFoundException
   implements ErrorController
 {
   protected final Logger LOG = LoggerFactory.getLogger(getClass());



   public String getErrorPath() { return "/error"; }


   @RequestMapping({"/error"})
   public String error(ModelMap map, HttpServletRequest request, HttpServletResponse response) {
     map.put("code", Integer.valueOf(404));
     if ((request.getHeader("accept") == null || request.getHeader("accept").indexOf("application/json") <= -1) && (request
       .getHeader("X-Requested-With") == null || request
       .getHeader("X-Requested-With").indexOf("XMLHttpRequest") <= -1)) {
       return "/error/index";
     }

     try {
       response.setContentType("application/json;charset=UTF-8");
       PrintWriter writer = response.getWriter();
       writer.write(JSONObject.toJSONString(map));
       writer.flush();
       writer.close();
     } catch (IOException e) {
       e.printStackTrace();
     }

     return null;
   }
 }


