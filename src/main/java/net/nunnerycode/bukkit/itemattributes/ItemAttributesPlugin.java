package net.nunnerycode.bukkit.itemattributes;

import com.conventnunnery.libraries.config.CommentedConventYamlConfiguration;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import net.nunnerycode.bukkit.itemattributes.api.ItemAttributes;
import net.nunnerycode.bukkit.itemattributes.api.commands.ItemAttributesCommand;
import net.nunnerycode.bukkit.itemattributes.api.managers.LanguageManager;
import net.nunnerycode.bukkit.itemattributes.api.managers.SettingsManager;
import net.nunnerycode.bukkit.itemattributes.api.tasks.HealthUpdateTask;
import net.nunnerycode.bukkit.itemattributes.commands.ItemAttributesCommands;
import net.nunnerycode.bukkit.itemattributes.listeners.ItemAttributesCoreListener;
import net.nunnerycode.bukkit.itemattributes.managers.ItemAttributesLanguageManager;
import net.nunnerycode.bukkit.itemattributes.managers.ItemAttributesSettingsManager;
import net.nunnerycode.bukkit.itemattributes.tasks.ItemAttributesHealthUpdateTask;
import net.nunnerycode.java.libraries.cannonball.DebugPrinter;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class ItemAttributesPlugin extends JavaPlugin implements ItemAttributes {

	private DebugPrinter debugPrinter;
	private CommentedConventYamlConfiguration configYAML;
	private CommentedConventYamlConfiguration languageYAML;
	private ItemAttributesLanguageManager itemAttributesLanguageManager;
	private ItemAttributesSettingsManager itemAttributesSettingsManager;
	private ItemAttributesCoreListener itemAttributesCoreListener;
	private HealthUpdateTask itemAttributesHealthUpdateTask;
	private ItemAttributesCommand itemAttributesCommands;

	@Override
	public ItemAttributesCoreListener getCoreListener() {
		return itemAttributesCoreListener;
	}

	@Override
	public DebugPrinter getDebugPrinter() {
		return debugPrinter;
	}

	@Override
	public CommentedConventYamlConfiguration getConfigYAML() {
		return configYAML;
	}

	@Override
	public CommentedConventYamlConfiguration getLanguageYAML() {
		return languageYAML;
	}

	@Override
	public LanguageManager getLanguageManager() {
		return itemAttributesLanguageManager;
	}

	@Override
	public SettingsManager getSettingsManager() {
		return itemAttributesSettingsManager;
	}

	@Override
	public HealthUpdateTask getHealthUpdateTask() {
		return itemAttributesHealthUpdateTask;
	}

	@Override
	public ItemAttributesCommand getItemAttributesCommand() {
		return itemAttributesCommands;
	}

	@Override
	public void onDisable() {
		itemAttributesSettingsManager.save();
		debugPrinter.debug(Level.INFO, "v" + getDescription().getVersion() + " disabled");
	}

	@Override
	public void onEnable() {
		unpackConfigurationFiles(new String[]{"config.yml", "language.yml"}, false);

		configYAML = new CommentedConventYamlConfiguration(new File(getDataFolder(), "config.yml"),
				YamlConfiguration.loadConfiguration(getResource("config.yml")).getString("version"));
		configYAML.options().updateOnLoad(true);
		configYAML.options().backupOnUpdate(true);
		languageYAML = new CommentedConventYamlConfiguration(new File(getDataFolder(), "language.yml"),
				YamlConfiguration.loadConfiguration(getResource("language.yml")).getString("version"));
		languageYAML.options().updateOnLoad(true);
		languageYAML.options().updateOnLoad(true);

		debugPrinter = new DebugPrinter(getDataFolder().getPath() + "/log/", "debug.log");

		itemAttributesSettingsManager = new ItemAttributesSettingsManager(this);
		itemAttributesSettingsManager.load();

		itemAttributesLanguageManager = new ItemAttributesLanguageManager(this);
		itemAttributesLanguageManager.load();

		itemAttributesCoreListener = new ItemAttributesCoreListener(this);

		if (itemAttributesSettingsManager.isHealthEnabled()) {
			itemAttributesHealthUpdateTask = new ItemAttributesHealthUpdateTask(this);
		}

		Bukkit.getServer().getPluginManager().registerEvents(itemAttributesCoreListener, this);

		itemAttributesCommands = new ItemAttributesCommands(this);

		debugPrinter.debug(Level.INFO, "v" + getDescription().getVersion() + " enabled");
	}

	private void unpackConfigurationFiles(String[] configurationFiles, boolean overwrite) {
		for (String s : configurationFiles) {
			YamlConfiguration yc = YamlConfiguration.loadConfiguration(getResource(s));
			try {
				File f = new File(getDataFolder(), s);
				if (!f.exists()) {
					yc.save(new File(getDataFolder(), s));
					continue;
				}
				if (overwrite) {
					yc.save(new File(getDataFolder(), s));
				}
			} catch (IOException e) {
				getLogger().warning("Could not unpack " + s);
			}
		}
	}
}
