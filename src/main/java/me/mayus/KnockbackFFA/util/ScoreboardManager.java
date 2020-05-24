package me.mayus.KnockbackFFA.util;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.text.DecimalFormat;

public class ScoreboardManager {

    private static DecimalFormat df = new DecimalFormat("0.00"); //for rounding to two decimal places

    public static void createScoreboard(Player player) {
        ScoreboardHelper helper = ScoreboardHelper.createScore(player);
        helper.setTitle("&aKnockback&eFFA");
        helper.setSlot(7, "&7&m--------------------------------");
        helper.setSlot(6, "&aKills&f: " + Database.getKills(player.getUniqueId()));
        helper.setSlot(5, "&aDeaths&f: " + Database.getDeaths(player.getUniqueId()));
        helper.setSlot(4, "&aK/D ratio&f: " + df.format((float)Database.getKills(player.getUniqueId()) / Database.getDeaths(player.getUniqueId())));
        helper.setSlot(3, "&7&m--------------------------------");
        helper.setSlot(2, "&aVote&f: vote.snapecraft.net");
        helper.setSlot(1, "&cReport&f: /report <Spieler>");

    }

    public static void updateScoreboard(Player player) {
        if(ScoreboardHelper.hasScore(player)) {
            ScoreboardHelper helper = ScoreboardHelper.getByPlayer(player);
            helper.setSlot(6, "&aKills&f: " + Database.getKills(player.getUniqueId()));
            helper.setSlot(5, "&aDeaths&f: " + Database.getDeaths(player.getUniqueId()));
            helper.setSlot(4, "&aK/D ratio&f: " + df.format((float)Database.getKills(player.getUniqueId()) / Database.getDeaths(player.getUniqueId())));
        }
    }
}
