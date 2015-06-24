/*     */ package com.wonders.send.common.util;
/*     */ 
/*     */ import com.google.gson.Gson;
import com.wonders.util.StringUtil;

/*     */ import java.io.BufferedReader;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.lang.reflect.Type;
/*     */ import java.security.MessageDigest;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
import java.util.Map;
/*     */ 
/*     */ public class CommonUtil
/*     */ {
/*  27 */   private static Gson gson = new Gson();
/*     */ 
/*     */   public static List<String> translateListByMap(List<String> target, Map<String, String> dict)
/*     */   {
/*  36 */     List list = new ArrayList();
/*     */ 
/*  38 */     for (int i = 0; i < target.size(); i++) {
/*  39 */       list.add(StringUtil.getNotNullValueString(dict.get(target.get(i))));
/*     */     }
/*     */ 
/*  42 */     return list;
/*     */   }
/*     */ 
/*     */   public static String listToStringBySplit(List<String> list)
/*     */   {
/*  51 */     return listToStringBySplit(list, ",");
/*     */   }
/*     */ 
/*     */   public static String listToStringBySplit(List<String> list, String split_str)
/*     */   {
/*  61 */     String str = "";
/*  62 */     for (int i = 0; i < list.size(); i++) {
/*  63 */       str = str + split_str + (String)list.get(i);
/*     */     }
/*     */ 
/*  66 */     if ((str.length() > 0) && (str.startsWith(split_str))) {
/*  67 */       str = str.substring(1);
/*     */     }
/*  69 */     return str;
/*     */   }
/*     */ 
/*     */   public static List<String> stringsToList(String str, String splitExp)
/*     */   {
/*  78 */     List list = new ArrayList();
/*     */     try {
/*  80 */       Collections.addAll(list, str.split(splitExp));
/*     */     } catch (Exception e) {
/*  82 */       e.printStackTrace();
/*     */     }
/*  84 */     return list;
/*     */   }
/*     */ 
/*     */   public static List<String> stringsToList(String str)
/*     */   {
/*  93 */     return stringsToList(str, ",");
/*     */   }
/*     */ 
/*     */   public static List<String> arrayToList(String[] array)
/*     */   {
/* 101 */     List list = new ArrayList();
/* 102 */     Collections.addAll(list, array);
/* 103 */     return list;
/*     */   }
/*     */ 
/*     */   public static boolean targetIsInArray(String target, String[] array)
/*     */   {
/* 112 */     boolean flag = false;
/* 113 */     target = StringUtil.getNotNullValueString(target);
/*     */     try {
/* 115 */       int i = 0;
/*     */       do { String str = StringUtil.getNotNullValueString(array[i]);
/* 117 */         if (target.equals(str)) return true;
/* 115 */         i++; if ((target.length() <= 0) || (array == null)) break;  }
/* 115 */       while (i < array.length);
/*     */     }
/*     */     catch (Exception localException)
/*     */     {
/*     */     }
/*     */ 
/* 121 */     return flag;
/*     */   }
/*     */ 
/*     */   public static String getStrByTimes(String str, int times)
/*     */   {
/* 130 */     StringBuffer sb = new StringBuffer();
/* 131 */     for (int i = 0; i < times; i++) {
/* 132 */       sb.append(str);
/*     */     }
/* 134 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static String getJsonByPath(String path)
/*     */   {
/* 142 */     StringBuilder sb = new StringBuilder();
/* 143 */     InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
/* 144 */     InputStreamReader isr = null;
/*     */     try
/*     */     {
/* 147 */       isr = new InputStreamReader(is, "utf-8");
/*     */     } catch (UnsupportedEncodingException e1) {
/* 149 */       e1.printStackTrace();
/*     */     }
/* 151 */     BufferedReader br = new BufferedReader(isr);
/*     */     try {
/* 153 */       String data = null;
/* 154 */       while ((data = br.readLine()) != null)
/* 155 */         sb.append(data);
/*     */     }
/*     */     catch (Exception e) {
/* 158 */       e.printStackTrace();
/*     */ 
/* 160 */       return null;
/*     */     } finally {
/*     */       try {
/* 163 */         br.close();
/* 164 */         isr.close();
/* 165 */         is.close();
/*     */       } catch (IOException e) {
/* 167 */         e.printStackTrace();
/*     */       }
/*     */     }
/*     */ 
/* 171 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static <T> T getInstanceByJson(String json, Class<T> clazz)
/*     */   {
/*     */     try
/*     */     {
/* 183 */       Type jsonType = clazz;
/* 184 */       return gson.fromJson(json, jsonType);
/*     */     } catch (Exception e) {
/* 186 */       e.printStackTrace();
/* 187 */     }return null;
/*     */   }
/*     */ 
/*     */   public static String getMD5(String value)
/*     */   {
/* 192 */     String result = null;
/*     */     try {
/* 194 */       byte[] valueByte = value.getBytes();
/* 195 */       MessageDigest md = MessageDigest.getInstance("MD5");
/* 196 */       md.update(valueByte);
/* 197 */       result = toHex(md.digest());
/*     */     } catch (NoSuchAlgorithmException e1) {
/* 199 */       e1.printStackTrace();
/*     */     }
/* 201 */     return result;
/*     */   }
/*     */ 
/*     */   private static String toHex(byte[] buffer) {
/* 205 */     StringBuffer sb = new StringBuffer(buffer.length * 2);
/* 206 */     for (int i = 0; i < buffer.length; i++) {
/* 207 */       sb.append(Character.forDigit((buffer[i] & 0xF0) >> 4, 16));
/* 208 */       sb.append(Character.forDigit(buffer[i] & 0xF, 16));
/*     */     }
/* 210 */     return sb.toString();
/*     */   }
/*     */ }

/* Location:           \\10.1.48.69\WeblogicApp\app\workflowNew\WEB-INF\classes\
 * Qualified Name:     com.wonders.workflow.common.util.CommonUtil
 * JD-Core Version:    0.6.0
 */