package net.nunnerycode.bukkit.itemattributes.attributes;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import net.nunnerycode.bukkit.itemattributes.api.ItemAttributes;
import net.nunnerycode.bukkit.itemattributes.api.attributes.Attribute;
import net.nunnerycode.bukkit.itemattributes.api.attributes.AttributeHandler;
import net.nunnerycode.bukkit.itemattributes.utils.ItemAttributesParseUtil;
import org.bukkit.ChatColor;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

public class ItemAttributeHandler implements AttributeHandler {

	private final ItemAttributes plugin;

	public ItemAttributeHandler(ItemAttributes plugin) {
		this.plugin = plugin;
	}

	@Override
	public double getAttributeValueFromItemStack(ItemStack itemStack, Attribute attribute) {
		if (itemStack == null || attribute == null || !attribute.isEnabled()) {
			return 0.0D;
		}
		List<String> lore = new ArrayList<String>();
		if (itemStack.hasItemMeta() && itemStack.getItemMeta().hasLore()) {
			lore = itemStack.getItemMeta().getLore();
		}
		return ItemAttributesParseUtil.getValue(lore, attribute);
	}

	@Override
	public double getAttributeValueFromEntity(LivingEntity livingEntity, Attribute attribute) {
		double d = 0.0D;
		if (livingEntity == null || attribute == null || !attribute.isEnabled()) {
			return d;
		}
		for (ItemStack itemStack : livingEntity.getEquipment().getArmorContents()) {
			d += getAttributeValueFromItemStack(itemStack, attribute);
		}
		d += getAttributeValueFromItemStack(livingEntity.getEquipment().getItemInHand(), attribute);
		return d;
	}

	@Override
	public Set<Attribute> getAttributesPresentOnItemStack(ItemStack itemStack) {
		Set<Attribute> attributes = new HashSet<Attribute>();
		if (itemStack == null) {
			return attributes;
		}
		List<String> lore = new ArrayList<String>();
		if (itemStack.hasItemMeta() && itemStack.getItemMeta().hasLore()) {
			lore = itemStack.getItemMeta().getLore();
		}
		for (String s : lore) {
			for (Attribute attribute : getPlugin().getSettingsManager().getLoadedAttributes()) {
				if (!attribute.isEnabled()) {
					continue;
				}
				if (s.contains(attribute.getFormat().replaceAll("%(?s)(.*?)%", "").trim()) && !attributes.contains
						(attribute)) {
					attributes.add(attribute);
				}
			}
		}
		return attributes;
	}

	@Override
	public boolean hasAttributeOnItemStack(ItemStack itemStack, Attribute attribute) {
		if (itemStack == null || attribute == null || !attribute.isEnabled()) {
			return false;
		}
		List<String> lore = new ArrayList<String>();
		if (itemStack.hasItemMeta() && itemStack.getItemMeta().hasLore()) {
			lore = itemStack.getItemMeta().getLore();
		}
		for (String s : lore) {
			if (ChatColor.stripColor(s).contains(ChatColor.stripColor(attribute.getFormat().replaceAll("%(?s)(.*?)%",
					"").trim()))) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean hasAttributeOnEntity(LivingEntity livingEntity, Attribute attribute) {
		if (livingEntity == null || attribute == null || !attribute.isEnabled()) {
			return false;
		}
		for (ItemStack itemStack : livingEntity.getEquipment().getArmorContents()) {
			if (hasAttributeOnItemStack(itemStack, attribute)) {
				return true;
			}
		}
		return hasAttributeOnItemStack(livingEntity.getEquipment().getItemInHand(), attribute);
	}

	@Override
	public ItemAttributes getPlugin() {
		return plugin;
	}
}
