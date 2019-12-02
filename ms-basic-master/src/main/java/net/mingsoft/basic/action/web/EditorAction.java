 package net.mingsoft.basic.action.web;

 import com.alibaba.fastjson.JSON;
 import com.mingsoft.ueditor.MsUeditorActionEnter;
 import java.io.File;
 import java.util.Map;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;
 import net.mingsoft.basic.util.BasicUtil;
 import org.springframework.beans.factory.annotation.Value;
 import org.springframework.stereotype.Controller;
 import org.springframework.web.bind.annotation.RequestMapping;
 import org.springframework.web.bind.annotation.ResponseBody;













 @Controller("ueAction")
 @RequestMapping({"/static/plugins/ueditor/{version}/jsp"})
 public class EditorAction
 {
   @Value("${ms.upload.path}")
   private String uploadFloderPath;
   @Value("${ms.upload.mapping}")
   private String uploadMapping;

   @ResponseBody
   @RequestMapping({"editor"})
   public String editor(HttpServletRequest request, HttpServletResponse response, String jsonConfig) {
     String rootPath = BasicUtil.getRealPath("");

     File saveFloder = new File(this.uploadFloderPath);
     if (saveFloder.isAbsolute()) {
       rootPath = saveFloder.getPath();

       jsonConfig = jsonConfig.replace("{ms.upload}", "");
     } else {

       jsonConfig = jsonConfig.replace("{ms.upload}", "/" + this.uploadFloderPath);
     }

     String json = (new MsUeditorActionEnter(request, rootPath, jsonConfig, BasicUtil.getRealPath(""))).exec();
     if (saveFloder.isAbsolute()) {

       Map<String, String> data = (Map)JSON.parse(json);
       data.put("url", this.uploadMapping.replace("/**", "") + data.get("url"));
       return JSON.toJSONString(data);
     }
     return json;
   }
 }


/* Location:              D:\User\Maven\repository\net\mingsoft\ms-basic\1.0.16\ms-basic-1.0.16.jar!\net\mingsoft\basic\action\web\EditorAction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */