package net.nunnerycode.bukkit.itemstats;

import com.conventnunnery.libraries.config.ConventConfiguration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

public final class LanguageManager {
	private final ItemStatsPlugin plugin;
	private final Map<String, String> messages;

	public LanguageManager(ItemStatsPlugin plugin) {
		this.plugin = plugin;
		messages = new HashMap<String, String>();
	}

	public Map<String, String> getMessages() {
		return messages;
	}

	public void sendMessage(CommandSender reciever, String path) {
		String message = getMessage(path);
		if (message == null) {
			return;
		}
		reciever.sendMessage(message);
	}

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

	public ItemStatsPlugin getPlugin() {
		return plugin;
	}

	public void sendMessage(CommandSender reciever, String path,
							String[][] arguments) {
		String message = getMessage(path, arguments);
		if (message == null) {
			return;
		}
		reciever.sendMessage(message);
	}

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

	public List<String> getStringList(String path) {
		List<String> message = Arrays.asList(messages.get(path).split("^"));
		List<String> strings = new ArrayList<String>();
		for (String s : message) {
			strings.add(ChatColor.translateAlternateColorCodes('&', s));
		}
		return strings;
	}

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
