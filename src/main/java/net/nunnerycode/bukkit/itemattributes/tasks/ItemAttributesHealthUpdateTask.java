package net.nunnerycode.bukkit.itemattributes.tasks;

import java.util.List;
import net.nunnerycode.bukkit.itemattributes.api.ItemAttributes;
import net.nunnerycode.bukkit.itemattributes.api.attributes.Attribute;
import net.nunnerycode.bukkit.itemattributes.api.tasks.HealthUpdateTask;
import net.nunnerycode.bukkit.itemattributes.attributes.ItemAttributeValue;
import net.nunnerycode.bukkit.itemattributes.events.ItemAttributesAttributeEvent;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.metadata.MetadataValue;

public final class ItemAttributesHealthUpdateTask implements HealthUpdateTask, Runnable {

	private final ItemAttributes plugin;

	public ItemAttributesHealthUpdateTask(ItemAttributes plugin) {
		this.plugin = plugin;
	}

	@Override
	public void run() {
		Attribute healthAttribute = getPlugin().getSettingsManager().getAttribute("HEALTH");
		if (!healthAttribute.isEnabled()) {
			return;
		}
		for (World w : Bukkit.getWorlds()) {
			for (Entity e : w.getEntities()) {
				if (e instanceof Player) {
					Player player = (Player) e;
					double d = getPlugin().getAttributeHandler().getAttributeValueFromEntity(player, healthAttribute);
					double currentHealth = player.getHealth();
					double baseMaxHealth = getPlugin().getSettingsManager().getBasePlayerHealth();

					ItemAttributesAttributeEvent iaae = new ItemAttributesAttributeEvent(player, player,
							healthAttribute, new ItemAttributeValue(d));
					Bukkit.getPluginManager().callEvent(iaae);

					if (iaae.isCancelled()) {
						return;
					}

					player.setMaxHealth(Math.max(healthAttribute.getBaseValue() + iaae.getAttributeValue().asDouble(),
							1));
					player.setHealth(Math.min(Math.max(currentHealth, 0), player.getMaxHealth()));
					player.setHealthScale(player.getMaxHealth());
					getPlugin().getAttributeHandler().playAttributeSounds(player.getEyeLocation(), healthAttribute);
					getPlugin().getAttributeHandler().playAttributeEffects(player.getEyeLocation(), healthAttribute);
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

					ItemAttributesAttributeEvent iaae = new ItemAttributesAttributeEvent(entity, entity,
							healthAttribute, new ItemAttributeValue(d));
					Bukkit.getPluginManager().callEvent(iaae);

					if (iaae.isCancelled()) {
						return;
					}

					entity.setHealth(Math.min(Math.max((iaae.getAttribute().getBaseValue() + iaae.getAttributeValue()
							.asDouble()) / 2, 1), entity.getMaxHealth()));
					entity.setMaxHealth(Math.max(iaae.getAttribute().getBaseValue() + iaae.getAttributeValue()
							.asDouble(), 1));
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

}
