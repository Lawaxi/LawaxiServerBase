package net.lawaxi.serverbase.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.lawaxi.serverbase.utils.LocationInfo;
import net.lawaxi.serverbase.utils.PseudoFreecam;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.world.GameMode;

public class c {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("c")
                .executes(ctx -> {
                    ServerPlayerEntity player = ctx.getSource().getPlayer();
                    if (player.interactionManager.getGameMode() == GameMode.SURVIVAL) {
                        PseudoFreecam.actualLocation.put(player.getGameProfile(), new LocationInfo(player));
                        player.changeGameMode(GameMode.SPECTATOR);
                        player.sendMessage(Text.literal("§6Freecam On"), true);
                        return 1;
                    } else {
                        player.sendMessage(Text.literal("§cYou are not in survival mode!"), false);
                        return 0;
                    }
                }));
    }
}