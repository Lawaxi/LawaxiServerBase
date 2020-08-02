package net.lawaxi.serverbase.utils.config;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import java.io.*;

public class configs {

    public static final File configfolder = new File("Lawaxi");
    public static final File backupfolder = new File(configfolder,"datasaves");
    public static final File warpfolder = new File("world","warps");
    public static final File homefolder = new File("world","homes");
    public static final File config = new File(configfolder,"config.json");

    public static boolean allowBackup = false;
    public static boolean allowFly = false;
    public static boolean allowBack = true;
    public static boolean allowSeed = true;
    public static boolean debug = false;

    public configs()
    {

        if(!config.exists()) {
            try {
                config.createNewFile();

            } catch (IOException e) {
            }
        }

        else {
            try {

                // 读取配置文件
                FileReader fr = new FileReader(config);
                JsonObject shits2 =  new GsonBuilder().create().fromJson(fr, JsonObject.class);

                try{
                    // 仅为判断文件是否为标准的Json
                    shits2.has("allowBackup");
                }catch (NullPointerException badJson){


                    config.delete();
                    config.createNewFile();

                    FileWriter fw = new FileWriter(config);
                    JsonObject shits = new JsonObject();
                    shits.addProperty("allowBackup", allowBackup);
                    shits.addProperty("allowFly", allowFly);
                    shits.addProperty("allowBack", allowBack);
                    shits.addProperty("allowSeed",allowSeed);
                    shits.addProperty("debugVersion",debug);

                    fw.write(shits.toString());
                    fw.close();

                    fr = new FileReader(config);
                    shits2 = new GsonBuilder().create().fromJson(fr, JsonObject.class);
                }

                // 检验是全部配置包含
                boolean change = false;
                if (!shits2.has("allowBackup")) {
                    shits2.addProperty("allowBackup", allowBackup);
                    change = true;
                }
                if(!shits2.has("allowFly")){
                    shits2.addProperty("allowFly", allowFly);
                    change=true;
                }
                if(!shits2.has("allowBack")){
                    shits2.addProperty("allowBack", allowBack);
                    change=true;
                }
                if(!shits2.has("allowSeed")){
                    shits2.addProperty("allowSeed", allowSeed);
                    change=true;
                }
                if(!shits2.has("debugVersion")){
                    shits2.addProperty("debugVersion", debug);
                    change=true;
                }
                if(change){
                    FileWriter fw = new FileWriter(config);
                    fw.write(shits2.toString());
                    fw.close();
                }


                // 读取配置
                allowBackup = shits2.get("allowBackup").getAsBoolean();
                allowFly = shits2.get("allowFly").getAsBoolean();
                allowBack = shits2.get("allowBack").getAsBoolean();
                allowSeed = shits2.get("allowSeed").getAsBoolean();
                debug = shits2.get("debugVersion").getAsBoolean();
                fr.close();
            }
            catch (IOException e){}
        }
    }
}
