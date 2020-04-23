package net.lawaxi.serverbase.mixin;


import com.mojang.brigadier.CommandDispatcher;
import net.lawaxi.serverbase.commands.*;
import net.lawaxi.serverbase.utils.list;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.File;
import java.io.IOException;

@Mixin(CommandManager.class)
public class CommandManagerMixin {

    @Shadow
    @Final
    private final CommandDispatcher<ServerCommandSource> dispatcher = new CommandDispatcher();

    @Inject(method = "<init>", at = @At("RETURN"))
    private void onRegistry(boolean boolean_1, CallbackInfo ci) {
        System.out.println("我们十分期待来自 LawaxiServerBase 上的命令们可以帮助你的服务器.");

        list.init();

        File configfolder = new File("Lawaxi");
        if(!configfolder.exists())
            configfolder.mkdir();

        //比较简单的一些命令的注册
        tpa.register(dispatcher);
        tpahere.register(dispatcher);
        tpaccept.register(dispatcher);
        tpadeny.register(dispatcher);

        delwarp.register(dispatcher);
        delhome.register(dispatcher);
        spawn.register(dispatcher);
        back.register(dispatcher);

        warps.register(dispatcher);
        homes.register(dispatcher);
        hat.register(dispatcher);
        ops.register(dispatcher);

        //需要读配置的一些命令的注册

        try{
            warp.register(dispatcher);
            setwarp.register(dispatcher);
            home.register(dispatcher);
            sethome.register(dispatcher);

        }
        catch(IOException e){
            System.out.print("LawaxiServerBase 注册命令时出现问题.");
        }


        if(list.allowBackup) {
            saveme.register(dispatcher);
            loadme.register(dispatcher);
        }
    }
}
