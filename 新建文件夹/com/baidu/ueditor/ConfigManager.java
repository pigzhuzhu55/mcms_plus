/*     */ package com.baidu.ueditor;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import org.json.JSONArray;
/*     */ import org.json.JSONObject;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ConfigManager
/*     */ {
/*     */   private final String rootPath;
/*     */   private final String originalPath;
/*     */   private final String contextPath;
/*     */   private static final String configFileName = "config.json";
/*  29 */   private String parentPath = null;
/*  30 */   private JSONObject jsonConfig = null;
/*     */ 
/*     */   
/*     */   private static final String SCRAWL_FILE_NAME = "scrawl";
/*     */ 
/*     */   
/*     */   private static final String REMOTE_FILE_NAME = "remote";
/*     */ 
/*     */ 
/*     */   
/*     */   private ConfigManager(String rootPath, String contextPath, String uri) throws FileNotFoundException, IOException {
/*  41 */     rootPath = rootPath.replace("\\", "/");
/*     */     
/*  43 */     this.rootPath = rootPath;
/*  44 */     this.contextPath = contextPath;
/*     */     
/*  46 */     if (contextPath.length() > 0) {
/*  47 */       this.originalPath = String.valueOf(this.rootPath) + uri.substring(contextPath.length());
/*     */     } else {
/*  49 */       this.originalPath = String.valueOf(this.rootPath) + uri;
/*     */     } 
/*     */     
/*  52 */     initEnv();
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
/*     */   public static ConfigManager getInstance(String rootPath, String contextPath, String uri) {
/*     */     try {
/*  66 */       return new ConfigManager(rootPath, contextPath, uri);
/*  67 */     } catch (Exception exception) {
/*  68 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  75 */   public boolean valid() { return (this.jsonConfig != null); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  80 */   public JSONObject getAllConfig() { return this.jsonConfig; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<String, Object> getConfig(int type) {
/*  86 */     Map<String, Object> conf = new HashMap<>();
/*  87 */     String savePath = null;
/*     */     
/*  89 */     switch (type) {
/*     */       
/*     */       case 4:
/*  92 */         conf.put("isBase64", "false");
/*  93 */         conf.put("maxSize", Long.valueOf(this.jsonConfig.getLong("fileMaxSize")));
/*  94 */         conf.put("allowFiles", getArray("fileAllowFiles"));
/*  95 */         conf.put("fieldName", this.jsonConfig.getString("fileFieldName"));
/*  96 */         savePath = this.jsonConfig.getString("filePathFormat");
/*     */         break;
/*     */       
/*     */       case 1:
/* 100 */         conf.put("isBase64", "false");
/* 101 */         conf.put("maxSize", Long.valueOf(this.jsonConfig.getLong("imageMaxSize")));
/* 102 */         conf.put("allowFiles", getArray("imageAllowFiles"));
/* 103 */         conf.put("fieldName", this.jsonConfig.getString("imageFieldName"));
/* 104 */         savePath = this.jsonConfig.getString("imagePathFormat");
/*     */         break;
/*     */       
/*     */       case 3:
/* 108 */         conf.put("maxSize", Long.valueOf(this.jsonConfig.getLong("videoMaxSize")));
/* 109 */         conf.put("allowFiles", getArray("videoAllowFiles"));
/* 110 */         conf.put("fieldName", this.jsonConfig.getString("videoFieldName"));
/* 111 */         savePath = this.jsonConfig.getString("videoPathFormat");
/*     */         break;
/*     */       
/*     */       case 2:
/* 115 */         conf.put("filename", "scrawl");
/* 116 */         conf.put("maxSize", Long.valueOf(this.jsonConfig.getLong("scrawlMaxSize")));
/* 117 */         conf.put("fieldName", this.jsonConfig.getString("scrawlFieldName"));
/* 118 */         conf.put("isBase64", "true");
/* 119 */         savePath = this.jsonConfig.getString("scrawlPathFormat");
/*     */         break;
/*     */       
/*     */       case 5:
/* 123 */         conf.put("filename", "remote");
/* 124 */         conf.put("filter", getArray("catcherLocalDomain"));
/* 125 */         conf.put("maxSize", Long.valueOf(this.jsonConfig.getLong("catcherMaxSize")));
/* 126 */         conf.put("allowFiles", getArray("catcherAllowFiles"));
/* 127 */         conf.put("fieldName", String.valueOf(this.jsonConfig.getString("catcherFieldName")) + "[]");
/* 128 */         savePath = this.jsonConfig.getString("catcherPathFormat");
/*     */         break;
/*     */       
/*     */       case 7:
/* 132 */         conf.put("allowFiles", getArray("imageManagerAllowFiles"));
/* 133 */         conf.put("dir", this.jsonConfig.getString("imageManagerListPath"));
/* 134 */         conf.put("count", Integer.valueOf(this.jsonConfig.getInt("imageManagerListSize")));
/*     */         break;
/*     */       
/*     */       case 6:
/* 138 */         conf.put("allowFiles", getArray("fileManagerAllowFiles"));
/* 139 */         conf.put("dir", this.jsonConfig.getString("fileManagerListPath"));
/* 140 */         conf.put("count", Integer.valueOf(this.jsonConfig.getInt("fileManagerListSize")));
/*     */         break;
/*     */     } 
/*     */ 
/*     */     
/* 145 */     conf.put("savePath", savePath);
/* 146 */     conf.put("rootPath", this.rootPath);
/*     */     
/* 148 */     return conf;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void initEnv() throws FileNotFoundException, IOException {
/* 154 */     File file = new File(this.originalPath);
/*     */     
/* 156 */     if (!file.isAbsolute()) {
/* 157 */       file = new File(file.getAbsolutePath());
/*     */     }
/*     */     
/* 160 */     this.parentPath = file.getParent();
/*     */     
/* 162 */     String configContent = readFile(getConfigPath());
/*     */     
/*     */     try {
/* 165 */       JSONObject jsonConfig = new JSONObject(configContent);
/* 166 */       this.jsonConfig = jsonConfig;
/* 167 */     } catch (Exception exception) {
/* 168 */       this.jsonConfig = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 174 */   private String getConfigPath() { return String.valueOf(this.parentPath) + File.separator + "config.json"; }
/*     */ 
/*     */ 
/*     */   
/*     */   private String[] getArray(String key) {
/* 179 */     JSONArray jsonArray = this.jsonConfig.getJSONArray(key);
/* 180 */     String[] result = new String[jsonArray.length()];
/*     */     
/* 182 */     for (int i = 0, len = jsonArray.length(); i < len; i++) {
/* 183 */       result[i] = jsonArray.getString(i);
/*     */     }
/*     */     
/* 186 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private String readFile(String path) throws IOException {
/* 192 */     StringBuilder builder = new StringBuilder();
/*     */ 
/*     */     
/*     */     try {
/* 196 */       InputStreamReader reader = new InputStreamReader(new FileInputStream(path), "UTF-8");
/* 197 */       BufferedReader bfReader = new BufferedReader(reader);
/*     */       
/* 199 */       String tmpContent = null;
/*     */       
/* 201 */       while ((tmpContent = bfReader.readLine()) != null) {
/* 202 */         builder.append(tmpContent);
/*     */       }
/*     */       
/* 205 */       bfReader.close();
/*     */     }
/* 207 */     catch (UnsupportedEncodingException unsupportedEncodingException) {}
/*     */ 
/*     */ 
/*     */     
/* 211 */     return filter(builder.toString());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 218 */   private String filter(String input) { return input.replaceAll("/\\*[\\s\\S]*?\\*/", ""); }
/*     */ }


/* Location:              D:\User\Maven\repository\net\mingsoft\ms-ueditor\1.0.4\ms-ueditor-1.0.4.jar!\com\baid\\ueditor\ConfigManager.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.2
 */