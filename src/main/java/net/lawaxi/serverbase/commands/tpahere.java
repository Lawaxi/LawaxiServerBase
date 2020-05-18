package net.lawaxi.serverbase.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.lawaxi.serverbase.utils.config.messages;
import net.lawaxi.serverbase.utils.list;
import net.lawaxi.serverbase.utils.tparequest;
import net.minecraft.command.arguments.EntityArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;

public class tpahere {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher)
    {
        dispatcher.register(CommandManager.literal("tpahere")
                        .then(CommandManager.argument(messages.m.get(23),EntityArgumentType.player())
                                .executes(ctx -> {
                                    ServerPlayerEntity to = EntityArgumentType.getPlayer(ctx,messages.m.get(23));
                                    ServerPlayerEntity me =ctx.getSource().getPlayer();
                                    if(!tparequest.hasrequest(me))
                                    {
                                        if(to.equals(me))
                                        {
                                            to.sendMessage(new LiteralText(messages.m.get(32)),false);
                                        }
                                        else
                                        {
                                            tparequest newrequest = new tparequest();
                                            newrequest.to=to;
                                            newrequest.me=me;
                                            newrequest.mode=true;

                                            if(list.tparequests.add(newrequest))
                                            {
                                                me.sendMessage(new LiteralText(messages.m.get(25)),false);

                                                to.sendMessage(new LiteralText(messages.m.get(33).replace("%from%",me.getEntityName())),false);
                                                to.sendMessage(new LiteralText(messages.m.get(27)),false);
                                                to.sendMessage(new LiteralText(messages.m.get(28)),false);
                                            }
                                            else
                                            {
                                                me.sendMessage(new LiteralText(messages.m.get(29)),false);
                                            }
                                        }
                                    }
                                    else
                                    {
                                        me.sendMessage(new LiteralText(messages.m.get(30)),false);
                                    }
                                    //-1 is failure, 0 is a pass and 1 is success.
                                    return 1;
                                }))
                        // execute if there are no arguments.
                        .executes(ctx -> {
                            ctx.getSource().getPlayer().sendMessage(new LiteralText(messages.m.get(31)),false);
                            return 1;
                        })
        );
    }
}
