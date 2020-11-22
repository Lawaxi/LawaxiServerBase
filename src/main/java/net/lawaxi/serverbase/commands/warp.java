package net.lawaxi.serverbase.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.lawaxi.serverbase.utils.BrokenPositionException;
import net.lawaxi.serverbase.utils.LocationInfo;
import net.lawaxi.serverbase.utils.config.configs;
import net.lawaxi.serverbase.utils.config.messages;
import net.minecraft.command.CommandSource;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;

import java.io.File;

import static net.lawaxi.serverbase.commands.warps.getWarpNames;

public class warp {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("warp")
                .then(CommandManager.argument(messages.get(3, "null"), StringArgumentType.string())
                        .suggests((ctx, suggestionsBuilder) -> CommandSource.suggestMatching(getWarpNames(), suggestionsBuilder))
                        .executes(ctx -> {
                            String warpName = StringArgumentType.getString(ctx, messages.get(3, "null"));
                            File warpFile = new File(configs.warpFolder, warpName + ".yml");
                            ServerPlayerEntity player = ctx.getSource().getPlayer();
                            LocationInfo location;
                            try {
                                location = new LocationInfo(warpFile, player);
                                player.sendMessage(new LiteralText(messages.get(1, player.getGameProfile().getName())), false);
                                player.sendMessage(new LiteralText(messages.get(2, player.getGameProfile().getName()).replace("%to%", warpName)), true);
                                location.teleport(player);
                                return 1;
                            } catch (BrokenPositionException e) {
                                player.sendMessage(new LiteralText(messages.get(4, ctx.getSource().getPlayer().getGameProfile().getName()).replace("%to%", warpName)), false);
                                return -1;
                            }
                        }))
                .executes(ctx -> {
                    warps.getWarps(ctx.getSource().getPlayer());
                    return 1;
                })
        );
    }
}
