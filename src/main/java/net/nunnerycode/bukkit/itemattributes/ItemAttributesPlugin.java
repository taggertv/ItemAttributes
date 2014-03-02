package net.nunnerycode.bukkit.itemattributes;

import net.nunnerycode.bukkit.itemattributes.api.attributes.IAttribute;
import net.nunnerycode.bukkit.itemattributes.api.attributes.IAttributeBuilder;
import net.nunnerycode.bukkit.itemattributes.api.attributes.IAttributeHandler;
import net.nunnerycode.bukkit.itemattributes.api.settings.ISettings;
import net.nunnerycode.bukkit.itemattributes.attributes.AttributeMap;
import net.nunnerycode.bukkit.itemattributes.attributes.ItemAttributeBuilder;
import net.nunnerycode.bukkit.itemattributes.attributes.ItemAttributeHandler;
import net.nunnerycode.bukkit.itemattributes.commands.ItemAttributesCommands;
import net.nunnerycode.bukkit.itemattributes.settings.ItemAttributesSettings;
import net.nunnerycode.bukkit.libraries.ivory.config.VersionedIvoryYamlConfiguration;
import net.nunnerycode.java.libraries.cannonball.DebugPrinter;

import org.bukkit.Effect;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import se.ranzdo.bukkit.methodcommand.CommandHandler;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import static net.nunnerycode.bukkit.libraries.ivory.config.VersionedIvoryYamlConfiguration.VersionUpdateType;

public final class ItemAttributesPlugin extends JavaPlugin {

  private IAttributeHandler attributeHandler;
  private ISettings settings;
  private DebugPrinter debugPrinter;
  private VersionedIvoryYamlConfiguration attributesYAML;
  private VersionedIvoryYamlConfiguration configYAML;
  private VersionedIvoryYamlConfiguration languageYAML;
  private VersionedIvoryYamlConfiguration permissionsYAML;

  public IAttributeHandler getAttributeHandler() {
    return attributeHandler;
  }

  public ISettings getSettings() {
    return settings;
  }

  @Override
  public void onEnable() {
    settings = new ItemAttributesSettings();
    attributeHandler = new ItemAttributeHandler();

    attributesYAML =
        new VersionedIvoryYamlConfiguration(new File(getDataFolder(), "attributes.yml"),
                                            getResource("attributes.yml"),
                                            VersionUpdateType.BACKUP_AND_UPDATE);
    if (attributesYAML.update()) {
      debug(Level.INFO, "updating attributes.yml");
    }
    attributesYAML.load();

    configYAML =
        new VersionedIvoryYamlConfiguration(new File(getDataFolder(), "config.yml"),
                                            getResource("config.yml"),
                                            VersionUpdateType.BACKUP_AND_UPDATE);
    if (configYAML.update()) {
      debug(Level.INFO, "updating config.yml");
    }
    configYAML.load();

    languageYAML =
        new VersionedIvoryYamlConfiguration(new File(getDataFolder(), "language.yml"),
                                            getResource("language.yml"),
                                            VersionUpdateType.BACKUP_AND_UPDATE);
    if (languageYAML.update()) {
      debug(Level.INFO, "updating language.yml");
    }
    languageYAML.load();

    permissionsYAML =
        new VersionedIvoryYamlConfiguration(new File(getDataFolder(), "permissions.yml"),
                                            getResource("permissions.yml"),
                                            VersionUpdateType.BACKUP_AND_UPDATE);
    if (permissionsYAML.update()) {
      debug(Level.INFO, "updating permissions.yml");
    }
    permissionsYAML.load();

    loadSettings();
    loadAttributes();

    CommandHandler commandHandler = new CommandHandler(this);
    commandHandler.registerCommands(new ItemAttributesCommands(this));

    debug(Level.INFO, "v" + getDescription().getVersion() + " enabled");
  }

  private void loadSettings() {
    YamlConfiguration c = configYAML;
    getSettings().setSettingValue("config.item-only-damage-system.enabled",
                                  c.getBoolean("item-only-damage-system.enabled", false));
    getSettings().setSettingValue("config.item-only-damage-system.base-damage",
                                  c.getDouble("item-only-damage-system.base-damage", 1.0));

    c = languageYAML;
    for (String key : c.getKeys(true)) {
      if (c.isConfigurationSection(key) || key.equals("version")) {
        continue;
      }
      getSettings().setSettingValue("language." + key, c.getString(key, key));
    }
  }

  private void loadAttributes() {
    YamlConfiguration c = attributesYAML;
    if (!c.isConfigurationSection("attributes")) {
      return;
    }
    ConfigurationSection attributes = c.getConfigurationSection("attributes");
    List<String> loadedAttributes = new ArrayList<>();
    AttributeMap.getInstance().clear();
    for (String key : attributes.getKeys(false)) {
      if (!attributes.isConfigurationSection(key)) {
        continue;
      }
      ConfigurationSection cs = attributes.getConfigurationSection(key);
      IAttributeBuilder attributeBuilder = getAttributeBuilder(key);
      attributeBuilder.withFormat(cs.getString("format"));
      attributeBuilder.withBaseValue(cs.getDouble("base-value", 0));
      attributeBuilder.withMinimumValue(cs.getDouble("minimum-value", 0));
      attributeBuilder.withMaximumValue(cs.getDouble("maximum-value", 0));
      try {
        attributeBuilder.withEffect(Effect.valueOf(cs.getString("effect")));
        attributeBuilder.withSound(Sound.valueOf(cs.getString("sound")));
      } catch (Exception e) {
        // do nothing
      }
      attributeBuilder.withPercentageDivisor(cs.getDouble("percentage-divisor", 100));
      attributeBuilder.withEnabled(cs.getBoolean("enabled", true));
      attributeBuilder.withPercentage(cs.getBoolean("percentage", false));
      IAttribute attribute = attributeBuilder.build();
      loadedAttributes.add(attribute.getName());
      AttributeMap.getInstance().put(key, attribute);
    }
    debug(Level.INFO, "Loaded attributes: " + loadedAttributes.toString());
  }

  public void debug(Level level, String... messages) {
    if (debugPrinter == null) {
      debugPrinter = new DebugPrinter(getDataFolder().getPath(), "debug.log");
    }
    debugPrinter.debug(level, messages);
  }

  public IAttributeBuilder getAttributeBuilder(String name) {
    return new ItemAttributeBuilder(name);
  }

  public VersionedIvoryYamlConfiguration getAttributesYAML() {
    return attributesYAML;
  }

  public VersionedIvoryYamlConfiguration getConfigYAML() {
    return configYAML;
  }

  public VersionedIvoryYamlConfiguration getLanguageYAML() {
    return languageYAML;
  }

  public VersionedIvoryYamlConfiguration getPermissionsYAML() {
    return permissionsYAML;
  }

}
