package me.adelemphii.kalliergeia.utils.gui;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import me.adelemphii.kalliergeia.Kalliergeia;
import me.adelemphii.kalliergeia.utils.ChatUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class SettingsMenu {

    private final Kalliergeia plugin;
    private final Player player;

    public SettingsMenu(Kalliergeia plugin, Player player) {
        this.plugin = plugin;
        this.player = player;
    }

    // I yoinked this from my WaterWalking trial plugin
    // it's not that good, but I'm not well-versed with IF
    // this method is used to create the settings gui
    public void openGUI() {

        ChestGui gui = new ChestGui(1, ChatColor.RED + "Kalliergeia Settings");

        // Makes sure players can't take from inventory
        gui.setOnGlobalClick(event -> event.setCancelled(true));

        StaticPane pane = new StaticPane(0, 0, 9, 1);

        ItemStack cropTrampleEnabled = createNewButton(Material.LIME_WOOL, "&aCrop Trample Enabled");
        ItemStack cropTrampleDisabled = createNewButton(Material.RED_WOOL, "&cCrop Trample Disabled");

        ItemStack autoReplantEnabled = createNewButton(Material.LIME_WOOL, "&aAuto Replant Enabled");
        ItemStack autoReplantDisabled = createNewButton(Material.RED_WOOL, "&cAuto Replant Disabled");

        if(plugin.getSQLManager().getPlayer(player.getUniqueId().toString()).isCropTrample()) {
            pane.addItem(new GuiItem(cropTrampleEnabled, event -> {
                cropTrampleEnabled.setType(Material.RED_WOOL);

                ItemMeta cropTrampleEnabledItemMeta = cropTrampleEnabled.getItemMeta();
                cropTrampleEnabledItemMeta.setDisplayName(ChatColor.RED + "CROP TRAMPLE DISABLED");
                cropTrampleEnabled.setItemMeta(cropTrampleEnabledItemMeta);

                plugin.getSQLManager().getPlayer(player.getUniqueId().toString()).setCropTrample(false);

                ChatUtils.sendMessage(player, "Crop trampling has been disabled.");
                gui.update();
                player.closeInventory();
            }), 0, 0);
        } else {
            pane.addItem(new GuiItem(cropTrampleDisabled, event -> {
                cropTrampleDisabled.setType(Material.LIME_WOOL);

                ItemMeta cropTrampleDisabledItemMeta = cropTrampleDisabled.getItemMeta();
                assert cropTrampleDisabledItemMeta != null;
                cropTrampleDisabledItemMeta.setDisplayName(ChatColor.GREEN + "CROP TRAMPLE ENABLED");
                cropTrampleDisabled.setItemMeta(cropTrampleDisabledItemMeta);

                plugin.getSQLManager().getPlayer(player.getUniqueId().toString()).setCropTrample(true);

                ChatUtils.sendMessage(player, "Crop trampling has been enabled.");
                gui.update();
                player.closeInventory();
            }), 0, 0);
        }

        if(plugin.getSQLManager().getPlayer(player.getUniqueId().toString()).isAutoReplant()) {
            pane.addItem(new GuiItem(autoReplantEnabled, event -> {
                autoReplantEnabled.setType(Material.RED_WOOL);

                ItemMeta autoReplantEnabledItemMeta = autoReplantEnabled.getItemMeta();
                autoReplantEnabledItemMeta.setDisplayName(ChatColor.RED + "AUTO REPLANT DISABLED");
                autoReplantEnabled.setItemMeta(autoReplantEnabledItemMeta);

                plugin.getSQLManager().getPlayer(player.getUniqueId().toString()).setAutoReplant(false);

                ChatUtils.sendMessage(player, "Auto replanting has been disabled.");
                gui.update();
                player.closeInventory();
            }), 1, 0);
        } else {
            pane.addItem(new GuiItem(autoReplantDisabled, event -> {
                autoReplantDisabled.setType(Material.LIME_WOOL);

                ItemMeta autoReplantDisabledItemMeta = autoReplantDisabled.getItemMeta();
                assert autoReplantDisabledItemMeta != null;
                autoReplantDisabledItemMeta.setDisplayName(ChatColor.GREEN + "AUTO REPLANT ENABLED");
                autoReplantDisabled.setItemMeta(autoReplantDisabledItemMeta);

                plugin.getSQLManager().getPlayer(player.getUniqueId().toString()).setAutoReplant(true);

                ChatUtils.sendMessage(player, "Auto replanting has been enabled.");
                gui.update();
                player.closeInventory();
            }), 1, 0);
        }

        gui.addPane(pane);

        gui.show(player);

    }

    private ItemStack createNewButton(Material material, String name) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();

        assert meta != null;
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
        item.setItemMeta(meta);
        return item;
    }

}
