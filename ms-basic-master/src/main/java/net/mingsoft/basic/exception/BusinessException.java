 package net.mingsoft.basic.exception;





 public class BusinessException
   extends RuntimeException
 {
   private String bizCode;
   private String bizMsg;

   public BusinessException(String msg) { super(msg); }







   public BusinessException(String code, String msg) {
     super(msg);
     this.bizCode = code;
     this.bizMsg = msg;
   }


   public String getBizCode() { return this.bizCode; }



   public void setBizCode(String bizCode) { this.bizCode = bizCode; }



   public String getBizMsg() { return this.bizMsg; }



   public void setBizMsg(String bizMsg) { this.bizMsg = bizMsg; }
 }


