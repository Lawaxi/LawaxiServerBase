package net.lawaxi.serverbase.commands;

import com.google.common.io.Files;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.lawaxi.serverbase.shits.list;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class sethome {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher)
    {
        dispatcher.register(CommandManager.literal("sethome")
                        .then(CommandManager.argument("家的名称", StringArgumentType.string())
                                .executes(ctx -> {

                                    File homefolder = new File(list.homefolder);
                                    if(!homefolder.exists())
                                        homefolder.mkdir();

                                    ServerPlayerEntity player =ctx.getSource().getPlayer();
                                    homefolder = new File(list.homefolder+File.separator+player.getEntityName());
                                    if(!homefolder.exists())
                                        homefolder.mkdir();

                                    String homename=StringArgumentType.getString(ctx,"家的名称");
                                    File homefile = new File(list.homefolder+File.separator+player.getEntityName() +File.separator+homename+".yml");
                                    if(homefile.exists())
                                    {
                                        player.sendMessage(new LiteralText("§c家§4 "+homename+" §c已存在，请删除后再写入"));
                                    }
                                    else
                                    {
                                        try{
                                            String world = getWorld(player.world,player.getServer());
                                            if(world.equals("shit"))
                                            {
                                                ctx.getSource().getPlayer().sendMessage(new LiteralText("§c很抱歉，暂不支持除主世界 地狱 末地外的家的设置"));
                                            }
                                            else
                                            {
                                                BufferedWriter buffer = Files.newWriter(homefile, StandardCharsets.UTF_8);

                                                buffer.write(world);
                                                buffer.newLine();
                                                buffer.write(String.valueOf(player.getX()));
                                                buffer.newLine();
                                                buffer.write(String.valueOf(player.getY()));
                                                buffer.newLine();
                                                buffer.write(String.valueOf(player.getZ()));

                                                buffer.close();
                                                ctx.getSource().getPlayer().sendMessage(new LiteralText("§a家§2 "+homename+" §a创建成功"));
                                            }
                                        }
                                        catch (IOException e)
                                        {
                                            System.out.print("sethome 时出现问题.");
                                        }
                                    }
                                    return 1;
                                }))
                        .executes(ctx -> {
                            ctx.getSource().getPlayer().sendMessage(new LiteralText("§c请输入要创建的家的名称"));
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
