package net.nunnerycode.bukkit.itemattributes.combat;

import net.nunnerycode.bukkit.itemattributes.ItemAttributesPlugin;
import net.nunnerycode.bukkit.itemattributes.api.attributes.IAttribute;
import net.nunnerycode.bukkit.itemattributes.attributes.AttributeMap;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public final class CombatListener implements Listener {

  private ItemAttributesPlugin plugin;

  public CombatListener(ItemAttributesPlugin plugin) {
    this.plugin = plugin;
  }

  @EventHandler(priority = EventPriority.LOWEST)
  public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent event) {
    if (event.isCancelled() || !(event.getDamager() instanceof LivingEntity && event
        .getEntity() instanceof LivingEntity)) {
      return;
    }

    double eventDamage = event.getDamage();
    if ((Boolean) plugin.getSettings()
        .getSettingValue("config.item-only-damage-system.enabled", false)) {
      eventDamage = 0;
    }

    LivingEntity damager = (LivingEntity) event.getDamager();
    LivingEntity target = (LivingEntity) event.getEntity();

    IAttribute damageAttribute = AttributeMap.getInstance().get("damage");
    IAttribute meleeDamageAttribute = AttributeMap.getInstance().get("melee-damage");
    IAttribute armorAttribute = AttributeMap.getInstance().get("armor");

    double damageAttributeD = 0;
    if (damageAttribute != null) {
      damageAttributeD = plugin.getAttributeHandler()
          .getAttributeValueFromEntity(damager, damageAttribute).asDouble();
    }
    double meleeDamageAttributeD = 0;
    if (meleeDamageAttribute != null) {
      meleeDamageAttributeD = plugin.getAttributeHandler()
          .getAttributeValueFromEntity(damager, meleeDamageAttribute).asDouble();
    }
    double armorAttributeD = 0;
    if (armorAttribute != null) {
      armorAttributeD = plugin.getAttributeHandler()
          .getAttributeValueFromEntity(target, armorAttribute).asDouble();
    }

    eventDamage += Math.max(damageAttributeD + meleeDamageAttributeD - armorAttributeD, 0);

    event.setDamage(eventDamage);
  }

}
