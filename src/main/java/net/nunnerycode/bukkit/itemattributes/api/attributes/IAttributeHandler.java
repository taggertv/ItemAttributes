package net.nunnerycode.bukkit.itemattributes.api.attributes;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Set;

public interface IAttributeHandler {

  /**
   * Gets and returns the value of the {@link IAttribute} on the {@link ItemStack}
   * @param itemStack ItemStack to check
   * @param attribute IAttribute to check for
   * @return value of IAttribute on ItemStack
   */
  double getAttributeValueFromItemStack(ItemStack itemStack, IAttribute attribute);

  /**
   * Gets and returns the value of the {@link IAttribute} on the LivingEntity
   * </br>
   * Equivalent of calling {@link #getAttributesPresentOnItemStack(ItemStack)} on all {@link
   * ItemStack}s in {@link org.bukkit.entity.LivingEntity#getEquipment()}.
   * @param livingEntity LivingEntity to check on
   * @param attribute IAttribute to check for
   * @return value of IAttribute on ItemStack
   */
  double getAttributeValueFromEntity(LivingEntity livingEntity, IAttribute attribute);

  /**
   * Gets and returns a {@link Set} of {@link IAttribute}s contained on an {@link ItemStack}.
   * @param itemStack ItemStack to check
   * @return Set of IAttributes
   */
  Set<IAttribute> getAttributesPresentOnItemStack(ItemStack itemStack);

  /**
   * Returns true if an {@link ItemStack} has an {@link IAttribute} on it.
   * @param itemStack ItemStack to check
   * @param attribute IAttribute to check for
   * @return if ItemStack has the IAttribute
   */
  boolean hasAttributeOnItemStack(ItemStack itemStack, IAttribute attribute);

  /**
   * Returns true if an {@link LivingEntity} has an {@link IAttribute} on it.
   * @param livingEntity ItemStack to check
   * @param attribute IAttribute to check for
   * @return if LivingEntity has the IAttribute
   */
  boolean hasAttributeOnEntity(LivingEntity livingEntity, IAttribute attribute);

  /**
   * Gets a {@link List} of {@link String}s from the {@link IAttribute} on the {@link ItemStack}.
   * @param itemStack ItemStack to check
   * @param attribute IAttribute to check for
   * @return List of Strings for the IAttribute
   */
  List<String> getAttributeStringsFromItemStack(ItemStack itemStack, IAttribute attribute);

  /**
   * Gets a {@link List} of {@link String}s from the {@link LivingEntity} on the {@link ItemStack}.
   * @param livingEntity ItemStack to check
   * @param attribute IAttribute to check for
   * @return List of Strings for the IAttribute
   */
  List<String> getAttributeStringsFromEntity(LivingEntity livingEntity, IAttribute attribute);

  /**
   * Plays the {@link org.bukkit.Effect} of some {@link IAttribute}s at a {@link Location}.
   * @param location Location to play Effects
   * @param attributes IAttributes to use for Sounds
   */
  void playAttributeEffects(Location location, IAttribute... attributes);

  /**
   * Plays the {@link org.bukkit.Sound} of some {@link IAttribute}s at a {@link Location}.
   * @param location Location to play Sounds
   * @param attributes IAttributes to use for Sounds
   */
  void playAttributeSounds(Location location, IAttribute... attributes);

}
