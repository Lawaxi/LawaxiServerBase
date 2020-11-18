package net.lawaxi.serverbase.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.lawaxi.serverbase.utils.Tparequest;
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
                    Tparequest request = Tparequest.search(who);
                    if (request == null) {
                        who.sendMessage(new LiteralText(messages.get(35, who.getGameProfile().getName())), false);
                    } else {
                        if (request.me.interactionManager.getGameMode() == GameMode.SPECTATOR) {
                            who.sendMessage(new LiteralText(messages.get(63, who.getGameProfile().getName())), false);
                            request.me.sendMessage(new LiteralText(messages.get(64, request.me.getGameProfile().getName())), false);
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

                            me.teleport((ServerWorld) to.world, to.getX(), to.getY(), to.getZ(), to.yaw, to.pitch);
                        }
                    }
                    return 1;
                })
        );
    }
}
