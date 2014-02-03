package net.nunnerycode.bukkit.itemattributes.api.settings;

public interface ISettings {

  /**
   * Gets and returns a value for a key, returning defaultValue if key doesn't have a value.
   * @param key key to check for
   * @param defaultValue default value to return if no value found
   * @return value for key
   */
  Object getSettingValue(String key, Object defaultValue);

  /**
   * Sets the value for a key
   * @param key key to set for
   * @param value value to set for the key
   */
  void setSettingValue(String key, Object value);

}
