 package com.mingsoft.ueditor;

 import com.baidu.ueditor.ActionEnter;
 import com.baidu.ueditor.ConfigManager;
 import java.lang.reflect.Constructor;
 import java.lang.reflect.Field;
 import java.util.Iterator;
 import javax.servlet.http.HttpServletRequest;
 import org.json.JSONObject;




 public class MsUeditorActionEnter
   extends ActionEnter
 {
   public MsUeditorActionEnter(HttpServletRequest request, String rootPath, String jsonConfig) {
     super(request, rootPath);

     if (jsonConfig == null || jsonConfig.trim().equals("") || jsonConfig.length() < 0) {
       return;
     }
     ConfigManager config = getConfigManager();
     JSONObject _jsonConfig = new JSONObject(jsonConfig);

     JSONObject jsonObject = config.getAllConfig();

     Iterator<String> iterator = _jsonConfig.keys();
     while (iterator.hasNext()) {
       String key = iterator.next();

       jsonObject.put(key, _jsonConfig.get(key));
     }
   }

   public MsUeditorActionEnter(HttpServletRequest request, String rootPath, String jsonConfig, String configPath) {
     super(request, rootPath);

     if (jsonConfig == null || jsonConfig.trim().equals("") || jsonConfig.length() < 0) {
       return;
     }
     setConfigManager(ConfigManager.getInstance(configPath, request.getContextPath(), request.getRequestURI()));
     ConfigManager config = getConfigManager();

     setValue(config, "rootPath", rootPath);
     JSONObject _jsonConfig = new JSONObject(jsonConfig);
     JSONObject jsonObject = config.getAllConfig();
     Iterator<String> iterator = _jsonConfig.keys();
     while (iterator.hasNext()) {
       String key = iterator.next();
       jsonObject.put(key, _jsonConfig.get(key));
     }
   }








   public static void setValue(Object target, String fieldName, Object value) {
     Class<?> clazz = target.getClass();
     String[] fs = fieldName.split("\\.");
     try {
       for (int i = 0; i < fs.length - 1; i++) {
         Field f = clazz.getDeclaredField(fs[i]);
         f.setAccessible(true);
         Object val = f.get(target);
         if (val == null) {
           Constructor<?> c = f.getType().getDeclaredConstructor(new Class[0]);
           c.setAccessible(true);
           val = c.newInstance(new Object[0]);
           f.set(target, val);
         }
         target = val;
         clazz = target.getClass();
       }

       Field f = clazz.getDeclaredField(fs[fs.length - 1]);
       f.setAccessible(true);
       f.set(target, value);
     } catch (Exception e) {
       throw new RuntimeException(e);
     }
   }
 }


