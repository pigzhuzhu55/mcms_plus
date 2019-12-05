 package net.mingsoft.basic.util;

 import com.github.pagehelper.PageHelper;
 import com.github.pagehelper.PageInfo;
 import java.io.File;
 import java.io.IOException;
 import java.io.InputStreamReader;
 import java.io.LineNumberReader;
 import java.net.InetAddress;
 import java.net.UnknownHostException;
 import java.util.Arrays;
 import java.util.Enumeration;
 import java.util.HashMap;
 import java.util.Iterator;
 import java.util.List;
 import java.util.Map;
 import java.util.ResourceBundle;
 import javax.servlet.http.Cookie;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;
 import net.mingsoft.base.constant.e.BaseCookieEnum;
 import net.mingsoft.base.constant.e.BaseEnum;
 import net.mingsoft.base.constant.e.BaseSessionEnum;
 import net.mingsoft.basic.biz.IAppBiz;
 import net.mingsoft.basic.biz.IModelBiz;
 import net.mingsoft.basic.entity.AppEntity;
 import net.mingsoft.basic.entity.ModelEntity;
 import org.apache.commons.lang3.ArrayUtils;
 import org.apache.commons.lang3.StringUtils;
 import org.apache.commons.lang3.math.NumberUtils;
 import org.springframework.util.ClassUtils;

























 public class BasicUtil
 {
   private static String[] mobileGateWayHeaders = new String[] { "ZXWAP", "chinamobile.com", "monternet.com", "infoX", "XMS 724Solutions HTG", "wap.lizongbo.com", "Bytemobile" };



















   private static String[] pcHeaders = new String[] { "Windows 98", "Windows ME", "Windows 2000", "Windows XP", "Windows NT", "Ubuntu" };


   private static String[] mobileUserAgents = new String[] { "Nokia", "SAMSUNG", "MIDP-2", "CLDC1.1", "SymbianOS", "MAUI", "UNTRUSTED/1.0", "Windows CE", "iPhone", "iPad", "Android", "BlackBerry", "UCWEB", "ucweb", "BREW", "J2ME", "YULONG", "YuLong", "COOLPAD", "TIANYU", "TY-", "K-Touch", "Haier", "DOPOD", "Lenovo", "LENOVO", "HUAQIN", "AIGO-", "CTC/1.0", "CTC/2.0", "CMCC", "DAXIAN", "MOT-", "SonyEricsson", "GIONEE", "HTC", "ZTE", "HUAWEI", "webOS", "GoBrowser", "IEMobile", "WAP2.0" };













   @Deprecated
   private static final String PAGE_NO = "pageNo";













   private static final String PAGE_NUMBER = "pageNumber";













   private static final String PAGE_SIZE = "pageSize";












   private static final String PAGE = "page";












   private static final String IDS = "ids";













   public static int getModelCodeId(BaseEnum code) {
     IModelBiz modelBiz = (IModelBiz)SpringUtil.getBean(SpringUtil.getRequest().getServletContext(), "modelBiz");
     ModelEntity model = modelBiz.getEntityByModelCode(code);
     if (model != null) {
       return model.getModelId();
     }
     return 0;
   }









   public static int getModelCodeId(String code) {
     IModelBiz modelBiz = (IModelBiz)SpringUtil.getBean(SpringUtil.getRequest().getServletContext(), "modelBiz");
     ModelEntity model = modelBiz.getEntityByModelCode(code);
     if (model != null) {
       return model.getModelId();
     }
     return 0;
   }






   public static int getModelCodeId() {
     String modelCode = getString("modelCode", "");

     IModelBiz modelBiz = (IModelBiz)SpringUtil.getBean(SpringUtil.getRequest().getServletContext(), "modelBiz");
     ModelEntity model = modelBiz.getEntityByModelCode(modelCode);
     if (model != null) {
       return model.getModelId();
     }
     return 0;
   }







   public static int getModeld() { return getInt("modelId", 0).intValue(); }









   public static int getAppId() {
     AppEntity app = getApp();
     if (app != null) {
       return app.getAppId();
     }
     return 0;
   }










   public static AppEntity getApp() {
     IAppBiz websiteBiz = SpringUtil.getBean(IAppBiz.class);
     AppEntity website = websiteBiz.getByUrl(getDomain());
     website.setAppUrl(getUrl());
     return website;
   }








   public static String getUrl() {
     HttpServletRequest request = SpringUtil.getRequest();
     String path = request.getContextPath();
     String basePath = request.getScheme() + "://" + request.getServerName();
     if (request.getServerPort() == 80) {
       basePath = basePath + path;
     } else {
       basePath = basePath + ":" + request.getServerPort() + path;
     }
     return basePath;
   }







   public String getMACAddress(String ip) {
     String str = "";
     String macAddress = "";
     try {
       Process p = Runtime.getRuntime().exec("nbtstat -A " + ip);
       InputStreamReader ir = new InputStreamReader(p.getInputStream());
       LineNumberReader input = new LineNumberReader(ir);
       for (int i = 1; i < 100; i++) {
         str = input.readLine();
         if (str != null &&
           str.indexOf("MAC Address") > 1) {
           macAddress = str.substring(str.indexOf("MAC Address") + 14, str.length());

           break;
         }
       }
     } catch (IOException e) {
       e.printStackTrace(System.out);
     }
     return macAddress;
   }






   public static String getIp() {
     HttpServletRequest request = SpringUtil.getRequest();
     String ipAddress = null;

     ipAddress = request.getHeader("x-forwarded-for");
     if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
       ipAddress = request.getHeader("Proxy-Client-IP");
     }
     if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
       ipAddress = request.getHeader("WL-Proxy-Client-IP");
     }
     if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
       ipAddress = request.getRemoteAddr();
       if (ipAddress.equals("127.0.0.1")) {

         InetAddress inet = null;
         try {
           inet = InetAddress.getLocalHost();
         } catch (UnknownHostException e) {
           e.printStackTrace();
         }
         ipAddress = inet.getHostAddress();
       }
     }



     if (ipAddress != null && ipAddress.length() > 15)
     {
       if (ipAddress.indexOf(",") > 0) {
         ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
       }
     }
     return ipAddress;
   }








   public static String getDomain() {
     String path = SpringUtil.getRequest().getContextPath();
     String domain = SpringUtil.getRequest().getServerName();
     if (SpringUtil.getRequest().getServerPort() == 80) {
       domain = domain + path;
     } else {
       domain = domain + ":" + SpringUtil.getRequest().getServerPort() + path;
     }
     return domain;
   }





















   public static void startPage() {
     int pageNo = getInt("pageNo", 1).intValue();
     int pageNumber = getInt("pageNumber", 1).intValue();
     int _pageNo = pageNo;
     if (pageNumber > pageNo) {
       _pageNo = pageNumber;
     }
     PageHelper.startPage(_pageNo, getInt("pageSize", 10).intValue());
   }







   public static int getPageSzie() { return getInt("pageSize", 10).intValue(); }








   public static int getPageNo() { return getInt("pageNo", 1).intValue(); }









   public static void startPage(boolean count) { startPage(getInt("pageNo", 1).intValue(), getInt("pageSize", 10).intValue(), count); }













   public static void startPage(int pageNo, int pageSize, boolean count) { PageHelper.startPage(getInt("pageNo", pageNo).intValue(), getInt("pageSize", pageSize).intValue(), count); }










   public static void orderBy(String orderBy, String order) {
     if (!order.equalsIgnoreCase("DESC") && !order.equalsIgnoreCase("ASC")) {
       order = "DESC";
     }
     PageHelper.orderBy(orderBy + " " + order);
   }



   public static PageInfo endPage(List list, String name) {
     PageInfo page = new PageInfo(list);
     SpringUtil.getRequest().setAttribute("params",
         assemblyRequestUrlParams(new String[] { "pageNo" }));
     SpringUtil.getRequest().setAttribute(name, page);
     return page;
   }



   public static PageInfo endPage(List list) { return endPage(list, "page"); }










   public static Boolean getBoolean(String param) {
     String value = SpringUtil.getRequest().getParameter(param);
     try {
       return Boolean.valueOf(Boolean.parseBoolean(value));
     } catch (Exception e) {
       return Boolean.valueOf(false);
     }
   }











   public static Integer getInt(String param, int def) {
     String value = SpringUtil.getRequest().getParameter(param);
     if (NumberUtils.isNumber(value)) {
       return Integer.valueOf(Integer.parseInt(value));
     }
     return Integer.valueOf(def);
   }



   public static Integer getInt(String param) { return getInt(param, 0); }













   public static String getString(String param, String def) {
     String value = SpringUtil.getRequest().getParameter(param);
     if (StringUtils.isEmpty(value)) {
       value = def;
     }
     return value;
   }











   public static String getString(String param) { return getString(param, ""); }









   public static int[] getInts(String param) {
     String[] value = SpringUtil.getRequest().getParameterValues(param);
     if (ArrayUtils.isNotEmpty((Object[])value)) {
       try {
         return StringUtil.stringsToInts(value);
       } catch (NumberFormatException e) {
         return null;
       }
     }
     return null;
   }











   public static int[] getInts(String param, String split) {
     if (getInts(param) != null) {
       return getInts(param);
     }
     String value = SpringUtil.getRequest().getParameter(param);
     if (StringUtils.isEmpty(value)) {
       return null;
     }
     if (ArrayUtils.isNotEmpty((Object[])value.split(split))) {
       return StringUtil.stringsToInts(value.split(split));
     }
     return null;
   }





   public static int[] getIds() { return getInts("ids"); }










   public static Map<String, Object> assemblyRequestMap() {
     HttpServletRequest request = SpringUtil.getRequest();
     Map<String, Object> params = new HashMap<>();
     Map<String, String[]> map = request.getParameterMap();
     Iterator<String> key = map.keySet().iterator();
     while (key.hasNext()) {
       String k = ((String)key.next()).replace(".", "_");
       String[] value = null;
       if (!StringUtil.isBlank(map.get(k))) {
         try {
           value = map.get(k);
         } catch (ClassCastException e) {
           value = new String[] { (new StringBuilder()).append(map.get(k)).append("").toString() };
         }
       }


       if (value == null) {
         params.put(k, null);
         request.setAttribute(k, null); continue;
       }  if (value.length == 1) {
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










   public static String assemblyRequestUrlParams() { return assemblyRequestUrlParams(null); }











   public static String assemblyRequestUrlParams(String[] filter) {
     Map<String, String[]> map = SpringUtil.getRequest().getParameterMap();
     Iterator<String> key = map.keySet().iterator();
     StringBuffer sb = new StringBuffer();

     while (key.hasNext()) {

       String k = key.next();

       if (filter != null && Arrays.asList(filter).contains(k)) {
         continue;
       }
       if (!StringUtil.isBlank(map.get(k))) {
         String[] value = null;
         try {
           value = map.get(k);
         } catch (ClassCastException e) {
           value = new String[] { (new StringBuilder()).append(map.get(k)).append("").toString() };
         }

         if (value.length == 1) {
           String temp = "";
           if (!StringUtils.isEmpty(value[0])) {
             temp = value[0];
           }
           sb.append(k).append("=").append(temp).append("&"); continue;
         }  if (value.length > 1) {
           sb.append(k).append("=").append(value).append("&");
         }
       }
     }

     if (sb.length() > 0)
     {
       return sb.substring(0, sb.length() - 1);
     }
     return sb.toString();
   }








   public static void removeUrlParams(String[] fitlers) { SpringUtil.getRequest().setAttribute("params", assemblyRequestUrlParams(fitlers)); }












   public static Object getSession(BaseSessionEnum key) { return SpringUtil.getRequest().getSession().getAttribute(key.toString()); }












   public static Object getSession(String key) { return SpringUtil.getRequest().getSession().getAttribute(key); }











   public static void setSession(BaseSessionEnum key, Object value) { SpringUtil.getRequest().getSession().setAttribute(key.toString(), value); }











   public static void setSession(String key, Object value) { SpringUtil.getRequest().getSession().setAttribute(key, value); }










   public static void removeSession(BaseSessionEnum key) { SpringUtil.getRequest().getSession().removeAttribute(key.toString()); }









   public static String getRealPath(String filePath) {
     String classPath = getClassPath(filePath);
     if (!classPath.startsWith("file")) {

       HttpServletRequest request = SpringUtil.getRequest();
       String path = request.getServletContext().getRealPath("/");

       if(path==null|| path.indexOf("Temp")>-1){ // 如果是window系统，调试的时候目录不想指定到随机目录
         return System.getProperty("user.dir") + File.separator + filePath;
       }

       if (!StringUtils.isEmpty(filePath)) {
         String last = path.charAt(path.length() - 1) + "";
         String frist = filePath.charAt(0) + "";
         if (last.equals(File.separator)) {
           if (frist.equals("\\") || frist.equals("/")) {
             path = path + filePath.substring(1);
           } else {
             path = path + filePath;
           }

         } else if (frist.equals("\\") || frist.equals("/")) {
           path = path + filePath;
         } else {
           path = path + File.separator + filePath;
         }
       }

       return path;
     }

     return System.getProperty("user.dir") + File.separator + filePath;
   }







   public static String getClassPath(String filePath) {
     String os = System.getProperty("os.name");
     String temp = ClassUtils.getDefaultClassLoader().getResource("").getPath() + filePath;
     if (os.toLowerCase().startsWith("win")) {
       return temp.replace("/", "\\");
     }
     return temp;
   }







   public static Class<?> deduceMainApplicationClass() {
     try {
       StackTraceElement[] stackTrace = (new RuntimeException()).getStackTrace();
       for (StackTraceElement stackTraceElement : stackTrace) {
         if ("main".equals(stackTraceElement.getMethodName())) {
           return Class.forName(stackTraceElement.getClassName());
         }
       }

     } catch (ClassNotFoundException classNotFoundException) {}


     return null;
   }








   public static Map enumToMap(BaseEnum[] baseEnum) { return enumToMap(baseEnum, true); }











   public static Map enumToMap(BaseEnum[] baseEnum, boolean idKey) {
     Map<Object, Object> map = new HashMap<>();
     for (BaseEnum be : baseEnum) {
       if (idKey) {
         map.put(be.toInt() + "", be.toString());
       } else {
         Enum e = (Enum)be;
         map.put(e.name(), be.toString());
       }
     }
     return map;
   }








   public static Map resToMap(String resPath) { return resToMap(ResourceBundle.getBundle(resPath)); }









   public static Map resToMap(ResourceBundle rb) {
     Map<Object, Object> map = new HashMap<>();
     Enumeration<String> e = rb.getKeys();
     while (e.hasMoreElements()) {
       String key = e.nextElement().toString();
       map.put(key, rb.getString(key));
     }
     return map;
   }










   public static String getCookie(BaseCookieEnum key) {
     HttpServletRequest request = SpringUtil.getRequest();
     if (request.getCookies() != null) {
       for (Cookie c : request.getCookies()) {
         if (c.getName().equals(key.toString())) {
           return c.getValue();
         }
       }
     }
     return null;
   }










   public static String getCookie(String key) {
     HttpServletRequest request = SpringUtil.getRequest();
     if (request.getCookies() != null) {
       for (Cookie c : request.getCookies()) {
         if (c.getName().equals(key)) {
           return c.getValue();
         }
       }
     }
     return null;
   }











   public static void setCookie(HttpServletResponse response, BaseCookieEnum key, Object value) {
     if (value == null) {
       setCookie(response, null, "/", key.toString(), null, -1);
     } else {
       setCookie(response, null, "/", key.toString(), value.toString(), -1);
     }
   }














   public static void setCookie(HttpServletResponse response, BaseCookieEnum key, Object value, int expiry) { setCookie(response, null, "/", key.toString(), value.toString(), expiry); }














   public static void setCookie(HttpServletResponse response, String key, String value, int expiry) { setCookie(response, null, "/", key.toString(), value.toString(), expiry); }















   public static void setCookie(HttpServletResponse response, String domain, String key, String value, int expiry) { setCookie(response, domain, "/", key.toString(), value.toString(), expiry); }











   public static void setCookie(HttpServletResponse response, String domain, String path, String key, String value, int expiry) {
     if (StringUtils.isEmpty(key)) {
       return;
     }
     Cookie cookie = new Cookie(key.toString(), value);
     if (StringUtils.isNotEmpty(path)) {
       cookie.setPath(path);
     }
     if (StringUtils.isNotEmpty(domain)) {
       cookie.setDomain(domain);
     }
     cookie.setMaxAge(expiry);
     response.addCookie(cookie);
   }









   public static boolean isMobileDevice() {
     HttpServletRequest request = SpringUtil.getRequest();
     boolean b = false;
     boolean pcFlag = false;
     boolean mobileFlag = false;
     String via = request.getHeader("Via");
     String userAgent = request.getHeader("user-agent");
     for (int i = 0; via != null && !via.trim().equals("") && i < mobileGateWayHeaders.length; i++) {
       if (via.contains(mobileGateWayHeaders[i])) {
         mobileFlag = true;
         break;
       }
     }
     for (int i = 0; !mobileFlag && userAgent != null && !userAgent.trim().equals("") && i < mobileUserAgents.length; i++) {
       if (userAgent.contains(mobileUserAgents[i])) {
         mobileFlag = true;
         break;
       }
     }
     for (int i = 0; userAgent != null && !userAgent.trim().equals("") && i < pcHeaders.length; i++) {
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
 }


