package net.lawaxi.serverbase.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;

public class ops {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher)
    {
        dispatcher.register(CommandManager.literal("ops")
                        .executes(ctx -> {

                            ServerPlayerEntity player = ctx.getSource().getPlayer();

                            String out ="";
                            for(String name : player.getServer().getPlayerManager().getOpList().getNames())
                            {
                                out+=name+",";
                            }

                            if(out!="")
                                player.sendMessage(new LiteralText("§eop列表：§6"+out.substring(0,out.length()-1)),false);
                            else
                                player.sendMessage(new LiteralText("§e本服务器没有op."),false);
                            return 1;
                        })
        );
    }
}
