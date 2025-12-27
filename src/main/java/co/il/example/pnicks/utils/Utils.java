package co.il.parlagames.pnicks.utils;

import co.il.parlagames.pnicks.PNicks;
import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
    private static final Pattern hexPatternt = Pattern.compile("#[a-fA-F0-9]{6}");

    /**
     * Converts a string to a colorized string.
     * @Author: Angel
     * @param txt The text to colorize.
     * @return The colorized text.
     * @see #color(List)
     * @see ChatColor
     */
    public static String color(String txt) {
        if(txt == null) return null;

        String version = Bukkit.getVersion();
        if (version.contains("1.16") ||
                version.contains("1.17") ||
                version.contains("1.18") ||
                version.contains("1.19") ||
                version.contains("1.20") ||
                version.contains("1.21")) {

            Matcher match = hexPatternt.matcher(txt);

            while (match.find()) {
                String color = txt.substring(match.start(), match.end());

                txt = txt.replace(color, net.md_5.bungee.api.ChatColor.of(color).toString());

                match = hexPatternt.matcher(txt);
            }

            return net.md_5.bungee.api.ChatColor.translateAlternateColorCodes('&', txt);
        } else {
            return ChatColor.translateAlternateColorCodes('&', txt);
        }
    }

    /**
     * Colors a list using 'org.bukkit.ChatColor'.
     * @Author: Angel
     * @param list The list to be translated
     * @return The color translated list
     * @see #color(String)
     * @see ChatColor
     */
    public static List<String> color(List<String> list) {
        List<String> newList = new ArrayList<>(list.size());
        for(String t : list){
            t = color(t);
            if(t != null) newList.add(t);
            else continue;
        }
        return newList;
    }

    /**
     * Hide all existing flags from an item.
     * @Author: Angel
     * @param item The item to be edited
     * @return the item with all the existing flags hidden
     * @see ItemFlag
     */
    public static ItemStack hideAllFlags(ItemStack item){
        ItemMeta meta = item.getItemMeta();
        meta.addItemFlags(ItemFlag.values());
        item.setItemMeta(meta);
        return item;
    }

    /**
     * Creates a custom ItemStack and returns it.
     * @Author: Angel
     * @param name The name of the item.
     * @param material The material of the item.
     * @param lore The lore of the item.
     * @param amount The amount of items.
     * @return The new item that was created.
     * @see #createSkull(String, OfflinePlayer, List)
     */
    public static ItemStack createItem(String name, Material material, List<String> lore, int amount) {
        ItemStack item = new ItemStack(material, amount);
        ItemMeta meta = item.getItemMeta();
        String nm = color(name);
        meta.setDisplayName(nm);
        List<String> lorea = new ArrayList<>();
        lorea = color(lore);
        meta.setLore(lorea);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        item.setItemMeta(meta);
        return item;
    }

    /**
     * Creates an Item skull with a player head.
     * @Author: Angel
     * @param name The name of the skull.
     * @param owner The name of the player (his head will be on the material).
     * @param lore The lore of the skull.
     * @return The new skull that was created.
     * @see #createItem(String, Material, List, int)
     */
    public static ItemStack createSkull(String name, OfflinePlayer owner, List<String> lore) {
        ItemStack skull = new ItemStack(Material.PLAYER_HEAD, 1, (short) SkullType.PLAYER.ordinal());
        SkullMeta meta = (SkullMeta) skull.getItemMeta();
        meta.setOwningPlayer(Bukkit.getOfflinePlayer(owner.getUniqueId()));
        meta.setDisplayName(color(name));
        List<String> lorea = new ArrayList<>();
        for (String string2 : lore) {
            lorea.add(color(string2));
        }
        meta.setLore(lorea);
        skull.setItemMeta(meta);

        return skull;
    }

    /**
     * @Author: Angel
     * @return The plugin instance.
     * @see PNicks
     */
    public static final PNicks getPlugin(){
        return PNicks.getPlugin(PNicks.class);
    }

    /**
     * @Author: Angel
     * @return The config instance.
     * @see FileConfiguration
     */
    public static final FileConfiguration getConfig(){
        return getPlugin().getConfig();
    }

    public static final Pattern STRIP_COLOR_PATTERN = Pattern.compile("(?i)" + '&' + "[0-9A-FK-ORX]");
    @Contract("!null -> !null; null -> null")
    @Nullable
    public static String stripColor(@Nullable String input) {
        input = input.replaceAll("§","&");
        return input == null ? null : STRIP_COLOR_PATTERN.matcher(input).replaceAll("");
    }
}
