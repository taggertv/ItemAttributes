package net.nunnerycode.bukkit.itemattributes.api.attributes;

import java.util.Set;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

public interface AttributeHandler {

	double getAttributeValueFromItemStack(ItemStack itemStack, Attribute attribute);

	double getAttributeValueFromEntity(LivingEntity livingEntity, Attribute attribute);

	Set<Attribute> getAttributesPresentOnItemStack(ItemStack itemStack);

}
