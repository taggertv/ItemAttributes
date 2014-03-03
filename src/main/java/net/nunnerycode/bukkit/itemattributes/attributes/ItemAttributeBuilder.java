package net.nunnerycode.bukkit.itemattributes.attributes;

import net.nunnerycode.bukkit.itemattributes.api.attributes.IAttribute;
import net.nunnerycode.bukkit.itemattributes.api.attributes.IAttributeBuilder;

import org.apache.commons.lang3.Validate;
import org.bukkit.Effect;
import org.bukkit.Sound;

public final class ItemAttributeBuilder implements IAttributeBuilder {

  private ItemAttribute itemAttribute;

  public ItemAttributeBuilder(String name) {
    Validate.notNull(name, "String cannot be null");
    itemAttribute = new ItemAttribute(name);
  }

  @Override
  public IAttributeBuilder withEnabled(boolean b) {
    itemAttribute.setEnabled(b);
    return this;
  }

  @Override
  public IAttributeBuilder withPercentage(boolean b) {
    itemAttribute.setPercentage(b);
    return this;
  }

  @Override
  public IAttributeBuilder withFormat(String s) {
    itemAttribute.setFormat(s);
    return this;
  }

  @Override
  public IAttributeBuilder withPercentageDivisor(double d) {
    itemAttribute.setPercentageDivisor(d);
    return this;
  }

  @Override
  public IAttributeBuilder withSound(Sound s) {
    itemAttribute.setSound(s);
    return this;
  }

  @Override
  public IAttributeBuilder withEffect(Effect e) {
    itemAttribute.setEffect(e);
    return this;
  }

  @Override
  public IAttributeBuilder withBaseValue(double d) {
    itemAttribute.setBaseValue(d);
    return this;
  }

  @Override
  public IAttributeBuilder withMinimumValue(double d) {
    itemAttribute.setMinimumValue(d);
    return this;
  }

  @Override
  public IAttributeBuilder withMaximumValue(double d) {
    itemAttribute.setMaximumValue(d);
    return this;
  }

  @Override
  public IAttributeBuilder withDisplayName(String s) {
    itemAttribute.setDisplayName(s);
    return this;
  }

  @Override
  public IAttributeBuilder withDescription(String s) {
    itemAttribute.setDescription(s);
    return this;
  }

  @Override
  public IAttribute build() {
    return itemAttribute;
  }

}
