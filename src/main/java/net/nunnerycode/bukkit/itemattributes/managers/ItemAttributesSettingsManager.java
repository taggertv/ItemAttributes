package net.nunnerycode.bukkit.itemattributes.managers;

import java.util.HashMap;
import java.util.Map;
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

		attributeMap.put("HEALTH", new ItemAttribute("Health", true, 100D, false, "%value% Health", null));
		attributeMap.put("ARMOR", new ItemAttribute("Armor", true, 100D, false, "%value% Armor", null));
		attributeMap.put("MELEE DAMAGE", new ItemAttribute("Melee Damage", true, 100D, false, "%value% Melee Damage",
				null));
		attributeMap.put("RANGED DAMAGE", new ItemAttribute("Ranged Damage", true, 100D, false,
				"%value% Ranged Damage", null));
		attributeMap.put("REGENERATION", new ItemAttribute("Regeneration", true, 100D, false, "%value% Regeneration",
				null));
		attributeMap.put("CRITICAL RATE", new ItemAttribute("Critical Rate", true, 100D, true,
				"%value% Critical Rate", null));
		attributeMap.put("CRITICAL DAMAGE", new ItemAttribute("Critical Damage", true, 100D, true,
				"%value% Critical Damage", null));
		attributeMap.put("LEVEL REQUIREMENT", new ItemAttribute("Level Requirement", true, 100D, false,
				"Level Requirement: %value%", null));
		attributeMap.put("ARMOR PENETRATION", new ItemAttribute("Armor Penetration", true, 100D, false,
				"%value% Armor Penetration", null));
		attributeMap.put("STUN RATE", new ItemAttribute("Stun Rate", true, 100D, true, "%value% Stun Rate", null));
		attributeMap.put("STUN LENGTH", new ItemAttribute("Stun Length", true, 100D, false, "%value% Stun Length",
				null));
		attributeMap.put("DODGE RATE", new ItemAttribute("Dodge Rate", true, 100D, true, "%value% Dodge Rate", null));
		attributeMap.put("FIRE IMMUNITY", new ItemAttribute("Fire Immunity", true, -1D, false,
				"Fire Immunity", null));
		attributeMap.put("WITHER IMMUNITY", new ItemAttribute("Wither Immunity", true, -1D, false,
				"Wither Immunity", null));
		attributeMap.put("POISON IMMUNITY", new ItemAttribute("Poison Immunity", true, -1D, false,
				"Poison Immunity", null));

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
				try {
					entry.getValue().setSound(Sound.valueOf(section.getString(entry.getKey().toLowerCase().replace(" ",
							"-") + ".sound", entry.getValue().getSound().name())));
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

	public void save() {
		if (!getPlugin().getConfigYAML().isSet("version")) {
			getPlugin().getConfigYAML().set("version", getPlugin().getConfigYAML().getVersion());
			getPlugin().getConfigYAML().set("options.base-player-health", basePlayerHealth);
			getPlugin().getConfigYAML().set("options.base-critical-rate", baseCriticalRate);
			getPlugin().getConfigYAML().set("options.base-critical-damage", baseCriticalDamage);
			getPlugin().getConfigYAML().set("options.base-stun-rate", baseStunRate);
			getPlugin().getConfigYAML().set("options.base-stun-length", baseStunLength);
			getPlugin().getConfigYAML().set("options.seconds-between-health-updates", secondsBetweenHealthUpdates);
			for (Map.Entry<String, Attribute> entry : attributeMap.entrySet()) {
				getPlugin().getConfigYAML().set("core-stats." + entry.getKey().toLowerCase().replace(" ",
						"-") + ".enabled", entry.getValue().isEnabled());
				getPlugin().getConfigYAML().set("core-stats." + entry.getKey().toLowerCase().replace(" ",
						"-") + ".format", entry.getValue().getFormat());
				getPlugin().getConfigYAML().set("core-stats." + entry.getKey().toLowerCase().replace(" ",
						"-") + ".percentage", entry.getValue().isPercentage());
				getPlugin().getConfigYAML().set("core-stats." + entry.getKey().toLowerCase().replace(" ",
						"-") + ".max-value", entry.getValue().getMaxValue());
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
}
