package net.nunnerycode.bukkit.itemattributes.managers;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import net.nunnerycode.bukkit.itemattributes.ItemAttributesPlugin;
import net.nunnerycode.bukkit.itemattributes.api.ItemAttributes;
import net.nunnerycode.bukkit.itemattributes.api.attributes.Attribute;
import net.nunnerycode.bukkit.itemattributes.api.managers.SettingsManager;
import net.nunnerycode.bukkit.itemattributes.attributes.ItemAttribute;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;

public final class ItemAttributesSettingsManager implements SettingsManager {

	private ItemAttributesPlugin plugin;
	private double basePlayerHealth;
	private double baseCriticalRate;
	private double baseCriticalDamage;
	private double baseStunRate;
	private double baseDodgeRate;
	private int baseStunLength;
	private int secondsBetweenHealthUpdates;
	private Map<String, Attribute> attributeMap;
	private boolean itemOnlyDamageSystemEnabled;
	private double itemOnlyDamageSystemBaseDamage;
	private boolean pluginCompatible;

	public ItemAttributesSettingsManager(ItemAttributesPlugin plugin) {
		this.plugin = plugin;
		attributeMap = new HashMap<String, Attribute>();
	}

	public void load() {
		getPlugin().getConfigYAML().load();
		basePlayerHealth = getPlugin().getConfigYAML().getDouble("options.base-player-health", 20.0);
		baseCriticalRate = getPlugin().getConfigYAML().getDouble("options.base-critical-rate", 0.05);
		baseCriticalDamage = getPlugin().getConfigYAML().getDouble("options.base-critical-damage", 0.2);
		baseStunRate = getPlugin().getConfigYAML().getDouble("options.base-stun-rate", 0.05);
		baseStunLength = getPlugin().getConfigYAML().getInt("options.base-stun-length", 1);
		baseDodgeRate = getPlugin().getConfigYAML().getDouble("options.base-dodge-rate", 0.0);
		secondsBetweenHealthUpdates = getPlugin().getConfigYAML().getInt("options.seconds-between-health-updates",
				10);
		itemOnlyDamageSystemEnabled = getPlugin().getConfigYAML().getBoolean("options.item-only-damage-system" +
				".enabled", false);
		itemOnlyDamageSystemBaseDamage = getPlugin().getConfigYAML().getDouble("options.item-only-damage-system" +
				".base-damage", 1.0D);
		pluginCompatible = getPlugin().getConfigYAML().getBoolean("options.enable-plugin-compatibility", true);
		attributeMap.put("HEALTH", new ItemAttribute("Health", true, 100D, false, "%value% Health", null, 0));
		attributeMap.put("ARMOR", new ItemAttribute("Armor", true, 100D, false, "%value% Armor", null, 0));
		attributeMap.put("DAMAGE", new ItemAttribute("Damage", true, 100D, false, "%value% Damage", null, 1));
		attributeMap.put("MELEE DAMAGE", new ItemAttribute("Melee Damage", true, 100D, false, "%value% Melee Damage",
				null, 0));
		attributeMap.put("RANGED DAMAGE", new ItemAttribute("Ranged Damage", true, 100D, false,
				"%value% Ranged Damage", null, 0D));
		attributeMap.put("REGENERATION", new ItemAttribute("Regeneration", true, 100D, false, "%value% Regeneration",
				null, 0D));
		attributeMap.put("CRITICAL RATE", new ItemAttribute("Critical Rate", true, 100D, true,
				"%value% Critical Rate", null, 0.05));
		attributeMap.put("CRITICAL DAMAGE", new ItemAttribute("Critical Damage", true, 100D, true,
				"%value% Critical Damage", null, 0.2));
		attributeMap.put("LEVEL REQUIREMENT", new ItemAttribute("Level Requirement", true, 100D, false,
				"Level Requirement: %value%", null, 0));
		attributeMap.put("ARMOR PENETRATION", new ItemAttribute("Armor Penetration", true, 100D, false,
				"%value% Armor Penetration", null, 0));
		attributeMap.put("STUN RATE", new ItemAttribute("Stun Rate", true, 100D, true, "%value% Stun Rate", null, 0D));
		attributeMap.put("STUN LENGTH", new ItemAttribute("Stun Length", true, 100D, false, "%value% Stun Length",
				null, 1));
		attributeMap.put("DODGE RATE", new ItemAttribute("Dodge Rate", true, 100D, true, "%value% Dodge Rate", null,
				0D));
		attributeMap.put("FIRE IMMUNITY", new ItemAttribute("Fire Immunity", true, -1D, false,
				"Fire Immunity", null, -1D));
		attributeMap.put("WITHER IMMUNITY", new ItemAttribute("Wither Immunity", true, -1D, false,
				"Wither Immunity", null, -1D));
		attributeMap.put("POISON IMMUNITY", new ItemAttribute("Poison Immunity", true, -1D, false,
				"Poison Immunity", null, -1D));
		attributeMap.put("PERMISSION REQUIREMENT", new ItemAttribute("Permission Requirement", true, -1D, false,
				"Permission Requirement: %value%", null, -1D));

		if (getPlugin().getConfigYAML().isConfigurationSection("core-stats")) {
			ConfigurationSection section = getPlugin().getConfigYAML().getConfigurationSection("core-stats");
			for (Map.Entry<String, Attribute> entry : attributeMap.entrySet()) {
				entry.getValue().setEnabled(section.getBoolean(entry.getKey().toLowerCase().replace(" ",
						"-") + ".enabled", entry.getValue().isEnabled()));
				entry.getValue().setFormat(section.getString(entry.getKey().toLowerCase().replace(" ",
						"-") + ".format", entry.getValue().getFormat()));
				entry.getValue().setMaxValue(section.getDouble(entry.getKey().toLowerCase().replace(" ",
						"-") + ".max-value", entry.getValue().getMaxValue()));
				entry.getValue().setPercentage(section.getBoolean(entry.getKey().toLowerCase().replace(" ",
						"-") + ".percentage", entry.getValue().isPercentage()));
				entry.getValue().setBaseValue(section.getDouble(entry.getKey().toLowerCase().replace(" ",
						"-") + ".base-value", entry.getValue().getBaseValue()));
				try {
					entry.getValue().setSound(Sound.valueOf(section.getString(entry.getKey().toLowerCase().replace(" ",
							"-") + ".sound", (entry.getValue().getSound() != null) ? entry.getValue().getSound().name
							() : "")));
				} catch (Exception e) {
					// do nothing
				}
			}
		}
	}

