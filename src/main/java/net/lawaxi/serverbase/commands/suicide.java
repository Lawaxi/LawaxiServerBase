package net.lawaxi.serverbase.commands;

import com.mojang.brigadier.CommandDispatcher;

import net.lawaxi.serverbase.utils.config.messages;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.world.GameMode;

public class suicide {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("suicide")
                .executes(ctx -> {
                    ServerPlayerEntity player = ctx.getSource().getPlayer();
                    if (player.interactionManager.getGameMode() != GameMode.SPECTATOR) {
                        player.sendMessage(new LiteralText(messages.get(82, player.getGameProfile().getName())), false);
                    } else {
                        player.kill();
                    }
                    return 1;
                }));
    }
}
