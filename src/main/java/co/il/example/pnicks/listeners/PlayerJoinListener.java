package co.il.parlagames.pnicks.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static co.il.parlagames.pnicks.PNicks.nicknames;
import static co.il.parlagames.pnicks.PNicks.nicks;
import static co.il.parlagames.pnicks.utils.Utils.*;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) throws SQLException {
        Player p = e.getPlayer();
        if(!p.hasPermission("parlanicks.command.randomnick"))
            return;

        Connection con = getPlugin().getConnection();
        Statement st = con.createStatement();
        ResultSet r = st.executeQuery("SELECT * FROM `parlaNicks` WHERE uuid='"+p.getUniqueId()+"'");
        //r.next();
        if(!r.next()) {
            st.execute("INSERT INTO `parlaNicks` VALUES ('"+p.getUniqueId()+"','"
                    +p.getName()+"',"+false+","+null+","+null+")");
            p.setDisplayName(p.getName());
            nicks.put(p.getName(),p.getUniqueId());
        } else {
            p.setDisplayName(color(r.getString("nick")));
            if(r.getString("nick") != null)
                nicknames.put(stripColor(r.getString("nick")),p.getUniqueId());
            /*else
                nicknames.put()*/
        }
        st.close();
        con.close();
    }

}
