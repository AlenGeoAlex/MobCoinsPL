package me.alen_alex.mobcoinspl.mobcoinspl;

import org.bukkit.scheduler.BukkitRunnable;

public class RefreshTask extends BukkitRunnable {

    private MobCoinsPL mainInstance;

    public RefreshTask(MobCoinsPL mainInstance) {
        this.mainInstance = mainInstance;
    }

    @Override
    public void run() {
        if(mainInstance.getServer().getOnlinePlayers().isEmpty())
            return;
        
        mainInstance.getLeaderboardCache().clear();
        mainInstance.setLeaderboardCache(mainInstance.getDataStorage().fetchLeaderBoards());
    }
}
