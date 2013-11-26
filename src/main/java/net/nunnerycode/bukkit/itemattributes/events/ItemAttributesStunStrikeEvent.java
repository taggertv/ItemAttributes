package net.nunnerycode.bukkit.itemattributes.events;

import net.nunnerycode.bukkit.itemattributes.api.events.ItemAttributesEvent;
import net.nunnerycode.bukkit.itemattributes.api.events.attributes.LivingEntityAttributeEvent;
import net.nunnerycode.bukkit.itemattributes.api.events.attributes.StunStrikeEvent;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Cancellable;

public class ItemAttributesStunStrikeEvent extends ItemAttributesEvent implements LivingEntityAttributeEvent,
		StunStrikeEvent, Cancellable {

	private LivingEntity livingEntity;
	private LivingEntity target;
	private double stunRate;
	private int stunLength;
	private boolean cancelled;

	public ItemAttributesStunStrikeEvent(LivingEntity livingEntity, LivingEntity target, double stunRate,
										 int stunLength) {
		this.livingEntity = livingEntity;
		this.target = target;
		this.stunRate = stunRate;
		this.stunLength = stunLength;
	}

	@Override
	public double getStunRate() {
		return stunRate;
	}

	@Override
	public int getStunLength() {
		return stunLength;
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
