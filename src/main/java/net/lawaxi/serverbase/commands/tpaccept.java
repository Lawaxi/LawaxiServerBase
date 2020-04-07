package net.lawaxi.serverbase.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.lawaxi.serverbase.shits.tparequest;
import net.lawaxi.serverbase.shits.list;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.LiteralText;

public class tpaccept {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher)
    {
        dispatcher.register(CommandManager.literal("tpaccept")
                        .executes(ctx -> {
                            ServerPlayerEntity who = ctx.getSource().getPlayer();
                            tparequest request = search(who);
                            if(request==null)
                            {
                                who.sendMessage(new LiteralText("§c您没有待接受的请求"));
                            }
                            else
                            {
                                ServerPlayerEntity me,to;
                                if(!request.mode)
                                {
                                    me = request.me;
                                    to = who;
                                }
                                else
                                {
                                    to = request.me;
                                    me = who;
                                }

                                me.sendMessage(new LiteralText("§a正在传送..."));
                                to.sendMessage(new LiteralText("§a正在传送..."));

                                me.teleport((ServerWorld)to.world,to.getX(),to.getY(),to.getZ(),to.yaw,to.pitch);
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
                list.tparequests.remove(i);
                return now;
            }
        }
        return null;
    }
}
