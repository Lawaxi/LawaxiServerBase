package net.lawaxi.serverbase.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.lawaxi.serverbase.utils.config.configs;
import net.lawaxi.serverbase.utils.config.messages;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

public class homes {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("homes")
                .executes(ctx -> {
                    ServerPlayerEntity player = ctx.getSource().getPlayer();
                    getHome(player);
                    return 1;
                })
        );
    }

    public static int getHome(ServerPlayerEntity player) {
        ArrayList<String> homes = getHomeNames(player);
        if (homes.isEmpty()) {
            player.sendMessage(new LiteralText(messages.get(17, player.getGameProfile().getName())), false);
        } else {
            StringBuilder sb = new StringBuilder(messages.get(16, player.getGameProfile().getName()).replace("%player%", player.getEntityName()));
            for (String s : Objects.requireNonNull(getHomeNames(player))) {
                sb.append(warps.sortName(s, player.getGameProfile().getName()));
            }
            player.sendMessage(new LiteralText(sb.toString()), false);
        }
        return 0;
    }

    public static ArrayList<String> getHomeNames(ServerPlayerEntity player) {
        File homeFolder = new File(configs.homeFolder, player.getEntityName());
        if (homeFolder.exists() && homeFolder.list().length != 0) {
            ArrayList<String> homes = new ArrayList<>();
            for (String home : homeFolder.list())
                homes.add(home.replaceAll("\\.yml$", ""));
            return homes;
        }
        return new ArrayList<>();
    }
}
