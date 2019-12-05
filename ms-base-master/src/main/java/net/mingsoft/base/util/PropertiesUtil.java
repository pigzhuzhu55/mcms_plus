 package net.mingsoft.base.util;

 import java.io.FileInputStream;
 import java.io.FileNotFoundException;
 import java.io.FileOutputStream;
 import java.io.IOException;
 import java.io.InputStream;
 import java.io.OutputStream;
 import java.util.Enumeration;
 import java.util.HashMap;
 import java.util.Map;
 import java.util.Properties;































 public class PropertiesUtil
 {
   public static String get(String properties, String key) throws IOException {
     InputStream in = PropertiesUtil.class.getClassLoader().getResourceAsStream(properties);
     Properties props = new Properties();
     props.load(in);
     String value = props.getProperty(key);

     in.close();
     return value;
   }









   public static Map<String, String> getMap(String properties) throws FileNotFoundException, IOException {
     Map<String, String> map = new HashMap<>();
     InputStream in = PropertiesUtil.class.getClassLoader().getResourceAsStream(properties);
     Properties props = new Properties();
     props.load(in);
     Enumeration<?> en = props.propertyNames();
     while (en.hasMoreElements()) {
       String key = (String)en.nextElement();
       String Property = props.getProperty(key);
       map.put(key, Property);
     }
     in.close();
     return map;
   }












   public void setValue(String properties, String key, String value) throws IOException {
     Properties prop = new Properties();
     InputStream fis = new FileInputStream(PropertiesUtil.class.getClassLoader().getResource(properties).getPath());

     prop.load(fis);



     OutputStream fos = new FileOutputStream(PropertiesUtil.class.getClassLoader().getResource(properties).getPath());
     prop.setProperty(key, value);


     prop.store(fos, "last update");

     fis.close();
     fos.close();
   }
 }


