/*     */ package com.baidu.ueditor.upload;
/*     */ 
/*     */ import com.baidu.ueditor.define.BaseState;
/*     */ import com.baidu.ueditor.define.State;
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.BufferedOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import org.apache.commons.io.FileUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StorageManager
/*     */ {
/*     */   public static final int BUFFER_SIZE = 8192;
/*     */   
/*     */   public static State saveBinaryFile(byte[] data, String path) {
/*  23 */     File file = new File(path);
/*     */     
/*  25 */     State state = valid(file);
/*     */     
/*  27 */     if (!state.isSuccess()) {
/*  28 */       return state;
/*     */     }
/*     */     
/*     */     try {
/*  32 */       BufferedOutputStream bos = new BufferedOutputStream(
/*  33 */           new FileOutputStream(file));
/*  34 */       bos.write(data);
/*  35 */       bos.flush();
/*  36 */       bos.close();
/*  37 */     } catch (IOException iOException) {
/*  38 */       return (State)new BaseState(false, 4);
/*     */     } 
/*     */     
/*  41 */     BaseState baseState = new BaseState(true, file.getAbsolutePath());
/*  42 */     baseState.putInfo("size", data.length);
/*  43 */     baseState.putInfo("title", file.getName());
/*  44 */     return (State)baseState;
/*     */   }
/*     */ 
/*     */   
/*     */   public static State saveFileByInputStream(InputStream is, String path, long maxSize) {
/*  49 */     State state = null;
/*     */     
/*  51 */     File tmpFile = getTmpFile();
/*     */     
/*  53 */     byte[] dataBuf = new byte[2048];
/*  54 */     BufferedInputStream bis = new BufferedInputStream(is, 8192);
/*     */     
/*     */     try {
/*  57 */       BufferedOutputStream bos = new BufferedOutputStream(
/*  58 */           new FileOutputStream(tmpFile), 8192);
/*     */       
/*  60 */       int count = 0;
/*  61 */       while ((count = bis.read(dataBuf)) != -1) {
/*  62 */         bos.write(dataBuf, 0, count);
/*     */       }
/*  64 */       bos.flush();
/*  65 */       bos.close();
/*     */       
/*  67 */       if (tmpFile.length() > maxSize) {
/*  68 */         tmpFile.delete();
/*  69 */         return (State)new BaseState(false, 1);
/*     */       } 
/*     */       
/*  72 */       state = saveTmpFile(tmpFile, path);
/*     */       
/*  74 */       if (!state.isSuccess()) {
/*  75 */         tmpFile.delete();
/*     */       }
/*     */       
/*  78 */       return state;
/*     */     }
/*  80 */     catch (IOException iOException) {
/*     */       
/*  82 */       return (State)new BaseState(false, 4);
/*     */     } 
/*     */   }
/*     */   public static State saveFileByInputStream(InputStream is, String path) {
/*  86 */     State state = null;
/*     */     
/*  88 */     File tmpFile = getTmpFile();
/*     */     
/*  90 */     byte[] dataBuf = new byte[2048];
/*  91 */     BufferedInputStream bis = new BufferedInputStream(is, 8192);
/*     */     
/*     */     try {
/*  94 */       BufferedOutputStream bos = new BufferedOutputStream(
/*  95 */           new FileOutputStream(tmpFile), 8192);
/*     */       
/*  97 */       int count = 0;
/*  98 */       while ((count = bis.read(dataBuf)) != -1) {
/*  99 */         bos.write(dataBuf, 0, count);
/*     */       }
/* 101 */       bos.flush();
/* 102 */       bos.close();
/*     */       
/* 104 */       state = saveTmpFile(tmpFile, path);
/*     */       
/* 106 */       if (!state.isSuccess()) {
/* 107 */         tmpFile.delete();
/*     */       }
/*     */       
/* 110 */       return state;
/* 111 */     } catch (IOException iOException) {
/*     */       
/* 113 */       return (State)new BaseState(false, 4);
/*     */     } 
/*     */   }
/*     */   private static File getTmpFile() {
/* 117 */     File tmpDir = FileUtils.getTempDirectory();
/* 118 */     String tmpFileName = (new StringBuilder(String.valueOf(Math.random() * 10000.0D))).toString().replace(".", "");
/* 119 */     return new File(tmpDir, tmpFileName);
/*     */   }
/*     */   
/*     */   private static State saveTmpFile(File tmpFile, String path) {
/* 123 */     State state = null;
/* 124 */     File targetFile = new File(path);
/*     */     
/* 126 */     if (targetFile.canWrite()) {
/* 127 */       return (State)new BaseState(false, 2);
/*     */     }
/*     */     try {
/* 130 */       FileUtils.moveFile(tmpFile, targetFile);
/* 131 */     } catch (IOException iOException) {
/* 132 */       return (State)new BaseState(false, 4);
/*     */     } 
/*     */     
/* 135 */     BaseState baseState = new BaseState(true);
/* 136 */     baseState.putInfo("size", targetFile.length());
/* 137 */     baseState.putInfo("title", targetFile.getName());
/*     */     
/* 139 */     return (State)baseState;
/*     */   }
/*     */   
/*     */   private static State valid(File file) {
/* 143 */     File parentPath = file.getParentFile();
/*     */     
/* 145 */     if (!parentPath.exists() && !parentPath.mkdirs()) {
/* 146 */       return (State)new BaseState(false, 3);
/*     */     }
/*     */     
/* 149 */     if (!parentPath.canWrite()) {
/* 150 */       return (State)new BaseState(false, 2);
/*     */     }
/*     */     
/* 153 */     return (State)new BaseState(true);
/*     */   }
/*     */ }


/* Location:              D:\User\Maven\repository\net\mingsoft\ms-ueditor\1.0.4\ms-ueditor-1.0.4.jar!\com\baid\\uedito\\upload\StorageManager.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.2
 */