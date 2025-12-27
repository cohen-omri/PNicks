package co.il.parlagames.pnicks.commands;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static co.il.parlagames.pnicks.PNicks.*;
import static co.il.parlagames.pnicks.utils.Constants.no_perms;
import static co.il.parlagames.pnicks.utils.Constants.wrong_syntax;
import static co.il.parlagames.pnicks.utils.Utils.*;

public class RealNameCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!(sender instanceof Player)) return false;
        Player p = (Player) sender;
        if(!p.hasPermission("parlanicks.command.realname")) {
            // no permission
            p.sendMessage(no_perms);
            return false;
        }
        if(args.length != 1) {
            // wrong syntax
            p.sendMessage(wrong_syntax.replaceAll("%syntax%","/realname {nick}"));
            return false;
        }
        try {
            Connection con = getPlugin().getConnection();
            Statement st = con.createStatement();
            ResultSet r1 = st.executeQuery("SELECT `name` FROM `parlaNicks` WHERE snick='"+args[0]+"'");
            String s = null;
            String ss = args[0];

            if(r1.next()) {
                s = r1.getString("name");
            } else {
                //s = args[0];
                if(taken.contains(stripColor(args[0].toLowerCase()))) {
                    if(nicknames.get(stripColor(args[0].toLowerCase())) == null) {
                        p.sendMessage(color("&cPlayer doesn't have a nickname"));
                        return false;
                    }
                    OfflinePlayer op = Bukkit.getOfflinePlayer(nicknames.get(stripColor(args[0].toLowerCase())));
                    s = op.getName();
                } else {
                    p.sendMessage(color("&cNot a real nick/name."));
                    return false;
                }
            }
            /*if(s == null) {
                getPlugin().getServer().broadcastMessage("danos maniac");
                return false;
            }*/
            if(checkIfTaken(args[0])) {
                p.sendMessage(color("&aReal name of &r" + ss + "&a is &r"+s));
            } else {
                p.sendMessage(color("&cNot a real nick/name."));
            }

            /*if(!taken.contains(s.toLowerCase())) {
                p.sendMessage(color("&cNot a real nick/name."));
                return false;
            }*/
            //getPlugin().getServer().broadcastMessage("brono said to cut here. : : : : : " + s);
            /*Player n = Bukkit.getPlayer(s);

            if(n == null) {
                // dead player
                p.sendMessage(color("&cNot a real player."));
                return false;
            }*/

            //ResultSet r = st.executeQuery("SELECT * FROM `parlaNicks` WHERE name='"+s+"'");
            //r.next();
            //if(r.getString("nick") == null) {

            //} else {
                //p.sendMessage(color("&aReal name of &r" + args[0] + "&a is &r"+s));
            //}
            st.close();
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return true;
    }
}
