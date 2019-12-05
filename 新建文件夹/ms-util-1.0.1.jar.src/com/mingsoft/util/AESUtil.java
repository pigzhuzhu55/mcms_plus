/*     */ package com.mingsoft.util;
/*     */ 
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.security.InvalidKeyException;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import javax.crypto.BadPaddingException;
/*     */ import javax.crypto.Cipher;
/*     */ import javax.crypto.IllegalBlockSizeException;
/*     */ import javax.crypto.NoSuchPaddingException;
/*     */ import javax.crypto.spec.SecretKeySpec;
/*     */ import org.apache.log4j.Logger;
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
/*     */ public class AESUtil
/*     */ {
/*  50 */   protected static final Logger LOG = Logger.getLogger(AESUtil.class.getClass());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String decrypt(String decryptStr, String strKey) {
/*     */     try {
/*  61 */       if (strKey == null) {
/*  62 */         LOG.debug("Key为空null");
/*  63 */         return null;
/*     */       } 
/*     */       
/*  66 */       if (strKey.length() != 16) {
/*  67 */         LOG.debug("Key长度不是16位");
/*  68 */         return null;
/*     */       } 
/*  70 */       byte[] raw = strKey.getBytes("ASCII");
/*  71 */       SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
/*  72 */       Cipher cipher = Cipher.getInstance("AES");
/*  73 */       cipher.init(2, skeySpec);
/*  74 */       byte[] encrypted1 = hex2byte(decryptStr);
/*     */       try {
/*  76 */         byte[] original = cipher.doFinal(encrypted1);
/*  77 */         String originalString = new String(original);
/*  78 */         return originalString;
/*  79 */       } catch (Exception e) {
/*  80 */         System.out.println(e.toString());
/*  81 */         return null;
/*     */       } 
/*  83 */     } catch (Exception ex) {
/*  84 */       System.out.println(ex.toString());
/*  85 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String encrypt(String encryptStr, String strKey) {
/*  96 */     if (strKey == null) {
/*  97 */       LOG.debug("Key为空null");
/*  98 */       return null;
/*     */     } 
/*     */     
/* 101 */     if (strKey.length() != 16) {
/* 102 */       LOG.debug("Key长度不是16位");
/* 103 */       return null;
/*     */     } 
/* 105 */     byte[] encrypted = null;
/*     */     try {
/* 107 */       byte[] raw = strKey.getBytes("ASCII");
/* 108 */       SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
/* 109 */       Cipher cipher = Cipher.getInstance("AES");
/* 110 */       cipher.init(1, skeySpec);
/* 111 */       encrypted = cipher.doFinal(encryptStr.getBytes());
/* 112 */     } catch (UnsupportedEncodingException e) {
/*     */       
/* 114 */       e.printStackTrace();
/* 115 */     } catch (NoSuchPaddingException e) {
/* 116 */       e.printStackTrace();
/* 117 */     } catch (NoSuchAlgorithmException e) {
/* 118 */       e.printStackTrace();
/* 119 */     } catch (InvalidKeyException e) {
/* 120 */       e.printStackTrace();
/* 121 */     } catch (IllegalBlockSizeException e) {
/*     */       
/* 123 */       e.printStackTrace();
/* 124 */     } catch (BadPaddingException e) {
/*     */       
/* 126 */       e.printStackTrace();
/*     */     } 
/*     */     
/* 129 */     return byte2hex(encrypted).toLowerCase();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static byte[] hex2byte(String str) {
/* 138 */     if (str == null) {
/* 139 */       return null;
/*     */     }
/* 141 */     int l = str.length();
/* 142 */     if (l % 2 == 1) {
/* 143 */       return null;
/*     */     }
/* 145 */     byte[] bytes = new byte[l / 2];
/* 146 */     for (int i = 0; i != l / 2; i++) {
/* 147 */       bytes[i] = (byte)Integer.parseInt(str.substring(i * 2, i * 2 + 2), 16);
/*     */     }
/* 149 */     return bytes;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String byte2hex(byte[] bytes) {
/* 158 */     if (bytes == null) {
/* 159 */       return "";
/*     */     }
/* 161 */     String hs = "";
/* 162 */     String stmp = "";
/* 163 */     for (int n = 0; n < bytes.length; n++) {
/* 164 */       stmp = Integer.toHexString(bytes[n] & 0xFF);
/* 165 */       if (stmp.length() == 1) {
/* 166 */         hs = String.valueOf(hs) + "0" + stmp;
/*     */       } else {
/* 168 */         hs = String.valueOf(hs) + stmp;
/*     */       } 
/*     */     } 
/* 171 */     return hs.toUpperCase();
/*     */   }
/*     */ }


/* Location:              D:\User\Maven\repository\net\mingsoft\ms-util\1.0.1\ms-util-1.0.1.jar!\com\mingsof\\util\AESUtil.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.2
 */