package net.nunnerycode.bukkit.itemattributes.managers;

import java.util.HashMap;
import java.util.Map;
import net.nunnerycode.bukkit.itemattributes.ItemAttributesPlugin;
import net.nunnerycode.bukkit.itemattributes.api.ItemAttributes;
import net.nunnerycode.bukkit.itemattributes.api.attributes.Attribute;
import net.nunnerycode.bukkit.itemattributes.api.managers.SettingsManager;

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

	public Attribute getAttribute(String name) {
		if (attributeMap.containsKey(name.toUpperCase())) {
			return attributeMap.get(name.toUpperCase());
		}
		return null;
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

	public void save() {
		if (!getPlugin().getConfigYAML().isSet("version")) {
			getPlugin().getConfigYAML().set("version", getPlugin().getConfigYAML().getVersion());
			getPlugin().getConfigYAML().set("options.base-player-health", basePlayerHealth);
			getPlugin().getConfigYAML().set("options.base-critical-rate", baseCriticalRate);
			getPlugin().getConfigYAML().set("options.base-critical-damage", baseCriticalDamage);
			getPlugin().getConfigYAML().set("options.base-stun-rate", baseStunRate);
			getPlugin().getConfigYAML().set("options.base-stun-length", baseStunLength);
			getPlugin().getConfigYAML().set("options.seconds-between-health-updates", secondsBetweenHealthUpdates);
		}
		getPlugin().getConfigYAML().save();
	}
}
