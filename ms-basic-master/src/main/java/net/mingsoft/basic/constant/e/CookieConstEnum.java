 package net.mingsoft.basic.constant.e;

 import net.mingsoft.base.constant.e.BaseCookieEnum;
































 public enum CookieConstEnum
   implements BaseCookieEnum
 {
   PAGENO_COOKIE("pageno_cookie"),




   BACK_COOKIE("back_cookie"),




   BASIC_HIT("basic_hit");


   private String attr;



   CookieConstEnum(String attr) { this.attr = attr; }











   public String toString() { return this.attr; }
 }


