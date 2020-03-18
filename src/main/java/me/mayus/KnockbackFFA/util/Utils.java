package me.mayus.KnockbackFFA.util;

import me.mayus.KnockbackFFA.Main;
import org.bukkit.entity.Player;

public class Utils {
    public static boolean checkBasicUnicodeCompliance(String text){
        char[] arr = text.toCharArray();

        for (char c : arr) {
            if (Character.UnicodeBlock.of(c) != Character.UnicodeBlock.BASIC_LATIN) {
                return false;
            }
        }
        return true;
    }

    public static boolean canParseInt(String text) {
        return text.matches("-?\\d+");
    }

    public static boolean checkPerm(Player p, String perm) {
        if (p.hasPermission(perm)){
            return true;
        } else {
            p.sendMessage(Main.noperm);
            return false;
        }
    }
}
