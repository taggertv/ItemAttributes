package net.nunnerycode.bukkit.itemattributes.attributes;

import net.nunnerycode.bukkit.itemattributes.api.attributes.IAttributeValue;

public final class ItemAttributeValue implements IAttributeValue {

  private final Object object;

  public ItemAttributeValue(Object object) {
    this.object = object;
  }

  @Override
  public double asDouble() {
    if (object instanceof Double) {
      return (Double) object;
    }
    return 0;
  }

  @Override
  public int asInt() {
    if (object instanceof Integer) {
      return (Integer) object;
    }
    return 0;
  }

  @Override
  public long asLong() {
    if (object instanceof Long) {
      return (Long) object;
    }
    return 0;
  }

  @Override
  public String asString() {
    return String.valueOf(object);
  }

  @Override
  public boolean asBoolean() {
    if (object instanceof Boolean) {
      return (Boolean) object;
    }
    return false;
  }

}
