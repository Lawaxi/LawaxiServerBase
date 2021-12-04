package net.lawaxi.serverbase.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.lawaxi.serverbase.utils.List;
import net.lawaxi.serverbase.utils.TpaRequest;
import net.lawaxi.serverbase.utils.config.messages;
import net.minecraft.command.CommandSource;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;

public class tpahere {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("tpahere")
                .then(CommandManager.argument(messages.get(24, "null"), EntityArgumentType.player())
                        .suggests((ctx, suggestionsBuilder) -> CommandSource.suggestMatching(tpa.getPlayersExceptSelf(ctx.getSource().getPlayer()), suggestionsBuilder))
                        .executes(ctx -> {
                            ServerPlayerEntity to = EntityArgumentType.getPlayer(ctx, messages.get(24, "null"));
                            ServerPlayerEntity me = ctx.getSource().getPlayer();
                            if (!TpaRequest.hasrequest(me)) {
                                if (to.equals(me)) {
                                    to.sendMessage(new LiteralText(messages.get(33, to.getGameProfile().getName())), false);
                                } else {
                                    TpaRequest newrequest = new TpaRequest();
                                    newrequest.to = to;
                                    newrequest.me = me;
                                    newrequest.mode = true;

                                    if (List.tparequests.add(newrequest)) {
                                        me.sendMessage(new LiteralText(messages.get(26, me.getGameProfile().getName())), false);

                                        to.sendMessage(new LiteralText(messages.get(34, to.getGameProfile().getName()).replace("%from%", me.getEntityName())), false);
                                        to.sendMessage(new LiteralText(messages.get(28, to.getGameProfile().getName())), false);
                                        to.sendMessage(new LiteralText(messages.get(29, to.getGameProfile().getName())), false);
                                    } else {
                                        me.sendMessage(new LiteralText(messages.get(30, me.getGameProfile().getName())), false);
                                    }
                                }
                            } else {
                                me.sendMessage(new LiteralText(messages.get(31, me.getGameProfile().getName())), false);
                            }
                            //-1 is failure, 0 is a pass and 1 is success.
                            return 1;
                        }))
                // execute if there are no arguments.
                .executes(ctx -> {
                    ctx.getSource().getPlayer().sendMessage(new LiteralText(messages.get(32, ctx.getSource().getPlayer().getGameProfile().getName())), false);
                    return 1;
                })
        );
    }
}
