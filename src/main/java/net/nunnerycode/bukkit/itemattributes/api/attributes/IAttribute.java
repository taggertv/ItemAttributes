package net.nunnerycode.bukkit.itemattributes.api.attributes;

public interface IAttribute {

  String getName();

  Object getFlagValue(AttributeFlag flag);

  void setFlagValue(AttributeFlag flag, Object value);

}
