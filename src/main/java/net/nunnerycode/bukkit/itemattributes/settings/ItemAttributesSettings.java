package net.nunnerycode.bukkit.itemattributes.settings;

import net.nunnerycode.bukkit.itemattributes.api.settings.ISettings;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class ItemAttributesSettings implements ISettings {

  private Map<String, Object> settingMap;

  public ItemAttributesSettings() {
    settingMap = new ConcurrentHashMap<>();
  }

  /**
   * Gets and returns a value for a key, returning defaultValue if key doesn't have a value.
   *
   * @param key          key to check for
   * @param defaultValue default value to return if no value found
   * @return value for key
   */
  @Override
  public Object getSettingValue(String key, Object defaultValue) {
    return settingMap.containsKey(key) ? settingMap.get(key) : defaultValue;
  }

  /**
   * Sets the value for a key
   *
   * @param key   key to set for
   * @param value value to set for the key
   */
  @Override
  public void setSettingValue(String key, Object value) {
    settingMap.put(key, value);
  }

}
