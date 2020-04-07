package net.lawaxi.serverbase.commands;
/*
import com.mojang.brigadier.CommandDispatcher;
import net.lawaxi.serverbase.shits.list;
import net.lawaxi.serverbase.shits.locationinfo;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.dimension.DimensionType;

public class back {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher)
    {
        dispatcher.register(CommandManager.literal("back")
                        .executes(ctx -> {
                            ServerPlayerEntity player = ctx.getSource().getPlayer();

                            locationinfo a = list.playerrecord.get(player.getEntityId());
                            if(a!=null)
                            {
                                player.sendMessage(new LiteralText("§a正在传送..."));
                                player.teleport(a.world,a.position.getX(),a.position.getY(),a.position.getZ(),0,0);
                            }
                            else
                            {
                                player.sendMessage(new LiteralText("§c没有关于上一个坐标的记录"));
                            }

                            return 1;
                        })
        );
    }
}*/
