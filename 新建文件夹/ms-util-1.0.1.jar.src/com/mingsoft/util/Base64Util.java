/*     */ package com.mingsoft.util;
/*     */ 
/*     */ import org.apache.commons.codec.binary.Base64;
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
/*     */ public class Base64Util
/*     */ {
/*     */   private static final int BASELENGTH = 255;
/*  37 */   private static final byte[] base64Alphabet = new byte[255];
/*     */   
/*     */   private static final int EIGHTBIT = 8;
/*     */   private static final boolean fDebug = false;
/*     */   private static final int FOURBYTE = 4;
/*     */   private static final int LOOKUPLENGTH = 64;
/*  43 */   private static final char[] lookUpBase64Alphabet = new char[64];
/*     */   
/*     */   private static final char PAD = '=';
/*     */   private static final int SIGN = -128;
/*     */   private static final int SIXBIT = 6;
/*     */   private static final int SIXTEENBIT = 16;
/*     */   private static final int TWENTYFOURBITGROUP = 24;
/*     */   
/*     */   static  {
/*  52 */     for (int i = 0; i < 255; i++) {
/*  53 */       base64Alphabet[i] = -1;
/*     */     }
/*  55 */     for (int i = 90; i >= 65; i--) {
/*  56 */       base64Alphabet[i] = (byte)(i - 65);
/*     */     }
/*  58 */     for (int i = 122; i >= 97; i--) {
/*  59 */       base64Alphabet[i] = (byte)(i - 97 + 26);
/*     */     }
/*     */     
/*  62 */     for (int i = 57; i >= 48; i--) {
/*  63 */       base64Alphabet[i] = (byte)(i - 48 + 52);
/*     */     }
/*     */     
/*  66 */     base64Alphabet[43] = 62;
/*  67 */     base64Alphabet[47] = 63;
/*     */     
/*  69 */     for (int i = 0; i <= 25; i++) {
/*  70 */       lookUpBase64Alphabet[i] = (char)(65 + i);
/*     */     }
/*  72 */     for (int i = 26, j = 0; i <= 51; i++, j++) {
/*  73 */       lookUpBase64Alphabet[i] = (char)(97 + j);
/*     */     }
/*  75 */     for (int i = 52, j = 0; i <= 61; i++, j++)
/*  76 */       lookUpBase64Alphabet[i] = (char)(48 + j); 
/*  77 */     lookUpBase64Alphabet[62] = '+';
/*  78 */     lookUpBase64Alphabet[63] = '/';
/*     */   }
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
/*     */   public static byte[] decode(String encoded) {
/*  94 */     if (encoded == null) {
/*  95 */       return null;
/*     */     }
/*  97 */     char[] base64Data = encoded.toCharArray();
/*     */     
/*  99 */     int len = removeWhiteSpace(base64Data);
/*     */     
/* 101 */     if (len % 4 != 0) {
/* 102 */       return null;
/*     */     }
/*     */     
/* 105 */     int numberQuadruple = len / 4;
/*     */     
/* 107 */     if (numberQuadruple == 0) {
/* 108 */       return new byte[0];
/*     */     }
/* 110 */     byte[] decodedData = null;
/* 111 */     byte b1 = 0, b2 = 0, b3 = 0, b4 = 0;
/* 112 */     char d1 = Character.MIN_VALUE, d2 = Character.MIN_VALUE, d3 = Character.MIN_VALUE, d4 = Character.MIN_VALUE;
/*     */     
/* 114 */     int i = 0;
/* 115 */     int encodedIndex = 0;
/* 116 */     int dataIndex = 0;
/* 117 */     decodedData = new byte[numberQuadruple * 3];
/*     */     
/* 119 */     for (; i < numberQuadruple - 1; i++) {
/*     */       
/* 121 */       if (!isData(d1 = base64Data[dataIndex++]) || !isData(d2 = base64Data[dataIndex++]) || !isData(d3 = base64Data[dataIndex++]) || !isData(d4 = base64Data[dataIndex++])) {
/* 122 */         return null;
/*     */       }
/* 124 */       b1 = base64Alphabet[d1];
/* 125 */       b2 = base64Alphabet[d2];
/* 126 */       b3 = base64Alphabet[d3];
/* 127 */       b4 = base64Alphabet[d4];
/*     */       
/* 129 */       decodedData[encodedIndex++] = (byte)(b1 << 2 | b2 >> 4);
/* 130 */       decodedData[encodedIndex++] = (byte)((b2 & 0xF) << 4 | b3 >> 2 & 0xF);
/* 131 */       decodedData[encodedIndex++] = (byte)(b3 << 6 | b4);
/*     */     } 
/*     */     
/* 134 */     if (!isData(d1 = base64Data[dataIndex++]) || !isData(d2 = base64Data[dataIndex++])) {
/* 135 */       return null;
/*     */     }
/*     */     
/* 138 */     b1 = base64Alphabet[d1];
/* 139 */     b2 = base64Alphabet[d2];
/*     */     
/* 141 */     d3 = base64Data[dataIndex++];
/* 142 */     d4 = base64Data[dataIndex++];
/* 143 */     if (!isData(d3) || !isData(d4)) {
/* 144 */       if (isPad(d3) && isPad(d4)) {
/* 145 */         if ((b2 & 0xF) != 0)
/* 146 */           return null; 
/* 147 */         byte[] tmp = new byte[i * 3 + 1];
/* 148 */         System.arraycopy(decodedData, 0, tmp, 0, i * 3);
/* 149 */         tmp[encodedIndex] = (byte)(b1 << 2 | b2 >> 4);
/* 150 */         return tmp;
/* 151 */       }  if (!isPad(d3) && isPad(d4)) {
/* 152 */         b3 = base64Alphabet[d3];
/* 153 */         if ((b3 & 0x3) != 0)
/* 154 */           return null; 
/* 155 */         byte[] tmp = new byte[i * 3 + 2];
/* 156 */         System.arraycopy(decodedData, 0, tmp, 0, i * 3);
/* 157 */         tmp[encodedIndex++] = (byte)(b1 << 2 | b2 >> 4);
/* 158 */         tmp[encodedIndex] = (byte)((b2 & 0xF) << 4 | b3 >> 2 & 0xF);
/* 159 */         return tmp;
/*     */       } 
/* 161 */       return null;
/*     */     } 
/*     */ 
/*     */     
/* 165 */     b3 = base64Alphabet[d3];
/* 166 */     b4 = base64Alphabet[d4];
/* 167 */     decodedData[encodedIndex++] = (byte)(b1 << 2 | b2 >> 4);
/* 168 */     decodedData[encodedIndex++] = (byte)((b2 & 0xF) << 4 | b3 >> 2 & 0xF);
/* 169 */     decodedData[encodedIndex++] = (byte)(b3 << 6 | b4);
/*     */ 
/*     */ 
/*     */     
/* 173 */     return decodedData;
/*     */   }
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
/*     */   public static String encode(byte[] binaryData) {
/* 187 */     if (binaryData == null) {
/* 188 */       return null;
/*     */     }
/* 190 */     int lengthDataBits = binaryData.length * 8;
/* 191 */     if (lengthDataBits == 0) {
/* 192 */       return "";
/*     */     }
/*     */     
/* 195 */     int fewerThan24bits = lengthDataBits % 24;
/* 196 */     int numberTriplets = lengthDataBits / 24;
/* 197 */     int numberQuartet = (fewerThan24bits != 0) ? (numberTriplets + 1) : numberTriplets;
/* 198 */     int numberLines = (numberQuartet - 1) / 19 + 1;
/* 199 */     char[] encodedData = null;
/*     */     
/* 201 */     encodedData = new char[numberQuartet * 4 + numberLines];
/*     */     
/* 203 */     byte k = 0, l = 0, b1 = 0, b2 = 0, b3 = 0;
/*     */     
/* 205 */     int encodedIndex = 0;
/* 206 */     int dataIndex = 0;
/* 207 */     int i = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 212 */     for (int line = 0; line < numberLines - 1; line++) {
/* 213 */       for (int quartet = 0; quartet < 19; quartet++) {
/* 214 */         b1 = binaryData[dataIndex++];
/* 215 */         b2 = binaryData[dataIndex++];
/* 216 */         b3 = binaryData[dataIndex++];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 222 */         l = (byte)(b2 & 0xF);
/* 223 */         k = (byte)(b1 & 0x3);
/*     */         
/* 225 */         byte val1 = ((b1 & Byte.MIN_VALUE) == 0) ? (byte)(b1 >> 2) : (byte)(b1 >> 2 ^ 0xC0);
/*     */         
/* 227 */         byte val2 = ((b2 & Byte.MIN_VALUE) == 0) ? (byte)(b2 >> 4) : (byte)(b2 >> 4 ^ 0xF0);
/* 228 */         byte val3 = ((b3 & Byte.MIN_VALUE) == 0) ? (byte)(b3 >> 6) : (byte)(b3 >> 6 ^ 0xFC);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 236 */         encodedData[encodedIndex++] = lookUpBase64Alphabet[val1];
/* 237 */         encodedData[encodedIndex++] = lookUpBase64Alphabet[val2 | k << 4];
/* 238 */         encodedData[encodedIndex++] = lookUpBase64Alphabet[l << 2 | val3];
/* 239 */         encodedData[encodedIndex++] = lookUpBase64Alphabet[b3 & 0x3F];
/*     */         
/* 241 */         i++;
/*     */       } 
/* 243 */       encodedData[encodedIndex++] = '\n';
/*     */     } 
/*     */     
/* 246 */     for (; i < numberTriplets; i++) {
/* 247 */       b1 = binaryData[dataIndex++];
/* 248 */       b2 = binaryData[dataIndex++];
/* 249 */       b3 = binaryData[dataIndex++];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 255 */       l = (byte)(b2 & 0xF);
/* 256 */       k = (byte)(b1 & 0x3);
/*     */       
/* 258 */       byte val1 = ((b1 & Byte.MIN_VALUE) == 0) ? (byte)(b1 >> 2) : (byte)(b1 >> 2 ^ 0xC0);
/*     */       
/* 260 */       byte val2 = ((b2 & Byte.MIN_VALUE) == 0) ? (byte)(b2 >> 4) : (byte)(b2 >> 4 ^ 0xF0);
/* 261 */       byte val3 = ((b3 & Byte.MIN_VALUE) == 0) ? (byte)(b3 >> 6) : (byte)(b3 >> 6 ^ 0xFC);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 269 */       encodedData[encodedIndex++] = lookUpBase64Alphabet[val1];
/* 270 */       encodedData[encodedIndex++] = lookUpBase64Alphabet[val2 | k << 4];
/* 271 */       encodedData[encodedIndex++] = lookUpBase64Alphabet[l << 2 | val3];
/* 272 */       encodedData[encodedIndex++] = lookUpBase64Alphabet[b3 & 0x3F];
/*     */     } 
/*     */ 
/*     */     
/* 276 */     if (fewerThan24bits == 8) {
/* 277 */       b1 = binaryData[dataIndex];
/* 278 */       k = (byte)(b1 & 0x3);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 283 */       byte val1 = ((b1 & Byte.MIN_VALUE) == 0) ? (byte)(b1 >> 2) : (byte)(b1 >> 2 ^ 0xC0);
/* 284 */       encodedData[encodedIndex++] = lookUpBase64Alphabet[val1];
/* 285 */       encodedData[encodedIndex++] = lookUpBase64Alphabet[k << 4];
/* 286 */       encodedData[encodedIndex++] = '=';
/* 287 */       encodedData[encodedIndex++] = '=';
/* 288 */     } else if (fewerThan24bits == 16) {
/* 289 */       b1 = binaryData[dataIndex];
/* 290 */       b2 = binaryData[dataIndex + 1];
/* 291 */       l = (byte)(b2 & 0xF);
/* 292 */       k = (byte)(b1 & 0x3);
/*     */       
/* 294 */       byte val1 = ((b1 & Byte.MIN_VALUE) == 0) ? (byte)(b1 >> 2) : (byte)(b1 >> 2 ^ 0xC0);
/* 295 */       byte val2 = ((b2 & Byte.MIN_VALUE) == 0) ? (byte)(b2 >> 4) : (byte)(b2 >> 4 ^ 0xF0);
/*     */       
/* 297 */       encodedData[encodedIndex++] = lookUpBase64Alphabet[val1];
/* 298 */       encodedData[encodedIndex++] = lookUpBase64Alphabet[val2 | k << 4];
/* 299 */       encodedData[encodedIndex++] = lookUpBase64Alphabet[l << 2];
/* 300 */       encodedData[encodedIndex++] = '=';
/*     */     } 
/*     */     
/* 303 */     encodedData[encodedIndex] = '\n';
/*     */     
/* 305 */     return new String(encodedData);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getBASE64(byte[] bytes) {
/* 315 */     byte[] s = null;
/* 316 */     if (bytes != null) {
/* 317 */       s = Base64.encodeBase64(bytes);
/*     */     }
/* 319 */     return new String(s);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static byte[] getFromBASE64(String str) {
/* 329 */     byte[] b = null;
/* 330 */     if (str != null) {
/*     */       try {
/* 332 */         b = Base64.encodeBase64(str.getBytes());
/* 333 */         return b;
/* 334 */       } catch (Exception e) {
/* 335 */         e.printStackTrace();
/*     */       } 
/*     */     }
/* 338 */     return b;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 347 */   protected static boolean isBase64(char octect) { return !(!isWhiteSpace(octect) && !isPad(octect) && !isData(octect)); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 356 */   protected static boolean isData(char octect) { return (base64Alphabet[octect] != -1); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 365 */   protected static boolean isPad(char octect) { return (octect == '='); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 374 */   protected static boolean isWhiteSpace(char octect) { return !(octect != ' ' && octect != '\r' && octect != '\n' && octect != '\t'); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void main(String[] args) {
/* 382 */     String str = "asfd";
/* 383 */     System.out.println(getBASE64(str.getBytes()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static int removeWhiteSpace(char[] data) {
/* 394 */     if (data == null) {
/* 395 */       return 0;
/*     */     }
/*     */     
/* 398 */     int newSize = 0;
/* 399 */     int len = data.length;
/* 400 */     for (int i = 0; i < len; i++) {
/* 401 */       if (!isWhiteSpace(data[i]))
/* 402 */         data[newSize++] = data[i]; 
/*     */     } 
/* 404 */     return newSize;
/*     */   }
/*     */ }


/* Location:              D:\User\Maven\repository\net\mingsoft\ms-util\1.0.1\ms-util-1.0.1.jar!\com\mingsof\\util\Base64Util.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.2
 */