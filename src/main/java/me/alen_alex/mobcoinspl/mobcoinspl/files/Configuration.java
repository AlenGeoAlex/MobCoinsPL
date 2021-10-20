package me.alen_alex.mobcoinspl.mobcoinspl.files;

import de.leonhard.storage.Config;
import me.alen_alex.mobcoinspl.mobcoinspl.MobCoinsPL;
import org.apache.commons.lang3.StringUtils;

public class Configuration {

    private MobCoinsPL mainInstance;
    private Config pluginConfig;
    private String version;
    //
    private boolean usingSQL = false;
    private String sqlHost,sqlPort,sqlUsername,sqlPassword,sqlDatabase;
    private int leadboardCount,leaderboardRefreshTime;


    public Configuration(MobCoinsPL mainInstance) {
        this.mainInstance = mainInstance;
    }

    public boolean initConfig(){
        pluginConfig = mainInstance.getFileUtils().createConfiguration();
        if(pluginConfig != null){
            version = pluginConfig.getOrSetDefault("version",mainInstance.getDescription().getVersion());
            mainInstance.getLogger().info("Configuration has been found and loaded!");
            mainInstance.getLogger().info("Verision: "+version);
            return true;
        }else return false;
    }

    public String getVersion() {
        return version;
    }

    public void loadConfig(){
        leadboardCount = pluginConfig.getInt("settings.leaderboard-count");
        leaderboardRefreshTime = pluginConfig.getInt("settings.leaderboard-refresh-in-mins");
    }



    public int getLeadboardCount() {
        return leadboardCount;
    }

    public int getLeaderboardRefreshTime() {
        return leaderboardRefreshTime;
    }
}
