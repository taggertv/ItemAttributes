package net.nunnerycode.bukkit.itemattributes;

import net.nunnerycode.bukkit.itemattributes.api.attributes.IAttributeBuilder;
import net.nunnerycode.bukkit.itemattributes.api.attributes.IAttributeHandler;
import net.nunnerycode.bukkit.itemattributes.api.settings.ISettings;
import net.nunnerycode.bukkit.itemattributes.attributes.ItemAttributeBuilder;
import net.nunnerycode.bukkit.itemattributes.attributes.ItemAttributeHandler;
import net.nunnerycode.bukkit.itemattributes.settings.ItemAttributesSettings;
import net.nunnerycode.bukkit.libraries.ivory.config.VersionedIvoryYamlConfiguration;
import net.nunnerycode.java.libraries.cannonball.DebugPrinter;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
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

    debug(Level.INFO, "v" + getDescription().getVersion() + " enabled");
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
