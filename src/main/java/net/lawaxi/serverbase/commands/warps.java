package net.lawaxi.serverbase.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.lawaxi.serverbase.utils.config.configs;
import net.lawaxi.serverbase.utils.config.messages;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;

public class warps {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("warps")
                .executes(ctx -> {

                    ServerPlayerEntity player = ctx.getSource().getPlayer();
                    getWarps(player);
                    return 1;
                })
        );
    }

    public static String sortName(String name, String playername) {
        //去掉文件名结尾的".yml"
        return messages.get(6, playername).replace("%name%", name.substring(0, name.length() - 4)) + messages.get(7, playername);
    }

    public static int getWarps(ServerPlayerEntity player) {
        if (configs.warpFolder.exists()) {
            String[] filelist = configs.warpFolder.list();
            if (filelist.length != 0) {
                String filelist2 = messages.get(5, player.getGameProfile().getName());
                for (String name : filelist) {
                    filelist2 += sortName(name, player.getGameProfile().getName());
                }

                player.sendMessage(new LiteralText(filelist2.substring(0, filelist2.length() - messages.get(7, player.getGameProfile().getName()).length())), false);

                return 0;
            }
        }
        player.sendMessage(new LiteralText(messages.get(8, player.getGameProfile().getName())), false);
        return 0;
    }

}
