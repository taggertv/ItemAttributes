package net.nunnerycode.bukkit.itemattributes.api.attributes;

public interface Attribute {

	boolean isEnabled();

	void setEnabled(boolean b);

	double getMaxValue();

	void setMaxValue(double d);

	boolean isPercentage();

	void setPercentage(boolean b);

	String getFormat();

	void setFormat(String s);

	String getName();

}
