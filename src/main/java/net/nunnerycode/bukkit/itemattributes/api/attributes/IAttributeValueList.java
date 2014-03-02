package net.nunnerycode.bukkit.itemattributes.api.attributes;

import java.util.ArrayList;
import java.util.List;

public abstract class IAttributeValueList extends ArrayList<IAttributeValue> {

  public abstract double asDouble();

  public abstract int asInt();

  public abstract long asLong();

  public abstract boolean asBoolean();

  public abstract List<String> asStrings();

}
