package net.lawaxi.serverbase.commands;

import com.google.common.io.Files;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.lawaxi.serverbase.utils.WorldDescription;
import net.lawaxi.serverbase.utils.config.configs;
import net.lawaxi.serverbase.utils.config.messages;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class setwarp {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("setwarp")
                .then(CommandManager.argument(messages.get(9, "null"), StringArgumentType.string())
                        .executes(ctx -> {
                            ServerPlayerEntity player = ctx.getSource().getPlayer();

                            File warpfolder = configs.warpFolder;
                            if (!warpfolder.exists())
                                warpfolder.mkdir();

                            String warpname = StringArgumentType.getString(ctx, messages.get(9, "null"));
                            File warpfile = new File(warpfolder, warpname + ".yml");
                            if (warpfile.exists()) {
                                player.sendMessage(new LiteralText(messages.get(10, player.getGameProfile().getName()).replace("%name%", warpname)), false);
                            } else {
                                try {
                                    String world = WorldDescription.getDiscription(player.getServerWorld(), player.getServer());
                                    if (world.equals("shit")) {
                                        ctx.getSource().getPlayer().sendMessage(new LiteralText(messages.get(11, player.getGameProfile().getName())), false);
                                    } else {
                                        BufferedWriter buffer = Files.newWriter(warpfile, StandardCharsets.UTF_8);

                                        buffer.write(world);
                                        buffer.newLine();
                                        buffer.write(String.valueOf(player.getX()));
                                        buffer.newLine();
                                        buffer.write(String.valueOf(player.getY()));
                                        buffer.newLine();
                                        buffer.write(String.valueOf(player.getZ()));

                                        buffer.close();
                                        ctx.getSource().getPlayer().sendMessage(new LiteralText(messages.get(12, player.getGameProfile().getName()).replace("%name%", warpname)), false);
                                        ctx.getSource().getPlayer().sendMessage(new LiteralText(messages.get(2, player.getGameProfile().getName()).replace("%to%", warpname)), true);
                                    }
                                } catch (IOException ignored) {
                                }
                            }
                            return 1;
                        }))
                .executes(ctx -> {
                    ctx.getSource().getPlayer().sendMessage(new LiteralText(messages.get(13, ctx.getSource().getPlayer().getGameProfile().getName())), false);
                    return 1;
                })
        );
    }
}
