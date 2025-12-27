package co.il.parlagames.pnicks.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import static co.il.parlagames.pnicks.utils.Constants.no_perms;
import static co.il.parlagames.pnicks.utils.Utils.stripColor;

public class RealNameTabCompleter implements TabCompleter {
    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        List<String> l = new ArrayList<>();
        if(!(sender instanceof Player)) return l;
        Player p = (Player) sender;
        if(!p.hasPermission("parlanicks.command.realname")) {
            // no permission
            p.sendMessage(no_perms);
            l.add("Nuh Uh");
            return l;
        }
        if(args.length == 0) {
            for(Player s : Bukkit.getOnlinePlayers())
                l.add(stripColor(s.getDisplayName()));
            return l;
        }

        if (args.length == 1) {
            for(Player s : Bukkit.getOnlinePlayers())
                l.add(stripColor(s.getDisplayName()));

            String keyword = args[0];
            ArrayList<String> list2 = new ArrayList<String>();

            for (String text : l) {
                if (text.toLowerCase().startsWith(keyword.toLowerCase())) {
                    list2.add(text);
                }
            }

            return list2;
        }

        return l;
    }
}
