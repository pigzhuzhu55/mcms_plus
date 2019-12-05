 package net.mingsoft.mdiy.entity;

 import net.mingsoft.base.entity.BaseEntity;



































 public class PageEntity
   extends BaseEntity
 {
   private static final long serialVersionUID = 1502431314331L;
   private Integer pageId;
   private Integer pageModelId;
   private Integer pageAppId;
   private String pagePath;
   private String pageTitle;
   private String pageKey;

   public void setPageId(Integer pageId) { this.pageId = pageId; }






   public Integer getPageId() { return this.pageId; }






   public void setPageModelId(Integer pageModelId) { this.pageModelId = pageModelId; }






   public Integer getPageModelId() { return this.pageModelId; }






   public void setPageAppId(Integer pageAppId) { this.pageAppId = pageAppId; }






   public Integer getPageAppId() { return this.pageAppId; }






   public void setPagePath(String pagePath) { this.pagePath = pagePath; }






   public String getPagePath() { return this.pagePath; }






   public void setPageTitle(String pageTitle) { this.pageTitle = pageTitle; }






   public String getPageTitle() { return this.pageTitle; }






   public void setPageKey(String pageKey) { this.pageKey = pageKey; }






   public String getPageKey() { return this.pageKey; }
 }


