package net.lawaxi.serverbase.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.lawaxi.serverbase.utils.WorldDescription;
import net.lawaxi.serverbase.utils.config.configs;
import net.lawaxi.serverbase.utils.config.messages;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.LiteralText;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class home {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("home")
                .then(CommandManager.argument(messages.get(14, "null"), StringArgumentType.string())
                        .executes(ctx -> {
                            ServerPlayerEntity player = ctx.getSource().getPlayer();
                            String homename = StringArgumentType.getString(ctx, messages.get(14, "null"));

                            File homefile = new File(configs.homeFolder, player.getEntityName() + File.separator + homename + ".yml");
                            if (homefile.exists()) {
                                try {
                                    FileInputStream fos = new FileInputStream(homefile);

                                    BufferedReader buffer = new BufferedReader(new InputStreamReader(fos, StandardCharsets.UTF_8));
                                    String worldx = buffer.readLine();
                                    if (worldx != null) {
                                        String sx = buffer.readLine();
                                        if (sx != null) {
                                            String sy = buffer.readLine();
                                            if (sy != null) {
                                                String sz = buffer.readLine();
                                                if (sz != null) {
                                                    ServerWorld world = WorldDescription.getWorld(worldx, player.getServer());
                                                    double x = Double.parseDouble(sx);
                                                    double y = Double.parseDouble(sy);
                                                    double z = Double.parseDouble(sz);

                                                    //locationinfo.recordlocation(player);
                                                    player.sendMessage(new LiteralText(messages.get(1, player.getGameProfile().getName())), false);
                                                    player.sendMessage(new LiteralText(messages.get(2, player.getGameProfile().getName()).replace("%to%", homename)), true);
                                                    player.teleport(world, x, y, z, 0, 0);

                                                    return 1;
                                                }
                                            }
                                        }
                                    }

                                } catch (IOException ignored) {
                                }

                            }

                            ctx.getSource().getPlayer().sendMessage(new LiteralText(messages.get(15, player.getGameProfile().getName()).replace("%to%", homename)), false);
                            return 1;
                        }))
                .executes(ctx -> {
                    homes.getHome(ctx.getSource().getPlayer());
                    return 1;
                })
        );
    }
}
