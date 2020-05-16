package net.lawaxi.serverbase.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.lawaxi.serverbase.utils.config.configs;
import net.lawaxi.serverbase.utils.config.messages;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.LiteralText;
import net.minecraft.world.dimension.DimensionType;

import java.io.*;

public class warp{
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) throws IOException
    {
        dispatcher.register(CommandManager.literal("warp")
                        .then(CommandManager.argument(messages.m.get(1), StringArgumentType.string())
                                .executes(ctx -> {

                                    String warpname=StringArgumentType.getString(ctx,messages.m.get(2));
                                    File warpfile = new File(configs.warpfolder+File.separator+ warpname+".yml");
                                    if(warpfile.exists())
                                    {
                                        try{
                                            FileInputStream fos = new FileInputStream(warpfile);

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

                                                            ServerPlayerEntity player =ctx.getSource().getPlayer();

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
                                                            player.sendMessage(new LiteralText(messages.m.get(0)),false);
                                                            player.sendMessage(new LiteralText(messages.m.get(1).replace("%to%",warpname)),true);
                                                            player.teleport(world,x,y,z,0,0);

                                                            return 1;

                                                        }
                                                    }
                                                }
                                            }

                                        }
                                        catch (IOException e)
                                        {
                                        }
                                    }

                                    ctx.getSource().getPlayer().sendMessage(new LiteralText(messages.m.get(3).replace("%to%",warpname)),false);
                                    return 1;
                                } ))
                        .executes(ctx -> {
                            warps.getWarps(ctx.getSource().getPlayer());
                            return 1;
                        })
        );
    }
}
