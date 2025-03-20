package org.becomingMd;



import java.io.File;
import java.io.IOException;
import java.util.*;


/**
 * 给出一个文件夹的绝对路径，返回一个
 * List<File>，里面是该文件夹下所有文件的File对象
 */
public class Make_md_main {
    //允许写入md文件的后缀名
    public static Set<String> Permitted_suffix_names;
    static {
        Permitted_suffix_names=new HashSet<>() ;
        Permitted_suffix_names.add("java");
        Permitted_suffix_names.add("cpp");
        Permitted_suffix_names.add("c");
        Permitted_suffix_names.add("txt");
        Permitted_suffix_names.add("py");


    }
    public static void main(String[] args) throws IOException {
        //D:\专业学习资料\不同语言的资料\c语言编程
        //D:\专业学习资料\Java学习\Java_项目\TakeNoteObsidian
        //C:\Users\86158\Desktop\ob_整理文件测试\text

        //
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入源码软件包的绝对路径：");
        String pathString = scanner.nextLine();
        System.out.println("请输入要生成的md文件存放的文件夹的绝对地址:");
        String targetPath = scanner.nextLine();

        //读取软件包生成md文档
        File dir = new File(pathString);
        String mdName=dir.getName()+".md";
        File target = new File(targetPath+"\\"+mdName);
            //获取软件包处在绝对路径上的层级
        int InitialLevel =FileLayerExample.getFileLayer(dir);
        List<String> md = getAllFileContents(dir,InitialLevel);
        Copy_file.copy_file(md,target);
    }


    public static List<String> getAllFileContents(File dir,int InitialLevel) throws IOException {
        File[] files = dir.listFiles();
        List<String> md = new ArrayList<>();
        //list保存没有匹配到后缀名的文件的绝对路径
        List<String> listFilepath = new ArrayList<>();
        //确定软件包的层级
        int fileLayer = 0;
        int titleLevel = 0;
        for (File f : files) {
            if (f.isDirectory()) {
                //确定软件包的层级
                fileLayer = FileLayerExample.getFileLayer(f);
                titleLevel = fileLayer - InitialLevel;
                //将软件名作为标题添加到md中
                md.add(getTitle(titleLevel,f.getName()));
                md.add("\n");
                // 递归获取子目录文件路径，并合并结果
                md.addAll(getAllFileContents(f,InitialLevel)) ;
            } else {



                // 确定标题的层级
                 fileLayer = FileLayerExample.getFileLayer(f);
                 titleLevel = fileLayer - InitialLevel;
                // 将文件名作为标题添加到md中

                if(!Permitted_suffix_names.contains(getFileSuffixName(f.getName()))){
                    String filepath ="file:///"+ f.getAbsolutePath().replaceAll(" ","%20");
                    listFilepath.add(filepath);
                }else{
                    md.add(getTitle(titleLevel,f.getName()));
                    md.add("\n");
                    //获取文件后缀名
                    md.add("```"+getFileSuffixName(f.getName()));
                    List<String> fileContent = Read_File.read_md(f);
                    md.addAll(fileContent);
                    md.add("```");
                    md.add("\n");

                }






            }
        }
        if(!listFilepath.isEmpty()){
            // 确定标题的层级
            if (titleLevel!=1) titleLevel=titleLevel+1;
            md.add(getTitle(titleLevel,"该文件夹包含的其它文件："));
            md.add("\n");
            md.addAll(listFilepath);
        }

        return md;
    }
    public static String getTitle(int layer,String fileName){

            StringBuilder title = new StringBuilder("#");
            for (int i = 0; i < layer; i++){
                title.append("#");
            }
            //删掉文件名后缀
            if(fileName.contains(".")){
                fileName = fileName.substring(0, fileName.lastIndexOf("."));
                title.append(" ").append(fileName).append("类");
            }else{
                title.append(" ").append(fileName).append("软件包");
            }

            return title.toString();



    }
    //获取文件后缀名
    public static String getFileSuffixName(String fileName){

            return fileName.substring(fileName.lastIndexOf(".")+1);

    }
}
