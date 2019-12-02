 package net.mingsoft.basic.listener;

 import javax.servlet.ServletContextEvent;
 import javax.servlet.ServletContextListener;
 import org.slf4j.Logger;
 import org.slf4j.LoggerFactory;



























 public class StartUpListener
   implements ServletContextListener
 {
   protected final Logger LOG = LoggerFactory.getLogger(getClass());

   public void contextInitialized(ServletContextEvent sce) {}

   public void contextDestroyed(ServletContextEvent sce) {}
 }


/* Location:              D:\User\Maven\repository\net\mingsoft\ms-basic\1.0.16\ms-basic-1.0.16.jar!\net\mingsoft\basic\listener\StartUpListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */