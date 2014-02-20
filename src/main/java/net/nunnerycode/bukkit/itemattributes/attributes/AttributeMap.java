package net.nunnerycode.bukkit.itemattributes.attributes;

import net.nunnerycode.bukkit.itemattributes.api.attributes.IAttribute;

import java.util.concurrent.ConcurrentHashMap;

public final class AttributeMap extends ConcurrentHashMap<String, IAttribute> {

  private AttributeMap() {
    // do nothing
  }

  private static AttributeMap instance;

  public static AttributeMap getInstance() {
    if (instance == null) {
      instance = new AttributeMap();
    }
    return instance;
  }

}
