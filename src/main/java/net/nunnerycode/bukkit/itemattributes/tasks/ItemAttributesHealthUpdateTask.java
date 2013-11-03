package net.nunnerycode.bukkit.itemattributes.tasks;

import java.util.ArrayList;
import java.util.List;
import net.nunnerycode.bukkit.itemattributes.ItemAttributesPlugin;
import net.nunnerycode.bukkit.itemattributes.api.ItemAttributes;
import net.nunnerycode.bukkit.itemattributes.api.tasks.HealthUpdateTask;
import net.nunnerycode.bukkit.itemattributes.events.ItemAttributesHealthUpdateEvent;
import net.nunnerycode.bukkit.itemattributes.utils.ItemAttributesParseUtil;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.scheduler.BukkitRunnable;

public final class ItemAttributesHealthUpdateTask extends BukkitRunnable implements HealthUpdateTask {

	private final ItemAttributesPlugin plugin;

	public ItemAttributesHealthUpdateTask(ItemAttributesPlugin plugin) {
		this.plugin = plugin;
		this.runTaskTimer(plugin, 20L * getPlugin().getSettingsManager().getSecondsBetweenHealthUpdates(),
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
						d += ItemAttributesParseUtil.getDouble(getItemStackLore(is), getPlugin().getSettingsManager()
								.getHealthFormat());
					}
					d += ItemAttributesParseUtil.getDouble(getItemStackLore(player.getItemInHand()),
							getPlugin().getSettingsManager().getHealthFormat());
					double currentHealth = player.getHealth();
					double baseMaxHealth = getPlugin().getSettingsManager().getBasePlayerHealth();

					ItemAttributesHealthUpdateEvent healthUpdateEvent = new ItemAttributesHealthUpdateEvent(player,
							player.getMaxHealth(), baseMaxHealth, d);
					Bukkit.getPluginManager().callEvent(healthUpdateEvent);

					if (healthUpdateEvent.isCancelled()) {
						return;
					}

					player.setMaxHealth(Math.max(healthUpdateEvent.getBaseHealth() + healthUpdateEvent
							.getChangeInHealth(), 1));
					player.setHealth(Math.min(Math.max(currentHealth, 0), player.getMaxHealth()));
					player.setHealthScale(player.getMaxHealth());
				} else if (e instanceof LivingEntity) {
					LivingEntity entity = (LivingEntity) e;
					ItemStack[] armorContents = entity.getEquipment().getArmorContents();
					double d = 0.0;
					for (ItemStack is : armorContents) {
						d += ItemAttributesParseUtil.getDouble(getItemStackLore(is), getPlugin().getSettingsManager()
								.getHealthFormat());
					}
					d += ItemAttributesParseUtil.getDouble(getItemStackLore(entity.getEquipment().getItemInHand()),
							getPlugin().getSettingsManager().getHealthFormat());
					double currentHealth = entity.getHealth();
					entity.resetMaxHealth();
					double baseMaxHealth = entity.getMaxHealth();
					if (entity.hasMetadata("itemattributes.basehealth")) {
						List<MetadataValue> metadataValueList = entity.getMetadata("itemattributes.basehealth");
						for (MetadataValue mv : metadataValueList) {
							if (mv.getOwningPlugin().equals(getPlugin())) {
								baseMaxHealth = mv.asDouble();
								break;
							}
						}
					}

					ItemAttributesHealthUpdateEvent healthUpdateEvent = new ItemAttributesHealthUpdateEvent(entity,
							entity.getMaxHealth(), baseMaxHealth, d);
					Bukkit.getPluginManager().callEvent(healthUpdateEvent);

					if (healthUpdateEvent.isCancelled()) {
						return;
					}

					entity.setMaxHealth(Math.max(healthUpdateEvent.getBaseHealth() + healthUpdateEvent
							.getChangeInHealth(), 1));
					entity.setHealth(Math.min(Math.max(currentHealth, 0), entity.getMaxHealth()));
				}
			}
		}
	}

	@Override
	public ItemAttributes getPlugin() {
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
