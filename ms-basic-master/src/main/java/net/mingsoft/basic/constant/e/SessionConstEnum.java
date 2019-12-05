 package net.mingsoft.basic.constant.e;

 import net.mingsoft.base.constant.e.BaseSessionEnum;
































 public enum SessionConstEnum
   implements BaseSessionEnum
 {
   MODEL_ID_SESSION("model_id_session"),




   MODEL_TITLE_SESSION("model_title_session"),




   MANAGER_SESSION("manager_session"),




   CODE_SESSION("rand_code"),




   MANAGER_ROLE_MODEL_ESSION("manager_role_model_session"),




   MANAGER_MODEL_CODE("manager_model_code"),



   EXCEPTOIN("ms_exception");



   private String attr;




   SessionConstEnum(String attr) { this.attr = attr; }












   public String toString() { return this.attr; }
 }


