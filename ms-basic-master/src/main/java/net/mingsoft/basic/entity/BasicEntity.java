 package net.mingsoft.basic.entity;

 import com.fasterxml.jackson.annotation.JsonInclude;
 import java.sql.Timestamp;
 import java.util.Date;
 import java.util.Map;
 import net.mingsoft.base.entity.BaseEntity;
 import org.apache.commons.beanutils.ConvertUtils;
 import org.apache.commons.lang3.StringUtils;






















































































 @JsonInclude(JsonInclude.Include.NON_NULL)
 public class BasicEntity
   extends BaseEntity
 {
   private int basicId;
   private String basicTitle;
   private String basicDescription;
   private String basicThumbnails;
   private String basicPic;
   private String basicType;
   private int[] basicTypeIds;
   private int basicHit;
   private Timestamp basicDateTime;
   private Date basicUpdateTime;
   private int basicPeopleId;
   private int basicSort;
   private int basicCategoryId = 0;




   private int basicAppId;




   private int basicModelId;




   private int basicComment;




   private int basicCollect;



   private int basicShare;



   private int basicDisplay;



   private Map<Object, Object> extendsFields;




   public int getBasicModelId() { return this.basicModelId; }



   public void setBasicModelId(int basicModelId) { this.basicModelId = basicModelId; }



   public int getBasicAppId() { return this.basicAppId; }








   public int getBasicCategoryId() { return this.basicCategoryId; }








   public Timestamp getBasicDateTime() { return this.basicDateTime; }








   public String getBasicDescription() { return this.basicDescription; }








   public int getBasicHit() { return this.basicHit; }








   public int getBasicId() { return this.basicId; }








   public int getBasicPeopleId() { return this.basicPeopleId; }








   public String getBasicThumbnails() { return this.basicThumbnails; }








   public String getBasicTitle() { return this.basicTitle; }








   public Date getBasicUpdateTime() { return this.basicUpdateTime; }



   public void setBasicAppId(int basicAppId) { this.basicAppId = basicAppId; }








   public void setBasicCategoryId(int basicCategoryId) { this.basicCategoryId = basicCategoryId; }








   public void setBasicDateTime(Timestamp basicDateTime) { this.basicDateTime = basicDateTime; }








   public void setBasicDescription(String basicDescription) { this.basicDescription = basicDescription; }








   public void setBasicHit(int basicHit) { this.basicHit = basicHit; }








   public void setBasicId(int basicId) { this.basicId = basicId; }








   public void setBasicPeopleId(int basicPeopleId) { this.basicPeopleId = basicPeopleId; }








   public void setBasicThumbnails(String basicThumbnails) { this.basicThumbnails = basicThumbnails; }








   public void setBasicTitle(String basicTitle) { this.basicTitle = basicTitle; }








   public void setBasicUpdateTime(Date basicUpdateTime) { this.basicUpdateTime = basicUpdateTime; }



   public int getBasicSort() { return this.basicSort; }



   public void setBasicSort(int basicSort) { this.basicSort = basicSort; }



   public Map<Object, Object> getExtendsFields() { return this.extendsFields; }



   public void setExtendsFields(Map<Object, Object> extendsFields) { this.extendsFields = extendsFields; }


   public String getBasicPic() {
     if (!StringUtils.isEmpty(this.basicThumbnails)) {
       String[] pics = this.basicThumbnails.split("\\|");
       return pics[0];
     }
     return "";
   }


   public void setBasicPic(String basicPic) { this.basicPic = basicPic; }



   public int getBasicComment() { return this.basicComment; }



   public void setBasicComment(int basicComment) { this.basicComment = basicComment; }



   public int getBasicCollect() { return this.basicCollect; }



   public void setBasicCollect(int basicCollect) { this.basicCollect = basicCollect; }



   public int getBasicShare() { return this.basicShare; }



   public void setBasicShare(int basicShare) { this.basicShare = basicShare; }



   public String getBasicType() { return this.basicType; }



   public void setBasicType(String basicType) { this.basicType = basicType; }


   public int[] getBasicTypeIds() {
     if (StringUtils.isEmpty(this.basicType)) {
       return new int[0];
     }
     return (int[])ConvertUtils.convert(this.basicType.split(","), int.class);
   }


   public void setBasicTypeIds(int[] basicTypeIds) { this.basicTypeIds = basicTypeIds; }



   public int getBasicDisplay() { return this.basicDisplay; }



   public void setBasicDisplay(int basicDisplay) { this.basicDisplay = basicDisplay; }
 }


