package net.lawaxi.serverbase.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.lawaxi.serverbase.utils.List;
import net.lawaxi.serverbase.utils.LocationInfo;
import net.lawaxi.serverbase.utils.config.messages;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;

public class back {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("back")
                .executes(ctx -> {
                    ServerPlayerEntity player = ctx.getSource().getPlayer();

                    LocationInfo a = List.lastlocation.get(player.getGameProfile());
                    player.sendMessage(new LiteralText(messages.get(1, player.getGameProfile().getName())), false);
                    player.teleport(a.world, a.position.getX(), a.position.getY(), a.position.getZ(), 0, 0);

                    return 1;
                })
        );
    }
}
