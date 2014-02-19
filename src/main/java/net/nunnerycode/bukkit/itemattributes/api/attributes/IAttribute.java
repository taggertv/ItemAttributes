package net.nunnerycode.bukkit.itemattributes.api.attributes;

import org.bukkit.Effect;
import org.bukkit.Sound;

public interface IAttribute {

  String getName();

  boolean isEnabled();

  void setEnabled(boolean b);

  boolean isPercentage();

  void setPercentage(boolean b);

  String getFormat();

  void setFormat(String s);

  double getPercentageDivisor();

  void setPercentageDivisor(double d);

  Sound getSound();

  void setSound(Sound s);

  Effect getEffect();

  void setEffect(Effect e);

  double getBaseValue();

  void setBaseValue(double d);

  double getMinimumValue();

  void setMinimumValue(double d);

  double getMaximumValue();

  void setMaximumValue(double d);

}
