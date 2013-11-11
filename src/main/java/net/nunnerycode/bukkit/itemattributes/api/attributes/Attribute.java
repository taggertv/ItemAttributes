package net.nunnerycode.bukkit.itemattributes.api.attributes;

public interface Attribute {

	String getName();

	String getConfigurationString();

	String getFormat();

	boolean isEnabled();

	double getMaxValue();

}
