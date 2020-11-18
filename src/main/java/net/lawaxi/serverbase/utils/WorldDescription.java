package net.lawaxi.serverbase.utils;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;

public class WorldDescription {

    public static String getDiscription(ServerWorld world, MinecraftServer server) {
        if (world.equals(server.getWorld(World.OVERWORLD)))
            return "主世界";
        else if (world.equals(server.getWorld(World.END)))
            return "末地";
        else if (world.equals(server.getWorld(World.NETHER)))
            return "地狱";
        else
            return "shit";
    }

    public static ServerWorld getWorld(String discrition, MinecraftServer server) {

        if (discrition.equals("末地"))
            return server.getWorld(World.END);
        else if (discrition.equals("地狱"))
            return server.getWorld(World.NETHER);
        else
            return server.getWorld(World.OVERWORLD);

    }
}
