package net.snapecraft.KnockbackFFA.kits;

import net.snapecraft.KnockbackFFA.util.Config;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.List;

public class Kit {

    public static HashMap<Player, String> currentKits = new HashMap<>();

    public static String getCurrentKit(Player p){
        return currentKits.getOrDefault(p, "starterItems");
    }

    public static void setCurrentKit(Player p, String kit) {
        currentKits.remove(p);
        currentKits.put(p, kit);
    }

    public static void receiveItems(Player p) {
        List<Material> ml = Config.getItemsOfKit(getCurrentKit(p));

        for(Material material : ml) {
            //ToDo: Special Items hier einfügen
            if(material.equals(Material.STICK)) {
                ItemStack stick = new ItemStack(material);
                ItemMeta stickm = stick.getItemMeta();
                stickm.setDisplayName("§5Knockback§r-§aStick");
                stickm.addEnchant(Enchantment.KNOCKBACK, 4, true);
                stick.setItemMeta(stickm);
                p.getInventory().addItem(stick);
            } else {
                p.getInventory().addItem(new ItemStack(material));
            }
        }
    }
}
