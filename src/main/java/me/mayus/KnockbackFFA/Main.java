package me.mayus.KnockbackFFA;

import me.mayus.KnockbackFFA.command.Buildmode;
import me.mayus.KnockbackFFA.command.commands;
import me.mayus.KnockbackFFA.events.*;
import me.mayus.KnockbackFFA.kits.KitGuiCommand;
import me.mayus.KnockbackFFA.kits.KitGuiListener;
import me.mayus.KnockbackFFA.util.KitObject;
import me.mayus.KnockbackFFA.util.NewConfig;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Objects;

public class Main extends JavaPlugin {

    // PlayerName, WorldName
    public static HashMap<String, String> gamelist = new HashMap<>();
    public static String prefix = ChatColor.GRAY + "[" + ChatColor.YELLOW + "KBFFA" + ChatColor.GRAY + "] " + ChatColor.LIGHT_PURPLE + ">> " + ChatColor.RESET;
    public static String noperm = prefix + "§cDu hast nicht die Berechtigung, um diesen Befehl auszuführen!";

    @Override
    public void onEnable() {
        ConfigurationSerialization.registerClass(KitObject.class, "KitObject");

        ConsoleCommandSender console = this.getServer().getConsoleSender();

        console.sendMessage(" \n\n___  __    ________  ________ ________ ________     \n" +
                "|\\  \\|\\  \\ |\\   __  \\|\\  _____\\\\  _____\\\\   __  \\    \n" +
                "\\ \\  \\/  /|\\ \\  \\|\\ /\\ \\  \\__/\\ \\  \\__/\\ \\  \\|\\  \\   \n" +
                " \\ \\   ___  \\ \\   __  \\ \\   __\\\\ \\   __\\\\ \\   __  \\  \n" +
                "  \\ \\  \\\\ \\  \\ \\  \\|\\  \\ \\  \\_| \\ \\  \\_| \\ \\  \\ \\  \\ \n" +
                "   \\ \\__\\\\ \\__\\ \\_______\\ \\__\\   \\ \\__\\   \\ \\__\\ \\__\\\n" +
                "    \\|__| \\|__|\\|_______|\\|__|    \\|__|    \\|__|\\|__|\n" +
                "                                                     \n" +
                "                                                     \n" +
                "                                                     ");

        init();
    }

    public void init() {

        NewConfig.setDefaults();

        System.out.println("\n\n" + NewConfig.getKits().toString() + "\n\n");
        Bukkit.getPluginManager().registerEvents(new PlayerKnockoffEvent(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerJoin(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerQuit(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerMove(), this);
        Bukkit.getPluginManager().registerEvents(new LobbyRestrictions(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerDeath(), this);

        Bukkit.getPluginManager().registerEvents(new KitGuiListener(), this);

        Objects.requireNonNull(getCommand("kbffa")).setExecutor(new commands());
        Objects.requireNonNull(getCommand("kit")).setExecutor(new KitGuiCommand());
        Objects.requireNonNull(getCommand("kits")).setExecutor(new KitGuiCommand());
        Objects.requireNonNull(getCommand("build")).setExecutor(new Buildmode());
    }

    public static void addToGamelist(String p, String world) {
        gamelist.remove(p);
        gamelist.put(p, world);
    }
    public static void removeFromGamelist(String p) {
        gamelist.remove(p);
    }
}
