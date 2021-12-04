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

import static net.lawaxi.serverbase.commands.homes.getHomeNames;

public class home {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("home")
                .then(CommandManager.argument(messages.get(14, "null"), StringArgumentType.string())
                        .suggests((ctx, suggestionsBuilder) -> CommandSource.suggestMatching(getHomeNames(ctx.getSource().getPlayer()), suggestionsBuilder))
                        .executes(ctx -> {
                            ServerPlayerEntity player = ctx.getSource().getPlayer();
                            String homeName = StringArgumentType.getString(ctx, messages.get(14, "null"));
                            File homeFile = new File(configs.homeFolder, player.getEntityName() + File.separator + homeName + ".yml");
                            LocationInfo location;
                            try {
                                location = new LocationInfo(homeFile, player);
                                player.sendMessage(new LiteralText(messages.get(1, player.getGameProfile().getName())), false);
                                player.sendMessage(new LiteralText(messages.get(2, player.getGameProfile().getName()).replace("%to%", homeName)), true);
                                location.teleport(player);
                                return 1;
                            } catch (BrokenPositionException e) {
                                player.sendMessage(new LiteralText(messages.get(15, player.getGameProfile().getName()).replace("%to%", homeName)), false);
                                return -1;
                            }
                        }))
                .executes(ctx -> {
                    homes.getHome(ctx.getSource().getPlayer());
                    return 1;
                })
        );
    }
}
