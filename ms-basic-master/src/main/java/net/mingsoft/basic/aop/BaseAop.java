 package net.mingsoft.basic.aop;

 import org.aspectj.lang.JoinPoint;
 import org.slf4j.Logger;
 import org.slf4j.LoggerFactory;
































 public abstract class BaseAop
 {
   protected final Logger LOG = LoggerFactory.getLogger(getClass());

   protected final <T> T getType(JoinPoint jp, Class<T> clazz) {
     Object[] objs = jp.getArgs();
     for (Object obj : objs) {
       if (obj.getClass() == clazz) {
         return (T)obj;
       }
     }
     return null;
   }

   protected final <T> T getType(JoinPoint jp, Class<T> clazz, boolean hasParent) {
     Object[] objs = jp.getArgs();
     for (Object obj : objs) {
       if (obj.getClass() == clazz || obj.getClass().getSuperclass() == clazz) {
         return (T)obj;
       }
     }
     return null;
   }
 }


