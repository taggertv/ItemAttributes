package net.nunnerycode.bukkit.itemattributes.api.managers;

import java.util.Set;
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

	boolean isItemOnlyDamageSystemEnabled();

	double getItemOnlyDamageSystemBaseDamage();

	boolean addAttribute(String name, Attribute attribute);

	boolean removeAttribute(String name);

	Set<Attribute> getLoadedAttributes();

	Set<Attribute> getLoadedCoreAttributes();

	Set<Attribute> getLoadedExtraAttributes();

	boolean isPluginCompatible();

}
