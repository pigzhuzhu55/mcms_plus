 package net.mingsoft.basic.elasticsearch.bean;

 import java.io.Serializable;

































 public abstract class BaseMapping
   implements Serializable
 {
   private String id;

   public String getId() { return this.id; }



   public void setId(String id) { this.id = id; }
 }


