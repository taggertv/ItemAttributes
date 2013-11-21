package net.nunnerycode.bukkit.itemattributes.tasks;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import net.nunnerycode.bukkit.itemattributes.api.ItemAttributes;
import net.nunnerycode.bukkit.itemattributes.api.attributes.Attribute;
import net.nunnerycode.bukkit.itemattributes.api.tasks.AttackSpeedTask;
import org.bukkit.entity.LivingEntity;
import org.bukkit.scheduler.BukkitRunnable;

public final class ItemAttributesAttackSpeedTask extends BukkitRunnable implements AttackSpeedTask {

	private final ItemAttributes plugin;
	private final Map<LivingEntity, Long> chargeTimer;

	public ItemAttributesAttackSpeedTask(ItemAttributes plugin) {
		this.plugin = plugin;
		chargeTimer = new HashMap<LivingEntity, Long>();
	}

	@Override
	public long getTimeLeft(LivingEntity entity) {
		if (chargeTimer.containsKey(entity)) {
			return chargeTimer.get(entity);
		}
		return 0;
	}

	@Override
	public void setTimeLeft(LivingEntity entity, long timeLeft) {
		chargeTimer.put(entity, timeLeft);
	}

	@Override
	public ItemAttributes getPlugin() {
		return plugin;
	}

	@Override
	public void run() {
		Iterator<Map.Entry<LivingEntity, Long>> iterator = chargeTimer.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<LivingEntity, Long> entry = iterator.next();
			if (!entry.getKey().isValid()) {
				iterator.remove();
				continue;
			}
			Attribute attribute = getPlugin().getSettingsManager().getAttribute("ATTACK SPEED");
			if (entry.getValue() < Math.round(attribute.getBaseValue())) {
				LivingEntity entity = entry.getKey();
				getPlugin().getAttributeHandler().playAttributeEffects(entity.getEyeLocation(), attribute);
				getPlugin().getAttributeHandler().playAttributeSounds(entity.getEyeLocation(), attribute);
				iterator.remove();
			} else {
				entry.setValue(entry.getValue() - Math.round(attribute.getBaseValue()));
			}
		}
	}
}
