package net.lawaxi.serverbase.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.LiteralText;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.dimension.DimensionType;

public class spawn {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher)
    {
        dispatcher.register(CommandManager.literal("spawn")
                        .executes(ctx -> {

                            ServerPlayerEntity player = ctx.getSource().getPlayer();
                            ServerWorld mainworld = player.getServer().getWorld(DimensionType.OVERWORLD);
                            BlockPos spawnpos = mainworld.getSpawnPos();

                            player.sendMessage(new LiteralText("§a正在传送..."));
                            player.teleport(mainworld,spawnpos.getX(),spawnpos.getY(),spawnpos.getZ(),0,0);

                            return 1;
                        })
        );
    }
}
