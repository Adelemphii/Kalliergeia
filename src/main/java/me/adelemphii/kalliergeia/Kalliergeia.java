package me.adelemphii.kalliergeia;

import me.adelemphii.kalliergeia.commands.SettingsCommand;
import me.adelemphii.kalliergeia.commands.TabCompletion;
import me.adelemphii.kalliergeia.events.*;
import me.adelemphii.kalliergeia.utils.storage.SQLManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class Kalliergeia extends JavaPlugin {

    // instance of the SQL class to access through DI in other classes
    SQLManager sqlManager; // should be private

    @Override
    public void onEnable() {
        saveDefaultConfig();

        registerCommands();
        registerEvents();

        // BEGIN SQL COLLECTION - Loads every user's settings from the database
        this.getLogger().info("Starting SQL Manager...");
        sqlManager = new SQLManager(this);
        sqlManager.collectAllUsersFromTable();
        // END SQL COLLECTION
    }

    @Override
    public void onDisable() {
        // BEGIN SQL SAVING - Saves every user's settings to the database
        this.getLogger().info("Saving UserSettings in SQL Manager...");
        sqlManager.saveToPlayerTable();
        // END SQL SAVING
    }

    private void registerCommands() {
        Objects.requireNonNull(getCommand("kalliergeia")).setExecutor(new SettingsCommand(this)); // no need to wrap these in requireNonNull checks?
        Objects.requireNonNull(getCommand("kalliergeia")).setTabCompleter(new TabCompletion());
    }

    private void registerEvents() {
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(this), this);
        getServer().getPluginManager().registerEvents(new CropTrampleListener(this), this);
        getServer().getPluginManager().registerEvents(new HoeFarmingListener(this), this);
        getServer().getPluginManager().registerEvents(new PistonBreakCropListener(this), this);
        getServer().getPluginManager().registerEvents(new BreakDirtUnderCropListener(), this);
    }

    public SQLManager getSQLManager() {
        return sqlManager;
    }
}
