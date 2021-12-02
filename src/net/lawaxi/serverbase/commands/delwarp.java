package net.lawaxi.serverbase.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.lawaxi.serverbase.utils.config.configs;
import net.lawaxi.serverbase.utils.config.messages;
import net.minecraft.command.CommandSource;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;

import java.io.File;

import static net.lawaxi.serverbase.commands.warps.getWarpNames;

public class delwarp {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("delwarp")
                .then(CommandManager.argument(messages.get(43, "null"), StringArgumentType.string())
                        .suggests((ctx, suggestionsBuilder) -> CommandSource.suggestMatching(getWarpNames(), suggestionsBuilder))
                        .executes(ctx -> {

                            ServerPlayerEntity player = ctx.getSource().getPlayer();
                            String warpname = StringArgumentType.getString(ctx, messages.get(43, "null"));
                            File warpfile = new File(configs.warpFolder + File.separator + warpname + ".yml");
                            if (warpfile.exists()) {
                                if (warpfile.delete()) {
                                    player.sendMessage(new LiteralText(messages.get(44, player.getGameProfile().getName()).replace("%name%", warpname)), false);
                                } else {
                                    player.sendMessage(new LiteralText(messages.get(45, player.getGameProfile().getName()).replace("%name%", warpname)), false);
                                }
                            } else {
                                ctx.getSource().getPlayer().sendMessage(new LiteralText(messages.get(46, player.getGameProfile().getName()).replace("%name%", warpname)), false);
                            }
                            return 1;
                        }))
                .executes(ctx -> {
                    ctx.getSource().getPlayer().sendMessage(new LiteralText(messages.get(47, ctx.getSource().getPlayer().getGameProfile().getName())), false);
                    return 1;
                })
        );
    }
}
