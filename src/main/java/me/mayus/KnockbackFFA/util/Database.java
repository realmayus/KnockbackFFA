package me.mayus.KnockbackFFA.util;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Database {


    public static int getCoins(UUID uuid) {
        return 100;
    }

    public static int getKills(UUID uuid) {
        return 10;
    }

    public static int getDeaths(UUID uuid) {
        return 15;
    }

    public static int getRank(UUID uuid) {
        return 2;
    }

    public static List<String> getKits(UUID uuid) {
        ArrayList<String> myList = new ArrayList<>();
        myList.add("starter");
        return myList;
    }

    public static Boolean hasKit(UUID uuid, KitObject kit) {
        return true;
    }

    public static void addKit(UUID uuid, KitObject kit) {

    }

    public static void spendCoins(UUID uuid, Integer amount) {

    }




}
