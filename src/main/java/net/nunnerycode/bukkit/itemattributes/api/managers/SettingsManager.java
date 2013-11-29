package net.nunnerycode.bukkit.itemattributes.api.managers;

import java.util.Set;
import net.nunnerycode.bukkit.itemattributes.api.ItemAttributes;
import net.nunnerycode.bukkit.itemattributes.api.attributes.Attribute;

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

	Double[] getAllowableDiceSizes();

	void addAllowableDiceSizes(Double... d);

	void removeAllowableDiceSizes(Double... d);

}
