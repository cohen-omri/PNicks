package co.il.parlagames.pnicks.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.Objects;

import static co.il.parlagames.pnicks.PNicks.*;
import static co.il.parlagames.pnicks.utils.Constants.*;
import static co.il.parlagames.pnicks.utils.Utils.color;
import static co.il.parlagames.pnicks.utils.Utils.stripColor;

public class PlayerChatListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onChat(AsyncPlayerChatEvent e) {

        if (playersChat.contains(e.getPlayer().getUniqueId())) {
            Player p = e.getPlayer();
            String nick = e.getMessage();
            if (!p.hasPermission("parlanicks.command.customnick")) {
                // no permission
                p.sendMessage(no_perms);
                e.setCancelled(true);
                playersChat.remove(p.getUniqueId());
                return;
            }
            if (checkIfNickIsIllegal(stripColor(nick))) {
                // nick is illegal
                p.sendMessage(illegal_nick);
                e.setCancelled(true);
                playersChat.remove(p.getUniqueId());
                return;
            }
            if (checkIfTaken(stripColor(nick))) {
                p.sendMessage(taken_nick);
                e.setCancelled(true);
                playersChat.remove(p.getUniqueId());
                return;
            }
            taken.remove(stripColor(p.getDisplayName()).toLowerCase());
            if (Objects.equals(stripColor(nick), nick)) { // no color
                // set player's new nick
                setNewNick(p, nick);
                //p.closeInventory();
            } else {
                if (!p.hasPermission("parlanicks.command.nickcolorized")) {
                    // no permission
                    p.sendMessage(no_perms);
                    e.setCancelled(true);
                    playersChat.remove(p.getUniqueId());
                    return;
                }

                setNewNick(p, nick);
                // set player's new nick (with colors)
                /*p.setDisplayName(color(nick));
                p.sendMessage(set_nick.replaceAll("%nick%",color(nick)));*/
            }
            /*nicks.put(p.getName(),p.getUniqueId());
            nicknames.
            taken.add(stripColor(nick).toLowerCase());*/
            e.setCancelled(true);
            playersChat.remove(p.getUniqueId());
        }
    }

}
