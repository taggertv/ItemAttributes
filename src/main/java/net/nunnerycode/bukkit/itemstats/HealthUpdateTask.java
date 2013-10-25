package net.nunnerycode.bukkit.itemstats;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.scheduler.BukkitRunnable;

public final class HealthUpdateTask extends BukkitRunnable {

	private final ItemStatsPlugin plugin;

	public HealthUpdateTask(ItemStatsPlugin plugin) {
		this.plugin = plugin;
		this.runTaskTimer(getPlugin(), 20L * getPlugin().getSettingsManager().getSecondsBetweenHealthUpdates(),
				20L * getPlugin().getSettingsManager().getSecondsBetweenHealthUpdates());
	}

	@Override
	public void run() {
		for (World w : Bukkit.getWorlds()) {
			for (Entity e : w.getEntities()) {
				if (e instanceof Player) {
					Player player = (Player) e;
					ItemStack[] armorContents = player.getEquipment().getArmorContents();
					double d = 0.0;
					for (ItemStack is : armorContents) {
						d += ParseUtil.getDouble(getItemStackLore(is), getPlugin().getSettingsManager()
								.getHealthFormat());
					}
					d += ParseUtil.getDouble(getItemStackLore(player.getItemInHand()),
							getPlugin().getSettingsManager().getHealthFormat());
					double currentHealth = player.getHealth();
					double baseMaxHealth = getPlugin().getSettingsManager().getBasePlayerHealth();
					player.setMaxHealth(Math.max(baseMaxHealth + d, 1));
					player.setHealth(Math.min(Math.max(currentHealth, 0), player.getMaxHealth()));
					player.setHealthScale(player.getMaxHealth());
				} else if (e instanceof LivingEntity) {
					LivingEntity entity = (LivingEntity) e;
					ItemStack[] armorContents = entity.getEquipment().getArmorContents();
					double d = 0.0;
					for (ItemStack is : armorContents) {
						d += ParseUtil.getDouble(getItemStackLore(is), getPlugin().getSettingsManager()
								.getHealthFormat());
					}
					d += ParseUtil.getDouble(getItemStackLore(entity.getEquipment().getItemInHand()),
							getPlugin().getSettingsManager().getHealthFormat());
					double currentHealth = entity.getHealth();
					entity.resetMaxHealth();
					double baseMaxHealth = entity.getMaxHealth();
					if (entity.hasMetadata("itemstats.basehealth")) {
						List<MetadataValue> metadataValueList = entity.getMetadata("itemstats.basehealth");
						for (MetadataValue mv : metadataValueList) {
							if (mv.getOwningPlugin().equals(getPlugin())) {
								baseMaxHealth = mv.asDouble();
								break;
							}
						}
					}
					entity.setMaxHealth(Math.max(baseMaxHealth + d, 1));
					entity.setHealth(Math.min(Math.max(currentHealth, 0), entity.getMaxHealth()));
				}
			}
		}
	}

	public ItemStatsPlugin getPlugin() {
		return plugin;
	}

	public List<String> getItemStackLore(ItemStack itemStack) {
		List<String> lore = new ArrayList<String>();
		if (itemStack != null && itemStack.hasItemMeta() && itemStack.getItemMeta().hasLore()) {
			lore.addAll(itemStack.getItemMeta().getLore());
		}
		return lore;
	}
}
