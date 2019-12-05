 package net.mingsoft.mdiy.util;

 import cn.hutool.core.util.ObjectUtil;
 import com.alibaba.fastjson.JSON;
 import java.util.ArrayList;
 import java.util.List;
 import net.mingsoft.base.entity.BaseEntity;
 import net.mingsoft.basic.util.BasicUtil;
 import net.mingsoft.basic.util.SpringUtil;
 import net.mingsoft.mdiy.biz.IDictBiz;
 import net.mingsoft.mdiy.entity.DictEntity;
 import org.apache.commons.lang3.StringUtils;







 public class DictUtil
 {
   public static DictEntity get(String dictLabel, String dictType) {
     DictEntity dict = new DictEntity();
     dict.setDictLabel(dictLabel);
     dict.setDictType(dictType);
     dict.setAppId(BasicUtil.getAppId());
     return (DictEntity)((IDictBiz)SpringUtil.getBean(IDictBiz.class)).getEntity((BaseEntity)dict);
   }






   public static DictEntity get(String dictType, String dictLabel, String dictValue) {
     DictEntity dict = new DictEntity();
     dict.setDictLabel(dictLabel);
     dict.setDictType(dictType);
     dict.setDictValue(dictValue);
     dict.setAppId(BasicUtil.getAppId());
     return (DictEntity)((IDictBiz)SpringUtil.getBean(IDictBiz.class)).getEntity((BaseEntity)dict);
   }






   public static String getToJson(String dictType, String dictLabel, String dictValue) {
     DictEntity dict = new DictEntity();
     dict.setDictLabel(dictLabel);
     dict.setDictType(dictType);
     dict.setDictValue(dictValue);
     dict.setAppId(BasicUtil.getAppId());
     DictEntity dictEntity = (DictEntity)((IDictBiz)SpringUtil.getBean(IDictBiz.class)).getEntity((BaseEntity)dict);
     return JSON.toJSONString(dictEntity);
   }





   public static List<DictEntity> list(String dictType) {
     DictEntity dict = new DictEntity();
     dict.setDictType(dictType);
     dict.setAppId(BasicUtil.getAppId());
     return ((IDictBiz)SpringUtil.getBean(IDictBiz.class)).query((BaseEntity)dict);
   }







   public static String listJson(String dictType) { return JSON.toJSONString(list(dictType)); }








   public static String getDictValue(String dictType, String dictLabel, String defaultValue) {
     if (StringUtils.isNotBlank(dictType) && StringUtils.isNotBlank(dictLabel)) {
       DictEntity dictEntity = get(dictType, dictLabel, null);
       if (ObjectUtil.isNotNull(dictEntity)) {
         return dictEntity.getDictValue();
       }
     }
     return defaultValue;
   }







   public static String getDictValue(String dictType, String dictLabel) { return getDictValue(dictType, dictLabel, ""); }







   public static String getDictLabel(String dictType, String dictValue, String defaultValue) {
     if (StringUtils.isNotBlank(dictType) && StringUtils.isNotBlank(dictValue)) {
       DictEntity dictEntity = get(dictType, null, dictValue);
       if (ObjectUtil.isNotNull(dictEntity)) {
         return dictEntity.getDictLabel();
       }
     }
     return defaultValue;
   }






   public static String getDictLabel(String dictType, String dictValue) { return getDictLabel(dictType, dictValue, ""); }








   public static String getDictLabels(String dictType, String dictValues, String defaultValue) {
     if (StringUtils.isNotBlank(dictType) && StringUtils.isNotBlank(dictValues)) {
       List<String> labels = new ArrayList();
       String[] values = dictValues.split(",");
       for (int i = 0; i < values.length; i++) {
         String value = values[i];
         String dictLabel = getDictLabel(dictType, value, defaultValue);
         if (!StringUtils.isBlank(dictLabel)) {
           labels.add(dictLabel);
         }
       }
       return StringUtils.join(labels, ",");
     }
     return defaultValue;
   }







   public static String getJsonByValues(String dictType, String dictValues) {
     List<DictEntity> labels = new ArrayList();
     if (StringUtils.isNotBlank(dictType) && StringUtils.isNotBlank(dictValues)) {
       String[] values = dictValues.split(",");
       for (int i = 0; i < values.length; i++) {
         String value = values[i];
         DictEntity dictEntity = get(dictType, null, value);
         if (ObjectUtil.isNotNull(dictEntity)) {
           labels.add(dictEntity);
         }
       }
     }
     return JSON.toJSONString(labels);
   }








   public static String getDictLabels(String dictType, String dictValues) { return getDictLabels(dictType, dictValues, ""); }






   public static String getJsonByLabels(String dictType, String dictLabels) {
     List<DictEntity> values = new ArrayList();
     if (StringUtils.isNotBlank(dictType) && StringUtils.isNotBlank(dictLabels)) {
       String[] labels = dictLabels.split(",");
       for (int i = 0; i < labels.length; i++) {
         String value = labels[i];
         DictEntity dictEntity = get(dictType, value, null);
         if (ObjectUtil.isNotNull(dictEntity)) {
           values.add(dictEntity);
         }
       }
     }
     return JSON.toJSONString(values);
   }







   public static String getDictValues(String dictType, String dictLabels, String defaultValue) {
     if (StringUtils.isNotBlank(dictType) && StringUtils.isNotBlank(dictLabels)) {
       List<String> values = new ArrayList();
       String[] labels = dictLabels.split(",");
       for (int i = 0; i < labels.length; i++) {
         String value = labels[i];
         values.add(getDictValue(dictType, value, defaultValue));
       }
       return StringUtils.join(values, ",");
     }
     return defaultValue;
   }








   public static String getDictValues(String dictType, String dictLabels) { return getDictValues(dictType, dictLabels, ""); }
 }


