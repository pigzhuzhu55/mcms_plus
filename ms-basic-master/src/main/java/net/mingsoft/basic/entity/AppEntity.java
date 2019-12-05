 package net.mingsoft.basic.entity;

 import cn.hutool.core.util.ObjectUtil;
 import java.util.Date;
 import net.mingsoft.base.entity.BaseEntity;
 import org.springframework.format.annotation.DateTimeFormat;























































 public class AppEntity
   extends BaseEntity
 {
   private String appName;
   private String appDescription;
   private String appLogo;
   private int appId;
   private String appStyle;
   private String appMobileStyle = "";




   private String appKeyword;




   private String appCopyright;




   private String appUrl;




   private int appManagerId;




   private Date appDatetime;




   @DateTimeFormat(pattern = "yyyy-MM-dd")
   private Date appPayDate;




   private String appPay;



   private String appLoginPage;




   public Date getAppPayDate() { return this.appPayDate; }



   public void setAppPayDate(Date appPayDate) { this.appPayDate = appPayDate; }



   public String getAppPay() { return this.appPay; }



   public void setAppPay(String appPay) { this.appPay = appPay; }



   public Date getAppDatetime() { return this.appDatetime; }



   public void setAppDatetime(Date appDatetime) { this.appDatetime = appDatetime; }








   public String getAppCopyright() { return this.appCopyright; }



   public String getAppDescription() { return this.appDescription; }



   public int getAppId() { return this.appId; }








   public String getAppKeyword() { return this.appKeyword; }








   public int getAppManagerId() { return this.appManagerId; }



   public String getAppName() { return this.appName; }








   public String getAppStyle() { return this.appStyle; }






   public String getAppUrl() {
     if (ObjectUtil.isNotNull(this.appUrl)) {
       return this.appUrl.replaceAll(" ", "");
     }
     return this.appUrl;
   }





   public String getAppHostUrl() {
     if (ObjectUtil.isNotNull(this.appUrl))
     {
       if (this.appUrl.indexOf("\n") > 0) {
         return this.appUrl.split("\n")[0].trim();
       }
     }
     return this.appUrl;
   }


   public String getAppLogo() { return this.appLogo; }








   public void setAppCopyright(String appCopyright) { this.appCopyright = appCopyright; }



   public void setAppDescription(String appDescription) { this.appDescription = appDescription; }



   public void setAppId(int appId) { this.appId = appId; }








   public void setAppKeyword(String appKeyword) { this.appKeyword = appKeyword; }






   public void setAppManagerId(int appManagerId) { this.appManagerId = appManagerId; }



   public void setAppName(String appName) { this.appName = appName; }








   public void setAppStyle(String appStyle) { this.appStyle = appStyle; }








   public void setAppUrl(String appUrl) { this.appUrl = appUrl; }




   public String getAppMobileStyle() { return this.appMobileStyle; }



   public void setAppMobileStyle(String appMobileStyle) { this.appMobileStyle = appMobileStyle; }



   public void setAppLogo(String appLogo) { this.appLogo = appLogo; }



   public String getAppLoginPage() { return this.appLoginPage; }



   public void setAppLoginPage(String appLoginPage) { this.appLoginPage = appLoginPage; }
 }


