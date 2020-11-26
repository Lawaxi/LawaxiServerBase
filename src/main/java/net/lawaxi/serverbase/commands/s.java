package net.lawaxi.serverbase.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.lawaxi.serverbase.utils.LocationInfo;
import net.lawaxi.serverbase.utils.PseudoFreecam;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.world.GameMode;

public class s {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("s")
                .executes(ctx -> {
                    ServerPlayerEntity player = ctx.getSource().getPlayer();
                    if (player.isSpectator()) {
                        if (PseudoFreecam.actualLocation.containsKey(player.getGameProfile())) { // has location in hashmap
                            LocationInfo actualLocation = PseudoFreecam.actualLocation.get(player.getGameProfile());
                            actualLocation.teleport(player);
                            player.setGameMode(GameMode.SURVIVAL);
                            player.sendMessage(new LiteralText("§6Freecam Off"), true);
                            PseudoFreecam.actualLocation.remove(player.getGameProfile());
                            return 1;
                        } else {
                            player.sendMessage(new LiteralText(
                                    "§cYou did not use /c or location is lost. If the latter is the case, please contact server administrator."
                            ), false);
                            return 0;
                        }
                    } else {
                        player.sendMessage(new LiteralText(
                                "§cYou are not in spectator mode!"
                        ), false);
                        return 0;
                    }
                })
        );
    }
}
