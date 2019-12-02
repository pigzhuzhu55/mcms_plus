 package net.mingsoft.basic.config;

 import org.springframework.boot.context.properties.ConfigurationProperties;
 import org.springframework.core.io.Resource;




























 @ConfigurationProperties(prefix = "ms.upload.multipart", ignoreUnknownFields = false)
 public class MultipartProperties
 {
   private long maxFileSize = 1024L;



   private long maxRequestSize = 10240L;



   private boolean resolveLazily = false;



   private String defaultEncoding = "ISO-8859-1";



   private Resource uploadTempDir = null;



   private int maxInMemorySize = 4096;




   public long getMaxFileSize() { return this.maxFileSize * 1000L; }



   public void setMaxFileSize(long maxFileSize) { this.maxFileSize = maxFileSize; }


   public long getMaxRequestSize() {
     if (this.maxRequestSize > 0L) {
       return this.maxRequestSize * 1000L;
     }
     return this.maxRequestSize;
   }



   public void setMaxRequestSize(long maxRequestSize) { this.maxRequestSize = maxRequestSize; }




   public boolean isResolveLazily() { return this.resolveLazily; }



   public void setResolveLazily(boolean resolveLazily) { this.resolveLazily = resolveLazily; }



   public String getDefaultEncoding() { return this.defaultEncoding; }



   public void setDefaultEncoding(String defaultEncoding) { this.defaultEncoding = defaultEncoding; }



   public Resource getUploadTempDir() { return this.uploadTempDir; }



   public void setUploadTempDir(Resource uploadTempDir) { this.uploadTempDir = uploadTempDir; }



   public int getMaxInMemorySize() { return this.maxInMemorySize; }



   public void setMaxInMemorySize(int maxInMemorySize) { this.maxInMemorySize = maxInMemorySize; }
 }


/* Location:              D:\User\Maven\repository\net\mingsoft\ms-basic\1.0.16\ms-basic-1.0.16.jar!\net\mingsoft\basic\config\MultipartProperties.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.2
 */