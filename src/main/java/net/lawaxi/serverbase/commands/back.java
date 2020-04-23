package net.lawaxi.serverbase.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.lawaxi.serverbase.utils.list;
import net.lawaxi.serverbase.utils.locationinfo;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;

public class back {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher)
    {
        dispatcher.register(CommandManager.literal("back")
                        .executes(ctx -> {
                            ServerPlayerEntity player = ctx.getSource().getPlayer();

                            locationinfo a = list.lastlocation.get(player.getGameProfile());
                            player.sendMessage(new LiteralText("§a正在传送..."),false);
                            player.teleport(a.world,a.position.getX(),a.position.getY(),a.position.getZ(),0,0);

                            return 1;
                        })
        );
    }
}
