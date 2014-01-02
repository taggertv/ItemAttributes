package net.nunnerycode.bukkit.itemattributes.api.managers;

import net.nunnerycode.bukkit.itemattributes.api.ItemAttributes;
import net.nunnerycode.bukkit.itemattributes.api.attributes.Attribute;

import java.util.Set;

public interface SettingsManager {

	ItemAttributes getPlugin();

	@Deprecated
	double getBasePlayerHealth();

	int getSecondsBetweenHealthUpdates();

	@Deprecated
	double getBaseCriticalRate();

	@Deprecated
	double getBaseCriticalDamage();

	@Deprecated
	double getBaseStunRate();

	@Deprecated
	int getBaseStunLength();

	@Deprecated
	double getBaseDodgeRate();

	Attribute getAttribute(String name);

	boolean isItemOnlyDamageSystemEnabled();

	double getItemOnlyDamageSystemBaseDamage();

	boolean addAttribute(String name, Attribute attribute);

	boolean removeAttribute(String name);

	Set<Attribute> getLoadedAttributes();

	Set<Attribute> getLoadedCoreAttributes();

	Set<Attribute> getLoadedExtraAttributes();

	boolean isPluginCompatible();

}
