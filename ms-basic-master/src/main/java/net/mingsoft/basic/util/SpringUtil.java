 package net.mingsoft.basic.util;

 import javax.servlet.ServletContext;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;
 import org.springframework.beans.BeansException;
 import org.springframework.context.ApplicationContext;
 import org.springframework.context.ApplicationContextAware;
 import org.springframework.stereotype.Component;
 import org.springframework.web.context.request.RequestContextHolder;
 import org.springframework.web.context.request.ServletRequestAttributes;




















 @Component
 public class SpringUtil
   implements ApplicationContextAware
 {
   private static ThreadLocal<HttpServletRequest> requestLocal = new ThreadLocal<>();
   private static ThreadLocal<HttpServletResponse> responseLocal = new ThreadLocal<>();



   private static ApplicationContext applicationContext;



   public static HttpServletRequest getRequest() {
     try {
       return ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
     } catch (Exception e) {
       return null;
     }
   }











   public static Object getBean(ServletContext sc, String beanName) { return getApplicationContext().getBean(beanName); }












   public static Object getBean(String beanName) { return getApplicationContext().getBean(beanName); }












   public static <T> T getBean(Class<T> cls) { return (T)getApplicationContext().getBean(cls); }



   public static void setResponse(HttpServletResponse response) { responseLocal.set(response); }



   public static void setResquest(HttpServletRequest request) { requestLocal.set(request); }



   public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
     if (SpringUtil.applicationContext == null) {
       SpringUtil.applicationContext = applicationContext;
     }
   }


   public static ApplicationContext getApplicationContext() { return applicationContext; }
 }


/* Location:              D:\User\Maven\repository\net\mingsoft\ms-basic\1.0.16\ms-basic-1.0.16.jar!\net\mingsoft\basi\\util\SpringUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */