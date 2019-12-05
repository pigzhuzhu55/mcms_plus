 package net.mingsoft.basic.bean;

 import java.util.List;
























 public class CityBean
 {
   private Long id;
   private String name;
   private Long parentId;
   private List<CityBean> childrensList;

   public Long getId() { return this.id; }



   public void setId(Long id) { this.id = id; }



   public String getName() { return this.name; }



   public void setName(String name) { this.name = name; }



   public Long getParentId() { return this.parentId; }



   public void setParentId(Long parentId) { this.parentId = parentId; }



   public List<CityBean> getChildrensList() { return this.childrensList; }



   public void setChildrensList(List<CityBean> childrensList) { this.childrensList = childrensList; }
 }


