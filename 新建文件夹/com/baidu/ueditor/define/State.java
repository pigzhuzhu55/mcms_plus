package com.baidu.ueditor.define;

public interface State {
  boolean isSuccess();
  
  void putInfo(String paramString1, String paramString2);
  
  void putInfo(String paramString, long paramLong);
  
  String toJSONString();
}


/* Location:              D:\User\Maven\repository\net\mingsoft\ms-ueditor\1.0.4\ms-ueditor-1.0.4.jar!\com\baid\\ueditor\define\State.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.2
 */