package me.alen_alex.mobcoinspl.mobcoinspl.data;

import me.alen_alex.mobcoinspl.mobcoinspl.PlayerCoins;

import java.sql.Connection;
import java.util.HashMap;

public interface DataStorage {

    boolean init();

    HashMap<Integer, PlayerCoins> fetchLeaderBoards();

    Connection getConnection();



}
