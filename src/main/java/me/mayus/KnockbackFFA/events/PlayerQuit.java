package me.mayus.KnockbackFFA.events;

import me.mayus.KnockbackFFA.Main;
import me.mayus.KnockbackFFA.util.ScoreboardHelper;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuit implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Main.removeFromGamelist(e.getPlayer().getName());
        if(ScoreboardHelper.hasScore(e.getPlayer())) {
            ScoreboardHelper.removeScore(e.getPlayer());
        }
    }
}
