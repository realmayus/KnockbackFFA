package me.mayus.KnockbackFFA.events;

import me.mayus.KnockbackFFA.Main;
import me.mayus.KnockbackFFA.util.ScoreboardManager;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import me.mayus.KnockbackFFA.kits.KitGui;
import me.mayus.KnockbackFFA.util.NewConfig;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;


public class PlayerJoin implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        ScoreboardManager.createScoreboard(e.getPlayer());
        KitGui.currentPage.put(e.getPlayer(), 0);
        e.getPlayer().getInventory().clear();
        e.getPlayer().setHealth(20.0);
        e.getPlayer().sendMessage("§aWillkommen auf KnockbackFFA!");

        if(NewConfig.getLobby() == null) {
            if(e.getPlayer().hasPermission("KBFFA.receiveWarnings")) {
                e.getPlayer().sendMessage("KBFFA ist noch nicht eingerichtet. Klicke den Knopf, um den Setup-Wizard zu starten:");
                TextComponent message1 = new TextComponent( "[" + ChatColor.YELLOW +  "Setup starten§r] " );
                message1.setClickEvent( new ClickEvent( ClickEvent.Action.RUN_COMMAND, "/kbffa setup" ));
                e.getPlayer().spigot().sendMessage(message1);

            } else {
                e.getPlayer().sendMessage("KBFFA ist noch nicht eingerichtet. Bitte warte, bis ein Admin dies tut.");
            }
        } else {
            e.getPlayer().teleport(NewConfig.getLobby());

            e.getPlayer().sendMessage("§aNutze /kbffa join <welt>, um einer Welt beizutreten!");

        }
    }
}
