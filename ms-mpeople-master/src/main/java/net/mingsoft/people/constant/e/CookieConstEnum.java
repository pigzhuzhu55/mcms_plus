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


