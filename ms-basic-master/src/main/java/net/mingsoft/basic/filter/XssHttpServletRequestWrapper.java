 package net.mingsoft.basic.filter;

 import java.io.BufferedReader;
 import java.io.ByteArrayInputStream;
 import java.io.IOException;
 import java.io.InputStream;
 import java.io.InputStreamReader;
 import java.util.HashMap;
 import java.util.Iterator;
 import java.util.Map;
 import javax.servlet.ReadListener;
 import javax.servlet.ServletInputStream;
 import javax.servlet.ServletRequest;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletRequestWrapper;
 import org.apache.commons.lang3.StringUtils;
 import org.jsoup.Jsoup;
 import org.jsoup.nodes.Document;
 import org.jsoup.safety.Whitelist;
















 public class XssHttpServletRequestWrapper
   extends HttpServletRequestWrapper
 {
   private HttpServletRequest request = null;




   private static final Whitelist whitelist = new Whitelist();



   private static final Document.OutputSettings outputSettings = (new Document.OutputSettings()).prettyPrint(false);

   public XssHttpServletRequestWrapper(HttpServletRequest request) {
     super(request);
     request = request;
   }



   public ServletInputStream getInputStream() throws IOException {
     BufferedReader br = new BufferedReader(new InputStreamReader((InputStream)this.request.getInputStream()));
     String line = br.readLine();
     String result = "";
     if (line != null) {
       result = result + clean(line);
     }

     return new WrappedServletInputStream(new ByteArrayInputStream(result.getBytes()));
   }









   public String getParameter(String name) {
     if ("content".equals(name) || name.endsWith("WithHtml")) {
       return super.getParameter(name);
     }
     name = clean(name);
     String value = super.getParameter(name);
     if (StringUtils.isNotBlank(value)) {
       value = clean(value);
     }
     return value;
   }


   public Map getParameterMap() {
     Map map = super.getParameterMap();

     Map<String, String> returnMap = new HashMap<>();
     Iterator<Map.Entry> entries = map.entrySet().iterator();

     String name = "";
     String value = "";
     while (entries.hasNext()) {
       Map.Entry entry = entries.next();
       name = (String)entry.getKey();
       Object valueObj = entry.getValue();
       if (null == valueObj) {
         value = "";
       } else if (valueObj instanceof String[]) {
         String[] values = (String[])valueObj;
         for (int i = 0; i < values.length; i++) {
           value = values[i] + ",";
         }
         value = value.substring(0, value.length() - 1);
       } else {
         value = valueObj.toString();
       }
       returnMap.put(name, clean(value).trim());
     }
     return returnMap;
   }


   public String[] getParameterValues(String name) {
     String[] arr = super.getParameterValues(name);
     if (arr != null) {
       for (int i = 0; i < arr.length; i++) {
         arr[i] = clean(arr[i]);
       }
     }
     return arr;
   }










   public String getHeader(String name) {
     name = clean(name);
     String value = super.getHeader(name);
     if (StringUtils.isNotBlank(value)) {
       value = clean(value);
     }
     return value;
   }







   public HttpServletRequest getRequest() { return this.request; }







   public static HttpServletRequest getOrgRequest(HttpServletRequest req) {
     if (req instanceof XssHttpServletRequestWrapper) {
       return ((XssHttpServletRequestWrapper)req).getRequest();
     }

     return req;
   }

   public String clean(String content) {
     String result = Jsoup.clean(content, "", whitelist, outputSettings);
     return result;
   }

   private class WrappedServletInputStream
     extends ServletInputStream {
     public void setStream(InputStream stream) { this.stream = stream; }


     private InputStream stream;


     public WrappedServletInputStream(InputStream stream) { this.stream = stream; }




     public int read() throws IOException { return this.stream.read(); }




     public boolean isFinished() { return true; }




     public boolean isReady() { return true; }

     public void setReadListener(ReadListener readListener) {}
   }
 }


/* Location:              D:\User\Maven\repository\net\mingsoft\ms-basic\1.0.16\ms-basic-1.0.16.jar!\net\mingsoft\basic\filter\XssHttpServletRequestWrapper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */