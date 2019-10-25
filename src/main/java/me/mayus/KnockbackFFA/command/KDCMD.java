package me.mayus.KnockbackFFA.command;

import me.mayus.KnockbackFFA.Main;
import me.mayus.KnockbackFFA.util.NewConfig;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class KDCMD implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        Player p = (Player) sender;

        p.sendMessage(Main.prefix + "Deine K/D: §6" + NewConfig.getKD(p.getName()));


        return true;
    }
}
