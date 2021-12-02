package net.lawaxi.serverbase.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.lawaxi.serverbase.utils.config.messages;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;

public class ops {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("ops")
                .executes(ctx -> {

                    ServerPlayerEntity player = ctx.getSource().getPlayer();

                    String out = "";
                    for (String name : player.getServer().getPlayerManager().getOpList().getNames()) {
                        out += name + ",";
                    }

                    if (!out.equals(""))
                        player.sendMessage(new LiteralText(messages.get(39, player.getGameProfile().getName())
                                + out.substring(0, out.length() - 1)), false);
                    else
                        player.sendMessage(new LiteralText(messages.get(40, player.getGameProfile().getName())), false);
                    return 1;
                })
        );
    }
}
