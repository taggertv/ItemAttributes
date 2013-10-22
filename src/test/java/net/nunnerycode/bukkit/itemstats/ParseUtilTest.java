package net.nunnerycode.bukkit.itemstats;

import java.util.Arrays;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

public class ParseUtilTest {
	@Test
	public void testGetHealthPositive() throws Exception {
		List<String> collection = Arrays.asList("This", "item", "has", "+10 Health");
		String format = "%value% Health";

		double actual = ParseUtil.getHealth(collection, format);
		double expected = 10D;

		Assert.assertTrue("Actual value is not equal to expected value", actual == expected);
	}

	@Test
	public void testGetDamagePositive() throws Exception {
		List<String> collection = Arrays.asList("This", "item", "has", "+10 Damage");
		String format = "%value% Damage";

		double actual = ParseUtil.getDamage(collection, format);
		double expected = 10D;

		Assert.assertTrue("Actual value is not equal to expected value", actual == expected);
	}

	@Test
	public void testGetCriticalDamagePositive() throws Exception {
		List<String> collection = Arrays.asList("This", "item", "has", "+10 Critical Damage");
		String format = "%value% Critical Damage";

		double actual = ParseUtil.getCriticalDamage(collection, format);
		double expected = 10D;

		Assert.assertTrue("Actual value is not equal to expected value", actual == expected);
	}

	@Test
	public void testGetRegenerationPositive() throws Exception {
		List<String> collection = Arrays.asList("This", "item", "has", "+10 Regeneration");
		String format = "%value% Regeneration";

		double actual = ParseUtil.getRegeneration(collection, format);
		double expected = 10D;

		Assert.assertTrue("Actual value is not equal to expected value", actual == expected);
	}

	@Test
	public void testGetArmorPositive() throws Exception {
		List<String> collection = Arrays.asList("This", "item", "has", "+10 Armor");
		String format = "%value% Armor";

		double actual = ParseUtil.getArmor(collection, format);
		double expected = 10D;

		Assert.assertTrue("Actual value is not equal to expected value", actual == expected);
	}

	@Test
	public void testGetCriticalRatePositive() throws Exception {
		List<String> collection = Arrays.asList("This", "item", "has", "+10% Critical Rate");
		String format = "%value% Critical Rate";

		double actual = ParseUtil.getCriticalRate(collection, format);
		double expected = 0.1D;

		Assert.assertTrue("Actual value is not equal to expected value", actual == expected);
	}

	@Test
	public void testGetLevelRequirementPositive() throws Exception {
		List<String> collection = Arrays.asList("This", "item", "has", "Level Required: 10");
		String format = "Level Required: %value%";

		int actual = ParseUtil.getLevelRequired(collection, format);
		int expected = 10;

		Assert.assertTrue("Actual value is not equal to expected value", actual == expected);
	}

	@Test
	public void testGetHealthNegative() throws Exception {
		List<String> collection = Arrays.asList("This", "item", "has", "+100 Health");
		String format = "%value% Health";

		double actual = ParseUtil.getHealth(collection, format);
		double expected = 10D;

		Assert.assertFalse("Actual value is equal to expected value", actual == expected);
	}

	@Test
	public void testGetDamageNegative() throws Exception {
		List<String> collection = Arrays.asList("This", "item", "has", "+100 Damage");
		String format = "%value% Damage";

		double actual = ParseUtil.getDamage(collection, format);
		double expected = 10D;

		Assert.assertFalse("Actual value is equal to expected value", actual == expected);
	}

	@Test
	public void testGetCriticalDamageNegative() throws Exception {
		List<String> collection = Arrays.asList("This", "item", "has", "+100 Critical Damage");
		String format = "%value% Critical Damage";

		double actual = ParseUtil.getCriticalDamage(collection, format);
		double expected = 10D;

		Assert.assertFalse("Actual value is equal to expected value", actual == expected);
	}

	@Test
	public void testGetRegenerationNegative() throws Exception {
		List<String> collection = Arrays.asList("This", "item", "has", "+100 Regeneration");
		String format = "%value% Regeneration";

		double actual = ParseUtil.getRegeneration(collection, format);
		double expected = 10D;

		Assert.assertFalse("Actual value is equal to expected value", actual == expected);
	}

	@Test
	public void testGetArmorNegative() throws Exception {
		List<String> collection = Arrays.asList("This", "item", "has", "+100 Armor");
		String format = "%value% Armor";

		double actual = ParseUtil.getArmor(collection, format);
		double expected = 10D;

		Assert.assertFalse("Actual value is equal to expected value", actual == expected);
	}

	@Test
	public void testGetCriticalRateNegative() throws Exception {
		List<String> collection = Arrays.asList("This", "item", "has", "+100% Critical Rate");
		String format = "%value% Critical Rate";

		double actual = ParseUtil.getCriticalRate(collection, format);
		double expected = 0.1D;

		Assert.assertFalse("Actual value is equal to expected value", actual == expected);
	}

	@Test
	public void testGetLevelRequirementNegative() throws Exception {
		List<String> collection = Arrays.asList("This", "item", "has", "Level Required: 100");
		String format = "Level Required: %value%";

		int actual = ParseUtil.getLevelRequired(collection, format);
		int expected = 10;

		Assert.assertFalse("Actual value is equal to expected value", actual == expected);
	}
}
