/*     */ package com.baidu.ueditor.hunter;
/*     */ 
/*     */ import com.baidu.ueditor.PathFormat;
/*     */ import com.baidu.ueditor.define.BaseState;
/*     */ import com.baidu.ueditor.define.MIMEType;
/*     */ import com.baidu.ueditor.define.MultiState;
/*     */ import com.baidu.ueditor.define.State;
/*     */ import com.baidu.ueditor.upload.StorageManager;
/*     */ import java.net.HttpURLConnection;
/*     */ import java.net.InetAddress;
/*     */ import java.net.URL;
/*     */ import java.net.UnknownHostException;
/*     */ import java.util.Arrays;
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
/*     */ public class ImageHunter
/*     */ {
/*  26 */   private String filename = null;
/*  27 */   private String savePath = null;
/*  28 */   private String rootPath = null;
/*  29 */   private List<String> allowTypes = null;
/*  30 */   private long maxSize = -1L;
/*     */   
/*  32 */   private List<String> filters = null;
/*     */ 
/*     */   
/*     */   public ImageHunter(Map<String, Object> conf) {
/*  36 */     this.filename = (String)conf.get("filename");
/*  37 */     this.savePath = (String)conf.get("savePath");
/*  38 */     this.rootPath = (String)conf.get("rootPath");
/*  39 */     this.maxSize = ((Long)conf.get("maxSize")).longValue();
/*  40 */     this.allowTypes = Arrays.asList((String[])conf.get("allowFiles"));
/*  41 */     this.filters = Arrays.asList((String[])conf.get("filter"));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public State capture(String[] list) {
/*  47 */     MultiState state = new MultiState(true); byte b; int i;
/*     */     String[] arrayOfString;
/*  49 */     for (i = (arrayOfString = list).length, b = 0; b < i; ) { String source = arrayOfString[b];
/*  50 */       state.addState(captureRemoteData(source));
/*     */       b++; }
/*     */     
/*  53 */     return (State)state;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public State captureRemoteData(String urlStr) {
/*  59 */     HttpURLConnection connection = null;
/*  60 */     URL url = null;
/*  61 */     String suffix = null;
/*     */     
/*     */     try {
/*  64 */       url = new URL(urlStr);
/*     */       
/*  66 */       if (!validHost(url.getHost())) {
/*  67 */         return (State)new BaseState(false, 201);
/*     */       }
/*     */       
/*  70 */       connection = (HttpURLConnection)url.openConnection();
/*     */       
/*  72 */       connection.setInstanceFollowRedirects(true);
/*  73 */       connection.setUseCaches(true);
/*     */       
/*  75 */       if (!validContentState(connection.getResponseCode())) {
/*  76 */         return (State)new BaseState(false, 202);
/*     */       }
/*     */       
/*  79 */       suffix = MIMEType.getSuffix(connection.getContentType());
/*     */       
/*  81 */       if (!validFileType(suffix)) {
/*  82 */         return (State)new BaseState(false, 8);
/*     */       }
/*     */       
/*  85 */       if (!validFileSize(connection.getContentLength())) {
/*  86 */         return (State)new BaseState(false, 1);
/*     */       }
/*     */       
/*  89 */       String savePath = getPath(this.savePath, this.filename, suffix);
/*  90 */       String physicalPath = String.valueOf(this.rootPath) + savePath;
/*     */       
/*  92 */       State state = StorageManager.saveFileByInputStream(connection.getInputStream(), physicalPath);
/*     */       
/*  94 */       if (state.isSuccess()) {
/*  95 */         state.putInfo("url", PathFormat.format(savePath));
/*  96 */         state.putInfo("source", urlStr);
/*     */       } 
/*     */       
/*  99 */       return state;
/*     */     }
/* 101 */     catch (Exception exception) {
/* 102 */       return (State)new BaseState(false, 203);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 109 */   private String getPath(String savePath, String filename, String suffix) { return PathFormat.parse(String.valueOf(savePath) + suffix, filename); }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean validHost(String hostname) {
/*     */     try {
/* 115 */       InetAddress ip = InetAddress.getByName(hostname);
/*     */       
/* 117 */       if (ip.isSiteLocalAddress()) {
/* 118 */         return false;
/*     */       }
/* 120 */     } catch (UnknownHostException unknownHostException) {
/* 121 */       return false;
/*     */     } 
/*     */     
/* 124 */     return !this.filters.contains(hostname);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 130 */   private boolean validContentState(int code) { return (200 == code); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 136 */   private boolean validFileType(String type) { return this.allowTypes.contains(type); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 141 */   private boolean validFileSize(int size) { return (size < this.maxSize); }
/*     */ }


/* Location:              D:\User\Maven\repository\net\mingsoft\ms-ueditor\1.0.4\ms-ueditor-1.0.4.jar!\com\baid\\ueditor\hunter\ImageHunter.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.2
 */