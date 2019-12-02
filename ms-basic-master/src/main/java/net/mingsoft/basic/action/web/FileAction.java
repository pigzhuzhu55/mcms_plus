 package net.mingsoft.basic.action.web;

 import io.swagger.annotations.Api;
 import io.swagger.annotations.ApiOperation;
 import java.io.IOException;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;
 import net.mingsoft.basic.action.BaseFileAction;
 import org.springframework.beans.factory.annotation.Value;
 import org.springframework.stereotype.Controller;
 import org.springframework.web.bind.annotation.PostMapping;
 import org.springframework.web.bind.annotation.RequestMapping;
 import org.springframework.web.bind.annotation.ResponseBody;














































 @Api("上传文件接口")
 @Controller
 @RequestMapping({"/file"})
 public class FileAction
   extends BaseFileAction
 {
   @Value("${ms.upload.denied}")
   private String uploadFileDenied;

   @ApiOperation("处理post请求上传文件")
   @PostMapping({"/upload"})
   @ResponseBody
   public void upload(BaseFileAction.Bean bean, HttpServletRequest req, HttpServletResponse res) throws IOException {
     BaseFileAction.Config config = new BaseFileAction.Config(bean.getUploadPath(), bean.getFile(), null);
     outString(res, upload(config));
   }
 }


/* Location:              D:\User\Maven\repository\net\mingsoft\ms-basic\1.0.16\ms-basic-1.0.16.jar!\net\mingsoft\basic\action\web\FileAction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */