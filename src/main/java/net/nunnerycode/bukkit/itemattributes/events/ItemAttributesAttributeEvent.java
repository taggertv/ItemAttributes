package net.nunnerycode.bukkit.itemattributes.events;

import net.nunnerycode.bukkit.itemattributes.api.attributes.Attribute;
import net.nunnerycode.bukkit.itemattributes.api.events.ItemAttributesEvent;
import net.nunnerycode.bukkit.itemattributes.api.events.attributes.AttributeEvent;
import org.bukkit.entity.LivingEntity;

public class ItemAttributesAttributeEvent extends ItemAttributesEvent implements AttributeEvent {

	private final LivingEntity livingEntity;
	private final LivingEntity target;
	private final Attribute[] attributes;

	public ItemAttributesAttributeEvent(LivingEntity livingEntity, LivingEntity target, Attribute[] attributes) {
		this.livingEntity = livingEntity;
		this.target = target;
		this.attributes = attributes;
	}

	@Override
	public LivingEntity getLivingEntity() {
		return livingEntity;
	}

	@Override
	public LivingEntity getTargetEntity() {
		return target;
	}

	@Override
	public Attribute[] getAttributes() {
		return attributes;
	}

}
