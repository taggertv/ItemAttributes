package net.nunnerycode.bukkit.itemattributes.api.attributes;

import java.util.List;

public interface IAttributeValueList extends List<IAttributeValue> {

  double asDouble();

  int asInt();

  long asLong();

  boolean asBoolean();

  List<String> asStrings();

}
