package net.nunnerycode.bukkit.itemattributes.managers;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import net.nunnerycode.bukkit.itemattributes.ItemAttributesPlugin;
import net.nunnerycode.bukkit.itemattributes.api.ItemAttributes;
import net.nunnerycode.bukkit.itemattributes.api.attributes.Attribute;
import net.nunnerycode.bukkit.itemattributes.api.managers.SettingsManager;
import net.nunnerycode.bukkit.itemattributes.attributes.ItemAttribute;
import org.bukkit.Effect;
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
	private Map<String, Attribute> coreAttributeMap;
	private Map<String, Attribute> externalAttributeMap;
	private boolean itemOnlyDamageSystemEnabled;
	private double itemOnlyDamageSystemBaseDamage;
	private boolean pluginCompatible;

	public ItemAttributesSettingsManager(ItemAttributesPlugin plugin) {
		this.plugin = plugin;
		coreAttributeMap = new HashMap<String, Attribute>();
		externalAttributeMap = new HashMap<String, Attribute>();
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
		coreAttributeMap.put("HEALTH", new ItemAttribute("Health", true, 100D, false, "%value% Health", null, 0));
		coreAttributeMap.put("ARMOR", new ItemAttribute("Armor", true, 100D, false, "%value% Armor", null, 0));
		coreAttributeMap.put("DAMAGE", new ItemAttribute("Damage", true, 100D, false, "%value% Damage", null, 1));
		coreAttributeMap.put("MELEE DAMAGE", new ItemAttribute("Melee Damage", true, 100D, false, "%value% Melee Damage",
				null, 0));
		coreAttributeMap.put("RANGED DAMAGE", new ItemAttribute("Ranged Damage", true, 100D, false,
				"%value% Ranged Damage", null, 0D));
		coreAttributeMap.put("REGENERATION", new ItemAttribute("Regeneration", true, 100D, false, "%value% Regeneration",
				null, 0D));
		coreAttributeMap.put("CRITICAL RATE", new ItemAttribute("Critical Rate", true, 100D, true,
				"%value% Critical Rate", null, 0.05));
		coreAttributeMap.put("CRITICAL DAMAGE", new ItemAttribute("Critical Damage", true, 100D, true,
				"%value% Critical Damage", null, 0.2));
		coreAttributeMap.put("LEVEL REQUIREMENT", new ItemAttribute("Level Requirement", true, 100D, false,
				"Level Requirement: %value%", null, 0));
		coreAttributeMap.put("ARMOR PENETRATION", new ItemAttribute("Armor Penetration", true, 100D, false,
				"%value% Armor Penetration", null, 0));
		coreAttributeMap.put("STUN RATE", new ItemAttribute("Stun Rate", true, 100D, true, "%value% Stun Rate", null, 0D));
		coreAttributeMap.put("STUN LENGTH", new ItemAttribute("Stun Length", true, 100D, false, "%value% Stun Length",
				null, 1));
		coreAttributeMap.put("DODGE RATE", new ItemAttribute("Dodge Rate", true, 100D, true, "%value% Dodge Rate", null,
				0D));
		coreAttributeMap.put("FIRE IMMUNITY", new ItemAttribute("Fire Immunity", true, -1D, false,
				"Fire Immunity", null, -1D));
		coreAttributeMap.put("WITHER IMMUNITY", new ItemAttribute("Wither Immunity", true, -1D, false,
				"Wither Immunity", null, -1D));
		coreAttributeMap.put("POISON IMMUNITY", new ItemAttribute("Poison Immunity", true, -1D, false,
				"Poison Immunity", null, -1D));
		coreAttributeMap.put("PERMISSION REQUIREMENT", new ItemAttribute("Permission Requirement", true, -1D, false,
				"Permission Requirement: %value%", null, -1D));
		coreAttributeMap.put("ATTACK SPEED", new ItemAttribute("Attack Speed", true, 100D, true,
				"%value% Attack Speed", null, 1D));
		coreAttributeMap.put("BLOCK", new ItemAttribute("Block", true, 100D, true, "%value% Block", null, 0.5D));
		coreAttributeMap.put("PARRY", new ItemAttribute("Parry", true, 100D, true, "%value% Parry", null, 1.5D));

		if (getPlugin().getConfigYAML().isConfigurationSection("core-stats")) {
			ConfigurationSection section = getPlugin().getConfigYAML().getConfigurationSection("core-stats");
			for (Map.Entry<String, Attribute> entry : coreAttributeMap.entrySet()) {
				entry.getValue().setEnabled(section.getBoolean(entry.getKey().toLowerCase().replace(" ",
						"-") + ".enabled", entry.getValue().isEnabled()));
				entry.getValue().setFormat(section.getString(entry.getKey().toLowerCase().replace(" ",
						"-") + ".format", entry.getValue().getFormat()));
				entry.getValue().setMaxValuePlayers(section.getDouble(entry.getKey().toLowerCase().replace(" ",
						"-") + ".players-max-value", entry.getValue().getMaxValuePlayers()));
				entry.getValue().setMaxValueMobs(section.getDouble(entry.getKey().toLowerCase().replace(" ",
						"-") + ".mobs-max-value", entry.getValue().getMaxValueMobs()));
				entry.getValue().setPercentage(section.getBoolean(entry.getKey().toLowerCase().replace(" ",
						"-") + ".percentage", entry.getValue().isPercentage()));
				entry.getValue().setPlayersBaseValue(section.getDouble(entry.getKey().toLowerCase().replace(" ",
						"-") + ".players-base-value", entry.getValue().getPlayersBaseValue()));
				entry.getValue().setMobsBaseValue(section.getDouble(entry.getKey().toLowerCase().replace(" ",
						"-") + ".mobs-base-value", entry.getValue().getMobsBaseValue()));
				try {
					entry.getValue().setSound(Sound.valueOf(section.getString(entry.getKey().toLowerCase().replace(" ",
							"-") + ".sound", (entry.getValue().getSound() != null) ? entry.getValue().getSound().name
							() : "")));
				} catch (Exception e) {
					// do nothing
				}
				try {
					entry.getValue().setEffect(Effect.valueOf(section.getString(entry.getKey().toLowerCase().replace
							(" ", "-") + ".sound", (entry.getValue().getEffect() != null) ? entry.getValue().getEffect
							().name() : "")));
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
		if (coreAttributeMap.containsKey(name.toUpperCase())) {
			return coreAttributeMap.get(name.toUpperCase());
		}
		return null;
	}

	@Override
	public boolean isItemOnlyDamageSystemEnabled() {
		return itemOnlyDamageSystemEnabled;
	}

	@Override
	public double getItemOnlyDamageSystemBaseDamage() {
		return itemOnlyDamageSystemBaseDamage;
	}

	@Override
	public boolean addAttribute(String name, Attribute attribute) {
		boolean b = false;
		if (name != null && attribute != null && isPluginCompatible()) {
			externalAttributeMap.put(name.toUpperCase(), attribute);
			b = externalAttributeMap.containsKey(name.toUpperCase());
			getPlugin().getDebugPrinter().debug(Level.INFO, "Adding attribute: " + attribute.getName());
		}
		return b;
	}

	@Override
	public boolean removeAttribute(String name) {
		boolean b = false;
		if (name != null && isPluginCompatible()) {
			externalAttributeMap.remove(name.toUpperCase());
			b = !externalAttributeMap.containsKey(name.toUpperCase());
			getPlugin().getDebugPrinter().debug(Level.INFO, "Removing attribute: " + name);
		}
		return b;
	}

	@Override
	public Set<Attribute> getLoadedAttributes() {
		Set<Attribute> allAttributes = new HashSet<Attribute>(getLoadedCoreAttributes());
		allAttributes.addAll(getLoadedExtraAttributes());
		return allAttributes;
	}

	@Override
	public Set<Attribute> getLoadedCoreAttributes() {
		return new HashSet<Attribute>(coreAttributeMap.values());
	}

	@Override
	public Set<Attribute> getLoadedExtraAttributes() {
		return new HashSet<Attribute>(externalAttributeMap.values());
	}

	@Override
	public boolean isPluginCompatible() {
		return pluginCompatible;
	}

	public void save() {
		getPlugin().getConfigYAML().load();
		getPlugin().getConfigYAML().set("options.base-player-health", basePlayerHealth);
		getPlugin().getConfigYAML().set("options.base-critical-rate", baseCriticalRate);
		getPlugin().getConfigYAML().set("options.base-critical-damage", baseCriticalDamage);
		getPlugin().getConfigYAML().set("options.base-stun-rate", baseStunRate);
		getPlugin().getConfigYAML().set("options.base-stun-length", baseStunLength);
		getPlugin().getConfigYAML().set("options.seconds-between-health-updates", secondsBetweenHealthUpdates);
		getPlugin().getConfigYAML().set("options.item-only-damage-system.enabled", itemOnlyDamageSystemEnabled);
		getPlugin().getConfigYAML().set("options.item-only-damage-system.base-damage", itemOnlyDamageSystemBaseDamage);
		for (Map.Entry<String, Attribute> entry : coreAttributeMap.entrySet()) {
			getPlugin().getConfigYAML().set("core-stats." + entry.getKey().toLowerCase().replace(" ",
					"-") + ".enabled", entry.getValue().isEnabled());
			getPlugin().getConfigYAML().set("core-stats." + entry.getKey().toLowerCase().replace(" ",
					"-") + ".format", entry.getValue().getFormat());
			getPlugin().getConfigYAML().set("core-stats." + entry.getKey().toLowerCase().replace(" ",
					"-") + ".percentage", entry.getValue().isPercentage());
			getPlugin().getConfigYAML().set("core-stats." + entry.getKey().toLowerCase().replace(" ",
					"-") + ".players-max-value", entry.getValue().getMaxValuePlayers());
			getPlugin().getConfigYAML().set("core-stats." + entry.getKey().toLowerCase().replace(" ",
					"-") + ".mobs-max-value", entry.getValue().getMaxValueMobs());
			getPlugin().getConfigYAML().set("core-stats." + entry.getKey().toLowerCase().replace(" ",
					"-") + ".players-base-value", entry.getValue().getPlayersBaseValue());
			getPlugin().getConfigYAML().set("core-stats." + entry.getKey().toLowerCase().replace(" ",
					"-") + ".mobs-base-value", entry.getValue().getMobsBaseValue());
			try {
				getPlugin().getConfigYAML().set("core-stats." + entry.getKey().toLowerCase().replace(" ",
						"-") + ".sound", entry.getValue().getSound().name());
			} catch (Exception e) {
				// do nothing
			}
			try {
				getPlugin().getConfigYAML().set("core-stats." + entry.getKey().toLowerCase().replace(" ",
						"-") + ".effect", entry.getValue().getEffect().name());
			} catch (Exception e) {
				// do nothing
			}
		}
		getPlugin().getConfigYAML().save();
	}
}
