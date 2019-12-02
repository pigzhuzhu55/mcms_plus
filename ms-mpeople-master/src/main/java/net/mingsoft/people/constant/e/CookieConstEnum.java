 package net.mingsoft.people.constant.e;

 import net.mingsoft.base.constant.e.BaseCookieEnum;





















 public enum CookieConstEnum
   implements BaseCookieEnum
 {
   PEOPLE_COOKIE("people_cookie"),



   PEOPLE_LOGIN_URL("people_login_url");



   private String attr;




   CookieConstEnum(String attr) { this.attr = attr; }












   public String toString() { return this.attr; }
 }


/* Location:              D:\User\Maven\repository\net\mingsoft\ms-mpeople\1.0.11\ms-mpeople-1.0.11.jar!\net\mingsoft\people\constant\e\CookieConstEnum.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */