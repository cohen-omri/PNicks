package co.il.parlagames.pnicks.commands;

import co.il.parlagames.pnicks.menus.NickMenu;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

import static co.il.parlagames.pnicks.PNicks.*;
import static co.il.parlagames.pnicks.PNicks.playersChat;
import static co.il.parlagames.pnicks.utils.Constants.*;
import static co.il.parlagames.pnicks.utils.Utils.*;

public class NickCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return false;

        Player p = (Player) sender;
        if (args.length == 0) {
            // open menu
            p.openInventory(NickMenu.GUI(p));
            playersInventory.add(p.getUniqueId());
        } else if (args.length == 1) {
            if (!p.hasPermission("parlanicks.command.customnick")) {
                // no permission
                p.sendMessage(no_perms);
                return false;
            }
            String nick = args[0];
            if (checkIfNickIsIllegal(stripColor(nick))) {
                // nick is illegal
                p.sendMessage(illegal_nick);
                return false;
            }
            if (checkIfTaken(stripColor(nick))) {
                p.sendMessage(taken_nick);
                return false;
            }
            taken.remove(p.getDisplayName().equals(p.getName()) ? "" : stripColor(p.getDisplayName()).toLowerCase());
            if (Objects.equals(stripColor(nick), nick)) { // no color
                // set player's new nick
                /*p.setDisplayName(nick);
                p.sendMessage(set_nick.replaceAll("%nick%",nick));*/
                setNewNick(p, nick);
                //p.closeInventory();
            } else {
                if (!p.hasPermission("parlanicks.command.nickcolorized")) {
                    // no permission
                    p.sendMessage(no_perms);
                    return false;
                }
                // set player's new nick (with colors)
                /*p.setDisplayName(color(nick));
                p.sendMessage(set_nick.replaceAll("%nick%",color(nick)));*/
                setNewNick(p, nick);
                //p.closeInventory();
            }
            //nicks.put(p.getName(),p.getUniqueId());
            //taken.add(stripColor(nick).toLowerCase());
        } else {
            // nick has spaces -> error
            p.sendMessage(illegal_nick);
        }

        return true;
    }
}
