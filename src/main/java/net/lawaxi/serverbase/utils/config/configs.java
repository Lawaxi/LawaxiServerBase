package net.lawaxi.serverbase.utils.config;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import java.io.*;

public class configs {

    public static final String configfolder = "Lawaxi";
    public static final String backupfolder =configfolder+File.separator+"datasaves";
    public static final String warpfolder = "world"+File.separator+"warps";
    public static final String homefolder = "world"+File.separator+"homes";

    public static boolean allowBackup = false;
    public static boolean allowFly = false;
    public static boolean allowBack = true;

    public static void init()
    {
        File config = new File(configfolder+File.separator+"config.json");

        if(!config.exists()) {
            try {
                config.createNewFile();

                FileWriter fw = new FileWriter(config);
                JsonObject shits = new JsonObject();
                shits.addProperty("allowBackup", true);
                shits.addProperty("allowFly", false);
                shits.addProperty("allowBack", true);
                fw.write(shits.toString());
                fw.close();

            } catch (IOException e) {
            }
        }
        else {
            try {
                FileReader fr = new FileReader(config);
                JsonObject shits = new GsonBuilder().create().fromJson(fr,JsonObject.class);
                allowBackup = shits.get("allowBackup").getAsBoolean();
                allowFly = shits.get("allowFly").getAsBoolean();
                allowBack = shits.get("allowBack").getAsBoolean();
                fr.close();
            }
            catch (IOException e){}
        }
    }
}
