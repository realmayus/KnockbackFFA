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

            if (e.getEntity().getLocation().getBlockY() < NewConfig.getArenaSpawn(NewConfig.getArenaNameFromWorldName(e.getEntity().getWorld().getName())).getBlockY() ) {
                if (!knockedPlayers.contains((Player)e.getEntity()))    {
                    knockedPlayers.add((Player)e.getEntity());
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            ctdwn++;
                            if (ctdwn >= 5) {
                                Player victim = (Player) e.getEntity();
                                Player murderer = (Player) e.getDamager();
                                cancel(); //stop runnable
                                ctdwn = 1; //reset countdown
                                if (victim.getLocation().getY() <= NewConfig.getDeathHeightForWorld(e.getEntity().getWorld().getName())) {
                                    NewConfig.addDeath(victim.getName()); //statistics
                                    NewConfig.addKill(murderer.getName()); //statistics
                                    victim.sendMessage("§cDu wurdest von " + murderer.getName() + " getötet!");
                                    murderer.sendMessage("§aDu hast " + victim.getName() + " getötet!");
                                    Bukkit.broadcastMessage("§6" + murderer.getName() + " §rhat §6" + victim.getName() + " §rgetötet!");
                                    victim.teleport(NewConfig.getArenaSpawn(NewConfig.getArenaNameFromWorldName(victim.getWorld().getName())));
                                    victim.setHealth(20D); //reset health of victim
                                    knockedPlayers.remove(victim); //remove victim from list of knocked players
                                    e.setCancelled(true);
                                } else {
                                    knockedPlayers.remove(victim);
                                }

                            } else { //need this again in case the victim is below the death height when the timer isn't at 5 yet
                                Player victim = (Player) e.getEntity();
                                Player murderer = (Player) e.getDamager();
                                if (victim.getLocation().getY() <= NewConfig.getDeathHeightForWorld(e.getEntity().getWorld().getName())) {
                                    NewConfig.addDeath(victim.getName());
                                    NewConfig.addKill(murderer.getName());
                                    victim.sendMessage("§cDu wurdest von " + murderer.getName() + " getötet!");
                                    murderer.sendMessage("§aDu hast " + victim.getName() + " getötet!");
                                    Bukkit.broadcastMessage("§6" + murderer.getName() + " §rhat §6" + victim.getName() + " §rgetötet!");
                                    victim.teleport(NewConfig.getArenaSpawn(NewConfig.getArenaNameFromWorldName(victim.getWorld().getName())));
                                    victim.setHealth(20D);
                                    knockedPlayers.remove(victim);
                                    e.setCancelled(true);
                                }
                            }
                        }
                    }.runTaskTimer(Main.getPlugin(Main.class), 0, 20);
                }
            }
        }
    }
}
