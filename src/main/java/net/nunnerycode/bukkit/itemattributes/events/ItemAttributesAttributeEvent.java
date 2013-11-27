package net.nunnerycode.bukkit.itemattributes.events;

import net.nunnerycode.bukkit.itemattributes.api.attributes.Attribute;
import net.nunnerycode.bukkit.itemattributes.api.attributes.AttributeValue;
import net.nunnerycode.bukkit.itemattributes.api.events.ItemAttributesEvent;
import net.nunnerycode.bukkit.itemattributes.api.events.attributes.LivingEntityAttributeEvent;
import org.bukkit.entity.LivingEntity;

public class ItemAttributesAttributeEvent extends ItemAttributesEvent implements LivingEntityAttributeEvent {

	private final LivingEntity livingEntity;
	private final LivingEntity target;
	private final Attribute attribute;
	private final AttributeValue attributeValue;

	public ItemAttributesAttributeEvent(LivingEntity livingEntity, LivingEntity target, Attribute attribute,
										AttributeValue attributeValue) {
		this.livingEntity = livingEntity;
		this.target = target;
		this.attribute = attribute;
		this.attributeValue = attributeValue;
	}

	@Override
	public LivingEntity getLivingEntity() {
		return livingEntity;
	}

	@Override
	public LivingEntity getTarget() {
		return target;
	}

	@Override
	public Attribute getAttribute() {
		return attribute;
	}

	@Override
	public AttributeValue getAttributeValue() {
		return attributeValue;
	}
}
