package net.lawaxi.serverbase.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.lawaxi.serverbase.utils.TpaRequest;
import net.lawaxi.serverbase.utils.config.messages;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.LiteralText;
import net.minecraft.world.GameMode;

public class tpaccept {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("tpaccept")
                .executes(ctx -> {
                    ServerPlayerEntity who = ctx.getSource().getPlayer();
                    TpaRequest request = TpaRequest.search(who);
                    if (request == null) {
                        who.sendMessage(new LiteralText(messages.get(35, who.getGameProfile().getName())), false);
                    } else {
                        if (request.to.interactionManager.getGameMode() == GameMode.SPECTATOR) {
                            who.sendMessage(new LiteralText(messages.get(80, who.getGameProfile().getName())), false);
                            request.to.sendMessage(new LiteralText(messages.get(80, request.me.getGameProfile().getName())), false);
                        } else {

                            ServerPlayerEntity me, to;
                            if (!request.mode) {
                                me = request.me;
                                to = who;
                            } else {
                                to = request.me;
                                me = who;
                            }

                            me.sendMessage(new LiteralText(messages.get(1, me.getGameProfile().getName())), false);
                            to.sendMessage(new LiteralText(messages.get(1, to.getGameProfile().getName())), false);
                            me.sendMessage(new LiteralText(messages.get(2, me.getGameProfile().getName()).replace("%to%", to.getEntityName())), true);

                            me.teleport((ServerWorld) to.world, to.getX(), to.getY(), to.getZ(), to.getYaw(), to.getPitch());
                        }
                    }
                    return 1;
                })
        );
    }
}
