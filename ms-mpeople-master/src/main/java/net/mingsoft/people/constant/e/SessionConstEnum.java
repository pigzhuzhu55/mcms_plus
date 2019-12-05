 package net.mingsoft.people.constant.e;

 import net.mingsoft.base.constant.e.BaseSessionEnum;






























 public enum SessionConstEnum
   implements BaseSessionEnum
 {
   PEOPLE_SESSION("people_session"),




   PEOPLE_GET_PASSWORD_SESSION("people_get_password_session"),




   PEOPLE_EXISTS_SESSION("people_exists_session"),





   PEOPLE_RESET_PASSWORD_SESSION("people_reset_password_session"),




   PEOPEL_SET_PHONE_SESSION("people_set_phone_seesion"),




   SEND_CODE_SESSION("send_code_seesion");



   private String attr;




   SessionConstEnum(String attr) { this.attr = attr; }












   public String toString() { return this.attr; }
 }


