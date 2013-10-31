package net.nunnerycode.bukkit.itemattributes.managers;

import com.conventnunnery.libraries.config.ConventConfiguration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.nunnerycode.bukkit.itemattributes.ItemAttributesPlugin;
import net.nunnerycode.bukkit.itemattributes.api.managers.LanguageManager;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

public final class ItemAttributesLanguageManager implements LanguageManager {
	private final ItemAttributesPlugin plugin;
	private final Map<String, String> messages;

	public ItemAttributesLanguageManager(ItemAttributesPlugin plugin) {
		this.plugin = plugin;
		messages = new HashMap<String, String>();
	}

	@Override
	public Map<String, String> getMessages() {
		return messages;
	}

	@Override
	public void sendMessage(CommandSender reciever, String path) {
		String message = getMessage(path);
		if (message == null) {
			return;
		}
		reciever.sendMessage(message);
	}

	@Override
	public String getMessage(String path) {
		String message = messages.get(path);
		if (message == null) {
			return null;
		}
		return ChatColor.translateAlternateColorCodes('&', message);
	}

	public void load() {
		ConventConfiguration c = getPlugin().getLanguageYAML();
		c.load();
		FileConfiguration fc = c.getFileConfiguration();
		if (fc.isConfigurationSection("messages")) {
			for (String key : fc.getConfigurationSection("messages").getKeys(true)) {
				if (fc.getConfigurationSection("messages").isConfigurationSection(key)) {
					continue;
				}
				messages.put(key, fc.getConfigurationSection("messages").getString(key, key));
			}
		}
	}

	@Override
	public ItemAttributesPlugin getPlugin() {
		return plugin;
	}

	@Override
	public void sendMessage(CommandSender reciever, String path,
							String[][] arguments) {
		String message = getMessage(path, arguments);
		if (message == null) {
			return;
		}
		reciever.sendMessage(message);
	}

	@Override
	public String getMessage(String path, String[][] arguments) {
		String message = messages.get(path);
		if (message == null) {
			return null;
		}
		message = ChatColor.translateAlternateColorCodes('&', message);
		for (String[] argument : arguments) {
			message = message.replaceAll(argument[0], argument[1]);
		}
		return message;
	}

	@Override
	public List<String> getStringList(String path) {
		List<String> message = Arrays.asList(messages.get(path).split("^"));
		List<String> strings = new ArrayList<String>();
		for (String s : message) {
			strings.add(ChatColor.translateAlternateColorCodes('&', s));
		}
		return strings;
	}

	@Override
	public List<String> getStringList(String path, String[][] arguments) {
		List<String> message = Arrays.asList(messages.get(path).split("^"));
		List<String> strings = new ArrayList<String>();
		for (String s : message) {
			for (String[] argument : arguments) {
				strings.add(ChatColor.translateAlternateColorCodes('&', s.replace(argument[0], argument[1])));
			}
		}
		return strings;
	}
}
