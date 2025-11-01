package Tool;

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
    /**
     * 读取文件内容并返回字符串
     * @param path 文件路径
     * @return 文件内容字符串
     * @throws IOException 当文件读取失败时抛出
     */
    public static String read_md_String(File path) throws IOException {
        StringBuilder sb = new StringBuilder();
        char[] buffer = new char[8192]; // 8KB缓冲区
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            int bytesRead;
            while ((bytesRead = br.read(buffer)) != -1) {
                sb.append(buffer, 0, bytesRead);
            }
        }
        return sb.toString();
    }


}