package net.nunnerycode.bukkit.itemattributes.api.attributes;

import org.junit.Assert;
import org.junit.Test;

public class AttributeFlagTest {

  @Test
  public void testMatches() throws Exception {
    AttributeFlag flag = AttributeFlag.AFFECTS_MOBS;
    Boolean value = true;

    boolean expected = true;
    boolean actual = flag.matchesType(value);

    Assert.assertEquals(expected, actual);
  }

  @Test
  public void testGetFromNameWithUnderscore() throws Exception {
    String name = "AFFECTS_MOBS";

    AttributeFlag expected = AttributeFlag.AFFECTS_MOBS;
    AttributeFlag actual = AttributeFlag.getFromName(name);

    Assert.assertNotNull(actual);
    Assert.assertEquals(expected, actual);
  }

  @Test
  public void testGetFromNameWithHyphen() throws Exception {
    String name = "AFFECTS-MOBS";

    AttributeFlag expected = AttributeFlag.AFFECTS_MOBS;
    AttributeFlag actual = AttributeFlag.getFromName(name);

    Assert.assertNotNull(actual);
    Assert.assertEquals(expected, actual);
  }

}
