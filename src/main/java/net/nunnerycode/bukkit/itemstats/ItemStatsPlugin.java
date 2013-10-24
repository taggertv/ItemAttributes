package net.nunnerycode.bukkit.itemstats;

import com.conventnunnery.libraries.config.CommentedConventYamlConfiguration;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import net.nunnerycode.java.libraries.cannonball.DebugPrinter;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class ItemStatsPlugin extends JavaPlugin {

	private DebugPrinter debugPrinter;
	private CommentedConventYamlConfiguration configYAML;
	private CommentedConventYamlConfiguration languageYAML;
	private LanguageManager languageManager;
	private SettingsManager settingsManager;
	private CoreListener coreListener;
	private HealthUpdateTask healthUpdateTask;

	public CoreListener getCoreListener() {
		return coreListener;
	}

	@Override
	public void onDisable() {
		settingsManager.save();
		debugPrinter.debug(Level.INFO, "v" + getDescription().getVersion() + " disabled");
	}

	@Override
	public void onEnable() {
		unpackConfigurationFiles(new String[]{"config.yml", "language.yml"}, false);

		configYAML = new CommentedConventYamlConfiguration(new File(getDataFolder(), "config.yml"),
				YamlConfiguration.loadConfiguration(getResource("config.yml")).getString("version"));
		languageYAML = new CommentedConventYamlConfiguration(new File(getDataFolder(), "language.yml"),
				YamlConfiguration.loadConfiguration(getResource("language.yml")).getString("version"));

		settingsManager = new SettingsManager(this);
		settingsManager.load();

		languageManager = new LanguageManager(this);
		languageManager.load();

		coreListener = new CoreListener(this);
		healthUpdateTask = new HealthUpdateTask(this);

		Bukkit.getServer().getPluginManager().registerEvents(coreListener, this);

		debugPrinter = new DebugPrinter(getDataFolder().getPath() + "/log/", "debug.log");
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

	public DebugPrinter getDebugPrinter() {
		return debugPrinter;
	}

	public CommentedConventYamlConfiguration getConfigYAML() {
		return configYAML;
	}

	public CommentedConventYamlConfiguration getLanguageYAML() {
		return languageYAML;
	}

	public LanguageManager getLanguageManager() {
		return languageManager;
	}

	public SettingsManager getSettingsManager() {
		return settingsManager;
	}

	public HealthUpdateTask getHealthUpdateTask() {
		return healthUpdateTask;
	}
}
