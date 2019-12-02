 package net.mingsoft.basic.constant;

 import net.mingsoft.base.constant.e.BaseEnum;








 public enum ErrorCodeEnum
   implements BaseEnum
 {
   SUCCESS(Integer.valueOf(200)),



   SUCCESS_POST(Integer.valueOf(201)),




   SUCCESS_LOADING(Integer.valueOf(202)),




   SUCCESS_PART(Integer.valueOf(203)),



   SUCCESS_NO_RESPONSE(Integer.valueOf(204)),




   REDIRECT_REMOVE(Integer.valueOf(301)),



   REDIRECT_FIND(Integer.valueOf(302)),



   REDIRECT_OTHER(Integer.valueOf(303)),



   REDIRECT_NOT_CHANGED(Integer.valueOf(304)),



   CLIENT_REQUEST(Integer.valueOf(400)),



   CLIENT_UNAUTHORIZED(Integer.valueOf(401)),



   CLIENT_NEED_PAY(Integer.valueOf(402)),



   CLIENT_NOT_FIND(Integer.valueOf(404)),




   SERVER_ERROR(Integer.valueOf(500)),



   SERVER_NOT_SUPPORT(Integer.valueOf(501)),



   SERVER_ERROR_GATEWAY(Integer.valueOf(502)),



   SERVER_NOT_FIND(Integer.valueOf(503));
   private Object code;

   ErrorCodeEnum(Object code) { this.code = code; }






   public String toString() { return this.code.toString(); }



   public int toInt() { return Integer.parseInt(this.code.toString()); }
 }


/* Location:              D:\User\Maven\repository\net\mingsoft\ms-basic\1.0.16\ms-basic-1.0.16.jar!\net\mingsoft\basic\constant\ErrorCodeEnum.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */