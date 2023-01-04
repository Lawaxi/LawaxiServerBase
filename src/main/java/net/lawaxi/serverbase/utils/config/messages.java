package net.lawaxi.serverbase.utils.config;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class messages {


    //玩家语言
    private static final File playerLanguagesFile = new File("Lawaxi", "playerlanguages.json");
    private static JsonObject pl2 = null;

    //语言表
    private static final File messagesFolder = new File("Lawaxi", "messages");
    private static final HashMap<String, String[]> m1 = new HashMap<>();


    private static final Charset utf8 = Charset.forName("UTF-8");


    public messages() {

        //玩家语言读取
        if (!playerLanguagesFile.exists()) {

            try {
                playerLanguagesFile.createNewFile();
                FileWriter fileWriter = new FileWriter(playerLanguagesFile);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

                JsonObject w3 = new JsonObject();
                w3.addProperty("null", "zh2");
                bufferedWriter.write(w3.toString());
                bufferedWriter.close();
                fileWriter.close();

            } catch (IOException ignored) {

            }
        }


        try {
            FileReader fileReader = new FileReader(playerLanguagesFile, utf8);
            pl2 = new GsonBuilder().create().fromJson(fileReader, JsonObject.class);
            fileReader.close();

        } catch (IOException e) {
            pl2 = null;
        }


        //默认三个语言文件生成
        if (!messagesFolder.exists()) {
            messagesFolder.mkdir();


            String[] l = {"zh", "zh2", "cant", "en"};
            for (String name : l) {
                InputStream input = this.getClass().getResourceAsStream("/messages/" + name + ".yml");
                try {
                    int index;
                    byte[] bytes = new byte[1024];
                    FileOutputStream downloadFile = new FileOutputStream(new File(messagesFolder, name + ".yml"));
                    while ((index = input.read(bytes)) != -1) {
                        downloadFile.write(bytes, 0, index);
                        downloadFile.flush();
                    }
                    downloadFile.close();
                    input.close();
                } catch (IOException ignored) {

                }

            }

        }

        //读自定义语言文件
        try {
            for (String name2 : messagesFolder.list()) {

                //除去".yml"
                String langname = name2.substring(0, name2.indexOf(".")).toLowerCase();
                if (!m1.containsKey(langname)) {

                    try{
                        ArrayList<String> a = new ArrayList();
                        FileReader q2 = new FileReader(new File(messagesFolder, name2), utf8);
                        BufferedReader q3 = new BufferedReader(q2);

                        String line;
                        while((line = q3.readLine()) != null){
                            a.add(line);
                        }

                        m1.put(langname, a.toArray(new String[0]));
                        q3.close();
                        q2.close();
                        System.out.println("成功加载语言: " + langname);

                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }

                }
            }


            //输出全部语言的第0行
            if (configs.debug) {
                for (String langname : m1.keySet()) {
                    System.out.println("[Lang] Language Debug: " + langname + " 的第0行为 " + getByLang(0,langname));
                }
            }


        } catch (NullPointerException e) {
            System.out.println("[Lang] 语言文件夹读取失败: " + messagesFolder.getAbsolutePath());
        }

    }

    public static String get(int index, String playername) {
        // index = (line in .yml file) - 2

        try {
            //玩家语言配置加载错误
            if (pl2 == null) {
                System.out.println("[Lang] Lang Info: 玩家语言配置加载错误");
                return "null";
            }

            //没有附加语言文件
            if (m1.keySet().size() == 0) {
                System.out.println("[Lang] Lang Info: 没有任何可用的语言文件");
                return "null";
            }

            String lang = getLang(playername);
            if (configs.debug) {
                System.out.println("[Lang] Lang Info: " + playername + " 的语言是 " + lang);
            }

            //玩家没有选择语言
            if (lang.equalsIgnoreCase("null")) {
                if (configs.debug) {
                    System.out.println("[Lang] Null Found: " + playername + " 的语言并未设置");
                }
                return getByLang(index,getDefaultLang());
            } else
                return getByLang(index,lang);
                
        } catch (NullPointerException e) {
            System.out.println("语言 " + index + " 不存在，试图获取时出错，请联系插件作者！");
            return "";
        }
    }

    public static String getByLang(int index, String lang){

        index++;

        try{
            return m1.get(lang.toLowerCase())[index];
        }catch(Exception e){
            return "null";
        }
    }

    public static String getLang(String playername) {

        //玩家语言配置加载错误
        if (pl2 == null) {
            return "null";
        }

        //玩家没有设置语言
        if (!pl2.has(playername)) {
            return "null";
        }

        //玩家语言不支持
        String lang = pl2.get(playername).getAsString();
        if (!m1.containsKey(lang)) {
            return "null";
        }

        return lang;
    }


    public static boolean setLang(String playername, String name) {

        if (pl2 == null)
            return false;

        if (pl2.has(playername)) {
            pl2.remove(playername);
        }

        pl2.addProperty(playername, name);

        try {
            FileWriter wn1 = new FileWriter(playerLanguagesFile);
            BufferedWriter wn2 = new BufferedWriter(wn1);
            wn2.write(pl2.toString());
            wn2.close();
            wn1.close();

            return true;

        } catch (Exception e) {
            return false;
        }
    }

    public static Set<String> getLangList() {
        return m1.keySet();
    }

    public static String getDefaultLang() {

        String nullLang = getLang("null");
        if (nullLang.equalsIgnoreCase("null")) {

            if (messagesFolder.list().length == 0) {
                return null;
            } else {
                String prename = messagesFolder.list()[0];
                return prename.substring(0, prename.indexOf("."));
            }
        } else
            return nullLang;
    }
}
