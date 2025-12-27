package co.il.parlagames.pnicks.listeners;

import co.il.parlagames.pnicks.menus.NickMenu;
import co.il.parlagames.pnicks.menus.RandomListMenu;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

import static co.il.parlagames.pnicks.PNicks.listInventory;
import static co.il.parlagames.pnicks.PNicks.playersInventory;
import static co.il.parlagames.pnicks.menus.RandomListMenu.page;

public class PlayerCloseInventoryListener implements Listener {

    @EventHandler
    public void onClose(InventoryCloseEvent e) {
        Player p = (Player) e.getPlayer();
        if(playersInventory.contains(p.getUniqueId())) {
            playersInventory.remove(p.getUniqueId());
        } else if(listInventory.contains(p.getUniqueId())) {
            listInventory.remove(p.getUniqueId());
        }
    }

}
