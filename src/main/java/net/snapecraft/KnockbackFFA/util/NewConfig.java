package net.snapecraft.KnockbackFFA.util;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.stream.Collectors;

public class NewConfig {

    public static HashMap<String, KitObject> getKits() {
        HashMap<String, KitObject> kits = new HashMap<>();
        for(String kit : Config.Config.getConfigurationSection("kits").getKeys(false)) {
            KitObject kitObject = (KitObject) Config.Config.get("kits." + kit);
            kits.put(kitObject.getName(), kitObject);
        }
        return kits;
    }

    public static KitObject getKitByDisplayName(String displayName) {
        Iterator it = getKits().entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            if(((KitObject)pair.getKey()).getDisplayName().equalsIgnoreCase(displayName)) {
                return ((KitObject)pair.getKey());
            }
            it.remove(); // avoids a ConcurrentModificationException
        }
        return null;
    }

    public static KitObject getKit(String name) {
        Iterator it = getKits().entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            if(((KitObject)pair.getKey()).getName().equalsIgnoreCase(name)) {
                return ((KitObject)pair.getKey());
            }
            it.remove(); // avoids a ConcurrentModificationException
        }
        return null;
    }

    public static void setLobby(Location loc) {
        Config.Config.set("settings.Lobby.WORLD", loc.getWorld().getName());
        Config.Config.set("settings.Lobby.X", loc.getX());
        Config.Config.set("settings.Lobby.Y", loc.getY());
        Config.Config.set("settings.Lobby.Z", loc.getZ());
        Config.Config.set("settings.Lobby.YAW", loc.getYaw());
        Config.Config.set("settings.Lobby.PITCH", loc.getPitch());
        try{
            Config.save();
            Config.reload();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public static Location getLobby() {
        return new Location(
                Bukkit.getWorld(Config.Config.getString("settings.Lobby.WORLD")),
                Double.parseDouble(Config.Config.getString("settings.Lobby.X")),
                Double.parseDouble(Config.Config.getString("settings.Lobby.Y")),
                Double.parseDouble(Config.Config.getString("settings.Lobby.Z")),
                Float.parseFloat(Config.Config.getString("settings.Lobby.YAW")),
                Float.parseFloat(Config.Config.getString("settings.Lobby.PITCH"))
        );
    }

    public static void setArena(String name, Location spawnpos) {
        Config.Config.set("worlds." + spawnpos.getWorld().getName() + ".name", name);
        Config.Config.set("worlds." + spawnpos.getWorld().getName() + ".X", spawnpos.getX());
        Config.Config.set("worlds." + spawnpos.getWorld().getName() + ".Y", spawnpos.getY());
        Config.Config.set("worlds." + spawnpos.getWorld().getName() + ".Z", spawnpos.getZ());
        Config.Config.set("worlds." + spawnpos.getWorld().getName() + ".YAW", spawnpos.getYaw());
        Config.Config.set("worlds." + spawnpos.getWorld().getName() + ".PITCH", spawnpos.getPitch());

        try{
            Config.save();
            Config.reload();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Location getArenaSpawn(String name) {

        return new Location(
                Bukkit.getWorld(getWorldNameFromArenaName(name)),
                Double.parseDouble(Config.Config.getString("worlds." + getWorldNameFromArenaName(name) + ".X")),
                Double.parseDouble(Config.Config.getString("worlds." + getWorldNameFromArenaName(name) + ".Y")),
                Double.parseDouble(Config.Config.getString("worlds." + getWorldNameFromArenaName(name) + ".Z")),
                Float.parseFloat(Config.Config.getString("worlds." + getWorldNameFromArenaName(name) + ".YAW")),
                Float.parseFloat(Config.Config.getString("worlds." + getWorldNameFromArenaName(name) + ".PITCH"))
        );

    }

    public static void setKills(String p, Integer amount) {

        Config.Config.set("stats." + p + ".kills", amount);


        try{
            Config.save();
            Config.reload();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Integer getKills(String p) {
        if(Config.Config.getString("stats." + p + ".kills") != null ) {
            return Integer.parseInt(Config.Config.getString("stats." + p + ".kills"));
        } else {
            return null;
        }
    }

    public static void addKill(String p) {
        if(getKills(p) == null) {
            setKills(p, 1);
        } else {
            setKills(p, getKills(p) + 1);
        }

        try{
            Config.save();
            Config.reload();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void setDeaths(String p, Integer amount) {

        Config.Config.set("stats." + p + ".deaths", amount);


        try{
            Config.save();
            Config.reload();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Integer getDeaths(String p) {
        if(Config.Config.getString("stats." + p + ".deaths") != null ) {
            return Integer.parseInt(Config.Config.getString("stats." + p + ".deaths"));
        } else {
            return null;
        }

    }

    public static void addDeath(String p) {
        if(getDeaths(p) == null) {
            setDeaths(p, 1);
        } else {
            setDeaths(p, getDeaths(p) + 1);
        }

        try{
            Config.save();
            Config.reload();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Float getKD(String p) {

        try{

            return (float) getKills(p) / getDeaths(p);

        } catch (Exception e) {
            return null;
        }
    }

    public static void reloadConfig() {
        Config.reload();
    }


    public static void setDeathHeightForWorld(String name, Integer value) {
        Config.Config.set("worlds." + getWorldNameFromArenaName(name) +".DeathHeight", value);

        try{
            Config.save();
            Config.reload();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Integer getDeathHeightForWorld(String world) {
        return Integer.parseInt(Config.Config.getString("worlds." + world + ".DeathHeight"));
    }



    public static List getArenas() {
        List<String> arenas = new ArrayList<>();
        for(String key : Config.Config.getConfigurationSection("worlds").getKeys(false)) {

            arenas.add(Config.Config.getString("worlds." + key + ".name"));
        }

        return arenas;
    }

    public static List getWorlds() {
        List<String> worlds = new ArrayList<>();
        for(String key : Config.Config.getConfigurationSection("worlds").getKeys(false)) {

            worlds.add(key);
        }

        return worlds;
    }

    public static String getWorldNameFromArenaName(String arena) {
        String world = "";
        for(String key : Config.Config.getConfigurationSection("worlds").getKeys(false)) {

            if(Config.Config.getString("worlds." + key + ".name").equalsIgnoreCase(arena)) {
                world = key;
            }
        }
        return world;
    }
    public static String getArenaNameFromWorldName(String world) {
        String arena = "";

        arena = Config.Config.getString("worlds." + world + ".name");


        return arena;
    }
}
