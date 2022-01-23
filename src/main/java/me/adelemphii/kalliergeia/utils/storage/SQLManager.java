package me.adelemphii.kalliergeia.utils.storage;

import me.adelemphii.kalliergeia.Kalliergeia;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

public class SQLManager {

    private final Kalliergeia plugin;
    // the player settings data map
    private Map<String, UserSettings> playersSettings = new HashMap<>();

    private String url;

    public SQLManager(Kalliergeia plugin) {
        this.plugin = plugin;
        this.url = "jdbc:sqlite:" + plugin.getDataFolder() + "/kalliergeia.db";

        createPlayerTable();
    }

    private static Connection connection;

    // connect to the database
    public void connect() {
        if(!isConnected()) {
            try {
                connection = DriverManager.getConnection(url);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // close the connection
    public boolean disconnect() {
        if(isConnected()) {
            try {
                connection.close();
                return true;
            } catch (SQLException e) {
                return false;
            }
        }
        return true;
    }

    // create the player table
    private void createPlayerTable() {
        String sql = """
                CREATE TABLE IF NOT EXISTS users (
                uuid text PRIMARY KEY,
                isCropTrample integer,
                autoReplant integer
                );""";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {

            stmt.execute(sql);
            plugin.getLogger().info("Successfully created player table.");
        } catch (SQLException e) {
            plugin.getLogger().log(Level.WARNING, e.getMessage());
        }
    }

    public void saveToPlayerTable() {
        if(playersSettings.isEmpty()) {
            return;
        }

        String sql = "INSERT INTO users(uuid,isCropTrample,autoReplant) " +
                "VALUES(?,?,?)";

        if(isConnected()) { // these checks aren't really needed, there is no instance when the connection would be dropped, just add a cll to connect() on startup
            playersSettings.forEach((uuid, settings) -> {
                try(PreparedStatement stmt = connection.prepareStatement(sql)) {
                    stmt.setString(1, settings.getUUID());
                    stmt.setBoolean(2, settings.isCropTrample());
                    stmt.setBoolean(3, settings.isAutoReplant());
                    stmt.executeUpdate();
                } catch (SQLException throwables) {
                    updatePlayerInTable(uuid, settings); // i don't love this method as it could silence actual errors in the SQL
                }
            });
        } else {
            this.connect();
            saveToPlayerTable();
        }
    }

    public void updatePlayerInTable(String uuid, UserSettings settings) {
        String sql = "UPDATE users SET isCropTrample = ?, autoReplant = ? WHERE uuid = ?";

        if(isConnected()) {
            try(PreparedStatement stmt = connection.prepareStatement(sql)) {
                // parameter order was wrong causing update to not do anything
                stmt.setBoolean(1, settings.isCropTrample());
                stmt.setBoolean(2, settings.isAutoReplant());
                stmt.setString(3, settings.getUUID());

                stmt.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                plugin.getLogger().log(Level.SEVERE, "Failed to update Player Table in Database.");
            }
        } else {
            this.connect();
            updatePlayerInTable(uuid, settings);
        }
    }

    public void collectAllUsersFromTable() {
        String sql = "SELECT * FROM users";

        if(isConnected()) {
            try(Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

                while(rs.next()) {
                    String uuid = rs.getString("uuid");
                    boolean isCropTrample = rs.getBoolean("isCropTrample");
                    boolean autoReplant = rs.getBoolean("autoReplant");

                    UserSettings settings = new UserSettings(uuid, isCropTrample, autoReplant);
                    playersSettings.put(uuid, settings);
                }

                plugin.getLogger().info("Successfully collected users to hashmap.");
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                plugin.getLogger().log(Level.SEVERE, "Failed to collect all users from table.");
            }
        } else {
            this.connect();
            collectAllUsersFromTable();
        }
    }

    public boolean isConnected() {
        return (connection != null);
    }

    public Connection getConnection() {
        return connection;
    }

    public Map<String, UserSettings> getPlayersSettings() {
        return playersSettings;
    }

    public UserSettings getPlayer(String uuid) {
        return playersSettings.get(uuid);
    }

    public void addPlayer(UserSettings player) {
        playersSettings.put(player.getUUID(), player);
    }

    public void removePlayer(String uuid) {
        playersSettings.remove(uuid);
    }

    public void setPlayersSettings(Map<String, UserSettings> playersSettings) {
        this.playersSettings = playersSettings;
    }
}
