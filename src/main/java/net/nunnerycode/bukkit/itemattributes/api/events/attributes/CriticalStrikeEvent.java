package net.nunnerycode.bukkit.itemattributes.api.events.attributes;

import org.bukkit.entity.LivingEntity;

public interface CriticalStrikeEvent {

	double getCriticalRate();

	double getCriticalDamage();

	LivingEntity getTarget();

}
