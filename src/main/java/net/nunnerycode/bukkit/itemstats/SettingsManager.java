package net.nunnerycode.bukkit.itemstats;

public final class SettingsManager {

	private ItemStatsPlugin plugin;
	private double basePlayerHealth;
	private String healthFormat;
	private String damageFormat;
	private String regenerationFormat;
	private String armorFormat;
	private String criticalRateFormat;
	private String criticalDamageFormat;
	private String levelRequirementFormat;
	private String poisonImmunityFormat;
	private String fireImmunityFormat;
	private String witherImmunityFormat;

	public SettingsManager(ItemStatsPlugin plugin) {
		this.plugin = plugin;
	}

	public void load() {
		basePlayerHealth = getPlugin().getConfigYAML().getDouble("options.base-player-health", 20.0);
		healthFormat = getPlugin().getConfigYAML().getString("core-stats.health.format", "%value% Health");
		damageFormat = getPlugin().getConfigYAML().getString("core-stats.damage.format", "%value% Damage");
		regenerationFormat = getPlugin().getConfigYAML().getString("core-stats.regeneration.format",
				"%value% Regeneration");
		armorFormat = getPlugin().getConfigYAML().getString("core-stats.armor.format", "%value% Armor");
		criticalRateFormat = getPlugin().getConfigYAML().getString("core-stats.critical-rate.format",
				"%value% Critical Rate");
		criticalDamageFormat = getPlugin().getConfigYAML().getString("core-stats.critical-damage.format",
				"%value% Critical Damage");
		levelRequirementFormat = getPlugin().getConfigYAML().getString("core-stats.level-requirement.format",
				"Level Required: %value%");
		poisonImmunityFormat = getPlugin().getConfigYAML().getString("core-stats.poison-immunity.format",
				"Poison Immunity");
		fireImmunityFormat = getPlugin().getConfigYAML().getString("core-stats.fire-immunity.format",
				"Fire Immunity");
		witherImmunityFormat = getPlugin().getConfigYAML().getString("core-stats.wither-immunity.format",
				"Wither Immunity");
	}

	public ItemStatsPlugin getPlugin() {
		return plugin;
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
}
