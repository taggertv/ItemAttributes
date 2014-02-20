package net.nunnerycode.bukkit.itemattributes.attributes;

import net.nunnerycode.bukkit.itemattributes.api.attributes.IAttribute;
import net.nunnerycode.bukkit.itemattributes.api.attributes.IAttributeHandler;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

import java.util.Set;

public final class ItemAttributeHandler implements IAttributeHandler {

  /**
   * Gets and returns the value of the {@link net.nunnerycode.bukkit.itemattributes.api.attributes.IAttribute} on the {@link org.bukkit.inventory.ItemStack}
   *
   * @param itemStack ItemStack to check
   * @param attribute IAttribute to check for
   * @return value of IAttribute on ItemStack
   */
  @Override
  public ItemAttributeValue getAttributeValueFromItemStack(ItemStack itemStack, IAttribute attribute) {
    return new ItemAttributeValue(0);
  }

  /**
   * Gets and returns the value of the {@link net.nunnerycode.bukkit.itemattributes.api.attributes.IAttribute} on the LivingEntity </br> Equivalent of
   * calling {@link #getAttributesPresentOnItemStack(org.bukkit.inventory.ItemStack)} on all {@link org.bukkit.inventory.ItemStack}s in {@link
   * org.bukkit.entity.LivingEntity#getEquipment()}.
   *
   * @param livingEntity LivingEntity to check on
   * @param attribute    IAttribute to check for
   * @return value of IAttribute on ItemStack
   */
  @Override
  public ItemAttributeValue getAttributeValueFromEntity(LivingEntity livingEntity, IAttribute attribute) {
    return new ItemAttributeValue(0);
  }

  /**
   * Gets and returns a {@link java.util.Set} of {@link net.nunnerycode.bukkit.itemattributes.api.attributes.IAttribute}s
   * contained on an {@link org.bukkit.inventory.ItemStack}.
   *
   * @param itemStack ItemStack to check
   * @return Set of IAttributes
   */
  @Override
  public Set<IAttribute> getAttributesPresentOnItemStack(ItemStack itemStack) {
    return null;
  }

  /**
   * Returns true if an {@link org.bukkit.inventory.ItemStack} has an {@link net.nunnerycode.bukkit.itemattributes.api.attributes.IAttribute} on it.
   *
   * @param itemStack ItemStack to check
   * @param attribute IAttribute to check for
   * @return if ItemStack has the IAttribute
   */
  @Override
  public boolean hasAttributeOnItemStack(ItemStack itemStack, IAttribute attribute) {
    return false;
  }

  /**
   * Returns true if an {@link org.bukkit.entity.LivingEntity} has an {@link net.nunnerycode.bukkit.itemattributes.api.attributes.IAttribute} on it.
   *
   * @param livingEntity ItemStack to check
   * @param attribute    IAttribute to check for
   * @return if LivingEntity has the IAttribute
   */
  @Override
  public boolean hasAttributeOnEntity(LivingEntity livingEntity, IAttribute attribute) {
    return false;
  }

  /**
   * Plays the {@link org.bukkit.Effect} of some {@link net.nunnerycode.bukkit.itemattributes.api.attributes.IAttribute}s at a {@link org.bukkit.Location}.
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
   * Plays the {@link org.bukkit.Sound} of some {@link net.nunnerycode.bukkit.itemattributes.api.attributes.IAttribute}s at a {@link org.bukkit.Location}.
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
