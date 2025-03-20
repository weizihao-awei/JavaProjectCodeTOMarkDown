package org.becomingMd;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * author: 伟字号
 * date: 2025/1/12
 * description: 读取md文件，并返回一个ArrayList<String>  
 * version: 1.0
 *
 */
public class Read_File {
   private BufferedReader br;
   private ArrayList<String> list;


   public Read_File(File textDirPath) throws Exception{
      this.br = new BufferedReader(new FileReader(textDirPath));
      this.list = new ArrayList<String>();
   }

public ArrayList<String> read_md() throws Exception {


   String line = null;
   while ((line = br.readLine()) != null) {
      list.add(line);
   }
   br.close();

//   // 创建一个新列表进行深拷贝
//   ArrayList<String> deepCopyList = new ArrayList<>();
//   for (String item : list) {
//      deepCopyList.add(new String(item)); // 这里使用new String()确保是新的对象
//   }


   return list;
}
public static ArrayList<String> read_md(File path) throws IOException {
   BufferedReader br=new BufferedReader(new FileReader(path));
   ArrayList<String> list = new ArrayList<String>();
   String line = null;
   while ((line = br.readLine()) != null) {
      list.add(line);
   }
   br.close();
   return list;

}

}