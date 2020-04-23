package net.lawaxi.serverbase.mixin;

import com.mojang.authlib.GameProfile;
import net.lawaxi.serverbase.utils.HttpRequest;
import net.lawaxi.serverbase.utils.list;
import net.lawaxi.serverbase.utils.locationinfo;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import java.util.Base64;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin extends PlayerEntityMixin{

    @Shadow public abstract ServerWorld getServerWorld();

    @Shadow @Final public MinecraftServer server;

    @Shadow public abstract void sendMessage(Text message, boolean actionBar);

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
        if(!list.lastlocation.containsKey(a))
        {
            list.lastlocation.put(a, createInfo(getServerWorld(),getBlockPos()));
            sendMessage(new LiteralText(list.hello.replace("%player%",getEntityName())),true);
        }

        /*
        //盗版服的正版皮肤恢复
        if(!getServer().isOnlineMode())
        {
            //获取正版uuid
            String uuid = HttpRequest.sendGet("https://api.mojang.com/users/profiles/minecraft/"+getEntityName(),"");
            uuid = uuid.substring(uuid.indexOf("id\":\""),uuid.indexOf("\",\"name")).replace("id\":\"","");

            if(!uuid.equals("")) {

                System.out.println("成功获取"+getEntityName()+"正版UUID："+uuid);

                GameProfile profile = new GameProfile(UUID.fromString(uuid.substring(0,8)+"-"+uuid.substring(8,12)+"-"+uuid.substring(12,16)+"-"+uuid.substring(16,20)+"-"+uuid.substring(20)),getEntityName());
                profile=SkullBlockEntity.loadProperties(profile);
                getGameProfile().getProperties().asMap().put("textures",profile.getProperties().asMap().get("textures"));

            }
        }
        */

    }


    private static String getOnlineSkin(String name)
    {
        //1.获取玩家正版UUid
        String uuid = HttpRequest.sendGet("https://api.mojang.com/users/profiles/minecraft/"+name,"");
        uuid = uuid.substring(uuid.indexOf("id\":\""),uuid.indexOf("\",\"name")).replace("id\":\"","");
        if(uuid.equals(""))
            return "";

        //2.获取加密的皮肤信息
        String profile=HttpRequest.sendGet("https://sessionserver.mojang.com/session/minecraft/profile/"+uuid,"");
        profile=profile.substring(profile.indexOf("value\":\""),profile.indexOf("\"}]}")).replace("value\":\"","");

        if(profile.equals(""))
            return "";

        //3.解密获得直链
        profile =  Base64.getDecoder().decode(profile).toString();
        profile = profile.substring(profile.indexOf("/texture/"),profile.indexOf(",")).replace("/texture/","");

        if(profile.equals("undefined"))
            return "";
        else
            return profile;
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