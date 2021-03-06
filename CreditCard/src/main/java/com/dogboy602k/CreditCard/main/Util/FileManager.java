package com.dogboy602k.CreditCard.main.Util;/*
 * Decompiled with CFR 0_118.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 *  org.bukkit.configuration.Configuration
 *  org.bukkit.configuration.ConfigurationSection
 *  org.bukkit.configuration.file.FileConfiguration
 *  org.bukkit.configuration.file.YamlConfiguration
 */

import com.dogboy602k.CreditCard.main.Main.Main;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

public class FileManager {
    private Main plugin;
    private ArrayList <UUID> list = new ArrayList();

    public FileManager(Main plugin) {
        this.plugin = plugin;
    }

    public void saveDefaultConfiguration(File file) {
        if (file == null) {
            System.out.println("Error config file null");
            return;
        }
        if (!file.exists()) {
            this.plugin.saveResource(file.getName(), false);
        }
    }

    public void loadPlayerData() {
        Manager m = this.plugin.getManager();
        File playerDataFile = new File(this.plugin.getDataFolder(), "playerdata.yml");
        FileConfiguration playerData = this.plugin.getFileManager().getConfiguration(playerDataFile);
        ConfigurationSection sec = playerData.getConfigurationSection("cardinfo");
        if (sec == null) {
            return;
        }
        int loadedArenas = 0;
        Set cardInfoSet = sec.getKeys(false);
        if (cardInfoSet != null) {
            for (String aPlayerUUID : playerData.getConfigurationSection("cardinfo").getKeys(false)) {
                String path = "cardinfo." + aPlayerUUID + ".";
                UUID playerUUID = UUID.fromString(aPlayerUUID);

                int pin = playerData.getInt(path + "pin");
                double debt = playerData.getInt(path + "debt");
                String nameofplayer = playerData.getString(path + "username");
                String password = playerData.getString(path + "password");
                long loanTimestamp = playerData.getLong(path + "time");
                long intrestUpdateDate = playerData.getInt(path + "intrestUpdateDate");

                list.add(playerUUID);
                m.addCardInfo(new CardInfo(playerUUID, pin, nameofplayer, password, debt, loanTimestamp, intrestUpdateDate));
                m.addArray(list);

                ++loadedArenas;
            }
        }
        SendConsoleMessage.info("Successfully loaded " + ChatColor.AQUA + loadedArenas + ChatColor.GREEN + " Credit Cards.");
    }

    public void savePlayerData() {
        if (this.plugin.getManager().getCardinfo().size() == 0) {
            SendConsoleMessage.info("No info to be stored");
            return;
        }
        File playerDataFile = new File(this.plugin.getDataFolder(), "playerdata.yml");
        FileConfiguration playerDataConfig = this.plugin.getFileManager().getConfiguration(playerDataFile);
        for (CardInfo cardInfo : this.plugin.getManager().getCardinfo()) {
            String path = "cardinfo." + cardInfo.getOwner().toString() + ".";
            playerDataConfig.set(path + "pin", cardInfo.getpin());
            playerDataConfig.set(path + "username", cardInfo.getName());
            playerDataConfig.set(path + "password", cardInfo.getPassword());
            playerDataConfig.set(path + "debt", cardInfo.getDebt());
            playerDataConfig.set(path + "time", cardInfo.getloanTimestamp());
            playerDataConfig.set(path + "intrestUpdateDate", cardInfo.getintrestUpdateDate());
        }
        try {
            playerDataConfig.save(playerDataFile);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reloadConfiguration(File file) {
        if (file == null) {
            System.out.println("Error config file null");
            return;
        }
        YamlConfiguration configFile = YamlConfiguration.loadConfiguration((File)file);
        InputStream defConfigStream = this.plugin.getResource(file.getName());
        if (defConfigStream != null) {
            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration((InputStream)defConfigStream);
            configFile.setDefaults(defConfig);
        }
    }

    public FileConfiguration getConfiguration(File file) {
        if (file == null) {
            System.out.println("Error config file null");
            return null;
        }
        return YamlConfiguration.loadConfiguration((File)file);
    }

    public void saveConfiguration(File file) {
        if (file == null) {
            System.out.println("Error config file null");
            return;
        }
        try {
            this.getConfiguration(file).save(file);
        }
        catch (IOException e) {
            System.out.println("Error saving configuration file! " + e.getMessage());
        }
    }

    public int getMaxLoanRankOne() {
        return this.plugin.getConfig().getInt("rankone");
    }

    public int getMaxLoanRankTwo() {
        return this.plugin.getConfig().getInt("ranktwo");
    }

    public int getMaxLoanRankThree() {
        return this.plugin.getConfig().getInt("rankthree");
    }

    public int getMaxLoanRankDefault() {
        return this.plugin.getConfig().getInt("defaultamount");
    }

    public int getMaxLoanRankFour() {
        return this.plugin.getConfig().getInt("rankfour");
    }

    public int getMaxLoanRankFive() {
        return this.plugin.getConfig().getInt("rankfive");
    }

    public int getMaxLoanRankSix() {
        return this.plugin.getConfig().getInt("ranksix");
    }

    public long getperdays() {
        return this.plugin.getConfig().getLong("perdays");
    }

    public int getDebtDeadLine() {
        return this.plugin.getConfig().getInt("debtdeadline");
    }

    public boolean getHackerDetect() {
        return this.plugin.getConfig().getBoolean("hackerdetect");
    }

    public double getIntrestRate() {
        return this.plugin.getConfig().getDouble("intrestrate");
    }

    public double getButtonAdd1() {
        return this.plugin.getConfig().getDouble("buttonadd1");
    }

    public double getButtonAdd2() {
        return this.plugin.getConfig().getDouble("buttonadd2");
    }

    public double getButtonSubtract1() {
        return this.plugin.getConfig().getDouble("buttonsubtract1");
    }

    public double getButtonSubtract2() {
        return this.plugin.getConfig().getDouble("buttonsubtract2");
    }
}

