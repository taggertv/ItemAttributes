package net.nunnerycode.bukkit.itemattributes.api.managers;

import net.nunnerycode.bukkit.itemattributes.api.ItemAttributes;
import net.nunnerycode.bukkit.itemattributes.api.attributes.Attribute;

public interface SettingsManager {

	ItemAttributes getPlugin();

	double getBasePlayerHealth();

	int getSecondsBetweenHealthUpdates();

	double getBaseCriticalRate();

	double getBaseCriticalDamage();

	double getBaseStunRate();

	int getBaseStunLength();

	double getBaseDodgeRate();

	Attribute getAttribute(String name);

}
