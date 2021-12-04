package net.lawaxi.serverbase.utils;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;

import java.util.Timer;
import java.util.TimerTask;

public class TpaRequest {

    public ServerPlayerEntity me;
    public ServerPlayerEntity to;
    public boolean mode;//false为tpa true为tpahere

    public boolean exist = true;

    public TpaRequest() {
        TpaRequest that = this;

        new Timer().schedule(new TimerTask() {
            public void run() {
                if (exist) {
                    me.sendMessage(new LiteralText("§c传送请求已失效，对方在60秒内没有接受"), false);
                    to.sendMessage(new LiteralText("§c来自 §4" + me.getEntityName() + " §c的传送请求已失效"), false);
                }
                List.tparequests.remove(that);
                this.cancel();
            }
        }, 60000);
    }

    public static boolean hasrequest(ServerPlayerEntity me) {

        for (TpaRequest request : List.tparequests) {
            if (request.me.equals(me))
                return true;
        }
        return false;
    }

    public static TpaRequest search(ServerPlayerEntity who) {
        for (TpaRequest request : List.tparequests) {
            if (request.to.equals(who)) {

                request.exist = false;
                List.tparequests.remove(request);
                return request;
            }
        }
        return null;
    }
}
