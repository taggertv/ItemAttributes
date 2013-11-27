package net.nunnerycode.bukkit.itemattributes.api.events.attributes;

import java.util.Map;
import net.nunnerycode.bukkit.itemattributes.api.attributes.Attribute;
import net.nunnerycode.bukkit.itemattributes.api.attributes.AttributeValue;
import org.bukkit.entity.LivingEntity;

public interface LivingEntityAttributeEvent {

	LivingEntity getLivingEntity();

	LivingEntity getTarget();

	Map<Attribute, AttributeValue> getAttributeValueMap();

}
