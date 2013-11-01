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
	private double newHealth;
	private boolean cancelled;

	public ItemAttributesHealthUpdateEvent(LivingEntity livingEntity, double previousHealth, double baseHealth,
										   double newHealth) {

		this.livingEntity = livingEntity;
		this.previousHealth = previousHealth;
		this.baseHealth = baseHealth;
		this.newHealth = newHealth;
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
	public double getNewHealth() {
		return this.newHealth;
	}

	@Override
	public void setNewHealth(double newHealth) {
		this.newHealth = newHealth;
	}

	@Override
	public LivingEntity getLivingEntity() {
		return livingEntity;
	}
}
