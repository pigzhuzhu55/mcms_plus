 package net.mingsoft.base.resolver;

 import javax.servlet.http.HttpServletRequest;
 import org.springframework.web.multipart.commons.CommonsMultipartResolver;




























 public class MultipartResolver
   extends CommonsMultipartResolver
 {
   private String excludeUrls;
   private String[] excludeUrlArray;

   public String getExcludeUrls() { return this.excludeUrls; }

   public void setExcludeUrls(String excludeUrls) {
     this.excludeUrls = excludeUrls;
     this.excludeUrlArray = excludeUrls.split(",");
   }







   public boolean isMultipart(HttpServletRequest request) {
     for (String url : this.excludeUrlArray) {

       if (request.getRequestURI().contains(url)) {
         return false;
       }
     }
     return super.isMultipart(request);
   }
 }


