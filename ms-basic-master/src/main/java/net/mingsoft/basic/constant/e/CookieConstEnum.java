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


/* Location:              D:\User\Maven\repository\net\mingsoft\ms-basic\1.0.16\ms-basic-1.0.16.jar!\net\mingsoft\basic\constant\e\CookieConstEnum.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */