/*     */ package com.baidu.ueditor;
/*     */ 
/*     */ import com.baidu.ueditor.define.ActionMap;
/*     */ import com.baidu.ueditor.define.BaseState;
/*     */ import com.baidu.ueditor.define.State;
/*     */ import com.baidu.ueditor.hunter.FileManager;
/*     */ import com.baidu.ueditor.hunter.ImageHunter;
/*     */ import com.baidu.ueditor.upload.Uploader;
/*     */ import java.util.Map;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ActionEnter
/*     */ {
/*  17 */   private HttpServletRequest request = null;
/*     */   
/*  19 */   private String rootPath = null;
/*  20 */   private String contextPath = null;
/*     */   
/*  22 */   private String actionType = null;
/*     */   
/*  24 */   private ConfigManager configManager = null;
/*     */ 
/*     */   
/*     */   public ActionEnter(HttpServletRequest request, String rootPath) {
/*  28 */     this.request = request;
/*  29 */     this.rootPath = rootPath;
/*  30 */     this.actionType = request.getParameter("action");
/*  31 */     this.contextPath = request.getContextPath();
/*  32 */     this.configManager = ConfigManager.getInstance(this.rootPath, this.contextPath, request.getRequestURI());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String exec() {
/*  38 */     String callbackName = this.request.getParameter("callback");
/*     */     
/*  40 */     if (callbackName != null) {
/*     */       
/*  42 */       if (!validCallbackName(callbackName)) {
/*  43 */         return (new BaseState(false, 401)).toJSONString();
/*     */       }
/*     */       
/*  46 */       return String.valueOf(callbackName) + "(" + invoke() + ");";
/*     */     } 
/*     */     
/*  49 */     return invoke();
/*     */   }
/*     */ 
/*     */   
/*     */   public String invoke() {
/*     */     int start;
/*     */     String[] list;
/*  56 */     if (this.actionType == null || !ActionMap.mapping.containsKey(this.actionType)) {
/*  57 */       return (new BaseState(false, 101)).toJSONString();
/*     */     }
/*     */     
/*  60 */     if (this.configManager == null || !this.configManager.valid()) {
/*  61 */       return (new BaseState(false, 102)).toJSONString();
/*     */     }
/*     */     
/*  64 */     State state = null;
/*     */     
/*  66 */     int actionCode = ActionMap.getType(this.actionType);
/*     */     
/*  68 */     Map<String, Object> conf = null;
/*     */     
/*  70 */     switch (actionCode) {
/*     */       
/*     */       case 0:
/*  73 */         return this.configManager.getAllConfig().toString();
/*     */       
/*     */       case 1:
/*     */       case 2:
/*     */       case 3:
/*     */       case 4:
/*  79 */         conf = this.configManager.getConfig(actionCode);
/*  80 */         state = (new Uploader(this.request, conf)).doExec();
/*     */         break;
/*     */       
/*     */       case 5:
/*  84 */         conf = this.configManager.getConfig(actionCode);
/*  85 */         list = this.request.getParameterValues((String)conf.get("fieldName"));
/*  86 */         state = (new ImageHunter(conf)).capture(list);
/*     */         break;
/*     */       
/*     */       case 6:
/*     */       case 7:
/*  91 */         conf = this.configManager.getConfig(actionCode);
/*  92 */         start = getStartIndex();
/*  93 */         state = (new FileManager(conf)).listFile(start);
/*     */         break;
/*     */     } 
/*     */ 
/*     */     
/*  98 */     return state.toJSONString();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getStartIndex() {
/* 104 */     String start = this.request.getParameter("start");
/*     */     
/*     */     try {
/* 107 */       return Integer.parseInt(start);
/* 108 */     } catch (Exception exception) {
/* 109 */       return 0;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean validCallbackName(String name) {
/* 119 */     if (name.matches("^[a-zA-Z_]+[\\w0-9_]*$")) {
/* 120 */       return true;
/*     */     }
/*     */     
/* 123 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 128 */   public ConfigManager getConfigManager() { return this.configManager; }
/*     */ 
/*     */ 
/*     */   
/* 132 */   public void setConfigManager(ConfigManager configManager) { this.configManager = configManager; }
/*     */ }


/* Location:              D:\User\Maven\repository\net\mingsoft\ms-ueditor\1.0.4\ms-ueditor-1.0.4.jar!\com\baid\\ueditor\ActionEnter.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.2
 */