 package net.mingsoft.basic.util;

 import com.alibaba.fastjson.JSONObject;
 import java.io.File;
 import java.io.IOException;
 import java.util.List;
 import java.util.regex.Matcher;
 import java.util.regex.Pattern;
 import net.mingsoft.base.entity.BaseEntity;
 import org.apache.commons.io.FileUtils;




































 public class FileUtil
 {
   private static String fileSuffix = "[/|\\\\]upload.*?\\.(rmvb|mpga|mpg4|mpeg|docx|xlsx|pptx|jpeg|[a-z]{3})";







   public static void del(String json) {
     Pattern pattern = Pattern.compile(fileSuffix);
     Matcher matcher = pattern.matcher(json);
     while (matcher.find()) {
       try {
         FileUtils.forceDelete(new File(BasicUtil.getRealPath(matcher.group())));
       } catch (IOException e) {
         e.printStackTrace();
       }
     }
   }







   public static void del(List<?> list) {
     String json = "";
     for (Object entity : list) {
       json = JSONObject.toJSONString(entity);
       del(json);
     }
   }







   public static void del(BaseEntity entity) {
     String json = JSONObject.toJSONString(entity);
     del(json);
   }
 }


