package net.lawaxi.serverbase.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.lawaxi.serverbase.utils.config.configs;
import net.lawaxi.serverbase.utils.config.messages;
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
        return messages.m.get(5).replace("%name%",name.substring(0,name.length()-4))+messages.m.get(6);
    }

    public static int getWarps(ServerPlayerEntity player)
    {
        File warpfolder = new File(configs.warpfolder);
        if(warpfolder.exists())
        {
            String[] filelist = warpfolder.list();
            if(filelist.length!=0)
            {
                String filelist2= messages.m.get(4);
                for(int i=0;i<filelist.length;i++)
                {
                    filelist2+=sortName(filelist[i]);
                }

                player.sendMessage(new LiteralText(filelist2.substring(0,filelist2.length()-messages.m.get(6).length())),false);

                return 0;
            }
        }
        player.sendMessage(new LiteralText(messages.m.get(7)),false);
        return 0;
    }

}
