package net.nunnerycode.bukkit.itemattributes.api.attributes;

import java.util.List;
import java.util.Set;
import net.nunnerycode.bukkit.itemattributes.api.ItemAttributes;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

public interface AttributeHandler {

	double getAttributeValueFromItemStack(LivingEntity livingEntity, ItemStack itemStack, Attribute attribute);

	double getAttributeValueFromEntity(LivingEntity livingEntity, Attribute attribute);

	Set<Attribute> getAttributesPresentOnItemStack(ItemStack itemStack);

	boolean hasAttributeOnItemStack(ItemStack itemStack, Attribute attribute);

	boolean hasAttributeOnEntity(LivingEntity livingEntity, Attribute attribute);

	ItemAttributes getPlugin();

	List<String> getAttributeStringsFromItemStack(ItemStack itemStack, Attribute attribute);

	List<String> getAttributeStringsFromEntity(LivingEntity livingEntity, Attribute attribute);

	void playAttributeEffects(Location location, Attribute... attributes);

	void playAttributeSounds(Location location, Attribute... attributes);

}
