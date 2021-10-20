package me.alen_alex.mobcoinspl.mobcoinspl.placeholderapi;

import me.alen_alex.mobcoinspl.mobcoinspl.MobCoinsPL;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PlaceholderAPI extends PlaceholderExpansion {

    private MobCoinsPL instance;
    private PlaceholderManager manager;
    public PlaceholderAPI(MobCoinsPL instance) {
        this.instance = instance;
        this.manager = new PlaceholderManager(this.instance);
    }

    @Override
    public @NotNull String getIdentifier() {
        return "mcoins";
    }

    @Override
    public @NotNull String getAuthor() {
        return "Alen_Alex";
    }

    @Override
    public @NotNull String getVersion() {
        return instance.getDescription().getVersion();
    }

    @Override
    public boolean canRegister(){
        return true;
    }

    @Override
    public boolean persist(){
        return true;
    }

    @Override
    public String onRequest(OfflinePlayer player, String identifier) {
        System.out.println(identifier);
        if(identifier.startsWith("topplayer")){
            String[] args = identifier.split("_");
            if(args.length == 2){
                System.out.println("Is Numeric");
                if(StringUtils.isNumeric(args[1])) {
                    return manager.getLeaderboardName(Integer.parseInt(args[1]));
                }else return "Error";
            }else return "Error";
        }

        if(identifier.startsWith("topcoins")){
            String[] args = identifier.split("_");
            if(args.length == 2){
                if(StringUtils.isNumeric(args[1]))
                    return manager.getLeaderboardCoins(Integer.parseInt(args[1]));
                else return "Error";
            }else return "Error";
        }
        return null;
    }
}
