package net.lawaxi.serverbase.commands;

import com.google.common.io.Files;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.command.arguments.EntityArgumentType;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.LiteralText;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import org.apache.logging.log4j.core.config.yaml.YamlConfiguration;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class setwarp {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher)
    {
        dispatcher.register(CommandManager.literal("setwarp")
                        .then(CommandManager.argument("地标名称", StringArgumentType.string())
                                .executes(ctx -> {
                                    ServerPlayerEntity player = ctx.getSource().getPlayer();

                                    File warpfolder = new File("Lawaxi"+File.separator+"warps");
                                    if(!warpfolder.exists())
                                        warpfolder.mkdir();

                                    String warpname =StringArgumentType.getString(ctx,"地标名称");
                                    File warpfile = new File("Lawaxi"+File.separator+"warps"+File.separator+warpname +".yml");
                                    if(warpfile.exists())
                                    {
                                        player.sendMessage(new LiteralText("§c地标§4 "+warpname+" §c已存在，请删除后再写入"));
                                    }
                                    else
                                    {
                                        try{
                                            String world = getWorld(player.world,player.getServer());
                                            if(world.equals("shit"))
                                            {
                                                ctx.getSource().getPlayer().sendMessage(new LiteralText("§c很抱歉，暂不支持除主世界 地狱 末地外的地标设置"));
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
                                                ctx.getSource().getPlayer().sendMessage(new LiteralText("§a地标§2 "+warpname+" §a创建成功"));
                                            }
                                        }
                                        catch (IOException e)
                                        {
                                            System.out.print("setwarp 时出现问题.");
                                        }
                                    }
                                    return 1;
                                }))
                        .executes(ctx -> {
                            ctx.getSource().getPlayer().sendMessage(new LiteralText("§c请输入要创建的地标名称"));
                            return 1;
                        })
        );
    }

    private static String getWorld(World world, MinecraftServer server)
    {
        if(world.equals(server.getWorld(DimensionType.OVERWORLD)))
        {
            return "主世界";
        }
        else if(world.equals(server.getWorld(DimensionType.THE_NETHER)))
        {
            return "地狱";
        }
        else if(world.equals(server.getWorld(DimensionType.THE_END)))
        {
            return "末地";
        }
        else
        {
            return "shit";
        }
    }
}
