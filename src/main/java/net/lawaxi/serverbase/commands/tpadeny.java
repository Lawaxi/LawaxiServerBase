package net.lawaxi.serverbase.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.lawaxi.serverbase.utils.TpaRequest;
import net.lawaxi.serverbase.utils.config.messages;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;

public class tpadeny {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("tpadeny")
                .executes(ctx -> {
                    ServerPlayerEntity who = ctx.getSource().getPlayer();
                    TpaRequest request = TpaRequest.search(who);
                    if (request == null) {
                        who.sendMessage(new LiteralText(messages.get(36, who.getGameProfile().getName())), false);
                    } else {
                        ServerPlayerEntity me = request.me;
                        me.sendMessage(new LiteralText(messages.get(37, me.getGameProfile().getName())), false);
                        who.sendMessage(new LiteralText(messages.get(38, who.getGameProfile().getName())), false);
                    }
                    return 1;
                })
        );
    }
}
