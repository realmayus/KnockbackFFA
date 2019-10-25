package me.mayus.KnockbackFFA.events;

import me.mayus.KnockbackFFA.Main;
import me.mayus.KnockbackFFA.util.NewConfig;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class PlayerKnockoffEvent implements Listener {
    int ctdwn = 1;
    public static List<Player> knockedPlayers = new ArrayList<>();
    @EventHandler
    public void onKnockoff(final EntityDamageByEntityEvent e) {

        if(e.getEntity() instanceof Player && e.getDamager() instanceof Player) {

            //Leben wiederherstellen
            ((Player)e.getEntity()).setHealth(20.0);

            //Damage auf der Plattform verbieten
            if(e.getEntity().getLocation().getBlockY() == NewConfig.getArenaSpawn(NewConfig.getArenaNameFromWorldName(e.getEntity().getWorld().getName())).getBlockY()) {
                e.setCancelled(true);
            }

            //Todeshöhe abfragen
            else if(e.getEntity().getLocation().getBlockY() != NewConfig.getDeathHeightForWorld(e.getEntity().getWorld().getName())) {
                knockedPlayers.add((Player)e.getEntity());
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        ctdwn++;
                        if(ctdwn >= 5) {
                            Player victim = (Player)e.getEntity();
                            Player murderer = (Player)e.getDamager();
                            cancel();
                            ctdwn = 1;
                            e.setCancelled(true);
                            if(victim.getLocation().getY() <= NewConfig.getDeathHeightForWorld(e.getEntity().getWorld().getName())) {
                                NewConfig.addDeath(victim.getName());
                                NewConfig.addKill(murderer.getName());
                                victim.sendMessage("§cDu wurdest von " + murderer.getName() + " getötet!");
                                murderer.sendMessage("§aDu hast " + victim.getName() + " getötet!");
                                Bukkit.broadcastMessage("§6" + murderer.getName() + " §rhat §6" + victim.getName() + " §rgetötet!");
                                victim.teleport(NewConfig.getArenaSpawn(NewConfig.getArenaNameFromWorldName(victim.getWorld().getName())));
                                knockedPlayers.remove(victim);
                            }

                        }
                    }
                }.runTaskTimer(Main.getPlugin(Main.class), 0, 20);
            } else {
                e.setCancelled(true);
            }

        }



    }


}
