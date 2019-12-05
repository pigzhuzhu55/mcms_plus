/*    */ package com.baidu.ueditor.define;
/*    */ 
/*    */ import com.baidu.ueditor.Encoder;
/*    */ import java.util.HashMap;
/*    */ import java.util.Iterator;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class BaseState
/*    */   implements State
/*    */ {
/*    */   private boolean state = false;
/* 12 */   private String info = null;
/*    */   
/* 14 */   private Map<String, String> infoMap = new HashMap<>();
/*    */ 
/*    */   
/* 17 */   public BaseState() { this.state = true; }
/*    */ 
/*    */ 
/*    */   
/* 21 */   public BaseState(boolean state) { setState(state); }
/*    */ 
/*    */   
/*    */   public BaseState(boolean state, String info) {
/* 25 */     setState(state);
/* 26 */     this.info = info;
/*    */   }
/*    */   
/*    */   public BaseState(boolean state, int infoCode) {
/* 30 */     setState(state);
/* 31 */     this.info = AppInfo.getStateInfo(infoCode);
/*    */   }
/*    */ 
/*    */   
/* 35 */   public boolean isSuccess() { return this.state; }
/*    */ 
/*    */ 
/*    */   
/* 39 */   public void setState(boolean state) { this.state = state; }
/*    */ 
/*    */ 
/*    */   
/* 43 */   public void setInfo(String info) { this.info = info; }
/*    */ 
/*    */ 
/*    */   
/* 47 */   public void setInfo(int infoCode) { this.info = AppInfo.getStateInfo(infoCode); }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 52 */   public String toJSONString() { return toString(); }
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 57 */     String key = null;
/* 58 */     String stateVal = isSuccess() ? AppInfo.getStateInfo(0) : this.info;
/*    */     
/* 60 */     StringBuilder builder = new StringBuilder();
/*    */     
/* 62 */     builder.append("{\"state\": \"" + stateVal + "\"");
/*    */     
/* 64 */     Iterator<String> iterator = this.infoMap.keySet().iterator();
/*    */     
/* 66 */     while (iterator.hasNext()) {
/*    */       
/* 68 */       key = iterator.next();
/*    */       
/* 70 */       builder.append(",\"" + key + "\": \"" + (String)this.infoMap.get(key) + "\"");
/*    */     } 
/*    */ 
/*    */     
/* 74 */     builder.append("}");
/*    */     
/* 76 */     return Encoder.toUnicode(builder.toString());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 82 */   public void putInfo(String name, String val) { this.infoMap.put(name, val); }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 87 */   public void putInfo(String name, long val) { putInfo(name, (new StringBuilder(String.valueOf(val))).toString()); }
/*    */ }


/* Location:              D:\User\Maven\repository\net\mingsoft\ms-ueditor\1.0.4\ms-ueditor-1.0.4.jar!\com\baid\\ueditor\define\BaseState.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.2
 */