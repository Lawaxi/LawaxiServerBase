package net.lawaxi.serverbase.utils;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;

import java.util.Timer;
import java.util.TimerTask;

public class tparequest {

    public ServerPlayerEntity me;
    public ServerPlayerEntity to;
    public boolean mode;//false为tpa true为tpahere

    public boolean exist=true;

    public tparequest(){

        new Timer().schedule(new TimerTask() {
            public void run() {
                if(exist) {
                    me.sendMessage(new LiteralText("§c传送请求已失效，对方在60秒内没有接受"),false);
                    to.sendMessage(new LiteralText("§c来自 §4" + me.getEntityName() + " §c的传送请求已失效"),false);
                }
                list.tparequests.remove(this);
                this.cancel();
            }
        }, 60000);
    }

    public static boolean hasrequest(ServerPlayerEntity me){

        for(tparequest request : list.tparequests)
        {
            if(request.me.equals(me))
                return true;
        }
        return false;
    }

    public static tparequest search(ServerPlayerEntity who)
    {
        for(tparequest request : list.tparequests)
        {
            if(request.to.equals(who))
            {

                request.exist=false;
                list.tparequests.remove(request);
                return request;
            }
        }
        return null;
    }
}
