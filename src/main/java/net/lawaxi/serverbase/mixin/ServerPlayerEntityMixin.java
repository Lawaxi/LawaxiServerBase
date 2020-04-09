package net.lawaxi.serverbase.mixin;

import com.mojang.authlib.GameProfile;
import net.lawaxi.serverbase.shits.list;
import net.lawaxi.serverbase.shits.locationinfo;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin extends PlayerEntityMixin{

    @Shadow public abstract void sendMessage(Text message);
    @Shadow public abstract ServerWorld getServerWorld();

    @Inject(at = @At("HEAD"), method = "onDeath")
    public void death(CallbackInfo info) {
        save();
    }

    @Inject(at = @At("HEAD"), method = "teleport")
    public void teleport(CallbackInfo info) {
        save();
    }

    @Inject(at = @At("RETURN"), method = "onSpawn")
    public void onSpawn(CallbackInfo info) {

        //这个方法好像是在进游戏和重生都会触发
        //我这样写意味着只在进游戏时触发

        GameProfile a = getGameProfile();
        String b = getEntityName();
        if(!list.lastlocation.containsKey(a))
        {
            list.lastlocation.put(a, createInfo(getServerWorld(),getBlockPos()));
            for(int i=0;i<list.hello.length;i++)
            {
                sendMessage(new LiteralText(list.hello[i]));
            }
        }
    }

    private void save(){
        list.lastlocation.replace(getGameProfile(), createInfo(getServerWorld(),getBlockPos()));
    }

    private static locationinfo createInfo(ServerWorld world,BlockPos pos)
    {
        locationinfo a = new locationinfo();
        a.position=pos;
        a.world=world;
        return a;
    }
}

//这个里写的比较乱，主要为了实现/back操作的死亡、传送记录

//需要的很多原版方法，getGameProfile()，getEntityName()，getBlockPos()都不在同一类中，
//而是用extends的方法分散在Entity,PlayerEntity,ServerPlayerEntity中

//所以我们在Mixin时也要Mixin3个类，才可以使用他们的方法
//本目录下的EntityMixin PlayerEntityMixin都是为了实现这种方法所不得已创建的