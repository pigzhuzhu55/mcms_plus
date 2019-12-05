/*    */ package com.baidu.ueditor.upload;
/*    */ 
/*    */ import com.baidu.ueditor.PathFormat;
/*    */ import com.baidu.ueditor.define.BaseState;
/*    */ import com.baidu.ueditor.define.FileType;
/*    */ import com.baidu.ueditor.define.State;
/*    */ import java.util.Map;
/*    */ import org.apache.commons.codec.binary.Base64;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class Base64Uploader
/*    */ {
/*    */   public static State save(String content, Map<String, Object> conf) {
/* 17 */     byte[] data = decode(content);
/*    */     
/* 19 */     long maxSize = ((Long)conf.get("maxSize")).longValue();
/*    */     
/* 21 */     if (!validSize(data, maxSize)) {
/* 22 */       return (State)new BaseState(false, 1);
/*    */     }
/*    */     
/* 25 */     String suffix = FileType.getSuffix("JPG");
/*    */     
/* 27 */     String savePath = PathFormat.parse((String)conf.get("savePath"), 
/* 28 */         (String)conf.get("filename"));
/*    */     
/* 30 */     savePath = String.valueOf(savePath) + suffix;
/* 31 */     String physicalPath = String.valueOf(conf.get("rootPath")) + savePath;
/*    */     
/* 33 */     State storageState = StorageManager.saveBinaryFile(data, physicalPath);
/*    */     
/* 35 */     if (storageState.isSuccess()) {
/* 36 */       storageState.putInfo("url", PathFormat.format(savePath));
/* 37 */       storageState.putInfo("type", suffix);
/* 38 */       storageState.putInfo("original", "");
/*    */     } 
/*    */     
/* 41 */     return storageState;
/*    */   }
/*    */ 
/*    */   
/* 45 */   private static byte[] decode(String content) { return Base64.decodeBase64(content); }
/*    */ 
/*    */ 
/*    */   
/* 49 */   private static boolean validSize(byte[] data, long length) { return (data.length <= length); }
/*    */ }


/* Location:              D:\User\Maven\repository\net\mingsoft\ms-ueditor\1.0.4\ms-ueditor-1.0.4.jar!\com\baid\\uedito\\upload\Base64Uploader.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.2
 */