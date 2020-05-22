package net.lawaxi.serverbase.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.lawaxi.serverbase.utils.config.messages;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;

public class fly {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher)
    {
        dispatcher.register(CommandManager.literal("fly")
                        .executes(ctx -> {

                            ServerPlayerEntity player = ctx.getSource().getPlayer();
                            if(player.abilities.allowFlying){
                                player.abilities.allowFlying=false;
                                player.abilities.flying=false;
                                player.sendMessage(new LiteralText(messages.get(55,player.getGameProfile().getName())),false);
                            }
                            else{
                                if(player.getServer().getPlayerManager().getWhitelist().isAllowed(player.getGameProfile())){

                                    player.abilities.allowFlying=true;
                                    player.abilities.flying=true;
                                    player.sendMessage(new LiteralText(messages.get(56,player.getGameProfile().getName())),false);
                                }
                                else{
                                    player.sendMessage(new LiteralText(messages.get(57,player.getGameProfile().getName())),false);
                                }
                            }
                            return 1;
                        })
        );
    }
}
