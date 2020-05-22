package net.lawaxi.serverbase.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.lawaxi.serverbase.utils.config.messages;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;

public class seed {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher)
    {
        dispatcher.register(CommandManager.literal("seed1")
                        .executes(ctx -> {

                            ServerPlayerEntity player = ctx.getSource().getPlayer();

                            if(player.getServerWorld().getWorld()==player.getServer().getWorld(DimensionType.OVERWORLD_REGISTRY_KEY)){
                                player.sendMessage(new LiteralText(messages.get(65,player.getGameProfile().getName()).replace("%seed%",String.valueOf(ctx.getSource().getWorld().getSeed()))),false);
                            }
                            else if(player.getServerWorld().getWorld()==player.getServer().getWorld(DimensionType.THE_END_REGISTRY_KEY)){
                                player.sendMessage(new LiteralText(messages.get(67,player.getGameProfile().getName()).replace("%seed%",String.valueOf(ctx.getSource().getWorld().getSeed()))),false);
                            }
                            else if(player.getServerWorld().getWorld()==player.getServer().getWorld(DimensionType.THE_NETHER_REGISTRY_KEY)){
                                player.sendMessage(new LiteralText(messages.get(66,player.getGameProfile().getName()).replace("%seed%",String.valueOf(ctx.getSource().getWorld().getSeed()))),false);
                            }
                            else{
                                player.sendMessage(new LiteralText(messages.get(68,player.getGameProfile().getName()).replace("%seed%",String.valueOf(ctx.getSource().getWorld().getSeed()))),false);
                            }

                            return 1;
                        })
        );
    }
}
