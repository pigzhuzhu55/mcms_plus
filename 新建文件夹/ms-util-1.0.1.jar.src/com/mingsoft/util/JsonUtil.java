/*     */ package com.mingsoft.util;
/*     */ 
/*     */ import com.alibaba.fastjson.JSON;
/*     */ import com.alibaba.fastjson.JSONArray;
/*     */ import com.alibaba.fastjson.JSONObject;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class JsonUtil
/*     */ {
/*     */   public static Object getObject4JsonString(String jsonString, Class<?> pojoCalss) {
/*  53 */     JSONObject jsonObject = JSONObject.parseObject(jsonString);
/*  54 */     Object pojo = JSONObject.toJavaObject((JSON)jsonObject, pojoCalss);
/*  55 */     return pojo;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Map<String, Object> getMap4Json(String jsonString) {
/*  65 */     JSONObject jsonObject = JSONObject.parseObject(jsonString);
/*  66 */     Iterator<String> keyIter = jsonObject.keySet().iterator();
/*     */ 
/*     */     
/*  69 */     Map<String, Object> valueMap = new HashMap<>();
/*     */     
/*  71 */     while (keyIter.hasNext()) {
/*  72 */       String key = keyIter.next();
/*  73 */       Object value = jsonObject.get(key);
/*  74 */       valueMap.put(key, value);
/*     */     } 
/*  76 */     return valueMap;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Object[] getObjectArray4Json(String jsonString) {
/*  86 */     JSONArray jsonArray = JSONArray.parseArray(jsonString);
/*  87 */     return jsonArray.toArray();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  96 */   public static String getObjectToJsonObject(Object obj) { return JSON.toJSON(obj).toString(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String[] getStringArray4Json(String jsonString) {
/* 107 */     JSONArray jsonArray = JSONArray.parseArray(jsonString);
/* 108 */     String[] stringArray = new String[jsonArray.size()];
/*     */     
/* 110 */     for (int i = 0; i < jsonArray.size(); i++) {
/* 111 */       stringArray[i] = jsonArray.get(i).toString();
/*     */     }
/*     */     
/* 114 */     return stringArray;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 126 */   public static <T> T getJsonToObject(String jsonString, Class<T> cls) { return (T)JSONObject.parseObject(jsonString, cls); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T> List<T> queryJsonToList(String jsonString, Class<T> cls) {
/* 139 */     List<T> list = JSONArray.parseArray(jsonString, cls);
/* 140 */     return list;
/*     */   }
/*     */ }


/* Location:              D:\User\Maven\repository\net\mingsoft\ms-util\1.0.1\ms-util-1.0.1.jar!\com\mingsof\\util\JsonUtil.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.2
 */