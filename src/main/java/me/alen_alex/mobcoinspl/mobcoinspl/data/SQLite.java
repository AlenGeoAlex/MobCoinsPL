package me.alen_alex.mobcoinspl.mobcoinspl.data;

import me.alen_alex.mobcoinspl.mobcoinspl.MobCoinsPL;
import me.alen_alex.mobcoinspl.mobcoinspl.PlayerCoins;

import java.io.File;
import java.sql.*;
import java.util.HashMap;
import java.util.UUID;

public class SQLite implements DataStorage{

    private MobCoinsPL mainInstance;
    private final String JDBC_URL;
    private Connection databaseConnection;

    public SQLite(MobCoinsPL mainInstance) {
        this.mainInstance = mainInstance;
        this.JDBC_URL = "jdbc:sqlite:"+mainInstance.getDataFolder().getParentFile().getAbsolutePath()+ File.separator+"QuadrexMobCoins"+File.separator+"database.db";
        mainInstance.getLogger().info(this.JDBC_URL);
    }


    @Override
    public boolean init() {
        try {
            Class.forName("org.sqlite.JDBC");
            databaseConnection = DriverManager.getConnection(JDBC_URL);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }


    @Override
    public HashMap<Integer, PlayerCoins> fetchLeaderBoards() {
        final int limit = mainInstance.getConfiguration().getLeadboardCount();
        final HashMap<Integer,PlayerCoins> cacheCoins = new HashMap<Integer,PlayerCoins>();
        int currentCount = 1;
        try {
            PreparedStatement ps = databaseConnection.prepareStatement("SELECT * FROM `mobcoin_balances` ORDER BY balance DESC LIMIT 0,"+mainInstance.getConfiguration().getLeadboardCount()+";");
            ResultSet set = ps.executeQuery();
            while (set.next() && currentCount <= limit){
                cacheCoins.put(currentCount,new PlayerCoins(UUID.fromString(set.getString("player")),set.getInt("balance")));
                currentCount++;
            }
            set.close();
            ps.close();
            return cacheCoins;
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }
    @Override
    public Connection getConnection(){
        try {
            if(databaseConnection == null && databaseConnection.isClosed()){
                databaseConnection = DriverManager.getConnection(JDBC_URL);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return databaseConnection;
    }
}
