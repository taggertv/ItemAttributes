package net.nunnerycode.bukkit.itemattributes.commands;

import net.nunnerycode.bukkit.itemattributes.ItemAttributesPlugin;
import net.nunnerycode.bukkit.itemattributes.api.ItemAttributes;
import net.nunnerycode.bukkit.itemattributes.api.commands.ItemAttributesCommand;
import se.ranzdo.bukkit.methodcommand.CommandHandler;

public class ItemAttributesCommands implements ItemAttributesCommand {

	private final ItemAttributes plugin;
	private final CommandHandler commandHandler;

	public ItemAttributesCommands(ItemAttributesPlugin plugin) {
		this.plugin = plugin;
		commandHandler = new CommandHandler(plugin);
	}

	@Override
	public CommandHandler getCommandHandler() {
		return commandHandler;
	}

	@Override
	public ItemAttributes getPlugin() {
		return plugin;
	}
}
