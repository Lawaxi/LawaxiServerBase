package net.lawaxi.serverbase.utils.useless;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

// deprecated
/* Reference: https://blog.csdn.net/qq_36761831/article/details/80643163
 class MyZip {
    public static void zip(File zipFile, File inputFile) {
        try {
            ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFile));
            zip(out, inputFile, "");
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void zip(ZipOutputStream out, File f, String base) throws Exception { // 方法重载
        if (f.isDirectory()) {
            File[] fl = f.listFiles();
            out.putNextEntry(new ZipEntry(base + "/")); // 写入此目录的entry
            base = base.length() == 0 ? "" : base + "/"; // 判断参数是否为空
            for (File file : fl) { // 循环遍历数组中文件
                zip(out, file, base + file);
            }
        } else {
            out.putNextEntry(new ZipEntry(base)); // 创建新的进入点
            FileInputStream in = new FileInputStream(f);
            int b;
            System.out.println(base);
            while ((b = in.read()) != -1) { // 如果没有到达流的尾部
                out.write(b); // 将字节写入当前ZIP条目
            }
            in.close();
        }
    }
 }*/


// Reference: https://blog.csdn.net/weixin_30597269/article/details/98359656
public class Zip {
    public static void zip(File zipFile, File inputFile) {
        try {
//            System.out.println("压缩中...");
            ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFile));
            BufferedOutputStream bos = new BufferedOutputStream(out);
            compress(out, bos, inputFile, inputFile.getName());
            bos.close();
            out.close();
//            System.out.println("压缩完成");
//        } catch (IOException ignored) {
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void compress(ZipOutputStream out, BufferedOutputStream bos, File sourceFile, String base) throws Exception {
        //如果路径为目录（文件夹）
        if (sourceFile.isDirectory()) {
            //取出文件夹中的文件（或子文件夹）
            File[] fileList = sourceFile.listFiles();
            if (fileList.length == 0) { //如果文件夹为空，则只需在目的地zip文件中写入一个目录进入点
//                System.out.println(base + "/");
                out.putNextEntry(new ZipEntry(base + "/"));
            } else //如果文件夹不为空，则递归调用compress，文件夹中的每一个文件（或文件夹）进行压缩
                for (File file : fileList)
                    compress(out, bos, file, base + "/" + file.getName());
        } else { //如果不是目录（文件夹），即为文件，则先写入目录进入点，之后将文件写入zip文件中
            out.putNextEntry(new ZipEntry(base));
            FileInputStream fos = new FileInputStream(sourceFile);
            BufferedInputStream bis = new BufferedInputStream(fos);

            int tag;
//            System.out.println(base);
            //将源文件写入到zip文件中
            while ((tag = bis.read()) != -1) {
                bos.write(tag);
            }
            bis.close();
            fos.close();
        }
    }
}
