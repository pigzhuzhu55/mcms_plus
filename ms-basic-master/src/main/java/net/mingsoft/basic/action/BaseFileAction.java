 package net.mingsoft.basic.action;

 import cn.hutool.core.io.FileUtil;
 import java.io.File;
 import java.io.IOException;
 import net.mingsoft.base.action.BaseAction;
 import net.mingsoft.basic.util.BasicUtil;
 import org.apache.commons.lang3.StringUtils;
 import org.springframework.beans.factory.annotation.Value;
 import org.springframework.web.multipart.MultipartFile;




















 public abstract class BaseFileAction
   extends BaseAction
 {
   @Value("${ms.upload.path}")
   private String uploadFloderPath;
   @Value("${ms.upload.mapping}")
   private String uploadMapping;
   @Value("${ms.upload.denied}")
   private String uploadFileDenied;

   public String upload(Config config) throws IOException {
     String[] errorType = this.uploadFileDenied.split(",");

     String fileName = config.getFile().getOriginalFilename();
     String fileType = fileName.substring(fileName.indexOf("."));
     boolean isReal = (new File(this.uploadFloderPath)).isAbsolute();

     this.uploadMapping = isReal ? this.uploadMapping : (config.uploadFloderPath ? "" : this.uploadFloderPath);

     String realPath = isReal ? this.uploadFloderPath : (config.uploadFloderPath ? BasicUtil.getRealPath("") : BasicUtil.getRealPath(this.uploadFloderPath));

     if (StringUtils.isNotBlank(config.getRootPath())) {
       realPath = config.getRootPath();
     }

     if (StringUtils.isNotBlank(config.getFileName())) {
       fileName = config.getFileName();
       fileType = fileName.substring(fileName.indexOf("."));
     } else {

       fileName = System.currentTimeMillis() + fileType;
     }
     for (String type : errorType) {
       if (fileType.equals(type)) {
         this.LOG.info("文件类型被拒绝:{}", fileType);
         return "";
       }
     }

     String uploadFolder = realPath + File.separator;

     if (StringUtils.isNotBlank(config.getUploadPath())) {
       uploadFolder = uploadFolder + config.getUploadPath() + File.separator;
     }

     File saveFolder = new File(uploadFolder);
     File saveFile = new File(uploadFolder, fileName);
     if (!saveFolder.exists()) {
       FileUtil.mkdir(saveFolder);
     }
     config.getFile().transferTo(saveFile);



     String path = this.uploadMapping.replace("**", "") + uploadFolder.replace(realPath, "") + "/" + fileName;



     return (new File("/" + path)).getPath().replace("\\", "/").replace("//", "/");
   }



   public class Bean
   {
     private String uploadPath;


     private MultipartFile file;

     private String fileName;


     public String getFileName() { return this.fileName; }



     public void setFileName(String fileName) { this.fileName = fileName; }



     public String getUploadPath() { return this.uploadPath; }



     public void setUploadPath(String uploadPath) { this.uploadPath = uploadPath; }



     public MultipartFile getFile() { return this.file; }



     public void setFile(MultipartFile file) { this.file = file; }
   }




   public class Config
     extends Bean
   {
     private String rootPath;


     private boolean uploadFloderPath;



     public Config() {}



     public Config(String fileName, String rootPath) { this.rootPath = rootPath; }


     public Config(String uploadPath, MultipartFile file, String rootPath, boolean uploadFloderPath) {
       this.rootPath = rootPath;
       this.uploadFloderPath = uploadFloderPath;
       setUploadPath(uploadPath);
       setFile(file);
     }
     public Config(String uploadPath, MultipartFile file, String rootPath) {
       this.rootPath = rootPath;
       setUploadPath(uploadPath);
       setFile(file);
     }


     public String getRootPath() { return this.rootPath; }



     public void setRootPath(String rootPath) { this.rootPath = rootPath; }



     public boolean isUploadFloderPath() { return this.uploadFloderPath; }



     public void setUploadFloderPath(boolean uploadFloderPath) { this.uploadFloderPath = uploadFloderPath; }
   }
 }


/* Location:              D:\User\Maven\repository\net\mingsoft\ms-basic\1.0.16\ms-basic-1.0.16.jar!\net\mingsoft\basic\action\BaseFileAction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */