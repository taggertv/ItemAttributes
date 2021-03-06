package net.nunnerycode.bukkit.itemattributes.tasks;

import net.nunnerycode.bukkit.itemattributes.api.ItemAttributes;
import net.nunnerycode.bukkit.itemattributes.api.attributes.Attribute;
import net.nunnerycode.bukkit.itemattributes.api.tasks.AttackSpeedTask;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public final class ItemAttributesAttackSpeedTask extends BukkitRunnable implements AttackSpeedTask {

	private final ItemAttributes plugin;
	private final Map<LivingEntity, Long> chargeTimer;

	public ItemAttributesAttackSpeedTask(ItemAttributes plugin) {
		this.plugin = plugin;
		chargeTimer = new HashMap<LivingEntity, Long>();
	}

	@Override
	public long getTimeLeft(LivingEntity entity) {
		Attribute attribute = getPlugin().getSettingsManager().getAttribute("ATTACK SPEED");
		if (!attribute.isEnabled()) {
			return 0;
		}
		if (entity instanceof Player) {
			if (!attribute.isAffectsPlayers()) {
				return 0;
			}
		} else if (!attribute.isAffectsMobs()) {
			return 0;
		}
		if (chargeTimer.containsKey(entity)) {
			return chargeTimer.get(entity);
		}
		return 0;
	}

	@Override
	public void setTimeLeft(LivingEntity entity, long timeLeft) {
		Attribute attribute = getPlugin().getSettingsManager().getAttribute("ATTACK SPEED");
		if (!attribute.isEnabled()) {
			return;
		}
		if (entity instanceof Player) {
			if (!attribute.isAffectsPlayers()) {
				return;
			}
		} else if (!attribute.isAffectsMobs()) {
			return;
		}
		chargeTimer.put(entity, timeLeft);
	}

	@Override
	public ItemAttributes getPlugin() {
		return plugin;
	}

	@Override
	public void run() {
		Attribute attackSpeedAttribute = getPlugin().getSettingsManager().getAttribute("ATTACK SPEED");
		if (!attackSpeedAttribute.isEnabled()) {
			return;
		}
		Iterator<Map.Entry<LivingEntity, Long>> iterator = chargeTimer.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<LivingEntity, Long> entry = iterator.next();
			if (!entry.getKey().isValid()) {
				iterator.remove();
				continue;
			}

			if (entry.getKey() instanceof Player) {
				if (!attackSpeedAttribute.isAffectsPlayers()) {
					continue;
				}
				if (entry.getValue() < 1) {
					LivingEntity entity = entry.getKey();
					getPlugin().getAttributeHandler().playAttributeEffects(entity.getEyeLocation(), attackSpeedAttribute);
					getPlugin().getAttributeHandler().playAttributeSounds(entity.getEyeLocation(), attackSpeedAttribute);
					iterator.remove();
				} else {
					entry.setValue(entry.getValue() - 1);
				}
			} else {
				if (!attackSpeedAttribute.isAffectsMobs()) {
					continue;
				}
				if (entry.getValue() < 1) {
					LivingEntity entity = entry.getKey();
					getPlugin().getAttributeHandler().playAttributeEffects(entity.getEyeLocation(), attackSpeedAttribute);
					getPlugin().getAttributeHandler().playAttributeSounds(entity.getEyeLocation(), attackSpeedAttribute);
					iterator.remove();
				} else {
					entry.setValue(entry.getValue() - 1);
				}
			}
		}
	}
}
