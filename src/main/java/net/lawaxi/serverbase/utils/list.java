package net.lawaxi.serverbase.utils;

import com.google.common.io.Files;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.mojang.authlib.GameProfile;
import net.lawaxi.serverbase.utils.locationinfo;
import net.lawaxi.serverbase.utils.tparequest;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;

public class list {

    public static final ArrayList<tparequest> tparequests = new ArrayList<>();
    public static final HashMap<GameProfile,locationinfo> lastlocation = new HashMap<>();
}
