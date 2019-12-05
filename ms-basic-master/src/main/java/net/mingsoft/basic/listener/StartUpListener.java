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


