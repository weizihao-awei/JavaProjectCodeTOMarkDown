package CodeToMd;



import Tool.Read_File;

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

        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入源码软件包的绝对路径（注意地址不要带双引号）：");
        String pathString = scanner.nextLine();
        System.out.println("请输入要生成的md文件存放的文件夹的绝对地址（注意地址不要带双引号）:");
        String targetPath = scanner.nextLine();

        System.out.println("请选择要导出的模式：");
        System.out.println("1.默认模式");
        System.out.println("2.导出三级java");
        System.out.println("请输入1或者2后回车：");
        int mode = scanner.nextInt();

        //读取软件包生成md文档
        File dir = new File(pathString);
        String mdName=dir.getName()+".md";
        File target = new File(targetPath+"\\"+mdName);
            //获取软件包处在绝对路径上的层级
        int InitialLevel =FileLayerExample.getFileLayer(dir);
        List<String> md = getAllFileContents(dir,InitialLevel,mode);
        Copy_file.copy_file(md,target);
    }

    /**
     *
     * @param dir 要获取源码的软件包文件夹的绝对路径地址：D:\专业学习资料\Java学习\Java_项目\TakeNoteObsidian
     * @param InitialLevel 源码软件包在绝对路径上的层级，例如：TakeNoteObsidian的层级为3，在三个文件夹内
     * @return md ：保存这个软件包所有源码文件的内容，以List<String> md 形式。
     *
     */

    public static List<String> getAllFileContents(File dir,int InitialLevel,int mode) throws IOException {
        List<String> md = null;
        //选择模式
        switch (mode) {
            case 1:
                // 标准默认模式
                md=DefaultMode(dir,InitialLevel);
                break;
            case 2:
                // 模式2的操作
                md=threeTitleCode(dir,InitialLevel);

                break;
            default:
                System.out.println("无效的模式选择");
                break;
        }

        return md;
    }

    /**
     * 默认的生成md模式，使用文件名作为标题，不仅是把源代码复制到md内，同时会复制其它文件的文件路径到md内
     *
     *  @param dir 要获取源码的软件包文件夹的绝对路径地址：D:\专业学习资料\Java学习\Java_项目\TakeNoteObsidian
     *  @param InitialLevel 源码软件包在绝对路径上的层级，例如：TakeNoteObsidian的层级为3，在三个文件夹内
     *  @return md ：保存这个软件包所有源码文件的内容，以List<String> md 形式。
     */
    public static List<String> DefaultMode(File dir,int InitialLevel) throws IOException {
        File[] files = dir.listFiles();
        List<String> md= new ArrayList<>();


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
                md.addAll(DefaultMode(f,InitialLevel)) ;
            } else {

                // md file

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

    /**
     * 给出一个文件夹，只将里面的源码文件复制到md内，并返回md，
     * 在生成的md文件，每一个源码文件放在一个三级标题内，而三级标题名字就是对应源码文件的名字。
     *
     *  @param dir 要获取源码的软件包文件夹的绝对路径地址：D:\专业学习资料\Java学习\Java_项目\TakeNoteObsidian
     *  @param InitialLevel 源码软件包在绝对路径上的层级，例如：TakeNoteObsidian的层级为3，在三个文件夹内
     *  @return md ：保存这个软件包所有源码文件的内容，以List<String> md 形式。
     */
    public static List<String> threeTitleCode(File dir,int InitialLevel)throws IOException{
        File[] files = dir.listFiles();
        List<String> md= new ArrayList<>();

        for (File f : files) {
            if (f.isDirectory()) {
                // 递归获取子目录文件路径，并合并结果
                md.addAll(threeTitleCode(f,InitialLevel)) ;
            } else {
                // 将文件名作为标题添加到md中
                if(Permitted_suffix_names.contains(getFileSuffixName(f.getName()))){
                    md.add("### "+f.getName());
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
                title.append(" ").append(fileName);
            }else{
                title.append(" ").append(fileName);
            }

            return title.toString();



    }
    //获取文件后缀名
    public static String getFileSuffixName(String fileName){

            return fileName.substring(fileName.lastIndexOf(".")+1);

    }
}
