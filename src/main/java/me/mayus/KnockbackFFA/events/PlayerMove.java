package me.mayus.KnockbackFFA.events;

import me.mayus.KnockbackFFA.kits.Kit;
import me.mayus.KnockbackFFA.util.NewConfig;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerMove implements Listener {

    @EventHandler
    public void onMove(org.bukkit.event.player.PlayerMoveEvent e) {
        System.out.println(PlayerKnockoffEvent.knockedPlayers.toString());
        if(NewConfig.Config.getString("worlds." + e.getPlayer().getWorld().getName() + ".DeathHeight") != null) {
            if(e.getPlayer().getLocation().getBlockY() < NewConfig.getDeathHeightForWorld(e.getPlayer().getWorld().getName())) {
                if(!PlayerKnockoffEvent.knockedPlayers.contains(e.getPlayer())) {
                    //TODO stats in DB
                    NewConfig.addDeath(e.getPlayer().getName());
                    e.getPlayer().sendMessage("§cDu bist gestorben!");
                    Bukkit.broadcastMessage("§6" + e.getPlayer().getName() + " §rist gestorben!");
                    e.getPlayer().teleport(NewConfig.getArenaSpawn(NewConfig.getArenaNameFromWorldName(e.getPlayer().getWorld().getName())));
                    e.getPlayer().getInventory().clear();
                    Kit.receiveItems(e.getPlayer());
                }
            }
        }

    }
}
