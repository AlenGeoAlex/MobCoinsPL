package me.alen_alex.mobcoinspl.mobcoinspl;

import me.alen_alex.mobcoinspl.mobcoinspl.data.DataStorage;
import me.alen_alex.mobcoinspl.mobcoinspl.data.SQLite;
import me.alen_alex.mobcoinspl.mobcoinspl.files.Configuration;
import me.alen_alex.mobcoinspl.mobcoinspl.placeholderapi.PlaceholderAPI;
import me.alen_alex.mobcoinspl.mobcoinspl.utils.FileUtils;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public final class MobCoinsPL extends JavaPlugin {

    private static MobCoinsPL plugin;
    private boolean placeholderAPIEnabled = false;
    private FileUtils fileUtils;
    private Configuration configuration;
    private static HashMap<Integer,PlayerCoins> leaderboardCache = new HashMap<Integer,PlayerCoins>();
    private DataStorage dataStorage;
    private RefreshTask refreshTask;

    @Override
    public void onEnable() {
        plugin = this;
        if(!getServer().getPluginManager().isPluginEnabled("QuadrexMobCoins")){
            getLogger().info("=-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-=");
            getLogger().info("=-");
            getLogger().info("=- The plugin QuadrexMobCoins is missing/disabled. This is a dependency.");
            getLogger().info("=- This plugin will be disabled!");
            getLogger().info("=- ");
            getLogger().info("=-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-=");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        if(getServer().getPluginManager().isPluginEnabled("PlaceholderAPI")) placeholderAPIEnabled = true;
        fileUtils = new FileUtils(this);
        configuration = new Configuration(this);
        if(!configuration.initConfig()){
            getLogger().info("=-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-=");
            getLogger().info("=-");
            getLogger().info("=- The plugin is unable to create/load config.yml.");
            getLogger().info("=- This plugin will be disabled!");
            getLogger().info("=- ");
            getLogger().info("=-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-=");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        configuration.loadConfig();
        dataStorage = new SQLite(this);
        if(!dataStorage.init()){
            getLogger().info("=-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-=");
            getLogger().info("=-");
            getLogger().info("=- The plugin is unable to load database.db.");
            getLogger().info("=- This plugin will be disabled!");
            getLogger().info("=- ");
            getLogger().info("=-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-==-=");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        plugin.getServer().getScheduler().runTaskAsynchronously(plugin, new Runnable() {
            @Override
            public void run() {
                leaderboardCache = dataStorage.fetchLeaderBoards();
            }
        });
        refreshTask = new RefreshTask(this);
        if(configuration.getLeaderboardRefreshTime() > 0){
            refreshTask.runTaskTimerAsynchronously(plugin, (long) configuration.getLeaderboardRefreshTime() *20*60, (long) configuration.getLeaderboardRefreshTime() *20*60 );
        }

        if(isPlaceholderAPIEnabled()) {
            new PlaceholderAPI(this).register();
            getLogger().info("Hooked with PlaceholderAPI");
        }

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static MobCoinsPL getPlugin() {
        return plugin;
    }

    public boolean isPlaceholderAPIEnabled() {
        return placeholderAPIEnabled;
    }

    public FileUtils getFileUtils() {
        return fileUtils;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public HashMap<Integer, PlayerCoins> getLeaderboardCache() {
        return leaderboardCache;
    }

    public DataStorage getDataStorage() {
        return dataStorage;
    }

    public void setLeaderboardCache(HashMap<Integer, PlayerCoins> leaderboardCache) {
        MobCoinsPL.leaderboardCache = leaderboardCache;
    }
}