	@Override
	public ItemAttributes getPlugin() {
		return plugin;
	}

	@Override
	public double getBasePlayerHealth() {
		return basePlayerHealth;
	}

	@Override
	public int getSecondsBetweenHealthUpdates() {
		return secondsBetweenHealthUpdates;
	}

	@Override
	public double getBaseCriticalRate() {
		return baseCriticalRate;
	}

	@Override
	public double getBaseCriticalDamage() {
		return baseCriticalDamage;
	}

	@Override
	public double getBaseStunRate() {
		return baseStunRate;
	}

	@Override
	public int getBaseStunLength() {
		return baseStunLength;
	}

	@Override
	public double getBaseDodgeRate() {
		return baseDodgeRate;
	}

	public Attribute getAttribute(String name) {
		if (attributeMap.containsKey(name.toUpperCase())) {
			return attributeMap.get(name.toUpperCase());
		}
		return null;
	}

	@Override
	public boolean isItemOnlyDamageSystemEnabled() {
		return itemOnlyDamageSystemEnabled;
	}

	public void save() {
		getPlugin().getConfigYAML().load();
		if (!getPlugin().getConfigYAML().isSet("version")) {
			getPlugin().getConfigYAML().set("version", getPlugin().getConfigYAML().getVersion());
			getPlugin().getConfigYAML().set("options.base-player-health", basePlayerHealth);
			getPlugin().getConfigYAML().set("options.base-critical-rate", baseCriticalRate);
			getPlugin().getConfigYAML().set("options.base-critical-damage", baseCriticalDamage);
			getPlugin().getConfigYAML().set("options.base-stun-rate", baseStunRate);
			getPlugin().getConfigYAML().set("options.base-stun-length", baseStunLength);
			getPlugin().getConfigYAML().set("options.seconds-between-health-updates", secondsBetweenHealthUpdates);
			getPlugin().getConfigYAML().set("options.item-only-damage-system.enabled", itemOnlyDamageSystemEnabled);
			getPlugin().getConfigYAML().set("options.item-only-damage-system.base-damage", itemOnlyDamageSystemBaseDamage);
			for (Map.Entry<String, Attribute> entry : attributeMap.entrySet()) {
				getPlugin().getConfigYAML().set("core-stats." + entry.getKey().toLowerCase().replace(" ",
						"-") + ".enabled", entry.getValue().isEnabled());
				getPlugin().getConfigYAML().set("core-stats." + entry.getKey().toLowerCase().replace(" ",
						"-") + ".format", entry.getValue().getFormat());
				getPlugin().getConfigYAML().set("core-stats." + entry.getKey().toLowerCase().replace(" ",
						"-") + ".percentage", entry.getValue().isPercentage());
				getPlugin().getConfigYAML().set("core-stats." + entry.getKey().toLowerCase().replace(" ",
						"-") + ".max-value", entry.getValue().getMaxValue());
				getPlugin().getConfigYAML().set("core-stats." + entry.getKey().toLowerCase().replace(" ",
						"-") + ".base-value", entry.getValue().getBaseValue());
				try {
					getPlugin().getConfigYAML().set("core-stats." + entry.getKey().toLowerCase().replace(" ",
							"-") + ".sound", entry.getValue().getSound().name());
				} catch (Exception e) {
					// do nothing
				}
			}
		}
		getPlugin().getConfigYAML().save();
	}

	@Override
	public double getItemOnlyDamageSystemBaseDamage() {
		return itemOnlyDamageSystemBaseDamage;
	}

	@Override
	public boolean addAttribute(String name, Attribute attribute) {
		boolean b = false;
		if (isPluginCompatible()) {
			attributeMap.put(name.toUpperCase(), attribute);
			b = attributeMap.containsKey(name.toUpperCase());
		}
		return b;
	}

	@Override
	public boolean removeAttribute(String name, Attribute attribute) {
		boolean b = false;
		if (isPluginCompatible()) {
			attributeMap.remove(name.toUpperCase());
			b = !attributeMap.containsKey(name.toUpperCase());
		}
		return b;
	}

	@Override
	public Set<Attribute> getLoadedAttributes() {
		return new HashSet<Attribute>(attributeMap.values());
	}

	@Override
	public boolean isPluginCompatible() {
		return pluginCompatible;
	}
}
