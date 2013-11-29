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

		Attribute damageAttribute = getPlugin().getSettingsManager().getAttribute("DAMAGE");
		Attribute meleeDamageAttribute = getPlugin().getSettingsManager().getAttribute("MELEE DAMAGE");
		Attribute rangedDamageAttribute = getPlugin().getSettingsManager().getAttribute("RANGED DAMAGE");
		Attribute criticalRateAttribute = getPlugin().getSettingsManager().getAttribute("CRITICAL RATE");
		Attribute criticalDamageAttribute = getPlugin().getSettingsManager().getAttribute("CRITICAL DAMAGE");
		Attribute stunRateAttribute = getPlugin().getSettingsManager().getAttribute("STUN RATE");
		Attribute stunLengthAttribute = getPlugin().getSettingsManager().getAttribute("STUN LENGTH");
		Attribute dodgeRateAttribute = getPlugin().getSettingsManager().getAttribute("DODGE RATE");
		Attribute healthAttribute = getPlugin().getSettingsManager().getAttribute("HEALTH");
		Attribute regenerationAttribute = getPlugin().getSettingsManager().getAttribute("REGENERATION");
		Attribute armorPenetrationAttribute = getPlugin().getSettingsManager().getAttribute("ARMOR PENETRATION");
		Attribute armorAttribute = getPlugin().getSettingsManager().getAttribute("ARMOR");

		// health stat
		sendStatMessage(sender, player, healthAttribute,
				getPlugin().getSettingsManager().getBasePlayerHealth());
		// damage stat
		sendStatMessage(sender, player, damageAttribute, 0D);
		// melee damage stat
		sendStatMessage(sender, player, meleeDamageAttribute, 0D);
		// ranged damage stat
		sendStatMessage(sender, player, rangedDamageAttribute, 0D);
		// regeneration stat
		sendStatMessage(sender, player, regenerationAttribute, 0D);
		// armor stat
		sendStatMessage(sender, player, armorAttribute, 0D);
		// critical rate stat
		sendPercentageStatMessage(sender, player, criticalRateAttribute, getPlugin().getSettingsManager()
				.getBaseCriticalRate());
		// critical damage stat
		sendPercentageStatMessage(sender, player, criticalDamageAttribute, getPlugin().getSettingsManager()
				.getBaseCriticalDamage());
		// armor penetration stat
		sendStatMessage(sender, player, armorPenetrationAttribute, 0);
		// stun rate stat
		sendPercentageStatMessage(sender, player, stunRateAttribute, getPlugin().getSettingsManager().getBaseStunRate
				());
		// stun length stat
		sendStatMessage(sender, player, stunLengthAttribute, getPlugin().getSettingsManager().getBaseStunLength());
		// dodge rate stat
		sendPercentageStatMessage(sender, player, dodgeRateAttribute, getPlugin().getSettingsManager()
				.getBaseDodgeRate());
	}

	private void sendStatMessage(CommandSender sender, Player player, Attribute attribute, double baseStat) {
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
		double statTotal = baseStat + statHelmet + statChestplate + statLeggings + statBoots + statItem;
		String formatString = attribute.getFormat().replaceAll("%(?s)(.*?)%", "").trim();
		getPlugin().getLanguageManager().sendMessage(sender, "commands.view-stats", new String[][]{{"%statname%",
				formatString}, {"%totalvalue%", DF.format(statTotal)}, {"%helmet%", DF.format(statHelmet)},
				{"%chestplate%", DF.format(statChestplate)}, {"%leggings%", DF.format(statLeggings)}, {"%boots%",
				DF.format(statBoots)}, {"%item%", DF.format(statItem)}});
	}

	private void sendPercentageStatMessage(CommandSender sender, Player player, Attribute attribute, double baseStat) {
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
		double statTotal = baseStat + statHelmet + statChestplate + statLeggings + statBoots + statItem;
		String formatString = attribute.getFormat().replaceAll("%(?s)(.*?)%", "").trim();
		getPlugin().getLanguageManager().sendMessage(sender, "commands.view-stats-percentage",
				new String[][]{{"%statname%", formatString}, {"%totalvalue%", DF.format(statTotal * 100)},
						{"%helmet%", DF.format(statHelmet * 100)}, {"%chestplate%", DF.format(statChestplate * 100)},
						{"%leggings%", DF.format(statLeggings * 100)}, {"%boots%", DF.format(statBoots * 100)},
						{"%item%", DF.format(statItem * 100)}});
	}

	private List<String> getItemStackLore(ItemStack itemStack) {
		List<String> lore = new ArrayList<String>();
		if (itemStack != null && itemStack.hasItemMeta() && itemStack.getItemMeta().hasLore()) {
			lore.addAll(itemStack.getItemMeta().getLore());
		}
		return lore;
	}
}
