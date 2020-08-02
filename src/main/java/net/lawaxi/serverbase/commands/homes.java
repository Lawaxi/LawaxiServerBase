package net.lawaxi.serverbase.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.lawaxi.serverbase.utils.config.configs;
import net.lawaxi.serverbase.utils.config.messages;
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
        File homefolder = new File(configs.homefolder,player.getEntityName());
        if(homefolder.exists())
        {
            String[] filelist = homefolder.list();
            if(filelist.length!=0)
            {
                String filelist2= messages.get(16,player.getGameProfile().getName()).replace("%player%",player.getEntityName());
                for(int i=0;i<filelist.length;i++)
                {
                    filelist2+=warps.sortName(filelist[i],player.getGameProfile().getName());
                }

                player.sendMessage(new LiteralText(filelist2.substring(0,filelist2.length()-3)),false);

                return 0;
            }
        }
        player.sendMessage(new LiteralText(messages.get(17,player.getGameProfile().getName())),false);
        return 0;
    }
}
