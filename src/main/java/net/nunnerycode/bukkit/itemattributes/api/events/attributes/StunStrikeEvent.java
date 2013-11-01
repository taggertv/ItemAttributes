package net.nunnerycode.bukkit.itemattributes.api.events.attributes;

import org.bukkit.entity.LivingEntity;

public interface StunStrikeEvent {

	double getStunRate();

	int getStunLength();

	LivingEntity getTarget();

}
