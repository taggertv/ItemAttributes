package net.nunnerycode.bukkit.itemattributes.api.events.attributes;

public interface HealthUpdateEvent extends LivingEntityAttributeEvent {

	double getPreviousHealth();

	double getBaseHealth();

	double getChangeInHealth();

	void setChangeInHealth(double changeInHealth);

}
