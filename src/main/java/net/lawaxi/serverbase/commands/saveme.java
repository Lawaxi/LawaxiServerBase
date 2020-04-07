package net.lawaxi.serverbase.commands;

import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.lawaxi.serverbase.shits.list;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.ServerConfigList;
import net.minecraft.server.WhitelistEntry;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.core.config.ConfigurationSource;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class saveme {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher)
    {
        dispatcher.register(CommandManager.literal("saveme")
                        .executes(ctx -> {
                            File worldsavefolder = new File("Lawaxi"+File.separator+"datasaves");
                            if(!worldsavefolder.exists())
                                worldsavefolder.mkdir();

                            try{

                                ServerPlayerEntity player = ctx.getSource().getPlayer();
                                String uuid = player.getUuid().toString();

                                if(player.getServer().getPlayerManager().getWhitelist().isAllowed(player.getGameProfile()))
                                {
                                    File world = new File("world" + File.separator + "playerdata" + File.separator + uuid + ".dat");
                                    File worldsave = new File("Lawaxi"+File.separator+"datasaves"+File.separator + uuid + ".dat");

                                    player.networkHandler.disconnect(new LiteralText("§a正在备份，请稍后连入"));
                                    FileUtils.copyFile(world,worldsave);
                                }
                                else
                                {
                                    player.sendMessage(new LiteralText("§c很抱歉，备份功能需要白名单，请联系管理员获取"));
                                }

                            }
                            catch (IOException e)
                            {
                                System.out.print("saveme 时出现问题.");
                            }

                            return 1;
                            })
        );
    }
}
