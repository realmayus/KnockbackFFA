package me.mayus.KnockbackFFA.util;

import me.mayus.KnockbackFFA.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class NewConfig {

    public static File ConfigFile = new File("plugins/KnockbackFFA", "config.yml");
    public static FileConfiguration Config = YamlConfiguration.loadConfiguration(ConfigFile);




    public static void save() throws IOException {
        Config.save(ConfigFile);

    }
    public static void reload() {
        Config = YamlConfiguration.loadConfiguration(ConfigFile);

    }

    public static void setDefaults() {
        try {
            save();
            reload();
        } catch (IOException e) {
            e.printStackTrace();
        }


        List<ItemStack> test = new ArrayList<>();
        test.add(new ItemStack(Material.STICK));
        test.add(new ItemStack(Material.COOKED_MUTTON));

        Config.addDefault("altkits.objectItems", new KitObject("objectTest", "object test", 10, test, Material.REDSTONE));
        Config.addDefault("settings.game.GetHunger", false);

        Config.options().copyDefaults(true);

        try {
            save();
            reload();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    public static HashMap<String, KitObject> getKits() {
        HashMap<String, KitObject> kits = new HashMap<>();
        for(String kit : Config.getConfigurationSection("altkits").getKeys(true)) {
            KitObject kitObject = (KitObject)Config.get("altkits." + kit);
            kits.put(kitObject.getName(), kitObject);
        }
        return kits;
    }

    public static KitObject getKitByDisplayName(String displayName) {
        Iterator it = getKits().entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            KitObject ko = (KitObject)pair.getValue();
            if(ko.getDisplayName().equals(displayName)) {
                return ko;
            }
            it.remove(); // avoids a ConcurrentModificationException
        }
        return null;
    }

    public static KitObject getKit(String name) {
        Iterator it = getKits().entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            KitObject ko = (KitObject)pair.getValue();
            if(ko.getName().equals(name)) {
                return ko;
            }
            it.remove(); // avoids a ConcurrentModificationException
        }
        return null;
    }

    public static void setLobby(Location loc) {
        Config.set("settings.lobby", loc);
        try{
            save();
            reload();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public static Location getLobby() {
        return (Location)Config.get("settings.lobby");
    }

    public static void setArena(String name, Location spawnpos) {
        Config.set("worlds." + spawnpos.getWorld().getName() + ".name", name);
        Config.set("worlds." + spawnpos.getWorld().getName() + ".loc", spawnpos);

        try{
            save();
            reload();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Location getArenaSpawn(String name) {

        return (Location) Config.get("worlds." + getWorldNameFromArenaName(name) + ".loc");

    }

    public static void setKills(String p, Integer amount) {
        ScoreboardManager.updateScoreboard(Bukkit.getPlayer(p));
        Config.set("stats." + p + ".kills", amount);


        try{
            save();
            reload();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Integer getKills(String p) {
        if(Config.getString("stats." + p + ".kills") != null ) {
            return Integer.parseInt(Config.getString("stats." + p + ".kills"));
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
            save();
            reload();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void setDeaths(String p, Integer amount) {
        ScoreboardManager.updateScoreboard(Bukkit.getPlayer(p));
        Config.set("stats." + p + ".deaths", amount);


        try{
            save();
            reload();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Integer getDeaths(String p) {
        if(Config.getString("stats." + p + ".deaths") != null ) {
            return Integer.parseInt(Config.getString("stats." + p + ".deaths"));
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
            save();
            reload();
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
        reload();
    }


    public static void setDeathHeightForWorld(String name, Integer value) {
        Config.set("worlds." + getWorldNameFromArenaName(name) +".DeathHeight", value);

        try{
            save();
            reload();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Integer getDeathHeightForWorld(String world) {
        return Integer.parseInt(Config.getString("worlds." + world + ".DeathHeight"));
    }



    public static List getArenas() {
        List<String> arenas = new ArrayList<>();
        for(String key : Config.getConfigurationSection("worlds").getKeys(false)) {

            arenas.add(Config.getString("worlds." + key + ".name"));
        }

        return arenas;
    }

    public static List getWorlds() {
        List<String> worlds = new ArrayList<>();
        for(String key : Config.getConfigurationSection("worlds").getKeys(false)) {

            worlds.add(key);
        }

        return worlds;
    }

    public static String getWorldNameFromArenaName(String arena) {
        String world = "";
        for(String key : Config.getConfigurationSection("worlds").getKeys(false)) {

            if(Config.getString("worlds." + key + ".name").equalsIgnoreCase(arena)) {
                world = key;
            }
        }
        return world;
    }
    public static String getArenaNameFromWorldName(String world) {
        String arena = "";

        arena = Config.getString("worlds." + world + ".name");


        return arena;
    }
}
