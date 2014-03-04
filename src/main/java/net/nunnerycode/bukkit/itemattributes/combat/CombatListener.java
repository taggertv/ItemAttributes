package net.nunnerycode.bukkit.itemattributes.combat;

import net.nunnerycode.bukkit.itemattributes.ItemAttributesPlugin;
import net.nunnerycode.bukkit.itemattributes.api.attributes.IAttribute;
import net.nunnerycode.bukkit.itemattributes.attributes.AttributeMap;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

import java.util.List;

public final class CombatListener implements Listener {

  private ItemAttributesPlugin plugin;

  public CombatListener(ItemAttributesPlugin plugin) {
    this.plugin = plugin;
  }

  @EventHandler(priority = EventPriority.MONITOR)
  public void onProjectileLaunchEvent(ProjectileLaunchEvent event) {
    if (event.isCancelled() || event.getEntity().getShooter() == null) {
      return;
    }

    IAttribute rangedDamageAttribute = AttributeMap.getInstance().get("ranged-damage");
    IAttribute damageAttribute = AttributeMap.getInstance().get("damage");
    IAttribute armorPenAttribute = AttributeMap.getInstance().get("armor-penetration");
    double rangedDamageAttributeD = 0;
    if (rangedDamageAttribute != null) {
      rangedDamageAttributeD = rangedDamageAttribute.getBaseValue() +
                               plugin.getAttributeHandler()
                                   .getAttributeValueFromEntity(event.getEntity().getShooter(),
                                                                rangedDamageAttribute).asDouble();
    }
    double damageAttributeD = 0;
    if (damageAttribute != null) {
      damageAttributeD = damageAttribute.getBaseValue() + plugin.getAttributeHandler()
          .getAttributeValueFromEntity(event.getEntity().getShooter(),
                                       rangedDamageAttribute).asDouble();
    }
    double armorPenD = 0;
    if (armorPenAttribute != null) {
      armorPenD =
          armorPenAttribute.getBaseValue() + plugin.getAttributeHandler()
              .getAttributeValueFromEntity(event.getEntity().getShooter(), armorPenAttribute)
              .asDouble();
    }

    event.getEntity().setMetadata("itemattributes.ranged-damage", new FixedMetadataValue(plugin,
                                                                                         rangedDamageAttributeD
                                                                                         + damageAttributeD));
    event.getEntity()
        .setMetadata("itemattributes.armor-penetration", new FixedMetadataValue(plugin, armorPenD));
  }

  @EventHandler(priority = EventPriority.LOWEST)
  public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent event) {
    if (event.isCancelled() || !(event.getEntity() instanceof LivingEntity)) {
      return;
    }

    double eventDamage = event.getDamage();
    if ((Boolean) plugin.getSettings()
        .getSettingValue("config.item-only-damage-system.enabled", false)) {
      eventDamage = (Double) plugin.getSettings()
          .getSettingValue("config.item-only-damage-system.base-damage", 1.0);
    }

    LivingEntity damager;
    LivingEntity target = (LivingEntity) event.getEntity();

    IAttribute damageAttribute = AttributeMap.getInstance().get("damage");
    IAttribute meleeDamageAttribute = AttributeMap.getInstance().get("melee-damage");
    IAttribute armorAttribute = AttributeMap.getInstance().get("armor");
    IAttribute armorPenetrationAttribute = AttributeMap.getInstance().get("armor-penetration");

    double armorAttributeD = 0;
    if (armorAttribute != null) {
      armorAttributeD = plugin.getAttributeHandler()
          .getAttributeValueFromEntity(target, armorAttribute).asDouble();
    }

    double damageD = 0;
    double armorPenD = 0;

    if (event.getDamager() instanceof Projectile) {
      damager = ((Projectile) event.getDamager()).getShooter();
      if (event.getDamager().hasMetadata("itemattributes.ranged-damage")) {
        List<MetadataValue> list = event.getDamager().getMetadata("itemattributes.ranged-damage");
        for (MetadataValue mv : list) {
          damageD += mv.asDouble();
        }
      }
      if (event.getDamager().hasMetadata("itemattributes.armor-penetration")) {
        List<MetadataValue>
            list =
            event.getDamager().getMetadata("itemattributes.armor-penetration");
        for (MetadataValue mv : list) {
          armorPenD += mv.asDouble();
        }
      }
    } else if (event.getDamager() instanceof LivingEntity) {
      damager = (LivingEntity) event.getDamager();
      double damageAttributeD = 0;
      if (damageAttribute != null) {
        damageAttributeD = damageAttribute.getBaseValue() + plugin.getAttributeHandler()
            .getAttributeValueFromEntity(damager, damageAttribute).asDouble();
      }
      double meleeDamageAttributeD = 0;
      if (meleeDamageAttribute != null) {
        meleeDamageAttributeD = meleeDamageAttribute.getBaseValue() + plugin.getAttributeHandler()
            .getAttributeValueFromEntity(damager, meleeDamageAttribute).asDouble();
      }
      damageD += damageAttributeD + meleeDamageAttributeD;
      if (armorPenetrationAttribute != null) {
        armorPenD = armorPenetrationAttribute.getBaseValue() +
                    plugin.getAttributeHandler()
                        .getAttributeValueFromEntity(damager, armorPenetrationAttribute).asDouble();
      }
    } else {
      return;
    }

    double armorD = armorAttributeD - armorPenD;

    eventDamage += Math.max(damageD - armorD, 0);

    event.setDamage(eventDamage);
  }

}
