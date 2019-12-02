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


/* Location:              D:\User\Maven\repository\net\mingsoft\ms-basic\1.0.16\ms-basic-1.0.16.jar!\net\mingsoft\basic\bean\CityBean.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */