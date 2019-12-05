 package net.mingsoft.basic.constant;

 import java.util.ResourceBundle;
 import org.springframework.context.ApplicationContext;
































 public final class Const
 {
   public static final ResourceBundle RESOURCES = ResourceBundle.getBundle("net.mingsoft.basic.resources.resources");





   public static final int DEFAULT_SYSTEM_MANGER_ROLE_ID = 1;




   public static final int DEFAULT_WEBSITE_MANGER_ROLE_ID = 2;




   public static final int DEFAULT_CMS_MODEL_ID = 1;




   public static final int COLUMN_TOP_CATEGORY_ID = 0;




   @Deprecated
   public static String VIEW = "";
   public static String PROJECT_PATH;
   public static ApplicationContext CONTEXT;
   public static final String BASE = "base";
   public static final String BASE_PATH = "basePath";
   public static final String MANAGER_PATH = "managerPath";
   public static final String MODEL_ID = "modelId";
   public static final String PARAMS = "params";
 }


