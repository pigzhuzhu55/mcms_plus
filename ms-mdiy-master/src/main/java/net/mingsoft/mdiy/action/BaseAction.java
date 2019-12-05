 package net.mingsoft.mdiy.action;

 import java.util.MissingResourceException;
 import net.mingsoft.mdiy.constant.Const;






 public class BaseAction
   extends net.mingsoft.basic.action.BaseAction
 {
   protected String getResString(String key) {
     String str = "";
     try {
       str = super.getResString(key);
     } catch (MissingResourceException e) {
       str = Const.RESOURCES.getString(key);
     }

     return str;
   }
 }


