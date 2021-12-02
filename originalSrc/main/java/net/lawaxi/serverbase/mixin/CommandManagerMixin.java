package net.lawaxi.serverbase.mixin;


import com.mojang.brigadier.CommandDispatcher;
import net.lawaxi.serverbase.commands.*;
import net.lawaxi.serverbase.utils.config.configs;
import net.lawaxi.serverbase.utils.config.messages;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.File;

@Mixin(CommandManager.class)
public class CommandManagerMixin {

    @Inject(method = "<init>", at = @At("RETURN"))
    private void onRegistry(CallbackInfo info) {

        System.out.println("欢迎使用ServerBase！");
        System.out.println("https://www.mcbbs.net/forum.php?mod=viewthread&tid=1031677");
        System.out.println("https://github.com/KaiXuan233/LawaxiServerBase");

        File configfolder = new File("Lawaxi");
        if (!configfolder.exists())
            configfolder.mkdir();

        new configs();
        new messages();

        CommandDispatcher<ServerCommandSource> dispatcher = ((CommandManager) (Object) this).getDispatcher();

        tpa.register(dispatcher);
        tpahere.register(dispatcher);
        tpaccept.register(dispatcher);
        tpadeny.register(dispatcher);

        delwarp.register(dispatcher);
        delhome.register(dispatcher);
        spawn.register(dispatcher);

        warps.register(dispatcher);
        homes.register(dispatcher);
        hat.register(dispatcher);
        ops.register(dispatcher);
        bans.register(dispatcher);
        here.register(dispatcher);
        lang.register(dispatcher);

        warp.register(dispatcher);
        setwarp.register(dispatcher);
        home.register(dispatcher);
        sethome.register(dispatcher);

        if (configs.allowBackup) {
            saveme.register(dispatcher);
            loadme.register(dispatcher);
        }
        if (configs.allowFly) {
            fly.register(dispatcher);
        }
        if (configs.allowBack) {
            back.register(dispatcher);
        }
        if (configs.allowSeed) {
            seed.register(dispatcher);
        }
        if (configs.freecam) {
            c.register(dispatcher);
            s.register(dispatcher);
        }
    }
}
