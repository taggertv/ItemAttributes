package net.nunnerycode.bukkit.itemattributes.api.attributes;

import org.bukkit.Effect;
import org.bukkit.Sound;

public interface Attribute {

	boolean isEnabled();

	void setEnabled(boolean b);

	boolean isDefaultEnabled();

	@Deprecated
	double getMaxValue();

	@Deprecated
	void setMaxValue(double d);

	double getMaxValueMobs();

	void setMaxValueMobs(double d);

	double getMaxValuePlayers();

	void setMaxValuePlayers(double d);

	boolean isPercentage();

	void setPercentage(boolean b);

	String getFormat();

	void setFormat(String s);

	String getName();

	Sound getSound();

	void setSound(Sound s);

	@Deprecated
	double getBaseValue();

	@Deprecated
	void setBaseValue(double d);

	double getMobsBaseValue();

	void setMobsBaseValue(double d);

	double getPlayersBaseValue();

	void setPlayersBaseValue(double d);

	Effect getEffect();

	void setEffect(Effect e);

	boolean isAffectsMobs();

	void setAffectsMobs(boolean b);

	boolean isAffectsPlayers();

	void setAffectsPlayers(boolean b);

}
