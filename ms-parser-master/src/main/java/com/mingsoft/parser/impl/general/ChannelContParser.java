 package com.mingsoft.parser.impl.general;

 import com.mingsoft.parser.IParser;
 import com.mingsoft.util.StringUtil;















































 public class ChannelContParser
   extends IParser
 {
   private static final String CHANNELCONT = "\\{ms:contchannel.*?/}";
   private static final String TYPEID_CHANNELCONT = "\\{ms:contchannel.*?(typeid\\=(\\d*).{0,})?/}";
   private static final String TITLELEN_CHANNELCONT = "\\{ms:contchannel.*?(titlelen\\=(\\d*).{0,})?/}";

   public ChannelContParser(String htmlContent, String newContent) {
     this.htmlCotent = htmlContent;
     this.newCotent = channelContTitleLen(newContent, htmlContent);
   }




   public String parse() { return replaceAll("\\{ms:contchannel.*?/}"); }







   public static int channelContNum(String html) {
     int channelNum = count(html, "\\{ms:contchannel.*?/}");
     return channelNum;
   }







   public static int channelContTypeId(String htmlContent) {
     int channelContTypeId = 0;
     String typeIdStr = parseFirst(htmlContent, "\\{ms:contchannel.*?(typeid\\=(\\d*).{0,})?/}", 2);
     if (!StringUtil.isBlank(typeIdStr)) {
       channelContTypeId = Integer.parseInt(typeIdStr);
     }
     return channelContTypeId;
   }








   public static String channelContTitleLen(String content, String htmlContent) {
     int lengthCon = 0;
     String length = parseFirst(htmlContent, "\\{ms:contchannel.*?(titlelen\\=(\\d*).{0,})?/}", 2);
     if (!StringUtil.isBlank(length) && !StringUtil.isBlank(content)) {
       lengthCon = Integer.parseInt(length);
     }

     String contentNew = content;
     if (StringUtil.isBlank(content)) {
       contentNew = "<!--未找到该标签内容-->，请检封面查ID";
     }
     if (lengthCon != 0 && lengthCon <= contentNew.length()) {
       StringBuffer strBuff = new StringBuffer(contentNew);
       contentNew = strBuff.substring(0, lengthCon);
     }
     return contentNew;
   }
 }


