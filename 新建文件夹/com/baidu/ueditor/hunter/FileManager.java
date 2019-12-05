/*     */ package com.baidu.ueditor.hunter;
/*     */ 
/*     */ import com.baidu.ueditor.PathFormat;
/*     */ import com.baidu.ueditor.define.BaseState;
/*     */ import com.baidu.ueditor.define.MultiState;
/*     */ import com.baidu.ueditor.define.State;
/*     */ import java.io.File;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.Map;
/*     */ import org.apache.commons.io.FileUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FileManager
/*     */ {
/*  18 */   private String dir = null;
/*  19 */   private String rootPath = null;
/*  20 */   private String[] allowFiles = null;
/*  21 */   private int count = 0;
/*     */ 
/*     */   
/*     */   public FileManager(Map<String, Object> conf) {
/*  25 */     this.rootPath = (String)conf.get("rootPath");
/*  26 */     this.dir = String.valueOf(this.rootPath) + (String)conf.get("dir");
/*  27 */     this.allowFiles = getAllowFiles(conf.get("allowFiles"));
/*  28 */     this.count = ((Integer)conf.get("count")).intValue();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public State listFile(int index) {
/*  34 */     File dir = new File(this.dir);
/*  35 */     State state = null;
/*     */     
/*  37 */     if (!dir.exists()) {
/*  38 */       return (State)new BaseState(false, 302);
/*     */     }
/*     */     
/*  41 */     if (!dir.isDirectory()) {
/*  42 */       return (State)new BaseState(false, 301);
/*     */     }
/*     */     
/*  45 */     Collection<File> list = FileUtils.listFiles(dir, this.allowFiles, true);
/*     */     
/*  47 */     if (index < 0 || index > list.size()) {
/*  48 */       MultiState multiState = new MultiState(true);
/*     */     } else {
/*  50 */       Object[] arrayOfObject = Arrays.copyOfRange(list.toArray(), index, index + this.count);
/*  51 */       state = getState(arrayOfObject);
/*     */     } 
/*     */     
/*  54 */     state.putInfo("start", index);
/*  55 */     state.putInfo("total", list.size());
/*     */     
/*  57 */     return state;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private State getState(Object[] files) {
/*  63 */     MultiState state = new MultiState(true);
/*  64 */     BaseState fileState = null;
/*     */     
/*  66 */     File file = null; byte b; int i;
/*     */     Object[] arrayOfObject;
/*  68 */     for (i = (arrayOfObject = files).length, b = 0; b < i; ) { Object obj = arrayOfObject[b];
/*  69 */       if (obj == null) {
/*     */         break;
/*     */       }
/*  72 */       file = (File)obj;
/*  73 */       fileState = new BaseState(true);
/*  74 */       fileState.putInfo("url", PathFormat.format(getPath(file)));
/*  75 */       state.addState((State)fileState);
/*     */       b++; }
/*     */     
/*  78 */     return (State)state;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private String getPath(File file) {
/*  84 */     String path = file.getAbsolutePath();
/*     */     
/*  86 */     return path.replace(this.rootPath, "/");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private String[] getAllowFiles(Object fileExt) {
/*  92 */     String[] exts = null;
/*  93 */     String ext = null;
/*     */     
/*  95 */     if (fileExt == null) {
/*  96 */       return new String[0];
/*     */     }
/*     */     
/*  99 */     exts = (String[])fileExt;
/*     */     
/* 101 */     for (int i = 0, len = exts.length; i < len; i++) {
/*     */       
/* 103 */       ext = exts[i];
/* 104 */       exts[i] = ext.replace(".", "");
/*     */     } 
/*     */ 
/*     */     
/* 108 */     return exts;
/*     */   }
/*     */ }


/* Location:              D:\User\Maven\repository\net\mingsoft\ms-ueditor\1.0.4\ms-ueditor-1.0.4.jar!\com\baid\\ueditor\hunter\FileManager.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.2
 */