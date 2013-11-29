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

	double getDefaultMaxValueMobs();

	double getMaxValuePlayers();

	void setMaxValuePlayers(double d);

	double getDefaultMaxValuePlayers();

	boolean isPercentage();

	void setPercentage(boolean b);

	boolean isDefaultPercentage();

	String getFormat();

	void setFormat(String s);

	String getDefaultFormat();

	String getName();

	Sound getSound();

	void setSound(Sound s);

	Sound getDefaultSound();

	@Deprecated
	double getBaseValue();

	@Deprecated
	void setBaseValue(double d);

	double getMobsBaseValue();

	void setMobsBaseValue(double d);

	double getDefaultMobsBaseValue();

	double getPlayersBaseValue();

	void setPlayersBaseValue(double d);

	double getDefaultPlayersBaseValue();

	Effect getEffect();

	void setEffect(Effect e);

	Effect getDefaultEffect();

	boolean isAffectsMobs();

	void setAffectsMobs(boolean b);

	boolean isDefaultAffectsMobs();

	boolean isAffectsPlayers();

	void setAffectsPlayers(boolean b);

	boolean isDefaultAffectsPlayers();

}
