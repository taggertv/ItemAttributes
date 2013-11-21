package net.nunnerycode.bukkit.itemattributes.tasks;

import java.util.ArrayList;
import java.util.List;
import net.nunnerycode.bukkit.itemattributes.api.ItemAttributes;
import net.nunnerycode.bukkit.itemattributes.api.attributes.Attribute;
import net.nunnerycode.bukkit.itemattributes.api.tasks.HealthUpdateTask;
import net.nunnerycode.bukkit.itemattributes.events.ItemAttributesHealthUpdateEvent;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.MetadataValue;

public final class ItemAttributesHealthUpdateTask implements HealthUpdateTask, Runnable {

	private final ItemAttributes plugin;

	public ItemAttributesHealthUpdateTask(ItemAttributes plugin) {
		this.plugin = plugin;
	}

	@Override
	public void run() {
		Attribute healthAttribute = getPlugin().getSettingsManager().getAttribute("HEALTH");
		for (World w : Bukkit.getWorlds()) {
			for (Entity e : w.getEntities()) {
				if (e instanceof Player) {
					Player player = (Player) e;
					double d = getPlugin().getAttributeHandler().getAttributeValueFromEntity(player, healthAttribute);
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
					getPlugin().getAttributeHandler().playAttributeSounds(player.getEyeLocation(), healthAttribute);
				} else if (e instanceof LivingEntity) {
					LivingEntity entity = (LivingEntity) e;
					double d = getPlugin().getAttributeHandler().getAttributeValueFromEntity(entity, healthAttribute);
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
					getPlugin().getAttributeHandler().playAttributeSounds(entity.getEyeLocation(), healthAttribute);
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
