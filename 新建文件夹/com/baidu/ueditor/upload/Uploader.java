/*    */ package com.baidu.ueditor.upload;
/*    */ 
/*    */ import com.baidu.ueditor.define.State;
/*    */ import java.util.Map;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ 
/*    */ public class Uploader {
/*  8 */   private HttpServletRequest request = null;
/*  9 */   private Map<String, Object> conf = null;
/*    */   
/*    */   public Uploader(HttpServletRequest request, Map<String, Object> conf) {
/* 12 */     this.request = request;
/* 13 */     this.conf = conf;
/*    */   }
/*    */   
/*    */   public final State doExec() {
/* 17 */     String filedName = (String)this.conf.get("fieldName");
/* 18 */     State state = null;
/*    */     
/* 20 */     if ("true".equals(this.conf.get("isBase64"))) {
/* 21 */       state = Base64Uploader.save(this.request.getParameter(filedName), 
/* 22 */           this.conf);
/*    */     } else {
/* 24 */       state = BinaryUploader.save(this.request, this.conf);
/*    */     } 
/*    */     
/* 27 */     return state;
/*    */   }
/*    */ }


/* Location:              D:\User\Maven\repository\net\mingsoft\ms-ueditor\1.0.4\ms-ueditor-1.0.4.jar!\com\baid\\uedito\\upload\Uploader.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.2
 */