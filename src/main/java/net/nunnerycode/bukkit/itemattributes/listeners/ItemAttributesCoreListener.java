package net.nunnerycode.bukkit.itemattributes.listeners;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import net.nunnerycode.bukkit.itemattributes.ItemAttributesPlugin;
import net.nunnerycode.bukkit.itemattributes.api.ItemAttributes;
import net.nunnerycode.bukkit.itemattributes.api.attributes.Attribute;
import net.nunnerycode.bukkit.itemattributes.api.listeners.CoreListener;
import net.nunnerycode.bukkit.itemattributes.events.ItemAttributesCriticalStrikeEvent;
import net.nunnerycode.bukkit.itemattributes.events.ItemAttributesHealthUpdateEvent;
import net.nunnerycode.bukkit.itemattributes.events.ItemAttributesStunStrikeEvent;
import net.nunnerycode.bukkit.itemattributes.utils.ItemAttributesParseUtil;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.commons.lang3.text.WordUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerItemBreakEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public final class ItemAttributesCoreListener implements Listener, CoreListener {

	private final ItemAttributesPlugin plugin;
	private final DecimalFormat decimalFormat;

	public ItemAttributesCoreListener(ItemAttributesPlugin plugin) {
		this.plugin = plugin;
		decimalFormat = new DecimalFormat("#.##");
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onCreatureSpawnEvent(CreatureSpawnEvent event) {
		if (event.isCancelled()) {
			return;
		}
		double maxHealth = event.getEntity().getMaxHealth();
		MetadataValue metadataValue = new FixedMetadataValue(plugin, maxHealth);
		event.getEntity().setMetadata("itemattributes.basehealth", metadataValue);
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onInventoryCloseEventLowest(InventoryCloseEvent event) {
		for (HumanEntity he : event.getViewers()) {
			if (!(he instanceof Player) || he.isDead()) {
				continue;
			}
			Player player = (Player) he;
			handleLevelRequirementCheck(player);
		}
	}

	private boolean handleLevelRequirementCheck(Player player) {
		if (player.hasPermission("itemattributes.admin.ignorelevels")) {
			return false;
		}

		boolean b = false;

		ItemStack itemInHand = player.getEquipment().getItemInHand();
		ItemStack helmet = player.getEquipment().getHelmet();
		ItemStack chestplate = player.getEquipment().getChestplate();
		ItemStack leggings = player.getEquipment().getLeggings();
		ItemStack boots = player.getEquipment().getBoots();

		Attribute levelRequirementAttribute = getPlugin().getSettingsManager().getAttribute("LEVEL REQUIREMENT");

		// item in hand check
		int level = (int) ItemAttributesParseUtil.getValue(getItemStackLore(itemInHand), levelRequirementAttribute);
		if (player.getLevel() < level) {
			if (player.getInventory().firstEmpty() >= 0) {
				player.getInventory().addItem(itemInHand);
			} else {
				player.getWorld().dropItem(player.getLocation(), itemInHand);
			}
			player.getEquipment().setItemInHand(null);
			getPlugin().getLanguageManager().sendMessage(player, "events.unable-to-use",
					new String[][]{{"%itemname%", getItemName(itemInHand)}, {"%level%", String.valueOf(level)}});
			b = true;
		}

		// helmet check
		level = (int) ItemAttributesParseUtil.getValue(getItemStackLore(helmet), levelRequirementAttribute);
		if (player.getLevel() < level) {
			if (player.getInventory().firstEmpty() >= 0) {
				player.getInventory().addItem(helmet);
			} else {
				player.getWorld().dropItem(player.getLocation(), helmet);
			}
			player.getEquipment().setHelmet(null);
			getPlugin().getLanguageManager().sendMessage(player, "events.unable-to-use",
					new String[][]{{"%itemname%", getItemName(helmet)}, {"%level%", String.valueOf(level)}});
			b = true;
		}

		// chestplate check
		level = (int) ItemAttributesParseUtil.getValue(getItemStackLore(chestplate), levelRequirementAttribute);
		if (player.getLevel() < level) {
			if (player.getInventory().firstEmpty() >= 0) {
				player.getInventory().addItem(chestplate);
			} else {
				player.getWorld().dropItem(player.getLocation(), chestplate);
			}
			player.getEquipment().setChestplate(null);
			getPlugin().getLanguageManager().sendMessage(player, "events.unable-to-use",
					new String[][]{{"%itemname%", getItemName(chestplate)}, {"%level%", String.valueOf(level)}});
			b = true;
		}

		// leggings check
		level = (int) ItemAttributesParseUtil.getValue(getItemStackLore(leggings), levelRequirementAttribute);
		if (player.getLevel() < level) {
			if (player.getInventory().firstEmpty() >= 0) {
				player.getInventory().addItem(leggings);
			} else {
				player.getWorld().dropItem(player.getLocation(), leggings);
			}
			player.getEquipment().setLeggings(null);
			getPlugin().getLanguageManager().sendMessage(player, "events.unable-to-use",
					new String[][]{{"%itemname%", getItemName(leggings)}, {"%level%", String.valueOf(level)}});
			b = true;
		}

		// boots check
		level = (int) ItemAttributesParseUtil.getValue(getItemStackLore(boots), levelRequirementAttribute);
		if (player.getLevel() < level) {
			if (player.getInventory().firstEmpty() >= 0) {
				player.getInventory().addItem(boots);
			} else {
				player.getWorld().dropItem(player.getLocation(), boots);
			}
			player.getEquipment().setBoots(null);
			getPlugin().getLanguageManager().sendMessage(player, "events.unable-to-use",
					new String[][]{{"%itemname%", getItemName(boots)}, {"%level%", String.valueOf(level)}});
			b = true;
		}

		return b;
	}

	private String getItemName(ItemStack itemStack) {
		String name = "";
		if (itemStack.hasItemMeta() && itemStack.getItemMeta().hasDisplayName()) {
			name = ChatColor.stripColor(itemStack.getItemMeta().getDisplayName());
		} else {
			String matName = itemStack.getType().name();
			String[] splitMatName = matName.split("_");
			for (int i = 0; i < splitMatName.length; i++) {
				if (i < splitMatName.length - 1) {
					name = name.concat(WordUtils.capitalizeFully(splitMatName[i]).concat(" "));
				} else {
					name = name.concat(WordUtils.capitalizeFully(splitMatName[i]));
				}
			}
		}
		return name;
	}

	private List<String> getItemStackLore(ItemStack itemStack) {
		List<String> lore = new ArrayList<String>();
		if (itemStack != null && itemStack.hasItemMeta() && itemStack.getItemMeta().hasLore()) {
			lore.addAll(itemStack.getItemMeta().getLore());
		}
		return lore;
	}

	@Override
	public ItemAttributes getPlugin() {
		return plugin;
	}

	@EventHandler(priority = EventPriority.LOW)
	public void onInventoryCloseEventLow(InventoryCloseEvent event) {
		Attribute healthAttribute = getPlugin().getSettingsManager().getAttribute("HEALTH");
		if (!healthAttribute.isEnabled()) {
			return;
		}
		for (HumanEntity he : event.getViewers()) {
			if (he.isDead()) {
				continue;
			}
			ItemStack[] armorContents = he.getEquipment().getArmorContents();
			double d = 0.0;
			for (ItemStack is : armorContents) {
				d += ItemAttributesParseUtil.getValue(getItemStackLore(is), healthAttribute);
			}
			d += ItemAttributesParseUtil.getValue(getItemStackLore(he.getEquipment().getItemInHand()),
					healthAttribute);

			double currentHealth = he.getHealth();
			double baseMaxHealth = getPlugin().getSettingsManager().getBasePlayerHealth();

			ItemAttributesHealthUpdateEvent healthUpdateEvent = new ItemAttributesHealthUpdateEvent(he,
					he.getMaxHealth(), baseMaxHealth, d);
			Bukkit.getPluginManager().callEvent(healthUpdateEvent);

			if (healthUpdateEvent.isCancelled()) {
				continue;
			}

			he.setMaxHealth(Math.max(healthUpdateEvent.getBaseHealth() + healthUpdateEvent.getChangeInHealth(), 1));
			he.setHealth(Math.min(Math.max(currentHealth, 0), he.getMaxHealth()));
			if (he instanceof Player) {
				((Player) he).setHealthScale(he.getMaxHealth());
			}
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerJoinEventLowest(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		handleLevelRequirementCheck(player);
	}

	@EventHandler(priority = EventPriority.LOW)
	public void onPlayerJoinEventLow(PlayerJoinEvent event) {
		Attribute healthAttribute = getPlugin().getSettingsManager().getAttribute("HEALTH");
		if (!healthAttribute.isEnabled()) {
			return;
		}
		ItemStack[] armorContents = event.getPlayer().getEquipment().getArmorContents();
		double d = 0.0;
		for (ItemStack is : armorContents) {
			d += ItemAttributesParseUtil.getValue(getItemStackLore(is), healthAttribute);
		}
		d += ItemAttributesParseUtil.getValue(getItemStackLore(event.getPlayer().getItemInHand()),
				healthAttribute);
		double currentHealth = event.getPlayer().getHealth();
		double baseMaxHealth = getPlugin().getSettingsManager().getBasePlayerHealth();

		ItemAttributesHealthUpdateEvent healthUpdateEvent = new ItemAttributesHealthUpdateEvent(event.getPlayer(),
				event.getPlayer().getMaxHealth(), baseMaxHealth, d);
		Bukkit.getPluginManager().callEvent(healthUpdateEvent);

		if (healthUpdateEvent.isCancelled()) {
			return;
		}

		event.getPlayer().setMaxHealth(Math.max(healthUpdateEvent.getBaseHealth() + healthUpdateEvent
				.getChangeInHealth(), 1));
		event.getPlayer().setHealth(Math.min(Math.max(currentHealth, 0), event.getPlayer().getMaxHealth()));
		event.getPlayer().setHealthScale(event.getPlayer().getMaxHealth());
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onEntityTargetEventLowest(EntityTargetEvent event) {
		Attribute healthAttribute = getPlugin().getSettingsManager().getAttribute("HEALTH");
		if (event.isCancelled() || !(event.getEntity() instanceof
				LivingEntity) || event.getEntity() instanceof Player || !healthAttribute.isEnabled()) {
			return;
		}
		LivingEntity entity = (LivingEntity) event.getEntity();
		ItemStack[] armorContents = entity.getEquipment().getArmorContents();
		double d = 0.0;
		for (ItemStack is : armorContents) {
			d += ItemAttributesParseUtil.getValue(getItemStackLore(is), healthAttribute);
		}
		d += ItemAttributesParseUtil.getValue(getItemStackLore(entity.getEquipment().getItemInHand()),
				healthAttribute);
		double currentHealth = entity.getHealth();
		entity.resetMaxHealth();
		double baseMaxHealth = entity.getMaxHealth();
		if (entity.hasMetadata("itemattributes.basehealth")) {
			List<MetadataValue> metadataValueList = entity.getMetadata("itemattributes.basehealth");
			for (MetadataValue mv : metadataValueList) {
				if (mv.getOwningPlugin().equals(getPlugin())) {
					baseMaxHealth = mv.asDouble();
					break;
				}
			}
		}

		ItemAttributesHealthUpdateEvent healthUpdateEvent = new ItemAttributesHealthUpdateEvent(entity,
				entity.getMaxHealth(), baseMaxHealth, d);
		Bukkit.getPluginManager().callEvent(healthUpdateEvent);

		if (healthUpdateEvent.isCancelled()) {
			return;
		}

		entity.setMaxHealth(Math.max(baseMaxHealth + d, 1));
		entity.setHealth(Math.min(Math.max(currentHealth, 0), entity.getMaxHealth()));
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerRespawnEventLowest(PlayerRespawnEvent event) {
		Player player = event.getPlayer();
		handleLevelRequirementCheck(player);
	}

	@EventHandler(priority = EventPriority.LOW)
	public void onPlayerRespawnEventLow(PlayerRespawnEvent event) {
		Attribute healthAttribute = getPlugin().getSettingsManager().getAttribute("HEALTH");
		if (!healthAttribute.isEnabled()) {
			return;
		}
		ItemStack[] armorContents = event.getPlayer().getEquipment().getArmorContents();
		double d = 0.0;
		for (ItemStack is : armorContents) {
			d += ItemAttributesParseUtil.getValue(getItemStackLore(is), healthAttribute);
		}
		d += ItemAttributesParseUtil.getValue(getItemStackLore(event.getPlayer().getItemInHand()),
				healthAttribute);
		double currentHealth = event.getPlayer().getHealth();
		double baseMaxHealth = getPlugin().getSettingsManager().getBasePlayerHealth();

		ItemAttributesHealthUpdateEvent healthUpdateEvent = new ItemAttributesHealthUpdateEvent(event.getPlayer(),
				event.getPlayer().getMaxHealth(), baseMaxHealth, d);
		Bukkit.getPluginManager().callEvent(healthUpdateEvent);

		if (healthUpdateEvent.isCancelled()) {
			return;
		}

		event.getPlayer().setMaxHealth(Math.max(healthUpdateEvent.getBaseHealth() + healthUpdateEvent
				.getChangeInHealth(), 1));
		event.getPlayer().setHealth(Math.min(Math.max(currentHealth, 0), event.getPlayer().getMaxHealth()));
		event.getPlayer().setHealthScale(event.getPlayer().getMaxHealth());
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onItemBreakEventLowest(PlayerItemBreakEvent event) {
		Attribute healthAttribute = getPlugin().getSettingsManager().getAttribute("HEALTH");
		if (!healthAttribute.isEnabled()) {
			return;
		}
		ItemStack[] armorContents = event.getPlayer().getEquipment().getArmorContents();
		double d = 0.0;
		for (ItemStack is : armorContents) {
			d += ItemAttributesParseUtil.getValue(getItemStackLore(is), healthAttribute);
		}
		d += ItemAttributesParseUtil.getValue(getItemStackLore(event.getPlayer().getItemInHand()),
				healthAttribute);
		double currentHealth = event.getPlayer().getHealth();
		double baseMaxHealth = getPlugin().getSettingsManager().getBasePlayerHealth();

		ItemAttributesHealthUpdateEvent healthUpdateEvent = new ItemAttributesHealthUpdateEvent(event.getPlayer(),
				event.getPlayer().getMaxHealth(), baseMaxHealth, d);
		Bukkit.getPluginManager().callEvent(healthUpdateEvent);

		if (healthUpdateEvent.isCancelled()) {
			return;
		}

		event.getPlayer().setMaxHealth(Math.max(healthUpdateEvent.getBaseHealth() + healthUpdateEvent
				.getChangeInHealth(), 1));
		event.getPlayer().setHealth(Math.min(Math.max(currentHealth, 0), event.getPlayer().getMaxHealth()));
		event.getPlayer().setHealthScale(event.getPlayer().getMaxHealth());
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onItemHeldEventLowest(PlayerItemHeldEvent event) {
		Player player = event.getPlayer();
		handleLevelRequirementCheckSlot(player, event.getNewSlot());
	}

	private boolean handleLevelRequirementCheckSlot(Player player, int i) {
		if (player.hasPermission("itemattributes.admin.ignorelevels")) {
			return false;
		}

		boolean b = false;

		ItemStack itemInHand = player.getInventory().getItem(i);
		ItemStack helmet = player.getEquipment().getHelmet();
		ItemStack chestplate = player.getEquipment().getChestplate();
		ItemStack leggings = player.getEquipment().getLeggings();
		ItemStack boots = player.getEquipment().getBoots();

		Attribute levelRequirementAttribute = getPlugin().getSettingsManager().getAttribute("LEVEL REQUIREMENT");

		// item in hand check
		int level = (int) ItemAttributesParseUtil.getValue(getItemStackLore(itemInHand), levelRequirementAttribute);
		if (player.getLevel() < level) {
			if (player.getInventory().firstEmpty() >= 0) {
				player.getInventory().addItem(itemInHand);
			} else {
				player.getWorld().dropItem(player.getLocation(), itemInHand);
			}
			player.getInventory().setItem(i, null);
			getPlugin().getLanguageManager().sendMessage(player, "events.unable-to-use",
					new String[][]{{"%itemname%", getItemName(itemInHand)}, {"%level%", String.valueOf(level)}});
			b = true;
		}

		// helmet check
		level = (int) ItemAttributesParseUtil.getValue(getItemStackLore(helmet), levelRequirementAttribute);
		if (player.getLevel() < level) {
			if (player.getInventory().firstEmpty() >= 0) {
				player.getInventory().addItem(helmet);
			} else {
				player.getWorld().dropItem(player.getLocation(), helmet);
			}
			player.getEquipment().setHelmet(null);
			getPlugin().getLanguageManager().sendMessage(player, "events.unable-to-use",
					new String[][]{{"%itemname%", getItemName(helmet)}, {"%level%", String.valueOf(level)}});
			b = true;
		}

		// chestplate check
		level = (int) ItemAttributesParseUtil.getValue(getItemStackLore(chestplate), levelRequirementAttribute);
		if (player.getLevel() < level) {
			if (player.getInventory().firstEmpty() >= 0) {
				player.getInventory().addItem(chestplate);
			} else {
				player.getWorld().dropItem(player.getLocation(), chestplate);
			}
			player.getEquipment().setChestplate(null);
			getPlugin().getLanguageManager().sendMessage(player, "events.unable-to-use",
					new String[][]{{"%itemname%", getItemName(chestplate)}, {"%level%", String.valueOf(level)}});
			b = true;
		}

		// leggings check
		level = (int) ItemAttributesParseUtil.getValue(getItemStackLore(leggings), levelRequirementAttribute);
		if (player.getLevel() < level) {
			if (player.getInventory().firstEmpty() >= 0) {
				player.getInventory().addItem(leggings);
			} else {
				player.getWorld().dropItem(player.getLocation(), leggings);
			}
			player.getEquipment().setLeggings(null);
			getPlugin().getLanguageManager().sendMessage(player, "events.unable-to-use",
					new String[][]{{"%itemname%", getItemName(leggings)}, {"%level%", String.valueOf(level)}});
			b = true;
		}

		// boots check
		level = (int) ItemAttributesParseUtil.getValue(getItemStackLore(boots), levelRequirementAttribute);
		if (player.getLevel() < level) {
			if (player.getInventory().firstEmpty() >= 0) {
				player.getInventory().addItem(boots);
			} else {
				player.getWorld().dropItem(player.getLocation(), boots);
			}
			player.getEquipment().setBoots(null);
			getPlugin().getLanguageManager().sendMessage(player, "events.unable-to-use",
					new String[][]{{"%itemname%", getItemName(boots)}, {"%level%", String.valueOf(level)}});
			b = true;
		}

		return b;
	}

	@EventHandler(priority = EventPriority.LOW)
	public void onItemHeldEventLow(PlayerItemHeldEvent event) {
		Attribute healthAttribute = getPlugin().getSettingsManager().getAttribute("HEALTH");
		if (!healthAttribute.isEnabled()) {
			return;
		}
		ItemStack[] armorContents = event.getPlayer().getEquipment().getArmorContents();
		double d = 0.0;
		for (ItemStack is : armorContents) {
			d += ItemAttributesParseUtil.getValue(getItemStackLore(is), healthAttribute);
		}
		d += ItemAttributesParseUtil.getValue(getItemStackLore(event.getPlayer().getInventory().getItem(event.getNewSlot())),
				healthAttribute);
		double currentHealth = event.getPlayer().getHealth();
		double baseMaxHealth = getPlugin().getSettingsManager().getBasePlayerHealth();

		ItemAttributesHealthUpdateEvent healthUpdateEvent = new ItemAttributesHealthUpdateEvent(event.getPlayer(),
				event.getPlayer().getMaxHealth(), baseMaxHealth, d);
		Bukkit.getPluginManager().callEvent(healthUpdateEvent);

		if (healthUpdateEvent.isCancelled()) {
			return;
		}

		event.getPlayer().setMaxHealth(Math.max(healthUpdateEvent.getBaseHealth() + healthUpdateEvent
				.getChangeInHealth(), 1));
		event.getPlayer().setHealth(Math.min(Math.max(currentHealth, 0), event.getPlayer().getMaxHealth()));
		event.getPlayer().setHealthScale(event.getPlayer().getMaxHealth());
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onEntityRegainHealthEventLowest(EntityRegainHealthEvent event) {
		if (!(event.getEntity() instanceof Player)) {
			return;
		}
		Player player = (Player) event.getEntity();
		handleLevelRequirementCheck(player);
	}

	@EventHandler(priority = EventPriority.LOW)
	public void onEntityRegainHealthEventLow(EntityRegainHealthEvent event) {
		double amount = event.getAmount();
		Attribute regenerationAttribute = getPlugin().getSettingsManager().getAttribute("REGENERATION");
		if (event.getEntity() instanceof LivingEntity) {
			LivingEntity le = (LivingEntity) event.getEntity();
			ItemStack[] armorContents = le.getEquipment().getArmorContents();
			for (ItemStack is : armorContents) {
				amount += ItemAttributesParseUtil.getValue(getItemStackLore(is), regenerationAttribute);
			}
			amount += ItemAttributesParseUtil.getValue(getItemStackLore(le.getEquipment().getItemInHand()),
					regenerationAttribute);
		}
		event.setAmount(amount);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onProjectileLaunchEventMonitor(ProjectileLaunchEvent event) {
		Projectile projectile = event.getEntity();
		if (projectile.getShooter() == null) {
			return;
		}
		LivingEntity le = projectile.getShooter();
		Material shotItemMaterial = getMaterialFromEntityType(projectile.getType());
		ItemStack shotItem = new ItemStack(shotItemMaterial);
		ItemStack shootingItem = null;

		if (le.getEquipment() != null && le.getEquipment().getItemInHand() != null) {
			shootingItem = le.getEquipment().getItemInHand();
		}

		if (le instanceof Player) {
			ItemStack[] contents = ((Player) le).getInventory().getContents();
			for (ItemStack is : contents) {
				if (is == null || is.getType() == null) {
					continue;
				}
				if (is.getType() == shotItemMaterial) {
					shotItem = is;
					break;
				}
			}
		}

		double criticalRate = 0.0;
		double criticalDamage = 0.0;
		double arrowDamage = 0.0;
		double bowDamage = 0.0;
		double armorDamage = 0.0;
		double armorPenetration = 0.0;
		double stunRate = 0.0;
		int stunLength = 0;

		Attribute damageAttribute = getPlugin().getSettingsManager().getAttribute("DAMAGE");
		Attribute rangedDamageAttribute = getPlugin().getSettingsManager().getAttribute("RANGED DAMAGE");
		Attribute criticalRateAttribute = getPlugin().getSettingsManager().getAttribute("CRITICAL RATE");
		Attribute criticalDamageAttribute = getPlugin().getSettingsManager().getAttribute("CRITICAL DAMAGE");
		Attribute stunRateAttribute = getPlugin().getSettingsManager().getAttribute("STUN RATE");
		Attribute stunLengthAttribute = getPlugin().getSettingsManager().getAttribute("STUN LENGTH");
		Attribute dodgeRateAttribute = getPlugin().getSettingsManager().getAttribute("DODGE RATE");
		Attribute armorPenetrationAttribute = getPlugin().getSettingsManager().getAttribute("ARMOR PENETRATION");

		arrowDamage += ItemAttributesParseUtil.getValue(getItemStackLore(shotItem), damageAttribute);
		arrowDamage += ItemAttributesParseUtil.getValue(getItemStackLore(shotItem), rangedDamageAttribute);
		criticalRate += ItemAttributesParseUtil.getValue(getItemStackLore(shotItem), criticalRateAttribute);
		criticalDamage += ItemAttributesParseUtil.getValue(getItemStackLore(shotItem), criticalDamageAttribute);
		armorPenetration += ItemAttributesParseUtil.getValue(getItemStackLore(shotItem), armorPenetrationAttribute);
		stunRate += ItemAttributesParseUtil.getValue(getItemStackLore(shotItem), stunRateAttribute);
		stunLength += ItemAttributesParseUtil.getValue(getItemStackLore(shotItem), stunLengthAttribute);

		if (shootingItem != null) {
			bowDamage += ItemAttributesParseUtil.getValue(getItemStackLore(shootingItem),
					damageAttribute);
			bowDamage += ItemAttributesParseUtil.getValue(getItemStackLore(shootingItem),
					rangedDamageAttribute);
			criticalRate += ItemAttributesParseUtil.getValue(getItemStackLore(shootingItem),
					criticalRateAttribute);
			criticalDamage += ItemAttributesParseUtil.getValue(getItemStackLore(shootingItem),
					criticalDamageAttribute);
			armorPenetration += ItemAttributesParseUtil.getValue(getItemStackLore(shootingItem),
					armorPenetrationAttribute);
			stunRate += ItemAttributesParseUtil.getValue(getItemStackLore(shootingItem),
					stunRateAttribute);
			stunLength += ItemAttributesParseUtil.getValue(getItemStackLore(shootingItem), stunLengthAttribute);
		}

		for (ItemStack is : le.getEquipment().getArmorContents()) {
			armorDamage += ItemAttributesParseUtil.getValue(getItemStackLore(is), damageAttribute);
			armorDamage += ItemAttributesParseUtil.getValue(getItemStackLore(is), rangedDamageAttribute);
			criticalRate += ItemAttributesParseUtil.getValue(getItemStackLore(is),
					criticalRateAttribute);
			criticalDamage += ItemAttributesParseUtil.getValue(getItemStackLore(is),
					criticalDamageAttribute);
			armorPenetration += ItemAttributesParseUtil.getValue(getItemStackLore(is), armorPenetrationAttribute);
			stunRate += ItemAttributesParseUtil.getValue(getItemStackLore(is), stunRateAttribute);
			stunLength += ItemAttributesParseUtil.getValue(getItemStackLore(is), stunLengthAttribute);
		}

		double totalDamage = arrowDamage + bowDamage + armorDamage;

		event.getEntity().setMetadata("itemattributes.damage", new FixedMetadataValue(plugin, totalDamage));
		event.getEntity().setMetadata("itemattributes.criticalrate", new FixedMetadataValue(plugin, criticalRate));
		event.getEntity().setMetadata("itemattributes.criticaldamage", new FixedMetadataValue(plugin, criticalDamage));
		event.getEntity().setMetadata("itemattributes.armorpenetration", new FixedMetadataValue(plugin,
				armorPenetration));
		event.getEntity().setMetadata("itemattributes.stunrate", new FixedMetadataValue(plugin, stunRate));
		event.getEntity().setMetadata("itemattributes.stunlength", new FixedMetadataValue(plugin, stunLength));
	}

	private Material getMaterialFromEntityType(EntityType entityType) {
		switch (entityType) {
			case ARROW:
				return Material.ARROW;
			case SNOWBALL:
				return Material.SNOW_BALL;
			case FIREBALL:
				return Material.FIREBALL;
			case SMALL_FIREBALL:
				return Material.FIREBALL;
			case ENDER_PEARL:
				return Material.ENDER_PEARL;
			case THROWN_EXP_BOTTLE:
				return Material.EXP_BOTTLE;
			case WITHER_SKULL:
				return Material.FIREBALL;
			case SPLASH_POTION:
				return Material.POTION;
			case EGG:
				return Material.EGG;
			case FISHING_HOOK:
				return Material.FISHING_ROD;
			default:
				return null;
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onEntityDamageByEntityEventLowest(EntityDamageByEntityEvent event) {
		if (event.isCancelled()) {
			return;
		}

		boolean b = false;

		if (event.getEntity() instanceof Player) {
			b = handleLevelRequirementCheck((Player) event.getEntity());
		}

		if (event.getDamager() instanceof Player) {
			b = handleLevelRequirementCheck((Player) event.getDamager());
		}

		event.setCancelled(b);

		if (b) {
			event.setDamage(0D);
		}
	}

	@EventHandler(priority = EventPriority.LOW)
	public void onEntityDamageByEntityEventLow(EntityDamageByEntityEvent event) {
		if (event.isCancelled()) {
			return;
		}

		double originalDamage = event.getDamage();

		double damage;

		double damagerEquipmentDamage = 0D;
		double damagerCriticalChance = getPlugin().getSettingsManager().getBaseCriticalRate();
		double damagerCriticalDamage = getPlugin().getSettingsManager().getBaseCriticalDamage();
		double armorPenetration = 0D;
		double stunRate = getPlugin().getSettingsManager().getBaseStunRate();
		int stunLength = getPlugin().getSettingsManager().getBaseStunLength();
		double dodgeRate = getPlugin().getSettingsManager().getBaseDodgeRate();

		Attribute damageAttribute = getPlugin().getSettingsManager().getAttribute("DAMAGE");
		Attribute meleeDamageAttribute = getPlugin().getSettingsManager().getAttribute("MELEE DAMAGE");
		Attribute criticalRateAttribute = getPlugin().getSettingsManager().getAttribute("CRITICAL RATE");
		Attribute criticalDamageAttribute = getPlugin().getSettingsManager().getAttribute("CRITICAL DAMAGE");
		Attribute stunRateAttribute = getPlugin().getSettingsManager().getAttribute("STUN RATE");
		Attribute stunLengthAttribute = getPlugin().getSettingsManager().getAttribute("STUN LENGTH");
		Attribute dodgeRateAttribute = getPlugin().getSettingsManager().getAttribute("DODGE RATE");
		Attribute armorAttribute = getPlugin().getSettingsManager().getAttribute("ARMOR");

		if (event.getDamager() instanceof Projectile) {
			Projectile projectile = (Projectile) event.getDamager();
			LivingEntity shooter = projectile.getShooter();
			if (shooter != null) {
				if (projectile.hasMetadata("itemattributes.damage")) {
					List<MetadataValue> metadataValueList = projectile.getMetadata("itemattributes.damage");
					for (MetadataValue mv : metadataValueList) {
						if (mv.getOwningPlugin().equals(getPlugin())) {
							damagerEquipmentDamage += mv.asDouble();
							break;
						}
					}
				}
				if (projectile.hasMetadata("itemattributes.criticalrate")) {
					List<MetadataValue> metadataValueList = projectile.getMetadata("itemattributes.criticalrate");
					for (MetadataValue mv : metadataValueList) {
						if (mv.getOwningPlugin().equals(getPlugin())) {
							damagerCriticalChance += mv.asDouble();
							break;
						}
					}
				}
				if (projectile.hasMetadata("itemattributes.criticaldamage")) {
					List<MetadataValue> metadataValueList = projectile.getMetadata("itemattributes.criticaldamage");
					for (MetadataValue mv : metadataValueList) {
						if (mv.getOwningPlugin().equals(getPlugin())) {
							damagerCriticalDamage += mv.asDouble();
							break;
						}
					}
				}
				if (projectile.hasMetadata("itemattributes.armorpenetration")) {
					List<MetadataValue> metadataValueList = projectile.getMetadata("itemattributes.armorpenetration");
					for (MetadataValue mv : metadataValueList) {
						if (mv.getOwningPlugin().equals(getPlugin())) {
							armorPenetration += mv.asDouble();
							break;
						}
					}
				}
				if (projectile.hasMetadata("itemattributes.stunrate")) {
					List<MetadataValue> metadataValueList = projectile.getMetadata("itemattributes.stunrate");
					for (MetadataValue mv : metadataValueList) {
						if (mv.getOwningPlugin().equals(getPlugin())) {
							stunRate += mv.asDouble();
							break;
						}
					}
				}
				if (projectile.hasMetadata("itemattributes.stunlength")) {
					List<MetadataValue> metadataValueList = projectile.getMetadata("itemattributes.stunlength");
					for (MetadataValue mv : metadataValueList) {
						if (mv.getOwningPlugin().equals(getPlugin())) {
							stunLength += mv.asInt();
							break;
						}
					}
				}
			}
		} else if (event.getDamager() instanceof LivingEntity) {
			LivingEntity damager = (LivingEntity) event.getDamager();
			ItemStack[] armor = damager.getEquipment().getArmorContents();
			for (ItemStack is : armor) {
				damagerEquipmentDamage += ItemAttributesParseUtil.getValue(getItemStackLore(is),
						meleeDamageAttribute);
				damagerEquipmentDamage += ItemAttributesParseUtil.getValue(getItemStackLore(is),
						damageAttribute);
				damagerCriticalChance += ItemAttributesParseUtil.getValue(getItemStackLore(is),
						criticalRateAttribute);
				damagerCriticalDamage += ItemAttributesParseUtil.getValue(getItemStackLore(is),
						criticalDamageAttribute);
				stunRate += ItemAttributesParseUtil.getValue(getItemStackLore(is),
						stunRateAttribute);
				stunLength += ItemAttributesParseUtil.getValue(getItemStackLore(is), stunLengthAttribute);
			}
			damagerEquipmentDamage += ItemAttributesParseUtil.getValue(getItemStackLore(damager.getEquipment().getItemInHand
					()), meleeDamageAttribute);
			damagerEquipmentDamage += ItemAttributesParseUtil.getValue(getItemStackLore(damager.getEquipment().getItemInHand()),
					damageAttribute);
			damagerCriticalChance += ItemAttributesParseUtil.getValue(getItemStackLore(damager.getEquipment()
					.getItemInHand()), criticalRateAttribute);
			damagerCriticalDamage += ItemAttributesParseUtil.getValue(getItemStackLore(damager.getEquipment().getItemInHand
					()), criticalDamageAttribute);
			stunRate += ItemAttributesParseUtil.getValue(getItemStackLore(damager.getEquipment().getItemInHand
					()), stunRateAttribute);
			stunLength += ItemAttributesParseUtil.getValue(getItemStackLore(damager.getEquipment().getItemInHand()),
					stunLengthAttribute);
		}

		if (event.getEntity() instanceof LivingEntity) {
			for (ItemStack is : ((LivingEntity) event.getEntity()).getEquipment().getArmorContents()) {
				dodgeRate += ItemAttributesParseUtil.getValue(getItemStackLore(is),
						dodgeRateAttribute);
			}
			dodgeRate += ItemAttributesParseUtil.getValue(getItemStackLore(((LivingEntity) event.getEntity
					()).getEquipment().getItemInHand()), dodgeRateAttribute);
		}

		double damagedEquipmentReduction = 0D;
		if (event.getEntity() instanceof LivingEntity) {
			LivingEntity entity = (LivingEntity) event.getEntity();
			ItemStack[] armor = entity.getEquipment().getArmorContents();
			for (ItemStack is : armor) {
				damagedEquipmentReduction += ItemAttributesParseUtil.getValue(getItemStackLore(is),
						armorAttribute);
			}
			damagedEquipmentReduction += ItemAttributesParseUtil.getValue(getItemStackLore(entity.getEquipment()
					.getItemInHand()), armorAttribute);
		}

		boolean dodged = RandomUtils.nextDouble() < dodgeRate;

		if (dodged) {
			if (event.getEntity() instanceof Player) {
				getPlugin().getLanguageManager().sendMessage(((Player) event.getEntity()), "events.dodge");
			}
			event.setDamage(0);
			event.setCancelled(true);
			return;
		}

		double equipmentDamage = damagerEquipmentDamage - (damagedEquipmentReduction - armorPenetration);
		damage = originalDamage + equipmentDamage;

		if (RandomUtils.nextDouble() < damagerCriticalChance) {

			ItemAttributesCriticalStrikeEvent criticalStrikeEvent = null;

			if (event.getDamager() instanceof LivingEntity && event.getEntity() instanceof LivingEntity) {
				criticalStrikeEvent = new ItemAttributesCriticalStrikeEvent((LivingEntity) event.getDamager(),
						(LivingEntity) event.getEntity(), damagerCriticalChance, damagerCriticalDamage);
				Bukkit.getPluginManager().callEvent(criticalStrikeEvent);
			}

			if (criticalStrikeEvent == null) {
				double critPercentage = (1.00 + damagerCriticalDamage);
				damage *= critPercentage;
				if (event.getDamager() instanceof Player) {
					getPlugin().getLanguageManager().sendMessage((Player) event.getDamager(), "events.critical-hit",
							new String[][]{{"%percentage%", decimalFormat.format(critPercentage * 100)}});
				} else if (event.getDamager() instanceof Projectile && ((Projectile) event.getDamager()).getShooter()
						instanceof Player) {
					getPlugin().getLanguageManager().sendMessage((Player) ((Projectile) event.getDamager()).getShooter(),
							"events.critical-hit", new String[][]{{"%percentage%", decimalFormat.format(critPercentage
							* 100)}});
				}
			} else if (criticalStrikeEvent != null && !criticalStrikeEvent.isCancelled()) {
				double critPercentage = (1.00 + criticalStrikeEvent.getCriticalDamage());
				damage *= critPercentage;
				if (event.getDamager() instanceof Player) {
					getPlugin().getLanguageManager().sendMessage((Player) event.getDamager(), "events.critical-hit",
							new String[][]{{"%percentage%", decimalFormat.format(critPercentage * 100)}});
				} else if (event.getDamager() instanceof Projectile && ((Projectile) event.getDamager()).getShooter()
						instanceof Player) {
					getPlugin().getLanguageManager().sendMessage((Player) ((Projectile) event.getDamager()).getShooter(),
							"events.critical-hit", new String[][]{{"%percentage%", decimalFormat.format(critPercentage
							* 100)}});
				}
			}
		}

		if (RandomUtils.nextDouble() < stunRate) {
			if (event.getEntity() instanceof LivingEntity) {
				LivingEntity defender = (LivingEntity) event.getEntity();
				LivingEntity attacker = null;
				if (event.getDamager() instanceof Player) {
					getPlugin().getLanguageManager().sendMessage((Player) event.getDamager(), "events.stun");
					attacker = (LivingEntity) event.getDamager();
				} else if (event.getDamager() instanceof Projectile && ((Projectile) event.getDamager()).getShooter()
						instanceof Player) {
					getPlugin().getLanguageManager().sendMessage((Player) ((Projectile) event.getDamager()).getShooter(),
							"events.stun");
					attacker = ((Projectile) event.getDamager()).getShooter();
				}
				if (attacker == null) {
					defender.addPotionEffect(new PotionEffect(PotionEffectType.SLOW,
							stunLength * 20, 7));
					defender.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS,
							stunLength * 20, 7));
				} else {
					ItemAttributesStunStrikeEvent stunStrikeEvent = new ItemAttributesStunStrikeEvent(attacker,
							defender, stunRate, stunLength);
					Bukkit.getPluginManager().callEvent(stunStrikeEvent);

					if (!stunStrikeEvent.isCancelled()) {
						defender.addPotionEffect(new PotionEffect(PotionEffectType.SLOW,
								stunLength * 20, 7));
						defender.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS,
								stunLength * 20, 7));
					}
				}
			}
		}

		event.setDamage(damage);
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onEntityDamageEventLowest(EntityDamageEvent event) {
		if (event.isCancelled() || event instanceof EntityDamageByEntityEvent || !(event.getEntity() instanceof
				Player)) {
			return;
		}
		Player player = (Player) event.getEntity();
		handleLevelRequirementCheck(player);
	}

	@EventHandler(priority = EventPriority.LOW)
	public void onEntityDamageEventLow(EntityDamageEvent event) {
		if (event.isCancelled() || event instanceof EntityDamageByEntityEvent || !(event.getEntity() instanceof
				LivingEntity)) {
			return;
		}
		EntityDamageEvent.DamageCause damageCause = event.getCause();
		if (damageCause == EntityDamageEvent.DamageCause.DROWNING || damageCause == EntityDamageEvent
				.DamageCause.STARVATION || damageCause == EntityDamageEvent.DamageCause.SUFFOCATION || damageCause ==
				EntityDamageEvent.DamageCause.SUICIDE || damageCause == EntityDamageEvent.DamageCause.THORNS) {
			return;
		}

		if (damageCause == EntityDamageEvent.DamageCause.FIRE || damageCause == EntityDamageEvent.DamageCause
				.FIRE_TICK) {
			boolean b = false;
			for (ItemStack is : ((LivingEntity) event.getEntity()).getEquipment().getArmorContents()) {
				if (!b) {
					b = ItemAttributesParseUtil.hasFormatInCollection(getItemStackLore(is),
							getPlugin().getSettingsManager().getAttribute("FIRE IMMUNITY").getFormat());
				}
			}
			if (!b) {
				b = ItemAttributesParseUtil.hasFormatInCollection(getItemStackLore(((LivingEntity) event.getEntity()).getEquipment()
						.getItemInHand()), getPlugin().getSettingsManager().getAttribute("FIRE IMMUNITY").getFormat());
			}
			if (b) {
				event.setDamage(0);
				event.setCancelled(true);
			}
		} else if (damageCause == EntityDamageEvent.DamageCause.POISON) {
			boolean b = false;
			for (ItemStack is : ((LivingEntity) event.getEntity()).getEquipment().getArmorContents()) {
				if (!b) {
					b = ItemAttributesParseUtil.hasFormatInCollection(getItemStackLore(is),
							getPlugin().getSettingsManager().getAttribute("POISON IMMUNITY").getFormat());
				}
			}
			if (!b) {
				b = ItemAttributesParseUtil.hasFormatInCollection(getItemStackLore(((LivingEntity) event.getEntity())
						.getEquipment().getItemInHand()), getPlugin().getSettingsManager().getAttribute("POISON " +
						"IMMUNITY").getFormat());
			}
			if (b) {
				event.setDamage(0);
				event.setCancelled(true);
			}
		} else if (damageCause == EntityDamageEvent.DamageCause.WITHER) {
			boolean b = false;
			for (ItemStack is : ((LivingEntity) event.getEntity()).getEquipment().getArmorContents()) {
				if (!b) {
					b = ItemAttributesParseUtil.hasFormatInCollection(getItemStackLore(is),
							getPlugin().getSettingsManager().getAttribute("WITHER IMMUNITY").getFormat());
				}
			}
			if (!b) {
				b = ItemAttributesParseUtil.hasFormatInCollection(getItemStackLore(((LivingEntity) event.getEntity()).getEquipment()
						.getItemInHand()), getPlugin().getSettingsManager().getAttribute("WITHER IMMUNITY").getFormat());
			}
			if (b) {
				event.setDamage(0);
				event.setCancelled(true);
			}
		}

		playAttributeSounds(((LivingEntity) event.getEntity()).getEyeLocation(), getPlugin().getSettingsManager()
				.getAttribute("FIRE IMMUNITY"), getPlugin().getSettingsManager().getAttribute("POISON IMMUNITY"),
				getPlugin().getSettingsManager().getAttribute("WITHER IMMUNITY"));
	}

	private void playAttributeSounds(Location location, Attribute... attributes) {
		for (Attribute attribute : attributes) {
			location.getWorld().playSound(location, attribute.getSound(), 1F, 1F);
		}
	}

}
