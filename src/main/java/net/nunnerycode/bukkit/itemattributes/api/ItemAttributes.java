package net.nunnerycode.bukkit.itemattributes.api;

import com.conventnunnery.libraries.config.CommentedConventYamlConfiguration;
import net.nunnerycode.bukkit.itemattributes.api.attributes.AttributeHandler;
import net.nunnerycode.bukkit.itemattributes.api.commands.ItemAttributesCommand;
import net.nunnerycode.bukkit.itemattributes.api.listeners.CoreListener;
import net.nunnerycode.bukkit.itemattributes.api.managers.LanguageManager;
import net.nunnerycode.bukkit.itemattributes.api.managers.SettingsManager;
import net.nunnerycode.bukkit.itemattributes.api.tasks.HealthUpdateTask;
import net.nunnerycode.java.libraries.cannonball.DebugPrinter;

public interface ItemAttributes {
	CoreListener getCoreListener();

	DebugPrinter getDebugPrinter();

	CommentedConventYamlConfiguration getConfigYAML();

	CommentedConventYamlConfiguration getLanguageYAML();

	LanguageManager getLanguageManager();

	SettingsManager getSettingsManager();

	HealthUpdateTask getHealthUpdateTask();

	ItemAttributesCommand getItemAttributesCommand();

	AttributeHandler getAttributeHandler();
}
