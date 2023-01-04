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
import net.minecraft.text.Text;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class sethome {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("sethome")
                .then(CommandManager.argument(messages.get(18, "null"), StringArgumentType.string())
                        .executes(ctx -> {

                            if (!configs.homeFolder.exists())
                                configs.homeFolder.mkdir();

                            ServerPlayerEntity player = ctx.getSource().getPlayer();
                            File homefolder = new File(configs.homeFolder, player.getEntityName());
                            if (!homefolder.exists())
                                homefolder.mkdir();

                            String homename = StringArgumentType.getString(ctx, messages.get(18, "null"));
                            File homefile = new File(configs.homeFolder, player.getEntityName() + File.separator + homename + ".yml");
                            if (homefile.exists()) {
                                player.sendMessage(Text.literal(messages.get(19, player.getGameProfile().getName()).replace("%name%", homename)), false);
                            } else {
                                try {
                                    String world = WorldDescription.getDescription(player.getWorld(), Objects.requireNonNull(player.getServer()));
                                    if (world.equals("shit")) {
                                        ctx.getSource().getPlayer().sendMessage(Text.literal(messages.get(20, player.getGameProfile().getName())), false);
                                    } else {
                                        BufferedWriter buffer = Files.newWriter(homefile, StandardCharsets.UTF_8);

                                        buffer.write(world);
                                        buffer.newLine();
                                        buffer.write(String.valueOf(player.getX()));
                                        buffer.newLine();
                                        buffer.write(String.valueOf(player.getY()));
                                        buffer.newLine();
                                        buffer.write(String.valueOf(player.getZ()));
                                        buffer.newLine();
                                        buffer.write(String.valueOf(player.getHeadYaw()));
                                        buffer.newLine();
                                        buffer.write(String.valueOf(player.getPitch(1)));

                                        buffer.close();
                                        ctx.getSource().getPlayer().sendMessage(Text.literal(messages.get(21, player.getGameProfile().getName()).replace("%name%", homename)), false);
                                        ctx.getSource().getPlayer().sendMessage(Text.literal(messages.get(2, player.getGameProfile().getName()).replace("%to%", homename)), true);
                                    }
                                } catch (IOException ignored) {
                                }
                            }
                            return 1;
                        }))
                .executes(ctx -> {
                    ctx.getSource().getPlayer().sendMessage(Text.literal(messages.get(22, ctx.getSource().getPlayer().getGameProfile().getName())), false);
                    return 1;
                })
        );
    }
}
