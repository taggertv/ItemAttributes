package net.nunnerycode.bukkit.itemattributes.attributes;

import net.nunnerycode.bukkit.itemattributes.api.ItemAttributes;
import net.nunnerycode.bukkit.itemattributes.api.attributes.Attribute;
import net.nunnerycode.bukkit.itemattributes.api.attributes.AttributeHandler;
import net.nunnerycode.bukkit.itemattributes.events.ItemAttributesAttributeEvent;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ItemAttributeHandler implements AttributeHandler {

	private final ItemAttributes plugin;

	public ItemAttributeHandler(ItemAttributes plugin) {
		this.plugin = plugin;
	}

	private List<String> getStrings(Collection<String> collection, Attribute attribute) {
		List<String> list = new ArrayList<String>();
		if (collection == null || collection.isEmpty() || attribute == null || !attribute.isEnabled()) {
			return list;
		}
		for (String s : collection) {
			String stripped = ChatColor.stripColor(s);
			String withoutVariables = attribute.getFormat().replaceAll("%(?s)(.*?)%", "").trim();
			if (stripped.contains(withoutVariables)) {
				list.add(stripped.replace(withoutVariables, "").trim());
			}
		}
		return list;
	}

	private double getDouble(Collection<String> collection, Attribute attribute) {
		double d = 0.0;
		if (collection == null || collection.isEmpty() || attribute == null || !attribute.isEnabled()) {
			return d;
		}
		for (String s : collection) {
			String stripped = ChatColor.stripColor(s);

			String withoutNumbers = stripped.replaceAll("[0-9\\+%\\-]", "").trim();
			String withoutLetters = stripped.replaceAll(withoutNumbers, "").trim();
			String withoutVariables = attribute.getFormat().replaceAll("%(?s)(.*?)%", "").trim();

			if (!withoutNumbers.equals(withoutVariables)) {
				continue;
			}

			if (withoutLetters.contains(" - ")) {
				String[] split = withoutLetters.split(" - ");
				if (split.length > 1) {
					double first = NumberUtils.toDouble(split[0], 0.0);
					double second = NumberUtils.toDouble(split[1], 0.0);
					d += RandomUtils.nextDouble() * (Math.max(first, second) - Math.min(first,
							second)) + Math.min(first, second);
				}
			} else {
				d += NumberUtils.toDouble(withoutLetters, 0.0);
			}
		}
		return d;
	}

	private double getValue(LivingEntity livingEntity, Collection<String> collection, Attribute attribute) {
		if (collection == null || attribute == null || !attribute.isEnabled()) {
			return 0.0;
		}
		if (attribute.isPercentage()) {
			return getDoublePercentage(livingEntity, collection, attribute);
		}
		return getDouble(collection, attribute);
	}

	@Override
	public double getAttributeValueFromItemStack(LivingEntity livingEntity, ItemStack itemStack, Attribute attribute) {
		if (itemStack == null || attribute == null || !attribute.isEnabled()) {
			return 0.0D;
		}
		List<String> lore = new ArrayList<String>();
		if (itemStack.hasItemMeta() && itemStack.getItemMeta().hasLore()) {
			lore = itemStack.getItemMeta().getLore();
		}
		return getValue(livingEntity, lore, attribute);
	}

	private double getDoublePercentage(LivingEntity livingEntity, Collection<String> collection, Attribute attribute) {
		double d = 0.0;
		if (collection == null || collection.isEmpty() || attribute == null || !attribute.isEnabled()) {
			return d;
		}
		double maxVal = (livingEntity instanceof Player) ? attribute.getMaxValuePlayers() : attribute.getMaxValueMobs();
		for (String s : collection) {
			String stripped = ChatColor.stripColor(s);
			String withoutNumbers = stripped.replaceAll("[0-9\\+%\\-]", "").trim();
			String withoutLetters = stripped.replaceAll("[a-zA-Z\\+%:]", "").trim();
			String withoutVariables = attribute.getFormat().replaceAll("%(?s)(.*?)%", "").trim();
			if (!withoutNumbers.equals(withoutVariables)) {
				continue;
			}
			if (!s.contains("%")) {
				if (withoutLetters.contains("-")) {
					String[] split = withoutLetters.split("-");
					double first = NumberUtils.toDouble(split[0].trim(), 0.0);
					double second = NumberUtils.toDouble(split[1].trim(), 0.0);
					d += (RandomUtils.nextDouble() * (Math.max(first, second) - Math.min(first,
							second)) + Math.min(first, second)) / ((maxVal != 0D) ? maxVal : 100D);
				} else {
					d += NumberUtils.toDouble(withoutLetters, 0.0) / ((maxVal != 0D) ? maxVal : 100D);
				}
			} else {
				if (withoutLetters.contains("-")) {
					String[] split = withoutLetters.split("-");
					if (split.length > 1) {
						double first = NumberUtils.toDouble(split[0].trim(), 0.0);
						double second = NumberUtils.toDouble(split[1].trim(), 0.0);
						d += (RandomUtils.nextDouble() * (Math.max(first, second) - Math.min(first,
								second)) + Math.min(first, second)) / 100D;
					}
				} else {
					d += NumberUtils.toDouble(withoutLetters, 0.0) / 100D;
				}
			}
		}
		return d;
	}

	@Override
	public double getAttributeValueFromEntity(LivingEntity livingEntity, Attribute attribute) {
		double d = 0.0D;
		if (livingEntity == null || attribute == null || !attribute.isEnabled()) {
			return d;
		}
		for (ItemStack itemStack : livingEntity.getEquipment().getArmorContents()) {
			d += getAttributeValueFromItemStack(livingEntity, itemStack, attribute);
		}
		d += getAttributeValueFromItemStack(livingEntity, livingEntity.getEquipment().getItemInHand(), attribute);
		ItemAttributesAttributeEvent iaae = new ItemAttributesAttributeEvent(livingEntity, attribute,
				new ItemAttributeValue(d));
		Bukkit.getPluginManager().callEvent(iaae);
		if (iaae.isCancelled()) {
			return 0;
		}
		return iaae.getAttributeValue().asDouble();
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


	@Override
	public List<String> getAttributeStringsFromItemStack(ItemStack itemStack, Attribute attribute) {
		List<String> list = new ArrayList<String>();
		if (itemStack == null || attribute == null || !attribute.isEnabled()) {
			return list;
		}
		List<String> lore = new ArrayList<String>();
		if (itemStack.hasItemMeta() && itemStack.getItemMeta().hasLore()) {
			lore = itemStack.getItemMeta().getLore();
		}
		list = getStrings(lore, attribute);
		return list;
	}

	@Override
	public List<String> getAttributeStringsFromEntity(LivingEntity livingEntity, Attribute attribute) {
		List<String> list = new ArrayList<String>();
		if (livingEntity == null || attribute == null || !attribute.isEnabled()) {
			return list;
		}
		for (ItemStack itemStack : livingEntity.getEquipment().getArmorContents()) {
			list.addAll(getAttributeStringsFromItemStack(itemStack, attribute));
		}
		list.addAll(getAttributeStringsFromItemStack(livingEntity.getEquipment().getItemInHand(), attribute));
		return list;
	}

	@Override
	public void playAttributeEffects(Location location, Attribute... attributes) {
		if (location == null || attributes == null) {
			return;
		}
		for (Attribute attribute : attributes) {
			if (attribute == null || !attribute.isEnabled() || attribute.getEffect() == null) {
				continue;
			}
			location.getWorld().playEffect(location, attribute.getEffect(), RandomUtils.nextInt(4));
		}
	}

	@Override
	public void playAttributeSounds(Location location, Attribute... attributes) {
		if (location == null || attributes == null) {
			return;
		}
		for (Attribute attribute : attributes) {
			if (attribute == null || !attribute.isEnabled() || attribute.getSound() == null) {
				continue;
			}
			location.getWorld().playSound(location, attribute.getSound(), 1F, 1F);
		}
	}

}
