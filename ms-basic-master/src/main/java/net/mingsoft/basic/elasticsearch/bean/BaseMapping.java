 package net.mingsoft.basic.elasticsearch.bean;

 import java.io.Serializable;

































 public abstract class BaseMapping
   implements Serializable
 {
   private String id;

   public String getId() { return this.id; }



   public void setId(String id) { this.id = id; }
 }


/* Location:              D:\User\Maven\repository\net\mingsoft\ms-basic\1.0.16\ms-basic-1.0.16.jar!\net\mingsoft\basic\elasticsearch\bean\BaseMapping.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */