package co.il.parlagames.pnicks.listeners;

import co.il.parlagames.pnicks.PNicks;
import co.il.parlagames.pnicks.menus.NickMenu;
import co.il.parlagames.pnicks.menus.RandomListMenu;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.sql.SQLException;

import static co.il.parlagames.pnicks.PNicks.listInventory;
import static co.il.parlagames.pnicks.PNicks.playersInventory;
import static co.il.parlagames.pnicks.utils.Utils.color;

public class InventoryClickListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if(playersInventory.contains(e.getWhoClicked().getUniqueId())) {
            e.setCancelled(true);
            NickMenu.clicked((Player)e.getWhoClicked(),e.getSlot(),e.getCurrentItem(),e.getInventory());
        }
        if(listInventory.contains(e.getWhoClicked().getUniqueId())) {
            e.setCancelled(true);
            RandomListMenu.clicked((Player)e.getWhoClicked(),e.getSlot(),e.getCurrentItem(),e.getInventory());
        }
    }

}
