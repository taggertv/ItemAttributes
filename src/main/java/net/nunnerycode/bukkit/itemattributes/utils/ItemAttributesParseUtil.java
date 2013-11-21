package net.nunnerycode.bukkit.itemattributes.utils;

import java.util.Collection;
import net.nunnerycode.bukkit.itemattributes.api.attributes.Attribute;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.bukkit.ChatColor;

@Deprecated
public final class ItemAttributesParseUtil {

	private ItemAttributesParseUtil() {
	}

	@Deprecated
	public static double getValue(Collection<String> collection, Attribute attribute) {
		if (collection == null || attribute == null) {
			return 0.0;
		}
		if (!attribute.isEnabled()) {
			return 0.0;
		}
		if (attribute.isPercentage()) {
			return getDoublePercentage(collection, attribute, attribute.getMaxValue());
		}
		return getDouble(collection, attribute);
	}

	@Deprecated
	public static double getDoublePercentage(Collection<String> collection, Attribute attribute, double base) {
		double d = 0.0;
		if (collection == null || collection.isEmpty() || attribute == null) {
			return d;
		}
		for (String s : collection) {
			String stripped = ChatColor.stripColor(s);
			String withoutNumbers = stripped.replaceAll("[0-9\\+%\\-]", "").trim();
			String withoutLetters = stripped.replaceAll("[a-zA-Z\\+%:]", "").trim();
			String withoutVariables = attribute.getFormat().replaceAll("%(?s)(.*?)%", "").trim();
			if (!withoutNumbers.equals(withoutVariables)) {
				continue;
			}
			if (!s.contains("%")) {
				if (withoutLetters.contains(" - ")) {
					String[] split = withoutLetters.split(" - ");
					double first = NumberUtils.toDouble(split[0], 0.0);
					double second = NumberUtils.toDouble(split[1], 0.0);
					d += (RandomUtils.nextDouble() * (Math.max(first, second) - Math.min(first,
							second)) + Math.min(first, second)) / ((base != 0D) ? base : 100D);
				} else {
					d += NumberUtils.toDouble(withoutLetters, 0.0) / ((base != 0D) ? base : 100D);
				}
			} else {
				if (withoutLetters.contains(" - ")) {
					String[] split = withoutLetters.split(" - ");
					if (split.length > 1) {
						double first = NumberUtils.toDouble(split[0], 0.0);
						double second = NumberUtils.toDouble(split[1], 0.0);
						d += (RandomUtils.nextDouble() * (Math.max(first, second) - Math.min(first,
								second)) + Math.min(first, second)) / 100D;
					}
				} else {
					d += NumberUtils.toDouble(withoutLetters, 0.0) / 100D;
				}
			}
		}
		return d;
	}

	@Deprecated
	public static double getDouble(Collection<String> collection, Attribute attribute) {
		double d = 0.0;
		if (collection == null || collection.isEmpty() || attribute == null) {
			return d;
		}
		for (String s : collection) {
			String stripped = ChatColor.stripColor(s);
			String withoutNumbers = stripped.replaceAll("[0-9\\+%\\-]", "").trim();
			String withoutLetters = stripped.replaceAll("[a-zA-Z\\+%:]", "").trim();
			String withoutVariables = attribute.getFormat().replaceAll("%(?s)(.*?)%", "").trim();
			if (!withoutNumbers.equals(withoutVariables)) {
				continue;
			}
			if (withoutLetters.contains(" - ")) {
				String[] split = withoutLetters.split(" - ");
				if (split.length > 1) {
					double first = NumberUtils.toDouble(split[0], 0.0);
					double second = NumberUtils.toDouble(split[1], 0.0);
					d += RandomUtils.nextDouble() * (Math.max(first, second) - Math.min(first,
							second)) + Math.min(first, second);
				}
			} else {
				d += NumberUtils.toDouble(withoutLetters, 0.0);
			}
		}
		return d;
	}

	@Deprecated
	public static double getDoublePercentage(Collection<String> collection, Attribute attribute) {
		return getDoublePercentage(collection, attribute, 100D);
	}

	@Deprecated
	public static int getInt(Collection<String> collection, Attribute attribute) {
		int i = 0;
		if (collection == null || collection.isEmpty() || attribute == null) {
			return i;
		}
		for (String s : collection) {
			String stripped = ChatColor.stripColor(s);
			String withoutNumbers = stripped.replaceAll("[0-9\\+%\\-]", "").trim();
			String withoutLetters = stripped.replaceAll("[a-zA-Z\\+%:]", "").trim();
			String withoutVariables = attribute.getFormat().replaceAll("%(?s)(.*?)%", "").trim();
			if (!withoutNumbers.equals(withoutVariables)) {
				continue;
			}
			if (withoutLetters.contains(" - ")) {
				String[] split = withoutLetters.split(" - ");
				if (split.length > 1) {
					int first = NumberUtils.toInt(split[0], 0);
					int second = NumberUtils.toInt(split[1], 0);
					i += RandomUtils.nextDouble() * (Math.max(first, second) - Math.min(first,
							second)) + Math.min(first, second);
				}
			} else {
				i += NumberUtils.toInt(withoutLetters, 0);
			}
		}
		return i;
	}

	@Deprecated
	public static boolean hasFormatInCollection(Collection<String> collection, String format) {
		boolean b = false;
		if (collection == null || collection.isEmpty() || format == null || format.isEmpty()) {
			return b;
		}
		for (String s : collection) {
			if (ChatColor.stripColor(s).equalsIgnoreCase(ChatColor.stripColor(format))) {
				b = true;
			}
		}
		return b;
	}

}
