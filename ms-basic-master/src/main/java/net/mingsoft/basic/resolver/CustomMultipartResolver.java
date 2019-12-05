 package net.mingsoft.basic.resolver;

 import cn.hutool.core.util.ObjectUtil;
 import java.io.IOException;
 import net.mingsoft.base.resolver.MultipartResolver;
 import net.mingsoft.basic.config.MultipartProperties;
 import org.springframework.boot.context.properties.EnableConfigurationProperties;
 import org.springframework.stereotype.Component;










 @Component
 @EnableConfigurationProperties({MultipartProperties.class})
 public class CustomMultipartResolver
   extends MultipartResolver
 {
   public CustomMultipartResolver(MultipartProperties multipartProperties) throws IOException {
     if (ObjectUtil.isNotNull(multipartProperties.getUploadTempDir())) {
       setUploadTempDir(multipartProperties.getUploadTempDir());
     }
     setDefaultEncoding(multipartProperties.getDefaultEncoding());
     setMaxUploadSize(multipartProperties.getMaxFileSize());
     setMaxUploadSizePerFile(multipartProperties.getMaxRequestSize());
     setMaxInMemorySize(multipartProperties.getMaxInMemorySize());
     setResolveLazily(multipartProperties.isResolveLazily());
     setExcludeUrls("jsp/editor.do");
   }
 }


