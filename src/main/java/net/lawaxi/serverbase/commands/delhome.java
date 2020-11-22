package net.lawaxi.serverbase.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.lawaxi.serverbase.utils.config.configs;
import net.lawaxi.serverbase.utils.config.messages;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;

import java.io.File;

public class delhome {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("delhome")
                .then(CommandManager.argument(messages.get(48, "null"), StringArgumentType.string())
                        .executes(ctx -> {

                            ServerPlayerEntity player = ctx.getSource().getPlayer();
                            String homename = StringArgumentType.getString(ctx, messages.get(48, "null"));
                            File homefile = new File(configs.homeFolder + File.separator + player.getEntityName() + File.separator + homename + ".yml");
                            if (homefile.exists()) {
                                if (homefile.delete()) {
                                    ctx.getSource().getPlayer().sendMessage(new LiteralText(messages.get(49, player.getGameProfile().getName()).replace("%name%", homename)), false);
                                } else {
                                    ctx.getSource().getPlayer().sendMessage(new LiteralText(messages.get(50, player.getGameProfile().getName()).replace("%name%", homename)), false);
                                }
                            } else {
                                ctx.getSource().getPlayer().sendMessage(new LiteralText(messages.get(51, player.getGameProfile().getName()).replace("%name%", homename)), false);
                            }
                            return 1;
                        }))
                .executes(ctx -> {
                    ctx.getSource().getPlayer().sendMessage(new LiteralText(messages.get(52, ctx.getSource().getPlayer().getGameProfile().getName())), false);
                    return 1;
                })
        );
    }
}
