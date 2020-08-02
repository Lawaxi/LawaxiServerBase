package net.lawaxi.serverbase.utils.config;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class messages {

    private static final File pl1 = new File("Lawaxi","playerlanguages.json");
    private static final File messagesFolder = new File("Lawaxi","messages");

    private static final ArrayList<String> m = new ArrayList<>();
    private static final HashMap<String,ArrayList<String>> m1 = new HashMap<>();

    private static JsonObject pl2 = null;

    public messages(){

        //玩家语言读取
        if(!pl1.exists()) {

            try {
                pl1.createNewFile();
                FileWriter w1 = new FileWriter(pl1);
                BufferedWriter w2 = new BufferedWriter(w1);

                JsonObject w3 = new JsonObject();
                w3.addProperty("null","zhsp");
                w2.write(w3.toString());
                w2.close();
                w1.close();

            }catch (IOException e){

            }
        }


        try {
            FileReader r1 = new FileReader(pl1);
            pl2 =  new GsonBuilder().create().fromJson(r1, JsonObject.class);
            r1.close();

        }catch (IOException e){
            pl2=null;
        }


        //默认三个语言文件生成
        if(!messagesFolder.exists()){
            messagesFolder.mkdir();


            String[] l = {"zh","zhsp","en"};
            for(int i=0;i<l.length;i++){


                String name = l[i];
                InputStream input = this.getClass().getResourceAsStream("/messages/"+name+".yml");

                try {
                    int index;
                    byte[] bytes = new byte[1024];
                    FileOutputStream downloadFile = new FileOutputStream(new File(messagesFolder,name+".yml"));
                    while ((index = input.read(bytes)) != -1) {
                        downloadFile.write(bytes, 0, index);
                        downloadFile.flush();
                    }
                    downloadFile.close();
                    input.close();

                }catch (IOException e){

                }

            }

        }


        //默认放bug语言
        m.add("§a你好，%player%！");
        m.add("§a正在传送...");
        m.add("§a%to%");
        m.add("地标名称");
        m.add("§c地标§4 %to% §c不存在或已损坏");
        m.add("§6地标列表：");
        m.add("§e%name%");
        m.add("§6,");
        m.add("§c没有可用的地标");
        m.add("地标名称");
        m.add("§c地标§4 %name% §c已存在，请删除后再写入");
        m.add("§c很抱歉，暂不支持除主世界 地狱 末地外的地标设置");
        m.add("§a地标§2 %name% §a创建成功");
        m.add("§c请输入要创建的地标名称");
        m.add("家的名称");
        m.add("§c家§4 %to% §c不存在或已损坏");
        m.add("§6玩家§e %player% §6的家列表：");
        m.add("§c您没有可用的家");
        m.add("家的名称");
        m.add("§c家§4 %name% §c已存在，请删除后再写入");
        m.add("§c很抱歉，暂不支持除主世界 地狱 末地外的家的设置");
        m.add("§a家§2 %name% §a创建成功");
        m.add("§c请输入要创建的家的名称");
        m.add("§a主城");
        m.add("玩家");
        m.add("§c您不能传送自己");
        m.add("§6已成功发送传送请求");
        m.add("§e%from% §6请求传送到你这里:");
        m.add("§6同意请输入/tpaccept");
        m.add("§6不同意请输入/tpadeny");
        m.add("§c传送请求发送失败");
        m.add("§c您已经有一条挂起的请求了");
        m.add("§c请输入要传送的玩家");
        m.add("§c您不能请求自己传送");
        m.add("§e%from% §6请求你传送到他那里:");
        m.add("§c您没有待接受的请求");
        m.add("§c您没有待拒绝的请求");
        m.add("§c对方拒绝了你的请求");
        m.add("§c已成功拒绝对方的请求");
        m.add("§eop列表：§6");
        m.add("§e本服务器没有op");
        m.add("§4banned列表：§6");
        m.add("§e本服务器没有玩家曾经被封禁");
        m.add("地标名称");
        m.add("§a地标§2 %name% §a删除成功");
        m.add("§c地标§4 %name% §c删除失败");
        m.add("§c地标§4 %name% §c不存在");
        m.add("§c请输入要删除的地标名称");
        m.add("家的名称");
        m.add("§a家§2 %name% §a删除成功");
        m.add("§c家§4 %name% §c删除失败");
        m.add("§c家§4 %name% §c不存在");
        m.add("§c请输入要删除的家的名称");
        m.add("§c您手中没有物品");
        m.add("§a享受您的新帽子吧~");
        m.add("§6切换飞行模式为: §c关闭");
        m.add("§6切换飞行模式为: §a开启");
        m.add("§c您没有权限开启飞行");
        m.add("§c您不在允许备份的白名单中");
        m.add("§a正在备份，请稍后连入");
        m.add("§c您没有要载入的备份");
        m.add("§a正在读取备份，请稍后连入");
        m.add("§c您不能在观察者模式下传送");
        m.add("§c对方正处于观察者模式中 无法接受传送请求");
        m.add("§c您处于观察者模式 对方正欲接受请求但被服务器驳回了");
        m.add("§e[种子] §6主世界: %seed%");
        m.add("§e[种子] §6地狱: %seed%");
        m.add("§e[种子] §6末地: %seed%");
        m.add("§e[种子] §6未知世界: %seed%");
        m.add("§b@%player% §9在主世界: §bx=%x% y=%y% z=%z%");
        m.add("§b@%player% §9在地狱: §bx=%x% y=%y% z=%z%");
        m.add("§b@%player% §9在末地: §bx=%x% y=%y% z=%z%");
        m.add("§b@%player% §9在未知世界: §bx=%x% y=%y% z=%z%");
        m.add("§9支持的语言列表: "); //73
        m.add("§b%lang%");
        m.add("§9,");
        m.add("§a成功将您的显示语言切换至 §b%lang%");
        m.add("§c语言切换失败");
        m.add("§c不支持的语言");
        m.add("§9您的语言: §b%lang%");


        //读自定义语言文件
        for(String name2 : messagesFolder.list()){

            //除去".yml"
            String langname = name2.substring(0,name2.indexOf("."));
            if(!m1.containsKey(langname)) {


                ArrayList<String> l1919 = (ArrayList<String>)m.clone();
                try {

                    FileReader q2 = new FileReader(new File(messagesFolder, name2));
                    BufferedReader q3 = new BufferedReader(q2);

                    for (int i = 0; i < m.size(); i++) {

                        try {
                            String a = q3.readLine();
                            l1919.set(i,a);
                        }catch (IOException e2){
                            break;
                        }
                    }

                    q3.close();
                    q2.close();

                    m1.put(langname.toString(),(ArrayList<String>)l1919.clone());
                    System.out.println("成功加载语言: " + langname);

                } catch (Exception e1) {
                    e1.printStackTrace();
                }

            }
        }



        //输出全部语言的第0行
        if(configs.debug){
            for(String key :m1.keySet()){
                System.out.println("[Lang] Language Debug: "+key+" 的第0行为 "+m1.get(key).get(0));
            }
        }
    }

    public static String get(int index, String playername){

        try {
            //玩家语言配置加载错误
            if (pl2 == null) {
                System.out.println("[Lang] Lang Info: 玩家语言配置加载错误");
                return m.get(index);
            }

            //没有附加语言文件
            if (m1.keySet().size()==0) {
                System.out.println("[Lang] Lang Info: 没有任何可用的语言文件");
                return m.get(index);
            }

            String lang = getLang(playername);
            if(configs.debug){
                System.out.println("[Lang] Lang Info: "+playername+" 的语言是 "+lang);
            }

            //玩家没有选择语言
            if (lang.equalsIgnoreCase("null"))
            {
                if(configs.debug) {
                    System.out.println("[Lang] Null Found: " + playername + " 的语言并未设置");
                }
                return m.get(index);
            }
            else
                return m1.get(lang).get(index);
        }
        catch (NullPointerException e){
            System.out.println("语言 "+index+" 不存在，试图获取时出错，请联系插件作者！");
            return "";
        }
    }

    public static String getLang(String playername){

        //玩家语言配置加载错误
        if(pl2==null) {
            return "null";
        }

        //玩家没有设置语言
        if(!pl2.has(playername)) {
            return "null";
        }

        //玩家语言不支持
        String lang = pl2.get(playername).getAsString();
        if(!m1.containsKey(lang)){
            return "null";
        }

        return lang;
    }


    public static boolean setLang(String playername,String name){

        if(pl2==null)
            return false;

        if(pl2.has(playername)){
            pl2.remove(playername);
        }

        pl2.addProperty(playername,name);

        try {
            FileWriter wn1 = new FileWriter(pl1);
            BufferedWriter wn2 = new BufferedWriter(wn1);
            wn2.write(pl2.toString());
            wn2.close();
            wn1.close();

            return true;

        }catch (Exception e){
            return false;
        }
    }

    public static Set<String> getLangList(){
        return m1.keySet();
    }

    public static String getDefaultLang(){

        String nullLang = getLang("null");
        if(nullLang.equalsIgnoreCase("null"))
        {

            if(messagesFolder.list().length==0){
                return null;
            }
            else{
                String prename = messagesFolder.list()[0];
                return prename.substring(0,prename.indexOf("."));
            }
        }
        else
            return nullLang;
    }
}
