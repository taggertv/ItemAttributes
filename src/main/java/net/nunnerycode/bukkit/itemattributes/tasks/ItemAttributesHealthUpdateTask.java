package net.nunnerycode.bukkit.itemattributes.tasks;

import net.nunnerycode.bukkit.itemattributes.api.ItemAttributes;
import net.nunnerycode.bukkit.itemattributes.api.attributes.Attribute;
import net.nunnerycode.bukkit.itemattributes.api.tasks.HealthUpdateTask;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public final class ItemAttributesHealthUpdateTask extends BukkitRunnable implements HealthUpdateTask{

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
					if (!healthAttribute.isAffectsPlayers()) {
						continue;
					}
					Player player = (Player) e;
					double d = getPlugin().getAttributeHandler().getAttributeValueFromEntity(player, healthAttribute);
					double currentHealth = player.getHealth();

					player.setMaxHealth(Math.max(healthAttribute.getPlayersBaseValue() + d, 1));
					player.setHealth(Math.min(Math.max(currentHealth, 0), player.getMaxHealth()));
					player.setHealthScale(player.getMaxHealth());
					getPlugin().getAttributeHandler().playAttributeSounds(player.getEyeLocation(), healthAttribute);
					getPlugin().getAttributeHandler().playAttributeEffects(player.getEyeLocation(), healthAttribute);
				} else if (e instanceof LivingEntity) {
					if (!healthAttribute.isAffectsMobs()) {
						continue;
					}
					LivingEntity entity = (LivingEntity) e;
					double d = getPlugin().getAttributeHandler().getAttributeValueFromEntity(entity, healthAttribute);
					double currentHealth = entity.getHealth();
					double baseMaxHealth = healthAttribute.getMobsBaseValue();
					if (entity.hasMetadata("itemattributes.basehealth")) {
						List<MetadataValue> metadataValueList = entity.getMetadata("itemattributes.basehealth");
						for (MetadataValue mv : metadataValueList) {
							if (mv.getOwningPlugin().equals(getPlugin())) {
								baseMaxHealth = mv.asDouble();
								break;
							}
						}
					} else {
						entity.resetMaxHealth();
						baseMaxHealth = entity.getMaxHealth();
					}
					entity.setHealth(1);
					entity.setMaxHealth(baseMaxHealth + d);
					entity.setHealth(Math.max(1, Math.min(currentHealth, entity.getMaxHealth())));
				}
			}
		}
	}

	@Override
	public ItemAttributes getPlugin() {
		return plugin;
	}

}
