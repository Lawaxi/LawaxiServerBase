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

                            if(player.getServerWorld().getWorld()==player.getServer().getWorld(DimensionType.OVERWORLD)){
                                player.sendMessage(new LiteralText(messages.m.get(64).replace("%seed%",String.valueOf(ctx.getSource().getWorld().getSeed()))),false);
                            }
                            else if(player.getServerWorld().getWorld()==player.getServer().getWorld(DimensionType.THE_END)){
                                player.sendMessage(new LiteralText(messages.m.get(66).replace("%seed%",String.valueOf(ctx.getSource().getWorld().getSeed()))),false);
                            }
                            else if(player.getServerWorld().getWorld()==player.getServer().getWorld(DimensionType.THE_NETHER)){
                                player.sendMessage(new LiteralText(messages.m.get(65).replace("%seed%",String.valueOf(ctx.getSource().getWorld().getSeed()))),false);
                            }
                            else{
                                player.sendMessage(new LiteralText(messages.m.get(67).replace("%seed%",String.valueOf(ctx.getSource().getWorld().getSeed()))),false);
                            }

                            return 1;
                        })
        );
    }
}
