 package com.baidu.ueditor.upload;

 import com.baidu.ueditor.define.BaseState;
 import com.baidu.ueditor.define.State;
 import java.io.BufferedInputStream;
 import java.io.BufferedOutputStream;
 import java.io.File;
 import java.io.FileOutputStream;
 import java.io.IOException;
 import java.io.InputStream;
 import org.apache.commons.io.FileUtils;






 public class StorageManager
 {
   public static final int BUFFER_SIZE = 8192;

   public static State saveBinaryFile(byte[] data, String path) {
     File file = new File(path);

     State state = valid(file);

     if (!state.isSuccess()) {
       return state;
     }

     try {
       BufferedOutputStream bos = new BufferedOutputStream(
           new FileOutputStream(file));
       bos.write(data);
       bos.flush();
       bos.close();
     } catch (IOException iOException) {
       return (State)new BaseState(false, 4);
     }

     BaseState baseState = new BaseState(true, file.getAbsolutePath());
     baseState.putInfo("size", data.length);
     baseState.putInfo("title", file.getName());
     return (State)baseState;
   }


   public static State saveFileByInputStream(InputStream is, String path, long maxSize) {
     State state = null;

     File tmpFile = getTmpFile();

     byte[] dataBuf = new byte[2048];
     BufferedInputStream bis = new BufferedInputStream(is, 8192);

     try {
       BufferedOutputStream bos = new BufferedOutputStream(
           new FileOutputStream(tmpFile), 8192);

       int count = 0;
       while ((count = bis.read(dataBuf)) != -1) {
         bos.write(dataBuf, 0, count);
       }
       bos.flush();
       bos.close();

       if (tmpFile.length() > maxSize) {
         tmpFile.delete();
         return (State)new BaseState(false, 1);
       }

       state = saveTmpFile(tmpFile, path);

       if (!state.isSuccess()) {
         tmpFile.delete();
       }

       return state;
     }
     catch (IOException iOException) {

       return (State)new BaseState(false, 4);
     }
   }
   public static State saveFileByInputStream(InputStream is, String path) {
     State state = null;

     File tmpFile = getTmpFile();

     byte[] dataBuf = new byte[2048];
     BufferedInputStream bis = new BufferedInputStream(is, 8192);

     try {
       BufferedOutputStream bos = new BufferedOutputStream(
           new FileOutputStream(tmpFile), 8192);

       int count = 0;
       while ((count = bis.read(dataBuf)) != -1) {
         bos.write(dataBuf, 0, count);
       }
       bos.flush();
       bos.close();

       state = saveTmpFile(tmpFile, path);

       if (!state.isSuccess()) {
         tmpFile.delete();
       }

       return state;
     } catch (IOException iOException) {

       return (State)new BaseState(false, 4);
     }
   }
   private static File getTmpFile() {
     File tmpDir = FileUtils.getTempDirectory();
     String tmpFileName = (new StringBuilder(String.valueOf(Math.random() * 10000.0D))).toString().replace(".", "");
     return new File(tmpDir, tmpFileName);
   }

   private static State saveTmpFile(File tmpFile, String path) {
     State state = null;
     File targetFile = new File(path);

     if (targetFile.canWrite()) {
       return (State)new BaseState(false, 2);
     }
     try {
       FileUtils.moveFile(tmpFile, targetFile);
     } catch (IOException iOException) {
       return (State)new BaseState(false, 4);
     }

     BaseState baseState = new BaseState(true);
     baseState.putInfo("size", targetFile.length());
     baseState.putInfo("title", targetFile.getName());

     return (State)baseState;
   }

   private static State valid(File file) {
     File parentPath = file.getParentFile();

     if (!parentPath.exists() && !parentPath.mkdirs()) {
       return (State)new BaseState(false, 3);
     }

     if (!parentPath.canWrite()) {
       return (State)new BaseState(false, 2);
     }

     return (State)new BaseState(true);
   }
 }


