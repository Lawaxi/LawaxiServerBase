package net.lawaxi.serverbase.mixin;

import net.minecraft.server.ServerConfigList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerConfigList.class)
public abstract class ServerConfigListMixin {

    @Inject(at = @At("RETURN"), method = "add")
    private void onAdd(CallbackInfo info) {

/*        if (((Object) this) instanceof BannedPlayerList) {
            Checking.check((BannedPlayerList) (Object) this);
        }*/
    }
}
