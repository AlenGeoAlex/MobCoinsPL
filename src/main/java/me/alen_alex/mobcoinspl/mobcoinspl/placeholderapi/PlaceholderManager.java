package me.alen_alex.mobcoinspl.mobcoinspl.placeholderapi;

import me.alen_alex.mobcoinspl.mobcoinspl.MobCoinsPL;
import org.bukkit.ChatColor;

import java.text.NumberFormat;

public class PlaceholderManager {

    private MobCoinsPL plugin;
    private final String UNKNOWN_POSITION , LEADERBOARD_REFRESHING;

    public PlaceholderManager(MobCoinsPL plugin) {
        this.plugin = plugin;
        UNKNOWN_POSITION = ChatColor.translateAlternateColorCodes('&',"&cUnknown Position");
        LEADERBOARD_REFRESHING = ChatColor.translateAlternateColorCodes('&',"&6Board Refreshing!");
    }

    public String getLeaderboardName(int position){
        if(plugin.getLeaderboardCache().isEmpty())
            return LEADERBOARD_REFRESHING;
        if(plugin.getLeaderboardCache().containsKey(position)){
            return plugin.getLeaderboardCache().get(position).getOfflinePlayerName();
        }else return UNKNOWN_POSITION;
    }

    public String getLeaderboardCoins(int position){
        if(plugin.getLeaderboardCache().isEmpty())
            return LEADERBOARD_REFRESHING;
        if(plugin.getLeaderboardCache().containsKey(position)){
            return String.valueOf(plugin.getLeaderboardCache().get(position).getPlayerBalance());
        }else return UNKNOWN_POSITION;
    }

    public String getLeaderboardCoinsFormatted(int position){
        if(plugin.getLeaderboardCache().isEmpty())
            return LEADERBOARD_REFRESHING;
        if(plugin.getLeaderboardCache().containsKey(position)){
            return String.valueOf(NumberFormat.getInstance().format(plugin.getLeaderboardCache().get(position).getPlayerBalance()));
        }else return UNKNOWN_POSITION;
    }
}
