package net.nunnerycode.bukkit.itemattributes.tasks;

import java.util.ArrayList;
import java.util.List;
import net.nunnerycode.bukkit.itemattributes.ItemAttributesPlugin;
import net.nunnerycode.bukkit.itemattributes.api.ItemAttributes;
import net.nunnerycode.bukkit.itemattributes.api.attributes.Attribute;
import net.nunnerycode.bukkit.itemattributes.api.tasks.HealthUpdateTask;
import net.nunnerycode.bukkit.itemattributes.events.ItemAttributesHealthUpdateEvent;
import net.nunnerycode.bukkit.itemattributes.utils.ItemAttributesParseUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
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
		Attribute healthAttribute = getPlugin().getSettingsManager().getAttribute("HEALTH");
		for (World w : Bukkit.getWorlds()) {
			for (Entity e : w.getEntities()) {
				if (e instanceof Player) {
					Player player = (Player) e;
					ItemStack[] armorContents = player.getEquipment().getArmorContents();
					double d = 0.0;
					for (ItemStack is : armorContents) {
						d += ItemAttributesParseUtil.getValue(getItemStackLore(is), healthAttribute);
					}
					d += ItemAttributesParseUtil.getValue(getItemStackLore(player.getItemInHand()), healthAttribute);
					double currentHealth = player.getHealth();
					double baseMaxHealth = getPlugin().getSettingsManager().getBasePlayerHealth();

					ItemAttributesHealthUpdateEvent healthUpdateEvent = new ItemAttributesHealthUpdateEvent(player,
							player.getMaxHealth(), baseMaxHealth, d);
					Bukkit.getPluginManager().callEvent(healthUpdateEvent);

					if (healthUpdateEvent.isCancelled()) {
						return;
					}

					player.setHealth(Math.min(Math.max((healthUpdateEvent.getBaseHealth() + healthUpdateEvent
							.getChangeInHealth()) / 2, 1), player.getMaxHealth()));
					player.setMaxHealth(Math.max(healthUpdateEvent.getBaseHealth() + healthUpdateEvent
							.getChangeInHealth(), 1));
					player.setHealth(Math.min(Math.max(currentHealth, 0), player.getMaxHealth()));
					player.setHealthScale(player.getMaxHealth());
					playAttributeSounds(player.getEyeLocation(), healthAttribute);
				} else if (e instanceof LivingEntity) {
					LivingEntity entity = (LivingEntity) e;
					ItemStack[] armorContents = entity.getEquipment().getArmorContents();
					double d = 0.0;
					for (ItemStack is : armorContents) {
						d += ItemAttributesParseUtil.getValue(getItemStackLore(is), healthAttribute);
					}
					d += ItemAttributesParseUtil.getValue(getItemStackLore(entity.getEquipment().getItemInHand()),
							healthAttribute);
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

					entity.setHealth(Math.min(Math.max((healthUpdateEvent.getBaseHealth() + healthUpdateEvent
							.getChangeInHealth()) / 2, 1), entity.getMaxHealth()));
					entity.setMaxHealth(Math.max(healthUpdateEvent.getBaseHealth() + healthUpdateEvent
							.getChangeInHealth(), 1));
					entity.setHealth(Math.min(Math.max(currentHealth, 0), entity.getMaxHealth()));
					playAttributeSounds(entity.getEyeLocation(), healthAttribute);
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

	private void playAttributeSounds(Location location, Attribute... attributes) {
		for (Attribute attribute : attributes) {
			location.getWorld().playSound(location, attribute.getSound(), 1F, 1F);
		}
	}

}
