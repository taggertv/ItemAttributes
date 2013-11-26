package net.nunnerycode.bukkit.itemattributes.api.events.attributes;

import net.nunnerycode.bukkit.itemattributes.api.attributes.Attribute;
import org.bukkit.entity.LivingEntity;

public interface AttributeEvent {

	LivingEntity getLivingEntity();

	LivingEntity getTargetEntity();

	Attribute[] getAttributes();

}
