package net.lawaxi.serverbase.commands;

import com.google.common.io.Files;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.lawaxi.serverbase.utils.config.configs;
import net.lawaxi.serverbase.utils.config.messages;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class setwarp {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher)
    {
        dispatcher.register(CommandManager.literal("setwarp")
                        .then(CommandManager.argument(messages.m.get(8), StringArgumentType.string())
                                .executes(ctx -> {
                                    ServerPlayerEntity player = ctx.getSource().getPlayer();

                                    File warpfolder = new File(configs.warpfolder);
                                    if(!warpfolder.exists())
                                        warpfolder.mkdir();

                                    String warpname =StringArgumentType.getString(ctx,messages.m.get(8));
                                    File warpfile = new File(configs.warpfolder+File.separator+warpname +".yml");
                                    if(warpfile.exists())
                                    {
                                        player.sendMessage(new LiteralText(messages.m.get(9).replace("%name%",warpname)),false);
                                    }
                                    else
                                    {
                                        try{
                                            String world = getWorld(player.world,player.getServer());
                                            if(world.equals("shit"))
                                            {
                                                ctx.getSource().getPlayer().sendMessage(new LiteralText(messages.m.get(10)),false);
                                            }
                                            else
                                            {
                                                BufferedWriter buffer = Files.newWriter(warpfile, StandardCharsets.UTF_8);

                                                buffer.write(world);
                                                buffer.newLine();
                                                buffer.write(String.valueOf(player.getX()));
                                                buffer.newLine();
                                                buffer.write(String.valueOf(player.getY()));
                                                buffer.newLine();
                                                buffer.write(String.valueOf(player.getZ()));

                                                buffer.close();
                                                ctx.getSource().getPlayer().sendMessage(new LiteralText(messages.m.get(11).replace("%name%",warpname)),false);
                                                ctx.getSource().getPlayer().sendMessage(new LiteralText(messages.m.get(1).replace("%to%",warpname)),true);
                                            }
                                        }
                                        catch (IOException e)
                                        {
                                        }
                                    }
                                    return 1;
                                }))
                        .executes(ctx -> {
                            ctx.getSource().getPlayer().sendMessage(new LiteralText(messages.m.get(12)),false);
                            return 1;
                        })
        );
    }

    public static String getWorld(World world, MinecraftServer server)
    {
        if(world.equals(server.getWorld(DimensionType.OVERWORLD)))
        {
            return "主世界";
        }
        else if(world.equals(server.getWorld(DimensionType.THE_END)))
        {
            return "末地";
        }
        else if(world.equals(server.getWorld(DimensionType.THE_NETHER)))
        {
            return "地狱";
        }
        else
        {
            return "shit";
        }
    }
}
