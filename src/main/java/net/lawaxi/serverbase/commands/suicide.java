package net.lawaxi.serverbase.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.lawaxi.serverbase.utils.config.messages;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.world.GameMode;

public class suicide {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("suicide")
                .executes(ctx -> {
                    ServerPlayerEntity player = ctx.getSource().getPlayer();
                    try {
                        if (player.interactionManager.getGameMode() == GameMode.SPECTATOR) {
                            player.sendMessage(Text.literal(messages.get(81, player.getGameProfile().getName())), false);
                            return 1;
                        }
                        player.kill();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    return 0;
                }));
    }
}
