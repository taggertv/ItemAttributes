package net.nunnerycode.bukkit.itemattributes.events;

import net.nunnerycode.bukkit.itemattributes.api.events.ItemAttributesEvent;
import net.nunnerycode.bukkit.itemattributes.api.events.attributes.HealthUpdateEvent;
import net.nunnerycode.bukkit.itemattributes.api.events.attributes.LivingEntityAttributeEvent;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Cancellable;

public class ItemAttributesHealthUpdateEvent extends ItemAttributesEvent implements LivingEntityAttributeEvent,
		HealthUpdateEvent, Cancellable {

	private LivingEntity livingEntity;
	private double previousHealth;
	private double baseHealth;
	private double changeInHealth;
	private boolean cancelled;

	public ItemAttributesHealthUpdateEvent(LivingEntity livingEntity, double previousHealth, double baseHealth,
										   double changeInHealth) {

		this.livingEntity = livingEntity;
		this.previousHealth = previousHealth;
		this.baseHealth = baseHealth;
		this.changeInHealth = changeInHealth;
	}

	@Override
	public boolean isCancelled() {
		return this.cancelled;
	}

	@Override
	public void setCancelled(boolean b) {
		this.cancelled = b;
	}

	@Override
	public double getPreviousHealth() {
		return this.previousHealth;
	}

	@Override
	public double getBaseHealth() {
		return this.baseHealth;
	}

	@Override
	public double getChangeInHealth() {
		return this.changeInHealth;
	}

	@Override
	public void setChangeInHealth(double changeInHealth) {
		this.changeInHealth = changeInHealth;
	}

	@Override
	public LivingEntity getLivingEntity() {
		return livingEntity;
	}
}
