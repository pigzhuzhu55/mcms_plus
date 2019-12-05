/*     */ package com.mingsoft.util;
/*     */ 
/*     */ import org.quartz.CronScheduleBuilder;
/*     */ import org.quartz.CronTrigger;
/*     */ import org.quartz.JobBuilder;
/*     */ import org.quartz.JobDetail;
/*     */ import org.quartz.JobKey;
/*     */ import org.quartz.ScheduleBuilder;
/*     */ import org.quartz.Scheduler;
/*     */ import org.quartz.SchedulerFactory;
/*     */ import org.quartz.Trigger;
/*     */ import org.quartz.TriggerBuilder;
/*     */ import org.quartz.TriggerKey;
/*     */ import org.quartz.impl.StdSchedulerFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class QuartzUtil
/*     */ {
/*  45 */   private static SchedulerFactory gSchedulerFactory = (SchedulerFactory)new StdSchedulerFactory();
/*     */ 
/*     */   
/*  48 */   private static String JOB_GROUP_NAME = "EXTJWEB_JOBGROUP_NAME";
/*  49 */   private static String TRIGGER_GROUP_NAME = "EXTJWEB_TRIGGERGROUP_NAME";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void addJob(String jobName, Class cls, String time) {
/*     */     try {
/*  64 */       Scheduler sched = gSchedulerFactory.getScheduler();
/*  65 */       JobDetail jobDetail = JobBuilder.newJob(cls).withIdentity(jobName, JOB_GROUP_NAME).build();
/*  66 */       Trigger trigger = TriggerBuilder.newTrigger().withIdentity(new TriggerKey(jobName, TRIGGER_GROUP_NAME)).startNow().withSchedule((ScheduleBuilder)CronScheduleBuilder.cronSchedule(time)).build();
/*  67 */       sched.scheduleJob(jobDetail, trigger);
/*     */       
/*  69 */       if (!sched.isShutdown()) {
/*  70 */         sched.start();
/*     */       }
/*  72 */     } catch (Exception e) {
/*  73 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void addJob(String jobName, String jobGroupName, String triggerName, String triggerGroupName, Class jobClass, String time) {
/*     */     try {
/*  96 */       Scheduler sched = gSchedulerFactory.getScheduler();
/*  97 */       JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(jobName, jobGroupName).build();
/*  98 */       Trigger trigger = TriggerBuilder.newTrigger().withIdentity(new TriggerKey(triggerName, triggerGroupName)).startNow().withSchedule((ScheduleBuilder)CronScheduleBuilder.cronSchedule(time)).build();
/*  99 */       sched.scheduleJob(jobDetail, trigger);
/* 100 */     } catch (Exception e) {
/* 101 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void modifyJobTime(String jobName, String time) {
/*     */     try {
/* 114 */       Scheduler sched = gSchedulerFactory.getScheduler();
/* 115 */       TriggerKey triggerKey = TriggerKey.triggerKey(jobName, TRIGGER_GROUP_NAME);
/* 116 */       CronTrigger trigger = (CronTrigger)sched.getTrigger(triggerKey);
/* 117 */       if (trigger == null) {
/*     */         return;
/*     */       }
/* 120 */       String oldTime = trigger.getCronExpression();
/* 121 */       if (!oldTime.equalsIgnoreCase(time)) {
/* 122 */         JobKey job = JobKey.jobKey(jobName);
/* 123 */         JobDetail jobDetail = sched.getJobDetail(job);
/* 124 */         Class objJobClass = jobDetail.getJobClass();
/* 125 */         removeJob(jobName);
/* 126 */         addJob(jobName, objJobClass, time);
/*     */       } 
/* 128 */     } catch (Exception e) {
/* 129 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void modifyJobTime(String triggerName, String triggerGroupName, String time) {
/*     */     try {
/* 142 */       Scheduler sched = gSchedulerFactory.getScheduler();
/* 143 */       TriggerKey triggerKey = TriggerKey.triggerKey(triggerName);
/* 144 */       CronTrigger trigger = (CronTrigger)sched.getTrigger(triggerKey);
/* 145 */       if (trigger == null) {
/*     */         return;
/*     */       }
/* 148 */       String oldTime = trigger.getCronExpression();
/* 149 */       if (!oldTime.equalsIgnoreCase(time)) {
/* 150 */         trigger.getTriggerBuilder().startNow().withSchedule((ScheduleBuilder)CronScheduleBuilder.cronSchedule(time));
/*     */         
/* 152 */         sched.resumeTrigger(triggerKey);
/*     */       } 
/* 154 */     } catch (Exception e) {
/* 155 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void removeJob(String jobName) {
/*     */     try {
/* 166 */       Scheduler sched = gSchedulerFactory.getScheduler();
/* 167 */       JobKey job = JobKey.jobKey(jobName, JOB_GROUP_NAME);
/* 168 */       TriggerKey triggerKey = TriggerKey.triggerKey(jobName, TRIGGER_GROUP_NAME);
/* 169 */       sched.pauseTrigger(triggerKey);
/* 170 */       sched.unscheduleJob(triggerKey);
/* 171 */       sched.deleteJob(job);
/* 172 */     } catch (Exception e) {
/* 173 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void removeJob(String jobName, String jobGroupName, String triggerName, String triggerGroupName) {
/*     */     try {
/* 187 */       Scheduler sched = gSchedulerFactory.getScheduler();
/* 188 */       JobKey job = JobKey.jobKey(jobName, jobGroupName);
/* 189 */       TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroupName);
/* 190 */       sched.pauseTrigger(triggerKey);
/* 191 */       sched.unscheduleJob(triggerKey);
/* 192 */       sched.deleteJob(job);
/* 193 */     } catch (Exception e) {
/* 194 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void startJobs() {
/*     */     try {
/* 203 */       Scheduler sched = gSchedulerFactory.getScheduler();
/* 204 */       sched.start();
/* 205 */     } catch (Exception e) {
/* 206 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void shutdownJobs() {
/*     */     try {
/* 216 */       Scheduler sched = gSchedulerFactory.getScheduler();
/* 217 */       if (!sched.isShutdown()) {
/* 218 */         sched.shutdown();
/*     */       }
/* 220 */     } catch (Exception e) {
/* 221 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\User\Maven\repository\net\mingsoft\ms-util\1.0.1\ms-util-1.0.1.jar!\com\mingsof\\util\QuartzUtil.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.2
 */