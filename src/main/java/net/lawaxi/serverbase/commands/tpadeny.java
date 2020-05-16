package net.lawaxi.serverbase.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.lawaxi.serverbase.utils.config.messages;
import net.lawaxi.serverbase.utils.tparequest;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;

public class tpadeny {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher)
    {
        dispatcher.register(CommandManager.literal("tpadeny")
                        .executes(ctx -> {
                            ServerPlayerEntity who = ctx.getSource().getPlayer();
                            tparequest request = tparequest.search(who);
                            if(request==null)
                            {
                                who.sendMessage(new LiteralText(messages.m.get(35)),false);
                            }
                            else
                            {
                                ServerPlayerEntity me = request.me;
                                ServerPlayerEntity to = who;
                                me.sendMessage(new LiteralText(messages.m.get(36)),false);
                                to.sendMessage(new LiteralText(messages.m.get(37)),false);
                            }
                            return 1;
                        })
        );
    }
}
