package net.lawaxi.serverbase.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.lawaxi.serverbase.utils.config.messages;
import net.lawaxi.serverbase.utils.list;
import net.lawaxi.serverbase.utils.tparequest;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;

public class tpa {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher)
    {
        dispatcher.register(CommandManager.literal("tpa")
                        .then(CommandManager.argument(messages.get(24,"null"), EntityArgumentType.player())
                                .executes(ctx -> {
                                    ServerPlayerEntity to = EntityArgumentType.getPlayer(ctx,messages.get(24,"null"));
                                    ServerPlayerEntity me = ctx.getSource().getPlayer();
                                    if(!tparequest.hasrequest(me))
                                    {
                                        if(to.equals(me)) {
                                            me.sendMessage(new LiteralText(messages.get(25,to.getGameProfile().getName())),false);
                                        }
                                        else
                                        {
                                            tparequest newrequest = new tparequest();
                                            newrequest.to=to;
                                            newrequest.me=me;
                                            newrequest.mode=false;

                                            if(list.tparequests.add(newrequest))
                                            {
                                                me.sendMessage(new LiteralText(messages.get(26,me.getGameProfile().getName())),false);

                                                to.sendMessage(new LiteralText(messages.get(27,to.getGameProfile().getName()).replace("%from%",me.getEntityName())),false);
                                                to.sendMessage(new LiteralText(messages.get(28,to.getGameProfile().getName())),false);
                                                to.sendMessage(new LiteralText(messages.get(29,to.getGameProfile().getName())),false);
                                            }
                                            else
                                            {
                                                me.sendMessage(new LiteralText(messages.get(30,me.getGameProfile().getName())),false);
                                            }
                                        }
                                    }
                                    else
                                    {
                                        me.sendMessage(new LiteralText(messages.get(31,me.getGameProfile().getName())),false);
                                    }
                                    //-1 is failure, 0 is a pass and 1 is success.
                                    return 1;
                                }))
                        // execute if there are no arguments.
                        .executes(ctx -> {
                            ctx.getSource().getPlayer().sendMessage(new LiteralText(messages.get(32,ctx.getSource().getPlayer().getGameProfile().getName())),false);
                            return 1;
                        })
        );
    }
}
