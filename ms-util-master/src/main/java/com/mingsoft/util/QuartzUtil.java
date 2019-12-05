 package com.mingsoft.util;

 import org.quartz.CronScheduleBuilder;
 import org.quartz.CronTrigger;
 import org.quartz.JobBuilder;
 import org.quartz.JobDetail;
 import org.quartz.JobKey;
 import org.quartz.ScheduleBuilder;
 import org.quartz.Scheduler;
 import org.quartz.SchedulerFactory;
 import org.quartz.Trigger;
 import org.quartz.TriggerBuilder;
 import org.quartz.TriggerKey;
 import org.quartz.impl.StdSchedulerFactory;




























 public class QuartzUtil
 {
   private static SchedulerFactory gSchedulerFactory = (SchedulerFactory)new StdSchedulerFactory();


   private static String JOB_GROUP_NAME = "EXTJWEB_JOBGROUP_NAME";
   private static String TRIGGER_GROUP_NAME = "EXTJWEB_TRIGGERGROUP_NAME";












   public static void addJob(String jobName, Class cls, String time) {
     try {
       Scheduler sched = gSchedulerFactory.getScheduler();
       JobDetail jobDetail = JobBuilder.newJob(cls).withIdentity(jobName, JOB_GROUP_NAME).build();
       Trigger trigger = TriggerBuilder.newTrigger().withIdentity(new TriggerKey(jobName, TRIGGER_GROUP_NAME)).startNow().withSchedule((ScheduleBuilder)CronScheduleBuilder.cronSchedule(time)).build();
       sched.scheduleJob(jobDetail, trigger);

       if (!sched.isShutdown()) {
         sched.start();
       }
     } catch (Exception e) {
       throw new RuntimeException(e);
     }
   }


















   public static void addJob(String jobName, String jobGroupName, String triggerName, String triggerGroupName, Class jobClass, String time) {
     try {
       Scheduler sched = gSchedulerFactory.getScheduler();
       JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(jobName, jobGroupName).build();
       Trigger trigger = TriggerBuilder.newTrigger().withIdentity(new TriggerKey(triggerName, triggerGroupName)).startNow().withSchedule((ScheduleBuilder)CronScheduleBuilder.cronSchedule(time)).build();
       sched.scheduleJob(jobDetail, trigger);
     } catch (Exception e) {
       throw new RuntimeException(e);
     }
   }








   public static void modifyJobTime(String jobName, String time) {
     try {
       Scheduler sched = gSchedulerFactory.getScheduler();
       TriggerKey triggerKey = TriggerKey.triggerKey(jobName, TRIGGER_GROUP_NAME);
       CronTrigger trigger = (CronTrigger)sched.getTrigger(triggerKey);
       if (trigger == null) {
         return;
       }
       String oldTime = trigger.getCronExpression();
       if (!oldTime.equalsIgnoreCase(time)) {
         JobKey job = JobKey.jobKey(jobName);
         JobDetail jobDetail = sched.getJobDetail(job);
         Class objJobClass = jobDetail.getJobClass();
         removeJob(jobName);
         addJob(jobName, objJobClass, time);
       }
     } catch (Exception e) {
       throw new RuntimeException(e);
     }
   }








   public static void modifyJobTime(String triggerName, String triggerGroupName, String time) {
     try {
       Scheduler sched = gSchedulerFactory.getScheduler();
       TriggerKey triggerKey = TriggerKey.triggerKey(triggerName);
       CronTrigger trigger = (CronTrigger)sched.getTrigger(triggerKey);
       if (trigger == null) {
         return;
       }
       String oldTime = trigger.getCronExpression();
       if (!oldTime.equalsIgnoreCase(time)) {
         trigger.getTriggerBuilder().startNow().withSchedule((ScheduleBuilder)CronScheduleBuilder.cronSchedule(time));

         sched.resumeTrigger(triggerKey);
       }
     } catch (Exception e) {
       throw new RuntimeException(e);
     }
   }






   public static void removeJob(String jobName) {
     try {
       Scheduler sched = gSchedulerFactory.getScheduler();
       JobKey job = JobKey.jobKey(jobName, JOB_GROUP_NAME);
       TriggerKey triggerKey = TriggerKey.triggerKey(jobName, TRIGGER_GROUP_NAME);
       sched.pauseTrigger(triggerKey);
       sched.unscheduleJob(triggerKey);
       sched.deleteJob(job);
     } catch (Exception e) {
       throw new RuntimeException(e);
     }
   }









   public static void removeJob(String jobName, String jobGroupName, String triggerName, String triggerGroupName) {
     try {
       Scheduler sched = gSchedulerFactory.getScheduler();
       JobKey job = JobKey.jobKey(jobName, jobGroupName);
       TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroupName);
       sched.pauseTrigger(triggerKey);
       sched.unscheduleJob(triggerKey);
       sched.deleteJob(job);
     } catch (Exception e) {
       throw new RuntimeException(e);
     }
   }




   public static void startJobs() {
     try {
       Scheduler sched = gSchedulerFactory.getScheduler();
       sched.start();
     } catch (Exception e) {
       throw new RuntimeException(e);
     }
   }





   public static void shutdownJobs() {
     try {
       Scheduler sched = gSchedulerFactory.getScheduler();
       if (!sched.isShutdown()) {
         sched.shutdown();
       }
     } catch (Exception e) {
       throw new RuntimeException(e);
     }
   }
 }


