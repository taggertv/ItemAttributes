package net.nunnerycode.bukkit.itemattributes.commands;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import net.nunnerycode.bukkit.itemattributes.ItemAttributesPlugin;
import net.nunnerycode.bukkit.itemattributes.api.ItemAttributes;
import net.nunnerycode.bukkit.itemattributes.api.attributes.Attribute;
import net.nunnerycode.bukkit.itemattributes.api.commands.ItemAttributesCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import se.ranzdo.bukkit.methodcommand.Arg;
import se.ranzdo.bukkit.methodcommand.Command;
import se.ranzdo.bukkit.methodcommand.CommandHandler;

public class ItemAttributesCommands implements ItemAttributesCommand {

	private final ItemAttributes plugin;
	private final CommandHandler commandHandler;
	private final DecimalFormat DF = new DecimalFormat("#.##");

	public ItemAttributesCommands(ItemAttributesPlugin plugin) {
		this.plugin = plugin;
		commandHandler = new CommandHandler(plugin);
		commandHandler.registerCommands(this);
	}

	@Override
	public CommandHandler getCommandHandler() {
		return commandHandler;
	}

	@Override
	public ItemAttributes getPlugin() {
		return plugin;
	}

	@Command(identifier = "itemattributes view", description = "Shows a player's ItemAttributes stats",
			permissions = "itemattributes.command.view")
	public void viewSubcommand(CommandSender sender, @Arg(name = "player name", def = "self") String name) {
		String playerName = name;
		if (!(sender instanceof Player) && playerName.equalsIgnoreCase("self")) {
			getPlugin().getLanguageManager().sendMessage(sender, "commands.cannot-use");
			return;
		}
		if (playerName.equalsIgnoreCase("self")) {
			playerName = sender.getName();
		}
		Player player = Bukkit.getPlayer(playerName);

		getPlugin().getLanguageManager().sendMessage(sender, "commands.view-stats-help");

		for (Attribute attribute : getPlugin().getSettingsManager().getLoadedAttributes()) {
			if (attribute.isPercentage()) {
				sendPercentageStatMessage(sender, player, attribute);
			} else {
				sendStatMessage(sender, player, attribute);
			}
		}
	}

	private void sendPercentageStatMessage(CommandSender sender, Player player, Attribute attribute) {
		double statHelmet = getPlugin().getAttributeHandler().getAttributeValueFromItemStack(player, player
				.getEquipment().getHelmet(), attribute);
		double statChestplate = getPlugin().getAttributeHandler().getAttributeValueFromItemStack(player, player
				.getEquipment().getChestplate(), attribute);
		double statLeggings = getPlugin().getAttributeHandler().getAttributeValueFromItemStack(player, player
				.getEquipment().getLeggings(), attribute);
		double statBoots = getPlugin().getAttributeHandler().getAttributeValueFromItemStack(player,
				player.getEquipment().getBoots(), attribute);
		double statItem = getPlugin().getAttributeHandler().getAttributeValueFromItemStack(player,
				player.getEquipment().getItemInHand(), attribute);
		double statTotal = attribute.getPlayersBaseValue() + statHelmet + statChestplate + statLeggings + statBoots
				+ statItem;
		String formatString = attribute.getFormat().replaceAll("%(?s)(.*?)%", "").trim();
		getPlugin().getLanguageManager().sendMessage(sender, "commands.view-stats-percentage",
				new String[][]{{"%statname%", formatString}, {"%totalvalue%", DF.format(statTotal * 100)},
						{"%helmet%", DF.format(statHelmet * 100)}, {"%chestplate%", DF.format(statChestplate * 100)},
						{"%leggings%", DF.format(statLeggings * 100)}, {"%boots%", DF.format(statBoots * 100)},
						{"%item%", DF.format(statItem * 100)}});
	}

	private void sendStatMessage(CommandSender sender, Player player, Attribute attribute) {
		double statHelmet = getPlugin().getAttributeHandler().getAttributeValueFromItemStack(player, player
				.getEquipment().getHelmet(), attribute);
		double statChestplate = getPlugin().getAttributeHandler().getAttributeValueFromItemStack(player, player
				.getEquipment().getChestplate(), attribute);
		double statLeggings = getPlugin().getAttributeHandler().getAttributeValueFromItemStack(player, player
				.getEquipment().getLeggings(), attribute);
		double statBoots = getPlugin().getAttributeHandler().getAttributeValueFromItemStack(player,
				player.getEquipment().getBoots(), attribute);
		double statItem = getPlugin().getAttributeHandler().getAttributeValueFromItemStack(player,
				player.getEquipment().getItemInHand(), attribute);
		double statTotal = attribute.getPlayersBaseValue() + statHelmet + statChestplate + statLeggings + statBoots
				+ statItem;
		String formatString = attribute.getFormat().replaceAll("%(?s)(.*?)%", "").trim();
		getPlugin().getLanguageManager().sendMessage(sender, "commands.view-stats", new String[][]{{"%statname%",
				formatString}, {"%totalvalue%", DF.format(statTotal)}, {"%helmet%", DF.format(statHelmet)},
				{"%chestplate%", DF.format(statChestplate)}, {"%leggings%", DF.format(statLeggings)}, {"%boots%",
				DF.format(statBoots)}, {"%item%", DF.format(statItem)}});
	}

	private List<String> getItemStackLore(ItemStack itemStack) {
		List<String> lore = new ArrayList<String>();
		if (itemStack != null && itemStack.hasItemMeta() && itemStack.getItemMeta().hasLore()) {
			lore.addAll(itemStack.getItemMeta().getLore());
		}
		return lore;
	}
}
