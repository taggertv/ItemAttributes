package net.nunnerycode.bukkit.itemstats;

import java.util.Collection;
import org.apache.commons.lang3.math.NumberUtils;
import org.bukkit.ChatColor;

public final class ParseUtil {

	private ParseUtil() {
	}

	public static double getHealth(Collection<String> collection, String format) {
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
			d += NumberUtils.toDouble(withoutLetters, 0.0);
		}
		return d;
	}

	public static double getDamage(Collection<String> collection, String format) {
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
			d += NumberUtils.toDouble(withoutLetters, 0.0);
		}
		return d;
	}

	public static double getMeleeDamage(Collection<String> collection, String format) {
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
			d += NumberUtils.toDouble(withoutLetters, 0.0);
		}
		return d;
	}

	public static double getRangedDamage(Collection<String> collection, String format) {
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
			d += NumberUtils.toDouble(withoutLetters, 0.0);
		}
		return d;
	}

	public static double getCriticalDamage(Collection<String> collection, String format) {
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
			d += NumberUtils.toDouble(withoutLetters, 0.0) / 100D;
		}
		return d;
	}

	public static double getArmorPenetration(Collection<String> collection, String format) {
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
			d += NumberUtils.toDouble(withoutLetters, 0.0) / 100D;
		}
		return d;
	}

	public static boolean hasPoisonImmunity(Collection<String> collection, String format) {
		boolean b = false;
		if (collection == null || collection.isEmpty() || format == null || format.isEmpty()) {
			return b;
		}
		b = containsIgnoreCase(collection, format);
		return b;
	}

	public static boolean hasFireImmunity(Collection<String> collection, String format) {
		boolean b = false;
		if (collection == null || collection.isEmpty() || format == null || format.isEmpty()) {
			return b;
		}
		b = containsIgnoreCase(collection, format);
		return b;
	}

	public static boolean hasWitherImmunity(Collection<String> collection, String format) {
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

	public static double getRegeneration(Collection<String> collection, String format) {
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
			d += NumberUtils.toDouble(withoutLetters, 0.0);
		}
		return d;
	}

	public static double getArmor(Collection<String> collection, String format) {
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
			d += NumberUtils.toDouble(withoutLetters, 0.0);
		}
		return d;
	}

	public static double getCriticalRate(Collection<String> collection, String format) {
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
			d += NumberUtils.toDouble(withoutLetters, 0.0) / 100D;
		}
		return d;
	}

	public static int getLevelRequired(Collection<String> collection, String format) {
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
			i += NumberUtils.toInt(withoutLetters, 0);
		}
		return i;
	}

}
