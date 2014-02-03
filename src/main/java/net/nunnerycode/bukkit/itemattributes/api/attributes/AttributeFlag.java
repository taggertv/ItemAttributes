package net.nunnerycode.bukkit.itemattributes.api.attributes;

import org.bukkit.Effect;
import org.bukkit.Sound;
import org.bukkit.event.entity.EntityDamageEvent;

public enum AttributeFlag {

  ENABLED(Boolean.class), FORMAT(String.class), PERCENTAGE(Boolean.class), PLAYER_PERCENTAGE_DIVIDER(Double.class),
  MOB_PERCENTAGE_DIVIDER(Double.class), PLAYER_BASE_VALUE(Double.class), MOB_BASE_VALUE(Double.class),
  AFFECTS_PLAYERS(Boolean.class), AFFECTS_MOBS(Boolean.class), EFFECT(Effect.class), SOUND(Sound.class),
  DAMAGE(Double.class), DAMAGE_MELEE(Double.class), DAMAGE_RANGED(Double.class), DAMAGE_FIRE(Double.class),
  ATTACK_SPEED(Double.class), REGENERATION(Double.class), CRITICAL_RATE(Double.class),
  CRITICAL_DAMAGE(Double.class), DODGE_RATE(Double.class), HEALTH(Double.class), BLOCK(Double.class),
  PARRY(Double.class), STUN_RATE(Double.class), STUN_LENGTH(Integer.class), ARMOR_PENETRATION(Double.class),
  ARMOR(Double.class), PERMISSION_REQUIREMENT(String.class), LEVEL_REQUIREMENT(Integer.class),
  DAMAGE_IMMUNITY(EntityDamageEvent.DamageCause.class), PLAYER_CAP_VALUE(Double.class), MOB_CAP_VALUE(Double.class);

  private final Class<?> clazz;

  private AttributeFlag(Class<?> clazz) {
    this.clazz = clazz;
  }

  public Class<?> getMatchingType() {
    return clazz;
  }

  public boolean matchesType(Object object) {
    return object.getClass().equals(clazz);
  }

  public static AttributeFlag getFromName(String name) {
    for (AttributeFlag val : values()) {
      if (val.name().equals(name)) {
        return val;
      }
      if (val.name().replace("_", "-").equals(name)) {
        return val;
      }
    }
    return null;
  }

}
