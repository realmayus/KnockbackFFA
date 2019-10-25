package me.mayus.KnockbackFFA.kits;

import me.mayus.KnockbackFFA.util.KitObject;
import me.mayus.KnockbackFFA.util.NewConfig;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;

public class Kit {

    public static HashMap<Player, KitObject> currentKits = new HashMap<>();

    public static KitObject getCurrentKit(Player p){
        return currentKits.getOrDefault(p, NewConfig.getKit("objectItems"));
    }

    public static void setCurrentKit(Player p, KitObject kit) {
        currentKits.remove(p);
        currentKits.put(p, kit);
    }

//    public static void receiveItems(Player p) {
//        List<Material> ml = Config.getItemsOfKit(getCurrentKit(p));
//
//        for(Material material : ml) {
//            if(material.equals(Material.STICK)) {
//                ItemStack stick = new ItemStack(material);
//                ItemMeta stickm = stick.getItemMeta();
//                stickm.setDisplayName("§5Knockback§r-§aStick");
//                stickm.addEnchant(Enchantment.KNOCKBACK, 4, true);
//                stick.setItemMeta(stickm);
//                p.getInventory().addItem(stick);
//            } else {
//                p.getInventory().addItem(new ItemStack(material));
//            }
//        }
//    }

    public static void receiveItems(Player p) {

        for(ItemStack itemStack : getCurrentKit(p).getItemsIncluded()) {
            if(itemStack.getType().equals(Material.STICK)) {
                ItemStack stick = new ItemStack(itemStack.getType());
                ItemMeta stickm = stick.getItemMeta();
                stickm.setDisplayName("§5Knockback§r-§aStick");
                stickm.addEnchant(Enchantment.KNOCKBACK, 4, true);
                stick.setItemMeta(stickm);
                p.getInventory().addItem(stick);
            } else {
                p.getInventory().addItem(new ItemStack(itemStack.getType()));
            }
        }
    }
}
