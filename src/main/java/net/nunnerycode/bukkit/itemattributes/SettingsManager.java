package net.nunnerycode.bukkit.itemattributes;

public final class SettingsManager {

	private ItemAttributesPlugin plugin;
	private double basePlayerHealth;
	private double baseCriticalRate;
	private double baseCriticalDamage;
	private double baseStunRate;
	private int baseStunLength;
	private int secondsBetweenHealthUpdates;
	private String healthFormat;
	private String damageFormat;
	private String meleeDamageFormat;
	private String rangedDamageFormat;
	private String regenerationFormat;
	private String armorFormat;
	private String armorPenetrationFormat;
	private String criticalRateFormat;
	private String criticalDamageFormat;
	private String stunRateFormat;
	private String stunLengthFormat;
	private String levelRequirementFormat;
	private String poisonImmunityFormat;
	private String fireImmunityFormat;
	private String witherImmunityFormat;

	public SettingsManager(ItemAttributesPlugin plugin) {
		this.plugin = plugin;
	}

	public void load() {
		basePlayerHealth = getPlugin().getConfigYAML().getDouble("options.base-player-health", 20.0);
		baseCriticalRate = getPlugin().getConfigYAML().getDouble("options.base-critical-rate", 0.05);
		baseCriticalDamage = getPlugin().getConfigYAML().getDouble("options.base-critical-damage", 0.2);
		baseStunRate = getPlugin().getConfigYAML().getDouble("options.base-stun-rate", 0.05);
		baseStunLength = getPlugin().getConfigYAML().getInt("options.base-stun-length", 1);
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
		criticalDamageFormat = getPlugin().getConfigYAML().getString("core-stats.critical-damage.format",
				"%value% Critical Damage");
		levelRequirementFormat = getPlugin().getConfigYAML().getString("core-stats.level-requirement.format",
				"Level Required: %value%");
		stunRateFormat = getPlugin().getConfigYAML().getString("core-stats.stun-rate.format", "%value% Stun Rate");
		stunLengthFormat = getPlugin().getConfigYAML().getString("core-stats.stun-length.format",
				"%value% Stun Length");
		poisonImmunityFormat = getPlugin().getConfigYAML().getString("core-stats.poison-immunity.format",
				"Poison Immunity");
		fireImmunityFormat = getPlugin().getConfigYAML().getString("core-stats.fire-immunity.format",
				"Fire Immunity");
		witherImmunityFormat = getPlugin().getConfigYAML().getString("core-stats.wither-immunity.format",
				"Wither Immunity");
	}

	public ItemAttributesPlugin getPlugin() {
		return plugin;
	}

	public String getArmorPenetrationFormat() {
		return armorPenetrationFormat;
	}

	public void save() {
		getPlugin().getConfigYAML().set("version", getPlugin().getConfigYAML().getVersion());
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
		getPlugin().getConfigYAML().set("core-stats.critical-damage.format", criticalDamageFormat);
		getPlugin().getConfigYAML().set("core-stats.level-requirement.format", levelRequirementFormat);
		getPlugin().getConfigYAML().set("core-stats.stun-rate.format", stunRateFormat);
		getPlugin().getConfigYAML().set("core-stats.stun-length.format", stunLengthFormat);
		getPlugin().getConfigYAML().set("core-stats.poison-immunity.format", poisonImmunityFormat);
		getPlugin().getConfigYAML().set("core-stats.fire-immunity.format", fireImmunityFormat);
		getPlugin().getConfigYAML().set("core-stats.wither-immunity.format", witherImmunityFormat);
		getPlugin().getConfigYAML().save();
	}

	public String getHealthFormat() {
		return healthFormat;
	}

	public String getDamageFormat() {
		return damageFormat;
	}

	public String getRegenerationFormat() {
		return regenerationFormat;
	}

	public String getArmorFormat() {
		return armorFormat;
	}

	public String getCriticalRateFormat() {
		return criticalRateFormat;
	}

	public String getCriticalDamageFormat() {
		return criticalDamageFormat;
	}

	public String getLevelRequirementFormat() {
		return levelRequirementFormat;
	}

	public double getBasePlayerHealth() {
		return basePlayerHealth;
	}

	public String getPoisonImmunityFormat() {
		return poisonImmunityFormat;
	}

	public String getFireImmunityFormat() {
		return fireImmunityFormat;
	}

	public String getWitherImmunityFormat() {
		return witherImmunityFormat;
	}

	public int getSecondsBetweenHealthUpdates() {
		return secondsBetweenHealthUpdates;
	}

	public String getMeleeDamageFormat() {
		return meleeDamageFormat;
	}

	public String getRangedDamageFormat() {
		return rangedDamageFormat;
	}

	public double getBaseCriticalRate() {
		return baseCriticalRate;
	}

	public double getBaseCriticalDamage() {
		return baseCriticalDamage;
	}

	public String getStunRateFormat() {
		return stunRateFormat;
	}

	public String getStunLengthFormat() {
		return stunLengthFormat;
	}

	public double getBaseStunRate() {
		return baseStunRate;
	}

	public int getBaseStunLength() {
		return baseStunLength;
	}
}
