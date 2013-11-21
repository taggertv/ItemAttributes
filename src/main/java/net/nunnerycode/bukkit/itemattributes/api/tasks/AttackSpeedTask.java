package net.nunnerycode.bukkit.itemattributes.api.tasks;

import net.nunnerycode.bukkit.itemattributes.api.ItemAttributes;
import org.bukkit.entity.LivingEntity;

public interface AttackSpeedTask {

	long getTimeLeft(LivingEntity entity);

	void setTimeLeft(LivingEntity entity, long timeLeft);

	ItemAttributes getPlugin();

}
