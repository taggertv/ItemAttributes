package net.nunnerycode.bukkit.itemattributes.attributes;

import net.nunnerycode.bukkit.itemattributes.api.attributes.Attribute;
import org.bukkit.Effect;
import org.bukkit.Sound;

public class ItemAttribute implements Attribute {

	private final String name;
	private boolean enabled;
	private boolean defaultEnabled;
	// begin max values
	private double mobMaxValue;
	private double defaultMobMaxValue;
	private double playerMaxValue;
	private double defaultPlayerMaxValue;
	private double maxValue;
	// end max values
	private boolean percentage;
	private boolean defaultPercentage;
	private String format;
	private String defaultFormat;
	private Sound sound;
	private Sound defaultSound;
	// begin base values
	private double mobBaseValue;
	private double defaultMobBaseValue;
	private double playerBaseValue;
	private double defaultPlayerBaseValue;
	private double baseValue;
	// end base values
	private Effect effect;
	private Effect defaultEffect;
	private boolean affectsPlayers;
	private boolean defaultAffectsPlayers;
	private boolean affectsMobs;
	private boolean defaultAffectsMobs;

	public ItemAttribute(String name, boolean enabled, double maxValue, boolean percentage, String format,
						 Sound sound, double baseValue) {
		this(name, enabled, maxValue, percentage, format, sound, baseValue, null);
	}

	public ItemAttribute(String name, boolean enabled, double maxValue, boolean percentage, String format,
						 Sound sound, double baseValue, Effect effect) {
		this(name, enabled, maxValue, percentage, format, sound, baseValue, effect, true, true);
	}

	public ItemAttribute(String name, boolean enabled, double maxValue, boolean percentage, String format,
						 Sound sound, double baseValue, Effect effect, boolean affectsPlayers, boolean affectsMobs) {
		this(name, enabled, maxValue, maxValue, percentage, format, sound, baseValue, baseValue, effect,
				affectsPlayers, affectsMobs);
	}

	public ItemAttribute(String name, boolean enabled, double playerMaxValue, double mobMaxValue,
						 boolean percentage, String format, Sound sound, double playerBaseValue, double mobBaseValue,
						 Effect effect, boolean affectsPlayers, boolean affectsMobs) {
		this.name = name;
		this.enabled = enabled;
		this.defaultEnabled = enabled;
		this.playerMaxValue = playerMaxValue;
		this.defaultPlayerMaxValue = playerMaxValue;
		this.mobMaxValue = mobMaxValue;
		this.defaultMobMaxValue = mobMaxValue;
		this.percentage = percentage;
		this.defaultPercentage = percentage;
		this.format = format;
		this.defaultFormat = format;
		this.sound = sound;
		this.defaultSound = sound;
		this.playerBaseValue = playerBaseValue;
		this.defaultPlayerBaseValue = playerBaseValue;
		this.mobBaseValue = mobBaseValue;
		this.defaultMobBaseValue = mobBaseValue;
		this.effect = effect;
		this.defaultEffect = effect;
		this.affectsPlayers = affectsPlayers;
		this.defaultAffectsPlayers = affectsPlayers;
		this.affectsMobs = affectsMobs;
		this.defaultAffectsMobs = affectsMobs;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

	@Override
	public void setEnabled(boolean b) {
		this.enabled = b;
	}

	@Override
	public boolean isDefaultEnabled() {
		return defaultEnabled;
	}

	@Override
	@Deprecated
	public double getMaxValue() {
		return maxValue;
	}

	@Override
	@Deprecated
	public void setMaxValue(double d) {
		this.maxValue = d;
	}

	@Override
	public double getMaxValueMobs() {
		return mobMaxValue;
	}

	@Override
	public void setMaxValueMobs(double d) {
		this.mobMaxValue = d;
	}

	@Override
	public double getDefaultMaxValueMobs() {
		return defaultMobMaxValue;
	}

	@Override
	public double getMaxValuePlayers() {
		return playerMaxValue;
	}

	@Override
	public void setMaxValuePlayers(double d) {
		this.playerMaxValue = d;
	}

	@Override
	public double getDefaultMaxValuePlayers() {
		return defaultPlayerMaxValue;
	}

	@Override
	public boolean isPercentage() {
		return percentage;
	}

	@Override
	public void setPercentage(boolean b) {
		this.percentage = b;
	}

	@Override
	public boolean isDefaultPercentage() {
		return defaultPercentage;
	}

	@Override
	public String getFormat() {
		return format;
	}

	@Override
	public void setFormat(String s) {
		this.format = s;
	}

	@Override
	public String getDefaultFormat() {
		return defaultFormat;
	}

	@Override
	public String getName() {
		return name;
	}

	public Sound getSound() {
		return sound;
	}

	public void setSound(Sound s) {
		this.sound = s;
	}

	@Override
	public Sound getDefaultSound() {
		return defaultSound;
	}

	@Override
	@Deprecated
	public double getBaseValue() {
		return baseValue;
	}

	@Override
	@Deprecated
	public void setBaseValue(double d) {
		this.baseValue = d;
	}

	@Override
	public double getMobsBaseValue() {
		return mobBaseValue;
	}

	@Override
	public void setMobsBaseValue(double d) {
		this.mobBaseValue = d;
	}

	@Override
	public double getDefaultMobsBaseValue() {
		return defaultMobBaseValue;
	}

	@Override
	public double getPlayersBaseValue() {
		return playerBaseValue;
	}

	@Override
	public void setPlayersBaseValue(double d) {
		this.playerBaseValue = d;
	}

	@Override
	public double getDefaultPlayersBaseValue() {
		return defaultPlayerBaseValue;
	}

	@Override
	public Effect getEffect() {
		return effect;
	}

	@Override
	public void setEffect(Effect e) {
		this.effect = e;
	}

	@Override
	public Effect getDefaultEffect() {
		return defaultEffect;
	}

	@Override
	public boolean isAffectsMobs() {
		return affectsMobs;
	}

	@Override
	public void setAffectsMobs(boolean b) {
		this.affectsMobs = b;
	}

	@Override
	public boolean isDefaultAffectsMobs() {
		return defaultAffectsMobs;
	}

	@Override
	public boolean isAffectsPlayers() {
		return affectsPlayers;
	}

	@Override
	public void setAffectsPlayers(boolean b) {
		this.affectsPlayers = b;
	}

	@Override
	public boolean isDefaultAffectsPlayers() {
		return defaultAffectsPlayers;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof ItemAttribute)) return false;

		ItemAttribute that = (ItemAttribute) o;

		if (name != null ? !name.equals(that.name) : that.name != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		return name != null ? name.hashCode() : 0;
	}
}
