/*     */ package com.mingsoft.parser.impl.general;
/*     */ 
/*     */ import com.mingsoft.parser.IParser;
/*     */ import com.mingsoft.util.StringUtil;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ChannelContParser
/*     */   extends IParser
/*     */ {
/*     */   private static final String CHANNELCONT = "\\{ms:contchannel.*?/}";
/*     */   private static final String TYPEID_CHANNELCONT = "\\{ms:contchannel.*?(typeid\\=(\\d*).{0,})?/}";
/*     */   private static final String TITLELEN_CHANNELCONT = "\\{ms:contchannel.*?(titlelen\\=(\\d*).{0,})?/}";
/*     */   
/*     */   public ChannelContParser(String htmlContent, String newContent) {
/*  60 */     this.htmlCotent = htmlContent;
/*  61 */     this.newCotent = channelContTitleLen(newContent, htmlContent);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  67 */   public String parse() { return replaceAll("\\{ms:contchannel.*?/}"); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int channelContNum(String html) {
/*  76 */     int channelNum = count(html, "\\{ms:contchannel.*?/}");
/*  77 */     return channelNum;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int channelContTypeId(String htmlContent) {
/*  87 */     int channelContTypeId = 0;
/*  88 */     String typeIdStr = parseFirst(htmlContent, "\\{ms:contchannel.*?(typeid\\=(\\d*).{0,})?/}", 2);
/*  89 */     if (!StringUtil.isBlank(typeIdStr)) {
/*  90 */       channelContTypeId = Integer.parseInt(typeIdStr);
/*     */     }
/*  92 */     return channelContTypeId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String channelContTitleLen(String content, String htmlContent) {
/* 103 */     int lengthCon = 0;
/* 104 */     String length = parseFirst(htmlContent, "\\{ms:contchannel.*?(titlelen\\=(\\d*).{0,})?/}", 2);
/* 105 */     if (!StringUtil.isBlank(length) && !StringUtil.isBlank(content)) {
/* 106 */       lengthCon = Integer.parseInt(length);
/*     */     }
/*     */     
/* 109 */     String contentNew = content;
/* 110 */     if (StringUtil.isBlank(content)) {
/* 111 */       contentNew = "<!--未找到该标签内容-->，请检封面查ID";
/*     */     }
/* 113 */     if (lengthCon != 0 && lengthCon <= contentNew.length()) {
/* 114 */       StringBuffer strBuff = new StringBuffer(contentNew);
/* 115 */       contentNew = strBuff.substring(0, lengthCon);
/*     */     } 
/* 117 */     return contentNew;
/*     */   }
/*     */ }


/* Location:              D:\User\Maven\repository\net\mingsoft\ms-parser\1.0.0\ms-parser-1.0.0.jar!\com\mingsoft\parser\impl\general\ChannelContParser.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.2
 */