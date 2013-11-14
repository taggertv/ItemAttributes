package net.nunnerycode.bukkit.itemattributes.attributes;

import net.nunnerycode.bukkit.itemattributes.api.attributes.Attribute;

public class ItemAttribute implements Attribute {

	private final String name;
	private boolean enabled;
	private double maxValue;
	private boolean percentage;
	private String format;

	public ItemAttribute(String name, boolean enabled, double maxValue, boolean percentage, String format) {
		this.name = name;
		this.enabled = enabled;
		this.maxValue = maxValue;
		this.percentage = percentage;
		this.format = format;
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
}
