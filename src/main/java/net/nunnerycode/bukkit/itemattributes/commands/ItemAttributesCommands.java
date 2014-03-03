package net.nunnerycode.bukkit.itemattributes.commands;

import net.nunnerycode.bukkit.itemattributes.ItemAttributesPlugin;
import net.nunnerycode.bukkit.itemattributes.api.attributes.IAttribute;
import net.nunnerycode.bukkit.itemattributes.attributes.AttributeMap;
import net.nunnerycode.bukkit.itemattributes.utils.MessageUtil;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import se.ranzdo.bukkit.methodcommand.Arg;
import se.ranzdo.bukkit.methodcommand.Command;

import java.text.DecimalFormat;

public final class ItemAttributesCommands {

  private ItemAttributesPlugin plugin;
  private final DecimalFormat decimalFormat = new DecimalFormat("#.##");

  public ItemAttributesCommands(ItemAttributesPlugin plugin) {
    this.plugin = plugin;
  }

  @Command(identifier = "itemattributes view")
  public void viewSubcommand(CommandSender commandSender,
                             @Arg(name = "player") String target) {
    Player player = Bukkit.getPlayer(target);
    if (player == null) {
      MessageUtil.sendColoredMessage(commandSender, (String) plugin.getSettings()
          .getSettingValue("language.messages.commands.target-is-not-player",
                           "language.messages.commands.target-is-not-player"));
      return;
    }
    String languageHelpKey =
        (String) plugin.getSettings().getSettingValue("language.messages.commands.view-stats-help",
                                                      "language.messages.commands.view-stats-help");
    MessageUtil.sendColoredMessage(player, languageHelpKey);
    String languageKey =
        (String) plugin.getSettings().getSettingValue("language.messages.commands.view-stats",
                                                      "language.messages.commands.view-stats");
    String languagePercentageKey =
        (String) plugin.getSettings().getSettingValue("language.messages.commands.view-stats-percentage",
                                                      "language.messages.commands.view-stats-percentage");
    for (IAttribute att : AttributeMap.getInstance().values()) {
      if (!att.isEnabled()) {
        continue;
      }
      double
          headValue =
          plugin.getAttributeHandler()
              .getAttributeValueFromItemStack(player.getEquipment().getHelmet(), att).asDouble();
      double
          chestValue =
          plugin.getAttributeHandler()
              .getAttributeValueFromItemStack(player.getEquipment().getChestplate(), att)
              .asDouble();
      double
          legValue =
          plugin.getAttributeHandler()
              .getAttributeValueFromItemStack(player.getEquipment().getLeggings(), att).asDouble();
      double
          footValue =
          plugin.getAttributeHandler()
              .getAttributeValueFromItemStack(player.getEquipment().getBoots(), att).asDouble();
      double
          handValue =
          plugin.getAttributeHandler()
              .getAttributeValueFromItemStack(player.getEquipment().getItemInHand(), att)
              .asDouble();
      double baseValue = att.getBaseValue();
      double tot = headValue + chestValue + legValue + footValue + handValue;
      double totalValue;
      if (att.isPercentage()) {
        totalValue = Math.min(
            Math.max(tot,
                     att.getMinimumValue()), att.getMaximumValue());
      } else {
        totalValue = Math.min(
            Math.max(tot + baseValue,
                     att.getMinimumValue()), att.getMaximumValue());
      }

      if (att.isPercentage()) {
        MessageUtil.sendColoredArgumentMessage(player, languagePercentageKey,
                                               new String[][]{{"%statname%", att.getName()},
                                                              {"%totalvalue%",
                                                               decimalFormat.format(totalValue)},
                                                              {"%helmet%",
                                                               decimalFormat.format(headValue)},
                                                              {"%chestplate%",
                                                               decimalFormat.format(chestValue)},
                                                              {"%leggings%",
                                                               decimalFormat.format(legValue)},
                                                              {"%boots%",
                                                               decimalFormat.format(footValue)},
                                                              {"%item%",
                                                               decimalFormat.format(handValue)}});
      } else {
        MessageUtil.sendColoredArgumentMessage(player, languageKey,
                                               new String[][]{{"%statname%", att.getName()},
                                                              {"%totalvalue%",
                                                               decimalFormat.format(totalValue)},
                                                              {"%helmet%",
                                                               decimalFormat.format(headValue)},
                                                              {"%chestplate%",
                                                               decimalFormat.format(chestValue)},
                                                              {"%leggings%",
                                                               decimalFormat.format(legValue)},
                                                              {"%boots%",
                                                               decimalFormat.format(footValue)},
                                                              {"%item%",
                                                               decimalFormat.format(handValue)}});
      }
    }
  }

}
