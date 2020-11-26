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
import java.util.ArrayList;
import java.util.Objects;

public class setwarp {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("setwarp")
                .then(CommandManager.argument(messages.get(9, "null"), StringArgumentType.string())
                        .executes(ctx -> {
                            ServerPlayerEntity player = ctx.getSource().getPlayer();

                            File warpFolder = configs.warpFolder;
                            if (!warpFolder.exists())
                                warpFolder.mkdir();

                            String warpName = StringArgumentType.getString(ctx, messages.get(9, "null"));
                            File warpFile = new File(warpFolder, warpName + ".yml");
                            if (warpFile.exists()) {
                                player.sendMessage(new LiteralText(messages.get(10, player.getGameProfile().getName()).replace("%name%", warpName)), false);
                            } else {
                                try {
                                    String world = WorldDescription.getDescription(player.getServerWorld(), Objects.requireNonNull(player.getServer()));
                                    if (world.equals("shit")) {
                                        ctx.getSource().getPlayer().sendMessage(new LiteralText(messages.get(11, player.getGameProfile().getName())), false);
                                    } else {
                                        BufferedWriter buffer = Files.newWriter(warpFile, StandardCharsets.UTF_8);

                                        ArrayList<String> lines = new ArrayList<>();
                                        lines.add(world);
                                        lines.add(String.valueOf(player.getX()));
                                        lines.add(String.valueOf(player.getY()));
                                        lines.add(String.valueOf(player.getZ()));
                                        lines.add(String.valueOf(player.getHeadYaw()));
                                        lines.add(String.valueOf(player.getPitch(1)));
                                        for (String line : lines) {
                                            buffer.write(line);
                                            if (lines.indexOf(line) != lines.size())
                                                buffer.newLine();
                                        }
                                        buffer.close();
                                        ctx.getSource().getPlayer().sendMessage(new LiteralText(messages.get(12, player.getGameProfile().getName()).replace("%name%", warpName)), false);
                                        ctx.getSource().getPlayer().sendMessage(new LiteralText(messages.get(2, player.getGameProfile().getName()).replace("%to%", warpName)), true);
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
