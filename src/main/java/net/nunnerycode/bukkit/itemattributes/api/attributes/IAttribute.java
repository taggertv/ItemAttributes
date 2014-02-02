package net.nunnerycode.bukkit.itemattributes.api.attributes;

public interface IAttribute {

  /**
   * Gets and returns the name of the IAttribute.
   * @return name of the IAttribute
   */
  String getName();

  /**
   * Gets the value of the AttributeFlag for the IAttribute. Returns the defaultValue if the
   * AttributeFlag is not set.
   * @param flag AttributeFlag to check against
   * @param defaultValue default value of the AttributeFlag
   * @return value of flag or defaultValue
   */
  Object getFlagValue(AttributeFlag flag, Object defaultValue);

  /**
   * Sets the value of the AttributeFlag.
   * @param flag AttributeFlag to set for
   * @param value value to set as
   */
  void setFlagValue(AttributeFlag flag, Object value);

}
