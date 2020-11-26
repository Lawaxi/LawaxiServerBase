package net.lawaxi.serverbase.utils;

import com.mojang.authlib.GameProfile;

import java.util.ArrayList;
import java.util.HashMap;

public class List {

    public static final ArrayList<TpaRequest> tparequests = new ArrayList<>();
    public static final HashMap<GameProfile, LocationInfo> lastlocation = new HashMap<>();
}
