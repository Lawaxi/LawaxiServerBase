package net.lawaxi.serverbase.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.lawaxi.serverbase.utils.tparequest;
import net.lawaxi.serverbase.utils.list;
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
                                who.sendMessage(new LiteralText("§c您没有待拒绝的请求"),false);
                            }
                            else
                            {
                                ServerPlayerEntity me = request.me;
                                ServerPlayerEntity to = who;
                                me.sendMessage(new LiteralText("§c对方拒绝了你的请求"),false);
                                to.sendMessage(new LiteralText("§c已成功拒绝对方的请求"),false);
                            }
                            return 1;
                        })
        );
    }
}
