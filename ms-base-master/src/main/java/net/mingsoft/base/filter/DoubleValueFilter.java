 package net.mingsoft.base.filter;

 import com.alibaba.fastjson.serializer.ValueFilter;
 import java.math.BigDecimal;





























 public class DoubleValueFilter
   implements ValueFilter
 {
   private int digits = 2;




   public DoubleValueFilter() {}



   public DoubleValueFilter(int digits) { this.digits = digits; }




   public Object process(Object object, String name, Object value) {
     if (value instanceof BigDecimal || value instanceof Double || value instanceof Float) {
       return (new BigDecimal(value.toString())).setScale(this.digits, 4);
     }
     return value;
   }
 }


