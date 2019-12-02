 package net.mingsoft.basic.util;

 import com.alibaba.fastjson.JSONObject;
 import java.util.Enumeration;
 import java.util.Map;
 import javax.servlet.ServletRequest;
 import javax.servlet.http.HttpServletRequest;
 import org.springframework.http.HttpEntity;
 import org.springframework.http.HttpHeaders;
 import org.springframework.http.HttpMethod;
 import org.springframework.http.ResponseEntity;
 import org.springframework.util.MultiValueMap;
 import org.springframework.web.client.RestTemplate;































 public class RestTemplateUtil
 {
   private static RestTemplate restTemplate = new RestTemplate();








   public static String post(ServletRequest req, String url, Map<String, ?> params) {
     ResponseEntity<String> rss = request(req, url, HttpMethod.POST, params);
     return (String)rss.getBody();
   }








   public static String get(ServletRequest req, String url, Map<String, ?> params) {
     ResponseEntity<String> rss = request(req, url, HttpMethod.GET, params);
     return (String)rss.getBody();
   }








   public static String delete(ServletRequest req, String url, Map<String, ?> params) {
     ResponseEntity<String> rss = request(req, url, HttpMethod.DELETE, params);
     return (String)rss.getBody();
   }








   public static String put(ServletRequest req, String url, Map<String, ?> params) {
     ResponseEntity<String> rss = request(req, url, HttpMethod.PUT, params);
     return (String)rss.getBody();
   }









   private static ResponseEntity<String> request(ServletRequest req, String url, HttpMethod method, Map<String, ?> params) {
     HttpServletRequest request = (HttpServletRequest)req;

     HttpHeaders requestHeaders = new HttpHeaders();
     Enumeration<String> headerNames = request.getHeaderNames();
     while (headerNames.hasMoreElements()) {
       String key = headerNames.nextElement();
       String value = request.getHeader(key);
       requestHeaders.add(key, value);
     }
     HttpEntity<String> requestEntity = new HttpEntity((params != null) ? JSONObject.toJSONString(params) : null, (MultiValueMap)requestHeaders);
     ResponseEntity<String> rss = restTemplate.exchange(url, method, requestEntity, String.class, new Object[0]);
     return rss;
   }
 }


/* Location:              D:\User\Maven\repository\net\mingsoft\ms-basic\1.0.16\ms-basic-1.0.16.jar!\net\mingsoft\basi\\util\RestTemplateUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */