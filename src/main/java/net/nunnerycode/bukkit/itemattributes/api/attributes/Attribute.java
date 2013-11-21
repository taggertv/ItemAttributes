package net.nunnerycode.bukkit.itemattributes.api.attributes;

import org.bukkit.Effect;
import org.bukkit.Sound;

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

	Sound getSound();

	void setSound(Sound s);

	double getBaseValue();

	void setBaseValue(double d);

	Effect getEffect();

	void setEffect(Effect e);

}
