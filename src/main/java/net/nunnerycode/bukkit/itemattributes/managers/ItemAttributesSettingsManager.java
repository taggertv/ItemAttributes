package net.nunnerycode.bukkit.itemattributes.managers;

import net.nunnerycode.bukkit.itemattributes.ItemAttributesPlugin;
import net.nunnerycode.bukkit.itemattributes.api.ItemAttributes;
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
	private String healthFormat;
	private boolean healthEnabled;
	private String damageFormat;
	private boolean damageEnabled;
	private String meleeDamageFormat;
	private boolean meleeDamageEnabled;
	private String rangedDamageFormat;
	private boolean rangedDamageEnabled;
	private String regenerationFormat;
	private boolean regenerationEnabled;
	private String armorFormat;
	private boolean armorEnabled;
	private String armorPenetrationFormat;
	private boolean armorPenetrationEnabled;
	private String criticalRateFormat;
	private boolean criticalRateEnabled;
	private String criticalDamageFormat;
	private boolean criticalDamageEnabled;
	private String stunRateFormat;
	private boolean stunRateEnabled;
	private String stunLengthFormat;
	private boolean stunLengthEnabled;
	private String dodgeRateFormat;
	private boolean dodgeRateEnabled;
	private String levelRequirementFormat;
	private boolean levelRequirementEnabled;
	private String poisonImmunityFormat;
	private boolean poisonImmunityEnabled;
	private String fireImmunityFormat;
	private boolean fireImmunityEnabled;
	private String witherImmunityFormat;
	private boolean witherImmunityEnabled;
	private double maximumCriticalRate;
	private double maximumCriticalDamage;
	private double maximumStunRate;
	private double maximumDodgeRate;
	private boolean healthModificationEnabled;

	public ItemAttributesSettingsManager(ItemAttributesPlugin plugin) {
		this.plugin = plugin;
	}

	public void load() {
		getPlugin().getConfigYAML().load();
		healthModificationEnabled = getPlugin().getConfigYAML().getBoolean("options.health-modification-enabled",
				true);
		basePlayerHealth = getPlugin().getConfigYAML().getDouble("options.base-player-health", 20.0);
		baseCriticalRate = getPlugin().getConfigYAML().getDouble("options.base-critical-rate", 0.05);
		baseCriticalDamage = getPlugin().getConfigYAML().getDouble("options.base-critical-damage", 0.2);
		baseStunRate = getPlugin().getConfigYAML().getDouble("options.base-stun-rate", 0.05);
		baseStunLength = getPlugin().getConfigYAML().getInt("options.base-stun-length", 1);
		baseDodgeRate = getPlugin().getConfigYAML().getDouble("options.base-dodge-rate", 0.0);
		secondsBetweenHealthUpdates = getPlugin().getConfigYAML().getInt("options.seconds-between-health-updates",
				10);
		healthFormat = getPlugin().getConfigYAML().getString("core-stats.health.format", "%value% Health");
		damageFormat = getPlugin().getConfigYAML().getString("core-stats.damage.format", "%value% Damage");
		meleeDamageFormat = getPlugin().getConfigYAML().getString("core-stats.melee-damage.format",
				"%value% Melee Damage");
		rangedDamageFormat = getPlugin().getConfigYAML().getString("core-stats.ranged-damage.format",
				"%value% Ranged Damage");
		regenerationFormat = getPlugin().getConfigYAML().getString("core-stats.regeneration.format",
				"%value% Regeneration");
		armorFormat = getPlugin().getConfigYAML().getString("core-stats.armor.format", "%value% Armor");
		armorPenetrationFormat = getPlugin().getConfigYAML().getString("core-stats.armor-penetration.format",
				"%value% Armor Penetration");
		criticalRateFormat = getPlugin().getConfigYAML().getString("core-stats.critical-rate.format",
				"%value% Critical Rate");
		maximumCriticalRate = getPlugin().getConfigYAML().getDouble("core-stats.critical-rate.max-value", 100D);
		criticalDamageFormat = getPlugin().getConfigYAML().getString("core-stats.critical-damage.format",
				"%value% Critical Damage");
		maximumCriticalDamage = getPlugin().getConfigYAML().getDouble("core-stats.critical-damage.max-value", 100D);
		levelRequirementFormat = getPlugin().getConfigYAML().getString("core-stats.level-requirement.format",
				"Level Required: %value%");
		stunRateFormat = getPlugin().getConfigYAML().getString("core-stats.stun-rate.format", "%value% Stun Rate");
		maximumStunRate = getPlugin().getConfigYAML().getDouble("core-stats.stun-rate.max-value", 100D);
		stunLengthFormat = getPlugin().getConfigYAML().getString("core-stats.stun-length.format",
				"%value% Stun Length");
		dodgeRateFormat = getPlugin().getConfigYAML().getString("core-stats.dodge-rate.format", "%value% Dodge Rate");
		maximumDodgeRate = getPlugin().getConfigYAML().getDouble("core-stats.dodge-rate.max-value", 100D);
		poisonImmunityFormat = getPlugin().getConfigYAML().getString("core-stats.poison-immunity.format",
				"Poison Immunity");
		fireImmunityFormat = getPlugin().getConfigYAML().getString("core-stats.fire-immunity.format",
				"Fire Immunity");
		witherImmunityFormat = getPlugin().getConfigYAML().getString("core-stats.wither-immunity.format",
				"Wither Immunity");
	}

	@Override
	public ItemAttributes getPlugin() {
		return plugin;
	}

	@Override
	public String getArmorPenetrationFormat() {
		return armorPenetrationFormat;
	}

	@Override
	public boolean isArmorPenetrationEnabled() {
		return armorPenetrationEnabled;
	}

	@Override
	public String getHealthFormat() {
		return healthFormat;
	}

	@Override
	public boolean isHealthEnabled() {
		return healthEnabled;
	}

	@Override
	public String getDamageFormat() {
		return damageFormat;
	}

	@Override
	public boolean isDamageEnabled() {
		return damageEnabled;
	}

	@Override
	public String getRegenerationFormat() {
		return regenerationFormat;
	}

	@Override
	public boolean isRegenerationEnabled() {
		return regenerationEnabled;
	}

	@Override
	public String getArmorFormat() {
		return armorFormat;
	}

	@Override
	public boolean isArmorEnabled() {
		return armorEnabled;
	}

	@Override
	public String getCriticalRateFormat() {
		return criticalRateFormat;
	}

	@Override
	public boolean isCriticalRateEnabled() {
		return criticalRateEnabled;
	}

	@Override
	public String getCriticalDamageFormat() {
		return criticalDamageFormat;
	}

	@Override
	public boolean isCriticalDamageEnabled() {
		return criticalDamageEnabled;
	}

	@Override
	public String getLevelRequirementFormat() {
		return levelRequirementFormat;
	}

	@Override
	public boolean isLevelRequirementEnabled() {
		return levelRequirementEnabled;
	}

	@Override
	public double getBasePlayerHealth() {
		return basePlayerHealth;
	}

	@Override
	public String getPoisonImmunityFormat() {
		return poisonImmunityFormat;
	}

	@Override
	public boolean isPoisonImmunityEnabled() {
		return poisonImmunityEnabled;
	}

	@Override
	public String getFireImmunityFormat() {
		return fireImmunityFormat;
	}

	@Override
	public boolean isFireImmunityEnabled() {
		return fireImmunityEnabled;
	}

	@Override
	public String getWitherImmunityFormat() {
		return witherImmunityFormat;
	}

	@Override
	public boolean isWitherImmunityEnabled() {
		return witherImmunityEnabled;
	}

	@Override
	public int getSecondsBetweenHealthUpdates() {
		return secondsBetweenHealthUpdates;
	}

	@Override
	public String getMeleeDamageFormat() {
		return meleeDamageFormat;
	}

	@Override
	public boolean isMeleeDamageEnabled() {
		return meleeDamageEnabled;
	}

	@Override
	public String getRangedDamageFormat() {
		return rangedDamageFormat;
	}

	@Override
	public boolean isRangedDamageEnabled() {
		return rangedDamageEnabled;
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
	public String getStunRateFormat() {
		return stunRateFormat;
	}

	@Override
	public boolean isStunRateEnabled() {
		return stunRateEnabled;
	}

	@Override
	public String getStunLengthFormat() {
		return stunLengthFormat;
	}

	@Override
	public boolean isStunLengthEnabled() {
		return stunLengthEnabled;
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
	public String getDodgeRateFormat() {
		return dodgeRateFormat;
	}

	@Override
	public boolean isDodgeRateEnabled() {
		return dodgeRateEnabled;
	}

	@Override
	public double getBaseDodgeRate() {
		return baseDodgeRate;
	}

	@Override
	public double getMaximumCriticalRate() {
		return maximumCriticalRate;
	}

	@Override
	public double getMaximumCriticalDamage() {
		return maximumCriticalDamage;
	}

	@Override
	public double getMaximumStunRate() {
		return maximumStunRate;
	}

	@Override
	public double getMaximumDodgeRate() {
		return maximumDodgeRate;
	}

	public void save() {
		if (!getPlugin().getConfigYAML().isSet("version")) {
			getPlugin().getConfigYAML().set("version", getPlugin().getConfigYAML().getVersion());
			getPlugin().getConfigYAML().set("options.health-modification-enabled", healthModificationEnabled);
			getPlugin().getConfigYAML().set("options.base-player-health", basePlayerHealth);
			getPlugin().getConfigYAML().set("options.base-critical-rate", baseCriticalRate);
			getPlugin().getConfigYAML().set("options.base-critical-damage", baseCriticalDamage);
			getPlugin().getConfigYAML().set("options.base-stun-rate", baseStunRate);
			getPlugin().getConfigYAML().set("options.base-stun-length", baseStunLength);
			getPlugin().getConfigYAML().set("options.seconds-between-health-updates", secondsBetweenHealthUpdates);
			getPlugin().getConfigYAML().set("core-stats.health.format", healthFormat);
			getPlugin().getConfigYAML().set("core-stats.damage.format", damageFormat);
			getPlugin().getConfigYAML().set("core-stats.ranged-damage.format", rangedDamageFormat);
			getPlugin().getConfigYAML().set("core-stats.melee-damage.format", meleeDamageFormat);
			getPlugin().getConfigYAML().set("core-stats.regeneration.format", regenerationFormat);
			getPlugin().getConfigYAML().set("core-stats.armor.format", armorFormat);
			getPlugin().getConfigYAML().set("core-stats.armor-penetration.format", armorPenetrationFormat);
			getPlugin().getConfigYAML().set("core-stats.critical-rate.format", criticalRateFormat);
			getPlugin().getConfigYAML().set("core-stats.critical-rate.max-value", maximumCriticalRate);
			getPlugin().getConfigYAML().set("core-stats.critical-damage.format", criticalDamageFormat);
			getPlugin().getConfigYAML().set("core-stats.critical-damage.max-value", maximumCriticalRate);
			getPlugin().getConfigYAML().set("core-stats.level-requirement.format", levelRequirementFormat);
			getPlugin().getConfigYAML().set("core-stats.stun-rate.format", stunRateFormat);
			getPlugin().getConfigYAML().set("core-stats.stun-rate.max-value", maximumStunRate);
			getPlugin().getConfigYAML().set("core-stats.stun-length.format", stunLengthFormat);
			getPlugin().getConfigYAML().set("core-stats.dodge-rate.format", dodgeRateFormat);
			getPlugin().getConfigYAML().set("core-stats.dodge-rate.max-value", maximumDodgeRate);
			getPlugin().getConfigYAML().set("core-stats.poison-immunity.format", poisonImmunityFormat);
			getPlugin().getConfigYAML().set("core-stats.fire-immunity.format", fireImmunityFormat);
			getPlugin().getConfigYAML().set("core-stats.wither-immunity.format", witherImmunityFormat);
		}
		getPlugin().getConfigYAML().save();
	}
}
