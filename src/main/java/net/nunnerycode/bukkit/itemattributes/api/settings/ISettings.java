package net.nunnerycode.bukkit.itemattributes.api.settings;

public interface ISettings {

  Object getSettingValue(String key, Object defaultValue);

  void setSettingValue(String key, Object value);

}
