package net.nunnerycode.bukkit.itemattributes.attributes;

import net.nunnerycode.bukkit.itemattributes.api.attributes.AttributeValue;

public class ItemAttributeValue implements AttributeValue {

	private final Object value;

	public ItemAttributeValue(Object value) {
		this.value = value;
	}

	@Override
	public double asDouble() {
		if (value instanceof Double) {
			return (Double) value;
		}
		return 0D;
	}

	@Override
	public int asInt() {
		if (value instanceof Integer) {
			return (Integer) value;
		}
		return 0;
	}

	@Override
	public long asLong() {
		if (value instanceof Long) {
			return (Long) value;
		}
		return 0;
	}

	@Override
	public String asString() {
		return String.valueOf(value);
	}

	@Override
	public boolean asBoolean() {
		if (value instanceof String) {
			return String.valueOf(value).equals("true");
		}
		if (value instanceof Long) {
			return (Long) value != 0;
		}
		if (value instanceof Double) {
			return (Double) value != 0D;
		}
		return value instanceof Integer && (Integer) value != 0;
	}
}
