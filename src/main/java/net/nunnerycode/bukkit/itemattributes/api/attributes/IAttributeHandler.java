package net.nunnerycode.bukkit.itemattributes.api.attributes;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

public interface IAttributeHandler {

  /**
   * Gets and returns the value of the {@link IAttribute} on the {@link ItemStack}
   * @param itemStack ItemStack to check
   * @param attribute IAttribute to check for
   * @return value of IAttribute on ItemStack
   */
  IAttributeValueList getAttributeValueFromItemStack(ItemStack itemStack, IAttribute attribute);

  /**
   * Gets and returns the value of the {@link IAttribute} on the LivingEntity
   * </br>
   * Equivalent of calling {@link #getAttributeValueFromItemStack(ItemStack, IAttribute)} on all {@link
   * ItemStack}s in {@link LivingEntity#getEquipment()}.
   * @param livingEntity LivingEntity to check on
   * @param attribute IAttribute to check for
   * @return value of IAttribute on ItemStack
   */
  IAttributeValueList getAttributeValueFromEntity(LivingEntity livingEntity,
                                                   IAttribute attribute);

  /**
   * Plays the {@link Effect} of some {@link IAttribute}s at a {@link Location}.
   * @param location Location to play Effects
   * @param attributes IAttributes to use for Sounds
   */
  void playAttributeEffects(Location location, IAttribute... attributes);

  /**
   * Plays the {@link Sound} of some {@link IAttribute}s at a {@link Location}.
   * @param location Location to play Sounds
   * @param attributes IAttributes to use for Sounds
   */
  void playAttributeSounds(Location location, IAttribute... attributes);

}
