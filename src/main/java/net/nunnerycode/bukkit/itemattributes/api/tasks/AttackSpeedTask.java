package net.nunnerycode.bukkit.itemattributes.api.tasks;

import org.bukkit.entity.LivingEntity;

public interface AttackSpeedTask {

	long getTimeLeft(LivingEntity entity);

	void setTimeLeft(LivingEntity entity, long timeLeft);

}
