 package com.mingsoft.parser.impl.general;

 import com.mingsoft.parser.IParser;
 import com.mingsoft.util.FileUtil;
 import com.mingsoft.util.StringUtil;
 import java.io.File;
 import java.util.regex.Matcher;
 import java.util.regex.Pattern;









































 public class IncludeParser
   extends IParser
 {
   String path;
   private static final String INCLUDE = "\\{ms:include filename\\=(.*?)\\s*/}";

   public IncludeParser(String htmlContent, String path, String mobilePath) {
     this.mobilePath = mobilePath;
     this.htmlCotent = htmlContent;
     this.path = path;
     if (!StringUtil.isBlank(mobilePath)) {
       this.path = String.valueOf(path) + File.separator + this.mobilePath;
     }
   }


   public IncludeParser(String htmlContent, String path) {
     this.htmlCotent = htmlContent;
     this.path = path;
   }


   public String parse() {
     String html = this.htmlCotent;
     int strNum = includeNum(this.htmlCotent);
     while (strNum != 0) {

       String htmlInclude = includeContentPrase(this.htmlCotent, this.path);
       this.newCotent = htmlInclude;

       this.htmlCotent = replaceFirst("\\{ms:include filename\\=(.*?)\\s*/}");
       html = this.htmlCotent;
       strNum = includeNum(this.htmlCotent);
     }

     return html;
   }










   private static String includeContentPrase(String html, String path) {
     String includeContent = "";
     Pattern patternL = Pattern.compile("\\{ms:include filename\\=(.*?)\\s*/}");
     Matcher matcherL = patternL.matcher(html);
     if (matcherL.find()) {
       String includeName = matcherL.group(1);

       File file = new File(String.valueOf(path) + File.separator + includeName);
       if (!file.exists()) {
         includeContent = String.valueOf(includeName) + "不存在，请仔细检查该模版的文件！";
       } else {
         includeContent = FileUtil.readFile(String.valueOf(path) + File.separator + includeName);
       }
     }
     return includeContent;
   }








   private int includeNum(String html) {
     int includeNum = count(html, "\\{ms:include filename\\=(.*?)\\s*/}");
     return includeNum;
   }
 }


