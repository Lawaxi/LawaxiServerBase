package net.lawaxi.serverbase.shits;

import com.google.common.io.Files;
import com.mojang.authlib.GameProfile;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class list {

    //配置项目录
    public static String configfolder = "Lawaxi";

    public static String backupfolder =configfolder+File.separator+"datasaves";
    public static String warpfolder = "world"+File.separator+"warps";
    public static String homefolder = "world"+File.separator+"homes";

    /*1.5.2以上的版本，warp和home是放在Lawaxi配置目录下的，现改为放在world中，方便备份.
    public static String warpfolder = configfolder+File.separator+"warps";
    public static String homefolder = configfolder+File.separator+"homes";
    */

    public static String[] hello = new String[1];
    public static boolean allowBackup = false;
    public static ArrayList<tparequest> tparequests = new ArrayList<>();
    public static Map<GameProfile,locationinfo> lastlocation = new HashMap<>();

    public list()
    {
        findhello();
        allowBackup();
    }

    public static void findhello()
    {
        File helloconfig = new File(configfolder+File.separator+"hello.yml");

        if(helloconfig.exists())
        {
            try{
                FileInputStream fos = new FileInputStream(helloconfig);
                BufferedReader reader = new BufferedReader(new InputStreamReader(fos, "UTF-8"));

                int lines = ((int)reader.lines().count());
                hello = new String[lines];

                for(int i=0;i<lines;i++)
                {
                    hello[i] = reader.readLine();
                }
            }
            catch(IOException e) {
            }
        }
        else
        {
            System.out.println("玩家进入时会收到欢迎消息，您可以在 /Lawaxi/hello.yml 目录下更改！");
            System.out.println("多行在文件中空行即可，不需要输入转义字符！");

            try
            {
                BufferedWriter buffer = Files.newWriter(helloconfig, StandardCharsets.UTF_8);
                buffer.write("§a你好！");
                buffer.close();

                hello[0]="§a你好！";
            }
            catch(IOException e)
            {
            }
        }
    }

    public static void allowBackup()
    {
        File config = new File(configfolder+File.separator+"allowbackup.yml");
        if(config.exists())
        {
            try {
                FileInputStream fos = new FileInputStream(config);
                BufferedReader reader = new BufferedReader(new InputStreamReader(fos, "UTF-8"));
                if(reader.readLine().equals("是"))
                {
                    allowBackup=true;
                }
            }
            catch (IOException e)
            {

            }
        }
        else
        {
            try {
                BufferedWriter buffer = Files.newWriter(config, StandardCharsets.UTF_8);
                buffer.write("否");
                buffer.close();
            }
            catch (IOException e) {
            }
        }
    }

}
