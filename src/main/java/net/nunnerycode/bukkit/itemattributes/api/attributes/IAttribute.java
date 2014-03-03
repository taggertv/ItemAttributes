package net.nunnerycode.bukkit.itemattributes.api.attributes;

import org.bukkit.Effect;
import org.bukkit.Sound;

public interface IAttribute {

  String getName();

  boolean isEnabled();

  boolean isPercentage();

  String getFormat();

  double getPercentageDivisor();

  Sound getSound();

  Effect getEffect();

  double getBaseValue();

  double getMinimumValue();

  double getMaximumValue();

  String getDisplayName();

  String getDescription();
}
