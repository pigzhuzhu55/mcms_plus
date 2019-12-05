package com.baidu.ueditor.define;

public interface State {
  boolean isSuccess();
  
  void putInfo(String paramString1, String paramString2);
  
  void putInfo(String paramString, long paramLong);
  
  String toJSONString();
}


