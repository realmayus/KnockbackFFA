package me.mayus.KnockbackFFA.kits;

import me.mayus.KnockbackFFA.Main;
import me.mayus.KnockbackFFA.util.KitObject;
import me.mayus.KnockbackFFA.util.NewConfig;
import me.mayus.KnockbackFFA.util.Utils;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;

public class KitGuiCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender p, Command command, String label, String[] args) {

        if(p instanceof Player) {
            if (args.length == 0) {
                if(!Main.gamelist.containsKey(p.getName())) {
                    p.sendMessage("§cDu musst in einer Arena sein, um diesen Commannd auszuführen.");
                    return false;
                }
                KitGui.openMainScreen((Player)p);
            } else {
                if (args[0].equalsIgnoreCase("create")) {
                    if (Utils.checkPerm((Player) p, "KBFFA.createKit")) {
                        if(args.length == 4) {
                            if (((Player) p).getInventory().getItemInMainHand().getType() != Material.AIR) {
                                if(Utils.checkBasicUnicodeCompliance(args[1])) {
                                    if (Utils.canParseInt(args[3])) {
                                        NewConfig.Config.set("altkits." + args[1], new KitObject(args[1], args[2].replace('%', ' '), 10, ((Player) p).getInventory(), ((Player) p).getInventory().getItemInMainHand().getType()));
                                        try {
                                            NewConfig.save();
                                            NewConfig.reload();
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                            return true;
                                        }
                                        p.sendMessage(Main.prefix + "§aKit erstellt!");
                                    } else {
                                        p.sendMessage(Main.prefix + "Der Preis muss eine ganze Zahl sein.");
                                    }
                                } else {
                                    p.sendMessage(Main.prefix + "Der Kitname ist ungültig, er sollte keine Umlaute oder Leerzeichen enthalten.");
                                }
                            } else {
                                p.sendMessage(Main.prefix + "Halte ein Item in deiner Hand, das als Icon benutzt werden soll.");
                            }
                        } else {
                            p.sendMessage(Main.prefix + "Benutzung:");
                            p.sendMessage(Main.prefix + "/kit create <name> <displayName> <price>");
                            p.sendMessage(Main.prefix + "Im displayName kann % als Leerzeichen verwendet werden.");
                        }
                    }
                } else {
                    p.sendMessage(Main.prefix + "Dieser Command existiert nicht.");
                    return true;
                }
            }
        } else {
            p.sendMessage("This command can only be executed by a player.");
        }
        return false;
    }
}
