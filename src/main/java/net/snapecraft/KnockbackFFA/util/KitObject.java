package net.snapecraft.KnockbackFFA.util;

import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KitObject implements ConfigurationSerializable {
    private String name;
    private String displayName;
    private int price;
    private List<ItemStack> itemsIncluded;
    private Material icon;

    public String getName() {
        return name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getPrice() {
        return price;
    }

    public List<ItemStack> getItemsIncluded() {
        return itemsIncluded;
    }

    public Material getIcon() {
        return icon;
    }

    public KitObject(String name, String displayName, Integer price, List<ItemStack> itemsIncluded, Material icon) {
        this.name = name;
        this.displayName = displayName;
        this.price = price;
        this.itemsIncluded = itemsIncluded;
        this.icon = icon;
    }

    // This is necessary for saving the object in a config
    public KitObject(Map<String, Object> map) {
        this.name = (String)map.get("name");
        this.displayName = (String)map.get("displayName");
        this.price = (int)map.get("price");
        this.itemsIncluded = (List<ItemStack>)map.get("itemsIncluded");
        this.icon = Material.valueOf((String)map.get("icon"));
    }

    // This is necessary for saving the object in a config
    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", getName());
        map.put("displayName", getDisplayName());
        map.put("price", getPrice());
        map.put("itemsIncluded", getItemsIncluded());
        map.put("icon", getIcon().name());
        return map;
    }


}
