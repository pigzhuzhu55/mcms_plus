/*     */ package com.mingsoft.util;
/*     */ 
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.BufferedOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Enumeration;
/*     */ import java.util.List;
/*     */ import java.util.zip.ZipEntry;
/*     */ import java.util.zip.ZipOutputStream;
/*     */ import org.apache.tools.zip.ZipEntry;
/*     */ import org.apache.tools.zip.ZipFile;
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
/*     */ public class ZipUtil
/*     */ {
/*     */   private static final int buffer = 2048;
/*     */   
/*     */   public static void unZipFiles(File zipFile, String descDir) throws IOException {
/*  39 */     File pathFile = new File(descDir);
/*  40 */     if (!pathFile.exists()) {
/*  41 */       pathFile.mkdirs();
/*     */     }
/*  43 */     ZipFile zip = new ZipFile(zipFile);
/*  44 */     for (Enumeration<ZipEntry> entries = zip.getEntries(); entries.hasMoreElements(); ) {
/*  45 */       ZipEntry entry = entries.nextElement();
/*  46 */       String zipEntryName = entry.getName();
/*  47 */       InputStream in = zip.getInputStream(entry);
/*  48 */       String outPath = (String.valueOf(descDir) + zipEntryName).replaceAll("\\*", "/");
/*  49 */       outPath = new String(outPath.getBytes("utf-8"), "ISO8859-1");
/*     */       
/*  51 */       File file = new File(outPath.substring(0, outPath.lastIndexOf('/')));
/*  52 */       if (!file.exists()) {
/*  53 */         file.mkdirs();
/*     */       }
/*     */       
/*  56 */       if ((new File(outPath)).isDirectory()) {
/*     */         continue;
/*     */       }
/*  59 */       OutputStream out = new FileOutputStream(outPath);
/*  60 */       byte[] buf1 = new byte[1024];
/*     */       int len;
/*  62 */       while ((len = in.read(buf1)) > 0) {
/*  63 */         out.write(buf1, 0, len);
/*     */       }
/*  65 */       in.close();
/*  66 */       out.close();
/*     */     } 
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
/*     */   public static void compress(String source, String destinct) throws IOException {
/*  80 */     List<File> fileList = loadFilename(new File(source));
/*  81 */     ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(new File(destinct)));
/*     */     
/*  83 */     byte[] buffere = new byte[8192];
/*     */ 
/*     */ 
/*     */     
/*  87 */     for (int i = 0; i < fileList.size(); i++) {
/*  88 */       File file = fileList.get(i);
/*  89 */       zos.putNextEntry((ZipEntry)new ZipEntry(getEntryName(source, file)));
/*  90 */       BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
/*     */       
/*     */       while (true) {
/*  93 */         int length = bis.read(buffere);
/*  94 */         if (length == -1)
/*     */           break; 
/*  96 */         zos.write(buffere, 0, length);
/*     */       } 
/*  98 */       bis.close();
/*  99 */       zos.closeEntry();
/*     */     } 
/* 101 */     zos.close();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static List loadFilename(File file) {
/* 111 */     List<File> filenameList = new ArrayList();
/* 112 */     if (file.isFile()) {
/* 113 */       filenameList.add(file);
/*     */     }
/* 115 */     if (file.isDirectory()) {
/* 116 */       byte b; int i; File[] arrayOfFile; for (i = (arrayOfFile = file.listFiles()).length, b = 0; b < i; ) { File f = arrayOfFile[b];
/* 117 */         filenameList.addAll(loadFilename(f)); b++; }
/*     */     
/*     */     } 
/* 120 */     return filenameList;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String getEntryName(String base, File file) {
/* 131 */     File baseFile = new File(base);
/* 132 */     String filename = file.getPath();
/*     */     
/* 134 */     if (baseFile.getParentFile().getParentFile() == null)
/* 135 */       return filename.substring(baseFile.getParent().length()); 
/* 136 */     return filename.substring(baseFile.getParent().length() + 1);
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
/*     */   public static void unZip(String path, String savepath) {
/* 148 */     int count = -1;
/* 149 */     File file = null;
/* 150 */     InputStream is = null;
/* 151 */     FileOutputStream fos = null;
/* 152 */     BufferedOutputStream bos = null;
/* 153 */     (new File(savepath)).mkdir();
/* 154 */     ZipFile zipFile = null;
/*     */     try {
/* 156 */       zipFile = new ZipFile(path, "gbk");
/* 157 */       Enumeration<?> entries = zipFile.getEntries();
/*     */       
/* 159 */       while (entries.hasMoreElements()) {
/* 160 */         byte[] buf = new byte[2048];
/*     */         
/* 162 */         ZipEntry entry = (ZipEntry)entries.nextElement();
/*     */         
/* 164 */         String filename = entry.getName();
/* 165 */         boolean ismkdir = false;
/* 166 */         if (filename.lastIndexOf("/") != -1) {
/* 167 */           ismkdir = true;
/*     */         }
/* 169 */         filename = String.valueOf(savepath) + filename;
/*     */         
/* 171 */         if (entry.isDirectory()) {
/* 172 */           file = new File(filename);
/* 173 */           file.mkdirs();
/*     */           continue;
/*     */         } 
/* 176 */         file = new File(filename);
/* 177 */         if (!file.exists() && 
/* 178 */           ismkdir) {
/* 179 */           (new File(filename.substring(0, filename.lastIndexOf("/")))).mkdirs();
/*     */         }
/*     */         
/* 182 */         file.createNewFile();
/*     */         
/* 184 */         is = zipFile.getInputStream(entry);
/* 185 */         fos = new FileOutputStream(file);
/* 186 */         bos = new BufferedOutputStream(fos, 2048);
/*     */         
/* 188 */         while ((count = is.read(buf)) > -1) {
/* 189 */           bos.write(buf, 0, count);
/*     */         }
/* 191 */         bos.flush();
/* 192 */         bos.close();
/* 193 */         fos.close();
/*     */         
/* 195 */         is.close();
/*     */       } 
/*     */       
/* 198 */       zipFile.close();
/*     */     }
/* 200 */     catch (IOException ioe) {
/* 201 */       ioe.printStackTrace();
/*     */     } finally {
/*     */       try {
/* 204 */         if (bos != null) {
/* 205 */           bos.close();
/*     */         }
/* 207 */         if (fos != null) {
/* 208 */           fos.close();
/*     */         }
/* 210 */         if (is != null) {
/* 211 */           is.close();
/*     */         }
/* 213 */         if (zipFile != null) {
/* 214 */           zipFile.close();
/*     */         }
/* 216 */       } catch (Exception e) {
/* 217 */         e.printStackTrace();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\User\Maven\repository\net\mingsoft\ms-util\1.0.1\ms-util-1.0.1.jar!\com\mingsof\\util\ZipUtil.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.2
 */