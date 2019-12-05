/*     */ package com.mingsoft.util;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.BufferedWriter;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.util.HashMap;
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
/*     */ public class FileUtil
/*     */ {
/*     */   public static final String URF8 = "UTF-8";
/*     */   
/*     */   public static String readFile(String filePath) {
/*  57 */     String fileContent = "";
/*     */     try {
/*  59 */       File f = new File(filePath);
/*  60 */       if (f.isFile() && f.exists()) {
/*  61 */         InputStreamReader read = new InputStreamReader(
/*  62 */             new FileInputStream(f), "UTF-8");
/*  63 */         BufferedReader reader = new BufferedReader(read);
/*     */         String line;
/*  65 */         while ((line = reader.readLine()) != null) {
/*  66 */           fileContent = String.valueOf(fileContent) + line + "\n";
/*     */         }
/*  68 */         reader.close();
/*  69 */         read.close();
/*     */       } 
/*  71 */     } catch (Exception e) {
/*  72 */       e.printStackTrace();
/*     */     } 
/*  74 */     return fileContent;
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
/*     */   public static List readFolder(List<Map<String, String>> list, String folderPath) {
/*  87 */     if (list == null) {
/*  88 */       return null;
/*     */     }
/*  90 */     File file = new File(folderPath);
/*  91 */     File[] tempList = file.listFiles();
/*  92 */     if (tempList != null && tempList.length > 0) {
/*  93 */       byte b; int i; File[] arrayOfFile; for (i = (arrayOfFile = tempList).length, b = 0; b < i; ) { File tmpFile = arrayOfFile[b];
/*  94 */         Map<String, String> map = new HashMap<>();
/*  95 */         map.put("name", file.getName());
/*  96 */         map.put("path", file.getPath());
/*  97 */         list.add(map);
/*  98 */         if (tmpFile.isDirectory())
/*  99 */           readFolder(list, tmpFile.getPath()); 
/*     */         b++; }
/*     */     
/*     */     } 
/* 103 */     return list;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void writeFile(String content, String writePath, String charCoder) {
/*     */     try {
/* 115 */       File file = new File(writePath);
/* 116 */       OutputStreamWriter osw = new OutputStreamWriter(
/* 117 */           new FileOutputStream(file), charCoder);
/* 118 */       BufferedWriter reader = new BufferedWriter(osw);
/* 119 */       reader.write(content);
/* 120 */       osw.flush();
/* 121 */       reader.close();
/* 122 */       osw.close();
/* 123 */     } catch (Exception e) {
/* 124 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void createFolder(String path) {
/* 134 */     File file = new File(path);
/* 135 */     if (!file.exists()) {
/* 136 */       file.mkdirs();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void delFolders(String folderPath) {
/* 147 */     File file = new File(folderPath);
/*     */     
/* 149 */     if (!file.isDirectory()) {
/* 150 */       file.delete();
/*     */       
/*     */       return;
/*     */     } 
/* 154 */     File[] tempList = file.listFiles();
/* 155 */     if (tempList != null && tempList.length > 0) {
/* 156 */       byte b; int i; File[] arrayOfFile; for (i = (arrayOfFile = tempList).length, b = 0; b < i; ) { File tmpFile = arrayOfFile[b];
/* 157 */         if (tmpFile.isDirectory()) {
/* 158 */           delFolders(tmpFile.getPath());
/*     */         } else {
/* 160 */           tmpFile.delete();
/*     */         }  b++; }
/*     */     
/*     */     } else {
/* 164 */       file.delete();
/*     */     } 
/* 166 */     delFolders(file.getPath());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void delFile(String path) {
/* 175 */     File file = new File(path);
/* 176 */     if (file.exists()) {
/* 177 */       file.delete();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void writeTmpFile(String fileName, String content) {
/* 188 */     String strDir = System.getProperty("user.dir");
/*     */     
/* 190 */     String folderpath = strDir;
/* 191 */     String filepath = String.valueOf(folderpath) + File.separatorChar + fileName + ".tmp";
/* 192 */     writeFile(content, filepath, "utf-8");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String readTmpFile(String fileName) {
/* 202 */     String strDir = System.getProperty("user.dir");
/*     */     
/* 204 */     String folderpath = strDir;
/* 205 */     String filepath = String.valueOf(folderpath) + File.separatorChar + fileName + ".tmp";
/* 206 */     return readFile(filepath);
/*     */   }
/*     */ }


/* Location:              D:\User\Maven\repository\net\mingsoft\ms-util\1.0.1\ms-util-1.0.1.jar!\com\mingsof\\util\FileUtil.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.2
 */