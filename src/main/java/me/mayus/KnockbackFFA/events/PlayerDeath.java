package me.mayus.KnockbackFFA.events;

import me.mayus.KnockbackFFA.Main;
import me.mayus.KnockbackFFA.kits.Kit;
import me.mayus.KnockbackFFA.util.NewConfig;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class PlayerDeath implements Listener {

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        Player p = e.getEntity();
        e.setDeathMessage(null);
        if(Main.gamelist.containsKey(p.getName())) {
            NewConfig.addDeath(p.getName());
            p.sendMessage("§cDu bist gestorben!");
            Bukkit.broadcastMessage("§6" + p.getName() + " §rist gestorben!");
            p.teleport(NewConfig.getArenaSpawn(NewConfig.getArenaNameFromWorldName(p.getWorld().getName())));
        } else {
            p.teleport(NewConfig.getLobby());
        }
    }


    @EventHandler
    public void onRespawn(PlayerRespawnEvent e) {
        if(Main.gamelist.containsKey(e.getPlayer().getName())) {
            Kit.receiveItems(e.getPlayer());
        }
    }
}
