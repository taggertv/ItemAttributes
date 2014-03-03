package net.nunnerycode.bukkit.itemattributes.api.attributes;

import org.bukkit.Effect;
import org.bukkit.Sound;

public interface IAttributeBuilder {

  IAttributeBuilder withEnabled(boolean b);

  IAttributeBuilder withPercentage(boolean b);

  IAttributeBuilder withFormat(String s);

  IAttributeBuilder withPercentageDivisor(double d);

  IAttributeBuilder withSound(Sound s);

  IAttributeBuilder withEffect(Effect e);

  IAttributeBuilder withBaseValue(double d);

  IAttributeBuilder withMinimumValue(double d);

  IAttributeBuilder withMaximumValue(double d);

  IAttributeBuilder withDisplayName(String s);

  IAttributeBuilder withDescription(String s);

  IAttribute build();

}
