package net.lawaxi.serverbase.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.lawaxi.serverbase.utils.list;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;

import java.io.File;

public class homes {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher)
    {
        dispatcher.register(CommandManager.literal("homes")
                        .executes(ctx -> {

                            ServerPlayerEntity player = ctx.getSource().getPlayer();
                            getHome(player);
                            return 1;
                        })
        );
    }

    public static int getHome(ServerPlayerEntity player)
    {
        File homefolder = new File(list.homefolder+File.separator+player.getEntityName());
        if(homefolder.exists())
        {
            String[] filelist = homefolder.list();
            if(filelist.length!=0)
            {
                String filelist2="§6玩家§e "+player.getEntityName()+" §6的家列表：";
                for(int i=0;i<filelist.length;i++)
                {
                    filelist2+=warps.sortName(filelist[i]);
                }

                player.sendMessage(new LiteralText(filelist2.substring(0,filelist2.length()-3)),false);

                return 0;
            }
        }
        player.sendMessage(new LiteralText("§c您没有可用的家"),false);
        return 0;
    }
}
