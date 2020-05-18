package net.lawaxi.serverbase.utils.config;

import java.io.*;
import java.util.ArrayList;

public class messages {

    public static final File messages = new File("Lawaxi","messages.yml");

    public static String hello = "你好，%player%！";
    public static final ArrayList<String> m = new ArrayList<>();

    public static void init(){

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
        m.add("§c您处于观察者模式 对方正欲接受请求但被服务器驳回了");   //63
        m.add("§e[种子] §6主世界: %seed%");
        m.add("§e[种子] §6地狱: %seed%");
        m.add("§e[种子] §6末地: %seed%");
        m.add("§e[种子] §6未知世界: %seed%");
        m.add("§b@%player% §9在主世界: §bx=%x% y=%y% z=%z%");
        m.add("§b@%player% §9在地狱: §bx=%x% y=%y% z=%z%");
        m.add("§b@%player% §9在末地: §bx=%x% y=%y% z=%z%");
        m.add("§b@%player% §9在未知世界: §bx=%x% y=%y% z=%z%");

        if(!messages.exists()){
            try {
                messages.createNewFile();

                FileWriter a1 = new FileWriter(messages);
                BufferedWriter a = new BufferedWriter(a1);

                a.write(hello);
                for(int i=0;i<m.size();i++){
                    a.newLine();
                    a.write(m.get(i));
                }
                a.close();
                a1.close();

            }catch (IOException e){}
        }else {

            BufferedReader b;
            try {

                b = new BufferedReader(new FileReader(messages));

            } catch (IOException e) {
                return;
            }

            try{
                hello = b.readLine();
            }catch (IOException e){
                return;
            }

            for(int i=0;i<b.lines().count()-1;i++){

                if(i>=m.size())
                    break;

                try{
                    m.set(i, b.readLine());
                }catch (IOException e){
                    return;
                }
            }
        }
    }
}
