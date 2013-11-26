package net.nunnerycode.bukkit.itemattributes.events;

import net.nunnerycode.bukkit.itemattributes.api.events.ItemAttributesEvent;
import net.nunnerycode.bukkit.itemattributes.api.events.attributes.CriticalStrikeEvent;
import net.nunnerycode.bukkit.itemattributes.api.events.attributes.LivingEntityAttributeEvent;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Cancellable;

public class ItemAttributesCriticalStrikeEvent extends ItemAttributesEvent implements LivingEntityAttributeEvent,
		CriticalStrikeEvent, Cancellable {

	private LivingEntity livingEntity;
	private LivingEntity target;
	private double criticalRate;
	private double criticalDamage;
	private boolean cancelled;

	public ItemAttributesCriticalStrikeEvent(LivingEntity livingEntity, LivingEntity target, double criticalRate,
											 double criticalDamage) {
		this.livingEntity = livingEntity;
		this.target = target;
		this.criticalRate = criticalRate;
		this.criticalDamage = criticalDamage;
	}

	@Override
	public double getCriticalRate() {
		return criticalRate;
	}

	@Override
	public double getCriticalDamage() {
		return criticalDamage;
	}

	@Override
	public LivingEntity getTarget() {
		return target;
	}

	@Override
	public LivingEntity getLivingEntity() {
		return livingEntity;
	}

	@Override
	public boolean isCancelled() {
		return cancelled;
	}

	@Override
	public void setCancelled(boolean b) {
		cancelled = b;
	}
}
