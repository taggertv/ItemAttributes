package net.nunnerycode.bukkit.itemattributes.api.managers;

import net.nunnerycode.bukkit.itemattributes.api.ItemAttributes;

public interface SettingsManager {
	ItemAttributes getPlugin();

	String getArmorPenetrationFormat();

	boolean isArmorPenetrationEnabled();

	String getHealthFormat();

	boolean isHealthEnabled();

	String getDamageFormat();

	boolean isDamageEnabled();

	String getRegenerationFormat();

	boolean isRegenerationEnabled();

	String getArmorFormat();

	boolean isArmorEnabled();

	String getCriticalRateFormat();

	boolean isCriticalRateEnabled();

	String getCriticalDamageFormat();

	boolean isCriticalDamageEnabled();

	String getLevelRequirementFormat();

	boolean isLevelRequirementEnabled();

	double getBasePlayerHealth();

	String getPoisonImmunityFormat();

	boolean isPoisonImmunityEnabled();

	String getFireImmunityFormat();

	boolean isFireImmunityEnabled();

	String getWitherImmunityFormat();

	boolean isWitherImmunityEnabled();

	int getSecondsBetweenHealthUpdates();

	String getMeleeDamageFormat();

	boolean isMeleeDamageEnabled();

	String getRangedDamageFormat();

	boolean isRangedDamageEnabled();

	double getBaseCriticalRate();

	double getBaseCriticalDamage();

	String getStunRateFormat();

	boolean isStunRateEnabled();

	String getStunLengthFormat();

	boolean isStunLengthEnabled();

	double getBaseStunRate();

	int getBaseStunLength();

	String getDodgeRateFormat();

	boolean isDodgeRateEnabled();

	double getBaseDodgeRate();

	double getMaximumCriticalRate();

	double getMaximumCriticalDamage();

	double getMaximumStunRate();

	double getMaximumDodgeRate();

}
