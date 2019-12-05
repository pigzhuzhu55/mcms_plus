 package net.mingsoft.basic.entity;

 import java.sql.Timestamp;
 import java.util.List;
 import net.mingsoft.base.constant.e.BaseEnum;
 import net.mingsoft.base.entity.BaseEntity;
 import org.apache.commons.lang3.StringUtils;































































 public class ModelEntity
   extends BaseEntity
 {
   private int modelId;
   private String modelTitle;
   private Timestamp modelDatetime;
   private int modelModelId;
   private String modelUrl;
   private String modelCode;
   private String isChild;
   private String modelIcon = null;




   private int modelManagerId;




   private int modelSort;




   private List<ModelEntity> modelChildList;




   private Integer modelIsMenu;




   private int chick;




   private int childNum;



   private int depth;



   private String modelParentIds;




   public int getChildNum() { return this.childNum; }







   public void setChildNum(int childNum) { this.childNum = childNum; }







   public int getDepth() {
     if (StringUtils.isNotEmpty(this.modelParentIds)) {
       return this.depth = (this.modelParentIds.split(",")).length;
     }
     return this.depth;
   }









   public void setDepth(int depth) { this.depth = depth; }



   public Integer getModelIsMenu() { return this.modelIsMenu; }



   public void setModelIsMenu(Integer modelIsMenu) { this.modelIsMenu = modelIsMenu; }








   public String getModelCode() { return this.modelCode; }







   public void setModelCode(String modelCode) { this.modelCode = modelCode; }







   public String getModelIcon() { return this.modelIcon; }







   public void setModelIcon(String modelIcon) { this.modelIcon = modelIcon; }







   public int getModelModelId() { return this.modelModelId; }







   public void setModelModelId(int modelModelId) { this.modelModelId = modelModelId; }







   public String getModelUrl() { return this.modelUrl; }







   public void setModelUrl(String modelUrl) { this.modelUrl = modelUrl; }







   public int getModelId() { return this.modelId; }







   public void setModelId(int modelId) { this.modelId = modelId; }







   public Timestamp getModelDatetime() { return this.modelDatetime; }







   public void setModelDatetime(Timestamp modelDatetime) { this.modelDatetime = modelDatetime; }







   public String getModelTitle() { return this.modelTitle; }







   public void setModelTitle(String modelTitle) { this.modelTitle = modelTitle; }







   public int getModelManagerId() { return this.modelManagerId; }







   public void setModelManagerId(int modelManagerId) { this.modelManagerId = modelManagerId; }



   public int getModelSort() { return this.modelSort; }



   public void setModelSort(int modelSort) { this.modelSort = modelSort; }



   public List<ModelEntity> getModelChildList() { return this.modelChildList; }



   public void setModelChildList(List<ModelEntity> modelChildList) { this.modelChildList = modelChildList; }



   public int getChick() { return this.chick; }



   public void setChick(int chick) { this.chick = chick; }




   public String getModelParentIds() { return this.modelParentIds; }



   public void setModelParentIds(String modelParentIds) { this.modelParentIds = modelParentIds; }






   public String getIsChild() { return this.isChild; }





   public void setIsChild(String isChild) { this.isChild = isChild; }

   public enum IsMenu
     implements BaseEnum
   {
     NO(0),
     YES(1);
     private int id;

     IsMenu(int id) { this.id = id; }




     public int toInt() { return this.id; }
   }
 }


