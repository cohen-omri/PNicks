package co.il.parlagames.pnicks.menus;

import co.il.parlagames.pnicks.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.*;

import static co.il.parlagames.pnicks.PNicks.*;
import static co.il.parlagames.pnicks.utils.Constants.*;
import static co.il.parlagames.pnicks.utils.Utils.*;
import static co.il.parlagames.pnicks.utils.Utils.stripColor;

public class RandomListMenu {

    public static Map<UUID,Integer> page = new HashMap<>();
    public static Inventory GUI(Player p) {
        int pa = page.get(p.getUniqueId());
        final Inventory inv = Bukkit.createInventory((InventoryHolder) null, 6 * 9, color(list_title.replaceAll("%number%",pa+"")));
        ItemStack item;
        List<String> lore;
        int slot;
        OfflinePlayer owner = (OfflinePlayer) p;
        ItemMeta meta;

        /*lore = exit_button_lore;
        item = createItem(exit_button_name, Material.BARRIER, lore, 1);
        slot = 6*9-9;
        inv.setItem(slot, item);*/

        //left arrow
        lore = back_button_lore;
        item = createItem(back_button_name, Material.PAPER, lore, 1);
        meta = item.getItemMeta();
        meta.setCustomModelData(back_data);
        item.setItemMeta(meta);
        item = hideAllFlags(item);
        slot = 6*9-18+9;
        inv.setItem(slot, item);

        //right arrow
        lore = next_button_lore;
        item = createItem(next_button_name, Material.PAPER, lore, 1);
        meta = item.getItemMeta();
        meta.setCustomModelData(next_data);
        item.setItemMeta(meta);
        item = hideAllFlags(item);
        slot = 6*9-10+9;
        inv.setItem(slot, item);

        int min = pa == 1 ? 0 : 3*9*(pa-1);
        int max = 3*9*pa;
        if(max > nicks_list.size()) max = nicks_list.size();

        int k = 0,j = 0;
        for(int i = min ; i<max ; i++) {
            lore = choose_button_lore;
            item = createItem(choose_button_name.replaceAll("%nick%",nicks_list.get(i)), Material.PAPER, lore, 1);
            meta = item.getItemMeta();
            meta.setCustomModelData(choose_data);
            item.setItemMeta(meta);
            if((i != min && i%9 == 0) || j == 9) {k++; j = 0;}
            item = hideAllFlags(item);
            slot = 9 + 9*k + j;
            inv.setItem(slot, item);
            j++;
        }

        return inv;
    }

    public static void clicked(final Player p, final int slot, final ItemStack clicked, final Inventory inv) {
        if(clicked == null || clicked.getItemMeta() == null || clicked.getType() == Material.AIR) {
            return;
        }
        String name = color(clicked.getItemMeta().getDisplayName());
        if(name == null || name.isEmpty()) return;
        if(name.equals(color(list_button_name))) return;

        if(name.equals(color(exit_button_name))){
            p.openInventory(NickMenu.GUI(p));
            playersInventory.add(p.getUniqueId());
            listInventory.remove(p.getUniqueId());
            page.put(p.getUniqueId(),1);
            return;
        }
        if(slot == (6*9-10+9) && name.equals(color(next_button_name))) { // next
            if(page.get(p.getUniqueId()) == pages) {
                return;
            }
            page.put(p.getUniqueId(),page.get(p.getUniqueId())+1);
            p.openInventory(GUI(p));
            listInventory.add(p.getUniqueId());
            return;
        }
        if(name.equals(color(back_button_name))) { // back
            if(page.get(p.getUniqueId()) == 1) {
                p.openInventory(NickMenu.GUI(p));
                playersInventory.add(p.getUniqueId());
                listInventory.remove(p.getUniqueId());
                page.put(p.getUniqueId(),1);
                return;
            }
            page.put(p.getUniqueId(),page.get(p.getUniqueId())-1);
            p.openInventory(GUI(p));
            listInventory.add(p.getUniqueId());
            return;
        }
        String nic = choose_button_name.replaceAll("%nick%","");
        name = clicked.getItemMeta().getDisplayName().substring(nic.length() - 2);
        //p.sendMessage(name + " : " + nic);
        if(checkIfNickIsIllegal(stripColor(name))) {
            // nick is illegal
            p.sendMessage(illegal_nick);
            return;
        }
        if(checkIfTaken(stripColor(name))) {
            p.sendMessage(taken_nick);
            return;
        }
        if(nicks_list.contains(name)) {
            /*taken.remove(p.getDisplayName().equals(p.getName()) ? "" : stripColor(p.getDisplayName()).toLowerCase());
            p.setDisplayName(color(name));
            taken.add(stripColor(name).toLowerCase());
            p.closeInventory();
            p.sendMessage(color(set_nick.replaceAll("%nick%",name)));
            nicks.put(p.getName(),p.getUniqueId());*/
            setNewNick(p,name);
            p.closeInventory();
            listInventory.remove(p.getUniqueId());
            return;
        }
    }

}
