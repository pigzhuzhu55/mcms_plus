 package com.mingsoft.parser;

 import com.mingsoft.util.RegexUtil;
 import com.mingsoft.util.StringUtil;
 import java.util.HashMap;
 import java.util.List;
 import java.util.Map;














































































 public abstract class IParser
 {
   public static final String DO_SUFFIX = ".do";
   public static final String HTM_SUFFIX = ".htm";
   public static final String HTML_SUFFIX = ".html";
   protected static final String PROPERTY_VALUE = "=\\s*(\\w*)";
   protected static final String PRORETY_NAME = "(\\w*)\\s*=";

   protected static int count(String source, String regex) { return RegexUtil.count(source, regex); }















   protected static List<String> parseAll(String source, String regex, int find) { return RegexUtil.parseAll(source, regex, find); }












   protected static String parseFirst(String source, String regex, int find) {
     String temp = RegexUtil.parseFirst(source, regex, find);
     return (temp == null) ? "" : temp;
   }




   protected String htmlCotent = null;

   protected String mobilePath = "";




   protected String newCotent = null;


   public String getNewCotent() { return this.newCotent; }









   public Map<String, String> getProperty(String regex) {
     Map<String, String> listPropertyMap = new HashMap<>();
     String listProperty = parseFirst(this.htmlCotent, regex, 1);
     if (listProperty == null) {
       return listPropertyMap;
     }
     List<String> listPropertyName = parseAll(listProperty, "(\\w*)\\s*=", 1);
     List<String> listPropertyValue = parseAll(listProperty, "=\\s*(\\w*)", 1);
     for (int i = 0; i < listPropertyName.size(); i++) {
       listPropertyMap.put(((String)listPropertyName.get(i)).toString(), ((String)listPropertyValue.get(i)).toString());
     }
     return listPropertyMap;
   }







   public abstract String parse();







   public String replaceAll(String regex) {
     if (StringUtil.isBlank(this.newCotent)) {
       this.newCotent = "<!--未找到该标签内容-->";
     }
     return RegexUtil.replaceAll(this.htmlCotent, regex, this.newCotent);
   }











   public String replaceAll(String content, String regex) { return RegexUtil.replaceAll(this.htmlCotent, regex, content); }













   public String replaceFirst(String regex) {
     if (StringUtil.isBlank(this.newCotent)) {
       this.newCotent = "<!--未找到该标签内容-->";
     }
     return RegexUtil.replaceFirst(this.htmlCotent, regex, this.newCotent);
   }


   public void setNewCotent(String newCotent) { this.newCotent = newCotent; }
 }


