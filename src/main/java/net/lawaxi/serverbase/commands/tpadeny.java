package net.lawaxi.serverbase.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.lawaxi.serverbase.shits.tparequest;
import net.lawaxi.serverbase.shits.list;
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
                            tparequest request = search(who);
                            if(request==null)
                            {
                                who.sendMessage(new LiteralText("§c您没有待拒绝的请求"));
                            }
                            else
                            {
                                ServerPlayerEntity me = request.me;
                                ServerPlayerEntity to = who;
                                me.sendMessage(new LiteralText("§c对方拒绝了你的请求"));
                                to.sendMessage(new LiteralText("§c已成功拒绝对方的请求"));
                            }
                            return 1;
                        })
        );
    }

    private static tparequest search(ServerPlayerEntity who)
    {
        System.out.print(list.tparequests.size());
        if(list.tparequests.size()==0)
            return null;

        int i;
        tparequest now;
        for(i=list.tparequests.size()-1;i>=0;i--)
        {
            now=list.tparequests.get(i);

            if(now.to.equals(who))
            {
                list.tparequests.get(i).exist=false;
                list.tparequests.remove(i);
                return now;
            }
        }
        return null;
    }
}
