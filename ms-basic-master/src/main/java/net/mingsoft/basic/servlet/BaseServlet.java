 package net.mingsoft.basic.servlet;

 import com.alibaba.fastjson.JSONObject;
 import java.io.BufferedReader;
 import java.io.File;
 import java.io.IOException;
 import java.io.InputStream;
 import java.io.InputStreamReader;
 import java.io.PrintWriter;
 import java.util.HashMap;
 import java.util.Iterator;
 import java.util.Map;
 import java.util.ResourceBundle;
 import javax.servlet.ServletContext;
 import javax.servlet.ServletException;
 import javax.servlet.http.Cookie;
 import javax.servlet.http.HttpServlet;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;
 import net.mingsoft.base.constant.e.BaseEnum;
 import net.mingsoft.base.constant.e.BaseSessionEnum;
 import net.mingsoft.base.entity.ResultJson;
 import net.mingsoft.basic.biz.IAppBiz;
 import net.mingsoft.basic.constant.e.CookieConstEnum;
 import net.mingsoft.basic.constant.e.SessionConstEnum;
 import net.mingsoft.basic.entity.AppEntity;
 import org.apache.commons.lang3.StringUtils;
 import org.apache.commons.lang3.math.NumberUtils;
 import org.slf4j.Logger;
 import org.slf4j.LoggerFactory;
 import org.springframework.web.context.support.WebApplicationContextUtils;





































 @Deprecated
 public abstract class BaseServlet
   extends HttpServlet
 {
   @Deprecated
   public Logger logger = LoggerFactory.getLogger(getClass());

   public Logger LOG = LoggerFactory.getLogger(getClass());





   public void destroy() { super.destroy(); }




   private static String[] mobileGateWayHeaders = new String[] { "ZXWAP", "chinamobile.com", "monternet.com", "infoX", "XMS 724Solutions HTG", "wap.lizongbo.com", "Bytemobile" };

















   private static String[] pcHeaders = new String[] { "Windows 98", "Windows ME", "Windows 2000", "Windows XP", "Windows NT", "Ubuntu" };


   private static String[] mobileUserAgents = new String[] { "Nokia", "SAMSUNG", "MIDP-2", "CLDC1.1", "SymbianOS", "MAUI", "UNTRUSTED/1.0", "Windows CE", "iPhone", "iPad", "Android", "BlackBerry", "UCWEB", "ucweb", "BREW", "J2ME", "YULONG", "YuLong", "COOLPAD", "TIANYU", "TY-", "K-Touch", "Haier", "DOPOD", "Lenovo", "LENOVO", "HUAQIN", "AIGO-", "CTC/1.0", "CTC/2.0", "CMCC", "DAXIAN", "MOT-", "SonyEricsson", "GIONEE", "HTC", "ZTE", "HUAWEI", "webOS", "GoBrowser", "IEMobile", "WAP2.0" };














































































   public void init() throws ServletException { super.init(); }










   protected void sendHtml(HttpServletRequest request, HttpServletResponse response, String html) {
     try {
       PrintWriter out = response.getWriter();
       response.setContentType("text/html");
       response.setCharacterEncoding("utf-8".toString());
       out.write(html.toString());
       out.close();
     } catch (IOException e) {
       e.printStackTrace();
     }
   }








   public String getIpAddr(HttpServletRequest request) {
     String ip = request.getHeader("x-forwarded-for");
     if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
       ip = request.getHeader("Proxy-Client-IP");
     }
     if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
       ip = request.getHeader("WL-Proxy-Client-IP");
     }
     if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
       ip = request.getRemoteAddr();
     }
     return ip;
   }






   public String readStreamParameter(HttpServletRequest request) {
     StringBuilder buffer = new StringBuilder();
     BufferedReader reader = null;
     try {
       reader = new BufferedReader(new InputStreamReader((InputStream)request.getInputStream(), "utf-8"));
       String line = null;
       while ((line = reader.readLine()) != null) {
         buffer.append(line);
       }
     } catch (Exception e) {
       e.printStackTrace();
     } finally {
       if (null != reader) {
         try {
           reader.close();
         } catch (IOException e) {
           e.printStackTrace();
         }
       }
     }
     return buffer.toString();
   }






   protected void outString(HttpServletResponse response, Object dataStr) {
     try {
       response.setContentType("text/html");
       response.setCharacterEncoding("utf-8".toString());
       PrintWriter out = response.getWriter();
       out.print(dataStr);
       out.flush();
       out.close();
     } catch (IOException e) {
       e.printStackTrace();
     }
   }






   protected void outJson(HttpServletResponse response, Object jsonDataStr) {
     try {
       response.setContentType("application/json;charset=utf-8");
       PrintWriter out = response.getWriter();
       out.print(jsonDataStr);
       out.flush();
       out.close();
     } catch (IOException e) {
       e.printStackTrace();
     }
   }







   protected void setSession(HttpServletRequest request, SessionConstEnum key, Object obj) {
     if (StringUtils.isEmpty(obj.toString())) {
       return;
     }
     request.getSession().setAttribute(key.toString(), obj);
   }








   protected Object getSession(HttpServletRequest request, SessionConstEnum key) { return request.getSession().getAttribute(key.toString()); }







   protected void setSession(HttpServletRequest request, BaseSessionEnum key, Object obj) {
     if (StringUtils.isEmpty(obj.toString())) {
       return;
     }
     request.getSession().setAttribute(key.toString(), obj);
   }








   protected Object getSession(HttpServletRequest request, BaseSessionEnum key) { return request.getSession().getAttribute(key.toString()); }









   protected void removeSession(HttpServletRequest request, BaseSessionEnum key) { request.getSession().removeAttribute(key.toString()); }









   protected void setCookie(HttpServletRequest request, HttpServletResponse response, CookieConstEnum key, Object value) {
     request.getSession().setAttribute(key.toString(), value);
     Cookie cookie = new Cookie(key.name(), (String)value);
     cookie.setPath("/");
     cookie.setValue((String)value);
     response.addCookie(cookie);
   }








   protected String getUrl(HttpServletRequest request) {
     String path = request.getContextPath();
     String basePath = request.getScheme() + "://" + request.getServerName();
     if (request.getServerPort() == 80) {
       basePath = basePath + path;
     } else {
       basePath = basePath + ":" + request.getServerPort() + path;
     }
     return basePath;
   }






   protected String getDomain(HttpServletRequest request) {
     String path = request.getContextPath();
     String domain = request.getServerName();
     if (request.getServerPort() == 80) {
       domain = domain + path;
     } else {
       domain = domain + ":" + request.getServerPort() + path;
     }
     return domain;
   }








   protected void outJson(HttpServletResponse resp, BaseEnum code, boolean flag, String msg) {
     try {
       ResultJson result = new ResultJson();
       if (code != null) {
         result.setCode(code.toString());
       }
       result.setResult(flag);
       result.setResultMsg(msg);
       PrintWriter out = resp.getWriter();
       out.print(JSONObject.toJSON(result));
       out.flush();
       out.close();
     } catch (IOException e) {
       e.printStackTrace();
     }
   }










   protected void outJson(HttpServletResponse resp, BaseEnum code, boolean flag, String msg, String data) {
     try {
       ResultJson result = new ResultJson();
       if (code != null) {
         result.setCode(code.toString());
       }
       result.setResult(flag);
       result.setResultMsg(msg);
       result.setResultData(data);
       PrintWriter out = resp.getWriter();
       out.print(JSONObject.toJSON(result));
       out.flush();
       out.close();
     } catch (IOException e) {
       e.printStackTrace();
     }
   }







   protected String getString(HttpServletRequest request, String fieldName) {
     if (StringUtils.isEmpty(request.getParameter(fieldName))) {
       return null;
     }
     return request.getParameter(fieldName);
   }







   protected int getInt(HttpServletRequest request, String fieldName) {
     if (!NumberUtils.isNumber(request.getParameter(fieldName))) {
       return 0;
     }
     return Integer.parseInt(request.getParameter(fieldName));
   }








   protected int getInt(HttpServletRequest request, String fieldName, int defaultValue) {
     if (!NumberUtils.isNumber(request.getParameter(fieldName))) {
       return defaultValue;
     }
     return Integer.parseInt(request.getParameter(fieldName));
   }








   protected Object getBean(ServletContext sc, String beanName) { return WebApplicationContextUtils.getWebApplicationContext(sc).getBean(beanName); }









   protected Object getBean(ServletContext sc, Class cls) { return WebApplicationContextUtils.getWebApplicationContext(sc).getBean(cls); }








   protected String getRealPath(HttpServletRequest request, String filePath) {
     if (filePath != null) {
       return request.getServletContext().getRealPath(File.separator) + File.separator + filePath;
     }
     return request.getServletContext().getRealPath(File.separator);
   }









   protected String getResString(String key, ResourceBundle rb) { return rb.getString(key); }








   protected String getResString(String key, ResourceBundle rb, String... fullStrs) {
     String temp = rb.getString(key);
     for (int i = 0; i < fullStrs.length; i++) {
       temp = temp.replace("{" + i + "}", fullStrs[i]);
     }
     return temp;
   }









   protected AppEntity getApp(HttpServletRequest request, IAppBiz appBiz) {
     AppEntity website = appBiz.getByUrl(getUrl(request));
     if (website != null) {
       return website;
     }
     return null;
   }








   public boolean isMobileDevice(HttpServletRequest request) {
     boolean b = false;
     boolean pcFlag = false;
     boolean mobileFlag = false;
     String via = request.getHeader("Via");
     String userAgent = request.getHeader("user-agent");
     int i = 0;
     for (; via != null && !via.trim().equals("") && i < mobileGateWayHeaders.length; i++) {
       if (via.contains(mobileGateWayHeaders[i])) {
         mobileFlag = true;
         break;
       }
     }
      i = 0;
     for (; !mobileFlag && userAgent != null && !userAgent.trim().equals("") && i < mobileUserAgents.length; i++) {
       if (userAgent.contains(mobileUserAgents[i])) {
         mobileFlag = true;
         break;
       }
     }
      i = 0;
     for (; userAgent != null && !userAgent.trim().equals("") && i < pcHeaders.length; i++) {
       if (userAgent.contains(pcHeaders[i])) {
         pcFlag = true;
         break;
       }
     }
     if (mobileFlag == true && !pcFlag) {
       b = true;
     }
     return b;
   }









   protected Map<String, Object> assemblyRequestMap(HttpServletRequest request) {
     Map<String, Object> params = new HashMap<>();
     Map<String, String[]> map = request.getParameterMap();
     Iterator<String> key = map.keySet().iterator();
     while (key.hasNext()) {
       String k = key.next();
       String[] value = map.get(k);

       if (value.length == 1) {
         String temp = null;
         if (!StringUtils.isEmpty(value[0])) {
           temp = value[0];
         }
         params.put(k, temp);
         request.setAttribute(k, temp); continue;
       }  if (value.length == 0) {
         params.put(k, null);
         request.setAttribute(k, null); continue;
       }  if (value.length > 1) {
         params.put(k, value);
         request.setAttribute(k, value);
       }
     }
     return params;
   }
 }


/* Location:              D:\User\Maven\repository\net\mingsoft\ms-basic\1.0.16\ms-basic-1.0.16.jar!\net\mingsoft\basic\servlet\BaseServlet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */