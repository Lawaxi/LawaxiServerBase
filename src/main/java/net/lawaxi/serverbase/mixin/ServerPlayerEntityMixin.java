package net.lawaxi.serverbase.mixin;

import com.mojang.authlib.GameProfile;
import net.lawaxi.serverbase.utils.checking;
import net.lawaxi.serverbase.utils.config.messages;
import net.lawaxi.serverbase.utils.list;
import net.lawaxi.serverbase.utils.locationinfo;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameMode;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin {


    @Shadow public abstract void playerTick();

    @Shadow public abstract void sendMessage(Text message, boolean actionBar);

    @Inject(at = @At("HEAD"), method = "onDeath")
    public void death(CallbackInfo info) {
        save();
    }

    @Inject(at = @At("HEAD"), method = "teleport")
    public void teleport(CallbackInfo info) {
        save();

        ServerPlayerEntity player = ((ServerPlayerEntity)(Object)this);
        if(player.interactionManager.getGameMode()== GameMode.SPECTATOR)
        {
            player.sendMessage(new LiteralText(messages.get(62,player.getGameProfile().getName())),false);
            info.cancel();
        }
    }

    @Inject(at = @At("HEAD"), method = "onDisconnect")
    public void onLogOut(CallbackInfo info) {

        GameProfile a = ((ServerPlayerEntity)(Object)this).getGameProfile();
        if(list.lastlocation.containsKey(a))
            list.lastlocation.remove(a);
    }


    @Inject(at = @At("RETURN"), method = "onSpawn")
    public void onSpawn(CallbackInfo info) {

        GameProfile a = ((ServerPlayerEntity)(Object)this).getGameProfile();



        //语言初始化为null对应语言
        if(messages.getLang(a.getName()).equalsIgnoreCase("null")){
            String defaultLang = messages.getDefaultLang();
            if(defaultLang!=null){
                messages.setLang(a.getName(),defaultLang);
            }
        }


        //出生位置和欢迎消息
        if(!list.lastlocation.containsKey(a))
        {
            list.lastlocation.put(a, createInfo(((ServerPlayerEntity)(Object)this).getServerWorld(),((ServerPlayerEntity)(Object)this).getBlockPos()));

            try {
                sendMessage(new LiteralText(messages.get(0, a.getName()).replace("%player%", a.getName())), true);
            }catch (NullPointerException e){

            }
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
        checking.check(((ServerPlayerEntity)(Object)this).getServer().getPlayerManager().getUserBanList());
    }

    /*
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
    }*/

    private void save(){
        list.lastlocation.replace(((ServerPlayerEntity)(Object)this).getGameProfile(), createInfo(((ServerPlayerEntity)(Object)this).getServerWorld(),((ServerPlayerEntity)(Object)this).getBlockPos()));
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