 package com.baidu.ueditor.hunter;

 import com.baidu.ueditor.PathFormat;
 import com.baidu.ueditor.define.BaseState;
 import com.baidu.ueditor.define.MultiState;
 import com.baidu.ueditor.define.State;
 import java.io.File;
 import java.util.Arrays;
 import java.util.Collection;
 import java.util.Map;
 import org.apache.commons.io.FileUtils;




 public class FileManager
 {
   private String dir = null;
   private String rootPath = null;
   private String[] allowFiles = null;
   private int count = 0;


   public FileManager(Map<String, Object> conf) {
     this.rootPath = (String)conf.get("rootPath");
     this.dir = String.valueOf(this.rootPath) + (String)conf.get("dir");
     this.allowFiles = getAllowFiles(conf.get("allowFiles"));
     this.count = ((Integer)conf.get("count")).intValue();
   }



   public State listFile(int index) {
     File dir = new File(this.dir);
     State state = null;

     if (!dir.exists()) {
       return (State)new BaseState(false, 302);
     }

     if (!dir.isDirectory()) {
       return (State)new BaseState(false, 301);
     }

     Collection<File> list = FileUtils.listFiles(dir, this.allowFiles, true);

     if (index < 0 || index > list.size()) {
       MultiState multiState = new MultiState(true);
     } else {
       Object[] arrayOfObject = Arrays.copyOfRange(list.toArray(), index, index + this.count);
       state = getState(arrayOfObject);
     }

     state.putInfo("start", index);
     state.putInfo("total", list.size());

     return state;
   }



   private State getState(Object[] files) {
     MultiState state = new MultiState(true);
     BaseState fileState = null;

     File file = null; byte b; int i;
     Object[] arrayOfObject;
     for (i = (arrayOfObject = files).length, b = 0; b < i; ) { Object obj = arrayOfObject[b];
       if (obj == null) {
         break;
       }
       file = (File)obj;
       fileState = new BaseState(true);
       fileState.putInfo("url", PathFormat.format(getPath(file)));
       state.addState((State)fileState);
       b++; }

     return (State)state;
   }



   private String getPath(File file) {
     String path = file.getAbsolutePath();

     return path.replace(this.rootPath, "/");
   }



   private String[] getAllowFiles(Object fileExt) {
     String[] exts = null;
     String ext = null;

     if (fileExt == null) {
       return new String[0];
     }

     exts = (String[])fileExt;

     for (int i = 0, len = exts.length; i < len; i++) {

       ext = exts[i];
       exts[i] = ext.replace(".", "");
     }


     return exts;
   }
 }


