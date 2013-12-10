package net.nunnerycode.bukkit.itemattributes.listeners;

import java.text.DecimalFormat;
import java.util.List;
import net.nunnerycode.bukkit.itemattributes.ItemAttributesPlugin;
import net.nunnerycode.bukkit.itemattributes.api.ItemAttributes;
import net.nunnerycode.bukkit.itemattributes.api.attributes.Attribute;
import net.nunnerycode.bukkit.itemattributes.api.listeners.CoreListener;
import net.nunnerycode.bukkit.itemattributes.attributes.ItemAttributeValue;
import net.nunnerycode.bukkit.itemattributes.events.ItemAttributesAttributeEvent;
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
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerEvent;
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
		try {
			for (HumanEntity he : event.getViewers()) {
				if (!(he instanceof Player) || he.isDead()) {
					continue;
				}
				Player player = (Player) he;
				handleLevelRequirementCheck(player);
				handlePermissionCheck(player);
			}
		} catch (Exception e) {
			// do nothing
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
		int level = (int) getPlugin().getAttributeHandler().getAttributeValueFromItemStack(player, itemInHand,
				levelRequirementAttribute);
		if (player.getLevel() < level) {
			if (player.getInventory().firstEmpty() >= 0) {
				player.getInventory().addItem(itemInHand);
			} else {
				player.getWorld().dropItem(player.getLocation(), itemInHand);
			}
			player.getEquipment().setItemInHand(new ItemStack(Material.AIR));
			getPlugin().getLanguageManager().sendMessage(player, "events.unable-to-use-level",
					new String[][]{{"%itemname%", getItemName(itemInHand)}, {"%level%", String.valueOf(level)}});
			b = true;
		}

		// helmet check
		level = (int) getPlugin().getAttributeHandler().getAttributeValueFromItemStack(player, helmet,
				levelRequirementAttribute);
		if (player.getLevel() < level) {
			if (player.getInventory().firstEmpty() >= 0) {
				player.getInventory().addItem(helmet);
			} else {
				player.getWorld().dropItem(player.getLocation(), helmet);
			}
			player.getEquipment().setHelmet(new ItemStack(Material.AIR));
			getPlugin().getLanguageManager().sendMessage(player, "events.unable-to-use-level",
					new String[][]{{"%itemname%", getItemName(helmet)}, {"%level%", String.valueOf(level)}});
			b = true;
		}

		// chestplate check
		level = (int) getPlugin().getAttributeHandler().getAttributeValueFromItemStack(player, chestplate,
				levelRequirementAttribute);
		if (player.getLevel() < level) {
			if (player.getInventory().firstEmpty() >= 0) {
				player.getInventory().addItem(chestplate);
			} else {
				player.getWorld().dropItem(player.getLocation(), chestplate);
			}
			player.getEquipment().setChestplate(new ItemStack(Material.AIR));
			getPlugin().getLanguageManager().sendMessage(player, "events.unable-to-use-level",
					new String[][]{{"%itemname%", getItemName(chestplate)}, {"%level%", String.valueOf(level)}});
			b = true;
		}

		// leggings check
		level = (int) getPlugin().getAttributeHandler().getAttributeValueFromItemStack(player, leggings,
				levelRequirementAttribute);
		if (player.getLevel() < level) {
			if (player.getInventory().firstEmpty() >= 0) {
				player.getInventory().addItem(leggings);
			} else {
				player.getWorld().dropItem(player.getLocation(), leggings);
			}
			player.getEquipment().setLeggings(new ItemStack(Material.AIR));
			getPlugin().getLanguageManager().sendMessage(player, "events.unable-to-use-level",
					new String[][]{{"%itemname%", getItemName(leggings)}, {"%level%", String.valueOf(level)}});
			b = true;
		}

		// boots check
		level = (int) getPlugin().getAttributeHandler().getAttributeValueFromItemStack(player, boots,
				levelRequirementAttribute);
		if (player.getLevel() < level) {
			if (player.getInventory().firstEmpty() >= 0) {
				player.getInventory().addItem(boots);
			} else {
				player.getWorld().dropItem(player.getLocation(), boots);
			}
			player.getEquipment().setBoots(new ItemStack(Material.AIR));
			getPlugin().getLanguageManager().sendMessage(player, "events.unable-to-use-level",
					new String[][]{{"%itemname%", getItemName(boots)}, {"%level%", String.valueOf(level)}});
			b = true;
		}

		if (b) {
			playAttributeSoundsAndEffects(player.getEyeLocation(), levelRequirementAttribute);
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

	private boolean handlePermissionCheck(Player player) {
		if (player.hasPermission("itemattributes.admin.ignorepermissions")) {
			return false;
		}

		boolean b = false;

		ItemStack itemInHand = player.getEquipment().getItemInHand();
		ItemStack helmet = player.getEquipment().getHelmet();
		ItemStack chestplate = player.getEquipment().getChestplate();
		ItemStack leggings = player.getEquipment().getLeggings();
		ItemStack boots = player.getEquipment().getBoots();

		Attribute permissionRequirementAttribute = getPlugin().getSettingsManager().getAttribute("PERMISSION REQUIREMENT");

		// item in hand check
		List<String> perms = getPlugin().getAttributeHandler().getAttributeStringsFromItemStack(itemInHand,
				permissionRequirementAttribute);
		for (String s : perms) {
			if (!getPlugin().getPermissionsManager().hasPermission(player, s)) {
				if (player.getInventory().firstEmpty() >= 0) {
					player.getInventory().addItem(itemInHand);
				} else {
					player.getWorld().dropItem(player.getLocation(), itemInHand);
				}
				player.getEquipment().setItemInHand(new ItemStack(Material.AIR));
				getPlugin().getLanguageManager().sendMessage(player, "events.unable-to-use-permission",
						new String[][]{{"%itemname%", getItemName(itemInHand)}});
				b = true;
				break;
			}
		}

		// helmet check
		perms = getPlugin().getAttributeHandler().getAttributeStringsFromItemStack(helmet,
				permissionRequirementAttribute);
		for (String s : perms) {
			if (!getPlugin().getPermissionsManager().hasPermission(player, s)) {
				if (player.getInventory().firstEmpty() >= 0) {
					player.getInventory().addItem(helmet);
				} else {
					player.getWorld().dropItem(player.getLocation(), helmet);
				}
				player.getEquipment().setHelmet(new ItemStack(Material.AIR));
				getPlugin().getLanguageManager().sendMessage(player, "events.unable-to-use-permission",
						new String[][]{{"%itemname%", getItemName(helmet)}});
				b = true;
				break;
			}
		}

		// chestplate check
		perms = getPlugin().getAttributeHandler().getAttributeStringsFromItemStack(chestplate,
				permissionRequirementAttribute);
		for (String s : perms) {
			if (!getPlugin().getPermissionsManager().hasPermission(player, s)) {
				if (player.getInventory().firstEmpty() >= 0) {
					player.getInventory().addItem(chestplate);
				} else {
					player.getWorld().dropItem(player.getLocation(), chestplate);
				}
				player.getEquipment().setChestplate(new ItemStack(Material.AIR));
				getPlugin().getLanguageManager().sendMessage(player, "events.unable-to-use-permission",
						new String[][]{{"%itemname%", getItemName(chestplate)}});
				b = true;
				break;
			}
		}

		// leggings check
		perms = getPlugin().getAttributeHandler().getAttributeStringsFromItemStack(leggings,
				permissionRequirementAttribute);
		for (String s : perms) {
			if (!getPlugin().getPermissionsManager().hasPermission(player, s)) {
				if (player.getInventory().firstEmpty() >= 0) {
					player.getInventory().addItem(leggings);
				} else {
					player.getWorld().dropItem(player.getLocation(), leggings);
				}
				player.getEquipment().setLeggings(new ItemStack(Material.AIR));
				getPlugin().getLanguageManager().sendMessage(player, "events.unable-to-use-permission",
						new String[][]{{"%itemname%", getItemName(leggings)}});
				b = true;
				break;
			}
		}

		// boots check
		perms = getPlugin().getAttributeHandler().getAttributeStringsFromItemStack(boots,
				permissionRequirementAttribute);
		for (String s : perms) {
			if (!getPlugin().getPermissionsManager().hasPermission(player, s)) {
				if (player.getInventory().firstEmpty() >= 0) {
					player.getInventory().addItem(boots);
				} else {
					player.getWorld().dropItem(player.getLocation(), boots);
				}
				player.getEquipment().setBoots(new ItemStack(Material.AIR));
				getPlugin().getLanguageManager().sendMessage(player, "events.unable-to-use-permission",
						new String[][]{{"%itemname%", getItemName(boots)}});
				b = true;
				break;
			}
		}

		if (b) {
			playAttributeSoundsAndEffects(player.getEyeLocation(), permissionRequirementAttribute);
		}

		return b;
	}

	@EventHandler(priority = EventPriority.LOW)
	public void onInventoryCloseEventLow(InventoryCloseEvent event) {
		Attribute healthAttribute = getPlugin().getSettingsManager().getAttribute("HEALTH");
		if (!healthAttribute.isEnabled()) {
			return;
		}
		try {
			for (HumanEntity he : event.getViewers()) {
				if (he.isDead()) {
					continue;
				}

				double d = getPlugin().getAttributeHandler().getAttributeValueFromEntity(he, healthAttribute);
				double currentHealth = event.getPlayer().getHealth();
				double baseMaxHealth = healthAttribute.getPlayersBaseValue();

				ItemAttributesAttributeEvent iaae = new ItemAttributesAttributeEvent(he, he,
						healthAttribute, new ItemAttributeValue(d));
				Bukkit.getPluginManager().callEvent(iaae);

				if (iaae.isCancelled()) {
					return;
				}

				he.setMaxHealth(Math.max(baseMaxHealth + iaae.getAttributeValue().asDouble(), 1));
				he.setHealth(Math.min(Math.max(currentHealth, 0), event.getPlayer().getMaxHealth()));
				if (he instanceof Player) {
					((Player) he).setHealthScale(he.getMaxHealth());
				}
				playAttributeSoundsAndEffects(he.getEyeLocation(), healthAttribute);
			}
		} catch (Exception e) {
			// do nothing
		}
	}

	private void playAttributeSoundsAndEffects(Location location, Attribute... attributes) {
		getPlugin().getAttributeHandler().playAttributeEffects(location, attributes);
		getPlugin().getAttributeHandler().playAttributeSounds(location, attributes);
	}

	@Override
	public ItemAttributes getPlugin() {
		return plugin;
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerJoinEventLowest(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		handleLevelRequirementCheck(player);
		handlePermissionCheck(player);
	}

	@EventHandler(priority = EventPriority.LOW)
	public void onPlayerJoinEventLow(PlayerJoinEvent event) {
		handlePlayerHealthCheck(event);
	}

	private void handlePlayerHealthCheck(PlayerEvent event) {
		Attribute healthAttribute = getPlugin().getSettingsManager().getAttribute("HEALTH");

		if (!healthAttribute.isEnabled() || !healthAttribute.isAffectsPlayers() || event instanceof Cancellable &&
				((Cancellable) event).isCancelled()) {
			return;
		}

		double d = getPlugin().getAttributeHandler().getAttributeValueFromEntity(event.getPlayer(), healthAttribute);
		double currentHealth = event.getPlayer().getHealth();

		ItemAttributesAttributeEvent iaae = new ItemAttributesAttributeEvent(event.getPlayer(), event.getPlayer(),
				healthAttribute, new ItemAttributeValue(d));
		Bukkit.getPluginManager().callEvent(iaae);

		if (iaae.isCancelled()) {
			return;
		}

		event.getPlayer().setMaxHealth(Math.max(healthAttribute.getPlayersBaseValue() + iaae.getAttributeValue()
				.asDouble(), 1));
		event.getPlayer().setHealth(Math.min(Math.max(currentHealth, 1), event.getPlayer().getMaxHealth()));
		event.getPlayer().setHealthScale(event.getPlayer().getMaxHealth());
		playAttributeSoundsAndEffects(event.getPlayer().getEyeLocation(), healthAttribute);
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onEntityTargetEventLowest(EntityTargetEvent event) {
		handleLivingEntityHealthCheck(event);
	}

	private void handleLivingEntityHealthCheck(EntityEvent event) {
		Attribute healthAttribute = getPlugin().getSettingsManager().getAttribute("HEALTH");
		if (!(event.getEntity() instanceof LivingEntity) || event.getEntity() instanceof Player || !healthAttribute
				.isEnabled() || !healthAttribute.isAffectsMobs() || event instanceof Cancellable && ((Cancellable)
				event).isCancelled()) {
			return;
		}
		LivingEntity entity = (LivingEntity) event.getEntity();
		double d = getPlugin().getAttributeHandler().getAttributeValueFromEntity(entity, healthAttribute);
		double currentHealth = entity.getHealth();
		double baseMaxHealth = 0D;
		if (entity.hasMetadata("itemattributes.basehealth")) {
			List<MetadataValue> metadataValueList = entity.getMetadata("itemattributes.basehealth");
			for (MetadataValue mv : metadataValueList) {
				if (mv.getOwningPlugin().equals(getPlugin())) {
					baseMaxHealth = mv.asDouble();
					break;
				}
			}
		} else {
			entity.resetMaxHealth();
			baseMaxHealth = entity.getMaxHealth();
		}
		entity.setHealth((baseMaxHealth + d) / 2);
		entity.setMaxHealth(baseMaxHealth + d);
		entity.setHealth(Math.max(1, Math.min(currentHealth, entity.getMaxHealth())));
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerRespawnEventLowest(PlayerRespawnEvent event) {
		Player player = event.getPlayer();
		handleLevelRequirementCheck(player);
		handlePermissionCheck(player);
	}

	@EventHandler(priority = EventPriority.LOW)
	public void onPlayerRespawnEventLow(PlayerRespawnEvent event) {
		handlePlayerHealthCheck(event);
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onItemBreakEventLowest(PlayerItemBreakEvent event) {
		handlePlayerHealthCheck(event);
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onItemHeldEventLowest(PlayerItemHeldEvent event) {
		Player player = event.getPlayer();
		handleLevelRequirementCheckSlot(player, event.getNewSlot());
		handlePermissionCheck(player, event.getNewSlot());
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
		int level = (int) getPlugin().getAttributeHandler().getAttributeValueFromItemStack(player, itemInHand,
				levelRequirementAttribute);
		if (player.getLevel() < level) {
			if (player.getInventory().firstEmpty() >= 0) {
				player.getInventory().addItem(itemInHand);
			} else {
				player.getWorld().dropItem(player.getLocation(), itemInHand);
			}
			player.getInventory().setItem(i, new ItemStack(Material.AIR));
			getPlugin().getLanguageManager().sendMessage(player, "events.unable-to-use-level",
					new String[][]{{"%itemname%", getItemName(itemInHand)}, {"%level%", String.valueOf(level)}});
			b = true;
		}

		// helmet check
		level = (int) getPlugin().getAttributeHandler().getAttributeValueFromItemStack(player, helmet,
				levelRequirementAttribute);
		if (player.getLevel() < level) {
			if (player.getInventory().firstEmpty() >= 0) {
				player.getInventory().addItem(helmet);
			} else {
				player.getWorld().dropItem(player.getLocation(), helmet);
			}
			player.getEquipment().setHelmet(new ItemStack(Material.AIR));
			getPlugin().getLanguageManager().sendMessage(player, "events.unable-to-use-level",
					new String[][]{{"%itemname%", getItemName(helmet)}, {"%level%", String.valueOf(level)}});
			b = true;
		}

		// chestplate check
		level = (int) getPlugin().getAttributeHandler().getAttributeValueFromItemStack(player, chestplate,
				levelRequirementAttribute);
		if (player.getLevel() < level) {
			if (player.getInventory().firstEmpty() >= 0) {
				player.getInventory().addItem(chestplate);
			} else {
				player.getWorld().dropItem(player.getLocation(), chestplate);
			}
			player.getEquipment().setChestplate(new ItemStack(Material.AIR));
			getPlugin().getLanguageManager().sendMessage(player, "events.unable-to-use-level",
					new String[][]{{"%itemname%", getItemName(chestplate)}, {"%level%", String.valueOf(level)}});
			b = true;
		}

		// leggings check
		level = (int) getPlugin().getAttributeHandler().getAttributeValueFromItemStack(player, leggings,
				levelRequirementAttribute);
		if (player.getLevel() < level) {
			if (player.getInventory().firstEmpty() >= 0) {
				player.getInventory().addItem(leggings);
			} else {
				player.getWorld().dropItem(player.getLocation(), leggings);
			}
			player.getEquipment().setLeggings(new ItemStack(Material.AIR));
			getPlugin().getLanguageManager().sendMessage(player, "events.unable-to-use-level",
					new String[][]{{"%itemname%", getItemName(leggings)}, {"%level%", String.valueOf(level)}});
			b = true;
		}

		// boots check
		level = (int) getPlugin().getAttributeHandler().getAttributeValueFromItemStack(player, boots,
				levelRequirementAttribute);
		if (player.getLevel() < level) {
			if (player.getInventory().firstEmpty() >= 0) {
				player.getInventory().addItem(boots);
			} else {
				player.getWorld().dropItem(player.getLocation(), boots);
			}
			player.getEquipment().setBoots(new ItemStack(Material.AIR));
			getPlugin().getLanguageManager().sendMessage(player, "events.unable-to-use-level",
					new String[][]{{"%itemname%", getItemName(boots)}, {"%level%", String.valueOf(level)}});
			b = true;
		}

		if (b) {
			playAttributeSoundsAndEffects(player.getEyeLocation(), levelRequirementAttribute);
		}

		return b;
	}

	private boolean handlePermissionCheck(Player player, int slot) {
		if (player.hasPermission("itemattributes.admin.ignorepermissions")) {
			return false;
		}

		boolean b = false;

		ItemStack itemInHand = player.getInventory().getItem(slot);
		ItemStack helmet = player.getEquipment().getHelmet();
		ItemStack chestplate = player.getEquipment().getChestplate();
		ItemStack leggings = player.getEquipment().getLeggings();
		ItemStack boots = player.getEquipment().getBoots();

		Attribute permissionRequirementAttribute = getPlugin().getSettingsManager().getAttribute("PERMISSION " +
				"REQUIREMENT");

		// item in hand check
		List<String> perms = getPlugin().getAttributeHandler().getAttributeStringsFromItemStack(itemInHand,
				permissionRequirementAttribute);
		for (String s : perms) {
			if (!getPlugin().getPermissionsManager().hasPermission(player, s)) {
				if (player.getInventory().firstEmpty() >= 0) {
					player.getInventory().addItem(itemInHand);
				} else {
					player.getWorld().dropItem(player.getLocation(), itemInHand);
				}
				player.getInventory().setItem(slot, new ItemStack(Material.AIR));
				getPlugin().getLanguageManager().sendMessage(player, "events.unable-to-use-permission",
						new String[][]{{"%itemname%", getItemName(itemInHand)}});
				b = true;
				break;
			}
		}

		// helmet check
		perms = getPlugin().getAttributeHandler().getAttributeStringsFromItemStack(helmet,
				permissionRequirementAttribute);
		for (String s : perms) {
			if (!getPlugin().getPermissionsManager().hasPermission(player, s)) {
				if (player.getInventory().firstEmpty() >= 0) {
					player.getInventory().addItem(helmet);
				} else {
					player.getWorld().dropItem(player.getLocation(), helmet);
				}
				player.getEquipment().setHelmet(new ItemStack(Material.AIR));
				getPlugin().getLanguageManager().sendMessage(player, "events.unable-to-use-permission",
						new String[][]{{"%itemname%", getItemName(helmet)}});
				b = true;
				break;
			}
		}

		// chestplate check
		perms = getPlugin().getAttributeHandler().getAttributeStringsFromItemStack(chestplate,
				permissionRequirementAttribute);
		for (String s : perms) {
			if (!getPlugin().getPermissionsManager().hasPermission(player, s)) {
				if (player.getInventory().firstEmpty() >= 0) {
					player.getInventory().addItem(chestplate);
				} else {
					player.getWorld().dropItem(player.getLocation(), chestplate);
				}
				player.getEquipment().setChestplate(new ItemStack(Material.AIR));
				getPlugin().getLanguageManager().sendMessage(player, "events.unable-to-use-permission",
						new String[][]{{"%itemname%", getItemName(chestplate)}});
				b = true;
				break;
			}
		}

		// leggings check
		perms = getPlugin().getAttributeHandler().getAttributeStringsFromItemStack(leggings,
				permissionRequirementAttribute);
		for (String s : perms) {
			if (!getPlugin().getPermissionsManager().hasPermission(player, s)) {
				if (player.getInventory().firstEmpty() >= 0) {
					player.getInventory().addItem(leggings);
				} else {
					player.getWorld().dropItem(player.getLocation(), leggings);
				}
				player.getEquipment().setLeggings(new ItemStack(Material.AIR));
				getPlugin().getLanguageManager().sendMessage(player, "events.unable-to-use-permission",
						new String[][]{{"%itemname%", getItemName(leggings)}});
				b = true;
				break;
			}
		}

		// boots check
		perms = getPlugin().getAttributeHandler().getAttributeStringsFromItemStack(boots,
				permissionRequirementAttribute);
		for (String s : perms) {
			if (!getPlugin().getPermissionsManager().hasPermission(player, s)) {
				if (player.getInventory().firstEmpty() >= 0) {
					player.getInventory().addItem(boots);
				} else {
					player.getWorld().dropItem(player.getLocation(), boots);
				}
				player.getEquipment().setBoots(new ItemStack(Material.AIR));
				getPlugin().getLanguageManager().sendMessage(player, "events.unable-to-use-permission",
						new String[][]{{"%itemname%", getItemName(boots)}});
				b = true;
				break;
			}
		}

		if (b) {
			playAttributeSoundsAndEffects(player.getEyeLocation(), permissionRequirementAttribute);
		}

		return b;
	}

	@EventHandler(priority = EventPriority.LOW)
	public void onItemHeldEventLow(PlayerItemHeldEvent event) {
		handlePlayerHealthCheck(event);
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onEntityRegainHealthEventLowest(EntityRegainHealthEvent event) {
		if (!(event.getEntity() instanceof Player)) {
			return;
		}
		Player player = (Player) event.getEntity();
		handleLevelRequirementCheck(player);
		handlePermissionCheck(player);
	}

	@EventHandler(priority = EventPriority.LOW)
	public void onEntityRegainHealthEventLow(EntityRegainHealthEvent event) {
		double amount = event.getAmount();
		Attribute regenerationAttribute = getPlugin().getSettingsManager().getAttribute("REGENERATION");
		if (event.getEntity() instanceof LivingEntity) {
			LivingEntity le = (LivingEntity) event.getEntity();
			amount += getPlugin().getAttributeHandler().getAttributeValueFromEntity(le, regenerationAttribute);
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
		Attribute armorPenetrationAttribute = getPlugin().getSettingsManager().getAttribute("ARMOR PENETRATION");

		arrowDamage += getPlugin().getAttributeHandler().getAttributeValueFromItemStack(le, shotItem, damageAttribute);
		arrowDamage += getPlugin().getAttributeHandler().getAttributeValueFromItemStack(le, shotItem,
				rangedDamageAttribute);
		criticalRate += getPlugin().getAttributeHandler().getAttributeValueFromItemStack(le, shotItem, criticalRateAttribute);
		criticalDamage += getPlugin().getAttributeHandler().getAttributeValueFromItemStack(le, shotItem, criticalDamageAttribute);
		armorPenetration += getPlugin().getAttributeHandler().getAttributeValueFromItemStack(le, shotItem, armorPenetrationAttribute);
		stunRate += getPlugin().getAttributeHandler().getAttributeValueFromItemStack(le, shotItem, stunRateAttribute);
		stunLength += getPlugin().getAttributeHandler().getAttributeValueFromItemStack(le, shotItem, stunLengthAttribute);

		if (shootingItem != null) {
			bowDamage += getPlugin().getAttributeHandler().getAttributeValueFromItemStack(le, shotItem, damageAttribute);
			bowDamage += getPlugin().getAttributeHandler().getAttributeValueFromItemStack(le, shotItem, rangedDamageAttribute);
			criticalRate += getPlugin().getAttributeHandler().getAttributeValueFromItemStack(le, shotItem, criticalRateAttribute);
			criticalDamage += getPlugin().getAttributeHandler().getAttributeValueFromItemStack(le, shotItem, criticalDamageAttribute);
			armorPenetration += getPlugin().getAttributeHandler().getAttributeValueFromItemStack(le, shotItem, armorPenetrationAttribute);
			stunRate += getPlugin().getAttributeHandler().getAttributeValueFromItemStack(le, shotItem, stunRateAttribute);
			stunLength += getPlugin().getAttributeHandler().getAttributeValueFromItemStack(le, shotItem, stunLengthAttribute);
		}

		for (ItemStack is : le.getEquipment().getArmorContents()) {
			armorDamage += getPlugin().getAttributeHandler().getAttributeValueFromItemStack(le, is, damageAttribute);
			armorDamage += getPlugin().getAttributeHandler().getAttributeValueFromItemStack(le, is, rangedDamageAttribute);
			criticalRate += getPlugin().getAttributeHandler().getAttributeValueFromItemStack(le, is,
					criticalRateAttribute);
			criticalDamage += getPlugin().getAttributeHandler().getAttributeValueFromItemStack(le, is,
					criticalDamageAttribute);
			armorPenetration += getPlugin().getAttributeHandler().getAttributeValueFromItemStack(le, is, armorPenetrationAttribute);
			stunRate += getPlugin().getAttributeHandler().getAttributeValueFromItemStack(le, is, stunRateAttribute);
			stunLength += getPlugin().getAttributeHandler().getAttributeValueFromItemStack(le, is, stunLengthAttribute);
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
			b = handleLevelRequirementCheck((Player) event.getEntity()) || handlePermissionCheck((Player) event
					.getEntity());
		}

		if (event.getDamager() instanceof Player) {
			b = handleLevelRequirementCheck((Player) event.getDamager()) || handlePermissionCheck((Player) event
					.getDamager());
		}

		if (event.getDamager() instanceof Projectile && ((Projectile) event.getDamager()).getShooter() instanceof Player) {
			b = handleLevelRequirementCheck((Player) ((Projectile) event.getDamager()).getShooter()) ||
					handlePermissionCheck((Player) ((Projectile) event.getDamager()).getShooter());
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
		if (event.getDamager() instanceof Player) {
			originalDamage = (getPlugin().getSettingsManager().isItemOnlyDamageSystemEnabled()) ? getPlugin()
					.getSettingsManager().getItemOnlyDamageSystemBaseDamage() : event.getDamage();
		}

		Attribute damageAttribute = getPlugin().getSettingsManager().getAttribute("DAMAGE");
		Attribute meleeDamageAttribute = getPlugin().getSettingsManager().getAttribute("MELEE DAMAGE");
		Attribute criticalRateAttribute = getPlugin().getSettingsManager().getAttribute("CRITICAL RATE");
		Attribute criticalDamageAttribute = getPlugin().getSettingsManager().getAttribute("CRITICAL DAMAGE");
		Attribute stunRateAttribute = getPlugin().getSettingsManager().getAttribute("STUN RATE");
		Attribute stunLengthAttribute = getPlugin().getSettingsManager().getAttribute("STUN LENGTH");
		Attribute dodgeRateAttribute = getPlugin().getSettingsManager().getAttribute("DODGE RATE");
		Attribute armorAttribute = getPlugin().getSettingsManager().getAttribute("ARMOR");
		Attribute armorPenetrationAttribute = getPlugin().getSettingsManager().getAttribute("ARMOR PENETRATION");
		Attribute attackSpeedAttribute = getPlugin().getSettingsManager().getAttribute("ATTACK SPEED");
		Attribute blockAttribute = getPlugin().getSettingsManager().getAttribute("BLOCK");
		Attribute parryAttribute = getPlugin().getSettingsManager().getAttribute("PARRY");

		double damagerEquipmentDamage = 0;
		double damagerCriticalChance = 0;
		double damagerCriticalDamage = 0;
		double armorPenetration = 0;
		double stunRate = 0;
		int stunLength = 0;
		double dodgeRate = 0;

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
		} else if (event.getDamager() instanceof Player) {
			Player damager = (Player) event.getDamager();
			damagerEquipmentDamage = damageAttribute.getPlayersBaseValue() + meleeDamageAttribute.getPlayersBaseValue
					();
			damagerEquipmentDamage += getPlugin().getAttributeHandler().getAttributeValueFromEntity(damager,
					meleeDamageAttribute);
			damagerEquipmentDamage += getPlugin().getAttributeHandler().getAttributeValueFromEntity(damager,
					damageAttribute);

			damagerCriticalChance = criticalRateAttribute.getPlayersBaseValue();
			damagerCriticalChance += getPlugin().getAttributeHandler().getAttributeValueFromEntity(damager,
					criticalRateAttribute);

			damagerCriticalDamage = criticalDamageAttribute.getPlayersBaseValue();
			damagerCriticalDamage += getPlugin().getAttributeHandler().getAttributeValueFromEntity(damager,
					criticalDamageAttribute);

			stunRate = stunRateAttribute.getPlayersBaseValue();
			stunRate += getPlugin().getAttributeHandler().getAttributeValueFromEntity(damager, stunRateAttribute);

			stunLength = (int) stunLengthAttribute.getPlayersBaseValue();
			stunLength += getPlugin().getAttributeHandler().getAttributeValueFromEntity(damager, stunLengthAttribute);

			armorPenetration = armorPenetrationAttribute.getPlayersBaseValue();
			armorPenetration += getPlugin().getAttributeHandler().getAttributeValueFromEntity(damager,
					armorPenetrationAttribute);
		} else if (event.getDamager() instanceof LivingEntity) {
			LivingEntity damager = (LivingEntity) event.getDamager();
			damagerEquipmentDamage = damageAttribute.getMobsBaseValue() + meleeDamageAttribute.getMobsBaseValue();
			damagerEquipmentDamage += getPlugin().getAttributeHandler().getAttributeValueFromEntity(damager,
					meleeDamageAttribute);
			damagerEquipmentDamage += getPlugin().getAttributeHandler().getAttributeValueFromEntity(damager,
					damageAttribute);

			damagerCriticalChance = criticalRateAttribute.getMobsBaseValue();
			damagerCriticalChance += getPlugin().getAttributeHandler().getAttributeValueFromEntity(damager,
					criticalRateAttribute);

			damagerCriticalDamage = criticalDamageAttribute.getMobsBaseValue();
			damagerCriticalDamage += getPlugin().getAttributeHandler().getAttributeValueFromEntity(damager,
					criticalDamageAttribute);

			stunRate = stunRateAttribute.getMobsBaseValue();
			stunRate += getPlugin().getAttributeHandler().getAttributeValueFromEntity(damager, stunRateAttribute);

			stunLength = (int) stunLengthAttribute.getMobsBaseValue();
			stunLength += getPlugin().getAttributeHandler().getAttributeValueFromEntity(damager, stunLengthAttribute);

			armorPenetration = armorPenetrationAttribute.getMobsBaseValue();
			armorPenetration += getPlugin().getAttributeHandler().getAttributeValueFromEntity(damager,
					armorPenetrationAttribute);
		}

		double damagedEquipmentReduction = 0D;
		if (event.getEntity() instanceof Player) {
			Player entity = (Player) event.getEntity();

			dodgeRate = dodgeRateAttribute.getPlayersBaseValue();
			dodgeRate += getPlugin().getAttributeHandler()
					.getAttributeValueFromEntity(entity, dodgeRateAttribute);

			damagedEquipmentReduction = armorAttribute.getPlayersBaseValue();
			damagedEquipmentReduction += getPlugin().getAttributeHandler().getAttributeValueFromEntity(entity,
					armorAttribute);
		} else if (event.getEntity() instanceof LivingEntity) {
			LivingEntity entity = (LivingEntity) event.getEntity();

			dodgeRate = dodgeRateAttribute.getMobsBaseValue();
			dodgeRate += getPlugin().getAttributeHandler().getAttributeValueFromEntity(entity, dodgeRateAttribute);

			damagedEquipmentReduction = armorAttribute.getMobsBaseValue();
			damagedEquipmentReduction += getPlugin().getAttributeHandler().getAttributeValueFromEntity(entity,
					armorAttribute);
		}

		boolean dodged = RandomUtils.nextDouble() < dodgeRate;

		if (dodged && event.getEntity() instanceof Player) {
			getPlugin().getLanguageManager().sendMessage(((Player) event.getEntity()), "events.dodge");
			event.setDamage(0);
			event.setCancelled(true);
			playAttributeSoundsAndEffects(event.getEntity().getLocation().add(0D, 1D, 0D), dodgeRateAttribute);
			return;
		}

		double equipmentDamage = damagerEquipmentDamage - (damagedEquipmentReduction - armorPenetration);

		double damage = originalDamage + equipmentDamage;

		double maximumDamage = damage;

		damage = handleAttackSpeedChecks(event, damage, attackSpeedAttribute);

		damage = handleBlockAndParryChecks(event, damage, blockAttribute, parryAttribute);

		if (damagedEquipmentReduction != 0D) {
			playAttributeSoundsAndEffects(event.getEntity().getLocation().add(0D, 1D, 0D), armorAttribute);
		}
		if (armorPenetration != 0D) {
			playAttributeSoundsAndEffects(event.getEntity().getLocation().add(0D, 1D, 0D), armorPenetrationAttribute);
		}

		damage = handleCriticalChecks(event, damage, damagerCriticalChance, damagerCriticalDamage,
				criticalRateAttribute, criticalDamageAttribute);

		handleStunChecks(event, stunRate, stunLength, stunRateAttribute, stunLengthAttribute);

		event.setDamage(damage);
	}

	private double handleAttackSpeedChecks(EntityDamageByEntityEvent event, double damage, Attribute attackSpeedAttribute) {
		if (event.getDamager() instanceof Player) {
			long timeLeft = getPlugin().getAttackSpeedTask().getTimeLeft((LivingEntity) event.getDamager());
			double attackSpeed = attackSpeedAttribute.getPlayersBaseValue() - (attackSpeedAttribute.getPlayersBaseValue
					() * getPlugin().getAttributeHandler().getAttributeValueFromEntity((LivingEntity) event
					.getDamager(), attackSpeedAttribute));

			ItemAttributesAttributeEvent attackSpeedEvent = new ItemAttributesAttributeEvent((LivingEntity) event
					.getDamager(), (event.getEntity() instanceof LivingEntity) ? (LivingEntity) event.getEntity() :
					null, attackSpeedAttribute, new ItemAttributeValue(attackSpeed));
			Bukkit.getPluginManager().callEvent(attackSpeedEvent);

			if (!attackSpeedEvent.isCancelled()) {
				double timeToSet = 4D * Math.max(attackSpeed, 0D);
				if (timeLeft > 0) {
					double frac = Math.max(0D, Math.min(1D, 1 - (timeLeft / timeToSet)));
					damage = Math.max(1D, damage * frac);
				}

				getPlugin().getAttackSpeedTask().setTimeLeft((LivingEntity) event.getDamager(), Math.round(timeToSet));
			}
		}
		return damage;
	}

	private double handleBlockAndParryChecks(EntityDamageByEntityEvent event, double damage, Attribute blockAttribute, Attribute parryAttribute) {
		if (event.getEntity() instanceof Player && event.getDamager() instanceof LivingEntity) {
			if (((Player) event.getEntity()).isBlocking()) {

				double blockDamageReduction = blockAttribute.getPlayersBaseValue() + getPlugin().getAttributeHandler()
						.getAttributeValueFromEntity((LivingEntity) event.getEntity(), blockAttribute);

				ItemAttributesAttributeEvent blockAttributeEvent = new ItemAttributesAttributeEvent((LivingEntity) event
						.getEntity(), (LivingEntity) event.getDamager(), blockAttribute,
						new ItemAttributeValue(blockDamageReduction));
				Bukkit.getPluginManager().callEvent(blockAttributeEvent);

				if (!blockAttributeEvent.isCancelled()) {
					damage = Math.max(0D, damage * blockDamageReduction);
					playAttributeSoundsAndEffects(((LivingEntity) event.getEntity()).getEyeLocation(), blockAttribute);
				}

				if (event.getDamager() instanceof Player) {
					long timeLeft = getPlugin().getAttackSpeedTask().getTimeLeft((LivingEntity) event.getDamager());
					double parryTime = parryAttribute.getPlayersBaseValue() + getPlugin().getAttributeHandler()
							.getAttributeValueFromEntity((LivingEntity) event.getDamager(), parryAttribute);

					ItemAttributesAttributeEvent parryAttributeEvent = new ItemAttributesAttributeEvent((LivingEntity)
							event.getEntity(), (LivingEntity) event.getDamager(), parryAttribute,
							new ItemAttributeValue(parryTime));
					Bukkit.getPluginManager().callEvent(parryAttributeEvent);

					if (!parryAttributeEvent.isCancelled()) {
						getPlugin().getAttackSpeedTask().setTimeLeft((LivingEntity) event.getDamager(),
								Math.round(timeLeft * parryTime));
					}
				}
			}
		}
		return damage;
	}

	private double handleCriticalChecks(EntityDamageByEntityEvent event, double damage, double damagerCriticalChance, double damagerCriticalDamage, Attribute criticalRateAttribute, Attribute criticalDamageAttribute) {
		if (RandomUtils.nextDouble() < damagerCriticalChance) {

			ItemAttributesAttributeEvent criticalDamageEvent = null;
			ItemAttributesAttributeEvent criticalRateEvent = null;

			if (event.getDamager() instanceof LivingEntity && event.getEntity() instanceof LivingEntity) {
				criticalDamageEvent = new ItemAttributesAttributeEvent((LivingEntity) event.getDamager(),
						(LivingEntity) event.getEntity(), criticalDamageAttribute,
						new ItemAttributeValue(damagerCriticalDamage));
				Bukkit.getPluginManager().callEvent(criticalDamageEvent);
				criticalRateEvent = new ItemAttributesAttributeEvent((LivingEntity) event.getDamager(),
						(LivingEntity) event.getEntity(), criticalRateAttribute,
						new ItemAttributeValue(damagerCriticalChance));
				Bukkit.getPluginManager().callEvent(criticalRateEvent);
			}

			if (criticalDamageEvent == null) {
				double critPercentage = (1.00 + damagerCriticalDamage);
				if (event.getDamager() instanceof Player) {
					damage *= critPercentage;
					getPlugin().getLanguageManager().sendMessage((Player) event.getDamager(), "events.critical-hit",
							new String[][]{{"%percentage%", decimalFormat.format(critPercentage * 100)}});
					playAttributeSoundsAndEffects(event.getDamager().getLocation().add(0D, 1D, 0D),
							criticalRateAttribute, criticalDamageAttribute);
				} else if (event.getDamager() instanceof Projectile && ((Projectile) event.getDamager()).getShooter()
						instanceof Player) {
					damage *= critPercentage;
					getPlugin().getLanguageManager().sendMessage((Player) ((Projectile) event.getDamager()).getShooter(),
							"events.critical-hit", new String[][]{{"%percentage%", decimalFormat.format(critPercentage
							* 100)}});
					playAttributeSoundsAndEffects(event.getDamager().getLocation().add(0D, 1D, 0D),
							criticalRateAttribute, criticalDamageAttribute);
				}
			} else {
				if (!criticalDamageEvent.isCancelled() && !criticalRateEvent.isCancelled()) {
					double critPercentage = (1.00 + criticalDamageEvent.getAttributeValue().asDouble());
					if (event.getDamager() instanceof Player) {
						damage *= critPercentage;
						getPlugin().getLanguageManager().sendMessage((Player) event.getDamager(), "events.critical-hit",
								new String[][]{{"%percentage%", decimalFormat.format(critPercentage * 100)}});
					} else if (event.getDamager() instanceof Projectile && ((Projectile) event.getDamager()).getShooter()
							instanceof Player) {
						damage *= critPercentage;
						getPlugin().getLanguageManager().sendMessage((Player) ((Projectile) event.getDamager()).getShooter(),
								"events.critical-hit", new String[][]{{"%percentage%", decimalFormat.format(critPercentage
								* 100)}});
					}
					playAttributeSoundsAndEffects(event.getDamager().getLocation().add(0D, 1D, 0D), criticalRateAttribute, criticalDamageAttribute);
				}
			}
		}
		return damage;
	}

	private void handleStunChecks(EntityDamageByEntityEvent event, double stunRate, int stunLength, Attribute stunRateAttribute, Attribute stunLengthAttribute) {
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
					Bukkit.getPluginManager().callEvent(new ItemAttributesAttributeEvent((LivingEntity) event
							.getDamager(), (LivingEntity) event.getEntity(), stunRateAttribute,
							new ItemAttributeValue(stunRate)));
					Bukkit.getPluginManager().callEvent(new ItemAttributesAttributeEvent((LivingEntity) event
							.getDamager(), (LivingEntity) event.getEntity(), stunLengthAttribute,
							new ItemAttributeValue(stunLength)));

					defender.addPotionEffect(new PotionEffect(PotionEffectType.SLOW,
							stunLength * 20, 7));
					defender.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS,
							stunLength * 20, 7));
				}
			}
			playAttributeSoundsAndEffects(event.getEntity().getLocation().add(0D, 1D, 0D), stunLengthAttribute,
					stunRateAttribute);
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onEntityDamageEventLowest(EntityDamageEvent event) {
		if (event.isCancelled() || event instanceof EntityDamageByEntityEvent || !(event.getEntity() instanceof
				Player)) {
			return;
		}
		Player player = (Player) event.getEntity();
		handleLevelRequirementCheck(player);
		handlePermissionCheck(player);
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
			if (getPlugin().getAttributeHandler().hasAttributeOnEntity((LivingEntity) event.getEntity(),
					getPlugin().getSettingsManager().getAttribute("FIRE IMMUNITY"))) {
				event.setDamage(0);
				event.setCancelled(true);
			}
		} else if (damageCause == EntityDamageEvent.DamageCause.POISON) {
			if (getPlugin().getAttributeHandler().hasAttributeOnEntity((LivingEntity) event.getEntity(),
					getPlugin().getSettingsManager().getAttribute("POISON IMMUNITY"))) {
				event.setDamage(0);
				event.setCancelled(true);
			}
		} else if (damageCause == EntityDamageEvent.DamageCause.WITHER) {
			if (getPlugin().getAttributeHandler().hasAttributeOnEntity((LivingEntity) event.getEntity(),
					getPlugin().getSettingsManager().getAttribute("WITHER IMMUNITY"))) {
				event.setDamage(0);
				event.setCancelled(true);
			}
		}

		if (event.isCancelled()) {
			playAttributeSoundsAndEffects(((LivingEntity) event.getEntity()).getEyeLocation(), getPlugin().getSettingsManager()
					.getAttribute("FIRE IMMUNITY"), getPlugin().getSettingsManager().getAttribute("POISON IMMUNITY"),
					getPlugin().getSettingsManager().getAttribute("WITHER IMMUNITY"));
		}
	}

}
