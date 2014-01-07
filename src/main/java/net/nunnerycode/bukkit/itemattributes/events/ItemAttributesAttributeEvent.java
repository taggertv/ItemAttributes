package net.nunnerycode.bukkit.itemattributes.events;

import net.nunnerycode.bukkit.itemattributes.api.attributes.Attribute;
import net.nunnerycode.bukkit.itemattributes.api.attributes.AttributeValue;
import net.nunnerycode.bukkit.itemattributes.api.events.ItemAttributesEvent;
import net.nunnerycode.bukkit.itemattributes.api.events.attributes.LivingEntityAttributeEvent;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Cancellable;

public class ItemAttributesAttributeEvent extends ItemAttributesEvent implements LivingEntityAttributeEvent,
		Cancellable {

	private final LivingEntity livingEntity;
	private final Attribute attribute;
	private AttributeValue attributeValue;
	private boolean cancelled;

	public ItemAttributesAttributeEvent(LivingEntity livingEntity, Attribute attribute,
										AttributeValue attributeValue) {
		this.livingEntity = livingEntity;
		this.attribute = attribute;
		this.attributeValue = attributeValue;
	}

	@Override
	public LivingEntity getLivingEntity() {
		return livingEntity;
	}

	@Override
	public Attribute getAttribute() {
		return attribute;
	}

	@Override
	public AttributeValue getAttributeValue() {
		return attributeValue;
	}

	@Override
	public void setAttributeValue(AttributeValue value) {
		this.attributeValue = value;
	}

	@Override
	public boolean isCancelled() {
		return cancelled;
	}

	@Override
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}
}
