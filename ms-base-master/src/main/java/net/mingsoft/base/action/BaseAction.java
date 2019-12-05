 package net.mingsoft.base.action;

 import com.alibaba.fastjson.JSONArray;
 import com.alibaba.fastjson.JSONObject;
 import com.alibaba.fastjson.serializer.JSONSerializer;
 import com.alibaba.fastjson.serializer.PropertyFilter;
 import com.alibaba.fastjson.serializer.SerializeWriter;
 import java.io.IOException;
 import java.io.PrintWriter;
 import java.net.InetAddress;
 import java.net.UnknownHostException;
 import java.util.Arrays;
 import java.util.Enumeration;
 import java.util.HashMap;
 import java.util.List;
 import java.util.Map;
 import java.util.MissingResourceException;
 import java.util.ResourceBundle;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;
 import net.mingsoft.base.constant.Const;
 import net.mingsoft.base.constant.e.BaseEnum;
 import net.mingsoft.base.entity.BaseEntity;
 import net.mingsoft.base.entity.ResultJson;
 import org.apache.commons.lang3.StringUtils;
 import org.slf4j.Logger;
 import org.slf4j.LoggerFactory;
















































 public abstract class BaseAction
 {
   protected final Logger LOG = LoggerFactory.getLogger(getClass());


   private static String[] mobileGateWayHeaders = new String[] { "ZXWAP", "chinamobile.com", "monternet.com", "infoX", "XMS 724Solutions HTG", "wap.lizongbo.com", "Bytemobile" };



















   private static String[] pcHeaders = new String[] { "Windows 98", "Windows ME", "Windows 2000", "Windows XP", "Windows NT", "Ubuntu" };


   private static String[] mobileUserAgents = new String[] { "Nokia", "SAMSUNG", "MIDP-2", "CLDC1.1", "SymbianOS", "MAUI", "UNTRUSTED/1.0", "Windows CE", "iPhone", "iPad", "Android", "BlackBerry", "UCWEB", "ucweb", "BREW", "J2ME", "YULONG", "YuLong", "COOLPAD", "TIANYU", "TY-", "K-Touch", "Haier", "DOPOD", "Lenovo", "LENOVO", "HUAQIN", "AIGO-", "CTC/1.0", "CTC/2.0", "CMCC", "DAXIAN", "MOT-", "SonyEricsson", "GIONEE", "HTC", "ZTE", "HUAWEI", "webOS", "GoBrowser", "IEMobile", "WAP2.0" };
























































































   protected void outJson(HttpServletResponse response, BaseEnum code, boolean flag, String msg, Object data) {
     try {
       response.setContentType("application/json;charset=utf-8");
       ResultJson result = new ResultJson();
       if (code != null) {
         result.setCode(code.toString());
       }
       result.setResult(flag);
       result.setResultMsg(msg);
       result.setResultData(data);
       response.setCharacterEncoding("utf-8");
       PrintWriter out = response.getWriter();
       out.print(JSONObject.toJSON(result));
       out.flush();
       out.close();
     } catch (IOException e) {
       e.printStackTrace();
     }
   }














   protected void outJson(HttpServletResponse response, BaseEnum code, boolean flag, String msg) { outJson(response, code, flag, msg, null); }















   protected void outJson(HttpServletResponse response, boolean flag, String msg) { outJson(response, null, flag, msg, null); }













   protected void outJson(HttpServletResponse response, BaseEnum code, boolean flag) { outJson(response, code, flag, null, null); }











   protected void outJson(HttpServletResponse response, boolean flag) { outJson(response, null, flag, null, null); }










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









   protected void outJson(HttpServletResponse response, BaseEntity entity) { outJson(response, JSONObject.toJSONString(entity)); }











   protected void outJson(HttpServletResponse response, List list) { outJson(response, JSONArray.toJSONString(list)); }








   protected void outJson(HttpServletResponse response, List list, String... filters) {
     PropertyFilter filter = new PropertyFilter() {
         public boolean apply(Object source, String name, Object value) {
           List<String> list = Arrays.asList(filters);
           if (list.contains(name)) {
             return false;
           }
           return true;
         }
       };
     SerializeWriter sw = new SerializeWriter();
     JSONSerializer serializer = new JSONSerializer(sw);
     serializer.getPropertyFilters().add(filter);
     serializer.write(list);
     outJson(response, sw);
   }







   protected void outJson(HttpServletResponse response, Object obj, String... filters) {
     PropertyFilter filter = new PropertyFilter() {
         public boolean apply(Object source, String name, Object value) {
           List<String> list = Arrays.asList(filters);
           if (list.contains(name)) {
             return false;
           }
           return true;
         }
       };
     SerializeWriter sw = new SerializeWriter();
     JSONSerializer serializer = new JSONSerializer(sw);
     serializer.getPropertyFilters().add(filter);
     serializer.write(obj);
     outJson(response, sw);
   }













   protected void outJson(HttpServletResponse response, List list, String dateFmt) { outJson(response, JSONArray.toJSONStringWithDateFormat(list, dateFmt, new com.alibaba.fastjson.serializer.SerializerFeature[0])); }










   protected void redirect(HttpServletResponse response, String path) { outString(response, "<script>location.href='" + path + "'</script>"); }










   protected void outString(HttpServletResponse response, Object dataStr) {
     try {
       response.setContentType("text/html;charset=utf-8");
       PrintWriter out = response.getWriter();
       out.print(dataStr);
       out.flush();
       out.close();
     } catch (IOException e) {
       e.printStackTrace();
     }
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








   protected String getHost(HttpServletRequest request) {
     String basePath = request.getServerName();
     if (request.getServerPort() != 80) {
       basePath = basePath + ":" + request.getServerPort();
     }
     return basePath;
   }







   protected String getHostIp() {
     try {
       InetAddress addr = InetAddress.getLocalHost();
       return addr.getHostAddress().toString();
     } catch (UnknownHostException e) {

       e.printStackTrace();

       return "";
     }
   }








   protected String getResString(String key) { return Const.RESOURCES.getString(key); }











   protected String getResString(String key, ResourceBundle rb) {
     try {
       return rb.getString(key);
     } catch (MissingResourceException e) {
       return Const.RESOURCES.getString(key);
     }
   }










   protected String getResString(String key, String... fullStrs) {
     String temp = getResString(key);
     for (int i = 0; i < fullStrs.length; i++) {
       temp = temp.replace("{" + i + "}", fullStrs[i]);
     }
     return temp;
   }










   protected String getResString(String key, ResourceBundle rb, String... fullStrs) {
     String temp = "";
     try {
       temp = rb.getString(key);
     } catch (MissingResourceException e) {
       temp = getResString(key);
     }
     for (int i = 0; i < fullStrs.length; i++) {
       temp = temp.replace("{" + i + "}", fullStrs[i]);
     }
     return temp;
   }








   public boolean isMobileDevice(HttpServletRequest request) {
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
     int i = 0;
     for (; !mobileFlag && userAgent != null && !userAgent.trim().equals("") && i < mobileUserAgents.length; i++) {
       if (userAgent.contains(mobileUserAgents[i])) {
         mobileFlag = true;
         break;
       }
     }
     for ( i = 0; userAgent != null && !userAgent.trim().equals("") && i < pcHeaders.length; i++) {
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







   protected Map<String, String> getMapByProperties(String filePath) {
     if (StringUtils.isBlank(filePath)) {
       return null;
     }
     ResourceBundle rb = ResourceBundle.getBundle(filePath);
     return getMapByProperties(rb);
   }

   protected Map<String, String> getMapByProperties(ResourceBundle rb) {
     Map<String, String> map = new HashMap<>();
     Enumeration<String> en = rb.getKeys();
     while (en.hasMoreElements()) {
       String key = en.nextElement();
       map.put(key, rb.getString(key));
     }
     return map;
   }
 }


