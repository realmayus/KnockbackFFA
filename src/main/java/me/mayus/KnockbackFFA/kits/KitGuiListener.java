package me.mayus.KnockbackFFA.kits;

import me.mayus.KnockbackFFA.Main;
import me.mayus.KnockbackFFA.util.Database;
import me.mayus.KnockbackFFA.util.KitObject;
import me.mayus.KnockbackFFA.util.NewConfig;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class KitGuiListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent e) {

        Player p = (Player)e.getWhoClicked();
        //main kit menu
        if(e.getClickedInventory() != null) {
            if (e.getView().getTitle().startsWith("§dKits")) {
                e.setCancelled(true);
                // Player has Kit already

                if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§bNächste Seite")) {
                    KitGui.nextPage(p);
                    KitGui.openMainScreen(p);
                } else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§bVorherige Seite")) {
                    KitGui.prevPage(p);
                    KitGui.openMainScreen(p);
                } else if (e.getCurrentItem().getType() == Material.BLUE_STAINED_GLASS_PANE) {
                    //skip
                } else if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§eDein Kontostand")) {
                    //skip
                } else {
                    if(Database.hasKit(p.getUniqueId(), NewConfig.getKitByDisplayName(e.getCurrentItem().getItemMeta().getDisplayName()))) {
                        Kit.setCurrentKit(p, NewConfig.getKitByDisplayName(e.getCurrentItem().getItemMeta().getDisplayName()));
                        Kit.receiveItems(p);
                        p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_CHIME, 1.0F, 1.0F);
                        p.sendMessage(Main.prefix + "§aKit '§6" + e.getCurrentItem().getItemMeta().getDisplayName() + "§a' ausgerüstet.");
                    } else { //Player doesn't have kit yet --> open buy dialog
                        KitGui.openBuyScreen(p, NewConfig.getKitByDisplayName(e.getCurrentItem().getItemMeta().getDisplayName()));
                    }
                }




            } else if(e.getView().getTitle().equalsIgnoreCase("§2Kit kaufen")) {
                e.setCancelled(true);
                if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§cAbbrechen")) {
                    KitGui.openMainScreen(p);
                } else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§aKaufen")) {
                    KitObject kit = NewConfig.getKitByDisplayName(e.getClickedInventory().getItem(0).getItemMeta().getDisplayName());
                    int price = kit.getPrice();

                    if(Database.getCoins(p.getUniqueId()) > price) {
                        Database.spendCoins(p.getUniqueId(), price);
                        Database.addKit(p.getUniqueId(), kit);
                        KitGui.openMainScreen(p);
                        p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 1.0F);
                        p.sendMessage(Main.prefix + "§aVielen Dank für deinen Einkauf, du hast das Kit erfolgreich gekauft! \n");
                        Kit.setCurrentKit(p, kit);
                        Kit.receiveItems(p);
                        p.sendMessage(Main.prefix + "§aKit '§6" + kit.getDisplayName() + "§a' ausgerüstet.");
                    } else {
                        p.closeInventory();
                        p.sendMessage(Main.prefix + "§cDu hast nicht genügend §eCoins§c, um dieses Kit zu kaufen!");
                    }
                }
            }
        }

    }
}
