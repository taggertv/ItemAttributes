package net.nunnerycode.bukkit.itemattributes.attributes;

import net.nunnerycode.bukkit.itemattributes.api.attributes.AttributeFlag;
import net.nunnerycode.bukkit.itemattributes.api.attributes.IAttribute;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class ItemAttribute implements IAttribute {

  private String name;
  private Map<AttributeFlag, Object> flagValueMap;

  public ItemAttribute(String name) {
    this.name = name != null ? name : "";
    flagValueMap = new ConcurrentHashMap<>();
  }

  /**
   * Gets and returns the name of the IAttribute.
   *
   * @return name of the IAttribute
   */
  @Override
  public String getName() {
    return name;
  }

  /**
   * Gets the value of the AttributeFlag for the IAttribute. Returns the defaultValue if the
   * AttributeFlag is not set.
   *
   * @param flag         AttributeFlag to check against
   * @param defaultValue default value of the AttributeFlag
   * @return value of flag or defaultValue
   */
  @Override
  public Object getFlagValue(AttributeFlag flag, Object defaultValue) {
    return flagValueMap.containsKey(flag) ? flagValueMap.get(flag) : defaultValue;
  }

  /**
   * Sets the value of the AttributeFlag. </br> Throws an IllegalArgumentException if the value does
   * not pass match the flag's accepted type.
   *
   * @param flag  AttributeFlag to set for
   * @param value value to set as
   * @throws java.lang.IllegalArgumentException when Object does not match type
   */
  @Override
  public void setFlagValue(AttributeFlag flag, Object value) throws IllegalArgumentException {
    if (value == null && flagValueMap.containsKey(flag)) {
      flagValueMap.remove(flag);
      return;
    }
    if (!flag.matchesType(value)) {
      throw new IllegalArgumentException("value does not match AttributeFlag's type: " + flag
          .getMatchingType().getName());
    }
    flagValueMap.put(flag, value);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    ItemAttribute that = (ItemAttribute) o;

    return name.equals(that.name);
  }

  @Override
  public int hashCode() {
    return name.hashCode();
  }

}
