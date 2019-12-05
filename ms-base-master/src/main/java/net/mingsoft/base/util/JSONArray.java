 package net.mingsoft.base.util;

 import com.alibaba.fastjson.serializer.SerializeFilter;
 import com.alibaba.fastjson.serializer.SerializerFeature;





































 public class JSONArray
 {
   public static final String toJSONString(Object object, SerializeFilter... filters) {
       return com.alibaba.fastjson.JSONArray.toJSONString(object, filters, new SerializerFeature[] { SerializerFeature.WriteMapNullValue });
   }
 }


