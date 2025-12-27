package co.il.parlagames.pnicks.menus;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import static co.il.parlagames.pnicks.PNicks.*;
import static co.il.parlagames.pnicks.menus.RandomListMenu.page;
import static co.il.parlagames.pnicks.utils.Constants.*;
import static co.il.parlagames.pnicks.utils.Utils.*;

public class NickMenu {
    public static Inventory GUI(Player p) {

        final Inventory inv = Bukkit.createInventory((InventoryHolder) null, 3 * 9, color(nicks_title));
        ItemStack item;
        List<String> lore;
        int slot;
        OfflinePlayer owner = (OfflinePlayer) p;
        ItemMeta meta;

        lore = exit_button_lore;
        item = createItem(exit_button_name, Material.PAPER, lore, 1);
        meta = item.getItemMeta();
        meta.setCustomModelData(exit_data);
        item.setItemMeta(meta);
        slot = 0;
        inv.setItem(slot, item);

        lore = new ArrayList<>();
        for(String s : info_button_lore)
            lore.add(s.replaceAll("%player%",color(p.getDisplayName())));
        item = createItem(info_button_name.replaceAll("%player%",color(p.getDisplayName())),Material.PAPER,lore,1);
        //createSkull(info_button_name.replaceAll("%player%", color(p.getDisplayName())), p, lore);
        meta = item.getItemMeta();
        meta.setCustomModelData(info_data);
        item.setItemMeta(meta);
        item = hideAllFlags(item);
        slot = 8;
        inv.setItem(slot, item);

        lore = random_button_lore;
        item = createItem(random_button_name, Material.PAPER, lore, 1);
        meta = item.getItemMeta();
        meta.setCustomModelData(random_data);
        item.setItemMeta(meta);
        item = hideAllFlags(item);
        slot = 11;
        inv.setItem(slot, item);

        lore = list_button_lore;
        item = createItem(list_button_name, Material.PAPER, lore, 1);
        meta = item.getItemMeta();
        meta.setCustomModelData(list_data);
        item.setItemMeta(meta);
        item = hideAllFlags(item);
        slot = 12;
        inv.setItem(slot, item);

        lore = custom_button_lore;
        item = createItem(custom_button_name, Material.PAPER, lore, 1);
        meta = item.getItemMeta();
        meta.setCustomModelData(custom_data);
        item.setItemMeta(meta);
        item = hideAllFlags(item);
        slot = 13;
        inv.setItem(slot, item);

        lore = reset_button_lore;
        item = createItem(reset_button_name, Material.PAPER, lore, 1);
        meta = item.getItemMeta();
        meta.setCustomModelData(reset_data);
        item.setItemMeta(meta);
        item = hideAllFlags(item);
        slot = 15;
        inv.setItem(slot, item);
        return inv;
    }

    static Random r = new Random();

    public static void clicked(final Player p, final int slot, final ItemStack clicked, final Inventory inv) {
        if (clicked != null && clicked.getItemMeta() != null && clicked.getItemMeta().getDisplayName() != null) {
            if (clicked == null || clicked.getItemMeta() == null) {
                return;
            }
            if (clicked.getItemMeta().getDisplayName().equals(color(exit_button_name))) {
                playersInventory.remove(p.getUniqueId());
                boolean b = p.performCommand(exit_command);
                if(!b) p.closeInventory();
                //p.closeInventory();
                return;
            }
            if (slot == 15 && clicked.getItemMeta().getDisplayName().equals(color(reset_button_name))) { // reset
                taken.remove(stripColor(p.getDisplayName()).toLowerCase());
                nicks.remove(p.getName());
                p.setDisplayName(p.getName());
                p.sendMessage(removed_nick);
                playersInventory.remove(p.getUniqueId());
                nicknames.remove(stripColor(p.getDisplayName()).toLowerCase());
                p.closeInventory();
                return;
            }
            if (slot == 13 && clicked.getItemMeta().getDisplayName().equals(color(custom_button_name))) { // custom nick
                if(!p.hasPermission("parlanicks.command.customnick")) {
                    p.sendMessage(color(no_perms));
                    return;
                }
                playersChat.add(p.getUniqueId());
                p.sendMessage(type_nick);
                playersInventory.remove(p.getUniqueId());
                p.closeInventory();
                return;
            }
            if (slot == 12 && clicked.getItemMeta().getDisplayName().equals(color(list_button_name))) { // opens list with existing nicks
                if(!p.hasPermission("parlanicks.command.fromlist")) {
                    p.sendMessage(color(no_perms));
                    return;
                }
                page.put(p.getUniqueId(), 1);
                p.openInventory(RandomListMenu.GUI(p));
                playersInventory.remove(p.getUniqueId());
                listInventory.add(p.getUniqueId());
                return;
            }
            if (slot == 11 && clicked.getItemMeta().getDisplayName().equals(color(random_button_name))) { // random nick
                if(!p.hasPermission("parlanicks.command.randomnick")) {
                    p.sendMessage(color(no_perms));
                    return;
                }
                String nick = freeNicksList.get(r.nextInt(0, freeNicksList.size()));
                if (checkIfTaken(stripColor(nick))) {
                    p.sendMessage(taken_nick);
                    return;
                }
                /*taken.remove(p.getDisplayName().equals(p.getName()) ? "" : stripColor(p.getDisplayName()).toLowerCase());
                p.setDisplayName(color(nick));
                taken.add(stripColor(nick).toLowerCase());
                nicks.put(p.getName(),p.getUniqueId());
                p.closeInventory();
                p.sendMessage(color(set_nick.replaceAll("%nick%",nick)));*/
                setNewNick(p, nick);
                p.closeInventory();
                playersInventory.remove(p.getUniqueId());
                return;
            }
        }
    }

}
