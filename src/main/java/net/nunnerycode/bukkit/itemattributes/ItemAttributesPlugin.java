package net.nunnerycode.bukkit.itemattributes;

import com.conventnunnery.libraries.config.CommentedConventYamlConfiguration;
import net.nunnerycode.bukkit.itemattributes.api.ItemAttributes;
import net.nunnerycode.bukkit.itemattributes.api.attributes.Attribute;
import net.nunnerycode.bukkit.itemattributes.api.attributes.AttributeHandler;
import net.nunnerycode.bukkit.itemattributes.api.commands.ItemAttributesCommand;
import net.nunnerycode.bukkit.itemattributes.api.managers.LanguageManager;
import net.nunnerycode.bukkit.itemattributes.api.managers.PermissionsManager;
import net.nunnerycode.bukkit.itemattributes.api.managers.SettingsManager;
import net.nunnerycode.bukkit.itemattributes.api.tasks.AttackSpeedTask;
import net.nunnerycode.bukkit.itemattributes.api.tasks.HealthUpdateTask;
import net.nunnerycode.bukkit.itemattributes.attributes.ItemAttributeHandler;
import net.nunnerycode.bukkit.itemattributes.commands.ItemAttributesCommands;
import net.nunnerycode.bukkit.itemattributes.listeners.ItemAttributesCoreListener;
import net.nunnerycode.bukkit.itemattributes.managers.ItemAttributesLanguageManager;
import net.nunnerycode.bukkit.itemattributes.managers.ItemAttributesPermissionsManager;
import net.nunnerycode.bukkit.itemattributes.managers.ItemAttributesSettingsManager;
import net.nunnerycode.bukkit.itemattributes.tasks.ItemAttributesAttackSpeedTask;
import net.nunnerycode.bukkit.itemattributes.tasks.ItemAttributesHealthUpdateTask;
import net.nunnerycode.java.libraries.cannonball.DebugPrinter;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public final class ItemAttributesPlugin extends JavaPlugin implements ItemAttributes {

	private DebugPrinter debugPrinter;
	private CommentedConventYamlConfiguration configYAML;
	private CommentedConventYamlConfiguration languageYAML;
	private CommentedConventYamlConfiguration permissionsYAML;
	private ItemAttributesLanguageManager itemAttributesLanguageManager;
	private ItemAttributesSettingsManager itemAttributesSettingsManager;
	private ItemAttributesPermissionsManager itemAttributesPermissionsManager;
	private ItemAttributesCoreListener itemAttributesCoreListener;
	private ItemAttributesHealthUpdateTask itemAttributesHealthUpdateTask;
	private ItemAttributesAttackSpeedTask itemAttributesAttackSpeedTask;
	private ItemAttributesCommand itemAttributesCommands;
	private ItemAttributeHandler itemAttributeHandler;

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
	public CommentedConventYamlConfiguration getPermissionsYAML() {
		return permissionsYAML;
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
	public PermissionsManager getPermissionsManager() {
		return itemAttributesPermissionsManager;
	}

	@Override
	public HealthUpdateTask getHealthUpdateTask() {
		return itemAttributesHealthUpdateTask;
	}

	@Override
	public AttackSpeedTask getAttackSpeedTask() {
		return itemAttributesAttackSpeedTask;
	}

	@Override
	public ItemAttributesCommand getItemAttributesCommand() {
		return itemAttributesCommands;
	}

	@Override
	public AttributeHandler getAttributeHandler() {
		return itemAttributeHandler;
	}

	@Override
	public void onDisable() {
		itemAttributesSettingsManager.save();
		debugPrinter.debug(Level.INFO, "v" + getDescription().getVersion() + " disabled");
	}

	@Override
	public void onEnable() {
		unpackConfigurationFiles(new String[]{"config.yml", "language.yml", "permissions.yml"}, false);

		configYAML = new CommentedConventYamlConfiguration(new File(getDataFolder(), "config.yml"),
				YamlConfiguration.loadConfiguration(getResource("config.yml")).getString("version"));
		configYAML.options().updateOnLoad(true);
		configYAML.options().backupOnUpdate(true);
		languageYAML = new CommentedConventYamlConfiguration(new File(getDataFolder(), "language.yml"),
				YamlConfiguration.loadConfiguration(getResource("language.yml")).getString("version"));
		languageYAML.options().updateOnLoad(true);
		languageYAML.options().backupOnUpdate(true);
		permissionsYAML = new CommentedConventYamlConfiguration(new File(getDataFolder(), "permissions.yml"),
				YamlConfiguration.loadConfiguration(getResource("permissions.yml")).getString("version"));
		permissionsYAML.options().updateOnLoad(true);
		permissionsYAML.options().backupOnUpdate(true);

		debugPrinter = new DebugPrinter(getDataFolder().getPath() + "/log/", "debug.log");

		itemAttributesSettingsManager = new ItemAttributesSettingsManager(this);
		itemAttributesSettingsManager.load();

		itemAttributesLanguageManager = new ItemAttributesLanguageManager(this);
		itemAttributesLanguageManager.load();

		itemAttributesPermissionsManager = new ItemAttributesPermissionsManager(this);
		itemAttributesPermissionsManager.load();

		itemAttributeHandler = new ItemAttributeHandler(this);

		itemAttributesCoreListener = new ItemAttributesCoreListener(this);

		itemAttributesHealthUpdateTask = new ItemAttributesHealthUpdateTask(this);
		getServer().getScheduler().runTaskTimer(this, itemAttributesHealthUpdateTask,
				20 * getSettingsManager().getSecondsBetweenHealthUpdates(),
				20 * getSettingsManager().getSecondsBetweenHealthUpdates());

		itemAttributesAttackSpeedTask = new ItemAttributesAttackSpeedTask(this);
		getServer().getScheduler().runTaskTimer(this, itemAttributesAttackSpeedTask, 20L / 4, 20L / 4);

		Bukkit.getServer().getPluginManager().registerEvents(itemAttributesCoreListener, this);

		itemAttributesCommands = new ItemAttributesCommands(this);

		List<String> loadedAttributes = new ArrayList<String>();
		for (Attribute attribute : itemAttributesSettingsManager.getLoadedAttributes()) {
			loadedAttributes.add(attribute.getName());
		}

		debugPrinter.debug(Level.INFO, "Loaded attributes: " + loadedAttributes.toString());
		debugPrinter.debug(Level.INFO, "v" + getDescription().getVersion() + " enabled");
	}

	private void unpackConfigurationFiles(String[] configurationFiles, boolean overwrite) {
		for (String s : configurationFiles) {
			if (getResource(s) == null) {
				continue;
			}
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
