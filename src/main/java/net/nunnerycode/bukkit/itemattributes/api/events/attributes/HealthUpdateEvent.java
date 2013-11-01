package net.nunnerycode.bukkit.itemattributes.api.events.attributes;

public interface HealthUpdateEvent extends PlayerAttributeEvent {

	double getPreviousHealth();

	double getBaseHealth();

	double getNewHealth();

}
