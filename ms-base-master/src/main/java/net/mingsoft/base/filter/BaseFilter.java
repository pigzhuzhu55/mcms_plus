 package net.mingsoft.base.filter;

 import java.io.IOException;
 import java.util.Enumeration;
 import javax.servlet.Filter;
 import javax.servlet.FilterChain;
 import javax.servlet.FilterConfig;
 import javax.servlet.ServletException;
 import javax.servlet.ServletRequest;
 import javax.servlet.ServletResponse;
 import javax.servlet.http.Cookie;
 import javax.servlet.http.HttpServletRequest;
 import org.slf4j.Logger;
 import org.slf4j.LoggerFactory;







































 public abstract class BaseFilter
   implements Filter
 {
   protected Logger logger = LoggerFactory.getLogger(getClass());










   public abstract void doFilter(ServletRequest paramServletRequest, ServletResponse paramServletResponse, FilterChain paramFilterChain) throws IOException, ServletException;









   public void log4jPrintOut(ServletRequest request, ServletResponse response) {
     HttpServletRequest httpRequest = (HttpServletRequest)request;

     if (this.logger.isDebugEnabled()) {
       StringBuffer sb = new StringBuffer();

       sb.append("Logging : \n");
       sb.append("--- Request URL: ---\n").append("\t").append(httpRequest
           .getRequestURL()).append("\n");

       Enumeration<String> names = httpRequest.getParameterNames();
       sb.append("--- Request Parameters: ---\n");
       while (names.hasMoreElements()) {
         String name = names.nextElement();
         sb.append("\t").append(name).append(":").append(httpRequest
             .getParameter(name)).append("\n");
       }
       names = httpRequest.getAttributeNames();
       sb.append("--- Request Attributes: ---\n");
       while (names.hasMoreElements()) {
         String name = names.nextElement();
         sb.append("\t").append(name).append(":").append(httpRequest
             .getAttribute(name)).append("\n");
       }
       names = httpRequest.getHeaderNames();
       sb.append("--- Request Heards: ---\n");
       while (names.hasMoreElements()) {
         String name = names.nextElement();
         sb.append("\t").append(name).append(":").append(httpRequest
             .getHeader(name)).append("\n");
       }

       names = httpRequest.getSession().getAttributeNames();
       sb.append("--- Request Sessions: ---\n");
       while (names.hasMoreElements()) {
         String name = names.nextElement();
         sb.append("\t").append(name).append(":").append(httpRequest
             .getSession().getAttribute(name)).append("\n");
       }


       Cookie[] cookies = httpRequest.getCookies();
       sb.append("--- Request Cookies: ---\n");
       if (cookies != null) {
         for (int i = 0; i < cookies.length; i++) {
           Cookie thisCookie = cookies[i];
           sb.append("\t").append(thisCookie.getName()).append(":")
             .append(thisCookie.getValue()).append("\n");
         }
       }
       this.logger.debug(sb.toString());
     }
   }

   public void init(FilterConfig filterConfig) throws ServletException {}

   public void destroy() {}
 }


