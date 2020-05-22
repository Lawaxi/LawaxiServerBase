package net.lawaxi.serverbase.utils;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;

public class WorldDiscription {

    public static String getDiscription(World world, MinecraftServer server)
    {
        if(world.equals(server.getWorld(DimensionType.OVERWORLD_REGISTRY_KEY)))
            return "主世界";
        else if(world.equals(server.getWorld(DimensionType.THE_END_REGISTRY_KEY)))
            return "末地";
        else if(world.equals(server.getWorld(DimensionType.THE_NETHER_REGISTRY_KEY)))
            return "地狱";
        else
            return "shit";
    }

    public static ServerWorld getWorld(String discrition, MinecraftServer server){

        if(discrition.equals("末地"))
            return server.getWorld(DimensionType.THE_END_REGISTRY_KEY);
        else if(discrition.equals("地狱"))
            return server.getWorld(DimensionType.THE_NETHER_REGISTRY_KEY);
        else
            return server.getWorld(DimensionType.OVERWORLD_REGISTRY_KEY);

    }
}
