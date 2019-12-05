 package net.mingsoft.mdiy.entity;

 import cn.hutool.crypto.SecureUtil;
 import net.mingsoft.basic.entity.BaseEntity;
 import net.mingsoft.basic.util.StringUtil;































 public class FormEntity
   extends BaseEntity
 {
   private static final long serialVersionUID = 1502524709328L;
   private Integer formId;
   private String formTipsName;
   private String formTableName;
   private Integer dfManagerid;
   private Integer formAppId;
   private String formUrl;

   public void setFormId(Integer formId) { this.formId = formId; }






   public Integer getFormId() { return this.formId; }






   public void setFormTipsName(String formTipsName) { this.formTipsName = formTipsName; }






   public String getFormTipsName() { return this.formTipsName; }






   public void setFormTableName(String formTableName) { this.formTableName = formTableName; }






   public String getFormTableName() { return this.formTableName; }






   public void setDfManagerid(Integer dfManagerid) { this.dfManagerid = dfManagerid; }






   public Integer getDfManagerid() { return this.dfManagerid; }






   public void setFormAppId(Integer formAppId) { this.formAppId = formAppId; }






   public Integer getFormAppId() { return this.formAppId; }

   public String getFormUrl() {
     if (StringUtil.isInteger(this.formId)) {
       this.formUrl = SecureUtil.aes(SecureUtil.md5(getAppId() + "").substring(16).getBytes()).encryptHex(this.formId + "");
     }
     return this.formUrl;
   }


   public void setFormUrl(String formUrl) { this.formUrl = formUrl; }
 }


