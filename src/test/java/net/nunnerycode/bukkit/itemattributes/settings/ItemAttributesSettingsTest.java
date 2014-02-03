package net.nunnerycode.bukkit.itemattributes.settings;

import net.nunnerycode.bukkit.itemattributes.api.settings.ISettings;

import org.junit.Assert;
import org.junit.Test;

public class ItemAttributesSettingsTest {

  @Test
  public void doesGetSettingValueReturnDefaultIfNotSet() {
    ISettings settings = new ItemAttributesSettings();

    int expected = 0;
    Object actual = settings.getSettingValue("test-key", expected);

    Assert.assertNotNull(actual);
    Assert.assertEquals(expected, actual);
  }

  @Test
  public void doesGetSettingValueReturnSetValue() {
    ISettings settings = new ItemAttributesSettings();

    String key = "test-key";

    int expected = 0;
    settings.setSettingValue(key, expected);

    Object actual = settings.getSettingValue(key, expected + 1);

    Assert.assertNotNull(actual);
    Assert.assertEquals(expected, actual);
  }

}
