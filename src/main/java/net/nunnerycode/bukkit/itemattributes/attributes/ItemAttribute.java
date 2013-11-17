package net.nunnerycode.bukkit.itemattributes.attributes;

import net.nunnerycode.bukkit.itemattributes.api.attributes.Attribute;
import org.bukkit.Sound;

public class ItemAttribute implements Attribute {

	private final String name;
	private boolean enabled;
	private double maxValue;
	private boolean percentage;
	private String format;
	private Sound sound;
	private double baseValue;

	public ItemAttribute(String name, boolean enabled, double maxValue, boolean percentage, String format,
						 Sound sound, double baseValue) {
		this.name = name;
		this.enabled = enabled;
		this.maxValue = maxValue;
		this.percentage = percentage;
		this.format = format;
		this.sound = sound;
		this.baseValue = baseValue;
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
		this.sound = sound;
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
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof ItemAttribute)) return false;

		ItemAttribute that = (ItemAttribute) o;

		if (Double.compare(that.baseValue, baseValue) != 0) return false;
		if (enabled != that.enabled) return false;
		if (Double.compare(that.maxValue, maxValue) != 0) return false;
		if (percentage != that.percentage) return false;
		if (format != null ? !format.equals(that.format) : that.format != null) return false;
		if (name != null ? !name.equals(that.name) : that.name != null) return false;
		if (sound != that.sound) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result;
		long temp;
		result = name != null ? name.hashCode() : 0;
		result = 31 * result + (enabled ? 1 : 0);
		temp = Double.doubleToLongBits(maxValue);
		result = 31 * result + (int) (temp ^ (temp >>> 32));
		result = 31 * result + (percentage ? 1 : 0);
		result = 31 * result + (format != null ? format.hashCode() : 0);
		result = 31 * result + (sound != null ? sound.hashCode() : 0);
		temp = Double.doubleToLongBits(baseValue);
		result = 31 * result + (int) (temp ^ (temp >>> 32));
		return result;
	}
}
