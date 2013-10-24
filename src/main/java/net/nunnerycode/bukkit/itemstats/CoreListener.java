package net.nunnerycode.bukkit.itemstats;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.commons.lang3.text.WordUtils;
import org.bukkit.ChatColor;
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

public final class CoreListener implements Listener {

	private final ItemStatsPlugin plugin;
	private final DecimalFormat decimalFormat;

	public CoreListener(ItemStatsPlugin plugin) {
		this.plugin = plugin;
		decimalFormat = new DecimalFormat("#.##");
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onCreatureSpawnEvent(CreatureSpawnEvent event) {
		if (event.isCancelled()) {
			return;
		}
		double maxHealth = event.getEntity().getMaxHealth();
		MetadataValue metadataValue = new FixedMetadataValue(getPlugin(), maxHealth);
		event.getEntity().setMetadata("itemstats.basehealth", metadataValue);
	}

	public ItemStatsPlugin getPlugin() {
		return plugin;
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

	private void handleLevelRequirementCheck(Player player) {
		ItemStack itemInHand = player.getEquipment().getItemInHand();
		ItemStack helmet = player.getEquipment().getHelmet();
		ItemStack chestplate = player.getEquipment().getChestplate();
		ItemStack leggings = player.getEquipment().getLeggings();
		ItemStack boots = player.getEquipment().getBoots();

		// item in hand check
		int level = ParseUtil.getLevelRequired(getItemStackLore(itemInHand), getPlugin().getSettingsManager()
				.getLevelRequirementFormat());
		if (player.getLevel() < level) {
			if (player.getInventory().firstEmpty() >= 0) {
				player.getInventory().addItem(itemInHand);
			} else {
				player.getWorld().dropItem(player.getLocation(), itemInHand);
			}
			player.getEquipment().setItemInHand(null);
			getPlugin().getLanguageManager().sendMessage(player, "unable-to-use",
					new String[][]{{"%itemname%", getItemName(itemInHand)}, {"%level%", String.valueOf(level)}});
		}

		// helmet check
		level = ParseUtil.getLevelRequired(getItemStackLore(helmet), getPlugin().getSettingsManager()
				.getLevelRequirementFormat());
		if (player.getLevel() < level) {
			if (player.getInventory().firstEmpty() >= 0) {
				player.getInventory().addItem(helmet);
			} else {
				player.getWorld().dropItem(player.getLocation(), helmet);
			}
			player.getEquipment().setHelmet(null);
			getPlugin().getLanguageManager().sendMessage(player, "unable-to-use",
					new String[][]{{"%itemname%", getItemName(helmet)}, {"%level%", String.valueOf(level)}});
		}

		// chestplate check
		level = ParseUtil.getLevelRequired(getItemStackLore(chestplate), getPlugin().getSettingsManager()
				.getLevelRequirementFormat());
		if (player.getLevel() < level) {
			if (player.getInventory().firstEmpty() >= 0) {
				player.getInventory().addItem(chestplate);
			} else {
				player.getWorld().dropItem(player.getLocation(), chestplate);
			}
			player.getEquipment().setChestplate(null);
			getPlugin().getLanguageManager().sendMessage(player, "unable-to-use",
					new String[][]{{"%itemname%", getItemName(chestplate)}, {"%level%", String.valueOf(level)}});
		}

		// leggings check
		level = ParseUtil.getLevelRequired(getItemStackLore(leggings), getPlugin().getSettingsManager()
				.getLevelRequirementFormat());
		if (player.getLevel() < level) {
			if (player.getInventory().firstEmpty() >= 0) {
				player.getInventory().addItem(leggings);
			} else {
				player.getWorld().dropItem(player.getLocation(), leggings);
			}
			player.getEquipment().setLeggings(null);
			getPlugin().getLanguageManager().sendMessage(player, "unable-to-use",
					new String[][]{{"%itemname%", getItemName(leggings)}, {"%level%", String.valueOf(level)}});
		}

		// boots check
		level = ParseUtil.getLevelRequired(getItemStackLore(boots), getPlugin().getSettingsManager()
				.getLevelRequirementFormat());
		if (player.getLevel() < level) {
			if (player.getInventory().firstEmpty() >= 0) {
				player.getInventory().addItem(boots);
			} else {
				player.getWorld().dropItem(player.getLocation(), boots);
			}
			player.getEquipment().setBoots(null);
			getPlugin().getLanguageManager().sendMessage(player, "unable-to-use",
					new String[][]{{"%itemname%", getItemName(boots)}, {"%level%", String.valueOf(level)}});
		}
	}

	public List<String> getItemStackLore(ItemStack itemStack) {
		List<String> lore = new ArrayList<String>();
		if (itemStack != null && itemStack.hasItemMeta() && itemStack.getItemMeta().hasLore()) {
			lore.addAll(itemStack.getItemMeta().getLore());
		}
		return lore;
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

	@EventHandler(priority = EventPriority.LOW)
	public void onInventoryCloseEventLow(InventoryCloseEvent event) {
		for (HumanEntity he : event.getViewers()) {
			if (he.isDead()) {
				continue;
			}
			ItemStack[] armorContents = he.getEquipment().getArmorContents();
			double d = 0.0;
			for (ItemStack is : armorContents) {
				d += ParseUtil.getHealth(getItemStackLore(is), getPlugin().getSettingsManager()
						.getHealthFormat());
			}
			d += ParseUtil.getHealth(getItemStackLore(he.getEquipment().getItemInHand()),
					getPlugin().getSettingsManager().getHealthFormat());
			double currentHealth = he.getHealth();
			double baseMaxHealth = getPlugin().getSettingsManager().getBasePlayerHealth();
			he.setMaxHealth(Math.max(baseMaxHealth + d, 1));
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
		ItemStack[] armorContents = event.getPlayer().getEquipment().getArmorContents();
		double d = 0.0;
		for (ItemStack is : armorContents) {
			d += ParseUtil.getHealth(getItemStackLore(is), getPlugin().getSettingsManager()
					.getHealthFormat());
		}
		d += ParseUtil.getHealth(getItemStackLore(event.getPlayer().getItemInHand()),
				getPlugin().getSettingsManager().getHealthFormat());
		double currentHealth = event.getPlayer().getHealth();
		double baseMaxHealth = getPlugin().getSettingsManager().getBasePlayerHealth();
		event.getPlayer().setMaxHealth(Math.max(baseMaxHealth + d, 1));
		event.getPlayer().setHealth(Math.min(Math.max(currentHealth, 0), event.getPlayer().getMaxHealth()));
		event.getPlayer().setHealthScale(event.getPlayer().getMaxHealth());
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onEntityTargetEventLowest(EntityTargetEvent event) {
		if (event.isCancelled() || !(event.getEntity() instanceof
				LivingEntity) || event.getEntity() instanceof Player) {
			return;
		}
		LivingEntity entity = (LivingEntity) event.getEntity();
		ItemStack[] armorContents = entity.getEquipment().getArmorContents();
		double d = 0.0;
		for (ItemStack is : armorContents) {
			d += ParseUtil.getHealth(getItemStackLore(is), getPlugin().getSettingsManager()
					.getHealthFormat());
		}
		d += ParseUtil.getHealth(getItemStackLore(entity.getEquipment().getItemInHand()),
				getPlugin().getSettingsManager().getHealthFormat());
		double currentHealth = entity.getHealth();
		entity.resetMaxHealth();
		double baseMaxHealth = entity.getMaxHealth();
		if (entity.hasMetadata("itemstats.basehealth")) {
			List<MetadataValue> metadataValueList = entity.getMetadata("itemstats.basehealth");
			for (MetadataValue mv : metadataValueList) {
				if (mv.getOwningPlugin().equals(getPlugin())) {
					baseMaxHealth = mv.asDouble();
					break;
				}
			}
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
		ItemStack[] armorContents = event.getPlayer().getEquipment().getArmorContents();
		double d = 0.0;
		for (ItemStack is : armorContents) {
			d += ParseUtil.getHealth(getItemStackLore(is), getPlugin().getSettingsManager()
					.getHealthFormat());
		}
		d += ParseUtil.getHealth(getItemStackLore(event.getPlayer().getItemInHand()),
				getPlugin().getSettingsManager().getHealthFormat());
		double currentHealth = event.getPlayer().getHealth();
		double baseMaxHealth = getPlugin().getSettingsManager().getBasePlayerHealth();
		event.getPlayer().setMaxHealth(Math.max(baseMaxHealth + d, 1));
		event.getPlayer().setHealth(Math.min(Math.max(currentHealth, 0), event.getPlayer().getMaxHealth()));
		event.getPlayer().setHealthScale(event.getPlayer().getMaxHealth());
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onItemBreakEventLowest(PlayerItemBreakEvent event) {
		ItemStack[] armorContents = event.getPlayer().getEquipment().getArmorContents();
		double d = 0.0;
		for (ItemStack is : armorContents) {
			d += ParseUtil.getHealth(getItemStackLore(is), getPlugin().getSettingsManager()
					.getHealthFormat());
		}
		d += ParseUtil.getHealth(getItemStackLore(event.getPlayer().getItemInHand()),
				getPlugin().getSettingsManager().getHealthFormat());
		double currentHealth = event.getPlayer().getHealth();
		double baseMaxHealth = getPlugin().getSettingsManager().getBasePlayerHealth();
		event.getPlayer().setMaxHealth(Math.max(baseMaxHealth + d, 1));
		event.getPlayer().setHealth(Math.min(Math.max(currentHealth, 0), event.getPlayer().getMaxHealth()));
		event.getPlayer().setHealthScale(event.getPlayer().getMaxHealth());
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onItemHeldEventLowest(PlayerItemHeldEvent event) {
		Player player = event.getPlayer();
		handleLevelRequirementCheckSlot(player, event.getNewSlot());
	}

	private void handleLevelRequirementCheckSlot(Player player, int i) {
		ItemStack itemInHand = player.getInventory().getItem(i);
		ItemStack helmet = player.getEquipment().getHelmet();
		ItemStack chestplate = player.getEquipment().getChestplate();
		ItemStack leggings = player.getEquipment().getLeggings();
		ItemStack boots = player.getEquipment().getBoots();

		// item in hand check
		int level = ParseUtil.getLevelRequired(getItemStackLore(itemInHand), getPlugin().getSettingsManager()
				.getLevelRequirementFormat());
		if (player.getLevel() < level) {
			if (player.getInventory().firstEmpty() >= 0) {
				player.getInventory().addItem(itemInHand);
			} else {
				player.getWorld().dropItem(player.getLocation(), itemInHand);
			}
			player.getInventory().setItem(i, null);
			getPlugin().getLanguageManager().sendMessage(player, "unable-to-use",
					new String[][]{{"%itemname%", getItemName(itemInHand)}, {"%level%", String.valueOf(level)}});
		}

		// helmet check
		level = ParseUtil.getLevelRequired(getItemStackLore(helmet), getPlugin().getSettingsManager()
				.getLevelRequirementFormat());
		if (player.getLevel() < level) {
			if (player.getInventory().firstEmpty() >= 0) {
				player.getInventory().addItem(helmet);
			} else {
				player.getWorld().dropItem(player.getLocation(), helmet);
			}
			player.getEquipment().setHelmet(null);
			getPlugin().getLanguageManager().sendMessage(player, "unable-to-use",
					new String[][]{{"%itemname%", getItemName(helmet)}, {"%level%", String.valueOf(level)}});
		}

		// chestplate check
		level = ParseUtil.getLevelRequired(getItemStackLore(chestplate), getPlugin().getSettingsManager()
				.getLevelRequirementFormat());
		if (player.getLevel() < level) {
			if (player.getInventory().firstEmpty() >= 0) {
				player.getInventory().addItem(chestplate);
			} else {
				player.getWorld().dropItem(player.getLocation(), chestplate);
			}
			player.getEquipment().setChestplate(null);
			getPlugin().getLanguageManager().sendMessage(player, "unable-to-use",
					new String[][]{{"%itemname%", getItemName(chestplate)}, {"%level%", String.valueOf(level)}});
		}

		// leggings check
		level = ParseUtil.getLevelRequired(getItemStackLore(leggings), getPlugin().getSettingsManager()
				.getLevelRequirementFormat());
		if (player.getLevel() < level) {
			if (player.getInventory().firstEmpty() >= 0) {
				player.getInventory().addItem(leggings);
			} else {
				player.getWorld().dropItem(player.getLocation(), leggings);
			}
			player.getEquipment().setLeggings(null);
			getPlugin().getLanguageManager().sendMessage(player, "unable-to-use",
					new String[][]{{"%itemname%", getItemName(leggings)}, {"%level%", String.valueOf(level)}});
		}

		// boots check
		level = ParseUtil.getLevelRequired(getItemStackLore(boots), getPlugin().getSettingsManager()
				.getLevelRequirementFormat());
		if (player.getLevel() < level) {
			if (player.getInventory().firstEmpty() >= 0) {
				player.getInventory().addItem(boots);
			} else {
				player.getWorld().dropItem(player.getLocation(), boots);
			}
			player.getEquipment().setBoots(null);
			getPlugin().getLanguageManager().sendMessage(player, "unable-to-use",
					new String[][]{{"%itemname%", getItemName(boots)}, {"%level%", String.valueOf(level)}});
		}
	}

	@EventHandler(priority = EventPriority.LOW)
	public void onItemHeldEventLow(PlayerItemHeldEvent event) {
		ItemStack[] armorContents = event.getPlayer().getEquipment().getArmorContents();
		double d = 0.0;
		for (ItemStack is : armorContents) {
			d += ParseUtil.getHealth(getItemStackLore(is), getPlugin().getSettingsManager()
					.getHealthFormat());
		}
		d += ParseUtil.getHealth(getItemStackLore(event.getPlayer().getInventory().getItem(event.getNewSlot())),
				getPlugin().getSettingsManager().getHealthFormat());
		double currentHealth = event.getPlayer().getHealth();
		double baseMaxHealth = getPlugin().getSettingsManager().getBasePlayerHealth();
		event.getPlayer().setMaxHealth(Math.max(baseMaxHealth + d, 1));
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
		if (event.getEntity() instanceof LivingEntity) {
			LivingEntity le = (LivingEntity) event.getEntity();
			ItemStack[] armorContents = le.getEquipment().getArmorContents();
			for (ItemStack is : armorContents) {
				amount += ParseUtil.getRegeneration(getItemStackLore(is), getPlugin().getSettingsManager()
						.getRegenerationFormat());
			}
			amount += ParseUtil.getRegeneration(getItemStackLore(le.getEquipment().getItemInHand()),
					getPlugin().getSettingsManager().getRegenerationFormat());
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

		double arrowDamage = 0.0D;
		arrowDamage += ParseUtil.getDamage(getItemStackLore(shotItem), getPlugin().getSettingsManager()
				.getDamageFormat());
		arrowDamage += ParseUtil.getRangedDamage(getItemStackLore(shotItem), getPlugin().getSettingsManager()
				.getRangedDamageFormat());

		double bowDamage = 0.0;

		if (shootingItem != null) {
			bowDamage += ParseUtil.getDamage(getItemStackLore(shootingItem), getPlugin().getSettingsManager()
					.getDamageFormat());
			bowDamage += ParseUtil.getRangedDamage(getItemStackLore(shootingItem), getPlugin().getSettingsManager()
					.getRangedDamageFormat());
		}

		double armorDamage = 0.0;
		for (ItemStack is : le.getEquipment().getArmorContents()) {
			armorDamage += ParseUtil.getDamage(getItemStackLore(is), getPlugin().getSettingsManager()
					.getDamageFormat());
			armorDamage += ParseUtil.getRangedDamage(getItemStackLore(is), getPlugin().getSettingsManager()
					.getRangedDamageFormat());
		}

		double totalDamage = arrowDamage + bowDamage + armorDamage;

		double criticalRate = 0.0;
		double criticalDamage = 0.0;

		criticalRate += ParseUtil.getCriticalRate(getItemStackLore(shotItem), getPlugin().getSettingsManager()
				.getCriticalRateFormat());
		criticalDamage += ParseUtil.getCriticalDamage(getItemStackLore(shotItem), getPlugin().getSettingsManager()
				.getCriticalDamageFormat());

		if (shootingItem != null) {
			criticalRate += ParseUtil.getCriticalRate(getItemStackLore(shootingItem), getPlugin().getSettingsManager()
					.getCriticalRateFormat());
			criticalDamage += ParseUtil.getCriticalDamage(getItemStackLore(shootingItem),
					getPlugin().getSettingsManager().getCriticalDamageFormat());
		}

		for (ItemStack is : le.getEquipment().getArmorContents()) {
			criticalRate += ParseUtil.getCriticalRate(getItemStackLore(is), getPlugin().getSettingsManager()
					.getCriticalRateFormat());
			criticalDamage += ParseUtil.getCriticalDamage(getItemStackLore(is), getPlugin().getSettingsManager()
					.getCriticalDamageFormat());
		}

		event.getEntity().setMetadata("itemstats.damage", new FixedMetadataValue(getPlugin(), totalDamage));
		event.getEntity().setMetadata("itemstats.criticalrate", new FixedMetadataValue(getPlugin(), criticalRate));
		event.getEntity().setMetadata("itemstats.criticaldamage", new FixedMetadataValue(getPlugin(), criticalDamage));
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

		if (event.getEntity() instanceof Player) {
			handleLevelRequirementCheck((Player) event.getEntity());
		}

		if (event.getDamager() instanceof Player) {
			handleLevelRequirementCheck((Player) event.getDamager());
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
		double damagerCriticalChance = 0D;
		double damagerCriticalDamage = 0D;

		if (event.getDamager() instanceof Projectile) {
			Projectile projectile = (Projectile) event.getDamager();
			LivingEntity shooter = projectile.getShooter();
			if (shooter != null) {
				if (projectile.hasMetadata("itemstats.damage")) {
					List<MetadataValue> metadataValueList = projectile.getMetadata("itemstats.damage");
					for (MetadataValue mv : metadataValueList) {
						if (mv.getOwningPlugin().equals(getPlugin())) {
							damagerEquipmentDamage = mv.asDouble();
							break;
						}
					}
				}
				if (projectile.hasMetadata("itemstats.criticalrate")) {
					List<MetadataValue> metadataValueList = projectile.getMetadata("itemstats.criticalrate");
					for (MetadataValue mv : metadataValueList) {
						if (mv.getOwningPlugin().equals(getPlugin())) {
							damagerCriticalChance = mv.asDouble();
							break;
						}
					}
				}
				if (projectile.hasMetadata("itemstats.criticaldamage")) {
					List<MetadataValue> metadataValueList = projectile.getMetadata("itemstats.criticaldamage");
					for (MetadataValue mv : metadataValueList) {
						if (mv.getOwningPlugin().equals(getPlugin())) {
							damagerCriticalDamage = mv.asDouble();
							break;
						}
					}
				}
			}
		} else if (event.getDamager() instanceof LivingEntity) {
			LivingEntity damager = (LivingEntity) event.getDamager();
			ItemStack[] armor = damager.getEquipment().getArmorContents();
			for (ItemStack is : armor) {
				damagerEquipmentDamage += ParseUtil.getMeleeDamage(getItemStackLore(is),
						getPlugin().getSettingsManager().getMeleeDamageFormat());
				damagerEquipmentDamage += ParseUtil.getDamage(getItemStackLore(is), getPlugin().getSettingsManager()
						.getDamageFormat());
				damagerCriticalChance += ParseUtil.getCriticalRate(getItemStackLore(is), getPlugin().getSettingsManager()
						.getCriticalRateFormat());
				damagerCriticalDamage += ParseUtil.getCriticalDamage(getItemStackLore(is),
						getPlugin().getSettingsManager().getCriticalDamageFormat());
			}
			damagerEquipmentDamage += ParseUtil.getMeleeDamage(getItemStackLore(damager.getEquipment().getItemInHand
					()), getPlugin().getSettingsManager().getMeleeDamageFormat());
			damagerEquipmentDamage += ParseUtil.getDamage(getItemStackLore(damager.getEquipment().getItemInHand()),
					getPlugin().getSettingsManager().getDamageFormat());
			damagerCriticalChance += ParseUtil.getCriticalRate(getItemStackLore(damager.getEquipment().getItemInHand()),
					getPlugin().getSettingsManager().getCriticalRateFormat());
			damagerCriticalDamage += ParseUtil.getCriticalDamage(getItemStackLore(damager.getEquipment().getItemInHand
					()), getPlugin().getSettingsManager().getCriticalDamageFormat());
		}

		double damagedEquipmentReduction = 0D;
		if (event.getEntity() instanceof LivingEntity) {
			LivingEntity entity = (LivingEntity) event.getEntity();
			ItemStack[] armor = entity.getEquipment().getArmorContents();
			for (ItemStack is : armor) {
				damagedEquipmentReduction += ParseUtil.getArmor(getItemStackLore(is), getPlugin().getSettingsManager()
						.getArmorFormat());
			}
			damagedEquipmentReduction += ParseUtil.getArmor(getItemStackLore(entity.getEquipment().getItemInHand()),
					getPlugin().getSettingsManager().getArmorFormat());
		}

		damage = Math.max(originalDamage + damagerEquipmentDamage + damagedEquipmentReduction, 0);

		if (RandomUtils.nextDouble() < damagerCriticalChance) {
			double critPercentage = (1.00 + damagerCriticalDamage);
			damage *= critPercentage;
			if (event.getDamager() instanceof Player) {
				getPlugin().getLanguageManager().sendMessage((Player) event.getDamager(), "critical-hit",
						new String[][]{{"%percentage%", decimalFormat.format(critPercentage * 100)}});
			} else if (event.getDamager() instanceof Projectile && ((Projectile) event.getDamager()).getShooter()
					instanceof Player) {
				getPlugin().getLanguageManager().sendMessage((Player) ((Projectile) event.getDamager()).getShooter(),
						"critical-hit", new String[][]{{"%percentage%", decimalFormat.format(critPercentage * 100)}});
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
					b = ParseUtil.hasFireImmunity(getItemStackLore(is), getPlugin().getSettingsManager()
							.getFireImmunityFormat());
				}
			}
			if (!b) {
				b = ParseUtil.hasFireImmunity(getItemStackLore(((LivingEntity) event.getEntity()).getEquipment()
						.getItemInHand()), getPlugin().getSettingsManager().getFireImmunityFormat());
			}
			if (b) {
				event.setDamage(0);
				event.setCancelled(true);
				return;
			}
		}

		if (damageCause == EntityDamageEvent.DamageCause.POISON) {
			boolean b = false;
			for (ItemStack is : ((LivingEntity) event.getEntity()).getEquipment().getArmorContents()) {
				if (!b) {
					b = ParseUtil.hasPoisonImmunity(getItemStackLore(is), getPlugin().getSettingsManager()
							.getPoisonImmunityFormat());
				}
			}
			if (!b) {
				b = ParseUtil.hasPoisonImmunity(getItemStackLore(((LivingEntity) event.getEntity()).getEquipment()
						.getItemInHand()), getPlugin().getSettingsManager().getPoisonImmunityFormat());
			}
			if (b) {
				event.setDamage(0);
				event.setCancelled(true);
				return;
			}
		}

		if (damageCause == EntityDamageEvent.DamageCause.WITHER) {
			boolean b = false;
			for (ItemStack is : ((LivingEntity) event.getEntity()).getEquipment().getArmorContents()) {
				if (!b) {
					b = ParseUtil.hasWitherImmunity(getItemStackLore(is), getPlugin().getSettingsManager()
							.getWitherImmunityFormat());
				}
			}
			if (!b) {
				b = ParseUtil.hasWitherImmunity(getItemStackLore(((LivingEntity) event.getEntity()).getEquipment()
						.getItemInHand()), getPlugin().getSettingsManager().getWitherImmunityFormat());
			}
			if (b) {
				event.setDamage(0);
				event.setCancelled(true);
			}
		}
	}

}
