package net.nunnerycode.bukkit.itemattributes;

import net.nunnerycode.bukkit.itemattributes.api.attributes.IAttributeBuilder;
import net.nunnerycode.bukkit.itemattributes.api.attributes.IAttributeHandler;
import net.nunnerycode.bukkit.itemattributes.api.settings.ISettings;
import net.nunnerycode.bukkit.itemattributes.attributes.ItemAttributeBuilder;
import net.nunnerycode.bukkit.itemattributes.attributes.ItemAttributeHandler;
import net.nunnerycode.bukkit.itemattributes.settings.ItemAttributesSettings;
import net.nunnerycode.java.libraries.cannonball.DebugPrinter;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public final class ItemAttributesPlugin extends JavaPlugin {

  private IAttributeHandler attributeHandler;
  private ISettings settings;
  private DebugPrinter debugPrinter;

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

}
