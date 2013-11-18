package net.nunnerycode.bukkit.itemattributes.api.attributes;

import java.util.Set;
import net.nunnerycode.bukkit.itemattributes.api.ItemAttributes;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

public interface AttributeHandler {

	double getAttributeValueFromItemStack(ItemStack itemStack, Attribute attribute);

	double getAttributeValueFromEntity(LivingEntity livingEntity, Attribute attribute);

	Set<Attribute> getAttributesPresentOnItemStack(ItemStack itemStack);

	boolean hasAttributeOnItemStack(ItemStack itemStack, Attribute attribute);

	boolean hasAttributeOnEntity(LivingEntity livingEntity, Attribute attribute);

	ItemAttributes getPlugin();

	String getAttributeStringFromItemStack(ItemStack itemStack, Attribute attribute);

	String[] getAttributeStringsFromEntity(LivingEntity livingEntity, Attribute attribute);

}
