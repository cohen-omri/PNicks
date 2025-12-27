package co.il.parlagames.pnicks.placeholders;

import co.il.parlagames.pnicks.PNicks;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

public class NicksPlaceHolder extends PlaceholderExpansion {
    //private PNicks pNicks;

    //public NicksPlaceHolder(PNicks pNicks) {
        //this.pNicks = pNicks;
    //}

    /*@Override
    public String getPlugin() {
        return pNicks;
    }*/

    @Override
    public @NotNull String getIdentifier() {
        return "%snick%";
    }

    @Override
    public @NotNull String getAuthor() {
        return "Angel";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0.0";
    }

    @Override
    public String onRequest(OfflinePlayer p, String id) {
        return null;
    }
}
