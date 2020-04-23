package net.lawaxi.serverbase.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.lawaxi.serverbase.utils.list;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.LiteralText;
import net.minecraft.world.dimension.DimensionType;

import java.io.*;

public class home {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) throws IOException
    {
        dispatcher.register(CommandManager.literal("home")
                        .then(CommandManager.argument("家的名称", StringArgumentType.string())
                                .executes(ctx -> {
                                    ServerPlayerEntity player =ctx.getSource().getPlayer();
                                    String homename =StringArgumentType.getString(ctx,"家的名称");
                                    
                                    File homefile = new File(list.homefolder+File.separator+player.getEntityName() +File.separator+homename+".yml");
                                    if(homefile.exists())
                                    {
                                        try{
                                            FileInputStream fos = new FileInputStream(homefile);

                                            BufferedReader buffer = new BufferedReader(new InputStreamReader(fos, "UTF-8"));
                                            String worldx = buffer.readLine();
                                            if(worldx!=null){
                                                String sx =buffer.readLine();
                                                if(sx!=null)
                                                {
                                                    String sy =buffer.readLine();
                                                    if(sy!=null)
                                                    {
                                                        String sz =buffer.readLine();
                                                        if(sz!=null)
                                                        {
                                                            ServerWorld world;
                                                            if(worldx.equals("末地"))
                                                            {
                                                                world=player.getServer().getWorld(DimensionType.THE_END);
                                                            }
                                                            else if(worldx.equals("地狱"))
                                                            {
                                                                world=player.getServer().getWorld(DimensionType.THE_NETHER);
                                                            }
                                                            else
                                                            {
                                                                world=player.getServer().getWorld(DimensionType.OVERWORLD);
                                                            }

                                                            double x = Double.valueOf(sx);
                                                            double y = Double.valueOf(sy);
                                                            double z = Double.valueOf(sz);

                                                            //locationinfo.recordlocation(player);
                                                            player.sendMessage(new LiteralText("§a正在传送..."),false);
                                                            player.sendMessage(new LiteralText("§a"+homename),true);
                                                            player.teleport(world,x,y,z,0,0);

                                                            return 1;
                                                        }
                                                    }
                                                }
                                            }

                                        }
                                        catch (IOException e)
                                        {
                                            System.out.print("home 时出现问题.");
                                        }

                                    }

                                    ctx.getSource().getPlayer().sendMessage(new LiteralText("§c家§4 "+homename+" §c不存在或已损坏"),false);
                                    return 1;
                                } ))
                        .executes(ctx -> {
                            homes.getHome(ctx.getSource().getPlayer());
                            return 1;
                        })
        );
    }
}
