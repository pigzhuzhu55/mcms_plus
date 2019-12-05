 package net.mingsoft.base.filter;

 import com.alibaba.fastjson.serializer.ValueFilter;
 import java.text.SimpleDateFormat;
































 public class DateValueFilter
   implements ValueFilter
 {
   private static String fmt = "yyyy-MM-dd HH:mm:ss";




   public DateValueFilter() {}



   public DateValueFilter(String fmt){DateValueFilter.fmt = fmt; }




   public Object process(Object object, String name, Object value) {
     if (value instanceof java.util.Date || value instanceof java.sql.Timestamp) {
       SimpleDateFormat sdf = new SimpleDateFormat(fmt);
       return sdf.format(value);
     }
     return value;
   }
 }


