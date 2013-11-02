package net.nunnerycode.bukkit.itemattributes.api.managers;

import net.nunnerycode.bukkit.itemattributes.api.ItemAttributes;

public interface SettingsManager {
	ItemAttributes getPlugin();

	String getArmorPenetrationFormat();

	String getHealthFormat();

	String getDamageFormat();

	String getRegenerationFormat();

	String getArmorFormat();

	String getCriticalRateFormat();

	String getCriticalDamageFormat();

	String getLevelRequirementFormat();

	double getBasePlayerHealth();

	String getPoisonImmunityFormat();

	String getFireImmunityFormat();

	String getWitherImmunityFormat();

	int getSecondsBetweenHealthUpdates();

	String getMeleeDamageFormat();

	String getRangedDamageFormat();

	double getBaseCriticalRate();

	double getBaseCriticalDamage();

	String getStunRateFormat();

	String getStunLengthFormat();

	double getBaseStunRate();

	int getBaseStunLength();

	String getDodgeRateFormat();

	double getBaseDodgeRate();
}
