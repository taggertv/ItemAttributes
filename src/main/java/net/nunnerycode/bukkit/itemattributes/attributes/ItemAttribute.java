package net.nunnerycode.bukkit.itemattributes.attributes;

import net.nunnerycode.bukkit.itemattributes.api.attributes.Attribute;
import org.bukkit.Effect;
import org.bukkit.Sound;

public class ItemAttribute implements Attribute {

	private final String name;
	private boolean enabled;
	private double maxValue;
	private boolean percentage;
	private String format;
	private Sound sound;
	private double baseValue;
	private Effect effect;

	public ItemAttribute(String name, boolean enabled, double maxValue, boolean percentage, String format,
						 Sound sound, double baseValue) {
		this(name, enabled, maxValue, percentage, format, sound, baseValue, null);
	}

	public ItemAttribute(String name, boolean enabled, double maxValue, boolean percentage, String format,
						 Sound sound, double baseValue, Effect effect) {
		this.name = name;
		this.enabled = enabled;
		this.maxValue = maxValue;
		this.percentage = percentage;
		this.format = format;
		this.sound = sound;
		this.baseValue = baseValue;
		this.effect = effect;
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
	public double getMaxValue() {
		return maxValue;
	}

	@Override
	public void setMaxValue(double d) {
		this.maxValue = d;
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
	public String getFormat() {
		return format;
	}

	@Override
	public void setFormat(String s) {
		this.format = s;
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
	public double getBaseValue() {
		return baseValue;
	}

	@Override
	public void setBaseValue(double d) {
		this.baseValue = d;
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
