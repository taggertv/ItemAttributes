package net.nunnerycode.bukkit.itemattributes.attributes;

import net.nunnerycode.bukkit.itemattributes.api.attributes.IAttributeValue;
import net.nunnerycode.bukkit.itemattributes.api.attributes.IAttributeValueList;

import java.util.Collections;
import java.util.List;

public final class ItemAttributeValueList extends IAttributeValueList {

  @Override
  public double asDouble() {
    double d = 0;
    for (IAttributeValue iav : this) {
      d += iav.asDouble();
    }
    return d;
  }

  @Override
  public int asInt() {
    int d = 0;
    for (IAttributeValue iav : this) {
      d += iav.asInt();
    }
    return d;
  }

  @Override
  public long asLong() {
    long d = 0;
    for (IAttributeValue iav : this) {
      d += iav.asLong();
    }
    return d;
  }

  @Override
  public boolean asBoolean() {
    boolean b = true;
    for (IAttributeValue iav : this) {
      b = b && iav.asBoolean();
    }
    return b;
  }

  @Override
  public List<String> asStrings() {
    List<String> list = Collections.emptyList();
    for (IAttributeValue iav : this) {
      list.add(iav.asString());
    }
    return list;
  }

}
