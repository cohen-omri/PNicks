package co.il.parlagames.pnicks.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static co.il.parlagames.pnicks.PNicks.nicknames;
import static co.il.parlagames.pnicks.PNicks.taken;
import static co.il.parlagames.pnicks.utils.Constants.no_perms;
import static co.il.parlagames.pnicks.utils.Constants.wrong_syntax;
import static co.il.parlagames.pnicks.utils.Utils.*;

public class NickResetCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!(sender instanceof Player)) return false;
        Player p = (Player) sender;
        if(!p.hasPermission("parlanicks.command.resetother")) {
            // no permission
            p.sendMessage(no_perms);
            return false;
        }
        if(args.length != 1) {
            // wrong syntax
            p.sendMessage(wrong_syntax.replaceAll("%syntax%","/nickreset {nick}"));
            return false;
        }
        try {
            Connection con = getPlugin().getConnection();
            Statement st = con.createStatement();
            ResultSet r1 = st.executeQuery("SELECT * FROM `parlaNicks` WHERE name='"+args[0]+"'");
            String s = args[0], ss = "";
            if(r1.next()) {
                //s = r1.getString("name");
                ss = r1.getString("nick");
            }
            else {
                //s = args[0];
                ss = args[0];
            }

            Player n = Bukkit.getPlayer(s);
            if(n == null) {
                // dead player
                p.sendMessage(color("&cNot a real player."));
                return false;
            }

            taken.remove(stripColor(args[0].toLowerCase()));
            nicknames.remove(stripColor(args[0].toLowerCase()));
            st.execute("UPDATE `parlaNicks` SET name='"+n.getName()+"',isNicked="+false+",nick="+null+",snick="+null+
                    " WHERE uuid='"+n.getUniqueId()+"';");
            n.setDisplayName(n.getName());
            if(ss == null) ss = "Wasn't nicked or sql thingy";
            p.sendMessage(color("&aNickname (&r"+ss+"&a) has been reset to &r"+n.getName()));
            st.close();
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }
}
