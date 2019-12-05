 package net.mingsoft.base.job;

 import org.quartz.Job;
 import org.slf4j.Logger;
 import org.slf4j.LoggerFactory;

































 public abstract class BaseJob
   implements Job
 {
   protected final Logger LOG = LoggerFactory.getLogger(getClass());
 }


