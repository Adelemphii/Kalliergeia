package me.adelemphii.kalliergeia.events;

import me.adelemphii.kalliergeia.Kalliergeia;
import me.adelemphii.kalliergeia.utils.enums.CropTypes;
import me.adelemphii.kalliergeia.utils.enums.HoeTypes;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;

import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

public class HoeFarmingListener implements Listener {

    Kalliergeia plugin;

    public HoeFarmingListener(Kalliergeia plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onHoeFarming(PlayerInteractEvent event) {

        if(event.getHand() == EquipmentSlot.OFF_HAND) return;

        // Check if the player is breaking the block
        if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
            Block block = event.getClickedBlock();
            ItemStack item = event.getPlayer().getInventory().getItemInMainHand();

            if(block == null) return;

            // If the block has age, it's a block we need
            if (block.getBlockData() instanceof Ageable age) {

                // we don't care about blocks that are not fully grown, since they don't yield any crops
                if(age.getAge() != age.getMaximumAge()) return;

                if(HoeTypes.getType(item.getType()) == HoeTypes.HAND) {
                    block.setType(Material.AIR); // hands don't have any yield
                } else {

                    // get the fortune level for the hoe, if not found, set it to 0
                    // then get the crop type for the block, if not found, return
                    // for each itemstack in Block#getDrops() compare its type to the crop type and
                    // if it matches, set the yield to the hoe level yield
                    // after, calculate the seed yield approximately the same way Minecraft does it (binomial distribution)
                    // then set the block to air and drop the seeds/yield if autoReplant is disabled

                    // if autoReplant is enabled, set the block's age to 1 and drop the seeds/yield
                    // also damages the hoe

                    int yield = HoeTypes.getType(item.getType()).getYield();

                    int fortuneLevel = 0;
                    if(item.getEnchantments().containsKey(Enchantment.LOOT_BONUS_BLOCKS)) {
                        fortuneLevel = item.getEnchantments().get(Enchantment.LOOT_BONUS_BLOCKS);
                    }

                    int finalFortuneLevel = fortuneLevel;

                    AtomicBoolean hasSeedHappened = new AtomicBoolean(false);
                    block.getDrops().forEach(itemStack -> {
                        CropTypes cropType = CropTypes.matchType(itemStack.getType());
                        if(cropType == null) return;

                        if(itemStack.getType() == cropType.getCropType()) {
                            itemStack.setAmount(yield);
                        }
                        if(itemStack.getType() == cropType.getSeedType() && !hasSeedHappened.get()) {
                            int amount = CropTypes.getSeedDropAmount(cropType, finalFortuneLevel);

                            itemStack.setAmount(amount);
                            hasSeedHappened.set(true);
                        }
                        // java.lang.IllegalArgumentException: Cannot drop air
                        // idk anymore https://paste.md-5.net/diyajatuto.sql
                        if(itemStack.getType() != Material.AIR) {
                            block.getWorld().dropItemNaturally(block.getLocation(), itemStack);
                        }
                    });
                    if(plugin.getSQLManager().getPlayer(event.getPlayer().getUniqueId().toString()).isAutoReplant()) {
                        age.setAge(1);
                        block.setBlockData(age);
                    } else {
                        block.setType(Material.AIR);
                    }
                }
                damageTool(item, 1);
                block.getWorld().playSound(block.getLocation(), Sound.BLOCK_CROP_BREAK, 1, 1);
                event.setCancelled(true);
            }
        }
    }
    // check if the item meta is an instance of damageable then damage the tool
    // smth aint right with this

    // takes into consideration if the hoe is enchanted with unbreaking or not, using a similar method as Minecraft
    private void damageTool(ItemStack item, int amount) {
        if(item.getItemMeta() instanceof Damageable damageable) {

            int enchantLevel = 0;
            if(item.getEnchantments().containsKey(Enchantment.DURABILITY)) {
                enchantLevel = item.getEnchantments().get(Enchantment.DURABILITY);
            }

            if(enchantLevel > 0) {
                int newDamage = damageable.getDamage() + amount;

                Random rand = new Random();
                int n = rand.nextInt(100) + 1;

                switch(enchantLevel) {
                    case 1:
                        if(n <= 50) {
                            damageable.setDamage(newDamage);
                        }
                        break;
                    case 2:
                        if(n <= 33) {
                            damageable.setDamage(newDamage);
                        }
                        break;
                    case 3:
                        if(n <= 25) {
                            damageable.setDamage(newDamage);
                        }
                        break;
                    default:
                        damageable.setDamage(newDamage);
                }
                item.setItemMeta(damageable);
            }

            if(damageable.getDamage() > item.getType().getMaxDurability()) {
                item.setAmount(0);
            }
            damageable.setDamage(damageable.getDamage() + amount);
            item.setItemMeta(damageable);
        }
    }
}
