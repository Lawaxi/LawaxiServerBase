package net.lawaxi.serverbase.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.lawaxi.serverbase.utils.config.messages;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.world.World;

public class seed {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("seed1")
                .executes(ctx -> {

                    ServerPlayerEntity player = ctx.getSource().getPlayer();

                    if (player.getWorld() == player.getServer().getWorld(World.OVERWORLD)) {
                        player.sendMessage(new LiteralText(messages.get(65, player.getGameProfile().getName()).replace("%seed%", String.valueOf(ctx.getSource().getWorld().getSeed()))), false);
                    } else if (player.getWorld() == player.getServer().getWorld(World.END)) {
                        player.sendMessage(new LiteralText(messages.get(67, player.getGameProfile().getName()).replace("%seed%", String.valueOf(ctx.getSource().getWorld().getSeed()))), false);
                    } else if (player.getWorld() == player.getServer().getWorld(World.NETHER)) {
                        player.sendMessage(new LiteralText(messages.get(66, player.getGameProfile().getName()).replace("%seed%", String.valueOf(ctx.getSource().getWorld().getSeed()))), false);
                    } else {
                        player.sendMessage(new LiteralText(messages.get(68, player.getGameProfile().getName()).replace("%seed%", String.valueOf(ctx.getSource().getWorld().getSeed()))), false);
                    }

                    return 1;
                })
        );
    }
}
