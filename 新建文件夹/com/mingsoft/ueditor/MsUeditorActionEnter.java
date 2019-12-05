/*    */ package com.mingsoft.ueditor;
/*    */ 
/*    */ import com.baidu.ueditor.ActionEnter;
/*    */ import com.baidu.ueditor.ConfigManager;
/*    */ import java.lang.reflect.Constructor;
/*    */ import java.lang.reflect.Field;
/*    */ import java.util.Iterator;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import org.json.JSONObject;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MsUeditorActionEnter
/*    */   extends ActionEnter
/*    */ {
/*    */   public MsUeditorActionEnter(HttpServletRequest request, String rootPath, String jsonConfig) {
/* 18 */     super(request, rootPath);
/*    */     
/* 20 */     if (jsonConfig == null || jsonConfig.trim().equals("") || jsonConfig.length() < 0) {
/*    */       return;
/*    */     }
/* 23 */     ConfigManager config = getConfigManager();
/* 24 */     JSONObject _jsonConfig = new JSONObject(jsonConfig);
/*    */     
/* 26 */     JSONObject jsonObject = config.getAllConfig();
/*    */     
/* 28 */     Iterator<String> iterator = _jsonConfig.keys();
/* 29 */     while (iterator.hasNext()) {
/* 30 */       String key = iterator.next();
/*    */       
/* 32 */       jsonObject.put(key, _jsonConfig.get(key));
/*    */     } 
/*    */   }
/*    */   
/*    */   public MsUeditorActionEnter(HttpServletRequest request, String rootPath, String jsonConfig, String configPath) {
/* 37 */     super(request, rootPath);
/*    */     
/* 39 */     if (jsonConfig == null || jsonConfig.trim().equals("") || jsonConfig.length() < 0) {
/*    */       return;
/*    */     }
/* 42 */     setConfigManager(ConfigManager.getInstance(configPath, request.getContextPath(), request.getRequestURI()));
/* 43 */     ConfigManager config = getConfigManager();
/*    */     
/* 45 */     setValue(config, "rootPath", rootPath);
/* 46 */     JSONObject _jsonConfig = new JSONObject(jsonConfig);
/* 47 */     JSONObject jsonObject = config.getAllConfig();
/* 48 */     Iterator<String> iterator = _jsonConfig.keys();
/* 49 */     while (iterator.hasNext()) {
/* 50 */       String key = iterator.next();
/* 51 */       jsonObject.put(key, _jsonConfig.get(key));
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void setValue(Object target, String fieldName, Object value) {
/* 63 */     Class<?> clazz = target.getClass();
/* 64 */     String[] fs = fieldName.split("\\.");
/*    */     try {
/* 66 */       for (int i = 0; i < fs.length - 1; i++) {
/* 67 */         Field f = clazz.getDeclaredField(fs[i]);
/* 68 */         f.setAccessible(true);
/* 69 */         Object val = f.get(target);
/* 70 */         if (val == null) {
/* 71 */           Constructor<?> c = f.getType().getDeclaredConstructor(new Class[0]);
/* 72 */           c.setAccessible(true);
/* 73 */           val = c.newInstance(new Object[0]);
/* 74 */           f.set(target, val);
/*    */         } 
/* 76 */         target = val;
/* 77 */         clazz = target.getClass();
/*    */       } 
/*    */       
/* 80 */       Field f = clazz.getDeclaredField(fs[fs.length - 1]);
/* 81 */       f.setAccessible(true);
/* 82 */       f.set(target, value);
/* 83 */     } catch (Exception e) {
/* 84 */       throw new RuntimeException(e);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\User\Maven\repository\net\mingsoft\ms-ueditor\1.0.4\ms-ueditor-1.0.4.jar!\com\mingsof\\ueditor\MsUeditorActionEnter.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.2
 */