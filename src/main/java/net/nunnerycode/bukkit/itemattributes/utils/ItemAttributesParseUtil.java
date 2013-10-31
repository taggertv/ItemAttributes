package net.nunnerycode.bukkit.itemattributes.utils;

import java.util.Collection;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.bukkit.ChatColor;

public final class ItemAttributesParseUtil {

	private ItemAttributesParseUtil() {
	}

	public static double getDouble(Collection<String> collection, String format) {
		double d = 0.0;
		if (collection == null || collection.isEmpty() || format == null || format.isEmpty()) {
			return d;
		}
		for (String s : collection) {
			String stripped = ChatColor.stripColor(s);
			String withoutNumbers = stripped.replaceAll("[0-9\\+%\\-]", "").trim();
			String withoutLetters = stripped.replaceAll("[a-zA-Z\\+%:]", "").trim();
			String withoutVariables = format.replaceAll("%(?s)(.*?)%", "").trim();
			if (!withoutNumbers.equals(withoutVariables)) {
				continue;
			}
			if (withoutLetters.contains(" - ")) {
				String[] split = withoutLetters.split(" - ");
				if (split.length > 1) {
					double first = NumberUtils.toDouble(split[0], 0.0);
					double second = NumberUtils.toDouble(split[1], 0.0);
					d += RandomUtils.nextDouble() * (Math.max(first, second) - Math.min(first,
							second) + Math.min(first, second));
				}
			} else {
				d += NumberUtils.toDouble(withoutLetters, 0.0);
			}
		}
		return d;
	}

	public static double getDoublePercentage(Collection<String> collection, String format) {
		double d = 0.0;
		if (collection == null || collection.isEmpty()) {
			return d;
		}
		for (String s : collection) {
			String stripped = ChatColor.stripColor(s);
			String withoutNumbers = stripped.replaceAll("[0-9\\+%\\-]", "").trim();
			String withoutLetters = stripped.replaceAll("[a-zA-Z\\+%:]", "").trim();
			String withoutVariables = format.replaceAll("%(?s)(.*?)%", "").trim();
			if (!withoutNumbers.equals(withoutVariables)) {
				continue;
			}
			if (withoutLetters.contains(" - ")) {
				String[] split = withoutLetters.split(" - ");
				if (split.length > 1) {
					double first = NumberUtils.toDouble(split[0], 0.0);
					double second = NumberUtils.toDouble(split[1], 0.0);
					d += (RandomUtils.nextDouble() * (Math.max(first, second) - Math.min(first,
							second) + Math.min(first, second))) / 100D;
				}
			} else {
				d += NumberUtils.toDouble(withoutLetters, 0.0) / 100D;
			}
		}
		return d;
	}

	public static int getInt(Collection<String> collection, String format) {
		int i = 0;
		if (collection == null || collection.isEmpty() || format == null || format.isEmpty()) {
			return i;
		}
		for (String s : collection) {
			String stripped = ChatColor.stripColor(s);
			String withoutNumbers = stripped.replaceAll("[0-9\\+%\\-]", "").trim();
			String withoutLetters = stripped.replaceAll("[a-zA-Z\\+%:]", "").trim();
			String withoutVariables = format.replaceAll("%(?s)(.*?)%", "").trim();
			if (!withoutNumbers.equals(withoutVariables)) {
				continue;
			}
			if (withoutLetters.contains(" - ")) {
				String[] split = withoutLetters.split(" - ");
				if (split.length > 1) {
					int first = NumberUtils.toInt(split[0], 0);
					int second = NumberUtils.toInt(split[1], 0);
					i += RandomUtils.nextDouble() * (Math.max(first, second) - Math.min(first,
							second) + Math.min(first, second));
				}
			} else {
				i += NumberUtils.toInt(withoutLetters, 0);
			}
		}
		return i;
	}

	public static boolean hasFormatInCollection(Collection<String> collection, String format) {
		boolean b = false;
		if (collection == null || collection.isEmpty() || format == null || format.isEmpty()) {
			return b;
		}
		b = containsIgnoreCase(collection, format);
		return b;
	}

	private static boolean containsIgnoreCase(Collection<String> collection, String string) {
		for (String s : collection) {
			if (ChatColor.stripColor(s).equalsIgnoreCase(ChatColor.stripColor(string))) {
				return true;
			}
		}
		return false;
	}

}
