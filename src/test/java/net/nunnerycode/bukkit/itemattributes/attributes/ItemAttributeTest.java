package net.nunnerycode.bukkit.itemattributes.attributes;

import net.nunnerycode.bukkit.itemattributes.api.attributes.AttributeFlag;

import org.junit.Assert;
import org.junit.Test;

public class ItemAttributeTest {

  @Test
  public void doesConstructorCreateWithCorrectName() {
    String expected = "test name";
    ItemAttribute itemAttribute = new ItemAttribute(expected);
    String actual = itemAttribute.getName();

    Assert.assertNotNull(actual);
    Assert.assertEquals(expected, actual);
  }

  @Test
  public void doesConstructorCreateWithEmptyIfGivenNull() {
    ItemAttribute itemAttribute = new ItemAttribute(null);

    String actual = itemAttribute.getName();

    Assert.assertNotNull(actual);
    Assert.assertTrue(actual.isEmpty());
  }

  @Test(expected = IllegalArgumentException.class)
  public void doesSetAttributeValueThrowExceptionIfGivenNonmatchingValue() {
    ItemAttribute itemAttribute = new ItemAttribute("testing");

    AttributeFlag attributeFlag = AttributeFlag.AFFECTS_MOBS;
    Double value = 0.0D;

    itemAttribute.setFlagValue(attributeFlag, value);
  }

  @Test
  public void doesSetAttributeValueSetCorrectValue() {
    ItemAttribute itemAttribute = new ItemAttribute("testing");

    AttributeFlag attributeFlag = AttributeFlag.AFFECTS_MOBS;
    boolean expected = true;

    itemAttribute.setFlagValue(attributeFlag, expected);

    Object actual = itemAttribute.getFlagValue(attributeFlag, !expected);

    Assert.assertNotNull(actual);
    Assert.assertEquals(expected, actual);
  }

  @Test
  public void doesGetAttributeValueReturnDefaultIfNoValueIsSet() {
    ItemAttribute itemAttribute = new ItemAttribute("testing");

    AttributeFlag attributeFlag = AttributeFlag.LEVEL_REQUIREMENT;
    int expected = 1;

    Object actual = itemAttribute.getFlagValue(attributeFlag, expected);

    Assert.assertNotNull(actual);
    Assert.assertEquals(expected, actual);
  }

}
