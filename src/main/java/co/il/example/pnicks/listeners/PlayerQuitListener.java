package co.il.parlagames.pnicks.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static co.il.parlagames.pnicks.PNicks.nicks;
import static co.il.parlagames.pnicks.utils.Utils.getPlugin;
import static co.il.parlagames.pnicks.utils.Utils.stripColor;

public class PlayerQuitListener implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent e) throws SQLException {
        Player p = e.getPlayer();
        if(!p.hasPermission("parlanicks.command.randomnick"))
            return;

        String s = p.getDisplayName();
        Connection con = getPlugin().getConnection();
        Statement st = con.createStatement();

        if(!s.equals(p.getName())) {
            st.execute("UPDATE `parlaNicks` SET name='"+p.getName()+"',isNicked="+true+",nick='"+s+"',snick='"+stripColor(s)+"'" +
                    " WHERE uuid='"+p.getUniqueId()+"';");
            st.close();
            con.close();
            return;
        }
        ResultSet r = st.executeQuery("SELECT * FROM `parlaNicks` WHERE uuid='"+p.getUniqueId()+"';");
        r.next();
        if(r.getBoolean("isNicked")) {
            st.execute("UPDATE `parlaNicks` SET name='"+p.getName()+"',isNicked="+false+",nick="+null+",snick="+null+
                    " WHERE uuid='"+p.getUniqueId()+"';");
        }
        nicks.remove(stripColor(s));
        st.close();
        con.close();
    }

}
