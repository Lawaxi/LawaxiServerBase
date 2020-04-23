package net.lawaxi.serverbase.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.lawaxi.serverbase.utils.list;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;

import java.io.File;

public class warps {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher)
    {
        dispatcher.register(CommandManager.literal("warps")
                        .executes(ctx -> {

                            ServerPlayerEntity player = ctx.getSource().getPlayer();
                            getWarps(player);
                            return 1;
                        })
        );
    }

    public static String sortName(String name)
    {
        //去掉文件名结尾的".yml"
        return "§e"+name.substring(0,name.length()-4)+"§6,";
    }

    public static int getWarps(ServerPlayerEntity player)
    {
        File warpfolder = new File(list.warpfolder);
        if(warpfolder.exists())
        {
            String[] filelist = warpfolder.list();
            if(filelist.length!=0)
            {
                String filelist2="§6地标列表：";
                for(int i=0;i<filelist.length;i++)
                {
                    filelist2+=sortName(filelist[i]);
                }

                player.sendMessage(new LiteralText(filelist2.substring(0,filelist2.length()-3)),false);

                return 0;
            }
        }
        player.sendMessage(new LiteralText("§c没有可用的地标"),false);
        return 0;
    }

}
