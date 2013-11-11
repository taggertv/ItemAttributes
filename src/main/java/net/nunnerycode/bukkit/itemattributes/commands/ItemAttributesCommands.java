package net.nunnerycode.bukkit.itemattributes.commands;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import net.nunnerycode.bukkit.itemattributes.ItemAttributesPlugin;
import net.nunnerycode.bukkit.itemattributes.api.ItemAttributes;
import net.nunnerycode.bukkit.itemattributes.api.commands.ItemAttributesCommand;
import net.nunnerycode.bukkit.itemattributes.utils.ItemAttributesParseUtil;
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

		// health stat
		if (getPlugin().getSettingsManager().isHealthModificationEnabled()) {
			sendStatMessage(sender, player, getPlugin().getSettingsManager().getHealthFormat(),
					getPlugin().getSettingsManager().getBasePlayerHealth());
		}
		// damage stat
		sendStatMessage(sender, player, getPlugin().getSettingsManager().getDamageFormat(), 0D);
		// melee damage stat
		sendStatMessage(sender, player, getPlugin().getSettingsManager().getMeleeDamageFormat(), 0D);
		// ranged damage stat
		sendStatMessage(sender, player, getPlugin().getSettingsManager().getRangedDamageFormat(), 0D);
		// regeneration stat
		if (getPlugin().getSettingsManager().isHealthModificationEnabled()) {
			sendStatMessage(sender, player, getPlugin().getSettingsManager().getRegenerationFormat(), 0D);
		}
		// armor stat
		sendStatMessage(sender, player, getPlugin().getSettingsManager().getArmorFormat(), 0D);
		// critical rate stat
		sendPercentageStatMessage(sender, player, getPlugin().getSettingsManager().getCriticalRateFormat(),
				getPlugin().getSettingsManager().getBaseCriticalRate(), getPlugin().getSettingsManager()
				.getMaximumCriticalRate());
		// critical damage stat
		sendPercentageStatMessage(sender, player, getPlugin().getSettingsManager().getCriticalDamageFormat(),
				getPlugin().getSettingsManager().getBaseCriticalDamage(), getPlugin().getSettingsManager()
				.getMaximumCriticalDamage());
		// armor penetration stat
		sendStatMessage(sender, player, getPlugin().getSettingsManager().getArmorPenetrationFormat(), 0);
		// stun rate stat
		sendPercentageStatMessage(sender, player, getPlugin().getSettingsManager().getStunRateFormat(),
				getPlugin().getSettingsManager().getBaseStunRate(), getPlugin().getSettingsManager()
				.getMaximumStunRate());
		// stun length stat
		sendStatMessage(sender, player, getPlugin().getSettingsManager().getStunLengthFormat(),
				getPlugin().getSettingsManager().getBaseStunLength());
		// dodge rate stat
		sendPercentageStatMessage(sender, player, getPlugin().getSettingsManager().getDodgeRateFormat(),
				getPlugin().getSettingsManager().getBaseDodgeRate(), getPlugin().getSettingsManager()
				.getMaximumDodgeRate());
	}

	private void sendStatMessage(CommandSender sender, Player player, String format, double baseStat) {
		double statHelmet = ItemAttributesParseUtil.getDouble(getItemStackLore(player.getEquipment().getHelmet()),
				format);
		double statChestplate = ItemAttributesParseUtil.getDouble(getItemStackLore(player.getEquipment().getChestplate()),
				format);
		double statLeggings = ItemAttributesParseUtil.getDouble(getItemStackLore(player.getEquipment().getLeggings
				()), format);
		double statBoots = ItemAttributesParseUtil.getDouble(getItemStackLore(player.getEquipment().getBoots()),
				format);
		double statItem = ItemAttributesParseUtil.getDouble(getItemStackLore(player.getEquipment().getItemInHand()),
				format);
		double statTotal = baseStat + statHelmet + statChestplate + statLeggings + statBoots + statItem;
		String formatString = format.replaceAll("%(?s)(.*?)%", "").trim();
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

	private void sendPercentageStatMessage(CommandSender sender, Player player, String format, double baseStat,
										   double maxValue) {
		double statHelmet = ItemAttributesParseUtil.getDoublePercentage(getItemStackLore(player.getEquipment()
				.getHelmet()), format, maxValue);
		double statChestplate = ItemAttributesParseUtil.getDoublePercentage(getItemStackLore(player.getEquipment()
				.getChestplate()), format, maxValue);
		double statLeggings = ItemAttributesParseUtil.getDoublePercentage(getItemStackLore(player.getEquipment()
				.getLeggings()), format, maxValue);
		double statBoots = ItemAttributesParseUtil.getDoublePercentage(getItemStackLore(player.getEquipment()
				.getBoots()), format, maxValue);
		double statItem = ItemAttributesParseUtil.getDoublePercentage(getItemStackLore(player.getEquipment()
				.getItemInHand()), format, maxValue);
		double statTotal = baseStat + statHelmet + statChestplate + statLeggings + statBoots + statItem;
		String formatString = format.replaceAll("%(?s)(.*?)%", "").trim();
		getPlugin().getLanguageManager().sendMessage(sender, "commands.view-stats-percentage",
				new String[][]{{"%statname%", formatString}, {"%totalvalue%", DF.format(statTotal * 100)},
						{"%helmet%", DF.format(statHelmet * 100)}, {"%chestplate%", DF.format(statChestplate * 100)},
						{"%leggings%", DF.format(statLeggings * 100)}, {"%boots%", DF.format(statBoots * 100)},
						{"%item%", DF.format(statItem * 100)}});
	}
}
