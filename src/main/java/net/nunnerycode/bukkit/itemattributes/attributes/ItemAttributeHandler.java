package net.nunnerycode.bukkit.itemattributes.attributes;

import net.nunnerycode.bukkit.itemattributes.api.attributes.IAttribute;
import net.nunnerycode.bukkit.itemattributes.api.attributes.IAttributeHandler;
import net.nunnerycode.bukkit.itemattributes.api.attributes.IAttributeValue;

import org.apache.commons.lang.math.RandomUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public final class ItemAttributeHandler implements IAttributeHandler {

  /**
   * Gets and returns the value of the {@link IAttribute} on the {@link ItemStack}
   *
   * @param itemStack ItemStack to check
   * @param attribute IAttribute to check for
   * @return value of IAttribute on ItemStack
   */
  @Override
  public List<IAttributeValue> getAttributeValueFromItemStack(ItemStack itemStack,
                                                              IAttribute attribute) {
    List<IAttributeValue> iAttributeValues = new ArrayList<>();
    if (itemStack == null || attribute == null) {
      return iAttributeValues;
    }
    if (!itemStack.hasItemMeta()) {
      return iAttributeValues;
    }
    if (!itemStack.getItemMeta().hasLore()) {
      return iAttributeValues;
    }
    String format = attribute.getFormat();
    String formatWithoutVariables = format.replaceAll("%(?s)(.*?)%", "").trim();
    for (String s : itemStack.getItemMeta().getLore()) {
      String stripped = ChatColor.stripColor(s);
      String withoutNumbers = stripped.replaceAll("[0-9\\+%\\-]", "").trim();
      String withoutLetters = stripped.replaceAll(withoutNumbers, "").trim();

      if (!withoutNumbers.equals(formatWithoutVariables)) {
        continue;
      }

      if (withoutLetters.contains("-")) {
        String[] split = withoutLetters.split("-");
        if (split.length > 1) {
          double first = NumberUtils.toDouble(split[0].trim(), 0.0);
          double second = NumberUtils.toDouble(split[1].trim(), 0.0);
          double d = RandomUtils.nextDouble() * (Math.max(first, second) - Math.min(first,
                                                                                    second)) + Math
                         .min(first, second);
          iAttributeValues.add(new ItemAttributeValue(d));
        }
      } else {
        iAttributeValues.add(new ItemAttributeValue(stripped.replace(formatWithoutVariables,
                                                                     "").trim()));
      }
    }
    return iAttributeValues;
  }

  /**
   * Gets and returns the value of the {@link IAttribute} on the LivingEntity </br> Equivalent of
   * calling {@link #getAttributeValueFromItemStack(ItemStack, IAttribute)} on all {@link
   * ItemStack}s in {@link LivingEntity#getEquipment()}.
   *
   * @param livingEntity LivingEntity to check on
   * @param attribute    IAttribute to check for
   * @return value of IAttribute on ItemStack
   */
  @Override
  public List<IAttributeValue> getAttributeValueFromEntity(LivingEntity livingEntity,
                                                           IAttribute attribute) {
    List<IAttributeValue> values = new ArrayList<>();
    for (ItemStack is : livingEntity.getEquipment().getArmorContents()) {
      values.addAll(getAttributeValueFromItemStack(is, attribute));
    }
    values.addAll(getAttributeValueFromItemStack(livingEntity.getEquipment().getItemInHand(),
                                                 attribute));
    return values;
  }

  /**
   * Plays the {@link org.bukkit.Effect} of some {@link IAttribute}s at a {@link
   * org.bukkit.Location}.
   *
   * @param location   Location to play Effects
   * @param attributes IAttributes to use for Sounds
   */
  @Override
  public void playAttributeEffects(Location location, IAttribute... attributes) {
    for (IAttribute ia : attributes) {
      if (ia.getEffect() != null) {
        location.getWorld().playEffect(location, ia.getEffect(), 0);
      }
    }
  }

  /**
   * Plays the {@link org.bukkit.Sound} of some {@link IAttribute}s at a {@link
   * org.bukkit.Location}.
   *
   * @param location   Location to play Sounds
   * @param attributes IAttributes to use for Sounds
   */
  @Override
  public void playAttributeSounds(Location location, IAttribute... attributes) {
    for (IAttribute ia : attributes) {
      if (ia.getSound() != null) {
        location.getWorld().playSound(location, ia.getSound(), (float) 1.0, (float) 1.0);
      }
    }
  }

}
