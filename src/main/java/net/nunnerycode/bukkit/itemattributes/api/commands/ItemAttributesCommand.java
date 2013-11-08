package net.nunnerycode.bukkit.itemattributes.api.commands;

import net.nunnerycode.bukkit.itemattributes.api.ItemAttributes;
import se.ranzdo.bukkit.methodcommand.CommandHandler;

public interface ItemAttributesCommand {

	CommandHandler getCommandHandler();

	ItemAttributes getPlugin();

}
