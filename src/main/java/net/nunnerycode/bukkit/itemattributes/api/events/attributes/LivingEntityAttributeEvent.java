package net.nunnerycode.bukkit.itemattributes.api.events.attributes;

import net.nunnerycode.bukkit.itemattributes.api.attributes.Attribute;
import net.nunnerycode.bukkit.itemattributes.api.attributes.AttributeValue;
import org.bukkit.entity.LivingEntity;

public interface LivingEntityAttributeEvent {

	LivingEntity getLivingEntity();

	Attribute getAttribute();

	AttributeValue getAttributeValue();

	void setAttributeValue(AttributeValue value);

}
