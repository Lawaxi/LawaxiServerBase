package net.lawaxi.serverbase.shits;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import org.apache.commons.io.FileUtils;

import java.io.IOException;
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
                    me.sendMessage(new LiteralText("§c传送请求已失效，对方在60秒内没有接受"));
                    to.sendMessage(new LiteralText("§c来自 §4" + me.getEntityName() + " §c的传送请求已失效"));
                }
                list.tparequests.remove(this);
                this.cancel();
            }
        }, 60000);
    }
}
