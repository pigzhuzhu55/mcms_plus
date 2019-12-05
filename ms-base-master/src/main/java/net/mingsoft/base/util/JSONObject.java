 package net.mingsoft.base.util;

 import com.alibaba.fastjson.serializer.SerializeFilter;
 import com.alibaba.fastjson.serializer.SerializerFeature;




































 public class JSONObject
   extends com.alibaba.fastjson.JSONObject
 {
   public static final String toJSONString(Object object, SerializeFilter... filters) { return JSONObject.toJSONString(object, filters, new SerializerFeature[] { SerializerFeature.WriteMapNullValue }); }
 }


