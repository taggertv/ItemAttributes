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
   * </br>
   * Throws an IllegalArgumentException if the value does not pass match the flag's accepted type.
   * @param flag AttributeFlag to set for
   * @param value value to set as
   * @throws java.lang.IllegalArgumentException when Object does not match type
   */
  void setFlagValue(AttributeFlag flag, Object value) throws IllegalArgumentException;

}
