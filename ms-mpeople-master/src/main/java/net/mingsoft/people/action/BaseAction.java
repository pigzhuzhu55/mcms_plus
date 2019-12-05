 package net.mingsoft.people.action;

 import com.alibaba.fastjson.JSONObject;
 import javax.servlet.http.HttpServletRequest;
 import net.mingsoft.base.constant.e.BaseSessionEnum;
 import net.mingsoft.basic.util.BasicUtil;
 import net.mingsoft.people.constant.Const;
 import net.mingsoft.people.constant.e.SessionConstEnum;
 import net.mingsoft.people.entity.PeopleEntity;

 public class BaseAction
   extends net.mingsoft.mdiy.action.BaseAction
 {
   protected String getResString(String key) { return getResString(key, Const.RESOURCES); }












   protected String getResString(String key, String... fullStrs) { return getResString(key, Const.RESOURCES, fullStrs); }






   @Deprecated
   protected PeopleEntity getPeopleBySession(HttpServletRequest request) {
     Object obj = BasicUtil.getSession((BaseSessionEnum)SessionConstEnum.PEOPLE_SESSION);
     if (obj != null && obj instanceof PeopleEntity)
       return (PeopleEntity)obj;
     if (obj instanceof String) {
       return (PeopleEntity)JSONObject.parseObject(obj.toString(), PeopleEntity.class);
     }
     return null;
   }




   protected PeopleEntity getPeopleBySession() {
     Object obj = BasicUtil.getSession((BaseSessionEnum)SessionConstEnum.PEOPLE_SESSION);
     if (obj != null && obj instanceof PeopleEntity)
       return (PeopleEntity)obj;
     if (obj instanceof String) {
       return (PeopleEntity)JSONObject.parseObject(obj.toString(), PeopleEntity.class);
     }
     return null;
   }









   protected void setPeopleBySession(HttpServletRequest request, PeopleEntity people) { BasicUtil.setSession((BaseSessionEnum)SessionConstEnum.PEOPLE_SESSION, people); }








   protected void removePeopleBySession(HttpServletRequest request) { BasicUtil.removeSession((BaseSessionEnum)SessionConstEnum.PEOPLE_SESSION); }
 }


