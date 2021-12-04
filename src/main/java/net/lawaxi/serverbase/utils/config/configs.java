package net.lawaxi.serverbase.utils.config;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class configs {

    public static final File worldFolder = new File("world");
    public static final File configFolder = new File("Lawaxi");
    public static final File inventoryBackupFolder = new File(configFolder, "inventoryBackups");
    public static final File warpFolder = new File(worldFolder, "warps");
    public static final File homeFolder = new File(worldFolder, "homes");
    public static final File config = new File(configFolder, "config.json");

    public static boolean allowBackup = false;
    public static boolean allowFly = false;
    public static boolean allowBack = true;
    public static boolean allowSeed = true;
    public static boolean debug = false;

    public static boolean freecam = true;

    public configs() {

        if (!config.exists()) {
            try {
                config.createNewFile();
            } catch (IOException ignored) {
            }
        } else {
            try {

                // 读取配置文件
                FileReader fr = new FileReader(config);
                JsonObject jsonObject = new GsonBuilder().create().fromJson(fr, JsonObject.class);

                try {
                    // 仅为判断文件是否为标准的Json
                    jsonObject.has("allowBackup");
                } catch (NullPointerException badJson) {


                    config.delete();
                    config.createNewFile();

                    FileWriter fw = new FileWriter(config);
                    JsonObject shits = new JsonObject();
                    shits.addProperty("allowBackup", allowBackup);
                    shits.addProperty("allowFly", allowFly);
                    shits.addProperty("allowBack", allowBack);
                    shits.addProperty("allowSeed", allowSeed);
                    shits.addProperty("debugVersion", debug);
                    shits.addProperty("freecam", freecam);

                    fw.write(shits.toString());
                    fw.close();

                    fr = new FileReader(config);
                    jsonObject = new GsonBuilder().create().fromJson(fr, JsonObject.class);
                }

                // 检验是全部配置包含
                boolean change = false;
                if (!jsonObject.has("allowBackup")) {
                    jsonObject.addProperty("allowBackup", allowBackup);
                    change = true;
                }
                if (!jsonObject.has("allowFly")) {
                    jsonObject.addProperty("allowFly", allowFly);
                    change = true;
                }
                if (!jsonObject.has("allowBack")) {
                    jsonObject.addProperty("allowBack", allowBack);
                    change = true;
                }
                if (!jsonObject.has("allowSeed")) {
                    jsonObject.addProperty("allowSeed", allowSeed);
                    change = true;
                }
                if (!jsonObject.has("debugVersion")) {
                    jsonObject.addProperty("debugVersion", debug);
                    change = true;
                }
                if (!jsonObject.has("freecam")) {
                    jsonObject.addProperty("freecam", freecam);
                    change = true;
                }
                if (change) {
                    FileWriter fw = new FileWriter(config);
                    fw.write(jsonObject.toString());
                    fw.close();
                }


                // 读取配置
                allowBackup = jsonObject.get("allowBackup").getAsBoolean();
                allowFly = jsonObject.get("allowFly").getAsBoolean();
                allowBack = jsonObject.get("allowBack").getAsBoolean();
                allowSeed = jsonObject.get("allowSeed").getAsBoolean();
                debug = jsonObject.get("debugVersion").getAsBoolean();
                freecam = jsonObject.get("freecam").getAsBoolean();
                fr.close();
            } catch (IOException ignored) {
            }
        }
    }
}
