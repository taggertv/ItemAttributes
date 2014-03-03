package net.nunnerycode.bukkit.itemattributes.attributes;

import net.nunnerycode.bukkit.itemattributes.api.attributes.IAttribute;

import org.bukkit.Effect;
import org.bukkit.Sound;

public final class ItemAttribute implements IAttribute {

  private String name;
  private String displayName;
  private String description;
  private boolean enabled;
  private Sound sound;
  private Effect effect;
  private boolean percentage;
  private double percentageDivisor;
  private double baseValue;
  private double minimumValue;
  private double maximumValue;
  private String format;

  public ItemAttribute(String name) {
    this.name = name != null ? name : "";
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    ItemAttribute that = (ItemAttribute) o;

    return name.equals(that.name);
  }

  @Override
  public int hashCode() {
    return name.hashCode();
  }

  @Override
  public boolean isEnabled() {
    return enabled;
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

  @Override
  public Sound getSound() {
    return sound;
  }

  public void setSound(Sound sound) {
    this.sound = sound;
  }

  @Override
  public Effect getEffect() {
    return effect;
  }

  public void setEffect(Effect effect) {
    this.effect = effect;
  }

  @Override
  public boolean isPercentage() {
    return percentage;
  }

  public void setPercentage(boolean percentage) {
    this.percentage = percentage;
  }

  @Override
  public double getPercentageDivisor() {
    return percentageDivisor;
  }

  public void setPercentageDivisor(double percentageDivisor) {
    this.percentageDivisor = percentageDivisor;
  }

  @Override
  public double getBaseValue() {
    return baseValue;
  }

  public void setBaseValue(double baseValue) {
    this.baseValue = baseValue;
  }

  @Override
  public double getMinimumValue() {
    return minimumValue;
  }

  public void setMinimumValue(double minimumValue) {
    this.minimumValue = minimumValue;
  }

  @Override
  public double getMaximumValue() {
    return maximumValue;
  }

  public void setMaximumValue(double maximumValue) {
    this.maximumValue = maximumValue;
  }

  @Override
  public String getFormat() {
    return format;
  }

  public void setFormat(String format) {
    this.format = format;
  }

  @Override
  public String getDisplayName() {
    return displayName;
  }

  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }

  @Override
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}
