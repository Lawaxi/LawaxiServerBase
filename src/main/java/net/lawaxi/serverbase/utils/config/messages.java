package net.lawaxi.serverbase.utils.config;

import java.io.*;
import java.util.ArrayList;

public class messages {

    public static final File messages = new File("Lawaxi","messages.yml");

    public static String hello = "你好，%player%！";
    public static final ArrayList<String> m = new ArrayList<>();
    public static final short size = 64;

    public static void init(){
        if(!messages.exists()){
            try {
                messages.createNewFile();

                FileWriter a1 = new FileWriter(messages);
                BufferedWriter a = new BufferedWriter(a1);

                a.write("§a你好，%player%！");
                a.newLine();

                a.write("§a正在传送...");
                a.newLine();

                a.write("§a%to%");
                a.newLine();

                a.write("地标名称");
                a.newLine();

                a.write("§c地标§4 %to% §c不存在或已损坏");
                a.newLine();

                a.write("§6地标列表：");
                a.newLine();

                a.write("§e%name%");
                a.newLine();

                a.write("§6,");
                a.newLine();

                a.write("§c没有可用的地标");
                a.newLine();

                a.write("地标名称");
                a.newLine();

                a.write("§c地标§4 %name% §c已存在，请删除后再写入");
                a.newLine();

                a.write("§c很抱歉，暂不支持除主世界 地狱 末地外的地标设置");
                a.newLine();

                a.write("§a地标§2 %name% §a创建成功");
                a.newLine();

                a.write("§c请输入要创建的地标名称");
                a.newLine();

                a.write("家的名称");
                a.newLine();

                a.write("§c家§4 %to% §c不存在或已损坏");
                a.newLine();

                a.write("§6玩家§e %player% §6的家列表：");
                a.newLine();

                a.write("§c您没有可用的家");
                a.newLine();

                a.write("家的名称");
                a.newLine();

                a.write("§c家§4 %name% §c已存在，请删除后再写入");
                a.newLine();

                a.write("§c很抱歉，暂不支持除主世界 地狱 末地外的家的设置");
                a.newLine();

                a.write("§a家§2 %name% §a创建成功");
                a.newLine();

                a.write("§c请输入要创建的家的名称");
                a.newLine();

                a.write("§a主城");
                a.newLine();

                a.write("玩家");
                a.newLine();

                a.write("§c您不能传送自己");
                a.newLine();

                a.write("§6已成功发送传送请求");
                a.newLine();

                a.write("§e%from% §6请求传送到你这里:");
                a.newLine();

                a.write("§6同意请输入/tpaccept");
                a.newLine();

                a.write("§6不同意请输入/tpadeny");
                a.newLine();

                a.write("§c传送请求发送失败");
                a.newLine();

                a.write("§c您已经有一条挂起的请求了");
                a.newLine();

                a.write("§c请输入要传送的玩家");
                a.newLine();

                a.write("§c您不能请求自己传送");
                a.newLine();

                a.write("§e%from% §6请求你传送到他那里:");
                a.newLine();

                a.write("§c您没有待接受的请求");
                a.newLine();

                a.write("§c您没有待拒绝的请求");
                a.newLine();

                a.write("§c对方拒绝了你的请求");
                a.newLine();

                a.write("§c已成功拒绝对方的请求");
                a.newLine();

                a.write("§eop列表：§6");

                a.newLine();
                a.write("§e本服务器没有op");
                a.newLine();

                a.write("§4banned列表：§6");
                a.newLine();

                a.write("§e本服务器没有玩家曾经被封禁");
                a.newLine();

                a.write("地标名称");
                a.newLine();

                a.write("§a地标§2 %name% §a删除成功");
                a.newLine();

                a.write("§c地标§4 %name% §c删除失败");
                a.newLine();

                a.write("§c地标§4 %name% §c不存在");
                a.newLine();

                a.write("§c请输入要删除的地标名称");
                a.newLine();

                a.write("家的名称");
                a.newLine();

                a.write("§a家§2 %name% §a删除成功");
                a.newLine();

                a.write("§c家§4 %name% §c删除失败");
                a.newLine();

                a.write("§c家§4 %name% §c不存在");
                a.newLine();

                a.write("§c请输入要删除的家的名称");
                a.newLine();

                a.write("§c您手中没有物品");
                a.newLine();

                a.write("§a享受您的新帽子吧~");
                a.newLine();

                a.write("§6切换飞行模式为: §c关闭");
                a.newLine();

                a.write("§6切换飞行模式为: §a开启");
                a.newLine();

                a.write("§c您没有权限开启飞行");
                a.newLine();

                a.write("§c您不在允许备份的白名单中");
                a.newLine();

                a.write("§a正在备份，请稍后连入");
                a.newLine();

                a.write("§c您没有要载入的备份");
                a.newLine();

                a.write("§a正在读取备份，请稍后连入");
                a.newLine();

                a.write("§c您不能在观察者模式下传送");
                a.newLine();

                a.write("§c对方正处于观察者模式中 无法接受传送请求");
                a.newLine();

                a.write("§c您处于观察者模式 对方正欲接受请求但被服务器驳回了");
                a.newLine();

                a.close();
                a1.close();

            }catch (IOException e){}
        }


        try {

            BufferedReader b = new BufferedReader(new FileReader(messages));

            hello = b.readLine();
            for(short i=0;i<size;i++){
                m.add(b.readLine());
            }


        }catch (IOException e){
            System.out.println("无法加载 messages.yml 语言文件，请检查后再启动服务器！");

            for(short i=0;i<size;i++){
                m.add("unloaded");
            }
        }
    }
}
