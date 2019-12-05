 package net.mingsoft.basic.interceptor;

 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;
 import net.mingsoft.basic.constant.e.SessionConstEnum;
 import net.mingsoft.basic.util.BasicUtil;
 import net.mingsoft.basic.util.SpringUtil;
 import org.apache.commons.lang3.math.NumberUtils;
 import org.springframework.beans.factory.annotation.Value;
 import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

















































 public class ActionInterceptor
   extends HandlerInterceptorAdapter
 {
   @Value("${ms.manager.path}")
   private String managerPath;

   public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
     String modelId = request.getParameter("modelId");
     if (NumberUtils.isNumber(modelId)) {
       request.setAttribute("modelId", modelId);
       request.getSession().setAttribute(SessionConstEnum.MODEL_ID_SESSION.toString(), modelId);
       request.getSession().setAttribute(SessionConstEnum.MODEL_TITLE_SESSION.toString(), request.getParameter("modelTitle"));
     }

     String base = request.getScheme() + "://" + request.getServerName() + ((request.getServerPort() == 80) ? "" : (":" + request.getServerPort()));
     String contextPath = request.getServletContext().getContextPath();

     request.setAttribute("base", contextPath);

     request.setAttribute("managerPath", contextPath + this.managerPath);

     request.setAttribute("basePath", base + contextPath);

     request.setAttribute("params", BasicUtil.assemblyRequestUrlParams());
     SpringUtil.setResponse(response);
     SpringUtil.setResquest(request);
     return true;
   }
 }


