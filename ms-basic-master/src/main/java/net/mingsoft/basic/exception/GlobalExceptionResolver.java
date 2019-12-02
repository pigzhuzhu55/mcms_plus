 package net.mingsoft.basic.exception;

 import com.alibaba.fastjson.JSONObject;
 import java.io.IOException;
 import java.io.PrintWriter;
 import java.io.StringWriter;
 import java.util.HashMap;
 import java.util.Map;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;
 import org.slf4j.Logger;
 import org.slf4j.LoggerFactory;
 import org.springframework.web.bind.annotation.ControllerAdvice;
 import org.springframework.web.bind.annotation.ExceptionHandler;
 import org.springframework.web.servlet.ModelAndView;
 import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;
























 @ControllerAdvice
 public class GlobalExceptionResolver
   extends DefaultHandlerExceptionResolver
 {
   protected final Logger LOG = LoggerFactory.getLogger(getClass());


   @ExceptionHandler({Exception.class})
   public ModelAndView defaultErrorHandler(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
     String url = request.getServletPath();
     StringWriter sw = new StringWriter();
     ex.printStackTrace(new PrintWriter(sw, true));
     String msg = sw.toString();
     Map<String, Object> map = new HashMap<>();
     int code = 200;
     map.put("url", url);
     map.put("msg", msg);
     map.put("exc", ex.getClass());
     map.put("cls", ex.getStackTrace()[0] + "");
     map.put("result", Boolean.valueOf(false));
     if (ex instanceof org.springframework.web.bind.MissingServletRequestParameterException) {
       code = 400;
     }
     if (ex instanceof org.apache.shiro.authz.UnauthorizedException) {
       code = 403;
     }
     if (ex instanceof org.springframework.web.servlet.NoHandlerFoundException) {
       code = 404;
     }
     if (ex instanceof org.springframework.web.HttpRequestMethodNotSupportedException) {
       code = 405;
     }
     if (ex instanceof BusinessException) {
       BusinessException be = (BusinessException)ex;
       map.put("msg", "业务异常");
       map.put("bizCode", be.getBizCode());
       map.put("bizMsg", be.getMessage());
     }
     ex.printStackTrace();
     if ((request.getHeader("accept") == null || request.getHeader("accept").indexOf("application/json") <= -1) && (request
       .getHeader("X-Requested-With") == null || request
       .getHeader("X-Requested-With").indexOf("XMLHttpRequest") <= -1)) {
       if (map.get("code") == null) {
         map.put("code", Integer.valueOf(500));
         return new ModelAndView("/error/index", map);
       }
       ModelAndView mav = doResolveException(request, response, handler, ex);
       if (mav == null) {
         map.put("code", "500");
         return new ModelAndView("/error/index", map);
       }

       return mav;
     }

     if (ex instanceof BusinessException) {
       map.remove("url");
       map.remove("cls");
       map.remove("exc");
     }

     try {
       if (map.get("code") == null) {
         map.put("code", Integer.valueOf(500));
       }
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


/* Location:              D:\User\Maven\repository\net\mingsoft\ms-basic\1.0.16\ms-basic-1.0.16.jar!\net\mingsoft\basic\exception\GlobalExceptionResolver.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */