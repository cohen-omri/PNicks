package co.il.parlagames.pnicks;

import co.il.parlagames.pnicks.commands.NickCommand;
import co.il.parlagames.pnicks.commands.NickResetCommand;
import co.il.parlagames.pnicks.commands.RealNameCommand;
import co.il.parlagames.pnicks.commands.RealNameTabCompleter;
import co.il.parlagames.pnicks.listeners.*;
import co.il.parlagames.pnicks.placeholders.NicksPlaceHolder;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.*;
import java.util.*;

import static co.il.parlagames.pnicks.utils.Constants.*;
import static co.il.parlagames.pnicks.utils.Utils.color;
import static co.il.parlagames.pnicks.utils.Utils.stripColor;

public final class PNicks extends JavaPlugin {

    public static Map<String, UUID> nicks = new HashMap<>(); // name, uuid (won't be used probably)
    public static Map<String, UUID> nicknames = new HashMap<>(); // nick, uuid
    public static Set<UUID> playersChat = new HashSet<>(); // players write in chat nicks
    public static Set<UUID> playersInventory = new HashSet<>(); // players in nicks inventory
    public static Set<UUID> listInventory = new HashSet<>(); // players in random list inventory
    public static List<String> taken = new ArrayList<>(); //taken nicks
    public static List<String> freeNicksList = new ArrayList<>(); // nicks from random that are not taken
    public static int pages = 1;
    @Override
    public void onEnable() {

        /*if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) { //
            new NicksPlaceHolder(/*this).register();
        }*/

        getConfig().options().copyDefaults(true);
        saveDefaultConfig();
        registerCommands();
        registerEvents();
        user = getConfig().getString("user");
        url = getConfig().getString("url");
        password = getConfig().getString("password");
        try {
            setupMySQL();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        setupConstants();

        for(String n : nicks_list) {
            String nick = stripColor(n).toLowerCase();
            if(!taken.contains(nick))
                freeNicksList.add(n);
        }

        pages = nicks_list.size()/(3*9);
        if(nicks_list.size()%(3*9) != 0) pages += 1;


    }

    @Override
    public void onDisable() {

    }

    public void registerEvents() {
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(),this);
        getServer().getPluginManager().registerEvents(new PlayerQuitListener(),this);
        getServer().getPluginManager().registerEvents(new InventoryClickListener(),this);
        getServer().getPluginManager().registerEvents(new PlayerChatListener(),this);
        getServer().getPluginManager().registerEvents(new PlayerCloseInventoryListener(),this);
    }

    public void registerCommands() {
        getCommand("nick").setExecutor(new NickCommand());
        getCommand("realname").setExecutor(new RealNameCommand());
        getCommand("realname").setTabCompleter(new RealNameTabCompleter());
        getCommand("nickreset").setExecutor(new NickResetCommand());
    }

    private void setupMySQL() throws SQLException {
        Connection con = getConnection();

        Statement st = con.createStatement();
        ResultSet r = st.executeQuery("SELECT * FROM `parlaNicks`");
        //r.next();
        while (r.next()) {
            String snick = r.getString("snick");
            if(snick != null) {
                taken.add(snick.toLowerCase());
                nicknames.put(snick.toLowerCase(),UUID.fromString(r.getString("uuid")));

                /*if(!nicks_list.contains(r.getString("nick")))
                    freeNicksList.add(r.getString("nick"));*/
            }
            if(r.getString("name") != null)
                taken.add(r.getString("name").toLowerCase());
            //Bukkit.getConsoleSender().sendMessage("s: " + r.getString("snick") + " n: " + r.getString("name"));
        }
        r.close();
        //r = st.executeQuery("SELECT `nick` FROM `parlaNicks`");

        //r.getString("name");
        //getServer().broadcastMessage("Connection Created");
        Bukkit.getConsoleSender().sendMessage("PNicks >> Connection Created Successfully");// + r.getString("uuid"));
        st.close();
        con.close();
    }
    private String url,user,password;
    public Connection getConnection() throws SQLException {
        String url = this.url;
        String user = this.user;
        String password = this.password;
        Connection con = DriverManager.getConnection(url,user,password);
        return con;
    }

    public static boolean checkIfTaken(String n) {
        if(taken.contains(stripColor(n).toLowerCase())) return true;
        OfflinePlayer op = Bukkit.getOfflinePlayer(n);
        if(op.getUniqueId() != null && op.hasPlayedBefore()) return true;
        return false;
    }

    public static boolean checkIfNickIsIllegal(String n) {
        for(String s : forbidden_list) {
            if(n.contains(s)) return true;
        }
        if(n.length() < 3 || n.length() > 16) {
            return true;
        }
        return false;
    }

    public static void setNewNick(Player p, String nick) {
        taken.remove(p.getDisplayName().equals(p.getName()) ? "" : stripColor(p.getDisplayName()).toLowerCase());
        p.setDisplayName(color(nick));
        taken.add(stripColor(nick).toLowerCase());
        nicks.put(p.getName(),p.getUniqueId());
        nicknames.put(stripColor(nick).toLowerCase(),p.getUniqueId());
        //p.closeInventory();
        p.sendMessage(color(set_nick.replaceAll("%nick%",nick)));
    }
}
