package me.alen_alex.mobcoinspl.mobcoinspl;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.util.UUID;

public class PlayerCoins {

    private UUID playerUUID;
    private int playerBalance;
    private OfflinePlayer playerOffline;

    public PlayerCoins(UUID playerUUID, int playerBalance) {
        this.playerUUID = playerUUID;
        this.playerBalance = playerBalance;
        fetchOfflinePlayer();
    }

    public void fetchOfflinePlayer(){
        try {
            playerOffline = Bukkit.getServer().getOfflinePlayer(this.playerUUID);
        }catch (Exception e){
            Bukkit.getLogger().info("Failed to fetch offline player info for the player!");
        }
    }

    public UUID getPlayerUUID() {
        return playerUUID;
    }

    public int getPlayerBalance() {
        return playerBalance;
    }

    public String getOfflinePlayerName(){
        if(playerOffline != null) {
            if(this.playerOffline.getName() != null)
                return this.playerOffline.getName();
            else return "Unknown Player";
        }
        else return "Unknown Player";
    }
}
